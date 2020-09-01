package com.geasp.micro.contenedores.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.geasp.micro.contenedores.models.Contenedor;
import com.geasp.micro.contenedores.models.EstadoMercancias;

public interface ContenedorRepository extends PagingAndSortingRepository<Contenedor, Long>,JpaRepository<Contenedor, Long>{
	
	List<Contenedor> findByEstado(EstadoMercancias estado);
}
