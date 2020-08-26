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
import com.geasp.micro.mercancias.requests.GuiaRequest;
import com.geasp.micro.mercancias.responses.GuiaResponse;
import com.geasp.micro.mercancias.responses.ResumenPendientes;
import com.geasp.micro.mercancias.services.GuiaService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/guias")
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
@Api(value = "Microservicio de Guías Aereas", description = "Son las operaciones CRUD sobre las guías aéreas")
public class GuiasController implements IMercanciaControllers<GuiaResponse, GuiaRequest> {

	@Autowired
	private GuiaService service;
	
	private static final String MAIN_SERVICE = "mainService";	
	
	@Override
	@PostMapping
	@ApiOperation(value = "Registra una guía aérea")
	public ResponseEntity<GuiaResponse> Save(@Valid @RequestBody GuiaRequest entity) {
		return ResponseEntity.ok(service.save(entity));
	}

	@Override
	@GetMapping
	@ApiOperation(value = "Busca todas las guías aereas", notes = "Devuelve la lista completa")
	public ResponseEntity<List<GuiaResponse>> getAll(
            @RequestParam(defaultValue = "0") Integer pageNo, 
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy
			) {
		return ResponseEntity.ok(service.listar(pageNo, pageSize, sortBy));
	}

	@Override
	@GetMapping(value = "/{id}")
	@ApiOperation(value = "Busca una guía aérea", notes = "Busca una guía por su ID")
	public ResponseEntity<GuiaResponse> getById(@PathVariable("id") Long id) {
		return ResponseEntity.ok(service.viewById(id));
	}

	@Override
	@PutMapping(value = "/{id}")
	@ApiOperation(value = "Actualiza una guía", notes = "Actualiza una guia por si ID")
	public ResponseEntity<GuiaResponse> updateById(@RequestBody GuiaRequest data, @PathVariable("id") Long id) {
		return ResponseEntity.ok(service.updateById(data, id));
	}

	@Override
	@PostMapping(value = "/desactivate/{id}")
	@ApiOperation(value = "Desactiva una guía", notes = "Realiza un borrado logico de una guía aerea por su ID")
	public ResponseEntity<GuiaResponse> desactivateById(@PathVariable("id") Long id) {
		return ResponseEntity.ok(service.desactivateById(id));
	}

	@Override
	@GetMapping(value = "/estado={estado}")
	@ApiOperation(value = "Busca todas las guias segun un estado")
	public ResponseEntity<List<GuiaResponse>> getAllByState(
			@PathVariable("estado") EstadoMercancias estado,
            @RequestParam(defaultValue = "0") Integer pageNo, 
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy
            ) {
		return ResponseEntity.ok(service.listarPorEstado(estado, pageNo, pageSize, sortBy));
	}
	
	@GetMapping(value = "/pendientes")
	@ApiOperation(value = "Realiza un resumen de todas las guias pendientes a extraer por cada cliente")
	@CircuitBreaker(name = MAIN_SERVICE, fallbackMethod = "getResumenPendientesCallback")
	public ResponseEntity<List<ResumenPendientes>> getResumenPendientes(){
		System.out.println("BUSCAR ESTA LINEA");
		return ResponseEntity.ok(service.listarPendientes());
	}
	public ResponseEntity<List<ResumenPendientes>> getResumenPendientesCallback(Exception e){
		HttpHeaders headers = new HttpHeaders();
		headers.add("message", "Ha ocurrido un error de comunicación entre servidores. Por favor comunique a soporte técnico.");		
		return new ResponseEntity<List<ResumenPendientes>>(new ArrayList<ResumenPendientes>(), headers, HttpStatus.INTERNAL_SERVER_ERROR);
	}	
}
