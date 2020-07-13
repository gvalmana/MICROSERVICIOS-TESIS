package com.geasp.micro.mercancias.requests;

import java.io.Serializable;
import java.time.LocalDate;

public class GuiaRequest extends MercanciaRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3668871460123442168L;
	
	private String dm;
	private Long cantidad_de_bultos;
	private Float peso;
	private LocalDate fecha_entrega;
	
	public GuiaRequest() {
		super();
	}

	public String getDm() {
		return dm;
	}

	public void setDm(String dm) {
		this.dm = dm;
	}

	public Long getCantidad_de_bultos() {
		return cantidad_de_bultos;
	}

	public void setCantidad_de_bultos(Long cantidad_de_bultos) {
		this.cantidad_de_bultos = cantidad_de_bultos;
	}

	public Float getPeso() {
		return peso;
	}

	public void setPeso(Float peso) {
		this.peso = peso;
	}

	public LocalDate getFecha_entrega() {
		return fecha_entrega;
	}

	public void setFecha_entrega(LocalDate fecha_entrega) {
		this.fecha_entrega = fecha_entrega;
	}
}
