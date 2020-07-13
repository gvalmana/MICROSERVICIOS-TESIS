package com.geasp.micro.mercancias.models;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.envers.Audited;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "tbl_contenedores", catalog = "bd_mercancias")
@PrimaryKeyJoinColumn(name = "id")
@Audited
public class Contenedor extends Mercancia{
	
	@Column(name = "fd_codigo")
	@NotNull
	private String codigo;
	
	@Column(name = "fd_manifiesto")
	@NotNull
	private String manifiesto;
	
	@Column(name = "fd_bl")
	private String bl;
	@JsonFormat(pattern="yyyy-MM-dd")
	@Column(name = "fd_fecha_planificacion")
	private LocalDate fecha_planificacion;
	
	@Column(name = "fd_tamano")
	@NotNull
	private int tamano;
	
	@Column(name = "fd_puerto")
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
