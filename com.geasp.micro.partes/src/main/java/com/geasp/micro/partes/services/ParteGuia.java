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
import com.geasp.micro.partes.models.Estadia;
import com.geasp.micro.partes.models.Guia;
import com.geasp.micro.partes.models.Operaciones;
import com.geasp.micro.partes.models.PendientesAlistar;
import com.geasp.micro.partes.models.ResumenGuias;

public class ParteGuia {
	@Autowired
	private WebClient.Builder webClientBuilder;
	@Autowired
	private KeycloakSecurityContext securityContext;
	@Autowired
	private ClientesHelper clientes;
	@Value("${estadia.guias}")
	private int estadiaGuias;
	@Value("${partes.guias.nombre}")
	private String guiasNombre;
	@Value("${partes.operaciones.guias.entradas}")
	private String titulo;
	
	public ResumenGuias getResumenGuias(LocalDate date) {
		ResumenGuias res = new ResumenGuias(guiasNombre);
		List<Guia> paraExtraer = getGuias();
		
		List<Guia> listaEstadia = new ArrayList<Guia>();
		List<Guia> porHabilitar = new ArrayList<Guia>();
		List<Guia> porEntregar = new ArrayList<Guia>();
		
		for (Guia index : paraExtraer) {
			if (calcularDiasEntregada(index)>=estadiaGuias) {
				listaEstadia.add(index);
			}
			if (index.getFecha_habilitacion()==null) {
				porHabilitar.add(index);
			} else if(index.getFecha_documentos()==null) {
				porEntregar.add(index);
			}
		}
		
		List<Guia> listaEntradas = paraExtraer.stream().filter(index->{
			return index.getFecha_arribo().equals(date); 
		}).collect(Collectors.toList());
		
		List<CantidadEmpresa> listosParaExtraer = listarPorEmpresas(paraExtraer);		
		List<CantidadEmpresa> ListaenEstadia = listarPorEmpresas(listaEstadia);
		List<CantidadEmpresa> resumenOperaciones = listarPorEmpresas(listaEntradas);
		
		Estadia enEstadia = new Estadia();
		
		enEstadia.setPorHabilitar(porHabilitar.size());
		enEstadia.setOtrasCausas(porEntregar.size());
		enEstadia.setPorExtraccion(listaEstadia.size()-porHabilitar.size()-porEntregar.size());
		enEstadia.setListado(ListaenEstadia);
		
		res.setTotal(paraExtraer.size());
		res.setListosParaExtraer(listosParaExtraer);
		res.setEnEstadia(enEstadia);
		res.setPendientesHabilitar(porHabilitar.size());
		res.setGuiasHabilitadas(paraExtraer.size()-porHabilitar.size());
		res.setResumenEntradas(new Operaciones(titulo, resumenOperaciones));
		
		res.setPendientesAlistar(
				new PendientesAlistar(
						listarPorEmpresas(porHabilitar), 
						listarPorEmpresas(porEntregar)
					));		
		return res;
	}
	private List<Guia> getGuias() {
		return webClientBuilder.build().get()
				.uri("http://MERCANCIAS/guias/estado=LISTO_PARA_EXTRAER")
				.headers(header->{
					header.setBearerAuth(securityContext.getTokenString());
					header.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
				})
				.retrieve()
				.bodyToMono(new ParameterizedTypeReference<List<Guia>>() {}).block();
	}
	private int calcularDiasEntregada(Guia guia) {
		if (guia.getFecha_entrega()==null) {
			return -1;
		}else {
			return Period.between(guia.getFecha_entrega(), LocalDate.now()).getDays();
		}		
	}
	private List<CantidadEmpresa> listarPorEmpresas(List<Guia> data){
		List<Cliente> empresas = clientes.buscarTodasLasEmpresas();
		List<CantidadEmpresa> resultado = new ArrayList<CantidadEmpresa>();
		//RECORRE TODA LA LISTA DE CLIENTES
		empresas.stream().forEach(index -> {
			int cantidad = 0;
			//RECORRE LA LISTA DE CONTENEDORES PARA EXTRAER
			for (Guia item : data) {
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
}
