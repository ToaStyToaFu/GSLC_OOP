package Models;
import java.util.ArrayList;

public class Model {
	private ArrayList<User> users = new ArrayList<>();
	ArrayList<Team> teams = new ArrayList<>();
	Integer teamID;
	
	public Model() {
	}
	
	public Model(Integer teamID) {
		this.teamID = teamID;
	}
	
	public void addUser(User user){
		users.add(user);
	}
	
	public void addTeam(Team team) {
		teams.add(team);
	}
	
	public ArrayList<Team> getTeams() {
		return teams;
	}
	
	public ArrayList<User> getUsers(){
		return users;
	}
	
}
