package com.geasp.micro.operaciones.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreakerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import com.geasp.micro.operaciones.models.Extraccion;
import com.geasp.micro.operaciones.models.Mercancia;
import com.geasp.micro.operaciones.repositories.ExtraccionRepository;
import com.geasp.micro.operaciones.requests.ExtraccionRequest;
import com.geasp.micro.operaciones.responses.ExtraccionResponse;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

import org.dozer.Mapper;
import org.keycloak.KeycloakSecurityContext;

@Service
public class ExtraccionesService implements IOperacionesService<ExtraccionResponse, ExtraccionRequest>{
	
	@Autowired
	private ExtraccionRepository extracciones;
	@Autowired
	private Mapper mapper;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private KeycloakSecurityContext securityContext;
	
	private ResponseEntity<Mercancia> get(Long id) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.setBearerAuth(securityContext.getTokenString());
		
		HttpEntity<String> entity = new HttpEntity<>("body",headers);
		return restTemplate.exchange(
				"http://MERCANCIAS/operaciones/"+id, 
				HttpMethod.GET,
				entity,
				Mercancia.class
			);
	}
	
	private ResponseEntity<Mercancia> extract(Long id){
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.setBearerAuth(securityContext.getTokenString());
		HttpEntity<String> entity = new HttpEntity<>("body",headers);
		
		return restTemplate.exchange(
				"http://MERCANCIAS/operaciones/extraer/"+id, 
				HttpMethod.POST, 
				entity, 
				Mercancia.class
			);
	}
	
	private ResponseEntity<Mercancia> revert(Extraccion extraccion){
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.setBearerAuth(securityContext.getTokenString());
		HttpEntity<String> entity = new HttpEntity<>("body",headers);
		
		return restTemplate.exchange(
				"http://MERCANCIAS/operaciones/revertir/"+extraccion.getMercanciaId(), 
				HttpMethod.POST, 
				entity, 
				Mercancia.class
			);
	}
	
	@HystrixCommand(fallbackMethod = "getFallbackCreate", commandProperties = {
			@HystrixProperty(name="execution.isolation.strategy",value="SEMAPHORE"),
//			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
			@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
			@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
			@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000")
	})
	@Override
	public ExtraccionResponse registrar(ExtraccionRequest entity, Long id) {
		try {
			Mercancia mercancia = extract(id).getBody();
			if (mercancia.getId()==id) {
				Extraccion extraccion = mapper.map(entity, Extraccion.class);
				extraccion.setMercanciaId(mercancia.getId());
				extraccion.setTipoMercancia(mercancia.getTipo_mercancia());
				extracciones.saveAndFlush(extraccion);
				ExtraccionResponse res = mapper.map(extraccion, ExtraccionResponse.class);
				res.setMercancia(mercancia);
				res.setMessage("Registro realizado con éxito.");
				return res;
			}else {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND,"El contenedor a extraer no existe");
			}			
		} catch (ResponseStatusException e) { 
			  throw new ResponseStatusException(e.getStatus(), e.getMessage()); 
		}
	}
	
	@SuppressWarnings("unused")
	private ExtraccionResponse getFallbackCreate(ExtraccionRequest request, Long id){
		return new ExtraccionResponse("Ha ocurrido al registrar la operación.");
	}	
	
	@Override
	@HystrixCommand(fallbackMethod = "getFallbackView", commandProperties = {
			@HystrixProperty(name="execution.isolation.strategy",value="SEMAPHORE"),
//			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
			@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
			@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
			@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000")
	})
	public ExtraccionResponse viewById(Long id) {
		try {
			if (extracciones.findByMercanciaId(id).isPresent()) {
				Extraccion extraccion = extracciones.findByMercanciaId(id).get();
				Mercancia mercancia = get(extraccion.getMercanciaId()).getBody();
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
	@HystrixCommand(fallbackMethod = "getFallbackListar", commandProperties = {
			@HystrixProperty(name="execution.isolation.strategy",value="SEMAPHORE"),
//			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
			@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
			@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
			@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000")
	})	
	public List<ExtraccionResponse> listar() {
		try {				
			List<ExtraccionResponse> response = new ArrayList<ExtraccionResponse>();
			List<Extraccion> lista = extracciones.findAll();
			
			lista.stream().forEach(index-> {
				Mercancia mercancia = get(index.getMercanciaId()).getBody();
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
	@HystrixCommand(fallbackMethod = "getFallbackUpdate", commandProperties = {
			@HystrixProperty(name="execution.isolation.strategy",value="SEMAPHORE"),
//			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
			@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
			@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
			@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000")
	})	
	public ExtraccionResponse updateById(ExtraccionRequest request, Long id) {
		try {
			Optional<Extraccion> extraccionOptional= extracciones.findByMercanciaId(id);
			if (extraccionOptional.isPresent() && comprobar(extraccionOptional)) {
				Extraccion extraccionActualizada = extraccionOptional.get();
				Extraccion data = mapper.map(request, Extraccion.class);
				extraccionActualizada.setFecha(data.getFecha());				
				extracciones.saveAndFlush(extraccionActualizada);
				ExtraccionResponse res = mapper.map(extraccionActualizada, ExtraccionResponse.class);
				Mercancia mercancia = get(extraccionActualizada.getMercanciaId()).getBody();
				res.setMessage("Operación actualizada con éxito.");
				res.setMercancia(mercancia);
				return res;
			}else {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND,"El operacion a actualizar no existe");
			}
		} catch (ResponseStatusException e) {
			throw new ResponseStatusException(e.getStatus(), e.getMessage());
		}
	}

	@SuppressWarnings("unused")
	private ExtraccionResponse getFallbackUpdate(ExtraccionRequest request, Long id){
		return new ExtraccionResponse("Ha ocurrido un error al actualizar.");
	}	
	
	@Override
	@HystrixCommand(fallbackMethod = "getFallbackView", commandProperties = {
			@HystrixProperty(name="execution.isolation.strategy",value="SEMAPHORE"),
//			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
			@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
			@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
			@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000")
	})	
	public ExtraccionResponse deleteById(Long id) {
		try {
			Optional<Extraccion> extraccionOptional= extracciones.findByMercanciaId(id);			
			if (extraccionOptional.isPresent() && comprobar(extraccionOptional)) {
				Extraccion extraccion = extraccionOptional.get();
				if (extraccionOptional.get().isActivo()==false) {
					Mercancia mercancia = revert(extraccion).getBody();
					ExtraccionResponse res = mapper.map(extraccion, ExtraccionResponse.class);
					res.setMercancia(mercancia);
					res.setMessage("Operación revertida con éxito.");
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
		return get(extraccionOptional.get().getMercanciaId()).getBody().getId()!=null;
	}

	@Override
	@HystrixCommand(fallbackMethod = "getFallbackView", commandProperties = {
			@HystrixProperty(name="execution.isolation.strategy",value="SEMAPHORE"),
//			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
			@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
			@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
			@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000")
	})	
	public ExtraccionResponse activateById(Long id) {
		try {
			Optional<Extraccion> extraccionOptional= extracciones.findByMercanciaId(id);
			if (extraccionOptional.isPresent()) {
				Extraccion extraccion = extraccionOptional.get();
				extraccion.setActivo(!extraccion.isActivo());				
				extracciones.saveAndFlush(extraccion);
				ExtraccionResponse res = mapper.map(extraccion, ExtraccionResponse.class);
				Mercancia mercancia = get(extraccion.getMercanciaId()).getBody();
				res.setMessage("Operación actualizada con éxito.");
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
