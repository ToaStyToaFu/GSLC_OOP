package Main;
import java.util.ArrayList;
import java.util.Scanner;

import Models.*;
import Repository.*;

public class Main {
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Connection connection = Connection.getInstance();
		connection.readFile();
		UserRepository userRepository = new User();
		TeamRepository teamRepository = new Team();
		
		
		int select = 0;
		do {
			System.out.printf("1. Menu Utama\n2. Insert Data\n3. Show\n4. Exit\n>> ");
			select = sc.nextInt();
			sc.nextLine();
			
			if(select == 2) {
				int tableSelection;
				do {
					System.out.println("Which table to insert? 1. User, 2. Team.");
					
					tableSelection = sc.nextInt();
					sc.nextLine();
					if(tableSelection == 1) {
						System.out.print("Add name: ");
						String name = sc.nextLine();
						
						System.out.print("Add NIM: ");
						String NIM = sc.nextLine();
						
						System.out.print("Add Team Name: ");
						String teamName = sc.nextLine();
						
						Integer teamID = connection.searchTeamID(teamName);
						
						// Kalau teamID ketemu, kalo ga ketemu dia return 0
						if(teamID != 0) {
							// Write user
							userRepository.insert(NIM, name, teamName, connection);
						}
						else {
							// Write team terus user
							teamRepository.insert(teamName, connection);
							userRepository.insert(NIM, name, teamName, connection);
						}
					}
					else if(tableSelection == 2) {
						System.out.print("Add Team Name: ");
						String teamName = sc.nextLine();
						
						teamRepository.insert(teamName, connection);
					}
				}while(tableSelection!=1 && tableSelection!=2);
			}
			
			else if(select == 3) {
				int showSelect;
				int condition;
				do {
					System.out.println("Which table to show? 1. User 2. Team.");
					showSelect = sc.nextInt();
					sc.nextLine();
					
					do {
						System.out.println("Want to filter by condition? 1. Yes, 2. No.");
						condition = sc.nextInt();
						sc.nextLine();
					}while(condition != 1 && condition != 2);
					
					//User Query
					if (showSelect == 1) {
						
						//With Conditions
						if(condition == 1) {
							System.out.println("Add condition, seperate by semicolon [filterType (e.g name, teamid);= or !=;filter");
							String[] temp = sc.nextLine().split(";");
							
							System.out.println("Use join? (y/n)");
							String useJoin;
							do {
								useJoin = sc.nextLine();
							}while(!useJoin.equalsIgnoreCase("y") && !useJoin.equalsIgnoreCase("n"));
							
							Boolean join = false;
							if(useJoin.equalsIgnoreCase("y")) {
								join = true;
							}
							else if(useJoin.equalsIgnoreCase("n")) {
								join = false;
							}
							
							try {
								String filterType = temp[0];
								String[] filter = {temp[1], temp[2]};
								String table = null;
								
								//With join
								if(join) {
									table = "Team";
									
									ArrayList<User> filteredUser = userRepository.find(filterType, filter, true, table, connection);
									Team joinTeam;
									System.out.println("NIM | Name | Team Name");
									for(int i=0; i<filteredUser.size(); i++) {
										joinTeam = teamRepository.joinTeamID(filteredUser.get(i).getTeamID(), connection);
										System.out.println(filteredUser.get(i).getNIM() + " | " + filteredUser.get(i).getName() + " | " + joinTeam.getTeamName());
												
									}
								}
								
								//Without join
								if(!join) {
									ArrayList<User> filteredUser = userRepository.find(filterType, filter, false, null, connection);
									System.out.println("NIM | Name | TeamID");
									for(int i=0; i<filteredUser.size(); i++) {
										System.out.println(filteredUser.get(i).getNIM() + " | " + filteredUser.get(i).getName() + " | " + filteredUser.get(i).getTeamID());
												
									}
								}
								
							}catch(ArrayIndexOutOfBoundsException e) {
								e.getStackTrace();
								System.out.println("Please input the proper format!");
								sc.nextLine();
							}
						}
						
						//Without conditions (show all)
						else if(condition == 2){
							//Show all data
							ArrayList<User> showUser = userRepository.getUsers(connection);
							System.out.println("NIM | Name | TeamID");
							for(int i=0; i<showUser.size(); i++) {
								System.out.println(showUser.get(i).getNIM() + " | " + showUser.get(i).getName() + " | " + showUser.get(i).getTeamID());
										
							}
						}
					}
					
					//Team Query
					else if(showSelect == 2) {
						// With Conditions
						if(condition == 1) {
							System.out.println("Add condition, seperate by semicolon [filterType;= or !=;filter");
							String[] temp = sc.nextLine().split(";");
							try {
								String filterType = temp[0];
								String[] filter = {temp[1], temp[2]};
								
								ArrayList<Team> showTeam = teamRepository.find(filterType, filter, connection);
								System.out.println("Team Name | TeamID");
								for(int i=0; i<showTeam.size(); i++) {
									System.out.println(showTeam.get(i).getTeamName() + " | " + showTeam.get(i).getTeamID());
								}			
			
							}catch(ArrayIndexOutOfBoundsException e) {
								e.getStackTrace();
								System.out.println("Please input the proper format!");
								sc.nextLine();
							}
						}
						
						// Without Conditions
						else if(condition == 2) {
							//Show all team data
							ArrayList<Team> showTeam = teamRepository.getTeams(connection);
							System.out.println("Team Name | TeamID");
							for(int i=0; i<showTeam.size(); i++) {
								System.out.println(showTeam.get(i).getTeamName() + " | " + showTeam.get(i).getTeamID());
							}
						}
					}
				}while(showSelect!=1 && showSelect!=2);
			}
		}while(select != 4);
		sc.close();
	}

}
