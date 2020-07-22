package com.geasp.micro.mercancias.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.keycloak.KeycloakSecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.geasp.micro.mercancias.conf.Calculos;
import com.geasp.micro.mercancias.models.CantidadEmpresa;
import com.geasp.micro.mercancias.models.Cliente;
import com.geasp.micro.mercancias.models.Estadia;
import com.geasp.micro.mercancias.models.EstadoMercancias;
import com.geasp.micro.mercancias.models.Guia;
import com.geasp.micro.mercancias.models.Operaciones;
import com.geasp.micro.mercancias.models.PendientesAlistar;
import com.geasp.micro.mercancias.repositories.GuiaRepository;
import com.geasp.micro.mercancias.responses.ResumenGuias;
import com.geasp.micro.mercancias.responses.ResumenPendientes;

import reactor.core.publisher.Mono;
import org.springframework.http.MediaType;

@Service
@RefreshScope
public class ParteGuiasService implements IParteService<ResumenGuias>{
	
	@Autowired
	private WebClient.Builder webClientBuilder;
	
	@Autowired
	private KeycloakSecurityContext securityContext;
	
	@Value("${partes.guias.nombre}")
	private String guiasNombre;
	@Autowired
	private GuiaRepository dao;
	@Autowired
	private Calculos calculadora;
	
	@Value("${estadia.guias}")
	private int estadiaGuias;
	
	@Value("${partes.operaciones.guias.nombre}")
	private String titulo;
	
	public ResumenGuias makePartefallback(LocalDate date){
		return new ResumenGuias("Error en la confección del parte de las guías aéras.");
	}
	
	@Override
	public ResumenGuias makeParte(LocalDate date) {
		// TODO Auto-generated method stub
		ResumenGuias res = new ResumenGuias(guiasNombre);
		List<Guia> paraExtraer = dao.findByEstado(EstadoMercancias.LISTO_PARA_EXTRAER);
		
		List<Guia> listaEstadia = new ArrayList<Guia>();
		List<Guia> porHabilitar = new ArrayList<Guia>();
		List<Guia> porEntregar = new ArrayList<Guia>();
		
		for (Guia index : paraExtraer) {
			if (calculadora.calcularDiasEntregada(index)>=estadiaGuias) {
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
		res.setResumenEntradas(new Operaciones(resumenOperaciones, titulo));
		
		res.setPendientesAlistar(
				new PendientesAlistar(
						listarPorEmpresas(porHabilitar), 
						listarPorEmpresas(porEntregar)
					));
		return res;
	}

	private List<CantidadEmpresa> listarPorEmpresas(List<Guia> data){
		List<Cliente> clientes = buscarTodasLasEmpresas().block();
		List<CantidadEmpresa> resultado = new ArrayList<CantidadEmpresa>();
		//RECORRE TODA LA LISTA DE CLIENTES
		clientes.stream().forEach(index -> {
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
	
	public List<ResumenPendientes> listarPendientes(){
		List<ResumenPendientes> res = new ArrayList<ResumenPendientes>();
		List<Cliente> clientes = buscarTodasLasEmpresas().block();
		List<Guia> guias = dao.findByEstado(EstadoMercancias.LISTO_PARA_EXTRAER);

		clientes.stream().forEach(index->{
			int total=0;
			int porHabilitar=0;
			int sinEntregar=0;
			int sinDescargar=0;
			int paraExtraer=0;
			for (Guia guia : guias) {
				if (guia.getCliente().equals(index.getNombre())) {
					total++;
					if (guia.getFecha_habilitacion()==null) {
						porHabilitar++;
					}else if (guia.getFecha_documentos()==null) {
						sinEntregar++;
					}else if(guia.getFecha_arribo()==null) {
						sinDescargar++;
					}
					paraExtraer = total-porHabilitar-sinEntregar-sinDescargar;
				}				
			}
			if (total>0) {
				res.add(new ResumenPendientes(index.getNombre(), total, paraExtraer, porHabilitar, sinEntregar, sinDescargar));
			}
		});
		
		return res;
	}
}
