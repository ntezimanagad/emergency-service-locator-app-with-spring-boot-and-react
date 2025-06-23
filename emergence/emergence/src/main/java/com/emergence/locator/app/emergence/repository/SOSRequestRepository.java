package com.emergence.locator.app.emergence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emergence.locator.app.emergence.model.SOSRequest;

public interface SOSRequestRepository extends JpaRepository<SOSRequest, Long> {
}
