package com.railwaysystem.backend.service;

import com.railwaysystem.backend.entity.OtpVerification;
import com.railwaysystem.backend.repository.OtpVerificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class OtpService {

    private final OtpVerificationRepository otpRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String senderEmail;

    public OtpService(OtpVerificationRepository otpRepository) {
        this.otpRepository = otpRepository;
    }

    public void sendOtp(String email) {

        String otp = String.format("%06d",
                new Random().nextInt(1000000));

        OtpVerification verification = new OtpVerification(
                email,
                otp,
                LocalDateTime.now().plusMinutes(5)
        );

        otpRepository.save(verification);

        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(senderEmail);
        message.setTo(email);
        message.setSubject("SmartRail Email Verification OTP");

        message.setText(
                "Your SmartRail verification OTP is: "
                        + otp
                        + "\n\nThis OTP is valid for 5 minutes."
                        + "\n\nSmartRail Team"
        );

        mailSender.send(message);
    }

    public boolean verifyOtp(String email, String otp) {

        OtpVerification verification =
                otpRepository.findTopByEmailOrderByIdDesc(email)
                        .orElse(null);

        if (verification == null) {
            return false;
        }

        if (verification.isVerified()) {
            return true;
        }

        if (verification.getExpiresAt()
                .isBefore(LocalDateTime.now())) {
            return false;
        }

        if (!verification.getOtp().equals(otp)) {
            return false;
        }

        verification.setVerified(true);
        otpRepository.save(verification);

        return true;
    }

    public boolean isEmailVerified(String email) {

        OtpVerification verification =
                otpRepository.findTopByEmailOrderByIdDesc(email)
                        .orElse(null);

        return verification != null
                && verification.isVerified()
                && verification.getExpiresAt()
                        .isAfter(LocalDateTime.now());
    }
}