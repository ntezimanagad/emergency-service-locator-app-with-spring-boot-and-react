package com.emergence.locator.app.emergence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emergence.locator.app.emergence.model.Blacklist;


public interface TokenBlacklistRepository extends JpaRepository<Blacklist, String> {
    boolean existsByToken(String token);
}
