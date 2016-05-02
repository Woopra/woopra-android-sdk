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

import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import android.util.Log;

/**
 * @author Woopra on 1/26/2013
 * 
 */
public class WoopraPing {

	private static String TAG = WoopraPing.class.getName();
	private static final String W_PING_ENDPOINT = "http://www.woopra.com/track/ping/";

	private final WoopraClientInfo clientInfo;

	private final String pingUrl;

	public WoopraPing(String domain, String cookie, WoopraClientInfo clientInfo, long idleTimeout) {
		StringBuilder pingUrlBuilder = new StringBuilder();
		try {
			pingUrlBuilder.append(W_PING_ENDPOINT)
				.append("?host=")
				.append(URLEncoder.encode(domain, "UTF-8"))
				.append("&cookie=")
				.append(URLEncoder.encode(cookie, "UTF-8"))
				.append("&screen=")
				.append(clientInfo.getScreenResolution())
				.append("&language=")
				.append(URLEncoder.encode(clientInfo.getLanguage(), "UTF-8"))
				.append("&browser=")
				.append(URLEncoder.encode(clientInfo.getClient(), "UTF-8"))
				.append("&app=android&response=xml&timeout=").append(idleTimeout);
		} catch (UnsupportedEncodingException e) {
		// eat it and will never happen
		}
		this.pingUrl = pingUrlBuilder.toString();
		this.clientInfo = clientInfo;
	}

	public void ping() {
		//
		try {
			Log.d(TAG, "Sending ping request:" + pingUrl);
			URL url = new URL(pingUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestProperty("User-Agent", clientInfo.getUserAgent());
			connection.connect();
			int result_code = connection.getResponseCode();
			Log.d(TAG, "Response:" + result_code);
		} catch (Exception e) {
			Log.e(TAG, "Got error!", e);
		}
	}
}
