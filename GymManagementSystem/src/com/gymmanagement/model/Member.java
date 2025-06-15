package com.gymmanagement.model;

import java.util.Date;
import java.util.List;

public class Member extends User {
    private MembershipType memberShipType;
    private Date memberShipStart;
    private Date memberShipEnd;
    private boolean isActive;
    private float height;
    private double weight;

    public Member(String userID, String password, String email, String name,
                 MembershipType memberShipType, Date memberShipStart, Date memberShipEnd,
                 float height, double weight) {
        super(userID, password, email, name);
        this.memberShipType = memberShipType;
        this.memberShipStart = memberShipStart;
        this.memberShipEnd = memberShipEnd;
        this.isActive = true; // New members are active by default
        this.height = height;
        this.weight = weight;
    }

    @Override
    public boolean login(String userID, String password) {
        // Check if the member is active and credentials match
        return isActive && 
               getUserID().equals(userID) && 
               getPassword().equals(password);
    }

    // Getters and Setters
    public MembershipType getMemberShipType() {
        return memberShipType;
    }

    public void setMemberShipType(MembershipType memberShipType) {
        this.memberShipType = memberShipType;
    }

    public Date getMemberShipStart() {
        return memberShipStart;
    }

    public void setMemberShipStart(Date memberShipStart) {
        this.memberShipStart = memberShipStart;
    }

    public Date getMemberShipEnd() {
        return memberShipEnd;
    }

    public void setMemberShipEnd(Date memberShipEnd) {
        this.memberShipEnd = memberShipEnd;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    // Member-specific methods
    public List<MembershipType> findMemberShipPlans() {
        // find available membership plans logic will be implemented here
        return List.of(MembershipType.values());
    }

    public void viewMembershipPlans() {
        // display membership plans logic will be implemented here
        System.out.println("Available Membership Plans:");
        for (MembershipType type : MembershipType.values()) {
            System.out.println("- " + type);
        }
    }

    public boolean makeReservation(Date date, String facility) {
        if (!isActive) {
            System.out.println("Cannot make reservation: Membership is not active");
            return false;
        }
        // reservation logic will be implemented here
        return true;
    }

    public boolean cancelReservation(String reservationId) {
        if (!isActive) {
            System.out.println("Cannot cancel reservation: Membership is not active");
            return false;
        }
        // cancelling reservation logic will be implemented here
        return true;
    }

    // Manual JSON serialization
    public String toJson() {
        return String.format("{\"userID\":\"%s\",\"password\":\"%s\",\"email\":\"%s\",\"name\":\"%s\",\"memberShipType\":\"%s\",\"memberShipStart\":%d,\"memberShipEnd\":%d,\"isActive\":%b,\"height\":%f,\"weight\":%f}",
                getUserID(), getPassword(), getEmail(), getName(),
                memberShipType.name(),
                memberShipStart.getTime(),
                memberShipEnd.getTime(),
                isActive, height, weight);
    }

    public static Member fromJson(String json) {
        // Very basic parsing, assumes well-formed input
        String[] parts = json.replace("{", "").replace("}", "").replace("\"", "").split(",");
        String userID = "", password = "", email = "", name = "";
        MembershipType memberShipType = MembershipType.BASIC;
        Date memberShipStart = new Date();
        Date memberShipEnd = new Date();
        boolean isActive = true;
        float height = 0;
        double weight = 0;
        for (String part : parts) {
            String[] kv = part.split(":");
            if (kv.length < 2) continue;
            switch (kv[0]) {
                case "userID": userID = kv[1]; break;
                case "password": password = kv[1]; break;
                case "email": email = kv[1]; break;
                case "name": name = kv[1]; break;
                case "memberShipType": memberShipType = MembershipType.valueOf(kv[1]); break;
                case "memberShipStart": memberShipStart = new Date(Long.parseLong(kv[1])); break;
                case "memberShipEnd": memberShipEnd = new Date(Long.parseLong(kv[1])); break;
                case "isActive": isActive = Boolean.parseBoolean(kv[1]); break;
                case "height": height = Float.parseFloat(kv[1]); break;
                case "weight": weight = Double.parseDouble(kv[1]); break;
            }
        }
        Member m = new Member(userID, password, email, name, memberShipType, memberShipStart, memberShipEnd, height, weight);
        m.setActive(isActive);
        return m;
    }
} 