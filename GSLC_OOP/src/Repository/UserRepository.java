package Repository;
import java.util.ArrayList;

import Main.Connection;
import Models.User;

public interface UserRepository {
	void insert(String NIM, String name, String teamName, Connection connection);
	ArrayList<User> find(String filterType, String[] filter, Boolean join, String table, Connection connection);
	ArrayList<User> getUsers(Connection connection);
	User findOne(String filterType, String[] filter, Boolean join, String table, Connection connection);
}
