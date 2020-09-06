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
import com.geasp.micro.partes.models.Carga;
import com.geasp.micro.partes.models.Cliente;
import com.geasp.micro.partes.models.Estadia;
import com.geasp.micro.partes.models.Operaciones;
import com.geasp.micro.partes.models.PendientesAlistar;
import com.geasp.micro.partes.models.ResumenCargas;
import com.geasp.micro.partes.models.ResumenPendientes;

@Service
public class CargaService implements IParte {

	@Autowired
	private WebClient.Builder webClientBuilder;
	@Autowired
	private KeycloakSecurityContext securityContext;
	@Autowired
	private ClientesHelper clientesApi;
	
	@Value("${partes.cargas.nombre}")
	private String cargasNombre;
	@Value("${estadia.maximo}")
	private int estadiaMaximo;
	@Value("${partes.operaciones.cargas.entradas}")
	private String tituloEntradas;	
	@Value("${partes.operaciones.cargas.salidas}")
	private String tituloSalidas;
	
	
	private List<Carga> getAll(String url) {
		List<Carga> res =  webClientBuilder.build().get()
				.uri(url)
				.headers(header->{
					header.setBearerAuth(securityContext.getTokenString());
					header.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
				})
				.retrieve()
				.bodyToMono(new ParameterizedTypeReference<List<Carga>>() {}).block();
		if (res !=null) {
			return res;
		}else {
			return new ArrayList<Carga>();
		}
	}	
	
	@Override
	public List<ResumenPendientes> getPendientes() {
		// TODO Auto-generated method stub
		String url = "http://CARGAS/v1/buscarporestados?estados=LISTO_PARA_EXTRAER";
		List<ResumenPendientes> res = new ArrayList<ResumenPendientes>();
		List<Cliente> clientes = clientesApi.buscarTodasLasEmpresas();		
		List<Carga> cargas = getAll(url);
		
		clientes.stream().forEach(index->{
			int total=0;
			int porHabilitar=0;
			int sinEntregar=0;
			int sinDescargar=0;
			int paraExtraer=0;
			for (Carga carga : cargas) {
				if (carga.getCliente().equals(index.getNombre())) {
					total++;
					if (carga.getFecha_habilitacion()==null) {
						porHabilitar++;
					}else if (carga.getFecha_documentos()==null) {
						sinEntregar++;
					}else if(carga.getFecha_arribo()==null) {
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

	public ResumenCargas getParteByDate(LocalDate date) {
		ResumenCargas res = new ResumenCargas(cargasNombre);
		String url = "http://CARGAS/v1/buscarporestados?estados=LISTO_PARA_EXTRAER,EXTRAIDA";
		List<Carga> cargas = getAll(url);
		res.setFecha(date);
		List<CantidadEmpresa> listosParaExtraer = listarCargasPorEmpresas(cargas);
		List<Carga> listaEstadia = new ArrayList<Carga>();
		List<Carga> porHabilitar = new ArrayList<Carga>();
		List<Carga> porEntregar = new ArrayList<Carga>();
		
		List<Carga> listaEntradas = cargas.stream().filter(index->{
			if (index.getFecha_arribo()!=null) {
				return index.getFecha_arribo().equals(date);
			} else {
				return false;
			}
		}).collect(Collectors.toList());
		
		List<Carga> listaSalidas = cargas.stream().filter(index->{
			if (index.getFecha_extraccion() != null) {
				return index.getFecha_extraccion().equals(date); 
			} else {
				return false;
			}
			
		}).collect(Collectors.toList());
		
		List<CantidadEmpresa> resumenEntradas = listarPorEmpresas(listaEntradas);
		List<CantidadEmpresa> resumenSalidas = listarPorEmpresas(listaSalidas);
		for (Carga index : cargas) {
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
		
		res.setTotal(cargas.size());
		res.setListosParaExtraer(listosParaExtraer);
		res.setEnEstadia(enEstadia);
		res.setResumenEntradas(new Operaciones(tituloEntradas, resumenEntradas));
		res.setResumenSalidas(new Operaciones(tituloSalidas, resumenSalidas));
		res.setPendientesAlistar(
				new PendientesAlistar(
						listarPorEmpresas(porHabilitar), 
						listarPorEmpresas(porEntregar)
					));		
		return res;
	}
	
	private List<CantidadEmpresa> listarPorEmpresas(List<Carga> data){
		List<Cliente> empresas = clientesApi.buscarTodasLasEmpresas();
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
	private List<CantidadEmpresa> listarCargasPorEmpresas(List<Carga> data){
		List<Cliente> empresas = clientesApi.buscarTodasLasEmpresas();
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
