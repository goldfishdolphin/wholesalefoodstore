package main.User;  // Adjust the package declaration to match your directory structure

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import main.Util;

import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static main.Util.requestStringToMap;

/**
 * This class process a log-in request by the users and give them access after checking the password by hashing.
 */
public class LoginFormHandler implements HttpHandler {
    /** This methods create the access display after processing log-in data.
     * @param he the exchange containing the request from the
     *                 client and used to send the response
     * @throws IOException
     */
    public void handle(HttpExchange he) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(he.getRequestBody()));
        UserDAO users = new UserDAO();
        User storedUser;
        String storedPassword = null;
        String storedUsername;
        String inputPassword;
        String inputUsername;
        String hashedpassword;
        String loggedinUser = null;
        String line;
        String request = "";
        while ((line = in.readLine()) != null) {
            request = request + line;
        }
        HashMap<String, String> map = requestStringToMap(request);

        inputPassword = map.get("password");
        inputUsername = map.get("username");

        try {
            hashedpassword = Util.hashPassword(inputPassword);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        storedUser = null;
        try {
            storedUser = users.selectUser(inputUsername);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Map<String, String> mapUser = Util.userKeyValuePairs(String.valueOf(storedUser));
        storedPassword = mapUser.get("Password");
        storedUsername = mapUser.get("Username");

        if ((inputUsername.equals(storedUsername)) && (hashedpassword.equals(storedPassword))) {
            System.out.println("Access Granted!");
            loggedinUser = storedUsername;
        } else {
            System.out.println("password does not match");
        }
        String sessionId = he.getRemoteAddress().toString();
        he.getResponseHeaders().add("Set-Cookie", "sessionId=" + sessionId);
        SessionManager.setLoggedInUser(sessionId, loggedinUser);
        PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(he.getResponseBody())));
        he.sendResponseHeaders(200, 0);
        try {
             users.selectUser(inputUsername);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if (loggedinUser != null) {
            System.out.println("Logged in as: " + loggedinUser);

            pw.println("<html>" +
                    "<head> <title>Confirmation</title> </head>" +
                    "<body>" +
                    "<h1 class=\"text-center\"> Access Granted!</h1>" +
                    "<a href=\"/ \" class=\"btn btn-dark\"> Home </a>" +
                    "</body>" +
                    "</html>");
        } else {
            System.out.println("Not logged in");
            pw.println("<html>" +
                    "<head> <title>Access Denied</title> </head>" +
                    "<body>" +
                    "<h1 class=\"text-center\"> Try Again!</h1>" +
                    "<a href=\"/ \" class=\"btn btn-dark\"> Home </a>" +
                    "</body>" +
                    "</html>");

        }

        SessionManager.setLoggedInUser(sessionId, loggedinUser);


        pw.close();
    }
}
