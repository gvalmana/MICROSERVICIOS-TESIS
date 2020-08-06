package com.geasp.micro.empresas.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

@Service
@RefreshScope
public class ImportadoraService {
	
	@Value("${importadoras}")
	private List<String> lista;
	
	public List<String> listar(){
		return lista;
	}
}
