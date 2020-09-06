package com.geasp.micro.contenedores.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.history.RevisionRepository;

import com.geasp.micro.contenedores.models.Contenedor;
import com.geasp.micro.contenedores.models.EstadoMercancias;

public interface ContenedorRepository extends RevisionRepository<Contenedor, Long, Integer>, PagingAndSortingRepository<Contenedor, Long>,JpaRepository<Contenedor, Long>{
	
	List<Contenedor> findByEstado(EstadoMercancias estado);
	List<Contenedor> findByEstadoIn(List<EstadoMercancias> estados);
}
