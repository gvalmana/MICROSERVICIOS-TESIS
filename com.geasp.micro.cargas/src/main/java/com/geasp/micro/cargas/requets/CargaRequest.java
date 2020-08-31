package com.geasp.micro.cargas.requets;

import java.io.Serializable;
import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import com.geasp.micro.cargas.models.EstadoMercancias;

public class CargaRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3139235331100266019L;
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
