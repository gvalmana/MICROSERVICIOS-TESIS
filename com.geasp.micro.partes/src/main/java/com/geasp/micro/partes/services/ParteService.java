package com.geasp.micro.partes.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;

import org.keycloak.KeycloakSecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.geasp.micro.partes.models.ResumenExtracciones;
import com.geasp.micro.partes.models.ResumenGuias;
import com.geasp.micro.partes.models.Parte;
import com.geasp.micro.partes.models.ResumenCargas;
import com.geasp.micro.partes.models.ResumenContenedores;

import reactor.core.publisher.Mono;

@Service
@RefreshScope
public class ParteService implements IParte {
	
	@Autowired
	private WebClient.Builder webClientBuilder;
	
	@Autowired
	private KeycloakSecurityContext securityContext;
	
	@Value("${partes.nombre}")
	private String nombreParte;
	@Autowired
	private ParteCarga parteCargas;
	@Autowired
	private ParteGuia parteGuias;
	@Autowired
	private ParteContenedor parteContedores;
	@Override
	public Parte getParteByDate(String date) {
		// TODO Auto-generated method stub
		LocalDate fecha = LocalDate.parse(date);
		LocalTime hora = LocalTime.now();
		Parte res = new Parte(fecha, hora, nombreParte);
		
		String url = "http://OPERACIONES/partes/fecha="+date;
		
		ResumenExtracciones extracciones = getOperaciones(url).block();
		
		ResumenContenedores contenedores = parteContedores.getResumenContenedores(fecha);
		contenedores.setResumenSalidas(extracciones.getContenedores());
		ResumenCargas cargas =  parteCargas.getResumenCargas(fecha);
		cargas.setResumenSalidas(extracciones.getCargas());
		
		ResumenGuias guias = parteGuias.getResumenGuias(fecha);
		guias.setResumenSalidas(extracciones.getGuias());
		
		res.setContenedores(contenedores);
		res.setCargas(cargas);
		res.setGuias(guias);
		
		return res;
	}
	
	private Mono<ResumenExtracciones> getOperaciones(String url) {
		return webClientBuilder.build().get()
				.uri(url)
				.headers(header->{
					header.setBearerAuth(securityContext.getTokenString());
					header.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
				})
				.retrieve()
				.bodyToMono(ResumenExtracciones.class);
	}
	
//	private Mono<ResumenContenedores> getResumenContenedores(LocalDate data) {
//		return webClientBuilder.build().get()
//				.uri("http://MERCANCIAS/contenedores/parte/fecha="+data)
//				.headers(header->{
//					header.setBearerAuth(securityContext.getTokenString());
//					header.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
//				})
//				.retrieve()
//				.bodyToMono(ResumenContenedores.class);
//	}
	
	
//	private Mono<ResumenCargas> getResumenCargas(LocalDate data) {
//		return webClientBuilder.build().get()
//				.uri("http://MERCANCIAS/cargas/parte/fecha="+data)
//				.headers(header->{
//					header.setBearerAuth(securityContext.getTokenString());
//					header.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
//				})
//				.retrieve()
//				.bodyToMono(ResumenCargas.class);
//	}
	
//	private Mono<ResumenGuias> getResumenGuias(LocalDate data) {
//		return webClientBuilder.build().get()
//				.uri("http://MERCANCIAS/guias/parte/fecha="+data)
//				.headers(header->{
//					header.setBearerAuth(securityContext.getTokenString());
//					header.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
//				})
//				.retrieve()
//				.bodyToMono(ResumenGuias.class);
//	}

}
