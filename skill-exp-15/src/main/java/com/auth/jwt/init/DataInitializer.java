package com.auth.jwt.init;

import com.auth.jwt.entity.Role;
import com.auth.jwt.entity.User;
import com.auth.jwt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Seeds the in-memory H2 database with test users on startup.
 *
 * Test credentials (use these in Postman):
 * ┌──────────┬───────────┬──────────┐
 * │ Role     │ Username  │ Password │
 * ├──────────┼───────────┼──────────┤
 * │ ADMIN    │ admin     │ admin123 │
 * │ EMPLOYEE │ employee  │ emp123   │
 * └──────────┴───────────┴──────────┘
 *
 * Passwords are stored BCrypt-hashed.
 */
@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        // Seed ADMIN user
        if (!userRepository.existsByUsername("admin")) {
            userRepository.save(new User(
                "admin",
                passwordEncoder.encode("admin123"),
                Role.ADMIN
            ));
            System.out.println("[DataInitializer] Created ADMIN user: admin / admin123");
        }

        // Seed EMPLOYEE user
        if (!userRepository.existsByUsername("employee")) {
            userRepository.save(new User(
                "employee",
                passwordEncoder.encode("emp123"),
                Role.EMPLOYEE
            ));
            System.out.println("[DataInitializer] Created EMPLOYEE user: employee / emp123");
        }

        System.out.println("[DataInitializer] ✅ Database seeded. H2 Console: http://localhost:8080/h2-console");
    }
}
