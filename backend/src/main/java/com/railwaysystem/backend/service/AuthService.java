package com.railwaysystem.backend.service;

import com.railwaysystem.backend.entity.Role;
import com.railwaysystem.backend.entity.User;
import com.railwaysystem.backend.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean emailExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public User createUser(
            String name,
            String email,
            String password,
            Role role) {

        if (emailExists(email)) {
            throw new RuntimeException("Email already registered");
        }

        if (!isValidPassword(password)) {
            throw new RuntimeException(
                    "Password must contain at least 8 characters, 1 uppercase, 1 lowercase, 1 digit and 1 special character"
            );
        }

        User user = new User(
                name,
                email,
                passwordEncoder.encode(password),
                role
        );

        return userRepository.save(user);
    }

    private boolean isValidPassword(String password) {

        if (password == null || password.length() < 8) {
            return false;
        }

        boolean uppercase = password.matches(".*[A-Z].*");
        boolean lowercase = password.matches(".*[a-z].*");
        boolean digit = password.matches(".*[0-9].*");
        boolean special = password.matches(".*[^a-zA-Z0-9].*");

        return uppercase && lowercase && digit && special;
    }
}