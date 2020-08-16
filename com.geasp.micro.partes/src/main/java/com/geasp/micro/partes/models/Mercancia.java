package com.geasp.micro.partes.models;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

public class Mercancia {
	
	private Long id;
	private LocalDate fecha_arribo;	
	private LocalDate fecha_habilitacion;
	private LocalDate fecha_documentos;
	private String situacion;
	private String observaciones;
	private String descripcion;
	private String cliente;
	private String importadora;
	public String estado;
	
	/*CONTRUCTORES*/
	public Mercancia() {
		super();
	}
	
	public Mercancia(Long id, LocalDate fecha_arribo, LocalDate fecha_habilitacion, LocalDate fecha_documentos,
			String situacion, String observaciones, @NotNull String descripcion, @NotNull String cliente,
			@NotNull String importadora, @NotNull String estado) {
		super();
		this.id = id;
		this.fecha_arribo = fecha_arribo;
		this.fecha_habilitacion = fecha_habilitacion;
		this.fecha_documentos = fecha_documentos;
		this.situacion = situacion;
		this.observaciones = observaciones;
		this.descripcion = descripcion;
		this.cliente = cliente;
		this.importadora = importadora;
	}
	
	/*GETTERS AND SETTERS*/
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getFecha_arribo() {
		return fecha_arribo;
	}

	public void setFecha_arribo(LocalDate fecha_arribo) {
		this.fecha_arribo = fecha_arribo;
	}

	public LocalDate getFecha_habilitacion() {
		return fecha_habilitacion;
	}

	public void setFecha_habilitacion(LocalDate fecha_habilitacion) {
		this.fecha_habilitacion = fecha_habilitacion;
	}

	public LocalDate getFecha_documentos() {
		return fecha_documentos;
	}

	public void setFecha_documentos(LocalDate fecha_documentos) {
		this.fecha_documentos = fecha_documentos;
	}

	public String getSituacion() {
		return situacion;
	}

	public void setSituacion(String situacion) {
		this.situacion = situacion;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public String getImportadora() {
		return importadora;
	}

	public void setImportadora(String importadora) {
		this.importadora = importadora;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	

}
