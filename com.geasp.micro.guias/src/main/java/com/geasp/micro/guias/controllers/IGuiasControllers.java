package com.geasp.micro.guias.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import com.geasp.micro.guias.models.EstadoMercancias;
import com.geasp.micro.guias.requests.OperacionRequest;

//T Plantilla para los Responses
//R Plantilla para los Request
public interface IGuiasControllers<T,R> {

	public ResponseEntity<T> Save(R entity);
	public ResponseEntity<List<T>> getAll();
	public ResponseEntity<List<T>> getAllByState(EstadoMercancias estado);
	public ResponseEntity<List<T>> getAllByStates(List<EstadoMercancias> estado);
	public ResponseEntity<T> getById(Long id);
	public ResponseEntity<T> updateById(R data, Long id);
	public ResponseEntity<T> desactivateById(Long id);
	public ResponseEntity<T> deleteById(Long id);
	
	public ResponseEntity<T> extractById(Long id, OperacionRequest date);
	public ResponseEntity<T> revertById(Long id);
}
