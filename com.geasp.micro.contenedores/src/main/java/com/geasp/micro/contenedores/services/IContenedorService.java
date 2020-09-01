package com.geasp.micro.contenedores.services;

import java.time.LocalDate;
import java.util.List;

import com.geasp.micro.contenedores.models.EstadoMercancias;
import com.geasp.micro.contenedores.requests.OperacionRequest;


//T Plantilla para los Responses
//R Plantilla para los Request
public interface IContenedorService<T,R> {
	
	public T save(R entity);
	public List<T> listar();
	public List<T> listarPorFechaArribo(LocalDate date);
	public List<T> listarPorEstado(EstadoMercancias estado);
	public T updateById(R request, Long id);
	public T desactivateById(Long id);
	public T deleteById(Long id);
	public T viewById(Long id);
	
	public T extractById(Long id, OperacionRequest date);
	public T devolverById(Long id, OperacionRequest date);
	public T revertById(Long id);	
}
