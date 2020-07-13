package com.geasp.micro.empresas.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.geasp.micro.empresas.models.Importadora;

@Repository
public interface ImportadorasRepository extends MongoRepository<Importadora, String>{

}
