package com.geasp.micro.partes.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.geasp.micro.partes.models.Parte;
import com.geasp.micro.partes.services.IParte;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@RestController
@RequestMapping(value = "/parte")
@CrossOrigin(
		origins = "*", 
		methods = {
				RequestMethod.GET, 
				RequestMethod.POST, 
				RequestMethod.PUT, 
				RequestMethod.DELETE, 
				RequestMethod.HEAD, 
				RequestMethod.OPTIONS}, 
		allowedHeaders = "*", 
		allowCredentials = "true" )
public class ParteController {

	@Autowired
	IParte servicio;
	
	@HystrixCommand(fallbackMethod = "getParteByIdFallCallBack", commandProperties = {
			@HystrixProperty(name="execution.isolation.strategy",value="SEMAPHORE"),
		//	@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
			@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
			@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
			@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000")
		})	
	@GetMapping("/fecha={fecha}")
	public ResponseEntity<Parte> getParteById(@PathVariable("fecha") String fecha){		
		return ResponseEntity.ok(servicio.getParteByDate(fecha));
	}
	
	public ResponseEntity<Parte> getParteByIdFallCallBack(@PathVariable("fecha") String fecha){		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(servicio.parteFallCallBack(fecha));
	}
}
