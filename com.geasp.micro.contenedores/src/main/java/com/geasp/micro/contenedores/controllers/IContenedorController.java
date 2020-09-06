package com.geasp.micro.contenedores.controllers;

import java.util.List;

import org.springframework.data.history.Revisions;
import org.springframework.http.ResponseEntity;

import com.geasp.micro.contenedores.models.Contenedor;
import com.geasp.micro.contenedores.models.EstadoMercancias;
import com.geasp.micro.contenedores.requests.OperacionRequest;


//T Plantilla para los Responses
//R Plantilla para los Request
public interface IContenedorController<T,R> {

	public ResponseEntity<T> Save(R entity);
	public ResponseEntity<List<T>> getAll();
	public ResponseEntity<List<T>> getAllByState(EstadoMercancias estado);
	public ResponseEntity<List<T>> getAllByStates(List<EstadoMercancias> estado);
	public ResponseEntity<T> getById(Long id);
	public ResponseEntity<T> updateById(R data, Long id);
	public ResponseEntity<T> desactivateById(Long id);
	public ResponseEntity<T> deleteById(Long id);
		
	public ResponseEntity<T> extractById(Long id, OperacionRequest date);
	public ResponseEntity<T> devolverById(Long id, OperacionRequest date);
	public ResponseEntity<T> revertById(Long id);
	public ResponseEntity<Revisions<Integer, Contenedor>> findRevisions(Long id);
}
