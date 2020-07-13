package com.geasp.micro.mercancias.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;

import com.geasp.micro.mercancias.models.EstadoMercancias;
import com.geasp.micro.mercancias.models.Guia;

public interface GuiaRepository extends RevisionRepository<Guia, Long, Long>, JpaRepository<Guia, Long>{
	List<Guia> findByEstado(EstadoMercancias estado);
	List<Guia> findByClienteAndEstado(String cliente, EstadoMercancias estado);
}
