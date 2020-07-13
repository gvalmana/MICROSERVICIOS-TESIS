package com.geasp.micro.operaciones.models;

public class CantidadEmpresa {

	String empresa;
	int cantidad;
	
	public CantidadEmpresa() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public CantidadEmpresa(String empresa, int cantidad) {
		super();
		this.empresa = empresa;
		this.cantidad = cantidad;
	}

	public String getEmpresa() {
		return empresa;
	}
	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	
}
