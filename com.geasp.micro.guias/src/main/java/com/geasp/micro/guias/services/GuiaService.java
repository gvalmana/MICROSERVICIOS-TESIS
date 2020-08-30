package com.geasp.micro.guias.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.dozer.Mapper;
import org.keycloak.KeycloakSecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import com.geasp.micro.guias.Calculos;
import com.geasp.micro.guias.models.Cliente;
import com.geasp.micro.guias.models.EstadoMercancias;
import com.geasp.micro.guias.models.Guia;
import com.geasp.micro.guias.repositories.GuiaRepository;
import com.geasp.micro.guias.requests.GuiaRequest;
import com.geasp.micro.guias.responses.GuiaResponse;
import com.geasp.micro.guias.responses.ResumenPendientes;

import reactor.core.publisher.Mono;

@Service
public class GuiaService implements IGuiaService<GuiaResponse, GuiaRequest>{
	
	@Autowired
	private WebClient.Builder webClientBuilder;
	
	@Autowired
	private KeycloakSecurityContext securityContext;
	
	@Autowired
	private Mapper mapper;
	
	@Autowired
	private GuiaRepository dao;
	
	@Autowired
	private Calculos calculos;	
	
	@Override
	public GuiaResponse save(GuiaRequest entity) {		
		Guia guia = dao.saveAndFlush(mapper.map(entity, Guia.class));
		GuiaResponse response = mapper.map(guia, GuiaResponse.class);
		mappearDatos(guia, response);
		return response;
	}

	@Override
	public List<GuiaResponse> listar() {
		try {
			List<Guia> guias = dao.findAll();
			if (guias.size()>0) {
				List<GuiaResponse> response = llenarLista(guias);
				return response.stream().collect(Collectors.toList());
			} else {
				throw new ResponseStatusException(HttpStatus.NO_CONTENT,"Lista de guías aereas no encontrados");
			}
		} catch (ResponseStatusException e) {
			throw new ResponseStatusException(e.getStatus(), e.getMessage());
		}
	}

	@Override
	public List<GuiaResponse> listarPorEstado(EstadoMercancias estado) {
		try {
			List<Guia> guias = dao.findByEstado(estado);
			if (guias.size()>0) {
				return llenarLista(guias).stream().collect(Collectors.toList());
			} else {
				throw new ResponseStatusException(HttpStatus.NO_CONTENT,"Lista de guías aereas no encontrados");
			}
		} catch (ResponseStatusException e) {
			throw new ResponseStatusException(e.getStatus(), e.getMessage());
		}
	}
	
	@Override
	public GuiaResponse viewById(Long id) {
		try {
			Optional<Guia> guia = dao.findById(id);
			if (guia.isPresent()) {
				GuiaResponse response = mapper.map(guia.get(), GuiaResponse.class);
				mappearDatos(guia.get(), response);
				return response;
			} else {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Guía no encontrada");
			}
		} catch (ResponseStatusException e) {
			throw new ResponseStatusException(e.getStatus(), e.getMessage());
		}
	}

	@Override
	public GuiaResponse updateById(GuiaRequest request, Long id) {
		try {
			Optional<Guia> optional = dao.findById(id);
			if (optional.isPresent()) {
				Guia actualizada = optional.get();
				Guia data = mapper.map(request, Guia.class);
				mappearGuia(actualizada, data);
				dao.saveAndFlush(actualizada);
				GuiaResponse response = mapper.map(actualizada, GuiaResponse.class);
				return response;
			} else {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND,"La guia a actualizar no existe");
			}
		} catch (ResponseStatusException e) {
			throw new ResponseStatusException(e.getStatus(), e.getMessage());
		}
	}

	@Override
	public GuiaResponse desactivateById(Long id) {
		try {
			Optional<Guia> optional = dao.findById(id);
			if (optional.isPresent()) {
				Guia guia = optional.get();
				if (guia.getEstado()==EstadoMercancias.DISABLE) {
					guia.setEstado(EstadoMercancias.LISTO_PARA_EXTRAER);
				} else {
					guia.setEstado(EstadoMercancias.DISABLE);
				}
				dao.saveAndFlush(guia);
				GuiaResponse response = mapper.map(guia, GuiaResponse.class);
				mappearDatos(optional.get(), response);
				return response;
			} else {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"La guía a eliminar no existe");
			}
		} catch (ResponseStatusException e) {
			throw new ResponseStatusException(e.getStatus(), e.getMessage());
		}
	}
	
	private void mappearDatos(Guia data, GuiaResponse index) {
		index.setEstado(calculos.mappearEstadoToText(data));
		index.setDias_declarada(calculos.calcularDiasDeclarada(data));
		index.setDias_entregada(calculos.calcularDiasEntregada(data));
		index.setCant_dias_documentos_entregados(calculos.calcularDiasDocumentos(data));
		index.setEstadia(calculos.determinarEstadia(index));
	}
	
	private void mappearGuia(Guia actualizada, Guia data){
		actualizada.setFecha_habilitacion(data.getFecha_habilitacion());
		actualizada.setFecha_documentos(data.getFecha_documentos());
		actualizada.setSituacion(data.getSituacion());
		actualizada.setObservaciones(data.getObservaciones());
		actualizada.setDescripcion(data.getDescripcion());
		actualizada.setCliente(data.getCliente());
		actualizada.setImportadora(data.getImportadora());
		actualizada.setEstado(data.getEstado());		
		actualizada.setDm(data.getDm());
		actualizada.setCantidad_de_bultos(data.getCantidad_de_bultos());
		actualizada.setPeso(data.getPeso());
		actualizada.setFecha_entrega(data.getFecha_entrega());
		actualizada.setFecha_arribo(data.getFecha_arribo());
	}

	private List<GuiaResponse> llenarLista(List<Guia> lista){		
		List<GuiaResponse> response = new ArrayList<GuiaResponse>();
		lista.stream().forEach(x -> {
			GuiaResponse index = mapper.map(x, GuiaResponse.class);
			mappearDatos(x, index);
			response.add(index);
		});	
		return response;
	}

	@Override
	public List<GuiaResponse> listarPorFechaArribo(LocalDate date) {
		try {
			List<Guia> guias = dao.findAll().stream().filter(index->{
				return index.getFecha_arribo().equals(date);
			}).collect(Collectors.toList());
			if (guias.size()>0) {
				List<GuiaResponse> response = llenarLista(guias);
				return response.stream().collect(Collectors.toList());
			} else {
				throw new ResponseStatusException(HttpStatus.NO_CONTENT,"Lista de guías aereas no encontrados");
			}
		} catch (ResponseStatusException e) {
			throw new ResponseStatusException(e.getStatus(), e.getMessage());
		}
	}
	private Mono<List<Cliente>> buscarTodasLasEmpresas() {
		return webClientBuilder.build().get()
				.uri("http://EMPRESAS/v1/clientes/")
				.headers(header->{
					header.setBearerAuth(securityContext.getTokenString());
					header.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
				})
				.retrieve()
				.bodyToMono(new ParameterizedTypeReference<List<Cliente>>() {});
	}
	
	public List<ResumenPendientes> listarPendientes(){
		List<ResumenPendientes> res = new ArrayList<ResumenPendientes>();
		List<Cliente> clientes = buscarTodasLasEmpresas().block();
		List<Guia> guias = dao.findByEstado(EstadoMercancias.LISTO_PARA_EXTRAER);

		clientes.stream().forEach(index->{
			int total=0;
			int porHabilitar=0;
			int sinEntregar=0;
			int sinDescargar=0;
			int paraExtraer=0;
			for (Guia guia : guias) {
				if (guia.getCliente().equals(index.getNombre())) {
					total++;
					if (guia.getFecha_habilitacion()==null) {
						porHabilitar++;
					}else if (guia.getFecha_documentos()==null) {
						sinEntregar++;
					}else if(guia.getFecha_arribo()==null) {
						sinDescargar++;
					}
					paraExtraer = total-porHabilitar-sinEntregar-sinDescargar;
				}				
			}
			if (total>0) {
				res.add(new ResumenPendientes(index.getNombre(), total, paraExtraer, porHabilitar, sinEntregar, sinDescargar));
			}
		});
		
		return res;
	}	
}
