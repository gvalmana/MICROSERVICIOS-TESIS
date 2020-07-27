package com.geasp.micro.operaciones.resources;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.geasp.micro.operaciones.requests.OperacionRequest;
import com.geasp.micro.operaciones.responses.DevolucionResponse;
import com.geasp.micro.operaciones.services.IOperacionesService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
@RequestMapping(value = "/devolucion")
@CrossOrigin(
		origins = "*", 
		methods = {
				RequestMethod.GET, 
				RequestMethod.POST, 
				RequestMethod.PUT, 
				RequestMethod.DELETE, 
				RequestMethod.HEAD, 
				RequestMethod.OPTIONS}, 
		allowedHeaders = "*", 
		allowCredentials = "true" )
public class DevolucionesController implements IControllers<DevolucionResponse, OperacionRequest>{

	@Autowired
	IOperacionesService<DevolucionResponse, OperacionRequest> service;
	private static final String MAIN_SERVICE = "mainService";
	
	@Override
	@PostMapping(value = "/{id}")
	public ResponseEntity<DevolucionResponse> Save(@RequestBody @Valid OperacionRequest entity, @PathVariable("id") Long id) {
		return ResponseEntity.ok(service.registrar(entity, id));
	}
	@CircuitBreaker(name = MAIN_SERVICE, fallbackMethod = "saveCallback")
	public ResponseEntity<DevolucionResponse> saveCallback(OperacionRequest entity, Long id, Exception e) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("message", "Ha ocurrido un error de comunicación entre servidores. Por favor comunique a soporte técnico.");		
		return new ResponseEntity<DevolucionResponse>(new DevolucionResponse(),headers, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@Override
	@GetMapping
	@CircuitBreaker(name = MAIN_SERVICE, fallbackMethod = "listarCallback")
	public ResponseEntity<List<DevolucionResponse>> getAll() {
		return ResponseEntity.ok(service.listar());
	}	
	public ResponseEntity<List<DevolucionResponse>> listarCallback(Exception e) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("message", "Ha ocurrido un error de comunicación entre servidores. Por favor comunique a soporte técnico.");		
		return new ResponseEntity<List<DevolucionResponse>>(new ArrayList<DevolucionResponse>(), headers, HttpStatus.INTERNAL_SERVER_ERROR);
	}	
	
	@Override
	@GetMapping(value = "/{id}")
	@CircuitBreaker(name = MAIN_SERVICE, fallbackMethod = "getCallback")
	public ResponseEntity<DevolucionResponse> getById(@PathVariable("id") Long id) {
		return ResponseEntity.ok(service.viewById(id));
	}
	public ResponseEntity<DevolucionResponse> getCallback(Long id, Exception e) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("message", "Ha ocurrido un error de comunicación entre servidores. Por favor comunique a soporte técnico.");		
		return new ResponseEntity<DevolucionResponse>(new DevolucionResponse(), headers, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@Override
	@PutMapping(value = "/{id}")
	@CircuitBreaker(name = MAIN_SERVICE, fallbackMethod = "updateCallback")
	public ResponseEntity<DevolucionResponse> updateById(@RequestBody @Valid OperacionRequest data, @PathVariable("id") Long id) {
		return ResponseEntity.ok(service.updateById(data, id));
	}
	
	public ResponseEntity<DevolucionResponse> updateCallback(OperacionRequest data, Long id, Exception e) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("message", "Ha ocurrido un error de comunicación entre servidores. Por favor comunique a soporte técnico.");		
		return new ResponseEntity<DevolucionResponse>(new DevolucionResponse(), headers, HttpStatus.INTERNAL_SERVER_ERROR);
	}	

	@Override
	@DeleteMapping(value = "/{id}")
	@CircuitBreaker(name = MAIN_SERVICE, fallbackMethod = "getCallback")
	public ResponseEntity<DevolucionResponse> revertById(@PathVariable("id") Long id) {
		return ResponseEntity.ok(service.deleteById(id));
	}

	@Override
	public ResponseEntity<DevolucionResponse> activateById(Long id) {
		return ResponseEntity.ok(service.activateById(id));
	}

}
