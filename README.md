## Woopra Android SDK

To setup your tracker SDK, configure the tracker instance as follows (replace mybusiness.com with your website):

``` java
WoopraTracker tracker = Woopra.getInstance(this /* context (e.g. activity) */).getTracker("mybusiness.com");
```

You can update your idle timeout (default: 30 seconds) by updating the timeout property in your WTracker instance:

``` java
tracker.setIdleTimeout(180); // in seconds
```

If you want to keep the user online on Woopra even if they don't commit any event between the last event and the idleTimeout, you can enable auto pings:

``` java
tracker.setPingEnabled(true); // default is false
```

To add custom visitor properties, you should edit the visitor object:

``` java
tracker.setVisitorProperty("name", "User Name");
tracker.setVisitorProperty("email", "user@company.com");
```

Or,

``` java
Map<String,String> visitorProps = new HashMap<String,String>();
visitorProps.put("name", "User Name");
visitorProps.put("email", "user@company.com");
tracker.setVisitorProperty(visitorProps);
```

To track an event, you must setup a `WoopraEvent` object and track it:

``` java
// setup event
WoopraEvent event = new WoopraEvent("appview");
event.setEventProperty("view", "home screen");
event.setEventProperty("title", "Home Screen");

// track event
tracker.trackEvent(event);
```

To add referer information, timestamp, and other track request properties, look at the WoopraTRracker class for an exhaustive list of settier methods.  Here are some common examples:

Track Referrer:
```
tracker.setReferrer(<REFERER_STRING>); //for legacy of this sdk as well as the HTTP, you can use both referer or referrer interchangeably
```

Explicitly Set Timestamp:
```
tracker.setTimestamp(<LONG_UNIX_MS_TIMESTAMP>); //Note this is unix epoch time in milliseconds
```


## License

Except as otherwise noted, the Woopra Android SDK is licensed under the Apache License, Version 2.0 (http://www.apache.org/licenses/LICENSE-2.0.html).

Ruby on Rails is released under the [MIT License](http://www.opensource.org/licenses/MIT)

