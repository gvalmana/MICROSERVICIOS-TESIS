package com.geasp.micro.mercancias.resources;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.geasp.micro.mercancias.models.CantidadEmpresa;
import com.geasp.micro.mercancias.models.EstadoMercancias;
import com.geasp.micro.mercancias.requests.ContenedorRequest;
import com.geasp.micro.mercancias.responses.ContenedorResponse;
import com.geasp.micro.mercancias.responses.ResumenPendientes;
import com.geasp.micro.mercancias.services.ContenedorService;
import org.springframework.http.HttpHeaders;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

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
@Api(value = "Microservicios de los contenedores", description = "Estas son las operaciones CRUD de los contenedores" )
public class ContenedoresController implements IMercanciaControllers<ContenedorResponse, ContenedorRequest> {
	
	@Autowired
	private ContenedorService service;
	
	private static final String MAIN_SERVICE = "mainService";
	
	@PostMapping
	@Override
	@ApiOperation(value = "Registra un nuevo contenedor")
	public ResponseEntity<ContenedorResponse> Save(@RequestBody @Valid ContenedorRequest entity) {
		return ResponseEntity.ok(service.save(entity));
	}
	
	@GetMapping
	@Override
	@ApiOperation(value = "Buscar todos los contenedores", notes = "Devuelve tos los contenedores sin filtrar")
	public ResponseEntity<List<ContenedorResponse>> getAll(
	            @RequestParam(defaultValue = "0") Integer pageNo, 
	            @RequestParam(defaultValue = "10") Integer pageSize,
	            @RequestParam(defaultValue = "id") String sortBy
			){
		return ResponseEntity.ok(service.listar(pageNo, pageSize, sortBy));
	}
	
	@GetMapping(value = "/estado={estado}")
	@Override
	@ApiOperation(value = "Busca todos los contenedores por un estado dado", notes = "Devuelve la lista de contenedores")
	public ResponseEntity<List<ContenedorResponse>> getAllByState(
			@PathVariable("estado") EstadoMercancias estado,
            @RequestParam(defaultValue = "0") Integer pageNo, 
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy
			){
		return ResponseEntity.ok(service.listarPorEstado(estado, pageNo, pageSize, sortBy));
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
	
	@GetMapping(value = "/pordevolver")
	@CircuitBreaker(name = MAIN_SERVICE, fallbackMethod = "resumenPorDevolverCallback")
	@ApiOperation(value = "Crea un resumen de los contenedores por devolver por cada cliente")
	public ResponseEntity<List<CantidadEmpresa>> getResumenPorDevolver(){
		return ResponseEntity.ok(service.listarContenedoresDevolver());
	}
	public ResponseEntity<List<CantidadEmpresa>> resumenPorDevolverCallback(Exception e){
		HttpHeaders headers = new HttpHeaders();
		headers.add("message", "Ha ocurrido un error de comunicación entre servidores. Por favor comunique a soporte técnico.");		
		return new ResponseEntity<List<CantidadEmpresa>>(new ArrayList<CantidadEmpresa>(), headers, HttpStatus.INTERNAL_SERVER_ERROR);	
	}	
	
	@GetMapping(value = "/pendientes")
	@ApiOperation(value = "Lista los contenedores pendientes por cada cliente")
	@CircuitBreaker(name = MAIN_SERVICE, fallbackMethod = "getResumenPendientesCallback")
	public ResponseEntity<List<ResumenPendientes>> getResumenPendientes(){
		return ResponseEntity.ok(service.listarPendientes());
	}
	public ResponseEntity<List<ResumenPendientes>> getResumenPendientesCallback(Exception e){
		HttpHeaders headers = new HttpHeaders();
		headers.add("message", "Ha ocurrido un error de comunicación entre servidores. Por favor comunique a soporte técnico.");		
		return new ResponseEntity<List<ResumenPendientes>>(new ArrayList<ResumenPendientes>(), headers, HttpStatus.INTERNAL_SERVER_ERROR);
	}	
}
