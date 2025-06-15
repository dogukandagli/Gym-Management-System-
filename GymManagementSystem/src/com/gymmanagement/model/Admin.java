package com.gymmanagement.model;

import java.util.List;
import java.util.ArrayList;

public class Admin extends User {
    private String role;
    private boolean isActive;

    public Admin(String userID, String password, String email, String name, String role) {
        super(userID, password, email, name);
        this.role = role;
        this.isActive = true;
    }

    @Override
    public boolean login(String userID, String password) {
        return isActive && getUserID().equals(userID) && getPassword().equals(password);
    }

    // Getters and Setters
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    // Admin-specific methods
    public Member addMember(String userID, String password, String email, String name,
                          MembershipType membershipType, float height, double weight) {
        Member newMember = new Member(userID, password, email, name, membershipType,
                                    new java.util.Date(), // start date
                                    new java.util.Date(System.currentTimeMillis() + (365L * 24 * 60 * 60 * 1000)), // 1 year from now
                                    height, weight);
        return newMember;
    }

    public void removeMember(Member member) {
        member.setActive(false);
    }

    public List<Member> manageMembers(List<Member> members) {
        // Implementation for managing members (viewing, filtering, etc.)
        return new ArrayList<>(members);
    }

    public void assignCoach(ClassSession classSession, Coach coach) {
        classSession.setCoach(coach);
    }

    public List<ClassSession> manageClasses(List<ClassSession> classes) {
        // Implementation for managing classes (viewing, filtering, etc.)
        return new ArrayList<>(classes);
    }

    public void controlPayments(Member member, double amount) {
        // Implementation for handling payments
        System.out.println("Processing payment of $" + amount + " for member " + member.getName());
    }

    public void editMembershipPlans() {
        // Implementation for editing membership plans
        System.out.println("Editing membership plans...");
    }

    public Coach addCoach(String userID, String password, String email, String name,
                         int experienceYears, String bio) {
        return new Coach(userID, password, email, name, experienceYears, bio);
    }

    // Manual JSON serialization
    public String toJson() {
        return String.format("{\"userID\":\"%s\",\"password\":\"%s\",\"email\":\"%s\",\"name\":\"%s\",\"role\":\"%s\",\"isActive\":%b}",
                getUserID(), getPassword(), getEmail(), getName(), role, isActive);
    }

    public static Admin fromJson(String json) {
        String[] parts = json.replace("{", "").replace("}", "").replace("\"", "").split(",");
        String userID = "", password = "", email = "", name = "", role = "";
        boolean isActive = true;
        for (String part : parts) {
            String[] kv = part.split(":");
            if (kv.length < 2) continue;
            switch (kv[0]) {
                case "userID": userID = kv[1]; break;
                case "password": password = kv[1]; break;
                case "email": email = kv[1]; break;
                case "name": name = kv[1]; break;
                case "role": role = kv[1]; break;
                case "isActive": isActive = Boolean.parseBoolean(kv[1]); break;
            }
        }
        Admin a = new Admin(userID, password, email, name, role);
        a.setActive(isActive);
        return a;
    }
} 