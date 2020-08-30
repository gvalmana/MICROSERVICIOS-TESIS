package com.geasp.micro.guias;

import org.keycloak.representations.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import java.util.Optional;


public class AuditorAwareImpl implements AuditorAware<String> {
	
	@Autowired
	private AccessToken token;
	
    @Override
    public Optional<String> getCurrentAuditor(){
    	return Optional.of(token.getPreferredUsername());
    }
}