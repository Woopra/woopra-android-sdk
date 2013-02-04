To setup your tracker SDK, configure the tracker instance as follows (replace mybusiness.com with your website):

``` java
WoopraTracker.getInstance().setup("mybusiness.com");
```

You can update your idle timeout (default: 30 seconds) by updating the timeout property in your WTracker instance:

``` java
WoopraTracker.getInstance().setIdleTimeout(180); // in seconds
```

If you want to keep the user online on Woopra even if they don't commit any event between the last event and the idleTimeout, you can enable auto pings:

``` java
WoopraTracker.getInstance().setPingEnabled(true); // default is false
```

To add custom visitor properties, you should edit the visitor object:

``` java
WoopraTracker.getInstance().addVisitorProperty("name", "User Name");
WoopraTracker.getInstance().addVisitorProperty("email", "user@company.com");
```

Or,

``` java
Properties visitorProps = new Properties();
visitorProps.setProperty("name", "User Name");
visitorProps.setProperty("email", "user@company.com");
WoopraTracker.getInstance().addVisitorProperties(visitorProps);
```

To track an event, you must setup a `WoopraEvent` object and track it:

``` java
// setup event
WoopraEvent event = new WoopraEvent("appview");
event.addEventProperty("view", "home screen");
event.addEventProperty("title", "Home Screen");

// track event
WoopraTracker.getInstance().trackEvent(event);
```

