package com.geasp.micro.cargas.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.geasp.micro.cargas.models.Carga;
import com.geasp.micro.cargas.models.EstadoMercancias;

public interface CargaRepository extends PagingAndSortingRepository<Carga, Long>, JpaRepository<Carga, Long> {
	
	List<Carga> findByEstado(EstadoMercancias estado);
	List<Carga> findByEstadoIn(List<EstadoMercancias> estados);
}
