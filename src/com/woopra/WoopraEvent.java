package com.woopra;

import java.util.LinkedHashMap;
import java.util.Map;
/**
 * @author Woopra on 1/26/2013
 * 
 */
public class WoopraEvent {
	private Map<String,String> properties = null;

	public WoopraEvent(String eventName) {
		this(eventName, new LinkedHashMap<String,String>());
	}

	public WoopraEvent(String eventName, Map<String,String> properties) {
		this.properties = properties;
		if (this.properties == null) {
			this.properties = new LinkedHashMap<String,String>();
		}
		this.properties.put("name", eventName);
	}

	public Map<String,String> getProperties() {
		return properties;
	}

	public void setProperties(Map<String,String> properties) {
		this.properties = properties;
	}

	public void addEventProperties(Map<String,String> newProperties) {
		properties.putAll(newProperties);
	}

	public void addEventProperty(String key, String value) {
		properties.put(key, value);
	}
}
