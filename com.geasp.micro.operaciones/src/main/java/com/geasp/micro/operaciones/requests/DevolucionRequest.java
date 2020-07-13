package com.geasp.micro.operaciones.requests;

import java.io.Serializable;
import java.time.LocalDate;

import javax.validation.constraints.NotNull;

public class DevolucionRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8787379304935927713L;
	
	@NotNull(message = "Debe indicar una fecha de devolución")
	private LocalDate fecha;
	
	public DevolucionRequest() {
		super();
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}
	
}
