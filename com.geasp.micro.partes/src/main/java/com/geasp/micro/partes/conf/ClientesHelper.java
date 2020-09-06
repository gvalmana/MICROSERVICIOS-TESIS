package com.geasp.micro.partes.conf;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.keycloak.KeycloakSecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import com.geasp.micro.partes.models.Cliente;


/*CLASE PARA CREAR UN BEAN QUE VA A BUSCAR LA LISTA DE CLIENTES EN UNA PETICION HTTP*/
public class ClientesHelper {
	@Autowired
	private WebClient.Builder webClientBuilder;
	
	@Autowired
	private KeycloakSecurityContext securityContext;
	
	public List<Cliente> buscarTodasLasEmpresas() {
		List<Cliente> clientes = webClientBuilder.build().get()
				.uri("http://EMPRESAS/v1/clientes/")
				.headers(header->{
					header.setBearerAuth(securityContext.getTokenString());
					header.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
				})
				.retrieve()
				.bodyToMono(new ParameterizedTypeReference<List<Cliente>>() {}).block();
		if (clientes != null) {
			return clientes;
		} else {
			return new ArrayList<Cliente>();
		}
	}
}
