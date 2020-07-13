package com.geasp.micro.mercancias.requests;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import com.geasp.micro.mercancias.models.EstadoMercancias;

public class MercanciaRequest {

	/*DATOS DE LA MERCANCIA*/
	private Long id;
	@NotNull
	private boolean confirmado;
	@NotNull
	private LocalDate fecha_habilitacion;
	@NotNull
	private LocalDate fecha_documentos;
	private String situacion;
	private String observaciones;
	@NotNull
	private String descripcion;
	@NotNull
	private String cliente;
	@NotNull
	private String importadora;
	public EstadoMercancias estado;
	@NotNull
	private LocalDate fecha_arribo;
	
	public MercanciaRequest() {
		super();
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public boolean isConfirmado() {
		return confirmado;
	}
	public void setConfirmado(boolean confirmado) {
		this.confirmado = confirmado;
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
	public EstadoMercancias getEstado() {
		return estado;
	}
	public void setEstado(EstadoMercancias estado) {
		this.estado = estado;
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
	public LocalDate getFecha_arribo() {
		return fecha_arribo;
	}
	public void setFecha_arribo(LocalDate fecha_arribo) {
		this.fecha_arribo = fecha_arribo;
	}
	
}
