package com.geasp.micro.mercancias.models;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.envers.Audited;
import org.springframework.data.history.RevisionMetadata;


@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "tbl_mercancias", catalog = "bd_mercancias")
@Audited
public class Mercancia extends Auditable<String> {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;
	
	@Column(name = "fd_fecha_arribo")
	private LocalDate fecha_arribo;	
	
	@Column(name = "fd_fecha_habilitacion")
	private LocalDate fecha_habilitacion;
	
	@Column(name = "fd_fecha_documentos")
	private LocalDate fecha_documentos;
	
	@Column(name = "fd_situacion")
	@Lob
	private String situacion;
	
	@Column(name = "fd_observaciones")
	@Lob
	private String observaciones;
	
	@Column(name = "fd_descripcion")
	@NotNull
	private String descripcion;
	
	@Column(name = "fd_cliente")
	@NotNull
	private String cliente;
	
	@Column(name = "fd_importadora")
	@NotNull
	private String importadora;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "fd_estado")
	@NotNull
	public EstadoMercancias estado;
	
	@Transient
	private RevisionMetadata<Long> editVersion;
	
	/*CONTRUCTORES*/
	public Mercancia() {
		super();
		this.estado = EstadoMercancias.LISTO_PARA_EXTRAER;
	}
	
	public Mercancia(Long id, LocalDate fecha_arribo, LocalDate fecha_habilitacion, LocalDate fecha_documentos,
			String situacion, String observaciones, @NotNull String descripcion, @NotNull String cliente,
			@NotNull String importadora, @NotNull EstadoMercancias estado, RevisionMetadata<Long> editVersion) {
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

}
