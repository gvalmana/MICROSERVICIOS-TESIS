package com.geasp.micro.empresas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class EmpresasApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmpresasApplication.class, args);
	}

}
