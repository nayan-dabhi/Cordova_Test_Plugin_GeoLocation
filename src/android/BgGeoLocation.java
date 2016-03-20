package com.nd.bgGeoLocation;

import org.apache.cordova.*;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class BgGeoLocation extends CordovaPlugin {

    public static String userId;
    public static String postURL;
    public static Integer timerInterval;

    public static AlarmManager alarmManager;
    public static PendingIntent pendingIntent;

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("start")) {
            JSONObject parameters = args.getJSONObject(0);

            userId = parameters.getString("userId");
            postURL = parameters.getString("postURL");
            timerInterval = Integer.parseInt(parameters.getString("timerInterval"));

            initialize(callbackContext);
            return true;
        } else {
            return false;
        }
    }

    public boolean initialize(CallbackContext callbackContext) {
        AlarmManager alarmManager = (AlarmManager) cordova.getActivity().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(cordova.getActivity().getApplicationContext(), BackgroundLocationReceiver.class);
        // PendingIntent pendingIntent = PendingIntent.getBroadcast(BrodActivity.this, 0, intent, 0);
        // alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), timerInterval, pendingIntent);

        callbackContext.success(" User Id : " + userId + ", \n postURL : "+ postURL + ", \n Interval : " + timerInterval);
        return true;
    }
}