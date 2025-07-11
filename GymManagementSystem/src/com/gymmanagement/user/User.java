package com.gymmanagement.user;

import com.gymmanagement.model.Gym;
import com.gymmanagement.model.*;
public abstract class User {
    private String userID;
    private String password;
    private String email;
    private String name;
    private String role;
    private Gym gym;
    
    public User(String userID, String password, String email, String name,String role,Gym gym) {
        this.userID = userID;
        this.password = password;
        this.email = email;
        this.name = name;
        this.role = role;
        this.gym=gym;
    }
    
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
    
    public Gym getGym() {
        return gym;
    }
    public void setGym(Gym gym) {
        this.gym = gym;
    }
    // Abstract method for login
    public abstract boolean login(String userID, String password);

    // Getters and Setters
    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
} 