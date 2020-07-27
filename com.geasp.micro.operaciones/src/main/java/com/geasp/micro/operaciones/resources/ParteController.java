package com.geasp.micro.operaciones.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.geasp.micro.operaciones.responses.ParteResponse;
import com.geasp.micro.operaciones.services.IParteService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
@RequestMapping(value = "/partes")
public class ParteController {

	@Autowired
	private IParteService service;
	
	private static final String MAIN_SERVICE = "mainService";
	
	@GetMapping(value = "/fecha={fecha}")
	@CircuitBreaker(name = MAIN_SERVICE, fallbackMethod = "ParteFallback")
	public ResponseEntity<ParteResponse> getParteContenedores(@PathVariable("fecha") String fecha){
		return ResponseEntity.ok(service.makeParte(fecha));
	}
	public ResponseEntity<ParteResponse> ParteFallback(@PathVariable("fecha") String fecha, Exception e) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("message", "Ha ocurrido un error de comunicación entre servidores. Por favor comunique a soporte técnico.");
		return new ResponseEntity<ParteResponse>(new ParteResponse(), headers, HttpStatus.INTERNAL_SERVER_ERROR);
	}	
}
