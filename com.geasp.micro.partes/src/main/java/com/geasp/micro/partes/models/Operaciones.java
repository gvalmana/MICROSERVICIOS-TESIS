package com.geasp.micro.partes.models;

import java.util.ArrayList;
import java.util.List;

public class Operaciones {

	private String titulo;
	private int cantidad;
	private List<CantidadEmpresa> lista;
	
	public Operaciones(List<CantidadEmpresa> lista) {
		super();
		this.lista = lista;
		this.cantidad = lista.size();
	}

	public Operaciones() {
		super();
		// TODO Auto-generated constructor stub
		this.lista = new ArrayList<CantidadEmpresa>();
	}
	
	public Operaciones(String titulo, List<CantidadEmpresa> lista) {
		super();
		this.titulo = titulo;
		this.lista = lista;
		this.cantidad = lista.size();
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public List<CantidadEmpresa> getLista() {
		return lista;
	}

	public void setLista(List<CantidadEmpresa> lista) {
		this.lista = lista;
	}
	
}
