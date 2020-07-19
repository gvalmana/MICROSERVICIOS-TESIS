package com.geasp.micro.mercancias.resources;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

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

import com.geasp.micro.mercancias.models.CantidadEmpresa;
import com.geasp.micro.mercancias.models.EstadoMercancias;
import com.geasp.micro.mercancias.requests.ContenedorRequest;
import com.geasp.micro.mercancias.responses.ContenedorResponse;
import com.geasp.micro.mercancias.responses.ResumenContenedores;
import com.geasp.micro.mercancias.responses.ResumenPendientes;
import com.geasp.micro.mercancias.services.IMercanciaService;
import com.geasp.micro.mercancias.services.ParteContenedoresService;

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
public class ContenedoresController implements IMercanciaControllers<ContenedorResponse, ContenedorRequest> {
	
	@Autowired
	private IMercanciaService<ContenedorResponse, ContenedorRequest> service;

	@Autowired
	private ParteContenedoresService parte;
	
	@PostMapping
	@Override
	public ResponseEntity<ContenedorResponse> Save(@RequestBody @Valid ContenedorRequest entity) {
		return ResponseEntity.ok(service.save(entity));
	}
	
	@GetMapping
	@Override
	public ResponseEntity<List<ContenedorResponse>> getAll(){
		return ResponseEntity.ok(service.listar());
	}
	
	@GetMapping(value = "/estado={estado}")
	@Override
	public ResponseEntity<List<ContenedorResponse>> getAllByState(@PathVariable("estado") EstadoMercancias estado){
		return ResponseEntity.ok(service.listarPorEstado(estado));
	}
	
	@GetMapping(value = "/{id}")
	@Override
	public ResponseEntity<ContenedorResponse> getById(@PathVariable("id") Long id){
		return ResponseEntity.ok(service.viewById(id));
	}
	
	@PutMapping(value = "/{id}")
	@Override
	public ResponseEntity<ContenedorResponse> updateById(@RequestBody @Valid ContenedorRequest data, @PathVariable("id") Long id){
		return ResponseEntity.ok(service.updateById(data, id));
	}
	
	@PostMapping(value = "/desactivate/{id}")
	@Override
	public ResponseEntity<ContenedorResponse> desactivateById(@PathVariable("id") Long id){
		return ResponseEntity.ok(service.desactivateById(id));
	}
	
	@GetMapping("/parte/fecha={fecha}")
	public ResponseEntity<ResumenContenedores> getParte(@PathVariable("fecha") String fecha) {
		LocalDate date = LocalDate.parse(fecha);
		return ResponseEntity.ok(parte.makeParte(date));
	}
	
	@GetMapping(value = "/pordevolver")
	public ResponseEntity<List<CantidadEmpresa>> getResumenPorDevolver(){
		return ResponseEntity.ok(parte.listarContenedoresDevolver());
	}
	@GetMapping(value = "/pendientes")
	public ResponseEntity<List<ResumenPendientes>> getResumenPendietnes(){
		return ResponseEntity.ok(parte.listarPendientes());
	}
}
