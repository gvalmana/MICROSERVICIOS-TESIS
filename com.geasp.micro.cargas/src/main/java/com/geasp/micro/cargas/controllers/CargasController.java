package com.geasp.micro.cargas.controllers;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.geasp.micro.cargas.models.EstadoMercancias;
import com.geasp.micro.cargas.requets.CargaRequest;
import com.geasp.micro.cargas.requets.OperacionRequest;
import com.geasp.micro.cargas.responses.CargaResponse;
import com.geasp.micro.cargas.services.CargaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/v1")
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
@Api(value = "Microservicio de las cargas agrupadas", description = "Estas son las operaciones CRUD sobre las cargas agrupadas")
public class CargasController implements ICargasControllers<CargaResponse, CargaRequest> {

	@Autowired
	private CargaService service;
	
	@Override
	@PostMapping
	@ApiOperation(value = "Registra una nueva carga agrupada")
	public ResponseEntity<CargaResponse> Save(@Valid @RequestBody CargaRequest entity) {
		return ResponseEntity.ok(service.save(entity));
	}

	@Override
	@GetMapping
	@ApiOperation(value = "Busca todas las cargas agrupadas", notes = "Devuelve la lista completa")
	public ResponseEntity<List<CargaResponse>> getAll(			) {
		return ResponseEntity.ok(service.listar());
	}

	@Override
	@GetMapping(value = "/{id}")
	@ApiOperation(value = "Busca una carga", notes = "Busca una carga por su ID")
	public ResponseEntity<CargaResponse> getById(@PathVariable("id") Long id) {
		return ResponseEntity.ok(service.viewById(id));
	}

	@Override
	@PutMapping(value = "/{id}")
	@ApiOperation(value = "Actualiza una carga agrupada")
	public ResponseEntity<CargaResponse> updateById(@Valid @RequestBody CargaRequest data, @PathVariable("id") Long id) {
		return ResponseEntity.ok(service.updateById(data, id));
	}

	@PostMapping(value = "/desactivate/{id}")
	@Override
	@ApiOperation(value = "Desactiva una carga agrupada", notes = "Hace un borrado logico de la carga agrupada")
	public ResponseEntity<CargaResponse> desactivateById(@PathVariable("id") Long id){
		return ResponseEntity.ok(service.desactivateById(id));
	}

	@Override
	@GetMapping(value = "/buscarporestado")
	@ApiOperation(value = "Lista todas las cargas dado une estado")
	public ResponseEntity<List<CargaResponse>> getAllByState(@RequestParam(name = "estado",defaultValue = "LISTO_PARA_EXTRAER") EstadoMercancias estado) {
		return ResponseEntity.ok(service.listarPorEstado(estado));
	}
	
	@Override
	@DeleteMapping(value = "/{id}")
	@ApiOperation(value = "Elimina una carga", notes = "Elimina  una carga por su ID")	
	public ResponseEntity<CargaResponse> deleteById(@PathVariable("id") Long id) {
		// TODO Auto-generated method stub
		return ResponseEntity.ok(service.deleteById(id));
	}

	@Override
	@PostMapping(value = "/extraer/{id}")
	@ApiOperation(value = "Extraer un contenedor", notes = "Cambia el estado de una mercancia a extraida por su ID")		
	public ResponseEntity<CargaResponse> extractById(@PathVariable("id") Long id, @RequestBody @Valid  OperacionRequest date) {
		// TODO Auto-generated method stub
		return ResponseEntity.ok(service.extractById(id, date));
	}

	@Override
	@PostMapping(value = "/revertir/{id}")
	public ResponseEntity<CargaResponse> revertById(@PathVariable("id") Long id) {
		// TODO Auto-generated method stub
		return ResponseEntity.ok(service.revertById(id));
	}

	@Override
	@GetMapping(value = "/buscarporestados")
	@ApiOperation(value = "Lista todas las cargas dado varios estados")
	public ResponseEntity<List<CargaResponse>> getAllByStates(@RequestParam(name = "estados",defaultValue = "[LISTO_PARA_EXTRAER]") List<EstadoMercancias> estados) {
		// TODO Auto-generated method stub
		return ResponseEntity.ok(service.listarPorEstados(estados));
	}	
}
