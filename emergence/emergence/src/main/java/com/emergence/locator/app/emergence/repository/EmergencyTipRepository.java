package com.emergence.locator.app.emergence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emergence.locator.app.emergence.model.EmergencyTip;

public interface EmergencyTipRepository extends JpaRepository<EmergencyTip, Long> {
}
