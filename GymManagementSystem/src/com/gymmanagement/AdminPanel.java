package com.gymmanagement;
import com.gymmanagement.model.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import com.gymmanagement.database.Database;

public class AdminPanel {

	public static void AdminPanelShow(Admin admin) {
		
	    Scanner scanner = new Scanner(System.in);
        int choice;
        
        
        do {
            System.out.println("\n=== Admin Panel ===");
            System.out.println("1. Üye Ekle");
            System.out.println("2. Ders Ekle");
            System.out.println("3. Eğitmen Ekle");
            System.out.println("4. Tüm Üyeleri Listele");
            System.out.println("5. Aktif Tüm Üyeleri Listele");
            System.out.println("6. Çıkış");
            System.out.print("Seçiminiz: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Üye ekleme işlemi...");
                    addMember(admin);
                    break;
                case 2:
                	System.out.println("Ders ekleme işlemi...\n");

                    System.out.println("Ders ismi giriniz :");
                    String name = scanner.nextLine();
                    scanner.nextLine(); 
                    System.out.println("Ders tanıtımı :");
                    String description = scanner.nextLine();

                    System.out.println("Kapasite giriniz  :");
                    int capacity = scanner.nextInt();
                    scanner.nextLine(); 

                    System.out.println("Hoca ID giriniz : (listelemek için 1 e basınız)  :");
                    String coachID = scanner.nextLine();
                    
                    if (coachID.equals("1"))  {
                    	admin.listCoach();
                    	
                    	 System.out.println("Hoca ID giriniz : (listelemek icin 1 e tiklayiniz)  :");
                    	 coachID = scanner.nextLine();
                    	 
                    }
                    
                    Coach coach = Database.getInstance().findCoachById(coachID);
                    Date oldDate = new Date();
                    LocalDateTime startDate = LocalDateTime.now();
                    
                    
                    ClassSession classes = new ClassSession( name ,  description , startDate ,capacity,coach);
                    coach.addClasses(classes);
                    Database.getInstance().addClass(classes);
                    System.out.println("Ders eklendi.");
                    
                    break;
                case 3:
                    System.out.println("Eğitmen ekleme işlemi...");
                    
                    addCoach(admin);
                    
                    System.out.println("Antrenor eklendi.");
                    break;
                case 4:
                    System.out.println("Üyeler listeleniyor...");
                    admin.listMembers();
                    break;
                    
                case 5:
                    System.out.println("Aktif Üyeler listeleniyor...");
                    admin.printActiveMemberStats();
                    break;
                case 6:
                    System.out.println("Admin çıkış yapıyor...");
                    break;
                default:
                    System.out.println("Geçersiz seçim!");
            }
        } while (choice != 6);
	}
	private static void addMember(Admin admin) {
	    Scanner scanner = new Scanner(System.in);

	    System.out.print("Kullanıcı ID: ");
	    String userID = scanner.nextLine();
	    scanner.nextLine();
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

	    
	    System.out.print("Boy (cm): ");
	    double height = scanner.nextDouble();

	    System.out.print("Kilo (kg): ");
	    double weight = scanner.nextDouble();

	    Date startDate = new java.util.Date(); 
	    Date endDate = new java.util.Date(System.currentTimeMillis() + (365L * 24 * 60 * 60 * 1000)); 

	    Member newMember = new Member(userID,password,email,name,role,admin.getGym(),membershipType,startDate,endDate,height,weight);

	    admin.addMember(newMember);
	    System.out.println("✅ Yeni üye başarıyla eklendi: " + name);
	}
	private static void addCoach(Admin admin) {
		   Scanner scanner = new Scanner(System.in);
		  System.out.print("Kullanıcı ID: ");
          String userID = scanner.nextLine();

          System.out.print("Şifre: ");
          String password = scanner.nextLine();

          System.out.print("Email: ");
          String email = scanner.nextLine();

          System.out.print("İsim: ");
          String name1 = scanner.nextLine();

          String role = "Coach"; 

          System.out.print("Deneyim yılı: ");
          int experienceYears = scanner.nextInt();
          scanner.nextLine(); 

          System.out.print("Hakkında kısa bilgi (bio): ");
          String bio = scanner.nextLine();

          Coach newCoach = new Coach(userID, password, email, name1, role,admin.getGym(), experienceYears, bio);
          admin.addCoach(newCoach);
	}


	
	
	
}
