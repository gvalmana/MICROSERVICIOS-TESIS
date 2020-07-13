package com.geasp.micro.mercancias.resources;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	public ResponseEntity<ResumenCargas> getParte(@PathVariable("fecha") String fecha) {
		LocalDate date = LocalDate.parse(fecha);
		return ResponseEntity.ok(parte.makeParte(date));
	}
	@GetMapping(value = "/pendientes")
	public ResponseEntity<List<ResumenPendientes>> getResumenPendietnes(){
		return ResponseEntity.ok(parte.listarPendientes());
	}	
}