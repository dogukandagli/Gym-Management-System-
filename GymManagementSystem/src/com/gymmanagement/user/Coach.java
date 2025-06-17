package com.gymmanagement.user;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.gymmanagement.database.Database;
import com.gymmanagement.model.*;

public class Coach extends User {
    private int experienceYears;
    private List<String> certifications;
    private String bio;
    private List<LocalDateTime> availableHours;
    private List<ClassSession> assignedClasses;

    public Coach(String userID, String password, String email, String name, String role, Gym gym,
                 int experienceYears, String bio) {
        super(userID, password, email, name,role,gym);
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

    public void addClasses(ClassSession newClass) {
        this.assignedClasses.add(newClass);
    }

    public void updateClass(ClassSession classSession, String name, String description,
                            LocalDateTime dateTime, int capacity) {
        classSession.setName(name);
        classSession.setDescription(description);
        classSession.setDateTime(dateTime);
        classSession.setCapacity(capacity);
    }

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

        return String.format(
        	    "{\"userID\":\"%s\",\"password\":\"%s\",\"email\":\"%s\",\"name\":\"%s\",\"role\":\"%s\",\"gymID\":\"%s\",\"experienceYears\":%d,\"certifications\":%s,\"bio\":\"%s\",\"availableHours\":%s}",
        	    getUserID(), getPassword(), getEmail(), getName(), getRole(),
        	    getGym() != null ? getGym().getGymID() : "", // veya getName()
        	    experienceYears, certs.toString(), bio, hours.toString()
        	);
    }

    public static Coach fromJson(String json) {
        String[] parts = json.replace("{", "")
                             .replace("}", "")
                             .replace("\"", "")
                             .split(",");

        String userID = "", password = "", email = "", name = "", bio = "", role = "Coach" ,gymID = "";
        int experienceYears = 0;
        List<String> certifications = new ArrayList<>();
        List<LocalDateTime> availableHours = new ArrayList<>();

        for (String part : parts) {
            String[] kv = part.split(":", 2);
            if (kv.length < 2) continue;

            switch (kv[0].trim()) {
                case "userID": userID = kv[1].trim(); break;
                case "password": password = kv[1].trim(); break;
                case "email": email = kv[1].trim(); break;
                case "name": name = kv[1].trim(); break;
                case "role": role = kv[1].trim(); break;
                case "gymID": gymID = kv[1].trim(); break;
                case "experienceYears": experienceYears = Integer.parseInt(kv[1].trim()); break;
                case "bio": bio = kv[1].trim(); break;
                case "certifications":
                    String certs = part.substring(part.indexOf("[") + 1, part.indexOf("]"));
                    if (!certs.isEmpty()) {
                        for (String c : certs.split(",")) {
                            certifications.add(c.replace("\"", "").trim());
                        }
                    }
                    break;
                case "availableHours":
                    String hours = part.substring(part.indexOf("[") + 1, part.indexOf("]"));
                    if (!hours.isEmpty()) {
                        for (String h : hours.split(",")) {
                            try {
                                availableHours.add(LocalDateTime.parse(h.replace("\"", "").trim()));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    break;
            }
        }

        Gym gym = Database.getInstance().findGymById(gymID);
        
        Coach c = new Coach(userID, password, email, name, role,gym, experienceYears, bio);
        c.setRole(role);
        for (String cert : certifications) c.addCertification(cert);
        for (LocalDateTime hour : availableHours) c.addAvailableHour(hour);
        return c;
    }
}
