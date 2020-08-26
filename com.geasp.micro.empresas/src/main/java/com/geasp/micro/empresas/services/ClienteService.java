package com.geasp.micro.empresas.services;

import java.util.List;

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
	
	public Cliente updateById(Cliente entity, String id) {
		Cliente data = dao.findById(id).get();
		mapear(entity, data);
		dao.save(data);
		return data;
	}

	private void mapear(Cliente entity, Cliente data) {
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
		data.setResolucion_representante(entity.getResolucion_representante());
		data.setResolucion_emisor(entity.getResolucion_emisor());
		data.setActividad(entity.getActividad());
		data.setSubordinada(entity.isSubordinada());
	}
}
