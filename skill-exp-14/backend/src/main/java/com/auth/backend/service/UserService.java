package com.auth.backend.service;

import com.auth.backend.dto.LoginRequest;
import com.auth.backend.dto.RegisterRequest;
import com.auth.backend.model.User;
import com.auth.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Register a new user. Throws RuntimeException on duplicate username or email.
     */
    public User register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already taken: " + request.getUsername());
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered: " + request.getEmail());
        }
        User newUser = new User(
            request.getUsername(),
            request.getPassword(),   // Plain text for simplicity; use BCrypt in production
            request.getEmail()
        );
        return userRepository.save(newUser);
    }

    /**
     * Validate login credentials. Returns the User if valid, empty otherwise.
     */
    public Optional<User> login(LoginRequest request) {
        return userRepository.findByUsername(request.getUsername())
            .filter(user -> user.getPassword().equals(request.getPassword()));
    }

    /**
     * Fetch user profile by ID.
     */
    public Optional<User> getProfile(Long id) {
        return userRepository.findById(id);
    }
}
