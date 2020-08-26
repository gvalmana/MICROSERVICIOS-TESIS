package com.geasp.micro.mercancias.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.geasp.micro.mercancias.responses.MercanciaResponse;
import com.geasp.micro.mercancias.services.OperacionesService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/operaciones")
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
@Api(value = "Operaciones sobre una mercancia", description = "Son los endpoints para realizar operaciones sobre cada mercancia")
public class OperacionesController {

	@Autowired
	private OperacionesService service;
	
	@PostMapping(value = "/extraer/{id}")
	@ApiOperation(value = "Extraer un contenedor", notes = "Cambia el estado de una mercancia a extraida por su ID")
	public ResponseEntity<MercanciaResponse> extractById(@PathVariable("id") Long id) {
		return ResponseEntity.ok(service.extractById(id));
	}
	
	@PostMapping(value = "/devolver/{id}")
	@ApiOperation(value = "Devuelve un contendor", notes = "Cambia el estado de una mercancia a devuelta por su ID")
	public ResponseEntity<MercanciaResponse> devolverById(@PathVariable("id") Long id){
		return ResponseEntity.ok(service.devolverById(id));
	}
	
	@PostMapping(value = "/revertir/{id}")
	@ApiOperation(value = "Revierte la ulitima operación", notes = "Revierte la ultima operación sobre una mercancia por su ID")
	public ResponseEntity<MercanciaResponse> revertById(@PathVariable("id") Long id){
		return ResponseEntity.ok(service.revertById(id));
	}

	@GetMapping(value = "/{id}")
	@ApiOperation(value = "Buscar una mercancia", notes = "Muestra una mercancia por su ID")
	public ResponseEntity<MercanciaResponse> viewById(@PathVariable("id") Long id) {
		return ResponseEntity.ok(service.viewById(id));
	}
}
