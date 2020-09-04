package com.geasp.micro.partes.models;

public class ResumenPendientes {

	String cliente;
	int total;
	int ParaExtraer;
	int PorHabilitar;
	int SinEntregar;
	int sinDescargar;
	
	public ResumenPendientes(String cliente, int total, int paraExtraer, int porHabilitar, int sinEntregar,
			int sinDescargar) {
		super();
		this.cliente = cliente;
		this.total = total;
		ParaExtraer = paraExtraer;
		PorHabilitar = porHabilitar;
		SinEntregar = sinEntregar;
		this.sinDescargar = sinDescargar;
	}
	public ResumenPendientes() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getCliente() {
		return cliente;
	}
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getParaExtraer() {
		return ParaExtraer;
	}
	public void setParaExtraer(int paraExtraer) {
		ParaExtraer = paraExtraer;
	}
	public int getPorHabilitar() {
		return PorHabilitar;
	}
	public void setPorHabilitar(int porHabilitar) {
		PorHabilitar = porHabilitar;
	}
	public int getSinEntregar() {
		return SinEntregar;
	}
	public void setSinEntregar(int sinEntregar) {
		SinEntregar = sinEntregar;
	}
	public int getSinDescargar() {
		return sinDescargar;
	}
	public void setSinDescargar(int sinDescargar) {
		this.sinDescargar = sinDescargar;
	}
	
}
