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
 */package com.woopra.tracking.android;

import android.util.Log;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

/**
 * @author Woopra on 1/26/2013
 *
 */
public class WoopraEvent implements Runnable{

	private WoopraTracker tracker;
	private final Map<String, String> properties = new ConcurrentHashMap<String, String>();
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
	public WoopraEvent(String eventName, Map<String, String> properties) {
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
		this.tracker=tracker;
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
	public Map<String, String> getProperties() {
		return properties;
	}

	/**
	 * @param newProperties
	 */
	@Deprecated
	public void setEventProperty(Map<String, String> newProperties) {
		properties.putAll(newProperties);
	}

	/**
	 * @param newProperties
	 */
	public void setProperties(Map<String, String> newProperties) {
		properties.putAll(newProperties);
	}

	/**
	 * @param key
	 * @param value
	 */
	@Deprecated
	public void setEventProperty(String key, String value) {
		properties.put(key, value);
	}

	/**
	 * @param key
	 * @param value
	 */
	public void setProperty(String key, String value) {
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

		StringBuilder urlBuilder = new StringBuilder().
				append(Constants.W_TRACK_ENDPOINT).append("?host=")
				.append(Utils.encode(tracker.getDomain()))
				.append("&cookie=")
				.append(Utils.encode(tracker.getWoopraContext().getVisitor().getCookie()))
				.append("&screen=")
				.append(Utils.encode(tracker.getWoopraContext().getClientInfo().getScreenResolution()))
				.append("&language=")
				.append(Utils.encode(tracker.getWoopraContext().getClientInfo().getLanguage()))
				.append("&browser=")
				.append(Utils.encode(tracker.getWoopraContext().getClientInfo().getClient()))
				.append("&app=android")
				.append("&response=xml")
				.append("&os=android")
				.append("&timeout=").append(tracker.getIdleTimeout());

		if (tracker.getReferrer() != null) {
			urlBuilder.append("&referer=").append(Utils.encode(tracker.getReferrer()));
		}
		if (tracker.getDeviceType() != null) {
			urlBuilder.append("&device=").append(Utils.encode(tracker.getDeviceType()));
		}

		//Event settings
		if (getTimestamp() != -1) {
			urlBuilder.append("&timestamp=").append(Utils.encode(Long.toString(getTimestamp())));
		}

		//
		// Add visitors properties
		for (Map.Entry<String, String> entry : tracker.getWoopraContext().getVisitor().getProperties().entrySet()) {
			urlBuilder.append("&cv_").append(Utils.encode(entry.getKey()))
					.append("=")
					.append(Utils.encode(entry.getValue()));
		}
		urlBuilder.append("&event=").append(Utils.encode(getName()));

		// Add Event properties
		for (Map.Entry<String, String> entry : getProperties().entrySet()) {
			urlBuilder.append("&ce_").append(Utils.encode(entry.getKey()))
					.append("=")
					.append(Utils.encode(entry.getValue()));
		}


		Log.d(WoopraEvent.class.getName(), "Final url:" + urlBuilder.toString());
		try {
			URL url = new URL(urlBuilder.toString());
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestProperty("User-Agent", tracker.getWoopraContext().getClientInfo().getUserAgent());
			connection.connect();
			int result_code = connection.getResponseCode();
			Log.d(WoopraEvent.class.getName(), "Response:" + result_code);
		} catch (Exception e) {
			Log.e(WoopraEvent.class.getName(), "Got error!", e);
		}
	}
}
