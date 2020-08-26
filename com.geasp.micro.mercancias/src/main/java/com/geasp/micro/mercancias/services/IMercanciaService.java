package com.geasp.micro.mercancias.services;

import java.time.LocalDate;
import java.util.List;

import com.geasp.micro.mercancias.models.EstadoMercancias;

//T Plantilla para los Responses
//R Plantilla para los Request
public interface IMercanciaService<T,R> {
	
	public T save(R entity);
	public List<T> listar(Integer pageNo, Integer pageSize, String sortBy);
	public List<T> listarPorFechaArribo(LocalDate date);
	public List<T> listarPorEstado(EstadoMercancias estado, Integer pageNo, Integer pageSize, String sortBy);
	public T updateById(R request, Long id);
	public T desactivateById(Long id);
	public T viewById(Long id);
}
