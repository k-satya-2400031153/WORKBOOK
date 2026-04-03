package com.auth.jwt.controller;

import com.auth.jwt.entity.User;
import com.auth.jwt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * EMPLOYEE-only endpoints. Restricted to ROLE_EMPLOYEE by Spring Security.
 *
 * GET /employee/profile — Returns the currently authenticated employee's profile.
 */
@RestController
@RequestMapping("/employee")
@PreAuthorize("hasRole('EMPLOYEE')")
public class EmployeeController {

    @Autowired
    private UserRepository userRepository;

    /**
     * GET /employee/profile
     * Returns the profile of the currently authenticated employee.
     * The username is extracted from the SecurityContext (set by JwtAuthFilter).
     */
    @GetMapping("/profile")
    public ResponseEntity<?> getProfile() {
        // Get the username of the currently authenticated user from SecurityContext
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        User user = userRepository.findByUsername(currentUsername)
                .orElse(null);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "User profile not found."));
        }

        // Return profile info — never expose the password hash
        return ResponseEntity.ok(Map.of(
            "id",       user.getId(),
            "username", user.getUsername(),
            "role",     user.getRole().name(),
            "message",  "Hello, " + user.getUsername() + "! Here is your profile."
        ));
    }
}
