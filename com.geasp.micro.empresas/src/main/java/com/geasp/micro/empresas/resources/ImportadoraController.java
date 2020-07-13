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

import com.geasp.micro.empresas.models.Importadora;
import com.geasp.micro.empresas.services.ImportadoraService;

@RestController
@RequestMapping(value = "/v1/importadoras")
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
public class ImportadoraController {

	@Autowired
	private ImportadoraService service;
	
	@GetMapping
	public ResponseEntity<List<Importadora>> listar(){
		return ResponseEntity.ok(service.listar());
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Importadora> viewById(@PathVariable("id") String id){
		return ResponseEntity.ok(service.viewById(id));
	}	
	
	@PostMapping
	public ResponseEntity<Importadora> save(@Validated @RequestBody Importadora entity){
		return ResponseEntity.ok(service.Save(entity));
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Importadora> updateById(@PathVariable("id") String id, @Validated @RequestBody Importadora entity){	
		return ResponseEntity.ok(service.updateById(id, entity));
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Importadora> deleteById(@PathVariable("id") String id){		
		return ResponseEntity.ok(service.deleteById(id));
	}	
}
