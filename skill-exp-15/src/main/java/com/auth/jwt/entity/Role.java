package com.auth.jwt.entity;

/**
 * Roles supported by the corporate portal.
 * Spring Security expects "ROLE_" prefix; we add it in CustomUserDetailsService.
 */
public enum Role {
    ADMIN,
    EMPLOYEE
}
