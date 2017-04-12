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
	//locals
	private String eventName;
	private long timestamp = -1;

	private String campaignName = null;
	private String campaignId = null;
	private String campaignSource = null;
	private String campaignTerm = null;
	private String campaignMedium = null;
	private String campaignContent = null;

	private final Map<String, String> properties = new java.util.concurrent.ConcurrentHashMap<String, String>();

	private final Map<String, String> campaignProperties = createCampaignPropsmap()
	private static Map<String, String> createCampaignPropsmap() {
		Map<String, String> campaignProperties = new java.util.concurrent.ConcurrentHashMap<String, String>();
		campaignProperties.put("campaign_name", null);
		campaignProperties.put("campaign_id", null);
		campaignProperties.put("campaign_source", null);
		campaignProperties.put("campaign_term", null);
		campaignProperties.put("campaign_medium", null);
		campaignProperties.put("campaign_content", null);
		return campaignProperties;
	}

	//Constructors
	public WoopraEvent(String eventName) {
		this(eventName, null);
	}

	public WoopraEvent(String eventName, Map<String,String> properties) {
		this.eventName=eventName;
		if (properties != null) {
			this.properties.putAll(properties);
		}
	}


	//Event Base Props Accessors
	public String getName() {
		return this.eventName;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}


	//Campaign data
	public Map<String, String> getCampaignProperties() {
		return campaignProperties;
	}
	public void setCampaignProperties(Map<String, String> newProperties) {
		campaignProperties.putAll(newProperties);
	}
	public void setCampaignProperty(String key, String value) {
		campaignProperties.put(key, value);
	}

	public String getCampaignName() {
		return campaignProperties.get("campaign_name");
	}

	public void setCampaignName(String campaignName) {
		campaignProperties.put("campaign_name", campaignName);
	}

	public String getCampaignId() {
		return campaignProperties.get("campaign_id");
	}

	public void setCampaignId(String campaignId) {
		campaignProperties.put("campaign_id", campaignId);
	}

	public String getCampaignSource() {
		return campaignProperties.get("campaign_source)";
	}

	public void setCampaignSource(String campaignSource) {
		campaignProperties.put("campaign_source", campaignSource);
	}

	public String getCampaignTerm() {
		return campaignProperties.get("campaign_term");
	}

	public void setCampaignTerm(String campaignTerm) {
		campaignProperties.put("campaign_term", campaignTerm);
	}

	public String getCampaignMedium() {
		return campaignProperties.get("campaign_medium");
	}

	public void setCampaignMedium(String campaignMedium) {
		campaignProperties.put("campaign_medium", campaignMedium);
	}

	public String getCampaignContent() {
		return campaignProperties.get("campaign_content");
	}

	public void setCampaignContent(String campaignContent) {
		campaignProperties.put("campaign_content", campaignContent);
	}



	//Event Prop Accessors
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
