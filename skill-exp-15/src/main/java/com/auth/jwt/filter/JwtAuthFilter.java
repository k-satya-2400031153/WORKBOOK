package com.auth.jwt.filter;

import com.auth.jwt.security.CustomUserDetailsService;
import com.auth.jwt.security.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * JWT Authentication Filter — runs once per request.
 *
 * Flow:
 *   1. Extract "Authorization: Bearer <token>" header
 *   2. Validate the token using JwtUtil
 *   3. Load UserDetails from DB
 *   4. Set authentication into SecurityContext so Spring Security allows the request
 *
 * If the token is missing/invalid, security context stays empty → 401/403 returned.
 */
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        String token    = null;
        String username = null;

        // ── Step 1: Extract token from header ────────────────────
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            try {
                username = jwtUtil.extractUsername(token);
            } catch (Exception e) {
                // Malformed token — log and continue (security context stays null)
                logger.warn("[JwtAuthFilter] Could not extract username from token: " + e.getMessage());
            }
        }

        // ── Step 2: Authenticate if username found & no existing auth ─
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // ── Step 3: Validate token ───────────────────────────
            if (jwtUtil.validateToken(token)) {
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()   // includes ROLE_ADMIN / ROLE_EMPLOYEE
                        );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // ── Step 4: Set in security context ─────────────
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}
