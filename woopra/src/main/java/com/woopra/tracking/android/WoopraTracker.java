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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import android.util.Log;

/**
 * @author Woopra on 1/26/2013
 *
 */
public class WoopraTracker {

	private final static ExecutorService executor = Executors.newFixedThreadPool(1);


	private final Woopra woopraContext;
	private final String domain;
	// default timeout value for Woopra service
	private long idleTimeoutMs = 5 * 60 * 1000;

	//
	private String referer = null;
	private String deviceType = null;

	/**
	 *
	 * @param domain
	 * @param woopraContext
	 */
	public WoopraTracker(String domain, Woopra woopraContext) {
		this.domain = domain;
		this.woopraContext = woopraContext;
	}

	/**
	 *
	 * @return
	 */
	public Woopra getWoopraContext(){
		return this.woopraContext;
	}

	/**
	 *
	 * @param event
	 * @return
	 */
	public boolean trackEvent(WoopraEvent event) {
		event.setTracker(this);
		executor.submit(event);
		return true;
	}

	/**
	 *
	 */
	public boolean push() {
		WoopraIdentify identify = new WoopraIdentify(this);
		executor.submit(identify);
		return true;
	}

	/**
	 *
	 * @return
	 */
	public String getDomain() {
		return domain;
	}

	/**
	 *
	 * @return
	 */
	public long getIdleTimeout() {
		return idleTimeoutMs / 1000L;
	}

	/**
	 *
	 * @param idleTimeout
	 */
	public void setIdleTimeout(long idleTimeout) {
		this.idleTimeoutMs = idleTimeout * 1000L;
	}

	/**
	 *
	 * @return
	 */
	@Deprecated
	public WoopraVisitor getVisitor() {
		return woopraContext.getVisitor();
	}

	/**
	 *
	 * @param visitor
	 */
	@Deprecated
	public void setVisitor(WoopraVisitor visitor) {
		woopraContext.setVisitor(visitor);
	}

	/**
	 *
	 * @return
	 */
	public String getReferer() {
		return referer;
	}

	/**
	 *
	 * @param referer
	 */
	public void setReferer(String referer) {
		this.referer = referer;
	}

	/**
	 *
	 * @return
	 */
	public String getReferrer() {
		return referer;
	}

	/**
	 *
	 * @param referer
	 */
	public void setReferrer(String referer) {
		this.referer = referer;
	}

	/**
	 *
	 * @return
	 */
	public String getDeviceType(){
		return this.deviceType;
	}

	/**
	 *
	 * @param deviceType
	 */
	public void setDeviceType(String deviceType){
		this.deviceType=deviceType;
	}

	/**
	 *
	 * @param key
	 * @param value
	 */
	public void setVisitorProperty(String key, Object value) {
	  if (key != null && value != null) {
		  woopraContext.getVisitor().setProperty(key, value);
	  }
	}

	/**
	 *
	 * @param newProperties
	 */
	public synchronized void setVisitorProperties(Map<String, Object> newProperties) {
		woopraContext.getVisitor().setProperties(newProperties);
	}
 
}
