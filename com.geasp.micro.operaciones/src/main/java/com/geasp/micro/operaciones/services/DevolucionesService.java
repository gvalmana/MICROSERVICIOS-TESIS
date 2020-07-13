package com.geasp.micro.operaciones.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.dozer.Mapper;
import org.keycloak.KeycloakSecurityContext;
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
//import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import com.geasp.micro.operaciones.models.Devolucion;
import com.geasp.micro.operaciones.models.Extraccion;
import com.geasp.micro.operaciones.models.Mercancia;
import com.geasp.micro.operaciones.repositories.DevolucionRepository;
import com.geasp.micro.operaciones.repositories.ExtraccionRepository;
import com.geasp.micro.operaciones.requests.DevolucionRequest;
import com.geasp.micro.operaciones.responses.DevolucionResponse;
import com.geasp.micro.operaciones.responses.ExtraccionResponse;
//import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
//import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
//
//import reactor.core.publisher.Mono;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@Service
public class DevolucionesService implements IOperacionesService<DevolucionResponse, DevolucionRequest> {
	
	@Autowired
	private DevolucionRepository devoluciones;
	
	@Autowired
	private ExtraccionRepository daoExtracciones;
	
	@Autowired
	private KeycloakSecurityContext securityContext;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private Mapper mapper;

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

//	private Mono<Mercancia> getMercancia(Extraccion extraccion) {
//		return webClientBuilder.build().get()
//				.uri("http://MICROSERVICIO-MERCANCIAS/mercancias/"+extraccion.getMercanciaId())
//				.headers(header->header.setBearerAuth(securityContext.getTokenString()))
//				.retrieve()
//				.bodyToMono(Mercancia.class)
//				.transform(it -> cbFactory.create("slow").run(it, throwable -> Mono.just(new Mercancia())));
//	}
	
	private ResponseEntity<Mercancia> devolver(Long id){
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.setBearerAuth(securityContext.getTokenString());
		HttpEntity<String> entity = new HttpEntity<>("body",headers);
		
		return restTemplate.exchange(
				"http://MERCANCIAS/operaciones/devolver/"+id, 
				HttpMethod.POST, 
				entity, 
				Mercancia.class
			);
	}	
//	private Mercancia postExtraer(Extraccion extraccion) {
//		Mercancia mercancia = webClientBuilder.build()
//				.post()
//				.uri("http://MICROSERVICIO-MERCANCIAS/mercancias/devolver/"+extraccion.getMercanciaId())
//				.headers(header->header.setBearerAuth(securityContext.getTokenString()))
//				.retrieve()
//				.bodyToMono(Mercancia.class)
//				.block();
//		return mercancia;
//	}	

	private ResponseEntity<Mercancia> revertir(Long id){
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.setBearerAuth(securityContext.getTokenString());
		HttpEntity<String> entity = new HttpEntity<>("body",headers);
		
		return restTemplate.exchange(
				"http://MERCANCIAS/operaciones/revertir/"+id, 
				HttpMethod.POST, 
				entity, 
				Mercancia.class
			);
	}
	
//	private Mercancia postRevert(Extraccion extraccion) {
//		Mercancia mercancia = webClientBuilder.build()
//				.post()
//				.uri("http://MICROSERVICIO-MERCANCIAS/mercancias/revertir/"+extraccion.getMercanciaId())
//				.headers(header->header.setBearerAuth(securityContext.getTokenString()))
//				.retrieve()
//				.bodyToMono(Mercancia.class)
//				.block();
//		return mercancia;
//	}	
	
	@Override
	@HystrixCommand(fallbackMethod = "getFallbackCreate", commandProperties = {
			@HystrixProperty(name="execution.isolation.strategy",value="SEMAPHORE"),
//			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
			@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
			@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
			@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000")
	})	
	public DevolucionResponse registrar(DevolucionRequest entity, Long id) {
		try {
			Optional<Extraccion> optionalExtraccion = daoExtracciones.findByMercanciaId(id);			
			if (optionalExtraccion.isPresent() && comprobar(optionalExtraccion)) {
				Extraccion extraccion = optionalExtraccion.get();				
				Devolucion devolucion = mapper.map(entity, Devolucion.class);
				devolucion.setExtraccion(extraccion);
				devoluciones.saveAndFlush(devolucion);
				
				Mercancia mercancia = devolver(extraccion.getMercanciaId()).getBody();
				
				ExtraccionResponse resExtraccion = mapper.map(extraccion, ExtraccionResponse.class);
				resExtraccion.setMercancia(mercancia);
				DevolucionResponse res = new DevolucionResponse();
				res = mapper.map(devolucion, DevolucionResponse.class);
				res.setExtraccion(resExtraccion);
				res.setMessage("Registro realizado con éxito.");
				return res;				
			} else {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND,"La operación solicitada no es posible");
			}
		} catch (ResponseStatusException e) {
			throw new ResponseStatusException(e.getStatus(), e.getMessage());
		}
	}
	@SuppressWarnings("unused")
	private DevolucionResponse getFallbackCreate(DevolucionRequest request, Long id){
		return new DevolucionResponse("Ha ocurrido al registrar la operación.");
	}	

	@Override
	@HystrixCommand(fallbackMethod = "getFallbackListar", commandProperties = {
			@HystrixProperty(name="execution.isolation.strategy",value="SEMAPHORE"),
//			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
			@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
			@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
			@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000")
	})
	public List<DevolucionResponse> listar() {
		try {
			List<DevolucionResponse> response = new ArrayList<DevolucionResponse>();
			List<Devolucion> lista = devoluciones.findAll();
			lista.stream().forEach(index -> {
				Extraccion extraccion = index.getExtraccion();
				Mercancia mercancia = get(extraccion.getMercanciaId()).getBody();
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
	@HystrixCommand(fallbackMethod = "getFallbackView", commandProperties = {
			@HystrixProperty(name="execution.isolation.strategy",value="SEMAPHORE"),
//			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
			@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
			@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
			@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000")
	})
	public DevolucionResponse viewById(Long id) {
		try {
			Optional<Extraccion> optionalExtraccion = daoExtracciones.findByMercanciaId(id);
			Optional<Devolucion> optionalDevolucion = devoluciones.findByExtraccion(optionalExtraccion.get());
			if (optionalDevolucion.isPresent() && optionalExtraccion.isPresent()) {
				Devolucion devolucion = optionalDevolucion.get();
				Extraccion extraccion = devolucion.getExtraccion();
				Mercancia mercancia = get(extraccion.getMercanciaId()).getBody();
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
	@SuppressWarnings("unused")
	private DevolucionResponse getFallbackView(Long id){
		return new DevolucionResponse("Error al obtener la operación solicitada, inténtelo mas tarde.");
	}	

	@Override
	@HystrixCommand(fallbackMethod = "getFallbackUpdate", commandProperties = {
			@HystrixProperty(name="execution.isolation.strategy",value="SEMAPHORE"),
//			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
			@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
			@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
			@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000")
	})	
	public DevolucionResponse updateById(DevolucionRequest request, Long id) {
		try {
			Optional<Extraccion> optionalExtraccion = daoExtracciones.findByMercanciaId(id);
			Optional<Devolucion> optionalDevolucion = devoluciones.findByExtraccion(optionalExtraccion.get());
			if (optionalExtraccion.isPresent() && optionalDevolucion.isPresent() && comprobar(optionalExtraccion)) {
				Devolucion actualizada = optionalDevolucion.get();
				Devolucion data = mapper.map(request, Devolucion.class);
				actualizada.setFecha(data.getFecha());				
				devoluciones.saveAndFlush(actualizada);								
				ExtraccionResponse extraccion = mapper.map(actualizada.getExtraccion(), ExtraccionResponse.class);
				Mercancia mercancia = get(actualizada.getExtraccion().getMercanciaId()).getBody();			
				extraccion.setMercancia(mercancia);
				DevolucionResponse res = mapper.map(actualizada, DevolucionResponse.class);				
				res.setExtraccion(extraccion);
				res.setMessage("Operación actualizada con éxito.");
				return res;
			}else {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND,"El operacion a actualizar no existe");
			}
		} catch (ResponseStatusException e) {
			throw new ResponseStatusException(e.getStatus(), e.getMessage());
		}
	}

	@SuppressWarnings("unused")
	private DevolucionResponse getFallbackUpdate(DevolucionRequest request, Long id){
		return new DevolucionResponse("Ha ocurrido un error al actualizar.");
	}
	
	@Override
	@HystrixCommand(fallbackMethod = "getFallbackView", commandProperties = {
			@HystrixProperty(name="execution.isolation.strategy",value="SEMAPHORE"),
//			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
			@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
			@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
			@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000")
	})		
	public DevolucionResponse deleteById(Long id) {
		try {
			Optional<Extraccion> optionalExtraccion = daoExtracciones.findByMercanciaId(id);
			Optional<Devolucion> optionalDevolucion = devoluciones.findByExtraccion(optionalExtraccion.get());			
			if (optionalDevolucion.isPresent() && optionalExtraccion.isPresent() && comprobar(optionalExtraccion)) {
				Extraccion extraccion = optionalExtraccion.get();
				if (extraccion.isActivo()==false) {
					ExtraccionResponse resExtraccion = mapper.map(extraccion, ExtraccionResponse.class);
					DevolucionResponse res = mapper.map(optionalDevolucion.get(), DevolucionResponse.class);
					Mercancia mercancia = revertir(extraccion.getMercanciaId()).getBody();				
					resExtraccion.setMercancia(mercancia);
					res.setExtraccion(resExtraccion);
					res.setMessage("Operación revertida con éxito.");
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
		return get(optional.get().getMercanciaId()).getBody().getId()!=null;
	}

	@Override
	public DevolucionResponse activateById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}
}
