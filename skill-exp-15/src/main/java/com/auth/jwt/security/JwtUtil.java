package com.auth.jwt.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

/**
 * Utility class for generating, parsing, and validating JWT tokens.
 *
 * Token contains:
 *   - subject  : username
 *   - claim "role" : "ADMIN" or "EMPLOYEE"
 *   - iat (issued-at)
 *   - exp (expiration)
 */
@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration.ms}")
    private long expirationMs;

    // ── Build the signing key from the configured secret ────
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    /**
     * Generate a signed JWT token for the given username and role.
     */
    public String generateToken(String username, String role) {
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Extract the username (subject) from a JWT token.
     */
    public String extractUsername(String token) {
        return getClaims(token).getSubject();
    }

    /**
     * Extract the role claim from a JWT token.
     */
    public String extractRole(String token) {
        return getClaims(token).get("role", String.class);
    }

    /**
     * Validate that a token is well-formed, correctly signed, and not expired.
     * Returns true if valid; false otherwise.
     */
    public boolean validateToken(String token) {
        try {
            getClaims(token);   // throws if invalid/expired
            return true;
        } catch (ExpiredJwtException e) {
            System.err.println("[JWT] Token expired: " + e.getMessage());
        } catch (MalformedJwtException e) {
            System.err.println("[JWT] Malformed token: " + e.getMessage());
        } catch (SignatureException e) {
            System.err.println("[JWT] Invalid signature: " + e.getMessage());
        } catch (JwtException e) {
            System.err.println("[JWT] Token error: " + e.getMessage());
        }
        return false;
    }

    // ── Parse all claims from a token ───────────────────────
    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
