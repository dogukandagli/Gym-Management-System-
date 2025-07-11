package com.gymmanagement;

import com.gymmanagement.user.Coach;
import com.gymmanagement.model.ClassSession;
import com.gymmanagement.util.ScreenUtil;
import java.util.List;
import java.util.Scanner;

public class CoachPanel {
    public static void CoachPanelShow(Coach coach) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            ScreenUtil.clearScreen();
            System.out.println("\n=== Eğitmen Paneli ===");
            System.out.println("1. Derslerimi Görüntüle");
            System.out.println("2. Müsait Saatlerimi Ayarla");
            System.out.println("3. Sertifikalarımı Görüntüle");
            System.out.println("4. Profil Bilgilerimi Güncelle");
            System.out.println("5. Çıkış Yap");
            System.out.print("Seçiminiz: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    ScreenUtil.clearScreen();
                    viewMyClasses(coach);
                    break;
                case "2":
                    ScreenUtil.clearScreen();
                    manageAvailableHours(coach);
                    break;
                case "3":
                    ScreenUtil.clearScreen();
                    viewCertifications(coach);
                    break;
                case "4":
                    ScreenUtil.clearScreen();
                    updateProfile(coach);
                    break;
                case "5":
                    System.out.println("Çıkış yapılıyor...");
                    running = false;
                    break;
                default:
                    System.out.println("Geçersiz seçim!");
                    break;
            }
        }
    }

    private static void viewMyClasses(Coach coach) {
        List<ClassSession> classes = coach.getAssignedClasses();
        if (classes.isEmpty()) {
            System.out.println("Henüz atanmış dersiniz bulunmamaktadır.");
        } else {
            System.out.println("\n=== Derslerim ===");
            for (ClassSession session : classes) {
                System.out.println("Ders: " + session.getName());
                System.out.println("Açıklama: " + session.getDescription());
                System.out.println("Tarih: " + session.getDateTime());
                System.out.println("Kapasite: " + session.getCapacity());
                System.out.println("------------------------");
            }
        }
        System.out.println("\nPress Enter to continue...");
        new Scanner(System.in).nextLine();
    }

    private static void manageAvailableHours(Coach coach) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n=== Müsait Saatler ===");
        System.out.println("1. Müsait Saat Ekle");
        System.out.println("2. Müsait Saat Sil");
        System.out.println("3. Müsait Saatleri Görüntüle");
        System.out.println("4. Geri Dön");
        System.out.print("Seçiminiz: ");

        String choice = scanner.nextLine();
        // Burada müsait saat yönetimi implementasyonu yapılacak
        System.out.println("Bu özellik henüz implement edilmedi.");
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    private static void viewCertifications(Coach coach) {
        List<String> certifications = coach.getCertifications();
        if (certifications.isEmpty()) {
            System.out.println("Henüz sertifikanız bulunmamaktadır.");
        } else {
            System.out.println("\n=== Sertifikalarım ===");
            for (String cert : certifications) {
                System.out.println("- " + cert);
            }
        }
        System.out.println("\nPress Enter to continue...");
        new Scanner(System.in).nextLine();
    }

    private static void updateProfile(Coach coach) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n=== Profil Güncelleme ===");
        System.out.println("1. Email Güncelle");
        System.out.println("2. Bio Güncelle");
        System.out.println("3. Geri Dön");
        System.out.print("Seçiminiz: ");

        String choice = scanner.nextLine();
        switch (choice) {
            case "1":
                System.out.print("Yeni email: ");
                String newEmail = scanner.nextLine();
                coach.setEmail(newEmail);
                System.out.println("Email güncellendi.");
                break;
            case "2":
                System.out.print("Yeni bio: ");
                String newBio = scanner.nextLine();
                coach.setBio(newBio);
                System.out.println("Bio güncellendi.");
                break;
            case "3":
                return;
            default:
                System.out.println("Geçersiz seçim!");
        }
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }
} 