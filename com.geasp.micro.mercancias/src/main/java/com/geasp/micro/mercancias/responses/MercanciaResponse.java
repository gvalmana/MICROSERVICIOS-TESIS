package com.geasp.micro.mercancias.responses;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Calendar;

import com.fasterxml.jackson.annotation.JsonFormat;

public class MercanciaResponse implements Serializable {
	
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 4206684771920268230L;
	
	protected String createdBy;
    protected String lastModifiedBy;
    
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    protected Calendar creationDate;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    protected Calendar lastModifiedDate;
	
	private Long id;
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDate fecha_habilitacion;
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDate fecha_documentos;
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDate fecha_arribo;
	private String situacion;
	private String observaciones;
	private String descripcion;
	private String cliente;
	private String importadora;	
	private String estado;
	private int cant_dias_documentos_entregados;
	
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
	private String codigo_contenedor;
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
	
	public MercanciaResponse() {
		super();
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

	public int getCant_dias_documentos_entregados() {
		return cant_dias_documentos_entregados;
	}

	public void setCant_dias_documentos_entregados(int cant_dias_documentos_entregados) {
		this.cant_dias_documentos_entregados = cant_dias_documentos_entregados;
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
	
}
