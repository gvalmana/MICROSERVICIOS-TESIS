package com.geasp.micro.empresas.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.geasp.micro.empresas.models.Importadora;
import com.geasp.micro.empresas.repositories.ImportadorasRepository;

@Service
public class ImportadoraService {

	@Autowired
	private ImportadorasRepository dao;
	
	public List<Importadora> listar(){
		return dao.findAll();
	}
	
	public Importadora viewById(String id) {
		return dao.findById(id).get();
	}
	
	public Importadora Save(Importadora entity) {
		return dao.save(entity);
	}
	
	public Importadora updateById(String id, Importadora entity) {
		Optional<Importadora> optional = dao.findById(id);
		if (optional.isPresent()) {
			Importadora data = optional.get();
			data.setNombre(entity.getNombre());
			return dao.save(data);
		} else {
			return null;
		}
	}
	
	public Importadora deleteById(String id) {
		Optional<Importadora> optional = dao.findById(id);
		if (optional.isPresent()) {
			dao.delete(optional.get());
			return optional.get();
		}
		else {
			return null;
		}
	}
	
}
