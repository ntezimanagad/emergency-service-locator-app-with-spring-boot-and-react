package com.emergence.locator.app.emergence.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.emergence.locator.app.emergence.model.Otp;
import com.emergence.locator.app.emergence.repository.OtpRepository;

@Service
public class OtpService {

    private static final int EXPIRE_IN_MINUTES = 5;

    @Autowired
    private OtpRepository otpRepository;

    @Autowired
    private EmailService emailService;

    public void generateOtp(String email, String purpose) {
        String otpCode = String.format("%06d", new Random().nextInt(999999));
        Instant now = Instant.now();
        Instant expire = now.plus(EXPIRE_IN_MINUTES, ChronoUnit.MINUTES);

        // Optional: delete previous unused OTPs
        deleteOtp(email, purpose);

        Otp otp = new Otp(email, otpCode, now, expire, purpose);
        otpRepository.save(otp);

        emailService.sendEmail(email, "Your OTP Code", "Your OTP is " + otpCode);
    }

    public boolean validateOtp(String email, String otpCode, String purpose) {
        Optional<Otp> optionalOtp = otpRepository.findByEmailAndPurpose(email, purpose);

        if (optionalOtp.isEmpty()) return false;

        Otp otp = optionalOtp.get();
        boolean isExpired = otp.getExpiredAt().isBefore(Instant.now());
        boolean isMatch = otp.getOtpCode().equals(otpCode);

        if (!isMatch || isExpired) {
            deleteOtp(email, purpose);
            return false;
        }

        // valid and matched
        deleteOtp(email, purpose);
        return true;
    }

    @Transactional
    public void deleteOtp(String email, String purpose) {
        otpRepository.findByEmailAndPurpose(email, purpose)
            .ifPresent(otpRepository::delete);
    }
}
