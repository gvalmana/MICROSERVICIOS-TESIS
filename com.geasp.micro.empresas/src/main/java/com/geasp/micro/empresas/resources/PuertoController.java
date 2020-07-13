package com.geasp.micro.empresas.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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

import com.geasp.micro.empresas.models.Puerto;
import com.geasp.micro.empresas.services.PuertoService;

@RestController
@RequestMapping(value = "/v1/puertos")
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
public class PuertoController {

	@Autowired
	private PuertoService service;
	
	@GetMapping
	public ResponseEntity<List<Puerto>> listar(){
		return ResponseEntity.ok(service.listar());
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Puerto> viewById(@PathVariable("id") String id){
		return ResponseEntity.ok(service.viewById(id));
	}	
	
	@PostMapping
	public ResponseEntity<Puerto> save(@Validated @RequestBody Puerto entity){
		return ResponseEntity.ok(service.Save(entity));
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Puerto> updateById(@PathVariable("id") String id, @Validated @RequestBody Puerto entity){	
		return ResponseEntity.ok(service.updateById(id, entity));
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Puerto> deleteById(@PathVariable("id") String id){		
		return ResponseEntity.ok(service.deleteById(id));
	}	
}
