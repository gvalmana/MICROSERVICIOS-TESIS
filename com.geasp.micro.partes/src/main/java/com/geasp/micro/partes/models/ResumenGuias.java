package com.geasp.micro.partes.models;

public class ResumenGuias extends Resumen {
	
	private int pendientesHabilitar;
	private int guiasHabilitadas;
	
	public ResumenGuias() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ResumenGuias(String titulo) {
		super(titulo);
		// TODO Auto-generated constructor stub
	}

	public int getPendientesHabilitar() {
		return pendientesHabilitar;
	}

	public void setPendientesHabilitar(int pendientesHabilitar) {
		this.pendientesHabilitar = pendientesHabilitar;
	}

	public int getGuiasHabilitadas() {
		return guiasHabilitadas;
	}

	public void setGuiasHabilitadas(int guiasHabilitadas) {
		this.guiasHabilitadas = guiasHabilitadas;
	}
	
}
