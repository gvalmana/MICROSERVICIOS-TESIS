package com.geasp.micro.mercancias.requests;

import java.io.Serializable;
import java.time.LocalDate;

import javax.validation.constraints.NotNull;

public class CargaRequest extends MercanciaRequest implements Serializable {
	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -6107269072838559482L;
	
	/*DATOS DE LA CARGA*/
	private String manifiesto;
	private String dm;
	private String bl;
	private String codigo_contenedor;
	private LocalDate fecha_desagrupe;
	private LocalDate fecha_descarga;
	@NotNull
	private String puerto;	
	
	public CargaRequest() {
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

	public String getPuerto() {
		return puerto;
	}

	public void setPuerto(String puerto) {
		this.puerto = puerto;
	}
}
