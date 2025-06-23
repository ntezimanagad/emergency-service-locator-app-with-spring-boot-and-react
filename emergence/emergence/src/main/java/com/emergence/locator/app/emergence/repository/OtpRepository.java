package com.emergence.locator.app.emergence.repository;

import java.time.Instant;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emergence.locator.app.emergence.model.Otp;


public interface OtpRepository extends JpaRepository<Otp, Long>{
    Optional<Otp> findByEmailAndPurpose(String email, String purpose);
    void deleteByExpiredAtBefore(Instant time);

}

