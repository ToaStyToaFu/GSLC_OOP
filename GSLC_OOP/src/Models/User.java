package Models;
import java.util.ArrayList;

import Repository.UserRepository;
import Main.Connection;

public class User extends Model implements UserRepository{
	private String NIM;
	private String name;
	
	public User(String NIM, String name, Integer teamID){
		super(teamID);
		this.NIM = NIM;
		this.name = name;
	}
	public User() {
	}
	
	public void insert(String NIM, String name, String teamName, Connection connection) {
		connection.writeUser(NIM, name, teamName);
	}
	
	public ArrayList<User> find(String filterType, String[] filter, Boolean join, String table, Connection connection){
		if(filterType == null || filter == null || (join == null && table != null) || (join == true && table == null)) {
			return null;
		}
		
		return connection.findUser(filterType, filter, join, table);
	}
	
	public User findOne(String filterType, String[] filter, Boolean join, String table, Connection connection) {
		if(filterType == null || filter == null || (join == null && table != null) || (join == true && table == null)) {
			return null;
		}
		
		return connection.findOneUser(filterType, filter, join, table);
	}
	
	public ArrayList<User> getUsers(Connection connection){
		return connection.getUsers();
	}
	
	public String getName() {
		return name;
	}
	
	public String getNIM() {
		return NIM;
	}
	
	public Integer getTeamID() {
		return teamID;
	}
}
