package com.geasp.micro.mercancias.services;

public interface IOperacionesService<T> {

	public T extractById(Long id);
	public T devolverById(Long id);
	public T revertById(Long id);
	public T viewById(Long id);
	
}
