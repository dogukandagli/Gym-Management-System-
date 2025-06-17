package com.gymmanagement;

import com.gymmanagement.model.*;
import com.gymmanagement.user.Admin;
import com.gymmanagement.user.Member;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import com.gymmanagement.database.Database;


public class Main {
    public static void main(String[] args) {
    	   Scanner scanner = new Scanner(System.in);
    	   boolean flag = true;
    	
    	
        while (true) {
            System.out.println("==== Gym Management System ====");
            System.out.println("1. Giriş Yap");
            System.out.println("2. Kayıt Ol");
            System.out.println("3. Çıkış");
            System.out.print("Seçiminizi girin: ");
            
            String input = scanner.nextLine();

            switch (input) {
                case "1":
                    System.out.println("Giriş işlemi başlatılıyor...");
                    System.out.println("=== GYM Management Login ===");
                    System.out.print("Email: ");
                    String email = scanner.nextLine();

                    System.out.print("Şifre: ");
                    String password = scanner.nextLine();
             	
                    System.out.print("Role gir: ");
                    String role = scanner.nextLine();
                    
             	LoginRequest request = new LoginRequest(email,password);
         		
         		BaseHandler chain = new EmailandPasswordHandler();
         		
         		chain.setNext(new RoleVerificationHandler(role)).setNext(new MemberShipActivityHandler());
         		
         		if(chain.handle(request)) {
         			if(request.loggedInUser instanceof Admin) {
         				System.out.println("/n Giris Basarili , Admin olarak giris yapiyorsunuz...");
         				AdminPanel.AdminPanelShow((Admin) request.loggedInUser);
         			}
         			if(request.loggedInUser instanceof Member) {
         				System.out.println("/n Giris Basarili , Member olarak giris yapiyorsunuz...");
         				UserPanel.UserPanelShow((Member) request.loggedInUser);
         			}
         		}
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
    
    private static void addMember() {
	    Scanner scanner = new Scanner(System.in);

	    System.out.print("Kullanıcı ID: ");
	    String userID = scanner.nextLine();
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
	    
	    System.out.println("\n Seçtiğiniz üyelik tipine uygun spor salonları:");
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
	    Date endDate = new java.util.Date(System.currentTimeMillis() + (365L * 24 * 60 * 60 * 1000)); 

	    Member newMember = new Member(userID,password,email,name,role,selectedGym,membershipType,startDate,endDate,height,weight);
	    newMember.setActive(false);
	    Admin.getInstance().pasifMember(newMember);
	}
} 