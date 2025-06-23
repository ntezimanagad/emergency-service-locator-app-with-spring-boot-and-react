package com.emergence.locator.app.emergence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emergence.locator.app.emergence.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
