package com.geasp.micro.contenedores.services;

import java.util.List;

import org.springframework.data.history.Revisions;

import com.geasp.micro.contenedores.models.Contenedor;
import com.geasp.micro.contenedores.models.EstadoMercancias;
import com.geasp.micro.contenedores.requests.OperacionRequest;


//T Plantilla para los Responses
//R Plantilla para los Request
public interface IContenedorService<T,R> {
	
	public T save(R entity);
	public List<T> listar();
	public List<T> listarPorEstado(EstadoMercancias estado);
	public List<T> listarPorEstados(List<EstadoMercancias> estados);
	public T updateById(R request, Long id);
	public T desactivateById(Long id);
	public T deleteById(Long id);
	public T viewById(Long id);
	
	public T extractById(Long id, OperacionRequest date);
	public T devolverById(Long id, OperacionRequest date);
	public T revertById(Long id);
	
	public Revisions<Integer, Contenedor> findRevisions(Long id);
}
