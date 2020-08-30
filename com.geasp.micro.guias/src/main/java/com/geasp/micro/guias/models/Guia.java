package com.geasp.micro.guias.models;

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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.envers.Audited;
import org.springframework.data.history.RevisionMetadata;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@Entity
@Table(name = "tbl_guias", catalog = "bd_guias")
@Audited
@ApiModel("Representa una guía aerea")
public class Guia extends Auditable<String> {
	
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
	
	@Column(name = "fd_dm")
	@NotNull
	@ApiModelProperty(value = "Representa el DM de la guía aérea")
	private String dm;
	
	@ApiModelProperty(value = "Cantidad de bultos que tiene la guía")
	@Column(name = "fd_cantidad_de_bultos")
	private Long cantidad_de_bultos;
	
	@ApiModelProperty(value = "Peso de la guía aérea")
	@Column(name = "fd_peso_de_la_carga")	
	private Float peso;
	
	@ApiModelProperty(value = "Fecha de entrega de la guia por la aduana")
	@Column(name = "fd_fecha_de_entrega")
	private LocalDate fecha_entrega;	
	
	@Transient
	private RevisionMetadata<Long> editVersion;
	
	/*CONTRUCTORES*/
	public Guia() {
		super();
		this.estado = EstadoMercancias.LISTO_PARA_EXTRAER;
	}
	
	
	
	public Guia(Long id, @NotNull LocalDate fecha_arribo, @NotNull LocalDate fecha_habilitacion,
			@NotNull LocalDate fecha_documentos, String situacion, String observaciones, @NotNull String descripcion,
			@NotNull String cliente, @NotNull String importadora, @NotNull EstadoMercancias estado, @NotNull String dm,
			Long cantidad_de_bultos, Float peso, LocalDate fecha_entrega, RevisionMetadata<Long> editVersion) {
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
		this.estado = estado;
		this.dm = dm;
		this.cantidad_de_bultos = cantidad_de_bultos;
		this.peso = peso;
		this.fecha_entrega = fecha_entrega;
		this.editVersion = editVersion;
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

	public RevisionMetadata<Long> getEditVersion() {
		return editVersion;
	}

	public void setEditVersion(RevisionMetadata<Long> editVersion) {
		this.editVersion = editVersion;
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

}
