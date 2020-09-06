package com.geasp.micro.guias.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.geasp.micro.guias.models.EstadoMercancias;
import com.geasp.micro.guias.models.Guia;

public interface GuiaRepository extends PagingAndSortingRepository<Guia, Long>, JpaRepository<Guia, Long>{
	List<Guia> findByEstado(EstadoMercancias estado);
	List<Guia> findByEstadoIn(List<EstadoMercancias> estados);
}
