package com.geasp.micro.partes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.geasp.micro.partes.conf.Calculos;
import com.geasp.micro.partes.conf.ClientesHelper;
import com.geasp.micro.partes.services.ParteCarga;
import com.geasp.micro.partes.services.ParteContenedor;
import com.geasp.micro.partes.services.ParteGuia;

@EnableEurekaClient
@SpringBootApplication
public class PartesApplication {

	@Bean
	@LoadBalanced
	public WebClient.Builder getWebClientBuilder(){
		return WebClient.builder();
	}
	
	@Bean
	@Primary
	@LoadBalanced
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
	
	@Bean
	public Calculos getCalculos() {
		return new Calculos();
	}
	@Bean
	public ParteCarga getParteCargas() {
		return new ParteCarga();
	}
	@Bean
	public ParteGuia getParteGuias() {
		return new ParteGuia();
	}
	@Bean
	public ParteContenedor getParteContenedor() {
		return new ParteContenedor();
	}
	@Bean
	public ClientesHelper getClientesHelper(){
		return new ClientesHelper();
	}
	public static void main(String[] args) {
		SpringApplication.run(PartesApplication.class, args);
	}

}
