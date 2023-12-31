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

public class LoginFormHandler implements HttpHandler {

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
        int id = Integer.parseInt(map.get("customer_id"));

        storedUser = null;
        try {
            storedUser = users.selectUser(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Map<String, String> mapUser = Util.userKeyValuePairs(String.valueOf(storedUser));
        storedPassword = mapUser.get("Password");
        storedUsername = mapUser.get("Username");

        if ((inputUsername.equals(storedUsername)) && (hashedpassword.equals(storedPassword))) {
            System.out.println("Access Granted!");
            loggedinUser = storedUsername;
            // Set the logged-in user in the session


        } else {
            System.out.println("password does not match");
        }
        String sessionId = he.getRemoteAddress().toString();
        he.getResponseHeaders().add("Set-Cookie", "sessionId=" + sessionId);
        SessionManager.setLoggedInUser(sessionId, loggedinUser);
        PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(he.getResponseBody())));
        he.sendResponseHeaders(200, 0);
        try {
             users.selectUser(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if (loggedinUser != null) {
            System.out.println("Logged in as: " + loggedinUser);
        } else {
            System.out.println("Not logged in");
        }

        SessionManager.setLoggedInUser(sessionId, loggedinUser);



        pw.println("<html>" +
                "<head> <title>Confirmation</title> </head>" +
                "<body>" +
                "<h1> Access Granted!</h1>" + "<a href=\"/ \" class=\"btn btn-primary\"> Home </a>" +
                "</body>" +
                "</html>");
        pw.close();
    }
}
