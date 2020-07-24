package com.geasp.micro.operaciones.resources;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.geasp.micro.operaciones.responses.ExtraccionResponse;
import com.geasp.micro.operaciones.services.IOperacionesService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
@RequestMapping(value = "/extraccion")
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
public class ExtraccionesController implements IControllers<ExtraccionResponse,OperacionRequest> {

	@Autowired
	private IOperacionesService<ExtraccionResponse, OperacionRequest> service;
	
	private static final String MAIN_SERVICE = "mainService";
	@Override
	@PostMapping(value = "/{id}")
	@CircuitBreaker(name = MAIN_SERVICE, fallbackMethod = "parteFallback")
	public ResponseEntity<ExtraccionResponse> Save(@RequestBody @Valid OperacionRequest entity, @PathVariable("id") Long id) {
		//TODO hacer el callback
		return ResponseEntity.ok(service.registrar(entity, id));
	}

	@Override
	@GetMapping
	@CircuitBreaker(name = MAIN_SERVICE, fallbackMethod = "parteFallback")
	public ResponseEntity<List<ExtraccionResponse>> getAll() {
		//TODO hacer el callback
		return ResponseEntity.ok(service.listar());
	}

	@Override
	@CircuitBreaker(name = MAIN_SERVICE, fallbackMethod = "parteFallback")
	@GetMapping(value = "/{id}")
	public ResponseEntity<ExtraccionResponse> getById(@PathVariable("id") Long id) {
		//TODO hacer el callback
		return ResponseEntity.ok(service.viewById(id));
	}

	@Override
	@CircuitBreaker(name = MAIN_SERVICE, fallbackMethod = "parteFallback")
	@PutMapping(value = "/{id}")
	public ResponseEntity<ExtraccionResponse> updateById(@RequestBody @Valid OperacionRequest data, @PathVariable("id")  Long id) {
		//TODO hacer el callback
		return ResponseEntity.ok(service.updateById(data, id));
	}

	@Override
	@CircuitBreaker(name = MAIN_SERVICE, fallbackMethod = "parteFallback")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<ExtraccionResponse> revertById(@PathVariable("id") Long id) {
		//TODO hacer el callback
		return ResponseEntity.ok(service.deleteById(id));
	}

	@Override
	@CircuitBreaker(name = MAIN_SERVICE, fallbackMethod = "parteFallback")
	@PostMapping(value = "activate/{id}")
	public ResponseEntity<ExtraccionResponse> activateById(@PathVariable("id") Long id) {
		// TODO Auto-generated method stub
		return ResponseEntity.ok(service.activateById(id));
	}
	
}
