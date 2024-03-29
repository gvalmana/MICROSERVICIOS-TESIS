package com.geasp.micro.mercancias.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.geasp.micro.mercancias.models.Contenedor;
import com.geasp.micro.mercancias.models.EstadoMercancias;

public interface ContenedorRepository extends PagingAndSortingRepository<Contenedor, Long>, JpaRepository<Contenedor, Long>{
	
	Page<Contenedor> findByEstado(EstadoMercancias estado, Pageable pageable);
	List<Contenedor> findByEstado(EstadoMercancias estado);
}
