package com.woopra.tracking.android;

import java.net.URLEncoder;
import java.util.UUID;

/**
 * Created by dev on 4/12/17.
 */

public class Utils {
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
