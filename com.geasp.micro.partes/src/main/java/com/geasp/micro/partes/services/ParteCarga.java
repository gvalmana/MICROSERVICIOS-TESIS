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
import com.geasp.micro.partes.models.Carga;
import com.geasp.micro.partes.models.Cliente;
import com.geasp.micro.partes.models.Estadia;
import com.geasp.micro.partes.models.Operaciones;
import com.geasp.micro.partes.models.PendientesAlistar;
import com.geasp.micro.partes.models.ResumenCargas;

public class ParteCarga {
	
	@Autowired
	private WebClient.Builder webClientBuilder;
	@Autowired
	private KeycloakSecurityContext securityContext;
	@Autowired
	private ClientesHelper clientes;
	
	@Value("${partes.cargas.nombre}")
	private String nombre;
	@Value("${estadia.maximo}")
	private int estadiaMaximo;
	@Value("${partes.operaciones.cargas.nombre}")
	private String tituloCargas;	
	
	public ResumenCargas getResumenCargas(LocalDate date) {
		ResumenCargas res = new ResumenCargas(nombre);
		List<Carga> paraExtraer = getCargas();
		
		List<CantidadEmpresa> listosParaExtraer = listarCargasPorEmpresas(paraExtraer);
		List<Carga> listaEstadia = new ArrayList<Carga>();
		List<Carga> porHabilitar = new ArrayList<Carga>();
		List<Carga> porEntregar = new ArrayList<Carga>();
		
		List<Carga> listaEntradas = paraExtraer.stream().filter(index->{
			return index.getFecha_arribo().equals(date); 
		}).collect(Collectors.toList());
		
		List<CantidadEmpresa> resumenOperaciones = listarPorEmpresas(listaEntradas);
		
		for (Carga index : paraExtraer) {
			if (calcularDiasDesagrupada(index)>=estadiaMaximo) {
				listaEstadia.add(index);
			}
			if (index.getFecha_habilitacion()==null) {
				porHabilitar.add(index);
			} else if(index.getFecha_documentos()==null) {
				porEntregar.add(index);
			}
		}
		List<CantidadEmpresa> ListaenEstadia = listarPorEmpresas(listaEstadia);
		
		Estadia enEstadia = new Estadia();
		
		enEstadia.setPorHabilitar(porHabilitar.size());
		enEstadia.setOtrasCausas(porEntregar.size());
		enEstadia.setPorExtraccion(listaEstadia.size()-porHabilitar.size()-porEntregar.size());
		enEstadia.setListado(ListaenEstadia);
		
		res.setTotal(paraExtraer.size());
		res.setListosParaExtraer(listosParaExtraer);
		res.setEnEstadia(enEstadia);
		res.setResumenEntradas(new Operaciones(tituloCargas, resumenOperaciones));
		res.setPendientesAlistar(
				new PendientesAlistar(
						listarPorEmpresas(porHabilitar), 
						listarPorEmpresas(porEntregar)
					));		
		return res;
		
	}

	private List<Carga> getCargas() {
		return webClientBuilder.build().get()
				.uri("http://MERCANCIAS/cargas/estado=LISTO_PARA_EXTRAER")
				.headers(header->{
					header.setBearerAuth(securityContext.getTokenString());
					header.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
				})
				.retrieve()
				.bodyToMono(new ParameterizedTypeReference<List<Carga>>() {}).block();
	}
	
	private List<CantidadEmpresa> listarCargasPorEmpresas(List<Carga> data){
		List<Cliente> empresas = clientes.buscarTodasLasEmpresas();
		List<CantidadEmpresa> resultado = new ArrayList<CantidadEmpresa>();
		//RECORRE TODA LA LISTA DE CLIENTES
		empresas.stream().forEach(index -> {
			int cantidad = 0;
			//RECORRE LA LISTA DE CONTENEDORES PARA EXTRAER
			for (Carga item : data) {
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
	private List<CantidadEmpresa> listarPorEmpresas(List<Carga> data){
		List<Cliente> empresas = clientes.buscarTodasLasEmpresas();
		List<CantidadEmpresa> resultado = new ArrayList<CantidadEmpresa>();
		//RECORRE TODA LA LISTA DE CLIENTES
		empresas.stream().forEach(index -> {
			int cantidad = 0;
			//RECORRE LA LISTA DE CONTENEDORES PARA EXTRAER
			for (Carga item : data) {
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
	
	private int calcularDiasDesagrupada(Carga carga) {
		if (carga.getFecha_desagrupe()==null) {
			return -1;
		}else {
			return Period.between(carga.getFecha_desagrupe(), LocalDate.now()).getDays();
		}
	}	
}
