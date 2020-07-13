package com.geasp.micro.partes.models;

public class ResumenExtracciones {

	Operaciones contenedores;
	Operaciones cargas;
	Operaciones guias;
	
	public ResumenExtracciones() {
		super();
		// TODO Auto-generated constructor stub
		this.contenedores = new Operaciones();
		this.cargas = new Operaciones();
		this.guias = new Operaciones();
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
