package com.geasp.micro.mercancias.responses;

import java.io.Serializable;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

public class CargaResponse extends Response implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2127163249275481079L;
	
	private String manifiesto;
	private String dm;
	private String bl;
	private String codigo_contenedor;
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDate fecha_desagrupe;
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDate fecha_descarga;
	private int cantidad_dias_desagrupada;
	private String puerto;
	
	public CargaResponse() {
		super();
	}

	public String getManifiesto() {
		return manifiesto;
	}

	public void setManifiesto(String manifiesto) {
		this.manifiesto = manifiesto;
	}

	public String getDm() {
		return dm;
	}

	public void setDm(String dm) {
		this.dm = dm;
	}

	public String getBl() {
		return bl;
	}

	public void setBl(String bl) {
		this.bl = bl;
	}

	public String getCodigo_contenedor() {
		return codigo_contenedor;
	}

	public void setCodigo_contenedor(String codigo_contenedor) {
		this.codigo_contenedor = codigo_contenedor;
	}
	
	public LocalDate getFecha_desagrupe() {
		return fecha_desagrupe;
	}

	public void setFecha_desagrupe(LocalDate fecha_desagrupe) {
		this.fecha_desagrupe = fecha_desagrupe;
	}

	public LocalDate getFecha_descarga() {
		return fecha_descarga;
	}

	public void setFecha_descarga(LocalDate fecha_descarga) {
		this.fecha_descarga = fecha_descarga;
	}
	
	public int getCantidad_dias_desagrupada() {
		return cantidad_dias_desagrupada;
	}

	public void setCantidad_dias_desagrupada(int cantidad_dias_desagrupada) {
		this.cantidad_dias_desagrupada = cantidad_dias_desagrupada;
	}

	public String getPuerto() {
		return puerto;
	}

	public void setPuerto(String puerto) {
		this.puerto = puerto;
	}
	
}
