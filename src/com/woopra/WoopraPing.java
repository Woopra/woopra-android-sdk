package com.woopra;

import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;
import java.util.Properties;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.util.Log;

/**
 * @author Woopra on 1/26/2013
 * 
 */
public class WoopraPing {

	public static String LOG_TAG = "WoopraPing";
	private static final String W_PING_ENDPOINT = "http://www.woopra.com/track/ping/";

	private boolean stopPing = false;
	private int interval = 0;
	StringBuilder pingUrlBuilder = new StringBuilder();

	public WoopraPing(String domain, String cookie, int idleTimeout) {
		stopPing = false;
		pingUrlBuilder = new StringBuilder();
		pingUrlBuilder.append(W_PING_ENDPOINT).append("?host=").append(domain)
				.append("&cookie=").append(cookie)
				.append("&response=xml&timeout=").append(idleTimeout);
		interval = idleTimeout - 5;
		if (interval <= 0) {
			interval = idleTimeout;
		}
	}

	public void ping() {
		//
		HttpClient pingHttpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(pingUrlBuilder.toString());
		while (!stopPing) {
			try {
				TimeUnit.SECONDS.sleep(interval);
			} catch (InterruptedException e) {
				Log.e(LOG_TAG, "Got Error!", e);
			}
			try {
				Log.i(LOG_TAG,
						"Sending ping request:" + pingUrlBuilder.toString());
				HttpResponse response = pingHttpClient.execute(httpGet);
				Log.i(LOG_TAG,
						"Response:"
								+ EntityUtils.toString(response.getEntity()));
			} catch (Exception e) {
				Log.e(LOG_TAG, "Got error!", e);
			}
		}
	}

	public void stopPing() {
		stopPing = true;
	}
}
