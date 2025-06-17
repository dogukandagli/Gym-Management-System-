package com.gymmanagement.model;

import java.util.ArrayList;
import java.util.List;

import com.gymmanagement.user.Coach;

public class Gym {
    private String name;
    private String gymID;
    private String location;
    private MembershipType category;
    private List<Coach> assignedCoaches;
    private int monthlyEntries;

    public Gym(String name, String gymID, String location, MembershipType category) {
        this.name = name;
        this.gymID = gymID;
        this.location = location;
        this.category = category;
        this.assignedCoaches = new ArrayList<>();
        this.monthlyEntries = 0;
    }

    // === GETTERS & SETTERS ===
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGymID() {
        return gymID;
    }

    public void setGymID(String gymID) {
        this.gymID = gymID;
    }

    // getId alias (opsiyonel)
    public String getId() {
        return gymID;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public MembershipType getCategory() {
        return category;
    }

    public void setCategory(MembershipType category) {
        this.category = category;
    }

    public List<Coach> getAssignedCoaches() {
        return assignedCoaches;
    }

    public void assignCoach(Coach coach) {
        if (!assignedCoaches.contains(coach)) {
            assignedCoaches.add(coach);
        }
    }

    public void removeCoach(Coach coach) {
        assignedCoaches.remove(coach);
    }

    public int getMonthlyEntries() {
        return monthlyEntries;
    }

    public void incrementMonthlyEntries() {
        this.monthlyEntries++;
    }

    public void resetMonthlyEntries() {
        this.monthlyEntries = 0;
    }

    // === JSON METHODS ===

    public String toJson() {
        return String.format(
            "{\"name\":\"%s\",\"gymID\":\"%s\",\"location\":\"%s\",\"category\":\"%s\",\"monthlyEntries\":%d}",
            name, gymID, location, category, monthlyEntries
        );
    }

    public static Gym fromJson(String json) {
        String[] parts = json.replace("{", "")
                             .replace("}", "")
                             .replace("\"", "")
                             .split(",");

        String name = "", gymID = "", location = "";
        MembershipType category = null;
        int monthlyEntries = 0;

        for (String part : parts) {
            String[] kv = part.split(":", 2);
            if (kv.length < 2) continue;

            switch (kv[0].trim()) {
                case "name":
                    name = kv[1].trim();
                    break;
                case "gymID":
                    gymID = kv[1].trim();
                    break;
                case "location":
                    location = kv[1].trim();
                    break;
                case "category":
                    try {
                        category = MembershipType.valueOf(kv[1].trim().toUpperCase());
                    } catch (IllegalArgumentException e) {
                        System.out.println("❌ Geçersiz kategori: " + kv[1]);
                        return null;
                    }
                    break;
                case "monthlyEntries":
                    monthlyEntries = Integer.parseInt(kv[1].trim());
                    break;
            }
        }

        Gym g = new Gym(name, gymID, location, category);
        g.monthlyEntries = monthlyEntries;
        return g;
    }

}
