package com.auth.jwt.controller;

import com.auth.jwt.dto.EmployeeRequest;
import com.auth.jwt.entity.Role;
import com.auth.jwt.entity.User;
import com.auth.jwt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * ADMIN-only endpoints. Spring Security restricts these to ROLE_ADMIN.
 *
 * POST /admin/add    — Add a new employee or admin user to the system.
 * DELETE /admin/delete/{id} — Remove a user record by ID.
 */
@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")   // double-guards all methods; Security config is primary
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * POST /admin/add
     * Adds a new user (EMPLOYEE or ADMIN) to the system.
     * Only accessible by authenticated ADMIN users.
     */
    @PostMapping("/add")
    public ResponseEntity<?> addEmployee(@RequestBody EmployeeRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("error", "Username already exists: " + request.getUsername()));
        }

        Role role;
        try {
            role = Role.valueOf(request.getRole().toUpperCase());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Invalid role. Use 'ADMIN' or 'EMPLOYEE'."));
        }

        User newUser = new User(
            request.getUsername(),
            passwordEncoder.encode(request.getPassword()),
            role
        );
        User saved = userRepository.save(newUser);

        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
            "message", "User added successfully",
            "userId",   saved.getId(),
            "username", saved.getUsername(),
            "role",     saved.getRole().name()
        ));
    }

    /**
     * DELETE /admin/delete/{id}
     * Deletes a user by their database ID.
     * Only accessible by authenticated ADMIN users.
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id) {
        if (!userRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "User with id " + id + " not found."));
        }
        userRepository.deleteById(id);
        return ResponseEntity.ok(Map.of(
            "message", "User with id " + id + " deleted successfully."
        ));
    }
}
