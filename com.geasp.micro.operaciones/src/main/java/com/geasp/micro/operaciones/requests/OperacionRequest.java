package com.geasp.micro.operaciones.requests;

import java.io.Serializable;
import java.time.LocalDate;


import javax.validation.constraints.NotNull;

public class OperacionRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3902976014284808702L;
	
	@NotNull(message = "Debe indicar una fecha de extracción")
	private LocalDate fecha;
	
	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public OperacionRequest() {
		super();
	}
	
}
