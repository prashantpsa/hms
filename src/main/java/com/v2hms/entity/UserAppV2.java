package com.v2hms.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "user_app_v_2")
public class UserAppV2 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

@Size(min=3, message = "please enter size>=3 charecters")
    @Column(name = "name", nullable = false)
    private String name;

@Email
    @Column(name = "email", nullable = false, unique = true)
    private String email;
@Size(min=10,max = 10, message = "enter 10 digits")
    @Column(name = "mobile", nullable = false, unique = true)
    private String mobile;
@Size(min=3)
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "role", length = 20)
    private String role;

}