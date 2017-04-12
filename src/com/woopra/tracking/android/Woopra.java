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

import java.util.HashMap;
import java.util.Map;
import android.content.Context;

public class Woopra {

  private final static Map<String, WoopraTracker> trackers = new HashMap<String, WoopraTracker>();
  public static Woopra getInstance(Context ctx) {
       return new Woopra(ctx);
  }


  private WoopraVisitor visitor;
  private WoopraClientInfo clientInfo;

  /**
   *
   * @param context
   */
  private Woopra(Context context) {
      this.visitor = WoopraVisitor.getVisitorByContext(context);
      this.clientInfo = new WoopraClientInfo(context);
  }

  /**
   *
   * @param visitor
   */
  @Deprecated
  public void setVisitor(WoopraVisitor visitor){
    this.visitor=visitor;
  }

  /**
   *
   * @return
   */
  public WoopraVisitor getVisitor(){
    return this.visitor;
  }

  /**
   *
   * @return
   */
  public WoopraClientInfo getClientInfo(){
    return this.clientInfo;
  }

  /**
   *
   * @param domain
   * @return
   */
  public synchronized WoopraTracker getTracker(String domain) {
      WoopraTracker tracker = trackers.get(domain);
      if (tracker == null) {
        tracker = new WoopraTracker(domain, this);
        trackers.put(domain, tracker);
      }
      return tracker;
    }

}
