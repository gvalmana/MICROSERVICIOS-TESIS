package com.geasp.micro.guias;

import java.time.LocalDate;
import java.time.Period;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import com.geasp.micro.guias.models.EstadoMercancias;
import com.geasp.micro.guias.models.Guia;
import com.geasp.micro.guias.responses.GuiaResponse;

@RefreshScope
public class Calculos {

	@Value("${estadia.guias}")
	private int estadiaGuias;
	
	public String determinarEstadia(GuiaResponse data) {
		return crearTextoEstadiaGuias(data.getDias_entregada());
	}
	
	private String crearTextoEstadiaGuias(int valor) {
		if (valor < estadiaGuias) {
			return "No está en riesgo de estadía.";
		}else {
			return "Está en estadía.";
		}
	}
	
	public String mappearEstadoToText(Guia data) {
		String res = "Sin extraer";
		switch(data.getEstado()) {
		  case LISTO_PARA_EXTRAER:
			  res = "Lista para extraer";
		    break;
		  case EXTRAIDA:
			  res = "Extraida";
		    break;
		  case DISABLE:
			  res = "Disabled";
			  break;
		  default:
			res = "";
			break;
		}
		return res;		
	}
	
	public int calcularDias(LocalDate date) {
		if (date.equals(null)) {
			return -1;
		}else {
			return Period.between(date, LocalDate.now()).getDays();
		}
	}
	
	public int calcularDiasDocumentos(Guia data) {
		if (data.getEstado()==EstadoMercancias.EXTRAIDA || data.getEstado()==EstadoMercancias.EXTRAIDA || data.getFecha_documentos() == null) {
			return -1;
		}else {
			return Period.between(data.getFecha_documentos(), LocalDate.now()).getDays();
		}
	}
	
	public int calcularDiasEstadia(Guia data) {
		if (data.getEstado()==EstadoMercancias.EXTRAIDA || data.getFecha_arribo()==null) {
			return -1;
		}else {
			return Period.between(data.getFecha_arribo(), LocalDate.now()).getDays();
		}
	}
	
	public int calcularDiasDeclarada(Guia guia) {
		if (guia.getEstado()==EstadoMercancias.EXTRAIDA || guia.getFecha_habilitacion()==null) {
			return -1;
		}else {
			return Period.between(guia.getFecha_habilitacion(), LocalDate.now()).getDays();
		}		
	}
	
	public int calcularDiasEntregada(Guia guia) {
		if (guia.getEstado()==EstadoMercancias.EXTRAIDA || guia.getFecha_entrega()==null) {
			return -1;
		}else {
			return Period.between(guia.getFecha_entrega(), LocalDate.now()).getDays();
		}		
	}
}
