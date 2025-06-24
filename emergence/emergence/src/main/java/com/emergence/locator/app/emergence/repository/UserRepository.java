package com.emergence.locator.app.emergence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emergence.locator.app.emergence.model.User;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long>{
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String name);
}
