package com.gymmanagement;

import com.gymmanagement.user.Coach;
import com.gymmanagement.model.ClassSession;
import java.util.List;
import java.util.Scanner;

public class CoachPanel {
    public static void CoachPanelShow(Coach coach) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
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
                    viewMyClasses(coach);
                    break;
                case "2":
                    manageAvailableHours(coach);
                    break;
                case "3":
                    viewCertifications(coach);
                    break;
                case "4":
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
                System.out.println("Katılımcı Sayısı: " + session.getMembers().size());
                System.out.println("------------------------");
            }
        }
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
    }

    private static void updateProfile(Coach coach) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n=== Profil Güncelleme ===");
        System.out.println("1. Bio Güncelle");
        System.out.println("2. Sertifika Ekle");
        System.out.println("3. Geri Dön");
        System.out.print("Seçiminiz: ");

        String choice = scanner.nextLine();
        // Burada profil güncelleme implementasyonu yapılacak
        System.out.println("Bu özellik henüz implement edilmedi.");
    }
} 