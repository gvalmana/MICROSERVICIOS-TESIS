package com.geasp.micro.operaciones.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.geasp.micro.operaciones.responses.ParteResponse;
import com.geasp.micro.operaciones.services.IParteService;

@RestController
@RequestMapping(value = "/partes")
public class ParteController {

	@Autowired
	private IParteService service;
	
	@GetMapping(value = "/fecha={fecha}")
	public ResponseEntity<ParteResponse> getParteContenedores(@PathVariable("fecha") String fecha){
		return ResponseEntity.ok(service.makeParte(fecha));
	}
}
