package com.geasp.micro.contenedores.requests;

import java.io.Serializable;
import java.time.LocalDate;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ContenedorRequest2 extends ContenedorRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3668871460123442168L;
	
	/* DATOS DEL CONTENEDOR */
	@NotNull
	@Size(min = 10, max = 15, message = "El código debe medir entre 11 y 15")
	private String codigo;
	@NotNull(message = "El manifiesto es obligatorio")
	private String manifiesto;
	private String bl;
	private LocalDate fecha_planificacion;
	@NotNull(message = "El tamaño del contenedor es obligatorio")
	private int tamano;
	@NotNull
	private String puerto;

	public ContenedorRequest2() {
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

}
