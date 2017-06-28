[![Bintray](https://img.shields.io/badge/jcenter-v0.0.2-brightgreen.svg?maxAge=2592000)](https://bintray.com/wailynnzaw/maven/GAWrappper)
[![Hex.pm](https://img.shields.io/badge/size-7.36KB-blue.svg)]()

# Google Analytics Wrapper

Easily integrate Google Analytics into your android app without downloading any of the Google SDKs.



## Set Up


### Add this line to your module ```build.gradle```
```	
compile 'co.monadlab.wailynnzaw:gawrapper:0.0.2'
```
## Usage
#### In your application class's onCreate,

```swift
  wrapper = new AnalyticsWrapper.Builder(getContext())
            .trackerId(GA_PROPERTY_ID)
            .quietMode(false)
            .uuid(UUID.randomUUID().toString()) // Should be saved with preferences for UUID
            .build();
```

``GAWrapper`` ``screenView()`` and ``event()``. Example:

```
Application.getWrapper().screenView("AnalyticsExample");

Application.getWrapper()
    .event("Auth", "Login", "Login Completed", 0);


```
#### See example for detail usage.

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
