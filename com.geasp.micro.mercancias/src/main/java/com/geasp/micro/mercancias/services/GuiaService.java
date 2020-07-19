package com.geasp.micro.mercancias.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.envers.repository.support.EnversRevisionRepositoryFactoryBean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.geasp.micro.mercancias.conf.Calculos;
import com.geasp.micro.mercancias.models.EstadoMercancias;
import com.geasp.micro.mercancias.models.Guia;
import com.geasp.micro.mercancias.repositories.GuiaRepository;
import com.geasp.micro.mercancias.requests.GuiaRequest;
import com.geasp.micro.mercancias.responses.GuiaResponse;

@Service
@EnableJpaRepositories(repositoryFactoryBeanClass = EnversRevisionRepositoryFactoryBean.class)
public class GuiaService implements IMercanciaService<GuiaResponse, GuiaRequest>{

	@Autowired
	Mapper mapper;
	
	@Autowired
	GuiaRepository dao;
	
	@Autowired
	private Calculos calculos;	
	
	@Override
	public GuiaResponse save(GuiaRequest entity) {		
		Guia guia = dao.saveAndFlush(mapper.map(entity, Guia.class));
		GuiaResponse response = mapper.map(guia, GuiaResponse.class);
		mappearDatos(guia, response);
		return response;
	}

	@Override
	public List<GuiaResponse> listar() {
		try {
			List<Guia> guias = dao.findAll();
			if (guias.size()>0) {
				List<GuiaResponse> response = llenarLista(guias);
				return response.stream().collect(Collectors.toList());
			} else {
				throw new ResponseStatusException(HttpStatus.NO_CONTENT,"Lista de guías aereas no encontrados");
			}
		} catch (ResponseStatusException e) {
			throw new ResponseStatusException(e.getStatus(), e.getMessage());
		}
	}

	@Override
	public List<GuiaResponse> listarPorEstado(EstadoMercancias estado) {
		try {
			List<Guia> guias = dao.findByEstado(estado);
			if (guias.size()>0) {
				return llenarLista(guias).stream().collect(Collectors.toList());
			} else {
				throw new ResponseStatusException(HttpStatus.NO_CONTENT,"Lista de guías aereas no encontrados");
			}
		} catch (ResponseStatusException e) {
			throw new ResponseStatusException(e.getStatus(), e.getMessage());
		}
	}
	
	@Override
	public GuiaResponse viewById(Long id) {
		try {
			Optional<Guia> guia = dao.findById(id);
			if (guia.isPresent()) {
				GuiaResponse response = mapper.map(guia.get(), GuiaResponse.class);
				mappearDatos(guia.get(), response);
				return response;
			} else {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Guía no encontrada");
			}
		} catch (ResponseStatusException e) {
			throw new ResponseStatusException(e.getStatus(), e.getMessage());
		}
	}

	@Override
	public GuiaResponse updateById(GuiaRequest request, Long id) {
		try {
			Optional<Guia> optional = dao.findById(id);
			if (optional.isPresent()) {
				Guia actualizada = optional.get();
				Guia data = mapper.map(request, Guia.class);
				mappearGuia(actualizada, data);
				dao.saveAndFlush(actualizada);
				GuiaResponse response = mapper.map(actualizada, GuiaResponse.class);
				return response;
			} else {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND,"La guia a actualizar no existe");
			}
		} catch (ResponseStatusException e) {
			throw new ResponseStatusException(e.getStatus(), e.getMessage());
		}
	}

	@Override
	public GuiaResponse desactivateById(Long id) {
		try {
			Optional<Guia> optional = dao.findById(id);
			if (optional.isPresent()) {
				Guia guia = optional.get();
				if (guia.getEstado()==EstadoMercancias.DISABLE) {
					guia.setEstado(EstadoMercancias.LISTO_PARA_EXTRAER);
				} else {
					guia.setEstado(EstadoMercancias.DISABLE);
				}
				dao.saveAndFlush(guia);
				GuiaResponse response = mapper.map(guia, GuiaResponse.class);
				mappearDatos(optional.get(), response);
				return response;
			} else {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"La guía a eliminar no existe");
			}
		} catch (ResponseStatusException e) {
			throw new ResponseStatusException(e.getStatus(), e.getMessage());
		}
	}
	
	private void mappearDatos(Guia data, GuiaResponse index) {
		index.setEstado(calculos.mappearEstadoToText(data));
		index.setDias_declarada(calculos.calcularDiasDeclarada(data));
		index.setDias_entregada(calculos.calcularDiasEntregada(data));
		index.setCant_dias_documentos_entregados(calculos.calcularDiasDocumentos(data));
		index.setEstadia(calculos.determinarEstadia(index));
	}
	
	private void mappearGuia(Guia actualizada, Guia data){
		actualizada.setFecha_habilitacion(data.getFecha_habilitacion());
		actualizada.setFecha_documentos(data.getFecha_documentos());
		actualizada.setSituacion(data.getSituacion());
		actualizada.setObservaciones(data.getObservaciones());
		actualizada.setDescripcion(data.getDescripcion());
		actualizada.setCliente(data.getCliente());
		actualizada.setImportadora(data.getImportadora());
		actualizada.setEstado(data.getEstado());		
		actualizada.setDm(data.getDm());
		actualizada.setCantidad_de_bultos(data.getCantidad_de_bultos());
		actualizada.setPeso(data.getPeso());
		actualizada.setFecha_entrega(data.getFecha_entrega());
		actualizada.setFecha_arribo(data.getFecha_arribo());
	}

	private List<GuiaResponse> llenarLista(List<Guia> lista){		
		List<GuiaResponse> response = new ArrayList<GuiaResponse>();
		lista.stream().forEach(x -> {
			GuiaResponse index = mapper.map(x, GuiaResponse.class);
			mappearDatos(x, index);
			response.add(index);
		});	
		return response;
	}

	@Override
	public List<GuiaResponse> listarPorFechaArribo(LocalDate date) {
		try {
			List<Guia> guias = dao.findAll().stream().filter(index->{
				return index.getFecha_arribo().equals(date);
			}).collect(Collectors.toList());
			if (guias.size()>0) {
				List<GuiaResponse> response = llenarLista(guias);
				return response.stream().collect(Collectors.toList());
			} else {
				throw new ResponseStatusException(HttpStatus.NO_CONTENT,"Lista de guías no encontrados");
			}
		} catch (ResponseStatusException e) {
			throw new ResponseStatusException(e.getStatus(), e.getMessage());
		}
	}

	@Override
	public GuiaResponse extractById(Long id) {
		try {
			Optional<Guia> optional = dao.findById(id);
			if (optional.isPresent()) {
				return crearRespuesta(optional,EstadoMercancias.EXTRAIDA);
			} else {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"La guía a extraer no existe");
			}
		} catch (ResponseStatusException e) {
			throw new ResponseStatusException(e.getStatus(), e.getMessage());
		}
	}

	@Override
	public GuiaResponse revertById(Long id) {
		try {
			Optional<Guia> optional = dao.findById(id);
			if (optional.isPresent()) {
				return crearRespuesta(optional,EstadoMercancias.LISTO_PARA_EXTRAER);
			} else {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"La guía a revertir no existe");
			}
		} catch (ResponseStatusException e) {
			throw new ResponseStatusException(e.getStatus(), e.getMessage());
		}
	}
	private GuiaResponse crearRespuesta(Optional<Guia> optional, EstadoMercancias estado) {
		Guia mercancia = optional.get();
		mercancia.setEstado(estado);
		dao.saveAndFlush(mercancia);
		GuiaResponse response = mapper.map(mercancia, GuiaResponse.class);
		mappearDatos(mercancia, response);
		return response;
	}	
}
