package com.geasp.micro.mercancias.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.geasp.micro.mercancias.models.Mercancia;

@Repository
public interface MercanciaRepository extends JpaRepository<Mercancia, Long> {
	
}
