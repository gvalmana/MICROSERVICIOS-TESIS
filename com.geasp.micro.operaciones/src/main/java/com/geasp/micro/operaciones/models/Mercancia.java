package com.geasp.micro.operaciones.models;

import java.time.LocalDate;
import java.util.Calendar;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Mercancia {

	@JsonFormat(pattern="yyyy-MM-dd")
    protected Calendar creationDate;
	@JsonFormat(pattern="yyyy-MM-dd")
    protected Calendar lastModifiedDate;
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDate fecha_arribo;
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDate fecha_habilitacion;
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDate fecha_documentos;
	private String situacion;
	private String observaciones;
	private String descripcion;
	private String cliente;
	private String importadora;
	private String estado;
	
	private String codigo;	
	private String manifiesto;	
	private String bl;
	@JsonFormat(pattern="yyyy-MM-dd") 
	private LocalDate fecha_planificacion;
	private int edad;	
	private String estadia;	
	private int tamano;	
	private String puerto;	
	
	private String dm;
	private String contenedor;
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDate fecha_desagrupe;
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDate fecha_descarga;
	private int cantidad_dias_desagrupada;
	
	private int cantidad_de_bultos;
	private Float peso;
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDate fecha_entrega;
	private int dias_declarada;
	private int dias_entregada;
	
	private String tipo_mercancia;
	private Long id;
	
	public Mercancia() {
		super();
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

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public String getEstadia() {
		return estadia;
	}

	public void setEstadia(String estadia) {
		this.estadia = estadia;
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

	public String getDm() {
		return dm;
	}

	public void setDm(String dm) {
		this.dm = dm;
	}

	public String getContenedor() {
		return contenedor;
	}

	public void setContenedor(String contenedor) {
		this.contenedor = contenedor;
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

	public int getCantidad_de_bultos() {
		return cantidad_de_bultos;
	}

	public void setCantidad_de_bultos(int cantidad_de_bultos) {
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

	public String getTipo_mercancia() {
		return tipo_mercancia;
	}

	public void setTipo_mercancia(String tipo_mercancia) {
		this.tipo_mercancia = tipo_mercancia;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}
