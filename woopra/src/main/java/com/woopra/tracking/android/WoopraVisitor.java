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

import java.util.Map;
import java.util.UUID;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * @author Woopra on 1/26/2013
 * 
 */
public class WoopraVisitor {


	private String cookie;
	private final Map<String, String> properties = new java.util.concurrent.ConcurrentHashMap<String, String>();

	private WoopraVisitor() {
	}

	public static WoopraVisitor getVisitorByContext(Context context) {
		WoopraVisitor visitor = null;
		// Application wide preferences
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(context);
		String cookieStore = preferences.getString(Constants.COOKIE_KEY, Constants.NOT_SET);
		// if application first start, create a new cookie
		if (Constants.NOT_SET.equals(cookieStore)) {
			visitor = getAnonymousVisitor();
			SharedPreferences.Editor editor = preferences.edit();
			editor.putString(Constants.COOKIE_KEY, visitor.getCookie());
			editor.commit();
			// else use the exist cookie
		} else {
			visitor = getVisitorByCookie(cookieStore);
		}
		return visitor;
	}

	public static WoopraVisitor getAnonymousVisitor() {
		WoopraVisitor visitor = new WoopraVisitor();
		visitor.setCookie(getUUID());
		return visitor;
	}

	public static WoopraVisitor getVisitorByCookie(String cookie) {
		WoopraVisitor visitor = new WoopraVisitor();
		visitor.setCookie(cookie);
		return visitor;
	}

	public static WoopraVisitor getVisitorByEmail(String email) {
		WoopraVisitor visitor = new WoopraVisitor();
		visitor.setCookie(Utils.getUUID(Constants.APP_KEY, email));
		visitor.setProperty("email", email);
		return visitor;
	}

	public static WoopraVisitor getVisitorByString(String key) {
		WoopraVisitor visitor = new WoopraVisitor();
		visitor.setCookie(Utils.getUUID(Constants.APP_KEY, key));
		visitor.setProperty("email", key);
		return visitor;
	}



	private static String getUUID() {
		String s = UUID.randomUUID().toString();
		return s.replace("-", "");
	}

	/**
	 *
	 * @return
	 */
	public String getCookie() {
		return cookie;
	}

	/**
	 *
	 * @param cookie
	 */
	public void setCookie(String cookie) {
		this.cookie = cookie;
	}

	/**
	 *
	 * @return
	 */
	public Map<String,String> getProperties() {
		return properties;
	}

	/**
	 *
	 * @param newProperties
	 */
	public void setProperties(Map<String,String> newProperties) {
		properties.putAll(newProperties);
	}

	/**
	 *
	 * @param key
	 * @param value
	 */
	public void setProperty(String key, String value) {
		properties.put(key, value);
	}
}
