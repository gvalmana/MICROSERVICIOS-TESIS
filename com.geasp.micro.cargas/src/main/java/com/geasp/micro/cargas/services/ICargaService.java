package com.geasp.micro.cargas.services;

import java.time.LocalDate;
import java.util.List;

import com.geasp.micro.cargas.models.EstadoMercancias;
import com.geasp.micro.cargas.requets.OperacionRequest;

//T Plantilla para los Responses
//R Plantilla para los Request
public interface ICargaService<T,R> {
	
	public T save(R entity);
	public List<T> listar();
	public List<T> listarPorFechaArribo(LocalDate date);
	public List<T> listarPorEstado(EstadoMercancias estado);
	public List<T> listarPorEstados(List<EstadoMercancias> estados);
	public T updateById(R request, Long id);
	public T desactivateById(Long id);
	public T deleteById(Long id);
	public T viewById(Long id);
	
	public T extractById(Long id, OperacionRequest date);
	public T revertById(Long id);	
}
