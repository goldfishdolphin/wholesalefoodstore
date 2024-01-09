package main.User;

import java.util.HashMap;
import java.util.Map;

/**
 * This class creates a session manager for current logged-in user.
 */
public class SessionManager {
    private static final Map<String, String> sessions = new HashMap<>();

    public static void setLoggedInUser(String sessionId, String username) {
        sessions.put(sessionId, username);
    }

    public static String getLoggedInUser(String sessionId) {
        return sessions.get(sessionId);
    }

    public static void removeSession(String sessionId) {
        sessions.remove(sessionId);
    }

}