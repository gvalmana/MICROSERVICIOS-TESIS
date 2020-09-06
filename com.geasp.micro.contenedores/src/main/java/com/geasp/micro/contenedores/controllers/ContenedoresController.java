package com.geasp.micro.contenedores.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.history.Revisions;
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

import com.geasp.micro.contenedores.models.Contenedor;
import com.geasp.micro.contenedores.models.EstadoMercancias;
import com.geasp.micro.contenedores.requests.ContenedorRequest;
import com.geasp.micro.contenedores.requests.OperacionRequest;
import com.geasp.micro.contenedores.responses.ContenedorResponse;
import com.geasp.micro.contenedores.services.ContenedorService;
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
@Api(value = "Microservicios de los contenedores", description = "Estas son las operaciones CRUD de los contenedores" )
public class ContenedoresController implements IContenedorController<ContenedorResponse, ContenedorRequest> {
	
	@Autowired
	private ContenedorService service;
	
	@PostMapping
	@Override
	@ApiOperation(value = "Registra un nuevo contenedor")
	public ResponseEntity<ContenedorResponse> Save(@RequestBody @Valid ContenedorRequest entity) {
		return ResponseEntity.ok(service.save(entity));
	}
	
	@GetMapping
	@Override
	@ApiOperation(value = "Buscar todos los contenedores", notes = "Devuelve tos los contenedores sin filtrar")
	public ResponseEntity<List<ContenedorResponse>> getAll(){
		return ResponseEntity.ok(service.listar());
	}
	
	@GetMapping(value = "/buscarporestado")
	@Override
	@ApiOperation(value = "Busca todos los contenedores por un estado dado", notes = "Devuelve la lista de contenedores")
	public ResponseEntity<List<ContenedorResponse>> getAllByState(@RequestParam(name = "estado",defaultValue = "LISTO_PARA_EXTRAER") EstadoMercancias estado){
		return ResponseEntity.ok(service.listarPorEstado(estado));
	}
	
	@GetMapping(value = "/{id}")
	@Override
	@ApiOperation(value = "Buscar un contenedor", notes = "Devuelve un contenedor por su ID")
	public ResponseEntity<ContenedorResponse> getById(@PathVariable("id") Long id){
		return ResponseEntity.ok(service.viewById(id));
	}
	
	@PutMapping(value = "/{id}")
	@Override
	@ApiOperation(value = "Actualiza un contenedor")
	public ResponseEntity<ContenedorResponse> updateById(@RequestBody @Valid ContenedorRequest data, @PathVariable("id") Long id){
		return ResponseEntity.ok(service.updateById(data, id));
	}
	
	@PostMapping(value = "/desactivate/{id}")
	@Override
	@ApiOperation(value = "Desactiva un contenedor", notes = "Desactiva un contenedor del sitema haciendo un borrado logico")
	public ResponseEntity<ContenedorResponse> desactivateById(@PathVariable("id") Long id){
		return ResponseEntity.ok(service.desactivateById(id));
	}
	
	@Override
	@PostMapping(value = "/extraer/{id}")
	@ApiOperation(value = "Extraer un contenedor", notes = "Cambia el estado de un contenedor a extraido por su ID")	
	public ResponseEntity<ContenedorResponse> extractById(@PathVariable("id") Long id, @RequestBody @Valid  OperacionRequest date) {
		// TODO Auto-generated method stub
		return ResponseEntity.ok(service.extractById(id, date));
	}

	@Override
	@PostMapping(value = "/devolver/{id}")
	@ApiOperation(value = "Devolve un contenedor", notes = "Cambia el estado de un contenedor a devuelto por su ID")	
	public ResponseEntity<ContenedorResponse> devolverById(@PathVariable("id") Long id, @RequestBody @Valid  OperacionRequest date) {
		// TODO Auto-generated method stub
		return ResponseEntity.ok(service.devolverById(id, date));
	}

	@Override
	@PostMapping(value = "/revertir/{id}")
	@ApiOperation(value = "Revierte operacion", notes = "Revierte el estado de un contenedor a su anterior por si ID")		
	public ResponseEntity<ContenedorResponse> revertById(@PathVariable("id") Long id) {
		return ResponseEntity.ok(service.revertById(id));
	}

	@Override
	@DeleteMapping(value = "/{id}")
	@ApiOperation(value = "Elimina un contenedor", notes = "Elimina un contenedor por su ID")	
	public ResponseEntity<ContenedorResponse> deleteById(@PathVariable("id") Long id) {
		// TODO Auto-generated method stub
		return ResponseEntity.ok(service.deleteById(id));
	}

	@Override
	@GetMapping(value = "/buscarporestados")
	@ApiOperation(value = "Busca todos los contenedores por varios estados", notes = "Devuelve la lista de contenedores")
	public ResponseEntity<List<ContenedorResponse>> getAllByStates(@RequestParam(name = "estados",defaultValue = "[LISTO_PARA_EXTRAER]") List<EstadoMercancias> estados) {
		// TODO Auto-generated method stub
		return ResponseEntity.ok(service.listarPorEstados(estados));
	}

	@Override
	@GetMapping(value = "/auditar/{id}")
	public ResponseEntity<Revisions<Integer, Contenedor>> findRevisions(@PathVariable("id") Long id){
		return ResponseEntity.ok(service.findRevisions(id));
	}	
}
