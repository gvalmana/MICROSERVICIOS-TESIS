package com.geasp.micro.mercancias.conf;

import java.time.LocalDate;
import java.time.Period;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import com.geasp.micro.mercancias.models.Carga;
import com.geasp.micro.mercancias.models.EstadoMercancias;
import com.geasp.micro.mercancias.models.Guia;
import com.geasp.micro.mercancias.models.Mercancia;
import com.geasp.micro.mercancias.responses.CargaResponse;
import com.geasp.micro.mercancias.responses.ContenedorResponse;
import com.geasp.micro.mercancias.responses.GuiaResponse;
import com.geasp.micro.mercancias.responses.MercanciaResponse;

@RefreshScope
public class Calculos {

	@Value("${estadia.minimo}")
	private int estadiaMinimo;
	
	@Value("${estadia.maximo}")
	private int estadiaMaximo;

	@Value("${estadia.guias}")
	private int estadiaGuias;
	
	public String determinarEstadia(MercanciaResponse data) {
		return crearTextoEstadia(data.getEdad());
	}
	
	public String determinarEstadia(ContenedorResponse data) {
		return crearTextoEstadia(data.getEdad());
	}
	
	public String determinarEstadia(CargaResponse data) {
		return crearTextoEstadia(data.getCantidad_dias_desagrupada());
	}
	
	public String determinarEstadia(GuiaResponse data) {
		return crearTextoEstadiaCargas(data.getDias_entregada());
	}
	
	private String crearTextoEstadiaCargas(int valor) {
		if (valor < estadiaGuias) {
			return "No está en riesgo de estadía.";
		}else {
			return "Está en estadía.";
		}
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
	
	public String mappearEstadoToText(Mercancia data) {
		String res = "Sin extraer";
		switch(data.getEstado()) {
		  case LISTO_PARA_EXTRAER:
			  res = "Lista para extraer";
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
	
	public int calcularDiasDocumentos(Mercancia data) {
		if (data.getEstado()==EstadoMercancias.DEVUELTA || data.getEstado()==EstadoMercancias.EXTRAIDA || data.getFecha_documentos() == null) {
			return -1;
		}else {
			return Period.between(data.getFecha_documentos(), LocalDate.now()).getDays();
		}
	}
	
	public int calcularDiasEstadia(Mercancia data) {
		if (data.getEstado()==EstadoMercancias.DEVUELTA || data.getFecha_arribo()==null) {
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
	
	public int calcularDiasDesagrupada(Carga carga) {
		if (carga.getEstado()==EstadoMercancias.EXTRAIDA || carga.getFecha_desagrupe()==null) {
			return -1;
		}else {
			return Period.between(carga.getFecha_desagrupe(), LocalDate.now()).getDays();
		}
	}
}
