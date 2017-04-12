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
public class WoopraPing implements Runnable{

	private final WoopraTracker tracker;
	/**
	 *
	 * @param tracker
	 */
	public WoopraPing(WoopraTracker tracker) {
		this.tracker=tracker;
	}

	/**
	 *
	 */
	@Override
	public void run() {
		try {

			String pingUrl = new StringBuilder()
					.append(Constants.W_PING_ENDPOINT)
					.append("?host=")
					.append(Utils.encode(tracker.getDomain()))
					.append("&cookie=")
					.append(Utils.encode(tracker.getWoopraContext().getVisitor().getCookie()))
					.append("&app=android&response=xml&timeout=")
					.append(tracker.getIdleTimeout()).toString();

			Log.d(WoopraPing.class.getName(), "Sending ping request:" + pingUrl);
			URL url = new URL(pingUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.connect();
			int result_code = connection.getResponseCode();
			Log.d(WoopraPing.class.getName(), "Response:" + result_code);
		} catch (Exception e) {
			Log.e(WoopraPing.class.getName(), "Got error!", e);
		}
	}
}
