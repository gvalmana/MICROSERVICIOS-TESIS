package com.geasp.micro.mercancias.responses;

import java.util.ArrayList;
import java.util.List;

import com.geasp.micro.mercancias.models.CantidadEmpresa;
import com.geasp.micro.mercancias.models.Estadia;

public class ResumenContenedores extends Resumen{

	private int cantidadParaExtraer;
	private int cantidadParaDevolver;
	private List<CantidadEmpresa> pendientesDevolver;
	private Estadia enRiesgo;
	
	public int getCantidadParaExtraer() {
		return cantidadParaExtraer;
	}

	public void setCantidadParaExtraer(int cantidadParaExtraer) {
		this.cantidadParaExtraer = cantidadParaExtraer;
	}

	public int getCantidadParaDevolver() {
		return cantidadParaDevolver;
	}

	public void setCantidadParaDevolver(int cantidadParaDevolver) {
		this.cantidadParaDevolver = cantidadParaDevolver;
	}

	public List<CantidadEmpresa> getPendientesDevolver() {
		return pendientesDevolver;
	}

	public void setPendientesDevolver(List<CantidadEmpresa> pendientesDevolver) {
		this.pendientesDevolver = pendientesDevolver;
	}

	public ResumenContenedores() {
		super();
		// TODO Auto-generated constructor stub
		this.pendientesDevolver = new ArrayList<CantidadEmpresa>();
		this.enRiesgo = new Estadia();
	}

	public ResumenContenedores(String titulo) {
		super(titulo);
		this.pendientesDevolver = new ArrayList<CantidadEmpresa>();
		this.enRiesgo = new Estadia();
		// TODO Auto-generated constructor stub
	}
	
	public Estadia getEnRiesgo() {
		return enRiesgo;
	}

	public void setEnRiesgo(Estadia enRiesgo) {
		this.enRiesgo = enRiesgo;
	}
}
