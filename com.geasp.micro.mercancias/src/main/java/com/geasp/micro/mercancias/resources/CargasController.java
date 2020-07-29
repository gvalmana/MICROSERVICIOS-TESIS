package com.geasp.micro.mercancias.resources;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.geasp.micro.mercancias.models.EstadoMercancias;
import com.geasp.micro.mercancias.requests.CargaRequest;
import com.geasp.micro.mercancias.responses.CargaResponse;
import com.geasp.micro.mercancias.responses.ResumenCargas;
import com.geasp.micro.mercancias.responses.ResumenPendientes;
import com.geasp.micro.mercancias.services.CargaService;
import com.geasp.micro.mercancias.services.ParteCargasService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

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
public class CargasController implements IMercanciaControllers<CargaResponse, CargaRequest> {

	@Autowired
	private CargaService service;
	
	@Autowired
	private ParteCargasService parte;
	
	private static final String MAIN_SERVICE = "mainService";
	
	@Override
	@PostMapping
	public ResponseEntity<CargaResponse> Save(@RequestBody CargaRequest entity) {
		return ResponseEntity.ok(service.save(entity));
	}

	@Override
	@GetMapping
	public ResponseEntity<List<CargaResponse>> getAll() {
		return ResponseEntity.ok(service.listar());
	}

	@Override
	@GetMapping(value = "/{id}")
	public ResponseEntity<CargaResponse> getById(@PathVariable("id") Long id) {
		return ResponseEntity.ok(service.viewById(id));
	}

	@Override
	@PutMapping(value = "/{id}")
	public ResponseEntity<CargaResponse> updateById(@RequestBody CargaRequest data, @PathVariable("id") Long id) {
		return ResponseEntity.ok(service.updateById(data, id));
	}

	@PostMapping(value = "/desactivate/{id}")
	@Override
	public ResponseEntity<CargaResponse> desactivateById(@PathVariable("id") Long id){
		return ResponseEntity.ok(service.desactivateById(id));
	}

	@Override
	@GetMapping(value = "/estado={estado}")
	public ResponseEntity<List<CargaResponse>> getAllByState(@PathVariable("estado") EstadoMercancias estado) {
		return ResponseEntity.ok(service.listarPorEstado(estado));
	}
	
	@GetMapping("/parte/fecha={fecha}")
	@CircuitBreaker(name = MAIN_SERVICE, fallbackMethod = "ParteFallback")		
	public ResponseEntity<ResumenCargas> getParte(@PathVariable("fecha") String fecha) {
		LocalDate date = LocalDate.parse(fecha);
		return ResponseEntity.ok(parte.makeParte(date));
	}
	public ResponseEntity<ResumenCargas> ParteFallback(@PathVariable("fecha") String fecha, Exception e) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("message", "Ha ocurrido un error de comunicación entre servidores. Por favor comunique a soporte técnico.");
		return new ResponseEntity<ResumenCargas>(new ResumenCargas(), headers, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@GetMapping(value = "/pendientes")
	@CircuitBreaker(name = MAIN_SERVICE, fallbackMethod = "getResumenPendientesCallback")	
	public ResponseEntity<List<ResumenPendientes>> getResumenPendietnes(){
		return ResponseEntity.ok(parte.listarPendientes());
	}
	public ResponseEntity<List<ResumenPendientes>> getResumenPendientesCallback(Exception e){
		HttpHeaders headers = new HttpHeaders();
		headers.add("message", "Ha ocurrido un error de comunicación entre servidores. Por favor comunique a soporte técnico.");		
		return new ResponseEntity<List<ResumenPendientes>>(new ArrayList<ResumenPendientes>(), headers, HttpStatus.INTERNAL_SERVER_ERROR);
	}	
}
