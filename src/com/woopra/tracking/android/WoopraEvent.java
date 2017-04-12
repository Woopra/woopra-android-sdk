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

import java.util.HashMap;
import java.util.Map;

/**
 * @author Woopra on 1/26/2013
 *
 */
public class WoopraEvent {
	private String eventName;
	private long timestamp = null;

	private final Map<String, String> properties = new java.util.concurrent.ConcurrentHashMap<String, String>();

	public WoopraEvent(String eventName) {
		this(eventName, null);
	}

	public WoopraEvent(String eventName, Map<String,String> properties) {
		this.eventName=eventName;
		if (properties != null) {
			this.properties.putAll(properties);
		}
	}

	public String getName() {
		return this.eventName;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public Map<String,String> getProperties() {
		return properties;
	}

	public void setEventProperty(Map<String,String> newProperties) {
		properties.putAll(newProperties);
	}

	public void setEventProperty(String key, String value) {
		properties.put(key, value);
	}
}
