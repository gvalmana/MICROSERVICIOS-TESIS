package com.geasp.micro.partes.conf;

import java.time.LocalDate;
import java.time.Period;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import com.geasp.micro.partes.models.Carga;
import com.geasp.micro.partes.models.Guia;
import com.geasp.micro.partes.models.Mercancia;

@RefreshScope
public class Calculos {

	@Value("${estadia.minimo}")
	private int estadiaMinimo;
	
	@Value("${estadia.maximo}")
	private int estadiaMaximo;

	@Value("${estadia.guias}")
	private int estadiaGuias;
	
	public int calcularDias(LocalDate date) {
		if (date.equals(null)) {
			return -1;
		}else {
			return Period.between(date, LocalDate.now()).getDays();
		}
	}
	
	public int calcularDiasDocumentos(Mercancia data) {
		if (data.getFecha_documentos() == null) {
			return -1;
		}else {
			return Period.between(data.getFecha_documentos(), LocalDate.now()).getDays();
		}
	}
	
	public int calcularDiasEstadia(Mercancia data) {
		if (data.getFecha_arribo()==null) {
			return -1;
		}else {
			return Period.between(data.getFecha_arribo(), LocalDate.now()).getDays();
		}
	}
	
	public int calcularDiasDeclarada(Guia guia) {
		if (guia.getFecha_habilitacion()==null) {
			return -1;
		}else {
			return Period.between(guia.getFecha_habilitacion(), LocalDate.now()).getDays();
		}		
	}
	
	public int calcularDiasEntregada(Guia guia) {
		if (guia.getFecha_entrega()==null) {
			return -1;
		}else {
			return Period.between(guia.getFecha_entrega(), LocalDate.now()).getDays();
		}		
	}
	
	public int calcularDiasDesagrupada(Carga carga) {
		if (carga.getFecha_desagrupe()==null) {
			return -1;
		}else {
			return Period.between(carga.getFecha_desagrupe(), LocalDate.now()).getDays();
		}
	}
}
