package com.auth.jwt.dto;

/** Request body for POST /admin/add — add a new employee */
public class EmployeeRequest {
    private String username;
    private String password;
    private String role;       // "ADMIN" or "EMPLOYEE"

    public EmployeeRequest() {}

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}
