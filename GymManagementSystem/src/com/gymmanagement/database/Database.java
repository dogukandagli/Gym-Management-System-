package com.gymmanagement.database;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import com.gymmanagement.model.ClassSession;
import com.gymmanagement.model.Gym;
import com.gymmanagement.user.Admin;
import com.gymmanagement.user.Coach;
import com.gymmanagement.user.Member;
import com.gymmanagement.user.User;

public class Database {
    private static Database instance;
    private final String BASE_DIR = System.getProperty("user.dir") + "/GymManagementSystem";
    private final String MEMBERS_FILE = BASE_DIR + "/data/Member.json";
    private final String COACHES_FILE = BASE_DIR + "/data/coach.json";
    private final String ADMINS_FILE = BASE_DIR + "/data/Admin.json";
    private final String CLASSES_FILE = BASE_DIR + "/data/classes.json";
    private final String GYMS_FILE = BASE_DIR + "/data/gyms.json";


    private Database() {
        createDataDirectory();
    }

    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    private void createDataDirectory() {
        File directory = new File(BASE_DIR + "/data");
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    // Member operations
    public void saveGyms(List<Gym> gyms) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(GYMS_FILE))) {
            for (Gym g : gyms) {
                writer.write(g.toJson());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public List<Gym> loadGyms() {
        List<Gym> gyms = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(GYMS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                gyms.add(Gym.fromJson(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return gyms;
    }
    public void addGym(Gym gym) {
        List<Gym> gyms = loadGyms();
        gyms.add(gym);
        saveGyms(gyms);
    }
    public Gym findGymById(String id) {
        for (Gym g : loadGyms()) {
            if (g.getId().equalsIgnoreCase(id)) {
                return g;
            }
        }
        return null;
    }

    public void saveMembers(List<Member> members) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(MEMBERS_FILE))) {
            for (Member m : members) {
                writer.write(m.toJson());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Member> loadMembers() {
        List<Member> members = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(MEMBERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                members.add(Member.fromJson(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return members;
    }

    public void addMember(Member member) {
        List<Member> members = loadMembers();
        members.add(member);
        saveMembers(members);
    }

    public Member findMemberById(String userId) {
        for (Member m : loadMembers()) {
            if (m.getUserID().equals(userId)) {
                return m;
            }
        }
        return null;
    }

    // Coach operations
    public void saveCoaches(List<Coach> coaches) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(COACHES_FILE))) {
            for (Coach c : coaches) {
                writer.write(c.toJson());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Coach> loadCoaches() {
        List<Coach> coaches = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(COACHES_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                coaches.add(Coach.fromJson(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return coaches;
    }

    public void addCoach(Coach coach) {
        List<Coach> coaches = loadCoaches();
        coaches.add(coach);
        saveCoaches(coaches);
    }

    public Coach findCoachById(String userId) {
        for (Coach c : loadCoaches()) {
            if (c.getUserID().equals(userId)) {
                return c;
            }
        }
        return null;
    }

    // Admin operations
    public void saveAdmins(List<Admin> admins) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ADMINS_FILE))) {
            for (Admin a : admins) {
                writer.write(a.toJson());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Admin> loadAdmins() {
        List<Admin> admins = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(ADMINS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                admins.add(Admin.fromJson(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return admins;
    }

    public void addAdmin(Admin admin) {
        List<Admin> admins = loadAdmins();
        admins.add(admin);
        saveAdmins(admins);
    }

    public Admin findAdminById(String userId) {
        for (Admin a : loadAdmins()) {
            if (a.getUserID().equals(userId)) {
                return a;
            }
        }
        return null;
    }

    // ClassSession operations
    public void saveClasses(List<ClassSession> classes) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CLASSES_FILE))) {
            for (ClassSession c : classes) {
            	if (c != null) {
                    writer.write(c.toJson());
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<ClassSession> loadClasses() {
        List<ClassSession> classes = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(CLASSES_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                classes.add(ClassSession.fromJson(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return classes;
    }

    public void addClass(ClassSession classSession) {
        List<ClassSession> classes = loadClasses();
        classes.add(classSession);
        saveClasses(classes);
    }

    public ClassSession findClassByName(String name) {
        for (ClassSession c : loadClasses()) {
            if (c.getName().equalsIgnoreCase(name)) {
                return c;
            }
        }
        return null;
    }

    public User getUserByEmail(String email) {
        for (Member m : loadMembers()) {
            if (m.getEmail().equalsIgnoreCase(email)) return m;
        }
        for (Admin a : loadAdmins()) {
            if (a.getEmail().equalsIgnoreCase(email)) return a;
        }
        for (Coach c : loadCoaches()) {
            if (c.getEmail().equalsIgnoreCase(email)) return c;
        }
        return null;
    }
    
    public void updateClassSession(ClassSession updatedClass) {
        List<ClassSession> classes = loadClasses();
        for (int i = 0; i < classes.size(); i++) {
            if (classes.get(i).getName().equalsIgnoreCase(updatedClass.getName())) {
                classes.set(i, updatedClass);
                break;
            }
        }
        saveClasses(classes);
    }
    
    public void updateMember(Member updateMember) {
        List<Member> members = loadMembers();
        for (int i = 0; i < members.size(); i++) {
            if (members.get(i).getName().equalsIgnoreCase(updateMember.getName())) {
            	members.set(i, updateMember);
                break;
            }
        }
        saveMembers(members);
    }
    
    public void updateCoach(Coach updateCoach) {
        List<Coach> coach = loadCoaches();
        for (int i = 0; i < coach.size(); i++) {
            if (coach.get(i).getName().equalsIgnoreCase(updateCoach.getName())) {
            	coach.set(i, updateCoach);
                break;
            }
        }
        saveCoaches(coach);
    }
}
