package com.geasp.micro.operaciones.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.geasp.micro.operaciones.models.Devolucion;
import com.geasp.micro.operaciones.models.Extraccion;

@Repository
public interface DevolucionRepository extends JpaRepository<Devolucion, Long> {
	Optional<Devolucion> findByExtraccion(Extraccion extraccion);
	List<Devolucion> findByFechaAndActivo(LocalDate fecha_devolucion,boolean activo);
	List<Devolucion> findByFecha(LocalDate fecha_devolucion);
}
