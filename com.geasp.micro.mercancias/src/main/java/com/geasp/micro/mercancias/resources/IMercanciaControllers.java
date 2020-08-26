package com.geasp.micro.mercancias.resources;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.geasp.micro.mercancias.models.EstadoMercancias;

//T Plantilla para los Responses
//R Plantilla para los Request
public interface IMercanciaControllers<T,R> {

	public ResponseEntity<T> Save(R entity);
	public ResponseEntity<List<T>> getAll(Integer pageNo, Integer pageSize, String sortBy);
	public ResponseEntity<T> getById(Long id);
	public ResponseEntity<T> updateById(R data, Long id);
	public ResponseEntity<T> desactivateById(Long id);
	public ResponseEntity<List<T>> getAllByState(EstadoMercancias estado, Integer pageNo, Integer pageSize, String sortBy);
}
