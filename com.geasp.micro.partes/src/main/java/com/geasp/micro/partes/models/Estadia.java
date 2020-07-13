package com.geasp.micro.partes.models;

import java.util.ArrayList;
import java.util.List;

public class Estadia {

	private int porExtraccion;
	private int porDevolucion;
	private int otrasCausas;
	private int porHabilitar;
	private List<CantidadEmpresa> listado;
	
	public Estadia(int porExtraccion, int porDevolucion, int otrasCausas, int porHabilitar) {
		super();
		this.porExtraccion = porExtraccion;
		this.porDevolucion = porDevolucion;
		this.otrasCausas = otrasCausas;
		this.porHabilitar = porHabilitar;
		this.listado = new ArrayList<CantidadEmpresa>();
	}
	public Estadia() {
		super();
		// TODO Auto-generated constructor stub
		this.listado = new ArrayList<CantidadEmpresa>();
	}
	public int getPorExtraccion() {
		return porExtraccion;
	}
	public void setPorExtraccion(int porExtraccion) {
		this.porExtraccion = porExtraccion;
	}
	public int getPorDevolucion() {
		return porDevolucion;
	}
	public void setPorDevolucion(int porDevolucion) {
		this.porDevolucion = porDevolucion;
	}
	public int getOtrasCausas() {
		return otrasCausas;
	}
	public void setOtrasCausas(int otrasCausas) {
		this.otrasCausas = otrasCausas;
	}
	public List<CantidadEmpresa> getListado() {
		return listado;
	}
	public void setListado(List<CantidadEmpresa> listado) {
		this.listado = listado;
	}
	public int getPorHabilitar() {
		return porHabilitar;
	}
	public void setPorHabilitar(int porHabilitar) {
		this.porHabilitar = porHabilitar;
	}
	
}
