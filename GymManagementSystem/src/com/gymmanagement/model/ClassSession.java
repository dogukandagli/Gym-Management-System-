package com.gymmanagement.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ClassSession {
    private String name;
    private String description;
    private LocalDateTime dateTime;
    private int capacity;
    private List<Member> members;
    private Coach coach;

    public ClassSession(String name, String description, LocalDateTime dateTime, int capacity, Coach coach) {
        this.name = name;
        this.description = description;
        this.dateTime = dateTime;
        this.capacity = capacity;
        this.coach = coach;
        this.members = new ArrayList<>();
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public List<Member> getMembers() {
        return members;
    }

    public Coach getCoach() {
        return coach;
    }

    public void setCoach(Coach coach) {
        this.coach = coach;
    }

    // Class-specific methods
    public boolean addMember(Member member) {
        if (members.size() >= capacity) {
            return false;
        }
        return members.add(member);
    }

    public boolean removeMember(Member member) {
        return members.remove(member);
    }

    public boolean isFull() {
        return members.size() >= capacity;
    }

    public int getAvailableSpots() {
        return capacity - members.size();
    }

    // Manual JSON serialization
    public String toJson() {
        return String.format("{\"name\":\"%s\",\"description\":\"%s\",\"dateTime\":\"%s\",\"capacity\":%d,\"coachID\":\"%s\"}",
                name, description, dateTime.toString(), capacity, coach != null ? coach.getUserID() : "");
    }

    public static ClassSession fromJson(String json, Coach coach) {
        String[] parts = json.replace("{", "").replace("}", "").replace("\"", "").split(",");
        String name = "", description = "", dateTimeStr = "";
        int capacity = 0;
        for (String part : parts) {
            String[] kv = part.split(":");
            if (kv.length < 2) continue;
            switch (kv[0]) {
                case "name": name = kv[1]; break;
                case "description": description = kv[1]; break;
                case "dateTime": dateTimeStr = kv[1]; break;
                case "capacity": capacity = Integer.parseInt(kv[1]); break;
            }
        }
        return new ClassSession(name, description, java.time.LocalDateTime.parse(dateTimeStr), capacity, coach);
    }
} 