package com.railwaysystem.backend.controller;

import com.railwaysystem.backend.entity.Role;
import com.railwaysystem.backend.entity.User;
import com.railwaysystem.backend.repository.UserRepository;
import com.railwaysystem.backend.service.AuthService;
import com.railwaysystem.backend.service.OtpService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final UserRepository userRepository;
    private final AuthService authService;
    private final OtpService otpService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserRepository userRepository,
                          AuthService authService,
                          OtpService otpService,
                          PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authService = authService;
        this.otpService = otpService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {

        Optional<User> user = userRepository.findByEmail(request.email());

        if (user.isEmpty()) {
            return "Invalid email or password";
        }

        if (!passwordEncoder.matches(
                request.password(),
                user.get().getPassword())) {
            return "Invalid email or password";
        }

        return "Login successful - Role: " + user.get().getRole();
    }

    @PostMapping("/register/send-otp")
    public String sendOtp(@RequestBody EmailRequest request) {

        if (userRepository.findByEmail(request.email()).isPresent()) {
            return "Email already registered";
        }

        otpService.sendOtp(request.email());

        return "OTP sent successfully";
    }

    @PostMapping("/register/verify-otp")
    public String verifyOtp(@RequestBody OtpRequest request) {

        boolean verified = otpService.verifyOtp(
                request.email(),
                request.otp()
        );

        if (!verified) {
            return "Invalid or expired OTP";
        }

        return "Email verified successfully";
    }

    @PostMapping("/register/complete")
    public String completeRegistration(
            @RequestBody RegisterRequest request) {

        if (!otpService.isEmailVerified(request.email())) {
            return "Please verify your email first";
        }

        try {
            Role selectedRole = Role.valueOf(request.role());

            authService.createUser(
                    request.name(),
                    request.email(),
                    request.password(),
                    selectedRole
            );

            return "Registration successful";

        } catch (IllegalArgumentException e) {
            return "Invalid role selected";

        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    public record LoginRequest(
            String email,
            String password
    ) {
    }

    public record EmailRequest(
            String email
    ) {
    }

    public record OtpRequest(
            String email,
            String otp
    ) {
    }

    public record RegisterRequest(
            String name,
            String email,
            String password,
            String role
    ) {
    }
}