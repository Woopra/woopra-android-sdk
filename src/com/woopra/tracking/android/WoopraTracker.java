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
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import android.util.Log;

/**
 * @author Woopra on 1/26/2013
 *
 */
public class WoopraTracker {

	private static final String TAG = WoopraTracker.class.getName();
	private static final String W_EVENT_ENDPOINT = "http://www.woopra.com/track/ce/";

	private ScheduledExecutorService pingScheduler;
	private final ExecutorService executor;
	private final String domain;
	private final WoopraClientInfo clientInfo;

	// default timeout value for Woopra service
	private long idleTimeoutMs = 30000;
	private boolean pingEnabled = false;

	//
	private String referer = null, deviceType=null;
	private WoopraVisitor visitor = null;


	WoopraTracker(ExecutorService executor, String domain, WoopraVisitor vistor, WoopraClientInfo clientInfo) {
		this.executor = executor;
		this.visitor = WoopraVisitor.getAnonymousVisitor();
		this.clientInfo = clientInfo;
		this.domain = domain;
	}

	public boolean trackEvent(WoopraEvent event) {
		EventRunner runner = new EventRunner(event);
		try {
			executor.execute(runner);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	private boolean trackEventImpl(WoopraEvent event) {
		// generate request url
		StringBuilder urlBuilder = new StringBuilder();
		urlBuilder.append(W_EVENT_ENDPOINT).append("?host=")
			.append(encodeUriComponent(getDomain()))
			.append("&cookie=")
			.append(encodeUriComponent(getVisitor().getCookie()))
			.append("&screen=")
			.append(encodeUriComponent(clientInfo.getScreenResolution()))
			.append("&language=")
			.append(encodeUriComponent(clientInfo.getLanguage()))
			.append("&browser=")
			.append(encodeUriComponent(clientInfo.getClient()))
			.append("&app=android&response=xml&os=android&timeout=").append(idleTimeoutMs);

		if (referer != null) {
			urlBuilder.append("&referer=").append(encodeUriComponent(referer));
		}
		if(deviceType != null){
			urlBuilder.append("&device=").append(encodeUriComponent(deviceType));
		}

		//Event settings
		if (event.getTimestamp() != null) {
			urlBuilder.append("&timestamp=").append(encodeUriComponent(Long.toString(event.getTimestamp())));
		}

		//
		// Add visitors properties
		for (Entry<String, String> entry : visitor.getProperties().entrySet()) {
			urlBuilder.append("&cv_").append(encodeUriComponent(entry.getKey()))
					.append("=")
					.append(encodeUriComponent(entry.getValue()));
		}
		urlBuilder.append("&event=").append(encodeUriComponent(event.getName()));

		// Add Event properties
		for (Entry<String, String> entry : event.getProperties().entrySet()) {
			urlBuilder.append("&ce_").append(encodeUriComponent(entry.getKey()))
					.append("=")
					.append(encodeUriComponent(entry.getValue()));
		}


		Log.d(TAG, "Final url:" + urlBuilder.toString());
		try {
			URL url = new URL(urlBuilder.toString());
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestProperty("User-Agent", clientInfo.getUserAgent());
			connection.connect();
			int result_code = connection.getResponseCode();
			Log.d(TAG, "Response:" + result_code);
		} catch (Exception e) {
			Log.e(TAG, "Got error!", e);
			return false;
		}
		return true;
	}

	public String getDomain() {
		return domain;
	}

	public long getIdleTimeout() {
		return idleTimeoutMs / 1000L;
	}

	public void setIdleTimeout(long idleTimeout) {
		this.idleTimeoutMs = idleTimeout * 1000L;
	}

	public boolean isPingEnabled() {
		return pingEnabled;
	}

	public void setPingEnabled(boolean enabled) {
		this.pingEnabled = enabled;
		if (enabled) {
			if (pingScheduler == null) {
				long interval = idleTimeoutMs - 5000L;
				if (interval < 0) {
					interval /= 2;
				}
				pingScheduler = Executors.newScheduledThreadPool(1);
				pingScheduler.scheduleAtFixedRate(new Runnable() {
				@Override
				public void run() {
					try {
						WoopraPing ping = new WoopraPing(domain, getVisitor().getCookie(), clientInfo,
						idleTimeoutMs);
					ping.ping();
					} catch (Throwable t) {
						Log.e(TAG, "unknown ping error", t);
					}
				}

				}, interval, interval, TimeUnit.MILLISECONDS);
			}
		} else {
			if (pingScheduler != null) {
				pingScheduler.shutdown();
				pingScheduler = null;
			}
		}
	}

	public static String encodeUriComponent(String param) {
		try {
			return URLEncoder.encode(param, "utf-8");
		} catch (Exception e) {
			// will not throw an exception since utf-8 is supported.
		}
		return param;
	}

	public WoopraVisitor getVisitor() {
		return visitor;
	}

	public void setVisitor(WoopraVisitor visitor) {
		this.visitor = visitor;
	}

	public String getReferer() {
		return referer;
	}

	public void setReferer(String referer) {
		this.referer = referer;
	}

	public String getReferrer() {
		return referer;
	}

	public void setReferrer(String referer) {
		this.referer = referer;
	}

	public void setDeviceType(String deviceType){
		this.deviceType=deviceType;
	}

	public String getDeviceType(){
		return this.deviceType;
	}

	public void setVisitorProperty(String key, String value) {
	  if (value != null) {
	    getVisitor().setProperty(key, value);
	  }
	}

	public synchronized void setVisitorProperties(Map<String,String> newProperties) {
		getVisitor().setProperties(newProperties);
	}

	class EventRunner implements Runnable {
		WoopraEvent event = null;

		public EventRunner(WoopraEvent event) {
			this.event = event;
		}

		@Override
		public void run() {
			// send track event
			trackEventImpl(event);
		}
	}
}
