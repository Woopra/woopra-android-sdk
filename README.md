# Woopra Android SDK

## Installation

To declare the mavenCentral() repository. Add mavenCentral() in the project-level build.gradle.
``` gradle
allprojects {
    repositories {
        ...
        mavenCentral()
        ...
    }
}
```

Or in the app module's build.gradle
``` gradle
repositories {
    ...
    mavenCentral()
    ...
}
```

Then add dependencies to app/build.gradle
``` gradle
implementation 'com.appier:woopra-android:1.1.1'
```

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

## Event Tracking

To track an event, you must setup a `WoopraEvent` object and track it:

``` java
// Java
// setup event
WoopraEvent event = new WoopraEvent("app_viewed");
event.setProperty("view", "home screen");
event.setProperty("title", "Home Screen");
event.setProperty("property_boolean", true);

// track event
tracker.trackEvent(event);
```

``` kotlin
// Kotlin
// setup event
val event = WoopraEvent("app_viewed")
event.setProperty("view", "home screen")
event.setProperty("title", "Home Screen")
event.setProperty("property_boolean", true)

// track event
tracker.trackEvent(event)
```

## Identifying

To add custom visitor properties, you should edit the visitor object:

``` java
// Java
tracker.setVisitorProperty("name", "John Smith");
tracker.setVisitorProperty("email", "john@smith.com");
```

``` kotlin
// Kotlin
tracker.setVisitorProperty("name", "John Smith")
tracker.setVisitorProperty("email", "john@smith.com")
```

You can then send an identify call without tracking an event by using the `tracker.push()` method:

``` java
// Java
tracker.push();
```

``` kotlin
// Kotlin
tracker.push()
```

## Advanced Settings

To add referrer information, timestamp, and other track request properties, look at the `WoopraTracker` and `WoopraEvent` class public methods for an exhaustive list of setter methods.  Here are some common examples:

### Tracker Settings

#### Track Referrer

``` java
// Java
tracker.setReferer(<REFERRER_STRING>);
```

``` kotlin
// Kotlin
tracker.referer = <REFERRER_STRING>
```

> [!NOTE]
> For legacy of this SDK as well as the HTTP, you can use both `referer` or `referrer` methods, but it will be stored as `referer`.

#### Idle Timeout

You can update your idle timeout (default: 5 minutes) by updating the timeout property in your Woopra tracker:

``` java
// Java
tracker.setIdleTimeout(300);
```

``` kotlin
// Kotlin
tracker.idleTimeout = 300
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

