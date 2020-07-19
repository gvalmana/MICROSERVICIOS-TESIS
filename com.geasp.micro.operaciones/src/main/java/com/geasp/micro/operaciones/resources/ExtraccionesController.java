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
	IOperacionesService<ExtraccionResponse, OperacionRequest> service;
	
	@Override
	@PostMapping(value = "/{id}")
	public ResponseEntity<ExtraccionResponse> Save(@RequestBody @Valid OperacionRequest entity, @PathVariable("id") Long id) {
		return ResponseEntity.ok(service.registrar(entity, id));
	}

	@Override
	@GetMapping
	public ResponseEntity<List<ExtraccionResponse>> getAll() {
		return ResponseEntity.ok(service.listar());
	}

	@Override
	@GetMapping(value = "/{id}")
	public ResponseEntity<ExtraccionResponse> getById(@PathVariable("id") Long id) {
		return ResponseEntity.ok(service.viewById(id));
	}

	@Override
	@PutMapping(value = "/{id}")
	public ResponseEntity<ExtraccionResponse> updateById(@RequestBody @Valid OperacionRequest data, @PathVariable("id")  Long id) {
		return ResponseEntity.ok(service.updateById(data, id));
	}

	@Override
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<ExtraccionResponse> revertById(@PathVariable("id") Long id) {
		return ResponseEntity.ok(service.deleteById(id));
	}

	@Override
	@PostMapping(value = "activate/{id}")
	public ResponseEntity<ExtraccionResponse> activateById(@PathVariable("id") Long id) {
		// TODO Auto-generated method stub
		return ResponseEntity.ok(service.activateById(id));
	}
	
}
