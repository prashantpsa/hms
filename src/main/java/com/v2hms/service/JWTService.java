package com.v2hms.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JWTService {
    private Algorithm algorithm;

    @Value("${jwt.algorithm.key}")
    private String algkey;

    @Value("${jwt.issuer}")
    private String issuer;

    @Value("${jwt.expiry.duration}")
    private int expirytime;

    @PostConstruct
    public void postConstruct(){
       algorithm= Algorithm.HMAC256(algkey);
    }

    public String generateToken(String username){
        return JWT.create().withClaim("name",username).
                withExpiresAt(new Date(System.currentTimeMillis()+expirytime)).
                withIssuer(issuer).sign(algorithm);

    }


    public String getUsername(String tokenval) {
        DecodedJWT decodedJWT= JWT.require(algorithm).withIssuer(issuer).build().verify(tokenval);
        return decodedJWT.getClaim("name").asString();

    }
}
