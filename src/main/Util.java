package main;

import com.sun.net.httpserver.HttpExchange;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class create simple utility methods to convert different string formats to get data.
 */
public class Util {
    public static HashMap<String, String> requestStringToMap(String request) {
        HashMap<String, String> map = new HashMap<>();
        String[] pairs = request.split("&");
        Arrays.stream(pairs).forEach(pair -> {
            String[] kv = pair.split("=");
            String key = URLDecoder.decode(kv[0], StandardCharsets.UTF_8);
            String value = URLDecoder.decode(kv[1], StandardCharsets.UTF_8);
            map.put(key, value);
        });
        return map;
    }

    /**
     * This method converts the product string to a key value pair after  removing '[ ]' and trimming whitespaces.
     *
     * @param productString is the input product in form of a string.
     * @return map of the key-value pairs of the product's attributes.
     */
    public static Map<String, String> productKeyValuePairs(String productString) {
        productString = productString.substring(productString.indexOf('[') + 1, productString.indexOf(']')).trim();
        String[] pairs = productString.split(",\\s*");
        Map<String, String> productMap = new HashMap<>();

        for (String pair : pairs) {
            String[] keyValue = pair.split("=");
            if (keyValue.length == 2) {
                productMap.put(keyValue[0], keyValue[1]);
            }
        }
        return productMap;
    }

    /**
     * This method converts a string request that has Category in the path to a key value pair using Hashmap.
     * @param request a URL of the request
     * @return a key value pair
     */
    public static HashMap<String, String> editStringToMap(String request) {
        HashMap<String, String> mapInput = new HashMap<>();
        String[] pairs = request.split("&");

        Arrays.stream(pairs).forEach(pair -> {
            try {
                String[] kv = pair.split("=");
                String key = URLDecoder.decode(kv[0], "UTF-8");
                String value = (kv.length > 1) ? URLDecoder.decode(kv[1], "UTF-8") : null;

                // Check if the key is 'category' and the value is the default "Change Category"
                if ("category".equals(key) && "Change Category".equals(value)) {
                    value = null;
                }
                mapInput.put(key, value);
            } catch (UnsupportedEncodingException e) {
                System.err.println(e.getMessage());
            }
        });
        return mapInput;
    }

    /**
     * This method converts string to key value pairs by removing [].
     * @param customerString The String of the customer output class.
     * @return a key value pair of a customer string.
     */
    public static Map<String, String> customerKeyValuePairs(String customerString) {
        customerString = customerString.substring(customerString.indexOf('[') + 1, customerString.indexOf(']')).trim();
        String[] pairs = customerString.split(",\\s*");
        Map<String, String> productMap = new HashMap<>();

        for (String pair : pairs) {
            String[] keyValue = pair.split("=");
            if (keyValue.length == 2) {
                productMap.put(keyValue[0], keyValue[1]);
            }
        }
        return productMap;
    }

    /**
     * This method converts a password string to hashed password string by appending padding bits and length, creating and Initialising MD buffer and processing message in 16-word block.
     *
     * @param password a string of password
     * @return hashedPassword String
     * @throws NoSuchAlgorithmException
     */
    public static String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] messageDigest = md.digest(password.getBytes());
        BigInteger no = new BigInteger(1, messageDigest);
        String hashedString = no.toString(16);
        while (hashedString.length() < 32) {
            hashedString = "0" + hashedString;
        }
        return hashedString;
    }

    /**
     * This method returns the key value pairs  for strings of the products.
     * @param productString a string of the product class output.
     * @return key value a pair of  product information
     */
    public static Map<String, String>  userKeyValuePairs(String productString) {

        String[] pairs = productString.split(",\\s*");
        Map<String, String> productMap = new HashMap<>();

        for (String pair : pairs) {
            String[] keyValue = pair.split("=");
            if (keyValue.length == 2) {
                productMap.put(keyValue[0], keyValue[1]);
            }
        }
        return productMap;
    }

    /**
     * The method returns current unique session id for the  logged-in user.
     * @param he Http request
     * @return the current session id
     */
    public static String getCurrentSessionId(HttpExchange he) {
        List<String> cookies = he.getRequestHeaders().get("Cookie");

        if (cookies != null) {
            for (String cookie : cookies) {
                String[] parts = cookie.split(";");

                for (String part : parts) {
                    part = part.trim();

                    if (part.startsWith("sessionId=")) {
                        return part.substring("sessionId=".length());
                    }
                }
            }
        }
        return null;
    }

    /**
     * This method returns key value pairs of a string.
     * @param itemString A string of items that have key value pairs seperated by ",".
     * @return Map of key-value pairs
     */
    public static Map<String, String> itemKeyValuePairs(String itemString) {
    String[] pairs = itemString.split(", ");
    Map<String, String> productMap = new java.util.HashMap<>();

    for (String pair : pairs) {
        String[] keyValue = pair.split("=");
        if (keyValue.length == 2) {
            String key = keyValue[0].trim();
            String value = keyValue[1].trim();
            productMap.put(key, value);
        }
    }
    return productMap;
}
}