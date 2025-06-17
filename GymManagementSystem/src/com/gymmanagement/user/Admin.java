package com.gymmanagement.user;

import java.util.List;
import java.util.Scanner;

import com.gymmanagement.model.*;
import com.gymmanagement.database.Database;

import java.util.ArrayList;

public class Admin extends User {
    private boolean isActive;
    private static Admin instance; 
    private List<Member> passiveMembers = new ArrayList<>();
    
    public Admin(String userID, String password, String email, String name, String role,Gym gym) {
        super(userID, password, email, name,role,gym);
        this.isActive = true;
    }
    
    public static Admin getInstance() {
        if (instance == null) {
            instance = Database.getInstance().findAdminById("1");
        }
        return instance;
    }


    @Override
    public boolean login(String userID, String password) {
        return isActive && getUserID().equals(userID) && getPassword().equals(password);
    }

    public void pasifMember(Member member) {
        if (!member.isActive()) {
            passiveMembers.add(member);
            System.out.println(" Üye pasif olarak eklendi: " + member.getName());
        } else {
            System.out.println(" Bu üye aktif durumda, pasif üyeler listesine eklenemez.");
        }
    }
    public void printPassiveMembers() {
        System.out.println(" Pasif Üyeler:");
        for (Member m : passiveMembers) {
            System.out.println(m.getUserID()+" - " + m.getName() + " (" + m.getEmail() + ")");
        }
    }
    
    public void removePassiveMember(Member member) {
        if (passiveMembers.remove(member)) {
            System.out.println("✅ Pasif üyeler listesinden silindi: " + member.getName());
        } else {
            System.out.println("❌ Üye pasif listede bulunamadı: " + member.getName());
        }
    }
    
    public Member findPassiveMemberByID(String userID) {
        for (Member m : passiveMembers) {
            if (m.getUserID().equals(userID)) {
                return m;
            }
        }
        return null; 
    }

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
	    return String.format(
	        "{\"userID\":\"%s\",\"password\":\"%s\",\"email\":\"%s\",\"name\":\"%s\",\"role\":\"%s\",\"isActive\":%b,\"gymID\":\"%s\"}",
	        getUserID(), getPassword(), getEmail(), getName(), getRole(), isActive,
	        getGym() != null ? getGym().getGymID() : ""
	    );
	}
   public void listMembersByGymID(String selectedGymID) {
       List<Member> members = Database.getInstance().loadMembers();

       if (members.isEmpty()) {
           System.out.println("Bu Gym ID'ye kayıtlı üye bulunamadı.");
       } else {
           System.out.println("=== " + selectedGymID + " ID'li salonun üyeleri ===");
           boolean foundMembers = false;
           
           for (Member m : members) {
               if (m.getGym() != null && m.getGym().getGymID().equals(selectedGymID)) {
                   System.out.println("- " + m.getUserID() + " | " + m.getName() + " | " + m.getEmail());
                   foundMembers = true;
               }
           }
           
           if (!foundMembers) {
               System.out.println("Bu Gym ID'ye kayıtlı aktif üye bulunamadı.");
           }
       }
   }

    public static Admin fromJson(String json) {
        String[] parts = json.replace("{", "").replace("}", "").replace("\"", "").split(",");
        String userID = "", password = "", email = "", name = "", role = "", gymID = "";
        boolean isActive = true;

        for (String part : parts) {
            String[] kv = part.split(":", 2);
            if (kv.length < 2) continue;

            switch (kv[0].trim()) {
                case "userID": userID = kv[1].trim(); break;
                case "password": password = kv[1].trim(); break;
                case "email": email = kv[1].trim(); break;
                case "name": name = kv[1].trim(); break;
                case "role": role = kv[1].trim(); break;
                case "isActive": isActive = Boolean.parseBoolean(kv[1].trim()); break;
                case "gymID": gymID = kv[1].trim(); break;
            }
        }

        Gym gym = com.gymmanagement.database.Database.getInstance().findGymById(gymID);
        Admin a = new Admin(userID, password, email, name, role,gym);
        a.setActive(isActive);
        return a;
    }

	
} 