package Repository;
import java.util.ArrayList;

import Main.Connection;
import Models.Team;

public interface TeamRepository {
	void insert(String teamName, Connection connection);
	ArrayList<Team> find(String filterType, String[] filter, Connection connection);
	Team findOne(String filterType, String[] filter, Connection connection);
	ArrayList<Team> getTeams(Connection connection);
	Team joinTeamID(Integer teamID, Connection connection);
}
