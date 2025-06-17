package com.gymmanagement;

import java.util.Scanner;

import com.gymmanagement.database.Database;
import com.gymmanagement.user.*;


public class UserPanel 
{

	
	public static void UserPanelShow(Member user) 
	{
		Scanner scanner = new Scanner(System.in);
		int choice;
		int updateChoice;
		
		do 
		{
	        System.out.println("=== User Panel ===");
	        System.out.println("1. View and Update My Information");
	        System.out.println("2. View My Membership Information");
	        System.out.println("3. List Gyms and Check In");
	        System.out.println("4. View Training Program");
	        System.out.println("5. View Nutrition Program");
	        System.out.println("6. My Activity History");
	        System.out.println("7. Exit");
	        System.out.print("Select a command: ");
	        
	        choice = scanner.nextInt();
	        
	        switch(choice) 
	        {
	        	case 1:
	        		System.out.println();
	        		System.out.println("  Account Information ");
	        		System.out.println("Name: " + user.getName());
	        		System.out.println("Email: " + user.getEmail());
	        		System.out.println("ID: " + user.getUserID());

	        		
	        		System.out.println();
	        		System.out.println("  Personal Information  ");
	        		System.out.println("Name: " + user.getName());
	        		System.out.println("Height: " + user.getHeight() + "cm");
	        		System.out.println("Weight: " + user.getWeight() + "kg");
	        		
	        		do 
	        		{
	        			System.out.println();
		    	        System.out.println("=== Update Panel ===");
		        		System.out.println("1. Update Email");
		        		System.out.println("2. Update Height");
		        		System.out.println("3. Update Weight");
		        		System.out.println("4. Exit");
		        		
		        		updateChoice = scanner.nextInt();
		        		scanner.nextLine();
		        		
		        		switch(updateChoice) 
		        		{
		        			case 1:
		        				System.out.print("Enter new email: ");
		        				System.out.println();
		        				
		        				String newEmail = scanner.nextLine();
		        				user.setEmail(newEmail);
		        				

		        				// send info to database
		        				
		        				System.out.println("Email has been changed.");
		        				System.out.println();

		        				break;
		        			case 2:
		        				System.out.print("Enter height: ");
		        				System.out.println();
		        				
		        				float newHeight = scanner.nextFloat();
		        				user.setHeight(newHeight);
		        				
		        				
		        				// send info to database
		        				
		        				System.out.println("Height has been updated.");
		        				System.out.println();
		        				scanner.nextLine();
		        				break;
		        			case 3:
		        				System.out.print("Enter Weight: ");
		        				System.out.println();
		        				
		        				float newWeight = scanner.nextFloat();
		        				user.setWeight(newWeight);
		        				

		        				// send info to database
		        				
		        				System.out.println("Weight has been updated.");
		        				System.out.println();
		        				scanner.nextLine();
		        				break;
		        			case 4:
		        				System.out.println("Exiting Update Panel.");
		        				break;
		        			default:
		        				System.out.println("Invaild Command!");
		        		}
	        		}while(updateChoice != 4);
	        	


	        		
	        	case 2:
	        		
	        		System.out.println("  Membership and GYM Information ");
	        		
	        		System.out.println();
	        		System.out.println("Membership Type: " + user.getRole());
	        		
	        		if(user.getGym() != null) 
	        		{
	        			System.out.println("GYM: ");
	        			System.out.println("Name: " + user.getGym().getName());
	        			System.out.println("ID: " + user.getGym().getGymID());
	        			System.out.println("Location: " + user.getGym().getLocation());
	        			System.out.println("Active Member: " +user.isActive());
	        			System.out.println("Start date: " + user.getMemberShipStart());
	        			System.out.println("End date: " + user.getMemberShipEnd());
	        			System.out.println("Remaining day: " + (user.getMemberShipEnd().getTime() - System.currentTimeMillis()) / (1000 * 60 * 60 * 24));
	        		}
	        		else 
	        		{
	        			System.out.println("You are not registered to any gym.");
	        		}
	        		break;
	        		
	        		
	        	case 3:
	        		System.out.println();
	        	case 4:
	        		trainingProgram();
	        			break;
	        	case 5:
	        	
	        	case 6:
	        		
	        	case 7:
	            	System.out.println("Exiting...");
	                break;
	        	default:
	        		System.out.println("Invalid command!");
	        }
		}while(choice != 7);
	}
	
	public static void trainingProgram() {
		Scanner scanner = new Scanner(System.in);
		TrainingProgram program = new BasicTraining();

		boolean devam = true;

		while (devam) {
		    System.out.println("=== ANTRENMAN LİSTESİ ===");
		    System.out.println("1. Göğüs - Bench Press (20 dk)");
		    System.out.println("2. Göğüs - Dumbbell Fly (15 dk)");
		    System.out.println("3. Sırt - Barbell Row (20 dk)");
		    System.out.println("4. Sırt - Lat Pulldown (15 dk)");
		    System.out.println("5. Arka Kol - Triceps Pushdown (15 dk)");
		    System.out.println("6. Ön Kol - Barbell Curl (15 dk)");
		    System.out.println("7. Bacak - Squat (25 dk)");
		    System.out.println("8. Bacak - Leg Press (20 dk)");
		    System.out.println("9. Omuz - Shoulder Press (20 dk)");
		    System.out.println("10. Kardiyo - Koşu Bandı (20 dk)");
		    System.out.println("11. Esneme - Full Body Stretching (10 dk)");
		    System.out.println("0. Programı Bitir ve Göster");
		    System.out.print("Seçiminiz: ");
		    
		    int secim = scanner.nextInt();
		    
		    
		    switch (secim) {
		    case 1:
		            ((BasicTraining) program).addExercise(new BasicExercise("Bench Press", 20));
		        break;
		    case 2:
		         ((BasicTraining) program).addExercise(new BasicExercise("Dumbbell Fly", 15));
		        break;
		    case 3:
		         ((BasicTraining) program).addExercise(new BasicExercise("Barbell Row", 20));
		        break;
		    case 4:
		         ((BasicTraining) program).addExercise(new BasicExercise("Lat Pulldown", 15));
		        break;
		    case 5:
		        ((BasicTraining) program).addExercise(new BasicExercise("Triceps Pushdown", 15));
		        break;
		    case 6:
		        ((BasicTraining) program).addExercise(new BasicExercise("Barbell Curl", 15));
		        break;
		    case 7:
		        ((BasicTraining) program).addExercise(new BasicExercise("Squat", 25));
		        break;
		    case 8:
		         ((BasicTraining) program).addExercise(new BasicExercise("Leg Press", 20));
		        break;
		    case 9:
		         ((BasicTraining) program).addExercise(new BasicExercise("Shoulder Press", 20));
		        break;
		    case 10:
		        program = new CardioDecorator(program);
		        break;
		    case 11:
		        program = new StretchingDecorator(program);
		        break;
		    case 0:
		        devam = false;
		        System.out.println("\n=== OLUŞTURULAN PROGRAM ===");
		        System.out.println("Açıklama: " + program.getDescription());
		        System.out.println("Toplam Süre: " + program.getDuration() + " dk");
		        System.out.println("Yakilan Kalori" + program.getCalori() + " cl");
		        break;
		    default:
		        System.out.println("Geçersiz seçim, lütfen tekrar deneyin.");
		        break;
		}

		}
	}


	
}
