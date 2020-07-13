package com.geasp.micro.operaciones.resources;

import java.util.List;

import org.springframework.http.ResponseEntity;

//T Plantilla para los Responses
//R Plantilla para los Request
public interface IControllers<T,R> {

	public ResponseEntity<T> Save(R entity, Long id);
	public ResponseEntity<List<T>> getAll();
	public ResponseEntity<T> getById(Long id);
	public ResponseEntity<T> updateById(R data, Long id);
	public ResponseEntity<T> revertById(Long id);
	public ResponseEntity<T> activateById(Long id);
}
