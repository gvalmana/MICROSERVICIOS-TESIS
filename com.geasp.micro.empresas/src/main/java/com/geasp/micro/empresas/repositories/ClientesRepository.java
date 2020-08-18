package com.geasp.micro.empresas.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.geasp.micro.empresas.models.Cliente;

@RepositoryRestResource(collectionResourceRel = "clientes", path = "clientes")
public interface ClientesRepository extends MongoRepository<Cliente, String>{

}
