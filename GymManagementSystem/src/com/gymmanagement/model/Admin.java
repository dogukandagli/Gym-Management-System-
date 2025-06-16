package com.gymmanagement.model;

import java.util.List;

import com.gymmanagement.database.Database;

import java.util.ArrayList;

public class Admin extends User {
    private boolean isActive;

    public Admin(String userID, String password, String email, String name, String role) {
        super(userID, password, email, name,role);
        this.isActive = true;
    }

    @Override
    public boolean login(String userID, String password) {
        return isActive && getUserID().equals(userID) && getPassword().equals(password);
    }

    // Getters and Setters
    

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void addMember(Member member) {
       Database.getInstance().addMember(member);
    }

    public void removeMember(Member member) {
        member.setActive(false);
        Database.getInstance().updateMember(member);
    }

    public List<Member> manageMembers(List<Member> members) {
        List<Member> member = Database.getInstance().loadMembers();
        return member;
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
    public void updateClassSession(ClassSession updatedClass) {
        Database.getInstance().updateClassSession(updatedClass);
    }
   
    public void editMembershipPlans() {
        // Implementation for editing membership plans
        System.out.println("Editing membership plans...");
    }
    
    public void printActiveMemberStats() {
        List<Member> members = Database.getInstance().loadMembers();
        long activeCount = members.stream().filter(Member::isActive).count();
        System.out.println("Aktif üye sayısı: " + activeCount);
    }

    public void addCoach(Coach coach) {
    	Database.getInstance().addCoach(coach);
    }

    public void listAllClasses() {
        List<ClassSession> classes = Database.getInstance().loadClasses();
        for (ClassSession c : classes) {
            System.out.println("- " + c.getName() + " | Koç: " + (c.getCoach() != null ? c.getCoach().getName() : "Yok"));
        }
    }
    public  void listMembers() {
        List<Member> members = Database.getInstance().loadMembers();
        for (Member m : members) {
            System.out.println("- " + m.getUserID() + " | " + m.getName() + " | " + m.getEmail());
        }
   }
   
   public void listCoach() {
        List<Coach> coach = Database.getInstance().loadCoaches();
        for (Coach m : coach) {
            System.out.println("- " + m.getUserID() + " | " + m.getName() + " | " + m.getEmail());
        }
   }
    // Manual JSON serialization
    public String toJson() {
        return String.format("{\"userID\":\"%s\",\"password\":\"%s\",\"email\":\"%s\",\"name\":\"%s\",\"role\":\"%s\",\"isActive\":%b}",
                getUserID(), getPassword(), getEmail(), getName(),getRole(), isActive);
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