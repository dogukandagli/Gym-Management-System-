package com.gymmanagement;

import com.gymmanagement.model.*;

import java.util.Scanner;

import com.gymmanagement.database.Database;


public class Main {
    public static void main(String[] args) {
    	Scanner scanner = new Scanner(System.in);
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
			}
		}
		
    }
} 