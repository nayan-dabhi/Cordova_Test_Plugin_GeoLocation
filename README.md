# Cordova_Test_Plugin
This plugin used in cordova application for update user location in background mode.


## Using
Clone the plugin

    $ git clone https://github.com/nayan-dabhi/Cordova_Test_Plugin_GeoLocation

Create a new Cordova Project

    $ cordova create hello com.example.helloapp Hello

Install iOS or Android platform

    cordova platform add ios
    cordova platform add android

Install the plugin

    $ cd hello
    $ cordova plugin add https://github.com/nayan-dabhi/Cordova_Test_Plugin_GeoLocation


Edit `www/js/index.js` and add the following code inside `onDeviceReady`

```js
    start: function() {
      if(window.cordova){
        var dataObj = {
          "userId": 'userId',
          "userEmail": 'userEmail',
          "userLoginToken": 'loginToken',
          "postURL": 'apiURL',
          "timerInterval": interval
        }
        // console.log(dataObj);

        bgGeoLocation.start_update(dataObj, function(data) {
          // console.log(data);
          if(data){
            if(data.status == 'success'){
              console.log("Background location update started.");
            }
          }
        }, function() {
          console.log("Error calling start_update.");
        });
      } else {
        console.log("application running on browser.");
      }
    }

    stop: function() {
      if(window.cordova){
        bgGeoLocation.stop_update("", function(data) {
         // console.log(data);
         if(data){
           if(data.status == 'success'){
             console.log("Background location update stoped.");
           }
         }
       }, function() {
         console.log("Error calling stop_update.");
       });
      } else {
        console.log("application running on browser.");
      }
    }
```
