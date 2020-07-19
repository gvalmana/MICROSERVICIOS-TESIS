package com.geasp.micro.operaciones.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.keycloak.KeycloakSecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.geasp.micro.operaciones.models.CantidadEmpresa;
import com.geasp.micro.operaciones.models.Cliente;
import com.geasp.micro.operaciones.models.Extraccion;
import com.geasp.micro.operaciones.models.Mercancia;
import com.geasp.micro.operaciones.models.Operaciones;
import com.geasp.micro.operaciones.repositories.ExtraccionRepository;
import com.geasp.micro.operaciones.responses.ParteResponse;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@Service
public class ParteService implements IParteService{

	@Autowired
	private ExtraccionRepository extracciones;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private KeycloakSecurityContext securityContext;
	
	private ResponseEntity<Mercancia> get(Long id) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.setBearerAuth(securityContext.getTokenString());		
		HttpEntity<String> entity = new HttpEntity<>("body",headers);
		
		return restTemplate.exchange(
				"http://MERCANCIAS/operaciones/"+id, 
				HttpMethod.GET,
				entity,
				Mercancia.class
			);
	}
	
	private ResponseEntity<List<Cliente>> buscarTodasLasEmpresas() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.setBearerAuth(securityContext.getTokenString());
		HttpEntity<String> entity = new HttpEntity<>("body",headers);
		
		return restTemplate.exchange(
				"http://EMPRESAS/v1/clientes/", 
				HttpMethod.GET,
				entity,
				new ParameterizedTypeReference<List<Cliente>>() {
				}
			);
	}
	
	private List<CantidadEmpresa> listarExtraccionesPorEmpresas(List<Extraccion> data){
		List<Cliente> clientes = buscarTodasLasEmpresas().getBody();
		List<CantidadEmpresa> resultado = new ArrayList<CantidadEmpresa>();
		//RECORRE TODA LA LISTA DE CLIENTES
		clientes.stream().forEach(index -> {
			int cantidad = 0;
			//RECORRE LA LISTA DE CONTENEDORES PARA EXTRAER
			for (Extraccion item : data) {
				//SI ES EL MISMO CLIENTE AGREGA UN CONTADOR
				if (index.getNombre().equals(get(item.getMercanciaId()).getBody().getCliente())) {
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
				"Salidas de contenedores en el dia "+fecha.toString(),
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
				"Salidas de cargas agrupadas en el dia "+fecha.toString(),
				listaExtraccion
				);
		return cargas;		
	}
	
	private Operaciones makeGuias(LocalDate fecha) {
		
		List<Extraccion> lista = extracciones.findByFechaAndTipoMercancia(fecha, "Guia");
		List<CantidadEmpresa> listaExtraccion = listarExtraccionesPorEmpresas(lista);		
		Operaciones guias = new Operaciones(
				"Salidas de guías aéreas en el dia "+fecha.toString(),
				listaExtraccion
				);
		return guias;		
	}

	public ParteResponse makeParteFallBack(String date){
		Operaciones contenedores = new Operaciones("Error al confeccionar el parte.");
		Operaciones cargas = new Operaciones("Error al confeccionar el parte.");
		Operaciones guias = new Operaciones("Error al confeccionar el parte.");
		return new ParteResponse(contenedores, cargas, guias);
	}
	
	@Override
	@HystrixCommand(fallbackMethod = "makeParteFallBack", commandProperties = {
			@HystrixProperty(name="execution.isolation.strategy",value="SEMAPHORE"),
		//	@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
			@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
			@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
			@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000")
		})	
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
