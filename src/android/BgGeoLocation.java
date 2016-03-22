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

    public static Context context;
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
        }if (action.equals("stop_update")) {
            stopAlarm(callbackContext);
            return true;
        } else {
            return false;
        }
    }

    public void initialize(CallbackContext callbackContext) {
        context = cordova.getActivity().getApplicationContext();

        alarmManager = (AlarmManager) cordova.getActivity().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, BackgroundLocationReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), timerInterval, pendingIntent);

        JSONObject response = new JSONObject();
        response.put("status", "success");
        callbackContext.success(response);
    }

    public void stopAlarm(CallbackContext callbackContext) {
        alarmManager.cancel(pendingIntent);
        JSONObject response = new JSONObject();
        response.put("status", "success");
        callbackContext.success(response);
    }
}