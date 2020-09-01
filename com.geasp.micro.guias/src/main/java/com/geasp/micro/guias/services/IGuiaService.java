package com.geasp.micro.guias.services;

import java.time.LocalDate;
import java.util.List;

import com.geasp.micro.guias.models.EstadoMercancias;
import com.geasp.micro.guias.requests.OperacionRequest;

//T Plantilla para los Responses
//R Plantilla para los Request
public interface IGuiaService<T,R> {
	
	public T save(R entity);
	public List<T> listar();
	public List<T> listarPorFechaArribo(LocalDate date);
	public List<T> listarPorEstado(EstadoMercancias estado);
	public T updateById(R request, Long id);
	public T desactivateById(Long id);
	public T deleteById(Long id);
	public T viewById(Long id);
	public T extractById(Long id, OperacionRequest date);
	public T revertById(Long id);	
}
