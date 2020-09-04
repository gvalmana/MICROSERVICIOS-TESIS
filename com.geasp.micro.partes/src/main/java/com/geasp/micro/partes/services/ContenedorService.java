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
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.geasp.micro.partes.conf.ClientesHelper;
import com.geasp.micro.partes.models.CantidadEmpresa;
import com.geasp.micro.partes.models.Cliente;
import com.geasp.micro.partes.models.Contenedor;
import com.geasp.micro.partes.models.Estadia;
import com.geasp.micro.partes.models.Operaciones;
import com.geasp.micro.partes.models.PendientesAlistar;
import com.geasp.micro.partes.models.ResumenContenedores;
import com.geasp.micro.partes.models.ResumenPendientes;

@Service
@RefreshScope
public class ContenedorService implements IParte{

	@Autowired
	private WebClient.Builder webClientBuilder;
	
	@Autowired
	private KeycloakSecurityContext securityContext;
	
	@Autowired
	private ClientesHelper clientesApi;
	
	@Value("${partes.contenedores.nombre}")
	private String contenedoresNombre;
	@Value("${estadia.minimo}")
	private int estadiaMinimo;	
	@Value("${estadia.maximo}")
	private int estadiaMaximo;
	
	@Value("${partes.operaciones.contenedores.entradas}")
	private String tituloEntradas;	
	@Value("${partes.operaciones.contenedores.salidas}")
	private String tituloSalidas;
	
	private List<Contenedor> getAll() {
		List<Contenedor> res =  webClientBuilder.build().get()
				.uri("http://CONTENEDORES/v1")
				.headers(header->{
					header.setBearerAuth(securityContext.getTokenString());
					header.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
				})
				.retrieve()
				.bodyToMono(new ParameterizedTypeReference<List<Contenedor>>() {}).block();
		if (res !=null) {
			return res;
		}else {
			return new ArrayList<Contenedor>();
		}
	}
	
	private List<Contenedor> getPorDevolver() {
		List<Contenedor> res =  webClientBuilder.build().get()
				.uri("http://CONTENEDORES/v1/buscarporestado?estado=EXTRAIDA")
				.headers(header->{
					header.setBearerAuth(securityContext.getTokenString());
					header.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
				})
				.retrieve()
				.bodyToMono(new ParameterizedTypeReference<List<Contenedor>>() {}).block();
		if (res !=null) {
			return res;
		}else {
			return new ArrayList<Contenedor>();
		}
	}
	
	private List<Contenedor> getPorExtraer() {
		List<Contenedor> res = webClientBuilder.build().get()
				.uri("http://CONTENEDORES/v1/buscarporestado?estado=LISTO_PARA_EXTRAER")
				.headers(header->{
					header.setBearerAuth(securityContext.getTokenString());
					header.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
				})
				.retrieve()
				.bodyToMono(new ParameterizedTypeReference<List<Contenedor>>() {}).block();
		if (res !=null) {
			return res;
		}else {
			return new ArrayList<Contenedor>();
		}		
	}
	
	private List<CantidadEmpresa> listarPorEmpresas(List<Contenedor> data){
		List<Cliente> clientes = clientesApi.buscarTodasLasEmpresas();
		List<CantidadEmpresa> resultado = new ArrayList<CantidadEmpresa>();
		if (data != null && !data.isEmpty()) {
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
			return resultado.stream().collect(Collectors.toList());			
		}else {
			return resultado;
		}

	}
	
	private int calcularDiasEstadia(Contenedor data) {
		if (data.getFecha_arribo()==null) {
			return -1;
		}else {
			return Period.between(data.getFecha_arribo(), LocalDate.now()).getDays();
		}
	}
	
	@Override
	public List<ResumenPendientes> getPendientes(){
		List<ResumenPendientes> res = new ArrayList<ResumenPendientes>();
		List<Cliente> clientes = clientesApi.buscarTodasLasEmpresas();
		List<Contenedor> contenedores = getPorExtraer();

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
		
		return res.stream().collect(Collectors.toList());
	}	
	
	public List<CantidadEmpresa> listarContenedoresDevolver(){
		List<Contenedor> lista = getPorDevolver();
		List<CantidadEmpresa> resumenOperaciones = listarPorEmpresas(lista);
		return resumenOperaciones.stream().collect(Collectors.toList());
	}
	
	public ResumenContenedores getParteByDate(LocalDate date) {
		ResumenContenedores res = new ResumenContenedores(contenedoresNombre);
		
		List<Contenedor> contenedores = getAll();
		
		List<Contenedor> paraExtraer = new ArrayList<Contenedor>();
		List<Contenedor> paraDevolver = new ArrayList<Contenedor>();
		
		contenedores.stream().forEach(index->{
			if (index.getEstado().equals("Extraido")) {
				paraDevolver.add(index);
			} else if(index.getEstado().equals("Listo para extraer")) {
				paraExtraer.add(index);
			}
		});
		
		List<CantidadEmpresa> listosParaExtraer = listarPorEmpresas(paraExtraer);
		List<CantidadEmpresa> pendientesParaDevolver = listarPorEmpresas(paraDevolver);
		List<Contenedor> listaEstadia = new ArrayList<Contenedor>();
		List<Contenedor> listaRiesgo = new ArrayList<Contenedor>();
				
		List<Contenedor> listaEntradas = contenedores.stream().filter(index->{
			if (index.getFecha_arribo()!=null) {
				return index.getFecha_arribo().equals(date) && index.getEstado().equals("Listo para extraer");
			} else {
				return false;
			}
		}).collect(Collectors.toList());
		
		List<Contenedor> listaSalidas = contenedores.stream().filter(index->{
			if (index.getFecha_extraccion()!=null) {
				return index.getFecha_extraccion().equals(date) && index.getEstado().equals("Extraido");
			} else {
				return false;
			}
		}).collect(Collectors.toList());
		
		List<CantidadEmpresa> resumenEntradas = listarPorEmpresas(listaEntradas);
		List<CantidadEmpresa> resumenSalidas = listarPorEmpresas(listaSalidas);
		
		int cantidadRiesgoExtraer = 0;
		int cantidadRiesgoParadevolver =0;		
		int cantdiadEstadiaOtros=0;
		int cantidadRiesgoOtros=0;		
		int cantidadEstadiaParadevolver=0;
		int cantidadEstadiaExtraer=0;		
		
		if (paraExtraer != null && !paraExtraer.isEmpty()) {
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
		}
		
		if (paraDevolver != null && !paraDevolver.isEmpty()) {
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
		
		res.setFecha(date);
		
		res.setTotal(paraExtraer.size()+paraDevolver.size());
		res.setCantidadParaExtraer(paraExtraer.size());
		res.setCantidadParaDevolver(paraDevolver.size());
		
		res.setEnEstadia(enEstadia);
		res.setEnRiesgo(enRiesgo);
		res.setListosParaExtraer(listosParaExtraer);
		res.setPendientesDevolver(pendientesParaDevolver);
		res.setResumenEntradas(new Operaciones(tituloEntradas, resumenEntradas));
		res.setResumenSalidas(new Operaciones(tituloSalidas, resumenSalidas));
		
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
}
