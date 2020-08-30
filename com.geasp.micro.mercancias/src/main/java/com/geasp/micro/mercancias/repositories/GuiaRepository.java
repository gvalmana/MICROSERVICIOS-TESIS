package com.geasp.micro.mercancias.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.geasp.micro.mercancias.models.EstadoMercancias;
import com.geasp.micro.mercancias.models.Guia;

public interface GuiaRepository extends PagingAndSortingRepository<Guia, Long>, JpaRepository<Guia, Long>{
	Page<Guia> findByEstado(EstadoMercancias estado,Pageable pageable);
	List<Guia> findByEstado(EstadoMercancias estado);
}
