package com.v2hms.Controller;

import com.v2hms.entity.UserAppV2;
import com.v2hms.payload.Logindto;
import com.v2hms.repository.UserAppV2Repository;
import com.v2hms.service.UserV2Service;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/v2/add")
public class UserAppV2Controller {
    private UserAppV2Repository userAppV2Repository;
    private UserV2Service userV2Service;


    public UserAppV2Controller(UserAppV2Repository userAppV2Repository, UserV2Service userV2Service) {
        this.userAppV2Repository = userAppV2Repository;
        this.userV2Service = userV2Service;
    }
    @PostMapping("signUp")
    public ResponseEntity<?> addtodb(@Valid @RequestBody UserAppV2 userAppV2, BindingResult result){
        if(result.hasErrors()){
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(),HttpStatus.FORBIDDEN);
        }
        Optional<UserAppV2> OpUsername = userAppV2Repository.findByUsername(userAppV2.getUsername());
        if(OpUsername.isPresent()){
            return new ResponseEntity<>("username already exists", HttpStatus.FORBIDDEN);
        }
        Optional<UserAppV2> OpEmail = userAppV2Repository.findByEmail(userAppV2.getEmail());
        if(OpEmail.isPresent()){
            return new ResponseEntity<>("email already exists", HttpStatus.FORBIDDEN);

        }

        userAppV2.setRole("ROLE_USER");
        String encpw=BCrypt.hashpw(userAppV2.getPassword(),BCrypt.gensalt(5));
        userAppV2.setPassword(encpw);
        userAppV2Repository.save(userAppV2);
        return new ResponseEntity<>(userAppV2,HttpStatus.CREATED);
    }
    @PostMapping("signUp-owner")
    public ResponseEntity<?> addtodbowner(@RequestBody UserAppV2 userAppV2){
        Optional<UserAppV2> OpUsername = userAppV2Repository.findByUsername(userAppV2.getUsername());
        if(OpUsername.isPresent()){
            return new ResponseEntity<>("username already exists", HttpStatus.FORBIDDEN);
        }
        Optional<UserAppV2> OpEmail = userAppV2Repository.findByEmail(userAppV2.getEmail());
        if(OpEmail.isPresent()){
            return new ResponseEntity<>("email already exists", HttpStatus.FORBIDDEN);

        }

        userAppV2.setRole("ROLE_OWNER");
        String encpw=BCrypt.hashpw(userAppV2.getPassword(),BCrypt.gensalt(5));
        userAppV2.setPassword(encpw);
        userAppV2Repository.save(userAppV2);
        return new ResponseEntity<>(userAppV2,HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> logindb(@RequestBody Logindto logindto){
        String token= userV2Service.verifyLogin(logindto);
        System.out.println(token);
        if(token!=null){
            return new ResponseEntity<>(token, HttpStatus.OK);
        }
        return new ResponseEntity<>("no",HttpStatus.UNAUTHORIZED);

    }



}

