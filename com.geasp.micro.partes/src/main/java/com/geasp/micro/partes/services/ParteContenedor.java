package com.geasp.micro.partes.services;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.keycloak.KeycloakSecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import com.geasp.micro.partes.conf.ClientesHelper;
import com.geasp.micro.partes.models.CantidadEmpresa;
import com.geasp.micro.partes.models.Cliente;
import com.geasp.micro.partes.models.Contenedor;
import com.geasp.micro.partes.models.Estadia;
import com.geasp.micro.partes.models.Operaciones;
import com.geasp.micro.partes.models.PendientesAlistar;
import com.geasp.micro.partes.models.ResumenContenedores;

public class ParteContenedor {
	@Autowired
	private WebClient.Builder webClientBuilder;
	@Autowired
	private KeycloakSecurityContext securityContext;
	@Autowired
	private ClientesHelper clientes;
	@Value("${partes.contenedores.nombre}")
	private String contenedoresNombre;
	@Value("${estadia.minimo}")
	private int estadiaMinimo;	
	@Value("${estadia.maximo}")
	private int estadiaMaximo;	
	@Value("${partes.operaciones.contenedores.nombre}")
	private String titulo;
	
	public ResumenContenedores getResumenContenedores(LocalDate date) {
		ResumenContenedores res = new ResumenContenedores(contenedoresNombre);
		List<Contenedor> paraExtraer = getExtraer();
		List<Contenedor> paraDevolver = getExtraidos();		
		List<CantidadEmpresa> listosParaExtraer = listarPorEmpresas(paraExtraer);
		List<CantidadEmpresa> pendientesParaDevolver = listarPorEmpresas(paraDevolver);
		List<Contenedor> listaEstadia = new ArrayList<Contenedor>();
		List<Contenedor> listaRiesgo = new ArrayList<Contenedor>();
		
		List<Contenedor> listaEntradas = paraExtraer.stream().filter(index->{
			return index.getFecha_arribo().equals(date); 
		}).collect(Collectors.toList());
		
		List<CantidadEmpresa> resumenOperaciones = listarPorEmpresas(listaEntradas);
		
		int cantidadRiesgoExtraer = 0;
		int cantidadRiesgoParadevolver =0;		
		int cantdiadEstadiaOtros=0;
		int cantidadRiesgoOtros=0;		
		int cantidadEstadiaParadevolver=0;
		int cantidadEstadiaExtraer=0;
		
		for (Contenedor index : paraExtraer) {
			int edad = calcularDiasEstadia(index);
			if (edad>estadiaMaximo) {
				if (index.getFecha_documentos()==null) {
					cantdiadEstadiaOtros++;
				} else {
					cantidadEstadiaExtraer++;					
				}
				listaEstadia.add(index);
			}else if(edad>estadiaMinimo && edad <=estadiaMaximo) {
				if (index.getFecha_documentos()==null) {
					cantidadRiesgoOtros++;
				} else {
					cantidadRiesgoExtraer++;
					
				}
				listaRiesgo.add(index);
			}			
		}
		
		for (Contenedor index : paraDevolver) {
			int edad = calcularDiasEstadia(index);
			if (edad>estadiaMaximo) {
				cantidadEstadiaParadevolver++;
				listaEstadia.add(index);
			} else if (edad>estadiaMinimo && edad<=estadiaMaximo) {
				cantidadRiesgoParadevolver++;
				listaRiesgo.add(index);
			}
		}
		
		Estadia enEstadia = new Estadia(
				cantidadEstadiaExtraer,
				cantidadEstadiaParadevolver,
				cantdiadEstadiaOtros,
				0);		
		Estadia enRiesgo = new Estadia(
				cantidadRiesgoExtraer, 
				cantidadRiesgoParadevolver, 
				cantidadRiesgoOtros,
				0);
		
		enEstadia.setListado(listarPorEmpresas(listaEstadia));
		
		enRiesgo.setListado(listarPorEmpresas(listaRiesgo));
		
		res.setTotal(paraExtraer.size()+paraDevolver.size());
		
		res.setCantidadParaExtraer(paraExtraer.size());
		res.setCantidadParaDevolver(paraDevolver.size());
		
		res.setEnEstadia(enEstadia);
		res.setEnRiesgo(enRiesgo);
		res.setListosParaExtraer(listosParaExtraer);
		res.setPendientesDevolver(pendientesParaDevolver);
		res.setResumenEntradas(new Operaciones(titulo, resumenOperaciones));
		
		List<Contenedor> porHabilitar = paraExtraer.stream().filter(index->{
			return index.getFecha_habilitacion()==null;
		}).collect(Collectors.toList());
		
		List<Contenedor> porEntregar = paraExtraer.stream().filter(index->{
			return index.getFecha_habilitacion()!=null && index.getFecha_documentos()==null;
		}).collect(Collectors.toList());
		
		res.setPendientesAlistar(
				new PendientesAlistar(
						listarPorEmpresas(porHabilitar), 
						listarPorEmpresas(porEntregar)
					));			
		return res;
	}
	private List<Contenedor> getExtraer() {
		return webClientBuilder.build().get()
				.uri("http://MERCANCIAS/v1/buscarporestado?estado=LISTO_PARA_EXTRAER")
				.headers(header->{
					header.setBearerAuth(securityContext.getTokenString());
					header.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
				})
				.retrieve()
				.bodyToMono(new ParameterizedTypeReference<List<Contenedor>>() {}).block();
	}
	private List<Contenedor> getExtraidos() {
		return webClientBuilder.build().get()
				.uri("http://MERCANCIAS/v1/buscarporestado?estado=EXTRAIDA")
				.headers(header->{
					header.setBearerAuth(securityContext.getTokenString());
					header.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
				})
				.retrieve()
				.bodyToMono(new ParameterizedTypeReference<List<Contenedor>>() {}).block();
	}
	private List<CantidadEmpresa> listarPorEmpresas(List<Contenedor> data){
		List<Cliente> empresas = clientes.buscarTodasLasEmpresas();
		List<CantidadEmpresa> resultado = new ArrayList<CantidadEmpresa>();
		//RECORRE TODA LA LISTA DE CLIENTES
		empresas.stream().forEach(index -> {
			int cantidad = 0;
			//RECORRE LA LISTA DE CONTENEDORES PARA EXTRAER
			for (Contenedor item : data) {
				//SI ES EL MISMO CLIENTE AGREGA UN CONTADOR
				if (index.getNombre().equals(item.getCliente())) {
					cantidad++;
				}
			}
			if (cantidad>0) {
				resultado.add(new CantidadEmpresa(index.getNombre(), cantidad));
			}
		});		
		return resultado;
	}
	private int calcularDiasEstadia(Contenedor data) {
		if (data.getFecha_arribo()==null) {
			return -1;
		}else {
			return Period.between(data.getFecha_arribo(), LocalDate.now()).getDays();
		}
	}	
}
