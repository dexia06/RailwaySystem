package com.railwaysystem.backend.controller;

import com.railwaysystem.backend.entity.User;
import com.railwaysystem.backend.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserRepository userRepository,
                          PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {

        Optional<User> user = userRepository.findByEmail(request.email());

        if (user.isEmpty()) {
            return "Invalid email or password";
        }

        if (!passwordEncoder.matches(request.password(), user.get().getPassword())) {
            return "Invalid email or password";
        }

        return "Login successful - Role: " + user.get().getRole();
    }

    public record LoginRequest(String email, String password) {
    }
}