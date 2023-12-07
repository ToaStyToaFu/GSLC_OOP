package Main;
import java.io.File;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import Models.*;

public class Connection {
	private static Connection connection = null;
    
    private Connection() {
    	buffer = new Model();
    }
    
    public static Connection getInstance() {
    	if(connection == null) {
    		connection = new Connection();
    	}
    	return connection;
    }
    
    Model buffer;
    Scanner fsc;
    int[] jumlahAnggota = new int[101];
   
   
    public void readFile() {
        try {
        	// Kosongin buffer
        	buffer = new Model();
        	
        	//Baca file user, masukin ke buffer
            File userFile = new File("Database/user.csv");
            fsc = new Scanner(userFile);
            
            //Skip baca descriptionnya
            fsc.nextLine();
            
            while (fsc.hasNextLine()) {
                String[] temp = fsc.nextLine().split(",");
                Integer teamID = Integer.parseInt(temp[2]);
                
                User user = new User(temp[0], temp[1], teamID);
                buffer.addUser(user);
                jumlahAnggota[teamID]++;
            }
            fsc.close();
            
            //Baca file team, masukin ke buffer
            File teamFile = new File("Database/teams.csv");
            fsc = new Scanner(teamFile);
            
            //Skip baca descriptionnya
            fsc.nextLine();
            
            while(fsc.hasNextLine()) {
            	String[] temp = fsc.nextLine().split(",");
            	Team team = new Team(Integer.parseInt(temp[0]),temp[1]);
            	buffer.addTeam(team);
            }
            fsc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    public void writeUser(String NIM, String name, String teamName) {
    	try {
    		FileWriter fw = new FileWriter("Database/user.csv", true);
    		PrintWriter writer = new PrintWriter(fw);
    		Integer teamID = searchTeamID(teamName);

    		if(jumlahAnggota[teamID]>=3) {
    			System.out.println("Error: Team Full.");
    			writer.close();
    			return;
    		}
    		else {
    			writer.printf("%s,%s,%d\n", NIM, name, teamID);
    			System.out.println("User Created!");
    		}
    		writer.close();
    		
    		//Update buffer
    		readFile();
    	}catch (IOException e) {
    		e.getStackTrace();
    	}
    }
    
    public void writeTeam(String teamName) {
    	try {
    		FileWriter fw = new FileWriter("Database/teams.csv", true);
    		PrintWriter writer = new PrintWriter(fw);
    		
    		writer.printf("%d,%s\n", buffer.getTeams().size()+1, teamName);
    		writer.close();
    		
    		//Update buffer
    		readFile();
    	}catch (IOException e) {
    		e.getStackTrace();
    	}
    }
    
    
    public Integer searchTeamID(String checkTeam) {
    	Team temp;
    	
    	for(int i = 0; i<buffer.getTeams().size(); i++) {
    		temp = buffer.getTeams().get(i);
    		if(temp.getTeamName().equalsIgnoreCase(checkTeam)) {
    			System.out.println(temp.getTeamName());
        		return temp.getTeamID();
        	}
    	}
    	return 0;
    }
    
    public ArrayList<User> findUser(String filterType, String[] filters, Boolean join, String table){
    	ArrayList<User> filteredUser = new ArrayList<>();
    	String operator = filters[0];
    	String filter = filters[1];
    	User search;
    	
		if(filterType.equalsIgnoreCase("name")) {
			if(operator.equals("=")) {
				for(int i = 0; i<buffer.getUsers().size(); i++) {
					search = buffer.getUsers().get(i);
					
					//Get Name
					if(search.getName().contains(filter)){
						filteredUser.add(search);
					}
				}
			}
			else if(operator.equals("!=")){
				for(int i = 0; i<buffer.getUsers().size(); i++) {
					search = buffer.getUsers().get(i);
					
					//Get Name
					if(!search.getName().contains(filter)){
						filteredUser.add(search);
					}
				}
			}
		}
		
		if(filterType.equalsIgnoreCase("teamID")){
			if(operator.equals("=")) {
				for(int i = 0; i<buffer.getUsers().size(); i++) {
					search = buffer.getUsers().get(i);
					
					//Get TeamID
					if(search.getTeamID().equals(Integer.parseInt(filter))){
						filteredUser.add(search);
					}
				}
			}
			else if(operator.equals("!=")) {
				for(int i = 0; i<buffer.getUsers().size(); i++) {
					search = buffer.getUsers().get(i);
					
					//Get TeamID
					if(!search.getTeamID().equals(Integer.parseInt(filter))){
						filteredUser.add(search);
					}
				}
			}
		}

//    		if(filterType.equalsIgnoreCase("teamname")) {
//    			if(operator.equals("=")) {
//    				for(int i=0; i<buffer.getUsers().size(); i++) {
//    					search = buffer.getUsers().get(i);
//    					String teamName;
//    					
//    					join
//    				}
//    			}
//    		}
		// Jika tidak ditemukan, ArrayList akan otomatis null. Maka tidak perlu dihandle
    	return filteredUser;
    }
    
    //Unused (bikin aja karena disuruh =))
    public User findOneUser(String filterType, String[] filters, Boolean join, String table) {
    	String operator = filters[0];
    	String filter = filters[1];
    	User search = null;
    	
		if(filterType.equalsIgnoreCase("name")) {
			if(operator.equals("=")) {
				for(int i = 0; i<buffer.getUsers().size(); i++) {
					search = buffer.getUsers().get(i);
					
					//Get Name
					if(search.getName().equals(filter)){
						return search;
					}
				}
			}
			else if(operator.equals("!=")){
				for(int i = 0; i<buffer.getUsers().size(); i++) {
					search = buffer.getUsers().get(i);
					
					//Get Name
					if(!search.getName().equals(filter)){
						return search;
					}
				}
			}
		}
		
		if(filterType.equalsIgnoreCase("teamID")){
			if(operator.equals("=")) {
				for(int i = 0; i<buffer.getUsers().size(); i++) {
					search = buffer.getUsers().get(i);
					
					//Get TeamID
					if(search.getTeamID().equals(Integer.parseInt(filter))){
						return search;
					}
				}
			}
			else if(operator.equals("!=")) {
				for(int i = 0; i<buffer.getUsers().size(); i++) {
					search = buffer.getUsers().get(i);
					
					//Get TeamID
					if(!search.getTeamID().equals(Integer.parseInt(filter))){
						return search;
					}
				}
			}
		}
		search = null;
		return search;
    }
    
    public ArrayList<Team> findTeam(String filterType, String[] filters){
    	ArrayList<Team> filteredTeam = new ArrayList<>();
    	String operator = filters[0];
    	String filter = filters[1];
    	Team search;
    	
    	if(filterType.equalsIgnoreCase("teamname")) {
    		if(operator.equals("=")){
    			for(int i=0; i<buffer.getTeams().size(); i++) {
    				search = buffer.getTeams().get(i);
    				
    				if(search.getTeamName().equals(filter)) {
    					filteredTeam.add(search);
    				}
    			}
    		}
    		else if(operator.equals("!=")) {
    			for(int i=0; i<buffer.getTeams().size(); i++) {
    				search = buffer.getTeams().get(i);
    				
    				if(!search.getTeamName().equals(filter)) {
    					filteredTeam.add(search);
    				}
    			}
    		}
    	}
    	else if(filterType.equalsIgnoreCase("teamID")) {
    		if(operator.equals("=")){
    			for(int i=0; i<buffer.getTeams().size(); i++) {
    				search = buffer.getTeams().get(i);
    				
    				if(search.getTeamID().equals(Integer.parseInt(filter))) {
    					filteredTeam.add(search);
    				}
    			}
    		}
    		else if(operator.equals("!=")) {
    			for(int i=0; i<buffer.getTeams().size(); i++) {
    				search = buffer.getTeams().get(i);
    				
    				if(!search.getTeamID().equals(Integer.parseInt(filter))) {
    					filteredTeam.add(search);
    				}
    			}
    		}			 
    	}
    	
    	return filteredTeam;
    }
    
    //Unused (bikin aja karena disuruh =))
    public Team findOneTeam(String filterType, String[] filters) {
    	String operator = filters[0];
    	String filter = filters[1];
    	Team search = null;
    	
    	if(filterType.equalsIgnoreCase("teamname")) {
    		if(operator.equals("=")){
    			for(int i=0; i<buffer.getTeams().size(); i++) {
    				search = buffer.getTeams().get(i);
    				
    				if(search.getTeamName().equals(filter)) {
    					return search;
    				}
    			}
    		}
    		else if(operator.equals("!=")) {
    			for(int i=0; i<buffer.getTeams().size(); i++) {
    				search = buffer.getTeams().get(i);
    				
    				if(!search.getTeamName().equals(filter)) {
    					return search;
    				}
    			}
    		}
    	}
    	else if(filterType.equalsIgnoreCase("teamID")) {
    		if(operator.equals("=")){
    			for(int i=0; i<buffer.getTeams().size(); i++) {
    				search = buffer.getTeams().get(i);
    				
    				if(search.getTeamID().equals(Integer.parseInt(filter))) {
    					return search;
    				}
    			}
    		}
    		else if(operator.equals("!=")) {
    			for(int i=0; i<buffer.getTeams().size(); i++) {
    				search = buffer.getTeams().get(i);
    				
    				if(!search.getTeamID().equals(Integer.parseInt(filter))) {
    					return search;
    				}
    			}
    		}			 
    	}
    	
    	search = null;
    	return search;
    }
    
    public Team joinTeamID(Integer teamID) {
    	for(int i=0; i<buffer.getTeams().size(); i++) {
    		if(buffer.getTeams().get(i).getTeamID() == teamID) {
    			return buffer.getTeams().get(i);
    		}
    	}
    	return null;
    }
    
    public ArrayList<User> getUsers(){
    	return buffer.getUsers();
    }
    
    public ArrayList<Team> getTeams(){
    	return buffer.getTeams();
    }
}
