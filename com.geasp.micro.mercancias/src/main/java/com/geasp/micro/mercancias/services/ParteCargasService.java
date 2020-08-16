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
import com.geasp.micro.mercancias.models.Carga;
import com.geasp.micro.mercancias.models.Cliente;
import com.geasp.micro.mercancias.models.Estadia;
import com.geasp.micro.mercancias.models.EstadoMercancias;
import com.geasp.micro.mercancias.models.Operaciones;
import com.geasp.micro.mercancias.models.PendientesAlistar;
import com.geasp.micro.mercancias.repositories.CargaRepository;
import com.geasp.micro.mercancias.responses.ResumenCargas;
import com.geasp.micro.mercancias.responses.ResumenPendientes;
import reactor.core.publisher.Mono;

import org.springframework.http.MediaType;

@Service
@RefreshScope
public class ParteCargasService implements IParteService<ResumenCargas>{
	
	@Autowired
	private KeycloakSecurityContext securityContext;
	
	@Autowired
	private WebClient.Builder webClientBuilder;	
	
	@Value("${partes.cargas.nombre}")
	private String cargasNombre;
	@Autowired
	private CargaRepository dao;
	@Autowired
	private Calculos calculadora;
	
	@Value("${estadia.minimo}")
	private int estadiaMinimo;
	
	@Value("${estadia.maximo}")
	private int estadiaMaximo;
	
	@Value("${partes.operaciones.cargas.nombre}")
	private String titulo;	
	
	public ResumenCargas makePartefallback(LocalDate date){
		return new ResumenCargas("Error en la confección del parte de las cargas agrupadas.");
	}
	
	@Override	
	public ResumenCargas makeParte(LocalDate date) {
		// TODO Auto-generated method stub
		ResumenCargas res = new ResumenCargas(cargasNombre);
		List<Carga> paraExtraer = dao.findByEstado(EstadoMercancias.LISTO_PARA_EXTRAER);
		List<CantidadEmpresa> listosParaExtraer = listarPorEmpresas(paraExtraer);

		List<Carga> listaEstadia = new ArrayList<Carga>();
		List<Carga> porHabilitar = new ArrayList<Carga>();
		List<Carga> porEntregar = new ArrayList<Carga>();
		
		List<Carga> listaEntradas = paraExtraer.stream().filter(index->{
			return index.getFecha_arribo().equals(date); 
		}).collect(Collectors.toList());
		List<CantidadEmpresa> resumenOperaciones = listarPorEmpresas(listaEntradas);
		for (Carga index : paraExtraer) {
			if (calculadora.calcularDiasDesagrupada(index)>=estadiaMaximo) {
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
		res.setResumenEntradas(new Operaciones(resumenOperaciones, titulo));
		res.setPendientesAlistar(
				new PendientesAlistar(
						listarPorEmpresas(porHabilitar), 
						listarPorEmpresas(porEntregar)
					));
		return res;
	}

	private List<CantidadEmpresa> listarPorEmpresas(List<Carga> data){
		List<Cliente> clientes = buscarTodasLasEmpresas().block();
		List<CantidadEmpresa> resultado = new ArrayList<CantidadEmpresa>();
		//RECORRE TODA LA LISTA DE CLIENTES
		clientes.stream().forEach(index -> {
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
		List<Carga> cargas = dao.findByEstado(EstadoMercancias.LISTO_PARA_EXTRAER);

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
		
		return res;
	}	
}
