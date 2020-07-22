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
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.geasp.micro.mercancias.conf.Calculos;
import com.geasp.micro.mercancias.models.CantidadEmpresa;
import com.geasp.micro.mercancias.models.Cliente;
import com.geasp.micro.mercancias.models.Contenedor;
import com.geasp.micro.mercancias.models.Estadia;
import com.geasp.micro.mercancias.models.EstadoMercancias;
import com.geasp.micro.mercancias.models.Operaciones;
import com.geasp.micro.mercancias.models.PendientesAlistar;
import com.geasp.micro.mercancias.repositories.ContenedorRepository;
import com.geasp.micro.mercancias.responses.ResumenContenedores;
import com.geasp.micro.mercancias.responses.ResumenPendientes;

import reactor.core.publisher.Mono;

@Service
@RefreshScope
public class ParteContenedoresService implements IParteService<ResumenContenedores> {

	
	@Autowired
	private KeycloakSecurityContext securityContext;
	
	@Autowired
	private WebClient.Builder webClientBuilder;	
	
	@Value("${partes.contenedores.nombre}")
	private String contenedoresNombre;
	
	@Autowired
	private ContenedorRepository dao;
	
	@Autowired
	private Calculos calculadora;
	
	@Value("${estadia.minimo}")
	private int estadiaMinimo;
	
	@Value("${estadia.maximo}")
	private int estadiaMaximo;
	
	@Value("${partes.operaciones.contenedores.nombre}")
	private String titulo;	
	
	public ResumenContenedores makePartefallback(LocalDate date) {
		return new ResumenContenedores("Error en la confección del parte de los contenedores.");
	}
	
	@Override
//	@HystrixCommand(fallbackMethod = "makePartefallback", commandProperties = {
//			@HystrixProperty(name="execution.isolation.strategy",value="SEMAPHORE"),
//		//	@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
//			@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
//			@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
//			@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000")
//		})
	public ResumenContenedores makeParte(LocalDate date) {
		ResumenContenedores res = new ResumenContenedores(contenedoresNombre);
		List<Contenedor> paraExtraer = dao.findByEstado(EstadoMercancias.LISTO_PARA_EXTRAER);
		List<Contenedor> paraDevolver = dao.findByEstado(EstadoMercancias.EXTRAIDA);		
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
			int edad = calculadora.calcularDiasEstadia(index);
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
			int edad = calculadora.calcularDiasEstadia(index);
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
		res.setResumenEntradas(new Operaciones(resumenOperaciones,titulo));
		
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
	
	private List<CantidadEmpresa> listarPorEmpresas(List<Contenedor> data){
		List<Cliente> clientes = buscarTodasLasEmpresas().block();
		List<CantidadEmpresa> resultado = new ArrayList<CantidadEmpresa>();
		//RECORRE TODA LA LISTA DE CLIENTES
		clientes.stream().forEach(index -> {
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
	
	public List<CantidadEmpresa> listarContenedoresDevolver(){
		List<Contenedor> lista = dao.findByEstado(EstadoMercancias.EXTRAIDA);
		List<CantidadEmpresa> resumenOperaciones = listarPorEmpresas(lista);
		return resumenOperaciones;
	}
	
	public List<ResumenPendientes> listarPendientes(){
		List<ResumenPendientes> res = new ArrayList<ResumenPendientes>();
		List<Cliente> clientes = buscarTodasLasEmpresas().block();
		List<Contenedor> contenedores = dao.findByEstado(EstadoMercancias.LISTO_PARA_EXTRAER);

		clientes.stream().forEach(index->{
			int total=0;
			int porHabilitar=0;
			int sinEntregar=0;
			int sinDescargar=0;
			int paraExtraer=0;
			for (Contenedor contenedor : contenedores) {
				if (contenedor.getCliente().equals(index.getNombre())) {
					total++;
					if (contenedor.getFecha_habilitacion()==null) {
						porHabilitar++;
					}else if (contenedor.getFecha_documentos()==null) {
						sinEntregar++;
					}else if(contenedor.getFecha_arribo()==null) {
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
