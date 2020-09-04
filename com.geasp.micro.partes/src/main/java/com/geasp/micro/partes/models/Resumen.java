package com.geasp.micro.partes.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Resumen {

	private String titulo;
	private LocalDate fecha;
	private int total;
	private List<CantidadEmpresa> listosParaExtraer;
	private Estadia enEstadia;
	private PendientesAlistar pendientesAlistar;
	private Operaciones resumenEntradas;
	private Operaciones resumenSalidas;
	
	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public List<CantidadEmpresa> getListosParaExtraer() {
		return listosParaExtraer;
	}

	public void setListosParaExtraer(List<CantidadEmpresa> listosParaExtraer) {
		this.listosParaExtraer = listosParaExtraer;
	}

	public Resumen() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Resumen(String titulo) {
		super();
		this.titulo = titulo;
		this.listosParaExtraer = new ArrayList<CantidadEmpresa>();
		this.enEstadia = new Estadia();
		this.pendientesAlistar = new PendientesAlistar();
	}
	public Estadia getEnEstadia() {
		return enEstadia;
	}
	public void setEnEstadia(Estadia enEstadia) {
		this.enEstadia = enEstadia;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public PendientesAlistar getPendientesAlistar() {
		return pendientesAlistar;
	}
	public void setPendientesAlistar(PendientesAlistar pendientesAlistar) {
		this.pendientesAlistar = pendientesAlistar;
	}

	public Operaciones getResumenEntradas() {
		return resumenEntradas;
	}

	public void setResumenEntradas(Operaciones resumenEntradas) {
		this.resumenEntradas = resumenEntradas;
	}

	public Operaciones getResumenSalidas() {
		return resumenSalidas;
	}

	public void setResumenSalidas(Operaciones resumenSalidas) {
		this.resumenSalidas = resumenSalidas;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}
	
	
}
