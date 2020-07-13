package com.geasp.micro.operaciones.responses;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Calendar;
import com.fasterxml.jackson.annotation.JsonFormat;

public class DevolucionResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6985401520162022237L;
	
	private Long id;
	
	private String message;
	private boolean activo;
	protected String createdBy;
    protected String lastModifiedBy;	
	
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDate fecha;
	
	private ExtraccionResponse extraccion;
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	protected Calendar creationDate;
	
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    protected Calendar lastModifiedDate;
	
    public DevolucionResponse() {
		super();
		this.extraccion = new ExtraccionResponse();
		this.activo = true;
	}
    
	public DevolucionResponse(String message) {
		super();
		this.activo = false;
		this.message = message;
		this.extraccion = new ExtraccionResponse();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
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

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public ExtraccionResponse getExtraccion() {
		return extraccion;
	}

	public void setExtraccion(ExtraccionResponse extraccion) {
		this.extraccion = extraccion;
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
	
}
