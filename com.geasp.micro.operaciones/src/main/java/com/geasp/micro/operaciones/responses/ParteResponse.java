package com.geasp.micro.operaciones.responses;

import java.io.Serializable;

import com.geasp.micro.operaciones.models.Operaciones;

public class ParteResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3667982700720952572L;
	
	Operaciones contenedores;
	Operaciones cargas;
	Operaciones guias;
	
	public ParteResponse() {
		super();
		// TODO Auto-generated constructor stub
		this.contenedores = new Operaciones();
		this.cargas = new Operaciones();
		this.guias = new Operaciones();
	}
	
	public ParteResponse(Operaciones contenedores, Operaciones cargas, Operaciones guias) {
		super();
		this.contenedores = contenedores;
		this.cargas = cargas;
		this.guias = guias;
	}

	public Operaciones getContenedores() {
		return contenedores;
	}
	public void setContenedores(Operaciones contenedores) {
		this.contenedores = contenedores;
	}
	public Operaciones getCargas() {
		return cargas;
	}
	public void setCargas(Operaciones cargas) {
		this.cargas = cargas;
	}
	public Operaciones getGuias() {
		return guias;
	}
	public void setGuias(Operaciones guias) {
		this.guias = guias;
	}
	
}
