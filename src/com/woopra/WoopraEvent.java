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
		super(eventName, new Properties();
	}

	public WoopraEvent(String eventName, Properties properties) {
		this.properties = properties;
		this.properties.setProperty("name", eventName);
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
