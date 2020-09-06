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
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.geasp.micro.partes.conf.ClientesHelper;
import com.geasp.micro.partes.models.CantidadEmpresa;
import com.geasp.micro.partes.models.Cliente;
import com.geasp.micro.partes.models.Estadia;
import com.geasp.micro.partes.models.Guia;
import com.geasp.micro.partes.models.Operaciones;
import com.geasp.micro.partes.models.PendientesAlistar;
import com.geasp.micro.partes.models.ResumenGuias;
import com.geasp.micro.partes.models.ResumenPendientes;

@Service
public class GuiaService implements IParte{
	
	@Autowired
	private WebClient.Builder webClientBuilder;
	@Autowired
	private KeycloakSecurityContext securityContext;
	@Autowired
	private ClientesHelper clientesApi;
	@Value("${estadia.guias}")
	private int estadiaGuias;
	@Value("${partes.guias.nombre}")
	private String guiasNombre;
	@Value("${partes.operaciones.guias.entradas}")
	private String tituloEntradas;	
	@Value("${partes.operaciones.guias.salidas}")
	private String tituloSalidas;
	
	private List<Guia> getAll(String url) {
		List<Guia> res =  webClientBuilder.build().get()
				.uri(url)
				.headers(header->{
					header.setBearerAuth(securityContext.getTokenString());
					header.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
				})
				.retrieve()
				.bodyToMono(new ParameterizedTypeReference<List<Guia>>() {}).block();
		if (res !=null) {
			return res;
		}else {
			return new ArrayList<Guia>();
		}
	}	
	
	@Override
	public List<ResumenPendientes> getPendientes() {
		// TODO Auto-generated method stub
		String url = "http://GUIAS/v1/buscarporestados?estados=LISTO_PARA_EXTRAER";
		List<ResumenPendientes> res = new ArrayList<ResumenPendientes>();
		List<Cliente> clientes = clientesApi.buscarTodasLasEmpresas();		
		List<Guia> guias = getAll(url);
		
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
		return res.stream().collect(Collectors.toList());
	}

	public ResumenGuias getParteByDate(LocalDate date) {
		ResumenGuias res = new ResumenGuias(guiasNombre);
		String url = "http://CARGAS/v1/buscarporestados?estados=LISTO_PARA_EXTRAER,EXTRAIDA";
		res.setFecha(date);
		List<Guia> guias = getAll(url);
		
		List<Guia> listaEstadia = new ArrayList<Guia>();
		List<Guia> porHabilitar = new ArrayList<Guia>();
		List<Guia> porEntregar = new ArrayList<Guia>();
		
		for (Guia index : guias) {
			if (calcularDiasEntregada(index)>=estadiaGuias) {
				listaEstadia.add(index);
			}
			if (index.getFecha_habilitacion()==null) {
				porHabilitar.add(index);
			} else if(index.getFecha_documentos()==null) {
				porEntregar.add(index);
			}
		}
		
		List<Guia> listaEntradas = guias.stream().filter(index->{
			if (index.getFecha_arribo()!=null) {
				return index.getFecha_arribo().equals(date);
			} else {
				return false;
			}
		}).collect(Collectors.toList());
		
		List<Guia> listaSalidas = guias.stream().filter(index->{
			if (index.getFecha_extraccion()!=null) {
				return index.getFecha_extraccion().equals(date);
			} else {
				return false;
			}
		}).collect(Collectors.toList());
		
		List<CantidadEmpresa> listosParaExtraer = listarPorEmpresas(guias);		
		List<CantidadEmpresa> ListaenEstadia = listarPorEmpresas(listaEstadia);
		List<CantidadEmpresa> resumenEntradas = listarPorEmpresas(listaEntradas);
		List<CantidadEmpresa> resumenSalidas = listarPorEmpresas(listaSalidas);
		
		Estadia enEstadia = new Estadia();
		
		enEstadia.setPorHabilitar(porHabilitar.size());
		enEstadia.setOtrasCausas(porEntregar.size());
		enEstadia.setPorExtraccion(listaEstadia.size()-porHabilitar.size()-porEntregar.size());
		enEstadia.setListado(ListaenEstadia);
		
		res.setTotal(guias.size());
		res.setListosParaExtraer(listosParaExtraer);
		res.setEnEstadia(enEstadia);
		res.setPendientesHabilitar(porHabilitar.size());
		res.setGuiasHabilitadas(guias.size()-porHabilitar.size());
		res.setResumenEntradas(new Operaciones(tituloEntradas, resumenEntradas));
		res.setResumenSalidas(new Operaciones(tituloSalidas, resumenSalidas));
		res.setPendientesAlistar(
				new PendientesAlistar(
						listarPorEmpresas(porHabilitar), 
						listarPorEmpresas(porEntregar)
					));		
		return res;
	}
	private int calcularDiasEntregada(Guia guia) {
		if (guia.getFecha_entrega()==null) {
			return -1;
		}else {
			return Period.between(guia.getFecha_entrega(), LocalDate.now()).getDays();
		}		
	}
	private List<CantidadEmpresa> listarPorEmpresas(List<Guia> data){
		List<Cliente> empresas = clientesApi.buscarTodasLasEmpresas();
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
