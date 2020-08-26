package com.geasp.micro.empresas.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.geasp.micro.empresas.models.Cliente;

public interface ClientesRepository extends MongoRepository<Cliente, String>{

}
