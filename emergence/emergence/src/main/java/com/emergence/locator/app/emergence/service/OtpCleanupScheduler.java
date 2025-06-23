// OtpCleanupScheduler.java
package com.emergence.locator.app.emergence.service;

import java.time.Instant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.emergence.locator.app.emergence.repository.OtpRepository;

@Component
public class OtpCleanupScheduler {

    @Autowired
    private OtpRepository otpRepository;

    @Scheduled(fixedRate = 60000) // Runs every 1 minute
    public void cleanExpiredOtps() {
        otpRepository.deleteByExpiredAtBefore(Instant.now());
    }
}
