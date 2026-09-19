package com.railwaysystem.backend.config;

import com.railwaysystem.backend.entity.Role;
import com.railwaysystem.backend.entity.User;
import com.railwaysystem.backend.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner createUsers(UserRepository userRepository,
                                   PasswordEncoder passwordEncoder) {

        return args -> {

            if (userRepository.findByEmail("admin@smartrail.com").isEmpty()) {
                userRepository.save(
                    new User(
                        "SmartRail Admin",
                        "admin@smartrail.com",
                        passwordEncoder.encode("Admin@123"),
                        Role.ADMIN
                    )
                );
            }

            if (userRepository.findByEmail("officer@smartrail.com").isEmpty()) {
                userRepository.save(
                    new User(
                        "Railway Officer",
                        "officer@smartrail.com",
                        passwordEncoder.encode("Officer@123"),
                        Role.OFFICER
                    )
                );
            }

            if (userRepository.findByEmail("maintenance@smartrail.com").isEmpty()) {
                userRepository.save(
                    new User(
                        "Maintenance Staff",
                        "maintenance@smartrail.com",
                        passwordEncoder.encode("Maintenance@123"),
                        Role.MAINTENANCE
                    )
                );
            }
        };
    }
}