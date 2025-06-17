package com.gymmanagement.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.gymmanagement.user.Coach;
import com.gymmanagement.user.Member;

import java.time.format.DateTimeFormatter;
public class ClassSession {
    private String name;
    private String description;
    private LocalDateTime dateTime;
    private int capacity;
    private List<Member> members;
    private Coach coach;
    private Gym gym;
    
    public ClassSession(String name, String description, LocalDateTime dateTime, int capacity, Coach coach, Gym gym) {
        this.name = name;
        this.description = description;
        this.dateTime = dateTime;
        this.capacity = capacity;
        this.coach = coach;
        this.members = new ArrayList<>();
        this.gym=gym;
    }

    // Getters and Setters
    public Gym getGym() {
        return gym;
    }
    public void setGym(Gym gym) {
        this.gym = gym;
    }
    
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        String formattedDate = dateTime.format(formatter);

        return String.format(
            "{\"name\":\"%s\",\"description\":\"%s\",\"dateTime\":\"%s\",\"capacity\":%d,\"coachID\":\"%s\",\"gymID\":\"%s\"}",
            name,
            description,
            formattedDate,
            capacity,
            coach != null ? coach.getUserID() : "",
            gym != null ? gym.getGymID() : ""
        );
    }


    public static ClassSession fromJson(String json) {
        String[] parts = json.replace("{", "")
                             .replace("}", "")
                             .replace("\"", "")
                             .split(",");

        String name = "", description = "", dateTimeStr = "", coachID = "", gymID = "";
        int capacity = 0;

        for (String part : parts) {
            String[] kv = part.split(":", 2);
            if (kv.length < 2) continue;

            switch (kv[0].trim()) {
                case "name":
                    name = kv[1].trim();
                    break;
                case "description":
                    description = kv[1].trim();
                    break;
                case "dateTime":
                    dateTimeStr = kv[1].trim();
                    break;
                case "capacity":
                    capacity = Integer.parseInt(kv[1].trim());
                    break;
                case "coachID":
                    coachID = kv[1].trim();
                    break;
                case "gymID":
                    gymID = kv[1].trim();
                    break;
            }
        }

        LocalDateTime parsedDate;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
            parsedDate = LocalDateTime.parse(dateTimeStr, formatter);
        } catch (Exception e) {
            System.out.println("❌ Tarih parse edilemedi: " + dateTimeStr);
            return null;
        }

        Coach coach = com.gymmanagement.database.Database.getInstance().findCoachById(coachID);
        Gym gym = com.gymmanagement.database.Database.getInstance().findGymById(gymID);

        if (gym == null) {
            System.out.println("❌ Gym bulunamadı: " + gymID);
            return null;
        }

        return new ClassSession(name, description, parsedDate, capacity, coach, gym);
    }

}
