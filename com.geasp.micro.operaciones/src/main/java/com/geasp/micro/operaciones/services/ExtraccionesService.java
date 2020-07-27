package com.geasp.micro.operaciones.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import com.geasp.micro.operaciones.models.Extraccion;
import com.geasp.micro.operaciones.models.Mercancia;
import com.geasp.micro.operaciones.repositories.ExtraccionRepository;
import com.geasp.micro.operaciones.requests.OperacionRequest;
import com.geasp.micro.operaciones.responses.ExtraccionResponse;

import reactor.core.publisher.Mono;

import org.dozer.Mapper;
import org.keycloak.KeycloakSecurityContext;

@Service
public class ExtraccionesService implements IOperacionesService<ExtraccionResponse, OperacionRequest>{
	
	@Autowired
	private ExtraccionRepository extracciones;
	@Autowired
	private Mapper mapper;
	
	@Autowired
	private WebClient.Builder webClientBuilder;	
	
	@Autowired
	private KeycloakSecurityContext securityContext;
	
	private Mono<Mercancia> get(Long id) {
		return webClientBuilder.build().get()
				.uri("http://MERCANCIAS/operaciones/"+id)
				.headers(header->{
					header.setBearerAuth(securityContext.getTokenString());
					header.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
				})
				.retrieve()
				.bodyToMono(Mercancia.class);
	}	
	
	private Mono<Mercancia> extract(Long id) {
		return webClientBuilder.build()
				.post()
				.uri("http://MERCANCIAS/operaciones/extraer/"+id)
				.headers(header->{
					header.setBearerAuth(securityContext.getTokenString());
					header.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
				})
				.retrieve()
				.bodyToMono(Mercancia.class);
	}
	
	private Mono<Mercancia> revertir(Long id) {
		return webClientBuilder.build()
				.post()
				.uri("http://MERCANCIAS/operaciones/revertir/"+id)
				.headers(header->{
					header.setBearerAuth(securityContext.getTokenString());
					header.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
				})
				.retrieve()
				.bodyToMono(Mercancia.class);
	}	
	
	@Override
	public ExtraccionResponse registrar(OperacionRequest entity, Long id) {
		try {
			Mercancia mercancia = extract(id).block();
			if (mercancia.getId()==id) {
				Extraccion extraccion = mapper.map(entity, Extraccion.class);
				extraccion.setMercanciaId(mercancia.getId());
				extraccion.setTipoMercancia(mercancia.getTipo_mercancia());
				extracciones.saveAndFlush(extraccion);
				ExtraccionResponse res = mapper.map(extraccion, ExtraccionResponse.class);
				res.setMercancia(mercancia);
				return res;
			}else {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND,"El contenedor a extraer no existe");
			}			
		} catch (ResponseStatusException e) { 
			  throw new ResponseStatusException(e.getStatus(), e.getMessage()); 
		}
	}
	
	@Override
	public ExtraccionResponse viewById(Long id) {
		try {
			if (extracciones.findByMercanciaId(id).isPresent()) {
				Extraccion extraccion = extracciones.findByMercanciaId(id).get();
				Mercancia mercancia = get(extraccion.getMercanciaId()).block();
				ExtraccionResponse res = mapper.map(extraccion, ExtraccionResponse.class);
				res.setMercancia(mercancia);
				return res;
			} else {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND,"La extracción solicitada no se encuentra");				
			}
		}
		catch (ResponseStatusException e) {
			throw new ResponseStatusException(e.getStatus(), e.getMessage());
		}
	}	

	@SuppressWarnings("unused")
	private ExtraccionResponse getFallbackView(Long id){
		return new ExtraccionResponse("Error al obtener la operación solicitada, inténtelo mas tarde.");
	}
	
	@Override
	public List<ExtraccionResponse> listar() {
		try {				
			List<ExtraccionResponse> response = new ArrayList<ExtraccionResponse>();
			List<Extraccion> lista = extracciones.findAll();
			
			lista.stream().forEach(index-> {
				Mercancia mercancia = get(index.getMercanciaId()).block();
				ExtraccionResponse item = mapper.map(index, ExtraccionResponse.class);
				item.setMercancia(mercancia);
				response.add(item);
			});
			return response.stream().collect(Collectors.toList());
			
		} catch (ResponseStatusException e) {
			throw new ResponseStatusException(e.getStatus(), e.getMessage());
		}
	}	

	@SuppressWarnings("unused")
	private List<ExtraccionResponse> getFallbackListar(){
		return new ArrayList<ExtraccionResponse>();
	}
	
	@Override
	public ExtraccionResponse updateById(OperacionRequest request, Long id) {
		try {
			Optional<Extraccion> extraccionOptional= extracciones.findByMercanciaId(id);
			if (extraccionOptional.isPresent() && comprobar(extraccionOptional)) {
				Extraccion extraccionActualizada = extraccionOptional.get();
				Extraccion data = mapper.map(request, Extraccion.class);
				extraccionActualizada.setFecha(data.getFecha());				
				extracciones.saveAndFlush(extraccionActualizada);
				ExtraccionResponse res = mapper.map(extraccionActualizada, ExtraccionResponse.class);
				Mercancia mercancia = get(extraccionActualizada.getMercanciaId()).block();
				res.setMercancia(mercancia);
				return res;
			}else {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND,"El operacion a actualizar no existe");
			}
		} catch (ResponseStatusException e) {
			throw new ResponseStatusException(e.getStatus(), e.getMessage());
		}
	}
	
	@Override	
	public ExtraccionResponse deleteById(Long id) {
		try {
			Optional<Extraccion> extraccionOptional= extracciones.findByMercanciaId(id);			
			if (extraccionOptional.isPresent() && comprobar(extraccionOptional)) {
				Extraccion extraccion = extraccionOptional.get();
				if (extraccionOptional.get().isActivo()==false) {
					Mercancia mercancia = revertir(extraccion.getMercanciaId()).block();
					ExtraccionResponse res = mapper.map(extraccion, ExtraccionResponse.class);
					res.setMercancia(mercancia);
					extracciones.delete(extraccionOptional.get());
					return res;
				}else {
					throw new ResponseStatusException(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS,"La operacion no esta permitida");
				}
			} else {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"La operación a eliminar no existe");
			}
		} catch (ResponseStatusException e) {
			throw new ResponseStatusException(e.getStatus(), e.getMessage());
		}
	}
	
	private Boolean comprobar(Optional<Extraccion> extraccionOptional) {
		return get(extraccionOptional.get().getMercanciaId()).block().getId()!=null;
	}

	@Override
	public ExtraccionResponse activateById(Long id) {
		try {
			Optional<Extraccion> extraccionOptional= extracciones.findByMercanciaId(id);
			if (extraccionOptional.isPresent()) {
				Extraccion extraccion = extraccionOptional.get();
				extraccion.setActivo(!extraccion.isActivo());				
				extracciones.saveAndFlush(extraccion);
				ExtraccionResponse res = mapper.map(extraccion, ExtraccionResponse.class);
				Mercancia mercancia = get(extraccion.getMercanciaId()).block();
				res.setMercancia(mercancia);
				return res;
			}else {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND,"El operacion a actualizar no existe");
			}
		} catch (ResponseStatusException e) {
			throw new ResponseStatusException(e.getStatus(), e.getMessage());
		}
	}

}
