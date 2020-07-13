package com.geasp.micro.empresas.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.geasp.micro.empresas.models.Cliente;
import com.geasp.micro.empresas.repositories.ClientesRepository;

@Service
public class ClienteService {

	@Autowired
	private ClientesRepository dao;
	
	public List<Cliente> listar(){
		return dao.findAll();
	}
	
	public Cliente viewById(String id) {
		return dao.findById(id).get();
	}
	
	public Cliente Save(Cliente entity) {
		return dao.save(entity);
	}
	
	public Cliente updateById(String id, Cliente entity) {
		Optional<Cliente> optional = dao.findById(id);
		if (optional.isPresent()) {
			Cliente data = optional.get();
			data.setCorreo(entity.getCorreo());
			data.setDireccion(entity.getDireccion());
			data.setNombre(entity.getNombre());
			data.setTelefono(entity.getTelefono());			
			return dao.save(entity);			
		}else {
			return null;
		}
	}
}
