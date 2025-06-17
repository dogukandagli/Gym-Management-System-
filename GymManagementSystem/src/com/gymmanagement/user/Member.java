package com.gymmanagement.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.gymmanagement.model.MembershipType;
import com.gymmanagement.model.*;

public class Member extends User {
    private MembershipType memberShipType;
    private List<String> activityHistory;
    private Date memberShipStart;
    private Date memberShipEnd;
    private boolean isActive;
    private int height;
    private int weight;
    public int ID;
    private boolean isYearly;
    private List<GymListener> listeners = new ArrayList<>();
    private List<String> notifications = new ArrayList<>();

    public Member(String userID, String password, String email, String name, String role, Gym gym,
                 MembershipType memberShipType, Date memberShipStart, Date memberShipEnd,
                 int height, int weight) {
        super(userID, password, email, name, role, gym);
        this.memberShipType = memberShipType;
        this.activityHistory = new ArrayList<>();
        this.memberShipStart = memberShipStart;
        this.memberShipEnd = memberShipEnd;
        this.isActive = true;
        this.height = height;
        this.weight = weight;
        this.isYearly = false; // Varsayılan olarak aylık
    }

    public Member(String userID, String password, String email, String name, String role, Gym gym,
                 MembershipType memberShipType, Date memberShipStart, Date memberShipEnd,
                 int height, int weight, boolean isYearly) {
        super(userID, password, email, name, role, gym);
        this.memberShipType = memberShipType;
        this.memberShipStart = memberShipStart;
        this.memberShipEnd = memberShipEnd;
        this.isActive = true;
        this.height = height;
        this.weight = weight;
        this.isYearly = isYearly;
    }
    
    public void addNotification(String notification) {
        if (notification != null && !notification.isBlank()) {
            notifications.add(notification);
        }
    }
    public List<String> getNotifications() {
        return new ArrayList<>(notifications); // dış müdahaleye kapalı kopya döner
    }
    public void clearNotifications() {
        notifications.clear();
    }

    

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
    public void notifyClassAdded(ClassSession session) {
        for (GymListener listener : listeners) {
            listener.onClassAdded(session);
        }
    }
    
   public void addListener(GymListener listener) {
	   listeners.add(listener);
   }
   
   

    public void addActivity(String activity) {
        if (activityHistory == null) {
            activityHistory = new ArrayList<>();
        }
        activityHistory.add(activity);
    }

    public List<String> getActivityHistory() {
        if (activityHistory == null) {
            activityHistory = new ArrayList<>();
        }
        return activityHistory;
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

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
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
        String notifStr = notifications.stream()
            .map(n -> "\"" + n.replace("\"", "") + "\"")
            .collect(Collectors.joining(","));

        return String.format(
            "{\"userID\":\"%s\",\"password\":\"%s\",\"email\":\"%s\",\"name\":\"%s\",\"role\":\"%s\"," +
            "\"memberShipType\":\"%s\",\"memberShipStart\":%d,\"memberShipEnd\":%d,\"isActive\":%b," +
            "\"height\":%d,\"weight\":%d,\"gymID\":\"%s\",\"isYearly\":%b,\"notifications\":[%s]}",
            getUserID(), getPassword(), getEmail(), getName(), getRole(),
            memberShipType, memberShipStart.getTime(), memberShipEnd.getTime(), isActive,
            height, weight, getGym() != null ? getGym().getGymID() : "",
            isYearly, notifStr
        );
    }

    // --- JSON parsing güncellendi ---
    public static Member fromJson(String json) {
        String[] parts = json.replace("{", "").replace("}", "")
                             .replace("\"", "").split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

        String userID = "", password = "", email = "", name = "", role = "", gymID = "";
        MembershipType memberShipType = MembershipType.BASIC;
        Date memberShipStart = new Date();
        Date memberShipEnd = new Date();
        boolean isActive = true;
        int height = 0;
        int weight = 0;
        boolean isYearly = false;
        List<String> notifications = new ArrayList<>();

        for (String part : parts) {
            String[] kv = part.split(":", 2);
            if (kv.length < 2) continue;

            String key = kv[0].trim();
            String value = kv[1].trim();

            switch (key) {
                case "userID": userID = value; break;
                case "password": password = value; break;
                case "email": email = value; break;
                case "name": name = value; break;
                case "role": role = value; break;
                case "memberShipType": memberShipType = MembershipType.valueOf(value); break;
                case "memberShipStart": memberShipStart = new Date(Long.parseLong(value)); break;
                case "memberShipEnd": memberShipEnd = new Date(Long.parseLong(value)); break;
                case "isActive": isActive = Boolean.parseBoolean(value); break;
                case "height": height = Integer.parseInt(value); break;
                case "weight": weight = Integer.parseInt(value); break;
                case "gymID": gymID = value; break;
                case "isYearly": isYearly = Boolean.parseBoolean(value); break;
                case "notifications":
                    // JSON dizisinden temizlenmiş liste: ["text1","text2"]
                    value = value.replace("[", "").replace("]", "");
                    if (!value.isBlank()) {
                        for (String n : value.split(",")) {
                            notifications.add(n.trim().replace("\"", ""));
                        }
                    }
                    break;
            }
        }

        Gym gym = com.gymmanagement.database.Database.getInstance().findGymById(gymID);
        Member m = new Member(userID, password, email, name, role, gym,
                memberShipType, memberShipStart, memberShipEnd, height, weight, isYearly);
        m.setActive(isActive);
        for (String note : notifications) {
            m.addNotification(note);
        }
        return m;
    }
} 