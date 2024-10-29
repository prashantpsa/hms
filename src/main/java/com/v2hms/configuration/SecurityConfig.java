package com.v2hms.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

@Configuration
public class SecurityConfig {
    private JWTFilter jwtFilter;

    public SecurityConfig(JWTFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain  (HttpSecurity http) throws Exception{
        http.addFilterBefore(jwtFilter, AuthorizationFilter.class);
        http.csrf().disable().cors().disable();
        http.authorizeHttpRequests().requestMatchers("/api/v2/add/signUp","/api/v2/add/login").
                permitAll().requestMatchers("/api/v2/country","/api/v2/signUp-owner").hasAnyRole("OWNER","ADMIN").anyRequest().authenticated();
        return http.build();


    }


}
