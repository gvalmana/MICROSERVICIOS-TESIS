package com.geasp.micro.cargas;

import java.time.LocalDate;
import java.time.Period;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import com.geasp.micro.cargas.models.Carga;
import com.geasp.micro.cargas.models.EstadoMercancias;
import com.geasp.micro.cargas.responses.CargaResponse;

@RefreshScope
public class Calculos {

	@Value("${estadia.minimo}")
	private int estadiaMinimo;
	
	@Value("${estadia.maximo}")
	private int estadiaMaximo;
	
//	public String determinarEstadia(CargaResponse data) {
//		return crearTextoEstadia(data.getEdad());
//	}
	
	public String determinarEstadia(CargaResponse data) {
		return crearTextoEstadia(data.getCantidad_dias_desagrupada());
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
	
	public String mappearEstadoToText(Carga data) {
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
	
	public int calcularDiasDocumentos(Carga data) {
		if (data.getEstado()==EstadoMercancias.EXTRAIDA || data.getEstado()==EstadoMercancias.EXTRAIDA || data.getFecha_documentos() == null) {
			return -1;
		}else {
			return Period.between(data.getFecha_documentos(), LocalDate.now()).getDays();
		}
	}
	
	public int calcularDiasEstadia(Carga data) {
		if (data.getEstado()==EstadoMercancias.EXTRAIDA || data.getFecha_arribo()==null) {
			return -1;
		}else {
			return Period.between(data.getFecha_arribo(), LocalDate.now()).getDays();
		}
	}
	
	public int calcularDiasDesagrupada(Carga carga) {
		if (carga.getEstado()==EstadoMercancias.EXTRAIDA || carga.getFecha_desagrupe()==null) {
			return -1;
		}else {
			return Period.between(carga.getFecha_desagrupe(), LocalDate.now()).getDays();
		}
	}
}
