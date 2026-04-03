package com.auth.jwt.config;

import com.auth.jwt.filter.JwtAuthFilter;
import com.auth.jwt.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Spring Security 6 configuration.
 *
 * ── Route access rules ──────────────────────────────────────
 *  Public    : POST /login, GET /h2-console/**
 *  ADMIN only: /admin/**       (ADD / DELETE employee records)
 *  EMPLOYEE  : /employee/**    (profile data)
 *  All others: any authenticated user
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity          // enables @PreAuthorize on controller methods
public class SecurityConfig {

    @Autowired
    private JwtAuthFilter jwtAuthFilter;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    // ── Password Encoder ────────────────────────────────────
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // ── DAO Authentication Provider ─────────────────────────
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    // ── Authentication Manager ──────────────────────────────
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    // ── Security Filter Chain ───────────────────────────────
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // Disable CSRF (fine for stateless REST APIs)
            .csrf(csrf -> csrf.disable())

            // Disable session creation — JWT is stateless
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

            // Allow H2 console frames (X-Frame-Options)
            .headers(headers ->
                headers.frameOptions(frame -> frame.disable()))

            // ── Authorization Rules ──────────────────────────
            .authorizeHttpRequests(auth -> auth
                // Public endpoints
                .requestMatchers("/login").permitAll()
                .requestMatchers("/h2-console/**").permitAll()

                // ADMIN-only endpoints
                .requestMatchers("/admin/**").hasRole("ADMIN")

                // EMPLOYEE-only endpoints
                .requestMatchers("/employee/**").hasRole("EMPLOYEE")

                // All other endpoints require authentication
                .anyRequest().authenticated()
            )

            // Register our JWT filter before the default username/password filter
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
