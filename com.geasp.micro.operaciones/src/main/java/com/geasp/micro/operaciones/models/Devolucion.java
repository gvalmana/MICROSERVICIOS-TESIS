package com.geasp.micro.operaciones.models;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.envers.Audited;


@Audited
@Table(name = "tbl_devoluciones", catalog = "bd_operaciones")
@Entity
public class Devolucion extends Auditable<String> {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;
	
	@NotNull
	private boolean activo;
	
	@NotNull
	@Column(name = "fd_fecha")
	private LocalDate fecha;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(nullable = false, unique = true)
	private Extraccion extraccion;
	
	public Devolucion() {
		super();
		this.activo = true;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Extraccion getExtraccion() {
		return extraccion;
	}

	public void setExtraccion(Extraccion extraccion) {
		this.extraccion = extraccion;
	}
	
	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	
}
