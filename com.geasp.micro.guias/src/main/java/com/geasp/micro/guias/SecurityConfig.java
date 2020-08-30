package com.geasp.micro.guias;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.KeycloakConfiguration;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
import org.keycloak.adapters.springsecurity.client.KeycloakClientRequestFactory;
import org.keycloak.adapters.springsecurity.client.KeycloakRestTemplate;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;



@KeycloakConfiguration
public class SecurityConfig extends KeycloakWebSecurityConfigurerAdapter
{
	
    /**
     * Use {@link KeycloakAuthenticationProvider}
     *
     * @param auth
     * @throws Exception
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
 
        SimpleAuthorityMapper grantedAuthorityMapper = new SimpleAuthorityMapper();
        grantedAuthorityMapper.setPrefix("ROLE_");
        grantedAuthorityMapper.setConvertToUpperCase(true);
 
        KeycloakAuthenticationProvider keycloakAuthenticationProvider = keycloakAuthenticationProvider();
        keycloakAuthenticationProvider.setGrantedAuthoritiesMapper(grantedAuthorityMapper);
        auth.authenticationProvider(keycloakAuthenticationProvider);
    }

	@Bean
	@Override
	protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
		return new NullAuthenticatedSessionStrategy();
	}
	
    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
		super.configure(http);
		http.cors().and().authorizeRequests()
			.antMatchers("/v2/**").permitAll()
			//.anyRequest().authenticated()
			.anyRequest().permitAll()
		.and().csrf().disable();
    }
    
    @Bean
    @Scope(scopeName = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public AccessToken getAccessToken() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        return ((KeycloakAuthenticationToken) request.getUserPrincipal()).getAccount().getKeycloakSecurityContext().getToken();
    }
    
	@Autowired
	public KeycloakClientRequestFactory keycloakClientRequestFactory;

	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public KeycloakRestTemplate keycloakRestTemplate(){
		return new KeycloakRestTemplate(keycloakClientRequestFactory);
	}
	
	   /**
  * Allows to inject requests scoped wrapper for {@link KeycloakSecurityContext}.
  *
  * Returns the {@link KeycloakSecurityContext} from the Spring
  * {@link ServletRequestAttributes}'s {@link Principal}.
  * <p>
  * The principal must support retrieval of the KeycloakSecurityContext, so at
  * this point, only {@link KeycloakPrincipal} values and
  * {@link KeycloakAuthenticationToken} are supported.
  *
  * @return the current <code>KeycloakSecurityContext</code>
  */

 @Bean
 @Scope(scopeName = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
 public KeycloakSecurityContext provideKeycloakSecurityContext() {

     ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
     Principal principal = attributes.getRequest().getUserPrincipal();
     if (principal == null) {
         return null;
     }

     if (principal instanceof KeycloakAuthenticationToken) {
         principal = Principal.class.cast(KeycloakAuthenticationToken.class.cast(principal).getPrincipal());
     }

     if (principal instanceof KeycloakPrincipal) {
         return KeycloakPrincipal.class.cast(principal).getKeycloakSecurityContext();
     }

     return null;
 }	
}
