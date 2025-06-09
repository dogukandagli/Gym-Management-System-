package com.gymmanagement;

import com.gymmanagement.model.Member;
import com.gymmanagement.model.MembershipType;
import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Create a test member
        Date startDate = new Date();
        Date endDate = new Date(System.currentTimeMillis() + (365L * 24 * 60 * 60 * 1000)); // 1 year from now
        
        Member member = new Member(
            "M001",
            "password123",
            "member@example.com",
            "John Doe",
            MembershipType.PREMIUM,
            startDate,
            endDate,
            175.5f,  // height in cm
            75.5     // weight in kg
        );

        // Test getters
        System.out.println("=== Testing Member Information ===");
        System.out.println("User ID: " + member.getUserID());
        System.out.println("Email: " + member.getEmail());
        System.out.println("Name: " + member.getName());
        System.out.println("Membership Type: " + member.getMemberShipType());
        System.out.println("Height: " + member.getHeight() + " cm");
        System.out.println("Weight: " + member.getWeight() + " kg");
        System.out.println("Is Active: " + member.isActive());

        // Test login
        System.out.println("\n=== Testing Login ===");
        System.out.println("Valid login: " + member.login("M001", "password123"));
        System.out.println("Invalid login: " + member.login("M001", "wrongpassword"));

        // Test membership plans
        System.out.println("\n=== Testing Membership Plans ===");
        List<MembershipType> plans = member.findMemberShipPlans();
        System.out.println("Available plans: " + plans);
        member.viewMembershipPlans();

        // Test reservations
        System.out.println("\n=== Testing Reservations ===");
        Date reservationDate = new Date(System.currentTimeMillis() + (24 * 60 * 60 * 1000)); // tomorrow
        System.out.println("Making reservation: " + member.makeReservation(reservationDate, "Fitness Room"));
        System.out.println("Cancelling reservation: " + member.cancelReservation("RES001"));

        // Test deactivating membership
        System.out.println("\n=== Testing Membership Deactivation ===");
        member.setActive(false);
        System.out.println("Is Active after deactivation: " + member.isActive());
        System.out.println("Can make reservation when inactive: " + member.makeReservation(reservationDate, "Fitness Room"));
    }
} 