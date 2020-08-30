package com.geasp.micro.contenedores.requests;

import java.io.Serializable;
import java.time.LocalDate;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.geasp.micro.contenedores.models.EstadoMercancias;

public class ContenedorRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5390039777647365806L;
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
	public EstadoMercancias getEstado() {
		return estado;
	}
	public void setEstado(EstadoMercancias estado) {
		this.estado = estado;
	}
	public LocalDate getFecha_arribo() {
		return fecha_arribo;
	}
	public void setFecha_arribo(LocalDate fecha_arribo) {
		this.fecha_arribo = fecha_arribo;
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
