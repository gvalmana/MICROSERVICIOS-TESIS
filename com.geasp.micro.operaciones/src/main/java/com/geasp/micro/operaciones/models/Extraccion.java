package com.geasp.micro.operaciones.models;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.envers.Audited;

@Audited
@Table(name = "tbl_extracciones", catalog = "bd_operaciones")
@Entity
public class Extraccion extends Auditable<String> {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;
	
	@NotNull
	@Column(name = "fd_mercancia_id", unique = true)
	private Long mercanciaId;
	
	@NotNull
	@Column(name = "fd_fecha")
	private LocalDate fecha;
	
	@NotNull
	@Column(name = "fd_tipo_mercancia")
	private String tipoMercancia;
	
	@NotNull
	private boolean activo;
	
	public Extraccion() {
		super();
		this.activo = false;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public Long getMercanciaId() {
		return mercanciaId;
	}

	public void setMercanciaId(Long mercanciaId) {
		this.mercanciaId = mercanciaId;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public String getTipoMercancia() {
		return tipoMercancia;
	}

	public void setTipoMercancia(String tipoMercancia) {
		this.tipoMercancia = tipoMercancia;
	}
	
}
