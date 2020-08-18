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
			data.setNombre(entity.getNombre());
			data.setDireccion(entity.getDireccion());
			data.setReeup(entity.getReeup());
			data.setNit(entity.getNit());
			data.setBanco(entity.getBanco());
			data.setSucursal(entity.getSucursal());
			data.setCuc(entity.getCuc());
			data.setCup(entity.getCup());
			data.setRepresentante(entity.getRepresentante());
			data.setFecha_resolucion(entity.getFecha_resolucion());
			data.setResolucion_emisor(entity.getResolucion_emisor());
			data.setResolucion_representante(entity.getResolucion_representante());
			data.setActividad(entity.getActividad());
			return dao.save(data);			
		}else {
			return null;
		}
	}
}
