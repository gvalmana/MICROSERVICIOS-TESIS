package com.geasp.micro.contenedores;

import java.time.LocalDate;
import java.time.Period;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import com.geasp.micro.contenedores.models.Contenedor;
import com.geasp.micro.contenedores.models.EstadoMercancias;
import com.geasp.micro.contenedores.responses.ContenedorResponse;

@RefreshScope
public class Calculos {

	@Value("${estadia.minimo}")
	private int estadiaMinimo;
	
	@Value("${estadia.maximo}")
	private int estadiaMaximo;
	
	public String determinarEstadia(ContenedorResponse data) {
		return crearTextoEstadia(data.getEdad());
	}
	
	private String crearTextoEstadia(int valor) {
		if (valor<estadiaMinimo) {
			return "No está en riesgo de estadía.";
		} else if (valor >=estadiaMinimo && valor <estadiaMaximo) {
			return "Está en riesgo de estadía.";
		} else if(valor >=estadiaMaximo) {
			return "Está en estadía.";
		}
		return "";		
	}
	
	public String mappearEstadoToText(Contenedor data) {
		String res = "Sin extraer";
		switch(data.getEstado()) {
		  case LISTO_PARA_EXTRAER:
			  res = "Listo para extraer";
		    break;
		  case EXTRAIDA:
			  res = "Extraida";
		    break;
		  case DEVUELTA:
			  res = "Devuelta";
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
	
	public int calcularDiasDocumentos(Contenedor data) {
		if (data.getEstado()==EstadoMercancias.DEVUELTA || data.getEstado()==EstadoMercancias.EXTRAIDA || data.getFecha_documentos() == null) {
			return -1;
		}else {
			return Period.between(data.getFecha_documentos(), LocalDate.now()).getDays();
		}
	}
	
	public int calcularDiasEstadia(Contenedor data) {
		if (data.getEstado()==EstadoMercancias.DEVUELTA || data.getFecha_arribo()==null) {
			return -1;
		}else {
			return Period.between(data.getFecha_arribo(), LocalDate.now()).getDays();
		}
	}
}
