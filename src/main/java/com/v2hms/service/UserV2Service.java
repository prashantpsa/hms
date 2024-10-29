package com.v2hms.service;

import com.v2hms.entity.UserAppV2;
import com.v2hms.payload.Logindto;
import com.v2hms.repository.UserAppV2Repository;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserV2Service {
    private JWTService jwtService;
    private UserAppV2Repository userAppV2Repository;

    public UserV2Service(JWTService jwtService, UserAppV2Repository userAppV2Repository) {
        this.jwtService = jwtService;
        this.userAppV2Repository = userAppV2Repository;
    }

    public String verifyLogin(Logindto logindto){
        Optional<UserAppV2> byUsername = userAppV2Repository.findByUsername(logindto.getUsername());
        if(byUsername.isPresent()){
            UserAppV2 userAppV2 = byUsername.get();
            if (BCrypt.checkpw(logindto.getPassword(),userAppV2.getPassword())){
                return jwtService.generateToken(logindto.getUsername());
            }

        }
    return null;

}

    }






