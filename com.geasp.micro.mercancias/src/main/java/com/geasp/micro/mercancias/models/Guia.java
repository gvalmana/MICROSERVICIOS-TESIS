package com.geasp.micro.mercancias.models;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.envers.Audited;
import org.springframework.data.history.RevisionMetadata;

@Entity
@Table(name = "tbl_guias", catalog = "bd_mercancias")
@Audited
public class Guia extends Mercancia{

	@Column(name = "fd_dm")
	@NotNull
	private String dm;
	
	@Column(name = "fd_cantidad_de_bultos")
	private Long cantidad_de_bultos;
	
	@Column(name = "fd_peso_de_la_carga")	
	private Float peso;
	
	@Column(name = "fd_fecha_de_entrega")
	private LocalDate fecha_entrega;
	
	public Guia() {
		super();
	}

	public Guia(Long id, LocalDate fecha_arribo, LocalDate fecha_habilitacion, LocalDate fecha_documentos,
			String situacion, String observaciones, @NotNull String descripcion, @NotNull String cliente,
			@NotNull String importadora, @NotNull EstadoMercancias estado, RevisionMetadata<Long> editVersion,
			@NotNull String dm, Long cantidad_de_bultos, Float peso, LocalDate fecha_entrega) {
		super(id, fecha_arribo, fecha_habilitacion, fecha_documentos, situacion, observaciones, descripcion, cliente,
				importadora, estado, editVersion);
		this.dm = dm;
		this.cantidad_de_bultos = cantidad_de_bultos;
		this.peso = peso;
		this.fecha_entrega = fecha_entrega;
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
