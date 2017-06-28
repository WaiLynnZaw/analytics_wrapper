# Google Analytics Wrapper

Easily integrate Google Analytics into your android app without downloading any of the Google SDKs.



## Set Up


### Add this line to your module ```build.gradle```
	repositories {
    	maven {
    		url "http://dl.bintray.com/wailynnzaw/maven"
    	}
	}
	
    compile 'co.monadlab.wailynnzaw:gawrapper:0.0.1'
## Usage
#### In your application class,

```swift
  private static final String GA_PROPERTY_ID = "UA-XXXXX-XX";

  static AnalyticsWrapper wrapper;

  private static Application instance;

  @Override
  public void onCreate() {
    super.onCreate();
    instance = this;
    initialize();
  }

  private static Context getContext(){
    return instance;
  }


  public static synchronized void initialize() {
    wrapper = new AnalyticsWrapper(getContext(),GA_PROPERTY_ID);
  }

  public static AnalyticsWrapper getWrapper() {
    return wrapper;
  }
```



You can track any event you wish to using the ``event()`` method on the ``GAWrapper``. Example:

```
Application
    .getWrapper()
    .event("Auth", "Login", "Login Completed", data);
```

You can use ``screenView()`` method on the ``GAWrapper`` to track screens Example:

```
Application
    .getWrapper()
    .screenView("MainActivity", data);
```


## License

Copyright 2017 Wai Lynn Zaw

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

[http://www.apache.org/licenses/LICENSE-2.0](http://www.apache.org/licenses/LICENSE-2.0)
    
Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
