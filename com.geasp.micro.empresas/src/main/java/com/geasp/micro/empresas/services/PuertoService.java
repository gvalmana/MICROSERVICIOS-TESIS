package com.geasp.micro.empresas.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.geasp.micro.empresas.models.Puerto;
import com.geasp.micro.empresas.repositories.PuertosRepository;

@Service
public class PuertoService {

	@Autowired
	private PuertosRepository dao;
	
	public List<Puerto> listar(){
		return dao.findAll();
	}
	
	public Puerto viewById(String id) {
		return dao.findById(id).get();
	}
	
	public Puerto Save(Puerto entity) {
		return dao.save(entity);
	}
	
	public Puerto updateById(String id, Puerto entity) {
		Optional<Puerto> optional = dao.findById(id);
		if (optional.isPresent()) {
			Puerto data = optional.get();
			data.setNombre(entity.getNombre());
			return dao.save(data);
		} else {
			return null;
		}
	}
	
	public Puerto deleteById(String id) {
		Optional<Puerto> optional = dao.findById(id);
		if (optional.isPresent()) {
			dao.delete(optional.get());
			return optional.get();
		}
		else {
			return null;
		}
	}
	
}
