# Cordova_Test_Plugin
This is a simple plugin for use in cordova application.

Greeting a user with "Hello, world" is something that could be done in JavaScript. This plugin provides a simple example demonstrating how Cordova plugins work.

## Using
Clone the plugin

    $ git clone https://github.com/don/cordova-plugin-hello.git

Create a new Cordova Project

    $ cordova create hello com.example.helloapp Hello
    
Install the plugin

    $ cd hello
    $ cordova plugin add ../cordova-plugin-hello
    

Edit `www/js/index.js` and add the following code inside `onDeviceReady`

```js
    var success = function(message) {
        alert(message);
    }

    var failure = function() {
        alert("Error calling Hello Plugin");
    }

    hello.greet("World", success, failure);
```

Install iOS or Android platform

    cordova platform add ios
    cordova platform add android