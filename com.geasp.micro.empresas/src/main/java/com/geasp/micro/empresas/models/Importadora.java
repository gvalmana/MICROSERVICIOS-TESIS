package com.geasp.micro.empresas.models;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "importadoras")
public class Importadora extends Item {

	private String nombre;
	
	public Importadora() {
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
