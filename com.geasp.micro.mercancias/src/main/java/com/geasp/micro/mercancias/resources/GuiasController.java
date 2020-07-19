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
import com.geasp.micro.mercancias.requests.GuiaRequest;
import com.geasp.micro.mercancias.responses.GuiaResponse;
import com.geasp.micro.mercancias.responses.ResumenGuias;
import com.geasp.micro.mercancias.responses.ResumenPendientes;
import com.geasp.micro.mercancias.services.GuiaService;
import com.geasp.micro.mercancias.services.ParteGuiasService;

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
public class GuiasController implements IMercanciaControllers<GuiaResponse, GuiaRequest> {

	@Autowired
	private GuiaService service;
	
	@Autowired
	private ParteGuiasService parte;
	
	@Override
	@PostMapping
	public ResponseEntity<GuiaResponse> Save(@RequestBody GuiaRequest entity) {
		return ResponseEntity.ok(service.save(entity));
	}

	@Override
	@GetMapping
	public ResponseEntity<List<GuiaResponse>> getAll() {
		return ResponseEntity.ok(service.listar());
	}

	@Override
	@GetMapping(value = "/{id}")
	public ResponseEntity<GuiaResponse> getById(@PathVariable("id") Long id) {
		return ResponseEntity.ok(service.viewById(id));
	}

	@Override
	@PutMapping(value = "/{id}")
	public ResponseEntity<GuiaResponse> updateById(@RequestBody GuiaRequest data, @PathVariable("id") Long id) {
		return ResponseEntity.ok(service.updateById(data, id));
	}

	@Override
	@PostMapping(value = "/desactivate/{id}")
	public ResponseEntity<GuiaResponse> desactivateById(@PathVariable("id") Long id) {
		return ResponseEntity.ok(service.desactivateById(id));
	}

	@Override
	@GetMapping(value = "/estado={estado}")
	public ResponseEntity<List<GuiaResponse>> getAllByState(@PathVariable("estado") EstadoMercancias estado) {
		return ResponseEntity.ok(service.listarPorEstado(estado));
	}
	
	@GetMapping("/parte/fecha={fecha}")
	public ResponseEntity<ResumenGuias> getParte(@PathVariable("fecha") String fecha) {
		LocalDate date = LocalDate.parse(fecha);
		return ResponseEntity.ok(parte.makeParte(date));
	}
	@GetMapping(value = "/pendientes")
	public ResponseEntity<List<ResumenPendientes>> getResumenPendietnes(){
		return ResponseEntity.ok(parte.listarPendientes());
	}

	@Override
	@PostMapping(value = "/extraer/{id}")
	public ResponseEntity<GuiaResponse> extraerById(@PathVariable("id") Long id) {
		// TODO Auto-generated method stub
		return ResponseEntity.ok(service.extractById(id));
	}

	@Override
	@PostMapping(value = "/revertir/{id}")
	public ResponseEntity<GuiaResponse> revertirById(@PathVariable("id") Long id) {
		// TODO Auto-generated method stub
		return ResponseEntity.ok(service.revertById(id));
	}
}
