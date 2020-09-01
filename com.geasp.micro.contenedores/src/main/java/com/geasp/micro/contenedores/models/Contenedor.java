package com.geasp.micro.contenedores.models;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.envers.Audited;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@Entity
@Table(name = "tbl_contenedores", catalog = "bd_contenedores")
@Audited
@ApiModel("Representa un contenedor como entidad")
public class Contenedor extends Auditable<String>{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;
	
	@Column(name = "fd_fecha_arribo")
	@NotNull
	@ApiModelProperty(value = "Fecha en que arriba una mercancia al país", required = true)
	private LocalDate fecha_arribo;	
	
	@NotNull
	@ApiModelProperty(value = "Fecha en que la mercancia es habilitada por la importadora", required = true)
	@Column(name = "fd_fecha_habilitacion")
	private LocalDate fecha_habilitacion;
	
	@NotNull
	@Column(name = "fd_fecha_documentos")
	@ApiModelProperty(value = "Fecha en que la importadora entrega los documentos al cliente", required = true)
	private LocalDate fecha_documentos;
	
	@ApiModelProperty(value = "Describe una situació en que se encuentre la mercancía")
	@Column(name = "fd_situacion")
	@Lob
	private String situacion;
	
	@Column(name = "fd_observaciones")
	@Lob
	@ApiModelProperty(value = "Describe alguan anotación especifica sobre una mercancia que el operador quiera destacar")
	private String observaciones;
	
	@Column(name = "fd_descripcion")
	@NotNull
	@ApiModelProperty(value = "Es la descripción del producto", required = true)
	private String descripcion;
	
	@ApiModelProperty(value = "Empresa cliente al cual pertenece la mercancía", required = true)
	@Column(name = "fd_cliente")
	@NotNull
	private String cliente;
	
	@Column(name = "fd_importadora")
	@NotNull
	@ApiModelProperty(value = "Importadora mediante la cual entró el producto al país", required = true)
	private String importadora;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "fd_estado")
	@NotNull
	@ApiModelProperty(value = "Representa el estado actual en que se encuentra la mercancía", required = true)
	public EstadoMercancias estado;
	
	@Column(name = "fd_codigo")
	@NotNull
	@ApiModelProperty(value = "Es el codigo unico que tiene un contenedor", required = true)
	private String codigo;
	
	@Column(name = "fd_manifiesto")
	@NotNull
	@ApiModelProperty(value = "Es el manifiesto del contendor", required = true)
	private String manifiesto;
	
	@Column(name = "fd_bl")
	@ApiModelProperty(value = "Es número BL del contenedor", required = false)
	private String bl;
	
	@ApiModelProperty(value = "Representa la fecha en que el cliente planifica la extración", required = false)
	@JsonFormat(pattern="yyyy-MM-dd")
	@Column(name = "fd_fecha_planificacion")
	private LocalDate fecha_planificacion;
	
	@ApiModelProperty(value = "Tamaño del contenedor", required = true)
	@Column(name = "fd_tamano")
	@NotNull
	private int tamano;
	
	@ApiModelProperty(value = "Puerto por donde arriba la mercancía", required = true)
	@Column(name = "fd_puerto")
	@NotNull
	private String puerto;	
	
	@ApiModelProperty(value = "Es la fecha de extraccion del contenedor del puerto")
	@Column(name = "fd_fecha_extraccion")
	private LocalDate fecha_extraccion;
	
	@ApiModelProperty(value = "Es la fecha de devolucion del contenedor por parte del cliente")
	@Column(name = "fd_fecha_devolucion")
	private LocalDate fecha_devolucion;
	
	/*CONTRUCTORES*/
	public Contenedor() {
		super();
		this.estado = EstadoMercancias.LISTO_PARA_EXTRAER;
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

	public EstadoMercancias getEstado() {
		return estado;
	}

	public void setEstado(EstadoMercancias estado) {
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
