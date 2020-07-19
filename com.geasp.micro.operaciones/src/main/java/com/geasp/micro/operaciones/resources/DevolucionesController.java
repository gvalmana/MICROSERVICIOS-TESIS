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
import com.geasp.micro.operaciones.responses.DevolucionResponse;
import com.geasp.micro.operaciones.services.IOperacionesService;

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
	
	@Override
	@PostMapping(value = "/{id}")
	public ResponseEntity<DevolucionResponse> Save(@RequestBody @Valid OperacionRequest entity, @PathVariable("id") Long id) {
		return ResponseEntity.ok(service.registrar(entity, id));
	}

	@Override
	@GetMapping
	public ResponseEntity<List<DevolucionResponse>> getAll() {
		return ResponseEntity.ok(service.listar());
	}

	@Override
	@GetMapping(value = "/{id}")
	public ResponseEntity<DevolucionResponse> getById(@PathVariable("id") Long id) {
		return ResponseEntity.ok(service.viewById(id));
	}

	@Override
	@PutMapping(value = "/{id}")
	public ResponseEntity<DevolucionResponse> updateById(@RequestBody @Valid OperacionRequest data, @PathVariable("id") Long id) {
		return ResponseEntity.ok(service.updateById(data, id));
	}

	@Override
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<DevolucionResponse> revertById(@PathVariable("id") Long id) {
		return ResponseEntity.ok(service.deleteById(id));
	}

	@Override
	public ResponseEntity<DevolucionResponse> activateById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
