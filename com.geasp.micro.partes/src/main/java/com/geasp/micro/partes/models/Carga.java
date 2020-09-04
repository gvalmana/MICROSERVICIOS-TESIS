package com.geasp.micro.partes.models;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Calendar;
import com.fasterxml.jackson.annotation.JsonFormat;

public class Carga implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3267440244122549029L;
	protected String createdBy;
    protected String lastModifiedBy;
    
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    protected Calendar creationDate;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    protected Calendar lastModifiedDate;
	
	private Long id;
	private String estado;
	private String estadia;	
	private String situacion;
	private String observaciones;
	private String descripcion;
	private String cliente;
	private String importadora;
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDate fecha_arribo;
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDate fecha_habilitacion;
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDate fecha_documentos;
	private int cant_dias_documentos_entregados;
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
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDate fecha_extraccion;	
	public Carga() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public Calendar getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Calendar creationDate) {
		this.creationDate = creationDate;
	}

	public Calendar getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Calendar lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getEstadia() {
		return estadia;
	}

	public void setEstadia(String estadia) {
		this.estadia = estadia;
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

	public int getCant_dias_documentos_entregados() {
		return cant_dias_documentos_entregados;
	}

	public void setCant_dias_documentos_entregados(int cant_dias_documentos_entregados) {
		this.cant_dias_documentos_entregados = cant_dias_documentos_entregados;
	}

	public LocalDate getFecha_documentos() {
		return fecha_documentos;
	}

	public void setFecha_documentos(LocalDate fecha_documentos) {
		this.fecha_documentos = fecha_documentos;
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

	public LocalDate getFecha_extraccion() {
		return fecha_extraccion;
	}

	public void setFecha_extraccion(LocalDate fecha_extraccion) {
		this.fecha_extraccion = fecha_extraccion;
	}
	
}
