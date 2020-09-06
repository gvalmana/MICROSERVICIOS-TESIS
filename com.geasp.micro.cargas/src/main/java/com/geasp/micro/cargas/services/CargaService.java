package com.geasp.micro.cargas.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.geasp.micro.cargas.Calculos;
import com.geasp.micro.cargas.models.Carga;
import com.geasp.micro.cargas.models.EstadoMercancias;
import com.geasp.micro.cargas.repositories.CargaRepository;
import com.geasp.micro.cargas.requets.CargaRequest;
import com.geasp.micro.cargas.requets.OperacionRequest;
import com.geasp.micro.cargas.responses.CargaResponse;

@Service
public class CargaService implements ICargaService<CargaResponse, CargaRequest> {
	
	@Autowired
	private CargaRepository dao;
	
	@Autowired
	private Mapper mapper;
	
	@Autowired
	private Calculos calculos;	
	
	@Override
	public CargaResponse save(CargaRequest entity) {
		Carga carga = dao.saveAndFlush(mapper.map(entity, Carga.class));
		CargaResponse response = mapper.map(carga, CargaResponse.class);
		mappearDatos(carga, response);
		return response;			
	}

	@Override
	public List<CargaResponse> listar() {
		try {
			List<Carga> cargas = dao.findAll();
			if (cargas.size()>0) {
				return llenarLista(cargas.stream().collect(Collectors.toList()));
			} else {
				throw new ResponseStatusException(HttpStatus.NO_CONTENT,"Lista de cargas agrupadas no encontrados");
			}
		} catch (ResponseStatusException e) {
			throw new ResponseStatusException(e.getStatus(), e.getMessage());
		}		
	}

	@Override
	public List<CargaResponse> listarPorEstado(EstadoMercancias estado) {
		try {			
			List<Carga> cargas = dao.findByEstado(estado);
			if (cargas.size()>0) {
				return llenarLista(cargas).stream().collect(Collectors.toList());
			} else {
				throw new ResponseStatusException(HttpStatus.NO_CONTENT,"Lista de cargas agrupadas no encontrados");
			}
		} catch (ResponseStatusException e) {
			throw new ResponseStatusException(e.getStatus(), e.getMessage());
		}
	}

	@Override
	public CargaResponse viewById(Long id) {
		try {
			Optional<Carga> optional = dao.findById(id);
			if (optional.isPresent()) {
				CargaResponse response = mapper.map(optional.get(), CargaResponse.class);
				mappearDatos(optional.get(), response);
				return response;
			} else {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Carga agrupada no encontrada");
			}
		} catch (ResponseStatusException e) {
			throw new ResponseStatusException(e.getStatus(), e.getMessage());
		}
	}

	@Override
	public CargaResponse updateById(CargaRequest request, Long id) {
		try {
			Optional<Carga> optional = dao.findById(id);
			if (optional.isPresent()) {
				Carga actualizada = optional.get();
				Carga data = mapper.map(request, Carga.class);
				mappearCarga(actualizada, data);
				dao.saveAndFlush(actualizada);
				CargaResponse response = mapper.map(actualizada, CargaResponse.class);
				return response;
			} else {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND,"La carga a actualizar no existe");
			}
		} catch (ResponseStatusException e) {
			throw new ResponseStatusException(e.getStatus(), e.getMessage());
		}
	}

	@Override
	public CargaResponse desactivateById(Long id) {
		try {
			Optional<Carga> optional = dao.findById(id);
			if (optional.isPresent()) {
				Carga carga = optional.get();
				if (carga.getEstado()==EstadoMercancias.DISABLE) {
					carga.setEstado(EstadoMercancias.LISTO_PARA_EXTRAER);
				} else {
					carga.setEstado(EstadoMercancias.DISABLE);
				}
				dao.saveAndFlush(carga);
				CargaResponse response = mapper.map(optional.get(), CargaResponse.class);
				mappearDatos(carga, response);
				return response;
			} else {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"La carga a eliminar no existe");
			}
		} catch (ResponseStatusException e) {
			throw new ResponseStatusException(e.getStatus(), e.getMessage());
		}
	}
	
	private void mappearDatos(Carga data, CargaResponse index) {
		index.setCantidad_dias_desagrupada(calculos.calcularDiasDesagrupada(data));
		index.setEstado(calculos.mappearEstadoToText(data));
		index.setCant_dias_documentos_entregados(calculos.calcularDiasDocumentos(data));
		index.setEstadia(calculos.determinarEstadia(index));
	}
	
	private void mappearCarga(Carga actualizada, Carga data) {
		actualizada.setFecha_habilitacion(data.getFecha_habilitacion());
		actualizada.setFecha_documentos(data.getFecha_documentos());
		actualizada.setFecha_desagrupe(data.getFecha_desagrupe());
		actualizada.setFecha_descarga(data.getFecha_descarga());
		actualizada.setFecha_arribo(data.getFecha_arribo());
		actualizada.setSituacion(data.getSituacion());
		actualizada.setObservaciones(data.getObservaciones());
		actualizada.setDescripcion(data.getDescripcion());
		actualizada.setCliente(data.getCliente());
		actualizada.setImportadora(data.getImportadora());
		actualizada.setEstado(data.getEstado());				
		actualizada.setManifiesto(data.getManifiesto());
		actualizada.setDm(data.getDm());
		actualizada.setBl(data.getBl());
		actualizada.setCodigo_contenedor(data.getCodigo_contenedor());
		actualizada.setPuerto(data.getPuerto());
	}

	private List<CargaResponse> llenarLista(List<Carga> lista){
		List<CargaResponse> response = new ArrayList<CargaResponse>();
		lista.stream().forEach(x -> {
			CargaResponse index = mapper.map(x, CargaResponse.class);
			mappearDatos(x, index);
			response.add(index);			
		});	
		return response;
	}

	@Override
	public List<CargaResponse> listarPorFechaArribo(LocalDate date) {
		try {
			List<Carga> cargas = dao.findAll().stream().filter(index->{
				return index.getFecha_arribo().equals(date);
			}).collect(Collectors.toList());
			if (cargas.size()>0) {
				return llenarLista(cargas).stream().collect(Collectors.toList());
			} else {
				throw new ResponseStatusException(HttpStatus.NO_CONTENT,"Lista de cargas agrupadas no encontrados");
			}
		} catch (ResponseStatusException e) {
			throw new ResponseStatusException(e.getStatus(), e.getMessage());
		}
	}

	@Override
	public CargaResponse deleteById(Long id) {
		try {
			Optional<Carga> optional = dao.findById(id);
			if (optional.isPresent()) {
				Carga carga = optional.get();
				CargaResponse response = mapper.map(optional.get(), CargaResponse.class);
				mappearDatos(carga, response);
				dao.deleteById(id);
				return response;
			} else {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"La carga a eliminar no existe");
			}
		} catch (ResponseStatusException e) {
			throw new ResponseStatusException(e.getStatus(), e.getMessage());
		}
	}

	@Override
	public CargaResponse extractById(Long id, OperacionRequest date) {
		// TODO Auto-generated method stub
		try {
			Optional<Carga> optional = dao.findById(id);
			if (optional.isPresent()) {
				Carga carga = optional.get();
				carga.setEstado(EstadoMercancias.EXTRAIDA);
				carga.setFecha_extraccion(date.getFecha());
				dao.saveAndFlush(carga);
				CargaResponse response = mapper.map(carga, CargaResponse.class);
				mappearDatos(carga, response);
				return response;
			} else {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND,"La carga a extraer no existe");
			}
		} catch (ResponseStatusException e) {
			throw new ResponseStatusException(e.getStatus(), e.getMessage());
		}
	}

	@Override
	public CargaResponse revertById(Long id) {
		try {
			Optional<Carga> optional = dao.findById(id);
			if (optional.isPresent()) {
				Carga carga = optional.get();
				switch (carga.getEstado()) {
				case EXTRAIDA:
					carga.setEstado(EstadoMercancias.LISTO_PARA_EXTRAER);
					carga.setFecha_extraccion(null);
				default:
					break;
				}
				dao.saveAndFlush(carga);
				CargaResponse response = mapper.map(carga, CargaResponse.class);
				return response;
			} else {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND,"La carga a actualizar no existe");
			}
		} catch (ResponseStatusException e) {
			throw new ResponseStatusException(e.getStatus(), e.getMessage());
		}
	}

	@Override
	public List<CargaResponse> listarPorEstados(List<EstadoMercancias> estados) {
		try {			
			List<Carga> cargas = dao.findByEstadoIn(estados);
			if (cargas.size()>0) {
				return llenarLista(cargas).stream().collect(Collectors.toList());
			} else {
				throw new ResponseStatusException(HttpStatus.NO_CONTENT,"Lista de cargas agrupadas no encontrados");
			}
		} catch (ResponseStatusException e) {
			throw new ResponseStatusException(e.getStatus(), e.getMessage());
		}
	}	
}
