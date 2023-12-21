import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.HashMap;

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
}

