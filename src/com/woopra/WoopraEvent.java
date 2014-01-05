package com.woopra;

import java.util.HashMap;
import java.util.Map;
/**
 * @author Woopra on 1/26/2013
 * 
 */
public class WoopraEvent {
	private final Map<String,String> properties = new HashMap<String,String>();

	public WoopraEvent(String eventName) {
		this(eventName, null);
	}

	public WoopraEvent(String eventName, Map<String,String> properties) {
		if (this.properties != null) {
		  
			this.properties.putAll(properties);
		}
		this.properties.put("name", eventName);
	}

	public Map<String,String> getProperties() {
		return properties;
	}

	public void addEventProperties(Map<String,String> newProperties) {
		properties.putAll(newProperties);
	}

	public void addEventProperty(String key, String value) {
		properties.put(key, value);
	}
}
