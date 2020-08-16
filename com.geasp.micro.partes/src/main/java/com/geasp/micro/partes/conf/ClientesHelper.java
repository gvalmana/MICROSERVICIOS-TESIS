package com.geasp.micro.partes.conf;

import java.util.Collections;
import java.util.List;

import org.keycloak.KeycloakSecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import com.geasp.micro.partes.models.Cliente;

public class ClientesHelper {
	@Autowired
	private WebClient.Builder webClientBuilder;
	
	@Autowired
	private KeycloakSecurityContext securityContext;
	
	public List<Cliente> buscarTodasLasEmpresas() {
		return webClientBuilder.build().get()
				.uri("http://EMPRESAS/v1/clientes/")
				.headers(header->{
					header.setBearerAuth(securityContext.getTokenString());
					header.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
				})
				.retrieve()
				.bodyToMono(new ParameterizedTypeReference<List<Cliente>>() {}).block();
	}
}
