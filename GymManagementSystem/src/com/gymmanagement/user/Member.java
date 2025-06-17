package com.gymmanagement.user;

import java.util.Date;
import java.util.List;

import com.gymmanagement.model.MembershipType;
import com.gymmanagement.model.*;

public class Member extends User {
    private MembershipType memberShipType;
    private Date memberShipStart;
    private Date memberShipEnd;
    private boolean isActive;
    private double height;
    private double weight;
    public int ID;
    private boolean isYearly;

    public Member(String userID, String password, String email, String name, String role, Gym gym,
                 MembershipType memberShipType, Date memberShipStart, Date memberShipEnd,
                 double height, double weight) {
        super(userID, password, email, name, role, gym);
        this.memberShipType = memberShipType;
        this.memberShipStart = memberShipStart;
        this.memberShipEnd = memberShipEnd;
        this.isActive = true;
        this.height = (float)height;
        this.weight = weight;
        this.isYearly = false; // Varsayılan olarak aylık
    }

    public Member(String userID, String password, String email, String name, String role, Gym gym,
                 MembershipType memberShipType, Date memberShipStart, Date memberShipEnd,
                 double height, double weight, boolean isYearly) {
        super(userID, password, email, name, role, gym);
        this.memberShipType = memberShipType;
        this.memberShipStart = memberShipStart;
        this.memberShipEnd = memberShipEnd;
        this.isActive = true;
        this.height = (float)height;
        this.weight = weight;
        this.isYearly = isYearly;
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

    public double getHeight() {
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
    public int getId() {
        return ID;
    }

    public double getMembershipPrice() {
        return isYearly ? memberShipType.getYearlyPrice() : memberShipType.getMonthlyPrice();
    }

    public boolean isYearly() {
        return isYearly;
    }

    public void setYearly(boolean yearly) {
        isYearly = yearly;
    }

    // Member-specific methods
    public List<MembershipType> findMemberShipPlans() {
        // find available membership plans logic will be implemented here
        return List.of(MembershipType.values());
    }

    public void viewMembershipPlans() {
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
        return String.format(
            "{\"userID\":\"%s\",\"password\":\"%s\",\"email\":\"%s\",\"name\":\"%s\",\"role\":\"%s\"," +
            "\"memberShipType\":\"%s\",\"memberShipStart\":%d,\"memberShipEnd\":%d,\"isActive\":%b," +
            "\"height\":%.2f,\"weight\":%.2f,\"gymID\":\"%s\",\"isYearly\":%b}",
            getUserID(), getPassword(), getEmail(), getName(), getRole(),
            memberShipType, memberShipStart.getTime(), memberShipEnd.getTime(), isActive,
            height, weight, getGym() != null ? getGym().getGymID() : "",
            isYearly
        );
    }

    public static Member fromJson(String json) {
        String[] parts = json.replace("{", "").replace("}", "").replace("\"", "").split(",");

        String userID = "", password = "", email = "", name = "", role = "", gymID = "";
        MembershipType memberShipType = MembershipType.BASIC;
        Date memberShipStart = new Date();
        Date memberShipEnd = new Date();
        boolean isActive = true;
        float height = 0;
        double weight = 0;
        boolean isYearly = false;

        for (String part : parts) {
            String[] kv = part.split(":", 2);
            if (kv.length < 2) continue;

            switch (kv[0].trim()) {
                case "userID": userID = kv[1].trim(); break;
                case "password": password = kv[1].trim(); break;
                case "email": email = kv[1].trim(); break;
                case "name": name = kv[1].trim(); break;
                case "role": role = kv[1].trim(); break;
                case "memberShipType": memberShipType = MembershipType.valueOf(kv[1].trim()); break;
                case "memberShipStart": memberShipStart = new Date(Long.parseLong(kv[1].trim())); break;
                case "memberShipEnd": memberShipEnd = new Date(Long.parseLong(kv[1].trim())); break;
                case "isActive": isActive = Boolean.parseBoolean(kv[1].trim()); break;
                case "height": height = Float.parseFloat(kv[1].trim()); break;
                case "weight": weight = Double.parseDouble(kv[1].trim()); break;
                case "gymID": gymID = kv[1].trim(); break;
                case "isYearly": isYearly = Boolean.parseBoolean(kv[1].trim()); break;
            }
        }

        Gym gym = com.gymmanagement.database.Database.getInstance().findGymById(gymID);
        Member m = new Member(userID, password, email, name, role, gym, memberShipType, memberShipStart, memberShipEnd, height, weight, isYearly);
        m.setActive(isActive);
        return m;
    }
} 