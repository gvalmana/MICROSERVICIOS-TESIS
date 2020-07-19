package com.geasp.micro.mercancias.services;

import java.util.Optional;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.geasp.micro.mercancias.conf.Calculos;
import com.geasp.micro.mercancias.models.EstadoMercancias;
import com.geasp.micro.mercancias.models.Mercancia;
import com.geasp.micro.mercancias.repositories.MercanciaRepository;
import com.geasp.micro.mercancias.responses.MercanciaResponse;

@Service
public class OperacionesService implements IOperacionesService<MercanciaResponse> {

	@Autowired
	private MercanciaRepository dao;
	@Autowired
	private Mapper mapper;
	@Autowired
	private Calculos calculos;
	
	@Override
	public MercanciaResponse extractById(Long id) {
		try {
			Optional<Mercancia> optional = dao.findById(id);
			if (optional.isPresent()) {
				return crearRespuesta(optional,EstadoMercancias.EXTRAIDA);
			} else {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"El contenedor a extraer no existe");
			}
		} catch (ResponseStatusException e) {
			throw new ResponseStatusException(e.getStatus(), e.getMessage());
		}
	}
	
	@Override
	public MercanciaResponse devolverById(Long id) {
		try {
			Optional<Mercancia> optional = dao.findById(id);
			if (optional.isPresent()) {
				return crearRespuesta(optional,EstadoMercancias.DEVUELTA);
			} else {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"El contenedor a extraer no existe");
			}
		} catch (ResponseStatusException e) {
			throw new ResponseStatusException(e.getStatus(), e.getMessage());
		}
	}
	
	@Override
	public MercanciaResponse revertById(Long id) {
		try {
			Optional<Mercancia> optional = dao.findById(id);
			if (optional.isPresent()) {
				return crearRespuesta(optional,EstadoMercancias.LISTO_PARA_EXTRAER);
			} else {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"El contenedor a extraer no existe");
			}
		} catch (ResponseStatusException e) {
			throw new ResponseStatusException(e.getStatus(), e.getMessage());
		}
	}
	
	@Override
	public MercanciaResponse viewById(Long id) {
		try {
			Optional<Mercancia> optional = dao.findById(id);
			if (optional.isPresent()) {
				MercanciaResponse res = new MercanciaResponse();
				res = mapper.map(optional.get(), MercanciaResponse.class);
				mappearDatos(optional.get(), res);
				return res;
			} else {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"La mercancia solicitada no existe");
			}
		} catch (ResponseStatusException e) {
			throw new ResponseStatusException(e.getStatus(), e.getMessage());
		}
	}
	
	private MercanciaResponse crearRespuesta(Optional<Mercancia> optional, EstadoMercancias estado) {
		Mercancia mercancia = optional.get();
		mercancia.setEstado(estado);
		dao.saveAndFlush(mercancia);
		MercanciaResponse response = mapper.map(mercancia, MercanciaResponse.class);
		mappearDatos(mercancia, response);
		return response;
	}
	
	private void mappearDatos(Mercancia mercancia, MercanciaResponse index) {
		index.setEdad(calculos.calcularDiasEstadia(mercancia));
		index.setCant_dias_documentos_entregados(calculos.calcularDiasDocumentos(mercancia));
		index.setEstado(calculos.mappearEstadoToText(mercancia));
		index.setEstadia(calculos.determinarEstadia(index));
		index.setTipo_mercancia(mercancia.getClass().getSimpleName());	
	}
		
}
