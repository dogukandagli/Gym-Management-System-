package com.gymmanagement.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Coach extends User {
    private int experienceYears;
    private List<String> certifications;
    private String bio;
    private List<LocalDateTime> availableHours;
    private List<ClassSession> assignedClasses;

    public Coach(String userID, String password, String email, String name,
                int experienceYears, String bio) {
        super(userID, password, email, name);
        this.experienceYears = experienceYears;
        this.bio = bio;
        this.certifications = new ArrayList<>();
        this.availableHours = new ArrayList<>();
        this.assignedClasses = new ArrayList<>();
    }

    @Override
    public boolean login(String userID, String password) {
        return getUserID().equals(userID) && getPassword().equals(password);
    }

    // Getters and Setters
    public int getExperienceYears() {
        return experienceYears;
    }

    public void setExperienceYears(int experienceYears) {
        this.experienceYears = experienceYears;
    }

    public List<String> getCertifications() {
        return certifications;
    }

    public void addCertification(String certification) {
        this.certifications.add(certification);
    }

    public void removeCertification(String certification) {
        this.certifications.remove(certification);
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public List<LocalDateTime> getAvailableHours() {
        return availableHours;
    }

    public void addAvailableHour(LocalDateTime time) {
        this.availableHours.add(time);
    }

    public void removeAvailableHour(LocalDateTime time) {
        this.availableHours.remove(time);
    }

    public List<ClassSession> getAssignedClasses() {
        return assignedClasses;
    }

    // Coach-specific methods
    public ClassSession createClass(String name, String description, LocalDateTime dateTime, int capacity) {
        ClassSession newClass = new ClassSession(name, description, dateTime, capacity, this);
        this.assignedClasses.add(newClass);
        return newClass;
    }

    public void updateClass(ClassSession classSession, String name, String description, 
                          LocalDateTime dateTime, int capacity) {
        classSession.setName(name);
        classSession.setDescription(description);
        classSession.setDateTime(dateTime);
        classSession.setCapacity(capacity);
    }

    // Manual JSON serialization
    public String toJson() {
        StringBuilder certs = new StringBuilder("[");
        for (int i = 0; i < certifications.size(); i++) {
            certs.append("\"").append(certifications.get(i)).append("\"");
            if (i < certifications.size() - 1) certs.append(",");
        }
        certs.append("]");
        StringBuilder hours = new StringBuilder("[");
        for (int i = 0; i < availableHours.size(); i++) {
            hours.append("\"").append(availableHours.get(i).toString()).append("\"");
            if (i < availableHours.size() - 1) hours.append(",");
        }
        hours.append("]");
        return String.format("{\"userID\":\"%s\",\"password\":\"%s\",\"email\":\"%s\",\"name\":\"%s\",\"experienceYears\":%d,\"certifications\":%s,\"bio\":\"%s\",\"availableHours\":%s}",
                getUserID(), getPassword(), getEmail(), getName(),
                experienceYears, certs.toString(), bio, hours.toString());
    }

    public static Coach fromJson(String json) {
        // Very basic parsing, assumes well-formed input
        String[] parts = json.replace("{", "").replace("}", "").replace("\"", "").split(",");
        String userID = "", password = "", email = "", name = "", bio = "";
        int experienceYears = 0;
        List<String> certifications = new ArrayList<>();
        List<LocalDateTime> availableHours = new ArrayList<>();
        for (String part : parts) {
            String[] kv = part.split(":");
            if (kv.length < 2) continue;
            switch (kv[0]) {
                case "userID": userID = kv[1]; break;
                case "password": password = kv[1]; break;
                case "email": email = kv[1]; break;
                case "name": name = kv[1]; break;
                case "experienceYears": experienceYears = Integer.parseInt(kv[1]); break;
                case "bio": bio = kv[1]; break;
                case "certifications":
                    String certs = part.substring(part.indexOf("[") + 1, part.indexOf("]"));
                    if (!certs.isEmpty()) {
                        for (String c : certs.split(",")) certifications.add(c.replace("\"", ""));
                    }
                    break;
                case "availableHours":
                    String hours = part.substring(part.indexOf("[") + 1, part.indexOf("]"));
                    if (!hours.isEmpty()) {
                        for (String h : hours.split(",")) availableHours.add(LocalDateTime.parse(h.replace("\"", "")));
                    }
                    break;
            }
        }
        Coach c = new Coach(userID, password, email, name, experienceYears, bio);
        for (String cert : certifications) c.addCertification(cert);
        for (LocalDateTime hour : availableHours) c.addAvailableHour(hour);
        return c;
    }
} 