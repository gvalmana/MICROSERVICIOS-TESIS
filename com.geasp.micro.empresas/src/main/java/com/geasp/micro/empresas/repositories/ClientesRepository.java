package com.geasp.micro.empresas.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.geasp.micro.empresas.models.Cliente;

@Repository
public interface ClientesRepository extends MongoRepository<Cliente, String>{

}
