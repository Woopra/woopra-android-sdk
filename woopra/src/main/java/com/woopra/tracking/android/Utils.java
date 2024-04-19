package com.woopra.tracking.android;

import java.net.URLEncoder;
import java.util.UUID;

/**
 * Created by dev on 4/12/17.
 */

public class Utils {
    /**
     *
     * @param firstKey
     * @param secondKey
     * @return
     */
    public static String getUUID(String firstKey, String secondKey) {
        long mostSigBits = firstKey.hashCode();
        long leastSigBits = secondKey.hashCode();
        UUID generateUUID = new UUID(mostSigBits, leastSigBits);
        String result = generateUUID.toString();
        return result.replace("-", "");
    }
}
