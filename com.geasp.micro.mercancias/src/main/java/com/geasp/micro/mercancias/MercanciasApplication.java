package com.geasp.micro.mercancias;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.envers.repository.support.EnversRevisionRepositoryFactoryBean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.geasp.micro.mercancias.conf.Calculos;

@SpringBootApplication
@EnableEurekaClient
@EnableJpaRepositories(repositoryFactoryBeanClass = EnversRevisionRepositoryFactoryBean.class)
public class MercanciasApplication {

	@Bean
	@Primary
	@LoadBalanced
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
	
	@Bean
	@LoadBalanced
	public WebClient.Builder getWebClientBuilder(){
		return WebClient.builder();
	}	
	
	@Bean
	public Calculos getCalculos() {
		return new Calculos();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(MercanciasApplication.class, args);
	}
	
}
