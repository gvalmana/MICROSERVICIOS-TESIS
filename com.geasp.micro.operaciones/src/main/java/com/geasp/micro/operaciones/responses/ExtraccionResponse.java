package com.geasp.micro.operaciones.responses;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Calendar;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.geasp.micro.operaciones.models.Mercancia;

public class ExtraccionResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5557997120074958741L;

	private Long id;
	
	protected String createdBy;
    protected String lastModifiedBy;	
	
    private boolean activo;
    private String tipoMercancia;
    
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDate fecha;
	
	private Mercancia mercancia;
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	protected Calendar creationDate;
	
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    protected Calendar lastModifiedDate;	
	
	public ExtraccionResponse() {
		super();
		this.mercancia = new Mercancia();
	}
	
	public ExtraccionResponse(String mensaje) {
		super();
		this.mercancia = new Mercancia();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public Mercancia getMercancia() {
		return mercancia;
	}

	public void setMercancia(Mercancia mercancia) {
		this.mercancia = mercancia;
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

	public String getTipoMercancia() {
		return tipoMercancia;
	}

	public void setTipoMercancia(String tipoMercancia) {
		this.tipoMercancia = tipoMercancia;
	}
	
}
