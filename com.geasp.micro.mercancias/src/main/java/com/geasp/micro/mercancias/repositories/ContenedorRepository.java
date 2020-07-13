package com.geasp.micro.mercancias.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

import com.geasp.micro.mercancias.models.Contenedor;
import com.geasp.micro.mercancias.models.EstadoMercancias;

@Repository
public interface ContenedorRepository extends RevisionRepository<Contenedor, Long, Long>,JpaRepository<Contenedor, Long>{
	
	List<Contenedor> findByEstado(EstadoMercancias estado);
	List<Contenedor> findByClienteAndEstado(String cliente, EstadoMercancias estado);
}
