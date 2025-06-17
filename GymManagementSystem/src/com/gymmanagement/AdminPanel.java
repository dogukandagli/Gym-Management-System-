package com.gymmanagement;
import com.gymmanagement.model.*;
import com.gymmanagement.user.Admin;
import com.gymmanagement.user.Coach;
import com.gymmanagement.user.Member;
import com.gymmanagement.util.ScreenUtil;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import com.gymmanagement.database.Database;

public class AdminPanel {
	AdminPanel instance;
	

	public static void AdminPanelShow(Admin admin) {
		
	    Scanner scanner = new Scanner(System.in);
        int choice;
        
        
        do {
        	
            ScreenUtil.clearScreen();
            System.out.println("\n=== Admin Panel ===");
            System.out.println("1. Üye Ekle");
            System.out.println("2. Ders Ekle");
            System.out.println("3. Eğitmen Ekle");
            System.out.println("4. Tüm Üyeleri Listele");
            System.out.println("5. Aktif Tüm Üyeleri Listele");
            System.out.println("6. Spor Salonu Ekle");
            System.out.println("7. Mali Tablo Görüntüle");
            System.out.println("8. Çıkış");
            System.out.print("Seçiminiz: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Buffer temizleme

            switch (choice) {
                case 1:
                    ScreenUtil.clearScreen();
                    System.out.println("Üye ekleme işlemi başlatılıyor...");
                    approvalMember();
                    break;
                case 2:
                    ScreenUtil.clearScreen();
                    scanner = new Scanner(System.in);
                    System.out.println("Ders ekleme işlemi...\n");
                    
                    System.out.println("Eklenmek istenen gym ID'yi giriniz.");
                    System.out.println("📋 Gym'leri listelemek için '1' yazın, doğrudan gym ID girmek için ID'yi yazın:");
                    System.out.print("Seçiminiz: ");
                    String input = scanner.nextLine();
                    scanner.nextLine();

                    if (input.equals("1")) {
                        ScreenUtil.clearScreen();
                        System.out.println("🔽 Mevcut Gym Listesi:");
                        for (Gym gym : Database.getInstance().loadGyms()) {
                            System.out.println("➡ Gym ID: " + gym.getGymID() + " | Lokasyon: " + gym.getLocation() + " | Kategori: " + gym.getCategory());
                        }

                        System.out.print("📥 Şimdi eklenmek istenen gym ID'yi girin: ");
                        input = scanner.nextLine().trim();
                    }
                    Gym selectedGym = Database.getInstance().findGymById(input);
                    
                    if (selectedGym == null) {
                        System.out.println("❌ Geçersiz Gym ID. İşlem iptal edildi.");
                        return;
                    } else {
                        System.out.println("✅ Seçilen Gym: " + selectedGym.getLocation());
                    }
                    
                    ScreenUtil.clearScreen();
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
                        ScreenUtil.clearScreen();
                        admin.listCoach();
                        
                        System.out.println("Hoca ID giriniz : (listelemek icin 1 e tiklayiniz)  :");
                        coachID = scanner.nextLine();
                    }
                    
                    Coach coach = Database.getInstance().findCoachById(coachID);
                    Date oldDate = new Date();
                    LocalDateTime startDate = LocalDateTime.now();
                    
                    ClassSession classes = new ClassSession(name, description, startDate, capacity, coach, selectedGym);
                    coach.addClasses(classes);
                    Database.getInstance().addClass(classes);
                    System.out.println("Ders eklendi.");
                    
                    break;
                case 3:
                    ScreenUtil.clearScreen();
                    System.out.println("Eğitmen ekleme işlemi...");
                    
                    addCoach(admin);
                    
                    System.out.println("Antrenor eklendi.");
                    break;
                case 4:
                    ScreenUtil.clearScreen();
                    listMembers();
                    break;
                    
                case 5:
                    ScreenUtil.clearScreen();
                    System.out.println("Aktif Üyeler listeleniyor...");
                    admin.printActiveMemberStats();
                    break;
                case 6:
                    ScreenUtil.clearScreen();
                    addNewGym();
                    break;
                case 7:
                    ScreenUtil.clearScreen();
                    showFinancialReport();
                    break;
                case 8: 
                    System.out.println("Admin çıkış yapıyor...");
                    break;
                default:
                    System.out.println("Geçersiz seçim!");
            }
        } while (choice != 8);
	}
	public static void listMembers() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Üyeler listeleniyor...");
    	System.out.println("1 - Tüm üyeleri listele");
    	System.out.println("2 - Gym ID'ye göre filtrele");
    	System.out.println("0 - Çıkış");
    	System.out.print("Seçiminiz: ");
    	int secim = scanner.nextInt();
    	scanner.nextLine(); // Buffer temizleme
    	
    	switch (secim) {
    	    case 0:
    	        return;
    	    case 1:
    	        ScreenUtil.clearScreen();
    	        Admin.getInstance().listMembers();
    	        System.out.println("\nPress Enter to continue...");
    	        scanner.nextLine();
    	        break;
    	    case 2:
    	    	ScreenUtil.clearScreen();
    	    	System.out.print("Lütfen görüntülemek istediğiniz salonun Gym ID'sini girin: ");
    	    	 String selectedGymID = scanner.nextLine();
    	    	 Admin.getInstance().listMembersByGymID(selectedGymID);
    	        System.out.println("\nPress Enter to continue...");
    	        scanner.nextLine();
    	        break;
    	    default:
    	        System.out.println("Geçersiz seçim. Lütfen 0, 1 veya 2 girin.");
    	        System.out.println("\nPress Enter to continue...");
    	        scanner.nextLine();
    	}
	}
	
	public static  void approvalMember() {
	    Scanner scanner = new Scanner(System.in);
	    ScreenUtil.clearScreen();
	    System.out.println(" Onay bekleyen üyelikler:");
	    Admin.getInstance().printPassiveMembers();

	    while (true) {
	        System.out.print("\nOnaylamak istediğiniz üyenin ID'sini girin (çıkmak için 'q'): ");
	        String id = scanner.nextLine();

	        if (id.equalsIgnoreCase("q")) {
	            System.out.println("Onaylama işlemi sonlandırıldı.");
	            System.out.println("\nPress Enter to continue...");
	            scanner.nextLine();
	            break;
	        }

	        Member passiveMember = Admin.getInstance().findPassiveMemberByID(id);

	        if (passiveMember == null) {
	            System.out.println(" Bu ID ile eşleşen pasif üye bulunamadı. Lütfen tekrar deneyin.");
	        } else {
	            passiveMember.setActive(true);
	            Admin.getInstance().addMember(passiveMember);
	            Admin.getInstance().removePassiveMember(passiveMember);
	            System.out.println(  passiveMember.getName() + " aktif üye olarak sisteme eklendi.");
	        }
	    }
	}

	public static void addCoach(Admin admin) {
        Scanner scanner = new Scanner(System.in);
        ScreenUtil.clearScreen();
        
        System.out.println("Eklenmek istenen gym ID'yi giriniz.");
        System.out.println("📋 Gym'leri listelemek için '1' yazın, doğrudan gym ID girmek için ID'yi yazın:");
        System.out.print("Seçiminiz: ");
        String input = scanner.nextLine().trim();

        if (input.equals("1")) {
            System.out.println("🔽 Mevcut Gym Listesi:");
            for (Gym gym : Database.getInstance().loadGyms()) {
                System.out.println("➡ Gym ID: " + gym.getGymID() + " | Lokasyon: " + gym.getLocation() + " | Kategori: " + gym.getCategory());
            }

            System.out.print("📥 Şimdi eklenmek istenen gym ID'yi girin: ");
            input = scanner.nextLine().trim();
        }
        Gym gym = Database.getInstance().findGymById(input);
        
        if (gym == null) {
            System.out.println("❌ Geçersiz Gym ID. İşlem iptal edildi.");
            return;
        } else {
            System.out.println("✅ Seçilen Gym: " + gym.getLocation());
        }
        
        String userID = Database.getInstance().getNextUserID();
        System.out.println("Kullanıcı ID: " + userID + " (Otomatik atandı)");

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
        
        Coach newCoach = new Coach(userID, password, email, name1, role, gym, experienceYears, bio);
        admin.addCoach(newCoach);
    }
	public static void addNewGym() {
	    Scanner scanner = new Scanner(System.in);
	    ScreenUtil.clearScreen();

	    System.out.println("🏋️ Yeni Gym Ekliyorsunuz...");

	    System.out.print("Gym Adı: ");
	    String name = scanner.nextLine().trim();

	    System.out.print("Gym ID: ");
	    String gymID = scanner.nextLine().trim();

	    System.out.print("Lokasyon: ");
	    String location = scanner.nextLine().trim();

	    System.out.print("Gerekli üyelik tipi (BASIC, PREMIUM, STUDENT): ");
	    MembershipType requiredMembership = null;
	    while (requiredMembership == null) {
	        try {
	            String input = scanner.nextLine().trim().toUpperCase();
	            requiredMembership = MembershipType.valueOf(input);
	        } catch (IllegalArgumentException e) {
	            System.out.print("❌ Geçersiz tip. Tekrar girin (BASIC, PREMIUM, STUDENT): ");
	        }
	    }

	    Gym newGym = new Gym(name, gymID, location, requiredMembership);

	    Database.getInstance().addGym(newGym);
	    System.out.println("✅ Yeni spor salonu eklendi: " + name + " (" + location + ")");
	}

	private static void showFinancialReport() {
        List<Member> members = Database.getInstance().loadMembers();
        double totalMonthlyIncome = 0;
        double totalYearlyIncome = 0;
        
        ScreenUtil.clearScreen();
        System.out.println("\n=== Mali Tablo ===");
        System.out.println("Üyelik Tipi | Aylık Üye Sayısı | Yıllık Üye Sayısı | Toplam Gelir");
        System.out.println("------------------------------------------------------------");
        
        for (MembershipType type : MembershipType.values()) {
            long monthlyMembers = members.stream()
                .filter(m -> m.getMemberShipType() == type && !m.isYearly())
                .count();
            
            long yearlyMembers = members.stream()
                .filter(m -> m.getMemberShipType() == type && m.isYearly())
                .count();
            
            double monthlyIncome = monthlyMembers * type.getMonthlyPrice();
            double yearlyIncome = yearlyMembers * type.getYearlyPrice();
            
            totalMonthlyIncome += monthlyIncome;
            totalYearlyIncome += yearlyIncome;
            
            System.out.printf("%-12s | %-16d | %-16d | %.2f TL\n",
                type, monthlyMembers, yearlyMembers, monthlyIncome + yearlyIncome);
        }
        
        System.out.println("------------------------------------------------------------");
        System.out.printf("Toplam Gelir: %.2f TL\n", totalMonthlyIncome + totalYearlyIncome);
        System.out.println("\nPress Enter to continue...");
        new Scanner(System.in).nextLine();
    }
}
