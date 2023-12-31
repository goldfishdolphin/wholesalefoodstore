package main.User;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

import static main.User.SessionManager.removeSession;
import static main.Util.getCurrentSessionId;

public class LogoutHandler implements HttpHandler {
    public void handle(HttpExchange he) throws IOException {

        String sessionId = getCurrentSessionId(he);
        if (sessionId != null) {
            String loggedInUser = SessionManager.getLoggedInUser(sessionId);
            System.out.println("Logged in as: " + loggedInUser);
            removeSession(sessionId);
            he.getResponseHeaders().set("Location", "/");
            he.sendResponseHeaders(302, 0);
        } else {
            System.out.println("No Session ID found.");
        }

    }
}