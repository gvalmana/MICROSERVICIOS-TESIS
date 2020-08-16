package com.geasp.micro.partes.models;

import java.time.LocalDate;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Contenedor extends Mercancia{
	
	@NotNull
	private String codigo;
	
	@NotNull
	private String manifiesto;
	
	private String bl;
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDate fecha_planificacion;
	
	@NotNull
	private int tamano;
	
	@NotNull
	private String puerto;
	
	/*CONSTRUCTORES*/
	public Contenedor() {
		super();
	}

	/*GETTERS AND SETTERS*/
	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getManifiesto() {
		return manifiesto;
	}

	public void setManifiesto(String manifiesto) {
		this.manifiesto = manifiesto;
	}

	public String getBl() {
		return bl;
	}

	public void setBl(String bl) {
		this.bl = bl;
	}
	
	public int getTamano() {
		return tamano;
	}

	public void setTamano(int tamano) {
		this.tamano = tamano;
	}

	public String getPuerto() {
		return puerto;
	}

	public void setPuerto(String puerto) {
		this.puerto = puerto;
	}

	public LocalDate getFecha_planificacion() {
		return fecha_planificacion;
	}

	public void setFecha_planificacion(LocalDate fecha_planificacion) {
		this.fecha_planificacion = fecha_planificacion;
	}
	
}
