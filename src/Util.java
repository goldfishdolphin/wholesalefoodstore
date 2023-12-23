import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Util {
    public static HashMap<String, String> requestStringToMap(String request){
        HashMap<String, String> map = new HashMap<String, String>();
        String[] pairs = request.split("&");
        Arrays.stream(pairs).forEach(pair -> {
            try {
                String[] kv = pair.split("=");
                String key = URLDecoder.decode(kv[0], "UTF-8");
                String value = URLDecoder.decode(kv[1], "UTF-8");
                map.put(key, value);
            } catch (UnsupportedEncodingException e) {
                System.err.println(e.getMessage());
            }
        });
        return map;
    }

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

}

