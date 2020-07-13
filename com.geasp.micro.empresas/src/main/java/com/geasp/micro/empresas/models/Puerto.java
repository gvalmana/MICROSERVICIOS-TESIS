package com.geasp.micro.empresas.models;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "puertos")
public class Puerto extends Item {

	private String nombre;
	
	public Puerto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
}
