## Woopra Android SDK

## Instantiate Tracker Object
To setup your tracker SDK, configure the tracker instance as follows (replace `mybusiness.com` with your domain name):

``` java
// Java
WoopraTracker tracker = Woopra.getInstance(this /* context (e.g. activity) */).getTracker("mybusiness.com");
```

``` kotlin
// Kotlin
val tracker = Woopra.getInstance(this /* context (e.g. activity) */).getTracker("mybusiness.com")
```


To add custom visitor properties, you should edit the visitor object:

``` java
// Java
tracker.setVisitorProperty("name", "User Name");
tracker.setVisitorProperty("email", "user@company.com");
```

``` kotlin
// Kotlin
tracker.setVisitorProperty("name", "User Name")
tracker.setVisitorProperty("email", "user@company.com")
```

Or,

``` java
// Java
Map<String,String> visitorProps = new HashMap<String,String>();
visitorProps.put("name", "User Name");
visitorProps.put("email", "user@company.com");
tracker.setVisitorProperties(visitorProps);
```

``` kotlin
// Kotlin
val visitorProps: MutableMap<String, String> = HashMap()
visitorProps["name"] = "User Name"
visitorProps["email"] = "user@company.com"
tracker.setVisitorProperties(visitorProps)
```

## Event Tracking
To track an event, you must setup a `WoopraEvent` object and track it:

``` java
// Java
// setup event
WoopraEvent event = new WoopraEvent("appview");
event.setProperty("view", "home screen");
event.setProperty("title", "Home Screen");

// track event
tracker.trackEvent(event);
```

``` kotlin
// Kotlin
// setup event
val event = WoopraEvent("appview")
event.setProperty("view", "home screen")
event.setProperty("title", "Home Screen")

// track event
tracker.trackEvent(event)
```

## Identifying
You can send an identify call without tracking an event by using the `tracker.push()` method: 

``` java
// Java
tracker.setVisitorProperty("name", "User Name");
tracker.setVisitorProperty("email", "user@company.com");
// Visitor data has not been sent to Woopra
tracker.push();
// Visitor data has been sent to Woopra, but no event has been tracked
```

``` kotlin
// Kotlin
tracker.setVisitorProperty("name", "User Name")
tracker.setVisitorProperty("email", "user@company.com")
// Visitor data has not been sent to Woopra
tracker.push()
// Visitor data has been sent to Woopra, but no event has been tracked
```

## Advanced Settings
To add referrer information, timestamp, and other track request properties, look at the `WoopraTracker` and `WoopraEvent` class public methods for an exhaustive list of setter methods.  Here are some common examples:

### Tracker Settings

<<<<<<< HEAD
#### Track Referrer

``` java
// Java
tracker.setReferer(<REFERRER_STRING>);
// Or
tracker.setReferrer(<REFERRER_STRING>);
```

``` kotlin
// Kotlin
tracker.referer = <REFERRER_STRING>
// Or
tracker.referrer = <REFERRER_STRING>
```

> [!NOTE]
> For legacy of this SDK as well as the HTTP, you can use both `referer` or `referrer` methods, but it will be stored as `referer`.

#### Idle Timeout

You can update your idle timeout (default: 30 seconds) by updating the timeout property in your Woopra tracker:

``` java
// Java
tracker.setIdleTimeout(180);
```

``` kotlin
// Kotlin
tracker.idleTimeout = 180
```

> [!NOTE]
> The idle timeout is in seconds.

### Event Settings

You can explicitly set a timestamp for an event:

``` java
// Java
event.setTimestamp(<LONG_UNIX_MS_TIMESTAMP>);
```

``` kotlin
// Kotlin
event.timestamp = <LONG_UNIX_MS_TIMESTAMP>
```

> [!NOTE]
> The unix epoch time is in milliseconds.

## License

Except as otherwise noted, the Woopra Android SDK is licensed under the Apache License, Version 2.0 (http://www.apache.org/licenses/LICENSE-2.0.html).

Ruby on Rails is released under the [MIT License](http://www.opensource.org/licenses/MIT)

