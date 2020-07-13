package com.geasp.com.geasp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/")
public class IndexController {

	@Value("${spring.application.name}")
	private String appName;
	@Value("${spring.application.description}")
	private String appDescription;
	
	@GetMapping
	public ResponseEntity<String> Index() {
		return ResponseEntity.ok("Aplicación: "+appName+" Descripción: "+appDescription);
	}
}