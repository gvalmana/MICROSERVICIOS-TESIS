package com.geasp.micro.partes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

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
	
	public static void main(String[] args) {
		SpringApplication.run(PartesApplication.class, args);
	}

}
