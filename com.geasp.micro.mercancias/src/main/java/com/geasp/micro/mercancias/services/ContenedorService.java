package com.geasp.micro.mercancias.services;

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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import com.geasp.micro.mercancias.conf.Calculos;
import com.geasp.micro.mercancias.models.CantidadEmpresa;
import com.geasp.micro.mercancias.models.Cliente;
import com.geasp.micro.mercancias.models.Contenedor;
import com.geasp.micro.mercancias.models.EstadoMercancias;
import com.geasp.micro.mercancias.repositories.ContenedorRepository;
import com.geasp.micro.mercancias.requests.ContenedorRequest;
import com.geasp.micro.mercancias.responses.ContenedorResponse;
import com.geasp.micro.mercancias.responses.ResumenPendientes;
import reactor.core.publisher.Mono;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Service
public class ContenedorService implements IMercanciaService<ContenedorResponse,ContenedorRequest> {
	
	@Autowired
	private ContenedorRepository dao;
	
	@Autowired
	private Mapper mapper;
	
	@Autowired
	private Calculos calculos;
	
	@Autowired
	private KeycloakSecurityContext securityContext;
		
	@Autowired
	private WebClient.Builder webClientBuilder;
	
	@Override
	public ContenedorResponse save(ContenedorRequest entity) {
		Contenedor contenedor = dao.saveAndFlush(mapper.map(entity, Contenedor.class));
		ContenedorResponse response = mapper.map(contenedor, ContenedorResponse.class);
		mappearDatos(contenedor, response);
		return response;
	}
	
	@Override
	public List<ContenedorResponse> listar(Integer pageNo, Integer pageSize, String sortBy) {
		try {
			Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
			Page<Contenedor> contenedores = dao.findAll(paging);
			if (contenedores.hasContent()) {
				return llenarLista(contenedores.getContent().stream().collect(Collectors.toList()));				
			} else {
				throw new ResponseStatusException(HttpStatus.NO_CONTENT,"Lista de contenedores no encontrados");
			}			
		} catch (ResponseStatusException e) {
			throw new ResponseStatusException(e.getStatus(), e.getMessage());
		}
	}

	@Override
	public List<ContenedorResponse> listarPorEstado(EstadoMercancias estado, Integer pageNo, Integer pageSize, String sortBy) {		
		try {
			Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
			Page<Contenedor> contenedores = dao.findByEstado(estado, paging);
			if (contenedores.hasContent()) {
				return llenarLista(contenedores.getContent().stream().collect(Collectors.toList()));
			} else {
				throw new ResponseStatusException(HttpStatus.NO_CONTENT,"Lista de contenedores no encontrados");
			}
		} catch (ResponseStatusException e) {
			throw new ResponseStatusException(e.getStatus(), e.getMessage());
		}
	}

	@Override
	public ContenedorResponse viewById(Long id) {
		try {
			Optional<Contenedor> contenedor = dao.findById(id);
			if (contenedor.isPresent()) {				
				ContenedorResponse response = new ContenedorResponse();
				response = mapper.map(contenedor.get(), ContenedorResponse.class);
				mappearDatos(contenedor.get(), response);
				return response;				
			} else {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Contenedor no encontrado");
			}
		} catch (ResponseStatusException e) {
			throw new ResponseStatusException(e.getStatus(), e.getMessage());
		}
	}
	
	@Override
	public ContenedorResponse updateById(ContenedorRequest request, Long id) {
		try {
			Optional<Contenedor> contenedorOptional = dao.findById(id);
			if (contenedorOptional.isPresent()) {
				Contenedor actualizado = contenedorOptional.get();
				Contenedor data = mapper.map(request, Contenedor.class);
				mappearContenedor(actualizado, data);
				dao.saveAndFlush(actualizado);
				ContenedorResponse response = mapper.map(actualizado, ContenedorResponse.class);
				mappearDatos(actualizado, response);
				return response;
			} else {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND,"El contenedor a actualizar no existe");
			}
		} catch (ResponseStatusException e) {
			throw new ResponseStatusException(e.getStatus(), e.getMessage());
		}
	}
	
	@Override
	public ContenedorResponse desactivateById(Long id) {
		try {
			Optional<Contenedor> optional = dao.findById(id);
			if (optional.isPresent()) {				
				Contenedor contenedor = optional.get();
				if (contenedor.getEstado()==EstadoMercancias.DISABLE) {
					contenedor.setEstado(EstadoMercancias.LISTO_PARA_EXTRAER);
				} else {
					contenedor.setEstado(EstadoMercancias.DISABLE);				
				}								
				dao.saveAndFlush(contenedor);
				ContenedorResponse response = mapper.map(optional.get(), ContenedorResponse.class);
				mappearDatos(contenedor, response);
				return response;
			} else {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"El contenedor a eliminar no existe");
			}
		} catch (ResponseStatusException e) {
			throw new ResponseStatusException(e.getStatus(), e.getMessage());
		}
	}
	
	private void mappearContenedor(Contenedor actualizado, Contenedor data) {
		actualizado.setFecha_habilitacion(data.getFecha_habilitacion());
		actualizado.setFecha_documentos(data.getFecha_documentos());
		actualizado.setSituacion(data.getSituacion());
		actualizado.setObservaciones(data.getObservaciones());
		actualizado.setDescripcion(data.getDescripcion());
		actualizado.setCliente(data.getCliente());
		actualizado.setImportadora(data.getImportadora());
		actualizado.setEstado(data.getEstado());		
		actualizado.setCodigo(data.getCodigo());
		actualizado.setManifiesto(data.getManifiesto());
		actualizado.setBl(data.getBl());
		actualizado.setFecha_arribo(data.getFecha_arribo());
		actualizado.setFecha_planificacion(data.getFecha_planificacion());
		actualizado.setTamano(data.getTamano());
		actualizado.setPuerto(data.getPuerto());
	}

	private void mappearDatos(Contenedor contenedor, ContenedorResponse index) {
		index.setEdad(calculos.calcularDiasEstadia(contenedor));
		index.setCant_dias_documentos_entregados(calculos.calcularDiasDocumentos(contenedor));
		index.setEstado(calculos.mappearEstadoToText(contenedor));
		index.setEstadia(calculos.determinarEstadia(index));
	}

	private List<ContenedorResponse> llenarLista(List<Contenedor> lista){
		List<ContenedorResponse> response = new ArrayList<ContenedorResponse>();
		lista.stream().forEach(x ->{
			ContenedorResponse index = mapper.map(x, ContenedorResponse.class);
			mappearDatos(x, index);
			response.add(index);
		});	
		return response;
	}

	@Override
	public List<ContenedorResponse> listarPorFechaArribo(LocalDate date) {
		// TODO Auto-generated method stub
		try {
			List<Contenedor> contenedores = dao.findAll().stream().filter(index->{
				return index.getFecha_arribo().equals(date);
			}).collect(Collectors.toList());
			if (contenedores.size()>0) {
				return llenarLista(contenedores).stream().collect(Collectors.toList());				
			} else {
				throw new ResponseStatusException(HttpStatus.NO_CONTENT,"Lista de contenedores no encontrados");
			}			
		} catch (ResponseStatusException e) {
			throw new ResponseStatusException(e.getStatus(), e.getMessage());
		}
	}
	
	public List<ResumenPendientes> listarPendientes(){
		List<ResumenPendientes> res = new ArrayList<ResumenPendientes>();
		List<Cliente> clientes = buscarTodasLasEmpresas().block();
		List<Contenedor> contenedores = dao.findByEstado(EstadoMercancias.LISTO_PARA_EXTRAER);

		clientes.stream().forEach(index->{
			int total=0;
			int porHabilitar=0;
			int sinEntregar=0;
			int sinDescargar=0;
			int paraExtraer=0;
			for (Contenedor contenedor : contenedores) {
				if (contenedor.getCliente().equals(index.getNombre())) {
					total++;
					if (contenedor.getFecha_habilitacion()==null) {
						porHabilitar++;
					}else if (contenedor.getFecha_documentos()==null) {
						sinEntregar++;
					}else if(contenedor.getFecha_arribo()==null) {
						sinDescargar++;
					}
					paraExtraer = total-porHabilitar-sinEntregar-sinDescargar;
				}				
			}
			if (total>0) {
				res.add(new ResumenPendientes(index.getNombre(), total, paraExtraer, porHabilitar, sinEntregar, sinDescargar));
			}
		});
		
		return res.stream().collect(Collectors.toList());
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
	
	private List<CantidadEmpresa> listarPorEmpresas(List<Contenedor> data){
		List<Cliente> clientes = buscarTodasLasEmpresas().block();
		List<CantidadEmpresa> resultado = new ArrayList<CantidadEmpresa>();
		//RECORRE TODA LA LISTA DE CLIENTES
		clientes.stream().forEach(index -> {
			int cantidad = 0;
			//RECORRE LA LISTA DE CONTENEDORES PARA EXTRAER
			for (Contenedor item : data) {
				//SI ES EL MISMO CLIENTE AGREGA UN CONTADOR
				if (index.getNombre().equals(item.getCliente())) {
					cantidad++;
				}
			}
			if (cantidad>0) {
				resultado.add(new CantidadEmpresa(index.getNombre(), cantidad));
			}
		});		
		return resultado.stream().collect(Collectors.toList());
	}
	
	public List<CantidadEmpresa> listarContenedoresDevolver(){
		List<Contenedor> lista = dao.findByEstado(EstadoMercancias.EXTRAIDA);
		List<CantidadEmpresa> resumenOperaciones = listarPorEmpresas(lista);
		return resumenOperaciones.stream().collect(Collectors.toList());
	}	
}
