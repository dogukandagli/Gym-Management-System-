package com.gymmanagement.model;

import java.util.ArrayList;
import java.util.List;

public class Gym {
    private String name;
    private String gymID;
    private String location;
    private String category;
    private List<Coach> assignedCoaches;
    private int monthlyEntries;

    public Gym(String name, String gymID, String location, String category) {
        this.name = name;
        this.gymID = gymID;
        this.location = location;
        this.category = category;
        this.assignedCoaches = new ArrayList<>();
        this.monthlyEntries = 0;
    }

    // Getters and Setters
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
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
} 