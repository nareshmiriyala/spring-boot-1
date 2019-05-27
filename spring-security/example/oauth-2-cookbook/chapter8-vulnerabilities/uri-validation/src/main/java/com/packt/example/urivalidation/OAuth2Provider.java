package com.packt.example.urivalidation;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
public class OAuth2Provider {

    @EnableAuthorizationServer
    public static class AuthorizationServer
            extends AuthorizationServerConfigurerAdapter {

        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
            clients.inMemory()
                    .withClient("clientapp").secret("123")
                    .scopes("read", "write")
                    .redirectUris("http://localhost:9000/callback")
                    .authorizedGrantTypes("implicit");
        }

    }

    @EnableResourceServer
    public static class ResourceServer
            extends ResourceServerConfigurerAdapter {
        public void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests().anyRequest().authenticated().and()
                    .requestMatchers().antMatchers("/api/**");
        }
    }

}