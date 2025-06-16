package com.gymmanagement.model;


public class LoginRequest {
    public String email;
    public String password;
    public User loggedInUser;
    public String role; // "admin", "member", "coach"...

    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}