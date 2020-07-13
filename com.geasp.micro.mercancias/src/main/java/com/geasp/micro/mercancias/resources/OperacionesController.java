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
public class OperacionesController {

	@Autowired
	private OperacionesService service;
	
	@PostMapping(value = "/extraer/{id}")
	public ResponseEntity<MercanciaResponse> extractById(@PathVariable("id") Long id) {
		return ResponseEntity.ok(service.extractById(id));
	}
	
	@PostMapping(value = "/devolver/{id}")
	public ResponseEntity<MercanciaResponse> devolverById(@PathVariable("id") Long id){
		return ResponseEntity.ok(service.devolverById(id));
	}
	
	@PostMapping(value = "/revertir/{id}")
	public ResponseEntity<MercanciaResponse> revertById(@PathVariable("id") Long id){
		return ResponseEntity.ok(service.revertById(id));
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<MercanciaResponse> viewById(@PathVariable("id") Long id) {
		return ResponseEntity.ok(service.viewById(id));
	}
//	
//	@GetMapping
//	public ResponseEntity<List<MercanciaResponse>> listar(){
//		return ResponseEntity.ok(service.listar());
//	}
}
