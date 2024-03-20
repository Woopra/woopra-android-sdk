package woopra;

import java.net.URLEncoder;
import java.util.UUID;

/**
 * Created by dev on 4/12/17.
 */

public class Utils {

    /**
     *
     * @param param
     * @return
     */
    public static String encode(String param) {
        try {
            return URLEncoder.encode(param, "utf-8");
        } catch (Exception e) {
            // will not throw an exception since utf-8 is supported.
        }
        return param;
    }

    /**
     *
     * @param fristKey
     * @param secondKey
     * @return
     */
    public static String getUUID(String fristKey, String secondKey) {
        long mostSigBits = fristKey.hashCode();
        long leastSigBits = secondKey.hashCode();
        UUID generateUUID = new UUID(mostSigBits, leastSigBits);
        String result = generateUUID.toString();
        return result.replace("-", "");
    }
}
