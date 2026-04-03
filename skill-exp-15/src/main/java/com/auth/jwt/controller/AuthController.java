package com.auth.jwt.controller;

import com.auth.jwt.dto.LoginRequest;
import com.auth.jwt.dto.LoginResponse;
import com.auth.jwt.entity.User;
import com.auth.jwt.repository.UserRepository;
import com.auth.jwt.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * POST /login
 * Validates credentials via Spring Security's AuthenticationManager.
 * On success → generates and returns a signed JWT.
 * On failure → 401 Unauthorized.
 */
@RestController
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            // ── Authenticate credentials using Spring Security ──
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    request.getUsername(),
                    request.getPassword()
                )
            );
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid username or password."));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Authentication failed: " + e.getMessage()));
        }

        // ── Credentials valid — load user to get role ───────────
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow();   // guaranteed to exist after successful auth

        String token = jwtUtil.generateToken(user.getUsername(), user.getRole().name());

        return ResponseEntity.ok(
            new LoginResponse(token, user.getUsername(), user.getRole().name())
        );
    }
}
