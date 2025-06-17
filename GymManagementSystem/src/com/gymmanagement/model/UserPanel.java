package com.gymmanagement.model;

import java.util.Scanner;

import com.gymmanagement.database.Database;


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
	
	


	
}
