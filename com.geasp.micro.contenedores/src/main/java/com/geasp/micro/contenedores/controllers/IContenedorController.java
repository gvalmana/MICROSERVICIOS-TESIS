package com.geasp.micro.contenedores.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.geasp.micro.contenedores.models.EstadoMercancias;


//T Plantilla para los Responses
//R Plantilla para los Request
public interface IContenedorController<T,R> {

	public ResponseEntity<T> Save(R entity);
	public ResponseEntity<List<T>> getAll();
	public ResponseEntity<T> getById(Long id);
	public ResponseEntity<T> updateById(R data, Long id);
	public ResponseEntity<T> desactivateById(Long id);
	public ResponseEntity<List<T>> getAllByState(EstadoMercancias estado);
}
