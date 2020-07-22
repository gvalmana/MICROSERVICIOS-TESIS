package com.geasp.micro.operaciones.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.keycloak.KeycloakSecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.geasp.micro.operaciones.models.CantidadEmpresa;
import com.geasp.micro.operaciones.models.Cliente;
import com.geasp.micro.operaciones.models.Extraccion;
import com.geasp.micro.operaciones.models.Mercancia;
import com.geasp.micro.operaciones.models.Operaciones;
import com.geasp.micro.operaciones.repositories.ExtraccionRepository;
import com.geasp.micro.operaciones.responses.ParteResponse;

import reactor.core.publisher.Mono;

@Service
public class ParteService implements IParteService{

	@Autowired
	private ExtraccionRepository extracciones;
	
	@Autowired
	private WebClient.Builder webClientBuilder;
	
	@Autowired
	private KeycloakSecurityContext securityContext;
	
	@Value("${parte.guias.nombre}")
	private String tituloGuias;
	@Value("${parte.cargas.nombre}")
	private String tituloCargas;
	@Value("${parte.contenedores.nombre}")
	private String tituloContenedores;
	@Value("${parte.error}")
	private String error;
	
	private Mono<Mercancia> get(Long id) {
		return webClientBuilder.build().get()
				.uri("http://MERCANCIAS/operaciones/"+id)
				.headers(header->{
					header.setBearerAuth(securityContext.getTokenString());
					header.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
				})
				.retrieve()
				.bodyToMono(Mercancia.class);
	}
	
	private Mono<List<Cliente>> buscarTodasLasEmpresas() {
		return webClientBuilder.build().get()
				.uri("http://EMPRESAS/v1/clientes/")
				.headers(header->{
					header.setBearerAuth(securityContext.getTokenString());
					header.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
				})
				.retrieve()
				.bodyToMono(new ParameterizedTypeReference<List<Cliente>>() {});
	}
	
	private List<CantidadEmpresa> listarExtraccionesPorEmpresas(List<Extraccion> data){
		List<Cliente> clientes = buscarTodasLasEmpresas().block();
		List<CantidadEmpresa> resultado = new ArrayList<CantidadEmpresa>();
		//RECORRE TODA LA LISTA DE CLIENTES
		clientes.stream().forEach(index -> {
			int cantidad = 0;
			//RECORRE LA LISTA DE CONTENEDORES PARA EXTRAER
			for (Extraccion item : data) {
				//SI ES EL MISMO CLIENTE AGREGA UN CONTADOR
				if (index.getNombre().equals(get(item.getMercanciaId()).block().getCliente())) {
					cantidad++;
				}
			}
			if (cantidad>0) {
				resultado.add(new CantidadEmpresa(index.getNombre(), cantidad));
			}
		});		
		return resultado;
	}
	
	private Operaciones makeContenedor(LocalDate fecha) {
		
		List<Extraccion> lista = extracciones.findByFecha(fecha)
				.stream()
				.filter(index->{
					return index.getTipoMercancia().equals("Contenedor");
				}).collect(Collectors.toList());		
		List<CantidadEmpresa> listaExtraccion = listarExtraccionesPorEmpresas(lista);
		Operaciones contenedores = new Operaciones(
				tituloContenedores,
				listaExtraccion
				);
		return contenedores;
	}
	
	private Operaciones makeCargas(LocalDate fecha) {
		
		List<Extraccion> lista = extracciones.findByFecha(fecha)
				.stream()
				.filter(index->{
					return index.getTipoMercancia().equals("Carga");
				})
				.collect(Collectors.toList());
		List<CantidadEmpresa> listaExtraccion = listarExtraccionesPorEmpresas(lista);
		Operaciones cargas = new Operaciones(
				tituloCargas,
				listaExtraccion
				);
		return cargas;		
	}
	
	private Operaciones makeGuias(LocalDate fecha) {
		
		List<Extraccion> lista = extracciones.findByFechaAndTipoMercancia(fecha, "Guia");
		List<CantidadEmpresa> listaExtraccion = listarExtraccionesPorEmpresas(lista);		
		Operaciones guias = new Operaciones(
				tituloGuias,
				listaExtraccion
				);
		return guias;		
	}

	public ParteResponse makeParteFallBack(String date){
		Operaciones contenedores = new Operaciones(error);
		Operaciones cargas = new Operaciones(error);
		Operaciones guias = new Operaciones(error);
		return new ParteResponse(contenedores, cargas, guias);
	}
	
	@Override	
	public ParteResponse makeParte(String date) {
		// TODO Auto-generated method stub
		LocalDate fecha = LocalDate.parse(date);
		ParteResponse res = new ParteResponse();
		res.setContenedores(makeContenedor(fecha));
		res.setCargas(makeCargas(fecha));
		res.setGuias(makeGuias(fecha));
		return res;
	}	
}
