package main;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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

    public static Map<String, String> customerKeyValuePairs(String productString) {
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
}