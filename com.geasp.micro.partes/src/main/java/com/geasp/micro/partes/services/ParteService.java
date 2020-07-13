package com.geasp.micro.partes.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;

import org.keycloak.KeycloakSecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.geasp.micro.partes.models.ResumenExtracciones;
import com.geasp.micro.partes.models.Parte;
import com.geasp.micro.partes.models.ResumenCargas;
import com.geasp.micro.partes.models.ResumenContenedores;
import com.geasp.micro.partes.models.ResumenGuias;

@Service
@RefreshScope
public class ParteService implements IParte {

	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private KeycloakSecurityContext securityContext;
	
	@Value("${partes.nombre}")
	private String nombreParte;
	
	@Override
	public Parte getParteByDate(String date) {
		// TODO Auto-generated method stub
		LocalDate fecha = LocalDate.parse(date);
		LocalTime hora = LocalTime.now();
		Parte res = new Parte(fecha, hora, nombreParte);
		
		String url = "http://OPERACIONES/partes/fecha="+date;
		
		ResumenExtracciones extracciones = getOperaciones(url);
		
		ResumenContenedores contenedores = getResumenContenedores(fecha);
		contenedores.setResumenSalidas(extracciones.getContenedores());
		
		ResumenCargas cargas =  getResumenCargas(fecha);
		cargas.setResumenSalidas(extracciones.getCargas());
		
		ResumenGuias guias = getResumenGuias(fecha);
		guias.setResumenSalidas(extracciones.getGuias());
		
		res.setContenedores(contenedores);
		res.setCargas(cargas);
		res.setGuias(guias);
		
		return res;
	}
	
	@Override
	public Parte parteFallCallBack(String date) {
		LocalDate fecha = LocalDate.parse(date);
		LocalTime hora = LocalTime.now();
		Parte res = new Parte(fecha, hora, "Error en la confección del parte.");
		return res;
	}
	
	private ResumenExtracciones getOperaciones(String url) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.setBearerAuth(securityContext.getTokenString());
		HttpEntity<String> entity = new HttpEntity<>("body",headers);
		
		return restTemplate.exchange(
				url, 
				HttpMethod.GET,
				entity,
				ResumenExtracciones.class
			).getBody();
	}
	
	private ResumenContenedores getResumenContenedores(LocalDate data) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.setBearerAuth(securityContext.getTokenString());
		HttpEntity<String> entity = new HttpEntity<>("body",headers);
		
		return restTemplate.exchange(
				"http://MERCANCIAS/contenedores/parte/fecha="+data, 
				HttpMethod.GET,
				entity,
				ResumenContenedores.class
			).getBody();
	}
	private ResumenCargas getResumenCargas(LocalDate data) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.setBearerAuth(securityContext.getTokenString());
		HttpEntity<String> entity = new HttpEntity<>("body",headers);
		
		return restTemplate.exchange(
				"http://MERCANCIAS/cargas/parte/fecha="+data, 
				HttpMethod.GET,
				entity,
				ResumenCargas.class
			).getBody();		
	}
	private ResumenGuias getResumenGuias(LocalDate data) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.setBearerAuth(securityContext.getTokenString());
		HttpEntity<String> entity = new HttpEntity<>("body",headers);
		
		return restTemplate.exchange(
				"http://MERCANCIAS/guias/parte/fecha="+data, 
				HttpMethod.GET,
				entity,
				ResumenGuias.class
			).getBody();		
	}	
}
