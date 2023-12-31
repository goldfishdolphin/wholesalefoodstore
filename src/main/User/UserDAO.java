package main.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private Connection connect() throws SQLException {

        String dbURL = "jdbc:sqlite:foodstore.sql";
        return DriverManager.getConnection(dbURL);
    }
    public String listUsers() {
        System.out.println("Listing all users...");
        Connection dbConnection = null;
        Statement statement = null;
        ResultSet result = null;
        String query = "SELECT * FROM user;";
        List<User> users = new ArrayList<>();

        try {
            dbConnection = connect();
            statement = dbConnection.createStatement();
            result = statement.executeQuery(query);
            while (result.next()) {
                int id = result.getInt("user_id");
                String username = result.getString("username");
                String password = result.getString("password");
                users.add(new User( id, username, password));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (result != null) {
                try {
                    result.close();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (statement != null) {
                try {
                    statement.close();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (dbConnection != null) {
                try {
                    dbConnection.close();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        users.forEach(user -> System.out.println(user)); //lambda function to iterate the users in the list.
        return users.toString();
    }
    public User selectUser(int user_id) throws SQLException {

    User user = null;
        Connection dbConnection = null;
        PreparedStatement statement = null;
        ResultSet result = null;

        String query = "SELECT * FROM user WHERE user_id =?;";

        try {
            dbConnection = connect();
            statement = dbConnection.prepareStatement(query);
            statement.setInt(1, user_id);
            result = statement.executeQuery();

            while (result.next()) {
                int id = result.getInt("user_id");
                String username = result.getString("username");
                String password = result.getString("password");
                user = new User(id,username,password);
            }
        } finally {
            if (result != null) {
                try {
                    result.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (statement != null) {
                try {
                    statement.close();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (dbConnection != null) {
                try {
                    dbConnection.close();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        if (user == null)
            System.out.println("The user does not exist with the id " + user_id);
        return user;
    }
}
