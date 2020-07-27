package com.geasp.micro.operaciones.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.dozer.Mapper;
import org.keycloak.KeycloakSecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import com.geasp.micro.operaciones.models.Devolucion;
import com.geasp.micro.operaciones.models.Extraccion;
import com.geasp.micro.operaciones.models.Mercancia;
import com.geasp.micro.operaciones.repositories.DevolucionRepository;
import com.geasp.micro.operaciones.repositories.ExtraccionRepository;
import com.geasp.micro.operaciones.requests.OperacionRequest;
import com.geasp.micro.operaciones.responses.DevolucionResponse;
import com.geasp.micro.operaciones.responses.ExtraccionResponse;
import reactor.core.publisher.Mono;

@Service
public class DevolucionesService implements IOperacionesService<DevolucionResponse, OperacionRequest> {
	
	@Autowired
	private DevolucionRepository devoluciones;
	
	@Autowired
	private ExtraccionRepository daoExtracciones;
	
	@Autowired
	private KeycloakSecurityContext securityContext;
	
	@Autowired
	private WebClient.Builder webClientBuilder;
	
	@Autowired
	private Mapper mapper;

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
	
	private Mono<Mercancia> devolver(Long id) {
		return webClientBuilder.build()
				.post()
				.uri("http://MERCANCIAS/operaciones/devolver/"+id)
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
	public DevolucionResponse registrar(OperacionRequest entity, Long id) {
		try {
			Optional<Extraccion> optionalExtraccion = daoExtracciones.findByMercanciaId(id);			
			if (optionalExtraccion.isPresent() && comprobar(optionalExtraccion)) {
				Extraccion extraccion = optionalExtraccion.get();				
				Devolucion devolucion = mapper.map(entity, Devolucion.class);
				devolucion.setExtraccion(extraccion);
				devoluciones.saveAndFlush(devolucion);
				
				Mercancia mercancia = devolver(extraccion.getMercanciaId()).block();
				
				ExtraccionResponse resExtraccion = mapper.map(extraccion, ExtraccionResponse.class);
				resExtraccion.setMercancia(mercancia);
				DevolucionResponse res = new DevolucionResponse();
				res = mapper.map(devolucion, DevolucionResponse.class);
				res.setExtraccion(resExtraccion);
				return res;				
			} else {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND,"La operación solicitada no es posible");
			}
		} catch (ResponseStatusException e) {
			throw new ResponseStatusException(e.getStatus(), e.getMessage());
		}
	}

	@Override
	public List<DevolucionResponse> listar() {
		try {
			List<DevolucionResponse> response = new ArrayList<DevolucionResponse>();
			List<Devolucion> lista = devoluciones.findAll();
			lista.stream().forEach(index -> {
				Extraccion extraccion = index.getExtraccion();
				Mercancia mercancia = get(extraccion.getMercanciaId()).block();
				ExtraccionResponse data = mapper.map(extraccion, ExtraccionResponse.class);
				data.setMercancia(mercancia);
				DevolucionResponse item = mapper.map(index, DevolucionResponse.class);
				item.setExtraccion(data);
				response.add(item);
			});
			return response.stream().collect(Collectors.toList());				
		} catch (ResponseStatusException e) {
			throw new ResponseStatusException(e.getStatus(), e.getMessage());
		}
	}
	
	@SuppressWarnings("unused")
	private List<DevolucionResponse> getFallbackListar(){
		return new ArrayList<DevolucionResponse>();
	}	

	@Override
	public DevolucionResponse viewById(Long id) {
		try {
			Optional<Extraccion> optionalExtraccion = daoExtracciones.findByMercanciaId(id);
			Optional<Devolucion> optionalDevolucion = devoluciones.findByExtraccion(optionalExtraccion.get());
			if (optionalDevolucion.isPresent() && optionalExtraccion.isPresent()) {
				Devolucion devolucion = optionalDevolucion.get();
				Extraccion extraccion = devolucion.getExtraccion();
				Mercancia mercancia = get(extraccion.getMercanciaId()).block();
				ExtraccionResponse data = mapper.map(extraccion, ExtraccionResponse.class);	
				data.setMercancia(mercancia);
				DevolucionResponse res = mapper.map(devolucion, DevolucionResponse.class);
				res.setExtraccion(data);
				return res;
			} else {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND,"La devolución solicitada no se encuentra");
			}
		} catch (ResponseStatusException e) {
			throw new ResponseStatusException(e.getStatus(), e.getMessage());
		}
	}

	@Override	
	public DevolucionResponse updateById(OperacionRequest request, Long id) {
		try {
			Optional<Extraccion> optionalExtraccion = daoExtracciones.findByMercanciaId(id);
			Optional<Devolucion> optionalDevolucion = devoluciones.findByExtraccion(optionalExtraccion.get());
			if (optionalExtraccion.isPresent() && optionalDevolucion.isPresent() && comprobar(optionalExtraccion)) {
				Devolucion actualizada = optionalDevolucion.get();
				Devolucion data = mapper.map(request, Devolucion.class);
				actualizada.setFecha(data.getFecha());				
				devoluciones.saveAndFlush(actualizada);								
				ExtraccionResponse extraccion = mapper.map(actualizada.getExtraccion(), ExtraccionResponse.class);
				Mercancia mercancia = get(actualizada.getExtraccion().getMercanciaId()).block();			
				extraccion.setMercancia(mercancia);
				DevolucionResponse res = mapper.map(actualizada, DevolucionResponse.class);				
				res.setExtraccion(extraccion);
				return res;
			}else {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND,"El operacion a actualizar no existe");
			}
		} catch (ResponseStatusException e) {
			throw new ResponseStatusException(e.getStatus(), e.getMessage());
		}
	}
	
	@Override	
	public DevolucionResponse deleteById(Long id) {
		try {
			Optional<Extraccion> optionalExtraccion = daoExtracciones.findByMercanciaId(id);
			Optional<Devolucion> optionalDevolucion = devoluciones.findByExtraccion(optionalExtraccion.get());			
			if (optionalDevolucion.isPresent() && optionalExtraccion.isPresent() && comprobar(optionalExtraccion)) {
				Extraccion extraccion = optionalExtraccion.get();
				if (extraccion.isActivo()==false) {
					ExtraccionResponse resExtraccion = mapper.map(extraccion, ExtraccionResponse.class);
					DevolucionResponse res = mapper.map(optionalDevolucion.get(), DevolucionResponse.class);
					Mercancia mercancia = revertir(extraccion.getMercanciaId()).block();				
					resExtraccion.setMercancia(mercancia);
					res.setExtraccion(resExtraccion);
					devoluciones.delete(optionalDevolucion.get());
					return res;					
				} else {
					throw new ResponseStatusException(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS,"La operacion no esta permitida");
				}

			} else {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"La operación a revertir no existe");
			}
		} catch (ResponseStatusException e) {
			throw new ResponseStatusException(e.getStatus(), e.getMessage());
		}
	}

	private Boolean comprobar(Optional<Extraccion> optional) {
		return get(optional.get().getMercanciaId()).block().getId()!=null;
	}

	@Override
	public DevolucionResponse activateById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}
}
