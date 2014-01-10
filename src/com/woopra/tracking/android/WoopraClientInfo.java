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

import java.util.Locale;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.os.Build;
import android.util.DisplayMetrics;

public class WoopraClientInfo {

  private final String packageName;
  private final String version;
  private final String screenSize;
  private final String screenResolution;

  WoopraClientInfo(Context context) {
    // do not save the content here since it can result in a memory leak
    // just pre-compute everything needed
    this.packageName = context.getPackageName();
    this.version = getVersion(context);
    this.screenSize = getScreenSize(context);
    this.screenResolution = getScreenResolution(context); 
  }

  private String getScreenResolution(Context context) {
    DisplayMetrics dm = context.getResources().getDisplayMetrics();
    StringBuilder sb = new StringBuilder();
    sb.append(dm.widthPixels);
    sb.append('x');
    sb.append(dm.heightPixels);
    return sb.toString();
  }

  private String getVersion(Context context) {
    String version = "";
    try {
      PackageInfo pInfo = context.getPackageManager().getPackageInfo(
          context.getPackageName(), 0);
      version = pInfo.versionName;
    } catch (NameNotFoundException e) {
      // eat it
    }
    return version;
  }

  private String getScreenSize(Context context) {
    String size = "unknown";
    int screen = context.getResources().getConfiguration().screenLayout
        & Configuration.SCREENLAYOUT_SIZE_MASK;
    if (screen == Configuration.SCREENLAYOUT_SIZE_SMALL) {
      size = "small";
    } else if (screen == Configuration.SCREENLAYOUT_SIZE_NORMAL) {
      size = "normal";
    } else if (screen == Configuration.SCREENLAYOUT_SIZE_LARGE) {
      size = "large";
    } else if (screen == 4 /* Configuration.SCREENLAYOUT_SIZE_XLARGE */) {
      size = "xlarge";
    }
    return size;
  }

  public String getScreenResolution() {
    return this.screenResolution;
  }

  public String getClient() {
    StringBuilder sb = new StringBuilder();
    sb.append(packageName);
    sb.append('/');
    sb.append(version);
    return sb.toString();
  }

  public String getUserAgent() {
    StringBuilder sb = new StringBuilder();
    sb.append(packageName);
    sb.append('/');
    sb.append(version);
    sb.append(' ');

    sb.append("(AndroidAPK; Android ");
    sb.append(Build.VERSION.RELEASE);
    sb.append("; ");
    sb.append(screenSize);
    sb.append("; ");
    sb.append(Build.MANUFACTURER);
    sb.append("; ");
    sb.append(Build.MODEL);
    sb.append("; ");
    sb.append(Build.PRODUCT);
    sb.append('/');
    sb.append(Build.ID);
    sb.append(')');

    return sb.toString();
  }

  public Object getLanguage() {
    return Locale.getDefault().toString().replace("_", "-");
  }
}
