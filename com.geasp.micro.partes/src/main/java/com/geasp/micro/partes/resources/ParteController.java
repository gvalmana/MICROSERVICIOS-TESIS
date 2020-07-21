package com.geasp.micro.partes.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.geasp.micro.partes.models.Parte;
import com.geasp.micro.partes.services.IParte;
@RestController
@RequestMapping(value = "/parte")
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
public class ParteController {

	@Autowired
	IParte servicio;
		
	@GetMapping("/fecha={fecha}")
	public ResponseEntity<Parte> getParteById(@PathVariable("fecha") String fecha){		
		return ResponseEntity.ok(servicio.getParteByDate(fecha));
	}
	
}
