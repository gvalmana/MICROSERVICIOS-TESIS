package com.geasp.micro.cargas.models;

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
@Table(name = "tbl_cargas", catalog = "bd_cargas")
@Audited
@ApiModel("Representa a una carga agrupada")
public class Carga extends Auditable<String> {
	
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
	
	@ApiModelProperty(value = "Es el manifiesto del contendor", required = true)
	@Column(name = "fd_manifiesto")
	@NotNull
	private String manifiesto;
	
	@ApiModelProperty(value = "Representa el DM de la carga agrupada")
	@Column(name = "fd_dm")
	private String dm;
	
	@ApiModelProperty(value = "Es número BL del contenedor", required = false)
	@Column(name = "fd_bl")
	private String bl;
	
	@ApiModelProperty(value = "Representa el codigo de un contenedor en el qeu arriba la carga")
	@Column(name = "fd_codigo_contenedor")
	private String codigo_contenedor;
	
	@ApiModelProperty(value = "Representa la fecha en que la carga es extradida el contenedor, a partir de aqui se cuenta la estadia")
	@Column(name = "fd_fecha_desagrupe")
	private LocalDate fecha_desagrupe;
	
	@ApiModelProperty(value = "Represetna la fecha en que la carga es puesta al dispocisión del cliente por parte de la aduana")
	@Column(name = "fd_fecha_descarga")
	private LocalDate fecha_descarga;
	
	@ApiModelProperty(value = "Puerto por donde arriba la mercancía", required = true)
	@Column(name = "fd_puerto")
	@NotNull
	private String puerto;	
	
	@ApiModelProperty(value = "Es la fecha de extraccion de la carga agrupada del puerto")
	@Column(name = "fd_fecha_extraccion")
	private LocalDate fecha_extraccion;
	
	/*CONTRUCTORES*/
	public Carga() {
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

	public LocalDate getFecha_extraccion() {
		return fecha_extraccion;
	}

	public void setFecha_extraccion(LocalDate fecha_extraccion) {
		this.fecha_extraccion = fecha_extraccion;
	}

}
