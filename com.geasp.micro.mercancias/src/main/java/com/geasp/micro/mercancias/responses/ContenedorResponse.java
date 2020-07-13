package com.geasp.micro.mercancias.responses;

import java.io.Serializable;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ContenedorResponse extends Response implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1732620256301595903L;
	private int edad;	
	private String estadia;	
	private String codigo;	
	private String manifiesto;	
	private String bl;
	@JsonFormat(pattern="yyyy-MM-dd") 
	private LocalDate fecha_planificacion;
	private int tamano;	
	private String puerto;
	
	public ContenedorResponse() {
		super();
	}
	
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
	
	public LocalDate getFecha_planificacion() {
		return fecha_planificacion;
	}

	public void setFecha_planificacion(LocalDate fecha_planificacion) {
		this.fecha_planificacion = fecha_planificacion;
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

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public String getEstadia() {
		return estadia;
	}

	public void setEstadia(String estadia) {
		this.estadia = estadia;
	}
	
}
