package com.geasp.micro.mercancias.resources;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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

import com.geasp.micro.mercancias.models.EstadoMercancias;
import com.geasp.micro.mercancias.requests.CargaRequest;
import com.geasp.micro.mercancias.responses.CargaResponse;
import com.geasp.micro.mercancias.responses.ResumenPendientes;
import com.geasp.micro.mercancias.services.CargaService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/cargas")
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
public class CargasController implements IMercanciaControllers<CargaResponse, CargaRequest> {

	@Autowired
	private CargaService service;
	
	private static final String MAIN_SERVICE = "mainService";
	
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
	@GetMapping(value = "/estado={estado}")
	@ApiOperation(value = "Lista todas las cargas dado une estado")
	public ResponseEntity<List<CargaResponse>> getAllByState(
			@PathVariable("estado") EstadoMercancias estado) {
		return ResponseEntity.ok(service.listarPorEstado(estado));
	}
	
	@GetMapping(value = "/pendientes")
	@ApiOperation(value = "Lista un resumen de cargas agrupadas por cada cliente")
	@CircuitBreaker(name = MAIN_SERVICE, fallbackMethod = "getResumenPendientesCallback")	
	public ResponseEntity<List<ResumenPendientes>> getResumenPendietnes(){
		return ResponseEntity.ok(service.listarPendientes());
	}
	public ResponseEntity<List<ResumenPendientes>> getResumenPendientesCallback(Exception e){
		HttpHeaders headers = new HttpHeaders();
		headers.add("message", "Ha ocurrido un error de comunicación entre servidores. Por favor comunique a soporte técnico.");		
		return new ResponseEntity<List<ResumenPendientes>>(new ArrayList<ResumenPendientes>(), headers, HttpStatus.INTERNAL_SERVER_ERROR);
	}	
}
