package com.geasp.micro.empresas.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.geasp.micro.empresas.models.Cliente;
import com.geasp.micro.empresas.services.ClienteService;


@RestController
@RequestMapping(value = "/v1/clientes")
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
		allowCredentials = "true")
public class ClienteController {
	
	@Autowired
	private ClienteService service;
	
	@GetMapping
	public ResponseEntity<List<Cliente>> listar(){		
		return ResponseEntity.ok(service.listar());
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Cliente> viewById(@PathVariable("id") String id){
		return ResponseEntity.ok(service.viewById(id));
	}
	
	@PostMapping
	public ResponseEntity<Cliente> save(@Validated @RequestBody Cliente entity){
		return ResponseEntity.ok(service.Save(entity));
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Cliente> update(@Validated @RequestBody Cliente entity, @PathVariable("id") String id){
		return ResponseEntity.ok(service.updateById(entity, id));
	}
}
