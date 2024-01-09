package main.User;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * This class creates a user object.
 */
public class User{
    private int userID;
    private String username;
    private String password;

   public User(String username, String password){
       this.userID= userID;
       this.username = username;
       this.password = password;
   }
    public void  assignParameters(PreparedStatement statement) throws SQLException {
        statement.setInt(1, userID);
        statement.setString(2, username);
        statement.setString(3, password);
    }
    @Override
    public String toString() {

        return "UserID=" + this.userID + ", Username=" + this.username+ ", Password=" + this.password;
    }

}
