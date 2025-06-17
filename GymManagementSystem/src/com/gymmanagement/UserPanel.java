package com.gymmanagement;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import com.gymmanagement.database.Database;
import com.gymmanagement.model.ClassSession;
import com.gymmanagement.model.Gym;
import com.gymmanagement.user.BasicExercise;
import com.gymmanagement.user.BasicTraining;
import com.gymmanagement.user.CardioDecorator;
import com.gymmanagement.user.Member;
import com.gymmanagement.user.StretchingDecorator;
import com.gymmanagement.user.TrainingProgram;
import com.gymmanagement.util.ScreenUtil;

public class UserPanel 
{

	
	public static void UserPanelShow(Member user) 
	{
		Scanner scanner = new Scanner(System.in);
		int choice;
		int updateChoice;
		
		do 
		{
			  List<String> notifications = user.getNotifications();
		        if (!notifications.isEmpty()) {
		            System.out.println("\n🔔 [BİLDİRİMLERİNİZ]");
		            for (String note : notifications) {
		                System.out.println(" - " + note);
		            }
		            user.clearNotifications();
		            com.gymmanagement.database.Database.getInstance().updateMember(user);
		            System.out.println("\n(Tüm bildirimler okundu olarak işaretlendi.)\n");
		        }
			System.out.println();
			
			ScreenUtil.clearScreen();
			System.out.println("=== User Panel ===");
			System.out.println("1. View and Update My Information");
			System.out.println("2. View My Membership Information");
			System.out.println("3. List Gyms and Check In");
			System.out.println("4. View Classes");
			System.out.println("5. View Nutrition Program");
			System.out.println("6. My Activity History");
			System.out.println("7. Exit");
			System.out.print("Select a command: ");
			
			choice = scanner.nextInt();
			
			switch(choice) 
			{
				case 1:
					ScreenUtil.clearScreen();
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
						ScreenUtil.clearScreen();
						System.out.println();
						System.out.println("=== Update Panel ===");
						System.out.println("1. Update Email");
						System.out.println("2. Update Height");
						System.out.println("3. Update Weight");
						System.out.println("4. Exit");
						System.out.print("Select a command: ");
						
						updateChoice = scanner.nextInt();
						scanner.nextLine();
						
						switch(updateChoice) 
						{
							case 1:
								ScreenUtil.clearScreen();
								System.out.print("Enter new email: ");
								String newEmail = scanner.nextLine();
								user.setEmail(newEmail);
								Database.getInstance().updateMember((Member) user);
								System.out.println("Email has been changed.");
								System.out.println("\nPress Enter to continue...");
								scanner.nextLine();
								break;
							case 2:
								ScreenUtil.clearScreen();
								System.out.print("Enter height: ");
								int newHeight = scanner.nextInt();
								user.setHeight(newHeight);
								Database.getInstance().updateMember((Member) user);
								System.out.println("Height has been updated.");
								System.out.println("\nPress Enter to continue...");
								scanner.nextLine();
								scanner.nextLine();
								break;
							case 3:
								ScreenUtil.clearScreen();
								System.out.print("Enter Weight: ");
								int newWeight = scanner.nextInt();
								user.setWeight(newWeight);
								Database.getInstance().updateMember((Member) user);
								System.out.println("Weight has been updated.");
								System.out.println("\nPress Enter to continue...");
								scanner.nextLine();
								scanner.nextLine();
								break;
							case 4:
								System.out.println("Exiting Update Panel.");
								System.out.println("\nPress Enter to continue...");
								scanner.nextLine();
								break;
							default:
								System.out.println("Invalid Command!");
								System.out.println("\nPress Enter to continue...");
								scanner.nextLine();
						}
					}while(updateChoice != 4);
					
					break;
				
				case 2:
					ScreenUtil.clearScreen();
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
					System.out.println("\nPress Enter to continue...");
					scanner.nextLine();
					break;
					
					
				case 3:
					ScreenUtil.clearScreen();
					int i = 1;
					List<Gym> gyms = Database.getInstance().loadGyms();
					for(Gym gym: gyms) 
					{
						System.out.println(i + ". " + gym.getName() + ", " + gym.getLocation() + ", Category: " + gym.getCategory());
						i++;
					}
					
					System.out.println("\n0. Exit");
					System.out.print("Enter the number of the gym to check in (0 to exit): " );
					
				    int selectedIndex = scanner.nextInt();
				    scanner.nextLine(); 

				    if (selectedIndex == 0) {
				        break;
				    }

				    if (selectedIndex < 1 || selectedIndex > gyms.size()) {
				        System.out.println("Invalid selection.");
				        System.out.println("\nPress Enter to continue...");
				        scanner.nextLine();
				        break;
				    }

				    Gym selectedGym = gyms.get(selectedIndex - 1);
				    if(!selectedGym.getCategory().equals(user.getMemberShipType())) 
				    {
				    	System.out.println("Your membership type is not compatible with this gym category (" + selectedGym.getCategory() + ").");
				    	System.out.println("\nPress Enter to continue...");
				    	scanner.nextLine();
				    	break;
				    }
				    System.out.print("Enter check-in date and time (yyyy-MM-dd HH:mm): ");
				    String dateTimeInput = scanner.nextLine();

				    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				    try 
				    {
				        Date checkInDate = formatter.parse(dateTimeInput);

				        user.setGym(selectedGym); 
				        System.out.println("Checked into: " + selectedGym.getName() + " at " + checkInDate);
				        
				    } 
				    catch (ParseException e) 
				    {
				        System.out.println("Invalid date format. Please use yyyy-MM-dd HH:mm");
				    }
					System.out.println("\nPress Enter to continue...");
					scanner.nextLine();
					
					trainingProgram(user);
				    Database.getInstance().updateMember((Member) user);
				    
					break;
				case 4:
					ScreenUtil.clearScreen();
					List<ClassSession> classes = Database.getInstance().loadClasses();
					List<ClassSession> availableClasses = new ArrayList<>();

					System.out.println("=== Available Classes in Your Gym: " + user.getGym().getName() + " ===");
					System.out.println();

					int index = 1;
					for (ClassSession cl : classes) {
					    if (cl.getGym().getGymID().equals(user.getGym().getGymID())) {
					        if (cl.getAvailableSpots() > 0) {
					            System.out.println(index + ". " + cl.getName()+ " | Time: " + cl.getDateTime() + 
					                               " | Capacity: " + cl.getMembers().size() + "/" + cl.getCapacity());
					            availableClasses.add(cl);
					            index++;
					        }
					    }
					}

					if (availableClasses.isEmpty()) {
					    System.out.println("No available classes at your gym.");
					} else {
					    System.out.println("\n0. Exit");
					    System.out.print("Enter the number of the class to join (0 to exit): ");
					    int selection = scanner.nextInt();
					    scanner.nextLine();

					    if (selection == 0) {
					        break;
					    }

					    if (selection >= 1 && selection <= availableClasses.size()) {
					        ClassSession selectedClass = availableClasses.get(selection - 1);
					        selectedClass.addMember(user); 
					        selectedClass.setCurrentCapacity(selectedClass.getCurrentCapacity() + 1); 
					        Database.getInstance().saveClasses(classes); 

					        System.out.println("You have successfully joined the class: " + selectedClass.getName());
					    } else {
					        System.out.println("Invalid class selection.");
					    }
					}
					System.out.println("\nPress Enter to continue...");
					scanner.nextLine();
					break;
				case 5:
					ScreenUtil.clearScreen();
					System.out.println("Nutrition Program feature is not implemented yet.");
					System.out.println("\nPress Enter to continue...");
					scanner.nextLine();
					break;
				case 6:
					ScreenUtil.clearScreen();
					System.out.println("Activity History feature is not implemented yet.");
					System.out.println("\nPress Enter to continue...");
					scanner.nextLine();
					break;
				case 7:
					System.out.println("Exiting User Panel...");
					break;
				default:
					System.out.println("Invalid command!");
					System.out.println("\nPress Enter to continue...");
					scanner.nextLine();
					break;
			}
		}while(choice != 7);
	}
	
	public static void trainingProgram(Member user) {
		Scanner scanner = new Scanner(System.in);
		TrainingProgram program = new BasicTraining();
		
		boolean devam = true;

		while (devam) {
		    ScreenUtil.clearScreen();
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
		        
                if (user != null) 
                {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    String activity = "Check-in: " + sdf.format(new Date())
                                    + " | Duration: " + program.getDuration() + " dk"
                                    + " | Calories: " + program.getCalori() + " cal"
                                    + " | Exercises: " + program.getDescription();
                    user.addActivity(activity);
                    System.out.println("Activity eklendi ✔");
                    
                    // adding to database will be implemented
                }

		        break;
		    default:
		        System.out.println("Geçersiz seçim, lütfen tekrar deneyin.");
		        break;
		}

		}
	}


	
}
