package com.v2hms.configuration;

import com.v2hms.entity.UserAppV2;
import com.v2hms.repository.UserAppV2Repository;
import com.v2hms.service.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class JWTFilter extends OncePerRequestFilter {
    private JWTService jwtService;
    private UserAppV2Repository userAppV2Repository;

    public JWTFilter(JWTService jwtService, UserAppV2Repository userAppV2Repository) {
        this.jwtService = jwtService;
        this.userAppV2Repository = userAppV2Repository;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("AUTHORIZATION");
        if(token!=null&&token.startsWith("Bearer ")){
            String tokenval = token.substring(7, token.length());
            System.out.println(tokenval);
            String username= jwtService.getUsername(tokenval);
            System.out.println(username);
            UserAppV2 userAppV2 = userAppV2Repository.findByUsername(username).get();
            UsernamePasswordAuthenticationToken authentication =new UsernamePasswordAuthenticationToken(null,null, Collections.singleton(new SimpleGrantedAuthority(userAppV2.getRole())));
            authentication.setDetails(new WebAuthenticationDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);

        }


        filterChain.doFilter(request,response);
    }

}
