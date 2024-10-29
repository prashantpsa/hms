package com.v2hms.repository;

import com.v2hms.entity.UserAppV2;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserAppV2Repository extends JpaRepository<UserAppV2, Long> {

    Optional<UserAppV2> findByUsername(String username);
    Optional<UserAppV2> findByEmail(String email);
}