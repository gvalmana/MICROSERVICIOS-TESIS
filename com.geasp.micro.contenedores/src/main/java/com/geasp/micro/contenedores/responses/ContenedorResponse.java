package com.geasp.micro.contenedores.responses;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Calendar;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ContenedorResponse implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 761093564087142907L;
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
	
	private int edad;
	private String codigo;	
	private String manifiesto;	
	private String bl;
	@JsonFormat(pattern="yyyy-MM-dd") 
	private LocalDate fecha_planificacion;
	private int tamano;	
	private String puerto;
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDate fecha_extraccion;
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDate fecha_devolucion;	
	public ContenedorResponse() {
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

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
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

	public LocalDate getFecha_extraccion() {
		return fecha_extraccion;
	}

	public void setFecha_extraccion(LocalDate fecha_extraccion) {
		this.fecha_extraccion = fecha_extraccion;
	}

	public LocalDate getFecha_devolucion() {
		return fecha_devolucion;
	}

	public void setFecha_devolucion(LocalDate fecha_devolucion) {
		this.fecha_devolucion = fecha_devolucion;
	}
	
}
