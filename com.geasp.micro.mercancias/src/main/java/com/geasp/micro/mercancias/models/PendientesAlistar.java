package com.geasp.micro.mercancias.models;

import java.util.ArrayList;
import java.util.List;

public class PendientesAlistar {

	private int porHabilitar;//Cuando no tienen fecha de habilitacion
	private int pendienteEntregar;//Cuando no tiene fecha de entrega de documentos
	private List<CantidadEmpresa> listaPendientesHabilitar;
	private List<CantidadEmpresa> listaPendientesEntregar;
	
	public PendientesAlistar(int porHabilitar, int pendienteEntregar) {
		super();
		this.porHabilitar = porHabilitar;
		this.pendienteEntregar = pendienteEntregar;
	}
	
	public PendientesAlistar(List<CantidadEmpresa> listaPendientesHabilitar,
			List<CantidadEmpresa> listaPendientesEntregar) {
		super();
		this.listaPendientesHabilitar = listaPendientesHabilitar;
		this.listaPendientesEntregar = listaPendientesEntregar;
		this.porHabilitar = listaPendientesHabilitar.size();
		this.pendienteEntregar = listaPendientesEntregar.size();
	}

	public PendientesAlistar() {
		super();
		// TODO Auto-generated constructor stub
		this.listaPendientesEntregar = new ArrayList<CantidadEmpresa>();
		this.listaPendientesHabilitar = new ArrayList<CantidadEmpresa>();
	}

	public int getPorHabilitar() {
		return porHabilitar;
	}

	public void setPorHabilitar(int porHabilitar) {
		this.porHabilitar = porHabilitar;
	}
	
	public int getPendienteEntregar() {
		return pendienteEntregar;
	}

	public void setPendienteEntregar(int pendienteEntregar) {
		this.pendienteEntregar = pendienteEntregar;
	}

	public List<CantidadEmpresa> getListaPendientesHabilitar() {
		return listaPendientesHabilitar;
	}

	public void setListaPendientesHabilitar(List<CantidadEmpresa> listaPendientesHabilitar) {
		this.listaPendientesHabilitar = listaPendientesHabilitar;
	}

	public List<CantidadEmpresa> getListaPendientesEntregar() {
		return listaPendientesEntregar;
	}

	public void setListaPendientesEntregar(List<CantidadEmpresa> listaPendientesEntregar) {
		this.listaPendientesEntregar = listaPendientesEntregar;
	}
	
}
