package com.gymmanagement;

import com.gymmanagement.model.*;
import com.gymmanagement.database.Database;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Get database instance
        Database db = Database.getInstance();

        // Create test dates
        Date startDate = new Date();
        Date endDate = new Date(System.currentTimeMillis() + (365L * 24 * 60 * 60 * 1000)); // 1 year from now
        LocalDateTime classDateTime = LocalDateTime.now().plusDays(1); // Tomorrow

        System.out.println("=== Testing Member Creation and Management ===");
        // Create a test member
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

        // Save member to database
        db.addMember(member);

        // Test member functionality
        System.out.println("Member Information:");
        System.out.println("User ID: " + member.getUserID());
        System.out.println("Email: " + member.getEmail());
        System.out.println("Name: " + member.getName());
        System.out.println("Membership Type: " + member.getMemberShipType());
        System.out.println("Height: " + member.getHeight() + " cm");
        System.out.println("Weight: " + member.getWeight() + " kg");
        System.out.println("Is Active: " + member.isActive());

        System.out.println("\n=== Testing Coach Creation and Management ===");
        // Create a test coach
        Coach coach = new Coach(
            "C001",
            "coachpass123",
            "coach@example.com",
            "Mike Trainer",
            5,  // experience years
            "Certified fitness trainer with 5 years of experience"
        );

        // Add certifications and available hours
        coach.addCertification("Personal Training");
        coach.addCertification("Group Fitness");
        coach.addAvailableHour(classDateTime);
        coach.addAvailableHour(classDateTime.plusHours(2));

        // Save coach to database
        db.addCoach(coach);

        // Test coach functionality
        System.out.println("Coach Information:");
        System.out.println("User ID: " + coach.getUserID());
        System.out.println("Name: " + coach.getName());
        System.out.println("Experience Years: " + coach.getExperienceYears());
        System.out.println("Bio: " + coach.getBio());
        System.out.println("Certifications: " + coach.getCertifications());
        System.out.println("Available Hours: " + coach.getAvailableHours());

        System.out.println("\n=== Testing Class Session Creation and Management ===");
        // Create a test class session
        ClassSession classSession = coach.createClass(
            "Morning Yoga",
            "Beginner-friendly yoga class",
            classDateTime,
            20  // capacity
        );

        // Save class to database
        db.addClass(classSession);

        // Test class session functionality
        System.out.println("Class Session Information:");
        System.out.println("Name: " + classSession.getName());
        System.out.println("Description: " + classSession.getDescription());
        System.out.println("DateTime: " + classSession.getDateTime());
        System.out.println("Capacity: " + classSession.getCapacity());
        System.out.println("Coach: " + classSession.getCoach().getName());
        System.out.println("Available Spots: " + classSession.getAvailableSpots());

        // Test adding and removing members
        System.out.println("\nTesting Member Management in Class:");
        System.out.println("Adding member to class: " + classSession.addMember(member));
        System.out.println("Current members in class: " + classSession.getMembers().size());
        System.out.println("Removing member from class: " + classSession.removeMember(member));
        System.out.println("Current members in class: " + classSession.getMembers().size());

        System.out.println("\n=== Testing Admin Functionality ===");
        // Create a test admin
        Admin admin = new Admin(
            "A001",
            "adminpass123",
            "admin@example.com",
            "Admin User",
            "System Administrator"
        );

        // Save admin to database
        db.addAdmin(admin);

        // Test admin functionality
        System.out.println("Admin Information:");
        System.out.println("User ID: " + admin.getUserID());
        System.out.println("Name: " + admin.getName());
        System.out.println("Role: " + admin.getRole());
        System.out.println("Is Active: " + admin.isActive());

        // Test admin operations
        System.out.println("\nTesting Admin Operations:");
        Member newMember = admin.addMember(
            "M002",
            "newpass123",
            "newmember@example.com",
            "Jane Smith",
            MembershipType.BASIC,
            165.0f,
            60.0
        );
        System.out.println("Created new member: " + newMember.getName());
        db.addMember(newMember);

        admin.assignCoach(classSession, coach);
        System.out.println("Assigned coach to class: " + classSession.getCoach().getName());

        admin.controlPayments(member, 99.99);
        admin.editMembershipPlans();

        // Test login functionality for all user types
        System.out.println("\n=== Testing Login Functionality ===");
        System.out.println("Member login (valid): " + member.login("M001", "password123"));
        System.out.println("Member login (invalid): " + member.login("M001", "wrongpass"));
        System.out.println("Coach login (valid): " + coach.login("C001", "coachpass123"));
        System.out.println("Admin login (valid): " + admin.login("A001", "adminpass123"));

        // Test database retrieval
        System.out.println("\n=== Testing Database Retrieval ===");
        System.out.println("Retrieved Member: " + db.findMemberById("M001").getName());
        System.out.println("Retrieved Coach: " + db.findCoachById("C001").getName());
        System.out.println("Retrieved Admin: " + db.findAdminById("A001").getName());
        System.out.println("Retrieved Class: " + db.findClassByName("Morning Yoga").getName());
    }
} 