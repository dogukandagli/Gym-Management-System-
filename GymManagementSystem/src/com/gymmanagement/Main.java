package com.gymmanagement;

import com.gymmanagement.model.*;
import com.gymmanagement.user.Admin;
import com.gymmanagement.user.Member;
import com.gymmanagement.user.Coach;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import com.gymmanagement.database.Database;

public class Main {
    public static void main(String[] args) {
    	   Scanner scanner = new Scanner(System.in);
    	   boolean flag = true;
    	
        while (flag) {
            System.out.println("\n==== Gym Management System ====");
            System.out.println("1. Giriş Yap");
            System.out.println("2. Kayıt Ol");
            System.out.println("3. Çıkış");
            System.out.print("Seçiminizi girin: ");
            
            String input = scanner.nextLine();

            switch (input) {
                case "1":
                    handleLogin(scanner);
                    break;
                case "2":
                    System.out.println("Kayıt işlemi başlatılıyor...");
                    addMember();
                    break;
                case "3":
                    System.out.println("Programdan çıkılıyor...");
                    flag = false;
                    break;
                default:
                    System.out.println("Geçersiz seçim! Lütfen 1, 2 veya 3 girin.");
                    break;
            }
        }
    }
    
    private static void handleLogin(Scanner scanner) {
        System.out.println("\nGiriş işlemi başlatılıyor...");
        
        System.out.println("\n=== Kullanıcı Tipi Seçimi ===");
        System.out.println("1. Admin Girişi");
        System.out.println("2. Üye Girişi");
        System.out.println("3. Eğitmen Girişi");
        System.out.println("4. Geri Dön");
        System.out.print("Seçiminizi girin: ");
        
        String userType = scanner.nextLine();
        String role = "";
        
        switch (userType) {
            case "1":
                role = "Admin";
                break;
            case "2":
                role = "Member";
                break;
            case "3":
                role = "Coach";
                break;
            case "4":
                return;
            default:
                System.out.println("Geçersiz seçim!");
                return;
        }
        
        System.out.println("\n=== GYM Management Login ===");
        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Şifre: ");
        String password = scanner.nextLine();
        
        LoginRequest request = new LoginRequest(email, password);
        BaseHandler chain = new EmailandPasswordHandler();
        chain.setNext(new RoleVerificationHandler(role)).setNext(new MemberShipActivityHandler());
        
        if (chain.handle(request)) {
            if (request.loggedInUser instanceof Admin) {
                System.out.println("\n✅ Giriş Başarılı! Admin olarak giriş yapıyorsunuz...");
                AdminPanel.AdminPanelShow((Admin) request.loggedInUser);
            } else if (request.loggedInUser instanceof Member) {
                System.out.println("\n✅ Giriş Başarılı! Üye olarak giriş yapıyorsunuz...");
                UserPanel.UserPanelShow((Member) request.loggedInUser);
            } else if (request.loggedInUser instanceof Coach) {
                System.out.println("\n✅ Giriş Başarılı! Eğitmen olarak giriş yapıyorsunuz...");
                CoachPanel.CoachPanelShow((Coach) request.loggedInUser);
            }
        } else {
            System.out.println("\n❌ Giriş başarısız! Email veya şifre hatalı.");
        }
    }
    
    private static void addMember() {
        Scanner scanner = new Scanner(System.in);

        String userID = Database.getInstance().getNextUserID();
        System.out.println("Kullanıcı ID: " + userID + " (Otomatik atandı)");
        
        System.out.print("Şifre: ");
        String password = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Ad Soyad: ");
        String name = scanner.nextLine();

        String role = "Member"; 

        System.out.print("Üyelik tipi seçin (BASIC, PREMIUM, STUDENT): ");
        MembershipType membershipType = null;
        while (membershipType == null) {
            try {
                String typeInput = scanner.nextLine().toUpperCase();
                membershipType = MembershipType.valueOf(typeInput);
            } catch (IllegalArgumentException e) {
                System.out.print("❌ Geçersiz giriş. Tekrar girin (BASIC, PREMIUM, STUDENT): ");
            }
        }

        System.out.println("\nÜyelik Süresi Seçin:");
        System.out.println("1. Aylık Üyelik (" + membershipType.getMonthlyPrice() + " TL)");
        System.out.println("2. Yıllık Üyelik (" + membershipType.getYearlyPrice() + " TL)");
        System.out.print("Seçiminiz (1/2): ");
        
        boolean isYearly = false;
        while (true) {
            String choice = scanner.nextLine();
            if (choice.equals("1")) {
                isYearly = false;
                break;
            } else if (choice.equals("2")) {
                isYearly = true;
                break;
            } else {
                System.out.print("Geçersiz seçim! Lütfen 1 veya 2 girin: ");
            }
        }
        
        System.out.println("\nSeçtiğiniz üyelik tipine uygun spor salonları:");
        List<Gym> Gyms = Database.getInstance().loadGyms();
        for (Gym gym : Gyms) {
            if (membershipType.ordinal() >= gym.getCategory().ordinal()) {
                System.out.println("➡ Gym ID: " + gym.getGymID() + ", Lokasyon: " + gym.getLocation() + " (Gerekli: " + gym.getCategory() + ")");
            }
        }

        Gym selectedGym = null;
        while (selectedGym == null) {
            System.out.print("\nLütfen kayıt olmak istediğiniz Gym ID'sini girin: ");
            String selectedID = scanner.nextLine();

            selectedGym = Database.getInstance().findGymById(selectedID);
            if (selectedGym == null) {
                System.out.println("❌ Geçersiz Gym ID. Lütfen uygun ID'lerden birini seçin.");
            } else {
                System.out.println("✅ Seçilen Gym: " + selectedGym.getLocation());
            }
        }
        
        System.out.print("Boy (cm): ");
        double height = scanner.nextDouble();

        System.out.print("Kilo (kg): ");
        double weight = scanner.nextDouble();

        Date startDate = new java.util.Date(); 
        Date endDate;
        if (isYearly) {
            endDate = new Date(System.currentTimeMillis() + (365L * 24 * 60 * 60 * 1000));
        } else {
            endDate = new Date(System.currentTimeMillis() + (30L * 24 * 60 * 60 * 1000));
        }

        Member newMember = new Member(userID, password, email, name, role, selectedGym, membershipType, startDate, endDate, height, weight, isYearly);
        newMember.setActive(false);
        Admin.getInstance().pasifMember(newMember);
        
        System.out.println("\n✅ Üyelik başarıyla oluşturuldu!");
        System.out.printf("Toplam Ücret: %.2f TL\n", newMember.getMembershipPrice());
    }
} 