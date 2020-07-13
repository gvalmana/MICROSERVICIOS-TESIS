package com.geasp.micro.operaciones.conf;

import java.util.Collections;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DozerMapper {

	@Bean
	public Mapper beanMapper() {
		DozerBeanMapper beanMapper = new DozerBeanMapper();
		beanMapper.setMappingFiles(Collections.singletonList("dozerJdk8Converters.xml"));
		return beanMapper;
	}
}
