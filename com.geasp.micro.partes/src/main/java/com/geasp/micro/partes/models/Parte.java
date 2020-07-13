package com.geasp.micro.partes.models;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

public class Parte implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1388095962890564740L;
	private LocalDate fecha;
	private LocalTime hora_cierre;
	private String titulo;
	
	private ResumenContenedores contenedores;
	private ResumenCargas cargas;
	private ResumenGuias guias;
	
	public Parte() {
		super();
		// TODO Auto-generated constructor stub
		this.contenedores = new ResumenContenedores();
		this.cargas = new ResumenCargas();
		this.guias = new ResumenGuias();
	}
	
	public Parte(String titulo) {
		super();
		this.titulo = titulo;
	}

	public Parte(LocalDate fecha, LocalTime hora_cierre, String titulo) {
		super();
		this.fecha = fecha;
		this.hora_cierre = hora_cierre;
		this.titulo = titulo;
		this.contenedores = new ResumenContenedores();
		this.cargas = new ResumenCargas();
		this.guias = new ResumenGuias();		
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public ResumenContenedores getContenedores() {
		return contenedores;
	}

	public void setContenedores(ResumenContenedores contenedores) {
		this.contenedores = contenedores;
	}

	public ResumenCargas getCargas() {
		return cargas;
	}

	public void setCargas(ResumenCargas cargas) {
		this.cargas = cargas;
	}

	public ResumenGuias getGuias() {
		return guias;
	}

	public void setGuias(ResumenGuias guias) {
		this.guias = guias;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public LocalTime getHora_cierre() {
		return hora_cierre;
	}

	public void setHora_cierre(LocalTime hora_cierre) {
		this.hora_cierre = hora_cierre;
	}
	
}
