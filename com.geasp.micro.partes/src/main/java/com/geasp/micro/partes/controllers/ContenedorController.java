package com.geasp.micro.partes.controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.geasp.micro.partes.models.CantidadEmpresa;
import com.geasp.micro.partes.models.ResumenContenedores;
import com.geasp.micro.partes.models.ResumenPendientes;
import com.geasp.micro.partes.services.ContenedorService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
@RequestMapping(value = "/contenedores")
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
public class ContenedorController {

	@Autowired
	private ContenedorService service;
	private static final String MAIN_SERVICE = "mainService";
	
	@GetMapping
	@CircuitBreaker(name = MAIN_SERVICE, fallbackMethod = "getResumenPendientesCallback")
	ResponseEntity<List<ResumenPendientes>> getPendietnes(){
		return ResponseEntity.ok(service.getPendientes());
	}
	public ResponseEntity<List<ResumenPendientes>> getResumenPendientesCallback(Exception e){
		HttpHeaders headers = new HttpHeaders();
		headers.add("message", 
				"Ha ocurrido un error en la operación. Por favor comunique a soporte técnico.");		
		return new ResponseEntity<List<ResumenPendientes>>(new ArrayList<ResumenPendientes>(), headers, HttpStatus.NO_CONTENT);
	}
	
	@GetMapping(value = "/pordevolver")
	@CircuitBreaker(name = MAIN_SERVICE, fallbackMethod = "resumenPorDevolverCallback")
	//@ApiOperation(value = "Crea un resumen de los contenedores por devolver por cada cliente")
	public ResponseEntity<List<CantidadEmpresa>> getResumenPorDevolver(){
		return ResponseEntity.ok(service.listarContenedoresDevolver());
	}
	public ResponseEntity<List<CantidadEmpresa>> resumenPorDevolverCallback(Exception e){
		HttpHeaders headers = new HttpHeaders();
		headers.add("message", "Ha ocurrido un error de comunicación entre servidores. Por favor comunique a soporte técnico.");		
		return new ResponseEntity<List<CantidadEmpresa>>(new ArrayList<CantidadEmpresa>(), headers, HttpStatus.NO_CONTENT);	
	}
	
	@GetMapping("parte/fecha={fecha}")
	public ResponseEntity<ResumenContenedores> getParte(@PathVariable("fecha") String fecha){
		LocalDate date = LocalDate.parse(fecha);
		return ResponseEntity.ok(service.getParteByDate(date));
	}
}
