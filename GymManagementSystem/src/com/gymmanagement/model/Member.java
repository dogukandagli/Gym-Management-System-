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
} 