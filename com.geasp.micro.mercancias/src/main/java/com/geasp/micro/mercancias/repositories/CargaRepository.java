package com.geasp.micro.mercancias.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;

import com.geasp.micro.mercancias.models.Carga;
import com.geasp.micro.mercancias.models.EstadoMercancias;

public interface CargaRepository extends RevisionRepository<Carga, Long, Long>, JpaRepository<Carga, Long> {
	
	List<Carga> findByEstado(EstadoMercancias estado);
	List<Carga> findByClienteAndEstado(String cliente, EstadoMercancias estado);
}
