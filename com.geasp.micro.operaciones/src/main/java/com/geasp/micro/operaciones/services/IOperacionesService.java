package com.geasp.micro.operaciones.services;

import java.util.List;

public interface IOperacionesService<T,R> {

	public T registrar(R entity, Long id);
	
	public List<T> listar();
	
	public T viewById(Long id);
	
	public T updateById(R request, Long id);
	
	public T deleteById(Long id);
	
	public T activateById(Long id);
	
}
