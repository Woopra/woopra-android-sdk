/*
 * Copyright 2014 Woopra, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.woopra.tracking.android;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @author Woopra on 1/26/2013
 *
 */
public class WoopraIdentify implements Runnable {

    private WoopraTracker tracker;

    /**
     */
    public WoopraIdentify(WoopraTracker tracker) {
        this.tracker=tracker;
    }


    /**
     *
     */

    @Override
    public void run() {
        try {
            JSONObject postBody = new JSONObject()
                    .put("host", tracker.getDomain())
                    .put("cookie", tracker.getWoopraContext().getVisitor().getCookie())
                    .put("app", "android")
                    .put("response", "xml")
                    .put("os", "android")
                    .put("timeout", tracker.getIdleTimeout());

            // Add visitors properties
            for (Map.Entry<String, Object> entry : tracker.getWoopraContext().getVisitor().getProperties().entrySet()) {
                postBody.put("cv_" + entry.getKey(), entry.getValue());
            }

            Log.d(WoopraEvent.class.getName(), "Identify visitor");
            URL url = new URL(Constants.W_IDENTIFY_ENDPOINT);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("User-Agent", tracker.getWoopraContext().getClientInfo().getUserAgent());
            connection.setRequestProperty("content-type", "application/json");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(10000);
            connection.setUseCaches(false);
            connection.setDoOutput(true);
            connection.setDoInput(false);
            connection.connect();

            byte[] postBodyByte = postBody.toString().getBytes(StandardCharsets.UTF_8);
            connection.getOutputStream().write(postBodyByte);
            int responseCode = connection.getResponseCode();
            Log.d(WoopraEvent.class.getName(), "Response: " + responseCode);
        } catch (JSONException e) {
            Log.e(WoopraEvent.class.getName(), "Failed to make POST request body when identifying visitors", e);
        } catch (Exception e) {
            Log.e(WoopraEvent.class.getName(), "Failed to make HTTP request when identifying visitors", e);
        }
    }
}
