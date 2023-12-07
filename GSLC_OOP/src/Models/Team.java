package Models;
import Repository.TeamRepository;

import java.util.ArrayList;

import Main.Connection;

public class Team extends Model implements TeamRepository{
	private String teamName;
	
	public Team(Integer teamID, String teamName) {
		super(teamID);
		this.teamName = teamName;
	}
	
	public Team() {
		
	}
	
	public void insert(String teamName, Connection connection) {
		connection.writeTeam(teamName);
	}
	
	public ArrayList<Team> getTeams(Connection connection){
		return connection.getTeams();
	}
	
	public ArrayList<Team> find(String filterType, String[] filter, Connection connection){
		return connection.findTeam(filterType, filter);
	}
	
	public Team findOne(String filterType, String[] filter, Connection connection) {
		return connection.findOneTeam(filterType, filter);
	}
	
	public Team joinTeamID(Integer teamID, Connection connection) {
		return connection.joinTeamID(teamID);
	}
	
	public String getTeamName() {
		return teamName;
	}
	
	public Integer getTeamID() {
		return teamID;
	}
}
