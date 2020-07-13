package com.geasp.micro.operaciones.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.geasp.micro.operaciones.models.Extraccion;

@Repository
public interface ExtraccionRepository extends JpaRepository<Extraccion, Long> {
	Optional<Extraccion> findByMercanciaId(Long mercanciaId);
	List<Extraccion> findByFechaAndActivo(LocalDate fecha, boolean activo);
	List<Extraccion> findByFecha(LocalDate fecha);
	List<Extraccion> findByFechaAndTipoMercancia(LocalDate fecha, String tipoMercancia);
}
