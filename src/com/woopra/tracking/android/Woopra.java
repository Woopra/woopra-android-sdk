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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.content.Context;
import android.util.Log;

public class Woopra {
  private final static String TAG = Woopra.class.getName();

  private final static Object sync = new Object();

  private final static Map<String, WoopraTracker> trackers = new HashMap<String, WoopraTracker>();

  private final static ExecutorService executor = Executors
      .newFixedThreadPool(1);

  private static WoopraVisitor visitor;
  private static WoopraClientInfo clientInfo;

  public static Woopra getInstance(Context c) {
    return new Woopra(c);
  }

  private Woopra(Context context) {
    synchronized (sync) {
      if (visitor == null) {
        visitor = WoopraVisitor.getVisitorByContext(context);
        Log.d(TAG, "WoopraVistor Cookie: " + visitor.getCookie());
      }
      if (clientInfo == null) {
        clientInfo = new WoopraClientInfo(context);
        Log.d(TAG, "UserAgent: " + clientInfo.getUserAgent());
      }
    }
  }

  public WoopraTracker getTracker(String domain) {
    synchronized (sync) {
      WoopraTracker tracker = trackers.get(domain);
      if (tracker == null) {
        tracker = new WoopraTracker(executor, domain, visitor, clientInfo);
        trackers.put(domain, tracker);
      }
      return tracker;
    }
  }

}
