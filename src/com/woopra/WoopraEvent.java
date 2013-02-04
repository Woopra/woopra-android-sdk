package com.woopra;

import java.util.Properties;
/**
 * @author Woopra on 1/26/2013
 * 
 */
public class WoopraEvent {
	private String eventName = null;
	private Properties properties = null;

	public WoopraEvent(String eventName) {
		this.eventName = eventName;
		properties = new Properties();
	}

	public WoopraEvent(String eventName, Properties properties) {
		this.eventName = eventName;
		if (properties != null) {
			this.properties = properties;
		} else {
			properties = new Properties();
		}
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	public void addEventProperties(Properties newProperties) {
		properties.putAll(newProperties);
	}

	public void addEventProperty(String key, String value) {
		properties.setProperty(key, value);
	}
}
