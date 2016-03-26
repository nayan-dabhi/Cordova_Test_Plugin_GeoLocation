package com.nd.bgGeoLocation;

import org.apache.cordova.*;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.content.SharedPreferences;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class BgGeoLocation extends CordovaPlugin {
    public static String userId, userEmail, userLoginToken, postURL;
    public static Integer timerInterval;

    public static Context context;
    public static AlarmManager alarmManager;
    public static PendingIntent pendingIntent;

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equalsIgnoreCase("start_update")) {
            JSONObject parameters = args.getJSONObject(0);
            // userId = parameters.getString("userId");
            // userEmail = parameters.getString("userEmail");
            // userLoginToken = parameters.getString("userLoginToken");
            // postURL = parameters.getString("postURL");
            timerInterval = Integer.parseInt(parameters.getString("timerInterval"));

            SharedPreferences mSharedPreferences = cordova.getActivity().getSharedPreferences("locationPref", cordova.getActivity().getApplicationContext().MODE_WORLD_READABLE);
            SharedPreferences.Editor prefsEditor = mSharedPreferences.edit();
            prefsEditor.putString("userId", parameters.getString("userId"));
            prefsEditor.putString("userEmail", parameters.getString("userEmail"));
            prefsEditor.putString("userLoginToken", parameters.getString("userLoginToken"));
            prefsEditor.putString("postURL", parameters.getString("postURL"));
            prefsEditor.putInt("timerInterval",Integer.parseInt(parameters.getString("timerInterval")));
            prefsEditor.commit();

            start_update(callbackContext);
            return true;
        } else if (action.equalsIgnoreCase("stop_update")) {
            stop_update(callbackContext);
            return true;
        } else {
            return false;
        }
    }

    public void start_update(CallbackContext callbackContext) {
        context = cordova.getActivity().getApplicationContext();

        alarmManager = (AlarmManager) cordova.getActivity().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, BackgroundLocationReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), timerInterval, pendingIntent);

        JSONObject response = new JSONObject();
        try {
            response.put("status", "success");
        } catch (JSONException e) {
            // response.put("status", "failed");
        }
        callbackContext.success(response);
    }

    public void stop_update(CallbackContext callbackContext) {
        alarmManager.cancel(pendingIntent);
        SharedPreferences mSharedPreferences = cordova.getActivity().getSharedPreferences("locationPref", cordova.getActivity().getApplicationContext().MODE_WORLD_READABLE);
        mSharedPreferences.edit().clear().commit();

        JSONObject response = new JSONObject();
        try {
            response.put("status", "success");
        } catch (JSONException e) {
            // response.put("status", "failed");
        }
        callbackContext.success(response);
    }
}