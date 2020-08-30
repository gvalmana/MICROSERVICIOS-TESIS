package com.geasp.micro.guias.responses;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Calendar;

import com.fasterxml.jackson.annotation.JsonFormat;

public class GuiaResponse implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2086001280170513689L;
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
	private String dm;
	private Long cantidad_de_bultos;
	private Float peso;
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDate fecha_entrega;
	private int dias_declarada;
	private int dias_entregada;
	public GuiaResponse() {
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
