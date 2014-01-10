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

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;

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
		pingUrlBuilder.append(W_PING_ENDPOINT).append("?host=").append(domain)
				.append("&cookie=").append(cookie)
        .append("&screen=")
        .append(clientInfo.getScreenResolution())
        .append("&language=")
        .append(clientInfo.getLanguage())
        .append("&browser=")
        .append(clientInfo.getClient())
				.append("&app=android&response=xml&timeout=").append(idleTimeout);
		this.pingUrl = pingUrlBuilder.toString();
		this.clientInfo = clientInfo;
	}

	public void ping() {
		//
		HttpClient pingHttpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(pingUrl);
		httpGet.setHeader(CoreProtocolPNames.USER_AGENT, clientInfo.getUserAgent());
		try {
			Log.d(TAG, "Sending ping request:" + pingUrl);
			HttpResponse response = pingHttpClient.execute(httpGet);
			Log.d(TAG, "Response:" + EntityUtils.toString(response.getEntity()));
		} catch (Exception e) {
			Log.e(TAG, "Got error!", e);
		}
	}
}
