package com.geasp.micro.mercancias.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.geasp.micro.mercancias.models.Carga;
import com.geasp.micro.mercancias.models.EstadoMercancias;

public interface CargaRepository extends JpaRepository<Carga, Long> {
	
	Page<Carga> findByEstado(EstadoMercancias estado, Pageable pageable);
	List<Carga> findByEstado(EstadoMercancias estado);
}
