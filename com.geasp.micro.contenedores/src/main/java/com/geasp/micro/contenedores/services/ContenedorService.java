package com.geasp.micro.contenedores.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.history.Revisions;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.geasp.micro.contenedores.Calculos;
import com.geasp.micro.contenedores.models.Contenedor;
import com.geasp.micro.contenedores.models.EstadoMercancias;
import com.geasp.micro.contenedores.repositories.ContenedorRepository;
import com.geasp.micro.contenedores.requests.ContenedorRequest;
import com.geasp.micro.contenedores.requests.OperacionRequest;
import com.geasp.micro.contenedores.responses.ContenedorResponse;

@Service
public class ContenedorService implements IContenedorService<ContenedorResponse,ContenedorRequest> {
	
	@Autowired
	private ContenedorRepository dao;
	
	@Autowired
	private Mapper mapper;
	
	@Autowired
	private Calculos calculos;
	
	@Override
	public ContenedorResponse save(ContenedorRequest entity) {
		Contenedor contenedor = dao.save(mapper.map(entity, Contenedor.class));
		ContenedorResponse response = mapper.map(contenedor, ContenedorResponse.class);
		mappearDatos(contenedor, response);
		return response;
	}
	
	@Override
	public List<ContenedorResponse> listar() {
		try {
			List<Contenedor> contenedores = dao.findAll();
			if (contenedores.size()>0) {
				return llenarLista(contenedores.stream().collect(Collectors.toList()));				
			} else {
				throw new ResponseStatusException(HttpStatus.NO_CONTENT,"Lista de contenedores no encontrados");
			}			
		} catch (ResponseStatusException e) {
			throw new ResponseStatusException(e.getStatus(), e.getMessage());
		}
	}

	@Override
	public List<ContenedorResponse> listarPorEstado(EstadoMercancias estado) {		
		try {
			List<Contenedor> contenedores = dao.findByEstado(estado);
			if (contenedores.size()>0) {
				return llenarLista(contenedores.stream().collect(Collectors.toList()));
			} else {
				throw new ResponseStatusException(HttpStatus.NO_CONTENT,"Lista de contenedores no encontrados");
			}
		} catch (ResponseStatusException e) {
			throw new ResponseStatusException(e.getStatus(), e.getMessage());
		}
	}

	@Override
	public ContenedorResponse viewById(Long id) {
		try {
			Optional<Contenedor> contenedor = dao.findById(id);
			if (contenedor.isPresent()) {				
				ContenedorResponse response = new ContenedorResponse();
				response = mapper.map(contenedor.get(), ContenedorResponse.class);
				mappearDatos(contenedor.get(), response);
				return response;				
			} else {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Contenedor no encontrado");
			}
		} catch (ResponseStatusException e) {
			throw new ResponseStatusException(e.getStatus(), e.getMessage());
		}
	}
	
	@Override
	public ContenedorResponse updateById(ContenedorRequest request, Long id) {
		try {
			Optional<Contenedor> contenedorOptional = dao.findById(id);
			if (contenedorOptional.isPresent()) {
				Contenedor actualizado = contenedorOptional.get();
				Contenedor data = mapper.map(request, Contenedor.class);
				mappearContenedor(actualizado, data);
				dao.save(actualizado);
				ContenedorResponse response = mapper.map(actualizado, ContenedorResponse.class);
				mappearDatos(actualizado, response);
				return response;
			} else {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND,"El contenedor a actualizar no existe");
			}
		} catch (ResponseStatusException e) {
			throw new ResponseStatusException(e.getStatus(), e.getMessage());
		}
	}
	
	@Override
	public ContenedorResponse desactivateById(Long id) {
		try {
			Optional<Contenedor> optional = dao.findById(id);
			if (optional.isPresent()) {				
				Contenedor contenedor = optional.get();
				if (contenedor.getEstado()==EstadoMercancias.DISABLE) {
					contenedor.setEstado(EstadoMercancias.LISTO_PARA_EXTRAER);
				} else {
					contenedor.setEstado(EstadoMercancias.DISABLE);				
				}								
				dao.save(contenedor);
				ContenedorResponse response = mapper.map(optional.get(), ContenedorResponse.class);
				mappearDatos(contenedor, response);
				return response;
			} else {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"El contenedor a eliminar no existe");
			}
		} catch (ResponseStatusException e) {
			throw new ResponseStatusException(e.getStatus(), e.getMessage());
		}
	}
	
	private void mappearContenedor(Contenedor actualizado, Contenedor data) {
		actualizado.setFecha_habilitacion(data.getFecha_habilitacion());
		actualizado.setFecha_documentos(data.getFecha_documentos());
		actualizado.setSituacion(data.getSituacion());
		actualizado.setObservaciones(data.getObservaciones());
		actualizado.setDescripcion(data.getDescripcion());
		actualizado.setCliente(data.getCliente());
		actualizado.setImportadora(data.getImportadora());
		actualizado.setEstado(data.getEstado());		
		actualizado.setCodigo(data.getCodigo());
		actualizado.setManifiesto(data.getManifiesto());
		actualizado.setBl(data.getBl());
		actualizado.setFecha_arribo(data.getFecha_arribo());
		actualizado.setFecha_planificacion(data.getFecha_planificacion());
		actualizado.setTamano(data.getTamano());
		actualizado.setPuerto(data.getPuerto());
	}

	private void mappearDatos(Contenedor contenedor, ContenedorResponse index) {
		index.setEdad(calculos.calcularDiasEstadia(contenedor));
		index.setCant_dias_documentos_entregados(calculos.calcularDiasDocumentos(contenedor));
		index.setEstado(calculos.mappearEstadoToText(contenedor));
		index.setEstadia(calculos.determinarEstadia(index));
	}

	private List<ContenedorResponse> llenarLista(List<Contenedor> lista){
		List<ContenedorResponse> response = new ArrayList<ContenedorResponse>();
		lista.stream().forEach(x ->{
			ContenedorResponse index = mapper.map(x, ContenedorResponse.class);
			mappearDatos(x, index);
			response.add(index);
		});	
		return response;
	}
	
	@Override
	public ContenedorResponse extractById(Long id, OperacionRequest date) {
		// TODO Auto-generated method stub
		try {
			Optional<Contenedor> optional = dao.findById(id);
			if (optional.isPresent()) {
				Contenedor contenedor = optional.get();
				contenedor.setFecha_extraccion(date.getFecha());
				contenedor.setEstado(EstadoMercancias.EXTRAIDA);
				dao.save(contenedor);
				ContenedorResponse response = mapper.map(contenedor, ContenedorResponse.class);
				mappearDatos(contenedor, response);
				return response;				
			} else {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"El contenedor a extraer no existe");
			}
		} catch (ResponseStatusException e) {
			throw new ResponseStatusException(e.getStatus(), e.getMessage());
		}
	}

	@Override
	public ContenedorResponse devolverById(Long id, OperacionRequest date) {
		// TODO Auto-generated method stub
		try {
			Optional<Contenedor> optional = dao.findById(id);
			if (optional.isPresent()) {
				Contenedor contenedor = optional.get();
				contenedor.setFecha_devolucion(date.getFecha());
				contenedor.setEstado(EstadoMercancias.DEVUELTA);
				dao.save(contenedor);
				ContenedorResponse response = mapper.map(contenedor, ContenedorResponse.class);
				mappearDatos(contenedor, response);
				return response;				
			} else {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"El contenedor a devover no existe");
			}
		} catch (ResponseStatusException e) {
			throw new ResponseStatusException(e.getStatus(), e.getMessage());
		}
	}

	@Override
	public ContenedorResponse revertById(Long id) {
		// TODO Auto-generated method stub
		try {
			Optional<Contenedor> optional = dao.findById(id);
			if (optional.isPresent()) {
				Contenedor contenedor = optional.get();
				switch (contenedor.getEstado()) {
				case DEVUELTA:
					contenedor.setEstado(EstadoMercancias.EXTRAIDA);
					contenedor.setFecha_devolucion(null);
					break;
				case EXTRAIDA:
					contenedor.setEstado(EstadoMercancias.LISTO_PARA_EXTRAER);
					contenedor.setFecha_extraccion(null);
				default:
					break;
				}
				dao.save(contenedor);
				ContenedorResponse response = mapper.map(contenedor, ContenedorResponse.class);
				mappearDatos(contenedor, response);
				return response;				
			}else {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"El contenedor a devover no existe");
			}
		} catch (ResponseStatusException e) {
			throw new ResponseStatusException(e.getStatus(), e.getMessage());
		}
	}

	@Override
	public ContenedorResponse deleteById(Long id) {
		// TODO Auto-generated method stub
		try {
			Optional<Contenedor> contenedorOptional = dao.findById(id);
			if (contenedorOptional.isPresent()) {
				Contenedor contenedor = contenedorOptional.get();				
				dao.deleteById(id);
				ContenedorResponse response = mapper.map(contenedor, ContenedorResponse.class);
				mappearDatos(contenedor, response);
				return response;
			} else {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND,"El contenedor a eliminar no existe");
			}
		} catch (ResponseStatusException e) {
			throw new ResponseStatusException(e.getStatus(), e.getMessage());
		}
	}

	@Override
	public List<ContenedorResponse> listarPorEstados(List<EstadoMercancias> estados) {
		try {
			List<Contenedor> contenedores = dao.findByEstadoIn(estados);
			if (contenedores.size()>0) {
				return llenarLista(contenedores.stream().collect(Collectors.toList()));
			} else {
				throw new ResponseStatusException(HttpStatus.NO_CONTENT,"Lista de contenedores no encontrados");
			}
		} catch (ResponseStatusException e) {
			throw new ResponseStatusException(e.getStatus(), e.getMessage());
		}
	}

	@Override
	public Revisions<Integer, Contenedor> findRevisions(Long id) {
		return dao.findRevisions(id);
	}
}
