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

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * @author Woopra on 1/26/2013
 *
 */
public class WoopraIdentify implements Runnable{

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

        StringBuilder urlBuilder = new StringBuilder().
                append(Constants.W_IDENTIFY_ENDPOINT).append("?host=")
                .append(Utils.encode(tracker.getDomain()))
                .append("&cookie=")
                .append(Utils.encode(tracker.getWoopraContext().getVisitor().getCookie()))
                .append("&app=android")
                .append("&response=xml")
                .append("&os=android")
                .append("&timeout=").append(tracker.getIdleTimeout());


        // Add visitors properties
        for (Map.Entry<String, String> entry : tracker.getWoopraContext().getVisitor().getProperties().entrySet()) {
            urlBuilder.append("&cv_").append(Utils.encode(entry.getKey()))
                    .append("=")
                    .append(Utils.encode(entry.getValue()));
        }

        Log.d(WoopraEvent.class.getName(), "Final url:" + urlBuilder.toString());
        try {
            URL url = new URL(urlBuilder.toString());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            int result_code = connection.getResponseCode();
            Log.d(WoopraEvent.class.getName(), "Response:" + result_code);
        } catch (Exception e) {
            Log.e(WoopraEvent.class.getName(), "Got error!", e);
        }
    }
}
