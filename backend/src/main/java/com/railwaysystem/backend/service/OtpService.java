package com.railwaysystem.backend.service;

import com.railwaysystem.backend.entity.OtpVerification;
import com.railwaysystem.backend.repository.OtpVerificationRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.Random;

@Service
public class OtpService {

    private final OtpVerificationRepository otpRepository;
@Value("${BREVO_API_KEY}")
private String brevoApiKey;

    @Value("${spring.mail.username}")
    private String senderEmail;

    public OtpService(OtpVerificationRepository otpRepository) {
        this.otpRepository = otpRepository;
    }

    public void sendOtp(String email) {

        String otp = String.format(
                "%06d",
                new Random().nextInt(1000000)
        );

        OtpVerification verification = new OtpVerification(
                email,
                otp,
                LocalDateTime.now().plusMinutes(5)
        );

        otpRepository.save(verification);

        String jsonBody =
                "{"
                + "\"sender\":{"
                + "\"name\":\"SmartRail\","
                + "\"email\":\"" + senderEmail + "\""
                + "},"
                + "\"to\":[{"
                + "\"email\":\"" + email + "\""
                + "}],"
                + "\"subject\":\"SmartRail Email Verification OTP\","
                + "\"textContent\":\"Your SmartRail verification OTP is: "
                + otp
                + "\\n\\nThis OTP is valid for 5 minutes."
                + "\\n\\nSmartRail Team\""
                + "}";

        try {

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(
                            "https://api.brevo.com/v3/smtp/email"
                    ))
                    .header("accept", "application/json")
                    .header("api-key", brevoApiKey)
                    .header("content-type", "application/json")
                    .POST(
                            HttpRequest.BodyPublishers
                                    .ofString(jsonBody)
                    )
                    .build();

            HttpClient client = HttpClient.newHttpClient();

            HttpResponse<String> response =
                    client.send(
                            request,
                            HttpResponse.BodyHandlers.ofString()
                    );

            if (response.statusCode() < 200
                    || response.statusCode() >= 300) {

                throw new RuntimeException(
                        "Brevo API failed: "
                        + response.statusCode()
                        + " "
                        + response.body()
                );
            }

        } catch (IOException e) {

            throw new RuntimeException(
                    "Failed to send OTP email",
                    e
            );

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();

            throw new RuntimeException(
                    "OTP email request interrupted",
                    e
            );
        }
    }

    public boolean verifyOtp(String email, String otp) {

        OtpVerification verification =
                otpRepository
                        .findTopByEmailOrderByIdDesc(email)
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
                otpRepository
                        .findTopByEmailOrderByIdDesc(email)
                        .orElse(null);

        return verification != null
                && verification.isVerified()
                && verification.getExpiresAt()
                        .isAfter(LocalDateTime.now());
    }
}