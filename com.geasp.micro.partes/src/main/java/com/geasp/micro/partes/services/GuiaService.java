package com.geasp.micro.partes.services;

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
import com.geasp.micro.partes.models.Cliente;
import com.geasp.micro.partes.models.Guia;
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
	private String titulo;
	
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

}
