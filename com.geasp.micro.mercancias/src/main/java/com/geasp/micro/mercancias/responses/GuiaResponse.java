package com.geasp.micro.mercancias.responses;

import java.io.Serializable;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

public class GuiaResponse extends Response implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1757004024820903159L;
	
	private String dm;
	private Long cantidad_de_bultos;
	private Float peso;
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDate fecha_entrega;
	private int dias_declarada;
	private int dias_entregada;
	
	public GuiaResponse() {
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

	public int getDias_declarada() {
		return dias_declarada;
	}

	public void setDias_declarada(int dias_declarada) {
		this.dias_declarada = dias_declarada;
	}

	public int getDias_entregada() {
		return dias_entregada;
	}

	public void setDias_entregada(int dias_entregada) {
		this.dias_entregada = dias_entregada;
	}
	
}
