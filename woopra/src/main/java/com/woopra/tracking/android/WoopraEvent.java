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
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

/**
 * @author Woopra on 1/26/2013
 *
 */
public class WoopraEvent implements Runnable {

	private WoopraTracker tracker;
	private final Map<String, Object> properties = new ConcurrentHashMap<>();
	private String eventName;
	private long timestamp = -1;

	/**
	 * @param eventName
	 */
	public WoopraEvent(String eventName) {
		this(eventName, null);
	}

	/**
	 * @param eventName
	 * @param properties
	 */
	public WoopraEvent(String eventName, Map<String, Object> properties) {
		this.eventName = eventName;
		if (properties != null) {
			this.properties.putAll(properties);
		}
	}

	/**
	 *
	 * @param tracker
	 */
	public void setTracker(WoopraTracker tracker){
		this.tracker = tracker;
	}

	/**
	 * @return
	 */
	public String getName() {
		return this.eventName;
	}

	/**
	 * @return
	 */
	public long getTimestamp() {
		return timestamp;
	}

	/**
	 * @param timestamp
	 */
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * @return
	 */
	public Map<String, Object> getProperties() {
		return properties;
	}

	/**
	 * @param newProperties
	 */
	@Deprecated
	public void setEventProperty(Map<String, Object> newProperties) {
		properties.putAll(newProperties);
	}

	/**
	 * @param newProperties
	 */
	public void setProperties(Map<String, Object> newProperties) {
		properties.putAll(newProperties);
	}

	/**
	 * @param key
	 * @param value
	 */
	@Deprecated
	public void setEventProperty(String key, Object value) {
		properties.put(key, value);
	}

	/**
	 * @param key
	 * @param value
	 */
	public void setProperty(String key, Object value) {
		properties.put(key, value);
	}

	/**
	 * @param campaignId
	 */
	public void setCampaignId(String campaignId) {
		setProperty("campaign_id", campaignId);
	}


	/**
	 * @param campaignName
	 */
	public void setCampaignName(String campaignName) {
		setProperty("campaign_name", campaignName);
	}

	/**
	 * @param campaignTerm
	 */
	public void setCampaignTerm(String campaignTerm) {
		setProperty("campaign_term", campaignTerm);
	}

	/**
	 * @param campaignMedium
	 */
	public void setCampaignMedium(String campaignMedium) {
		setProperty("campaign_medium", campaignMedium);
	}


	/**
	 * @param campaignSource
	 */
	public void setCampaignSource(String campaignSource) {
		setProperty("campaign_source", campaignSource);
	}

	/**
	 * @param campaignContent
	 */
	public void setCampaignContent(String campaignContent) {
		setProperty("campaign_content", campaignContent);
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
                    .put("screen", tracker.getWoopraContext().getClientInfo().getScreenResolution())
                    .put("language", tracker.getWoopraContext().getClientInfo().getLanguage())
                    .put("browser", tracker.getWoopraContext().getClientInfo().getClient())
                    .put("app", "android")
                    .put("response", "xml")
                    .put("os", "android")
                    .put("timeout", tracker.getIdleTimeout());

            if (tracker.getReferrer() != null) {
                postBody.put("referer", tracker.getReferrer());
            }
            if (tracker.getDeviceType() != null) {
                postBody.put("device", tracker.getDeviceType());
            }

            //Event settings
            if (getTimestamp() != -1) {
                postBody.put("timestamp", Long.toString(getTimestamp()));
            }

            // Add visitors properties
            for (Map.Entry<String, Object> entry : tracker.getWoopraContext().getVisitor().getProperties().entrySet()) {
                postBody.put("cv_" + entry.getKey(), entry.getValue());
            }

            postBody.put("event", getName());
            // Add Event properties
            for (Map.Entry<String, Object> entry : getProperties().entrySet()) {
                postBody.put("ce_" + entry.getKey(), entry.getValue());
            }
            Log.d(WoopraEvent.class.getName(), "Track event: " + getName());

            URL url = new URL(Constants.W_TRACK_ENDPOINT);
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
            Log.e(WoopraEvent.class.getName(), "Failed to make POST request body when tracking events", e);
        } catch (Exception e) {
            Log.e(WoopraEvent.class.getName(), "Failed to make HTTP request when tracking events", e);
        }
    }
}
