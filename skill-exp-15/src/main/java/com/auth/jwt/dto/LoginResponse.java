package com.auth.jwt.dto;

/** Response body for POST /login — contains the JWT token */
public class LoginResponse {
    private String token;
    private String username;
    private String role;
    private String message;

    public LoginResponse(String token, String username, String role) {
        this.token = token;
        this.username = username;
        this.role = role;
        this.message = "Login successful";
    }

    public String getToken()    { return token; }
    public String getUsername() { return username; }
    public String getRole()     { return role; }
    public String getMessage()  { return message; }
}
