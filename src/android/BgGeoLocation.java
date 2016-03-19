package com.nd.bgGeoLocation;

import org.apache.cordova.*;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

public class BgGeoLocation extends CordovaPlugin {

    public static String userId;

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("start")) {
            JSONObject parameters = args.getJSONObject(0);

            userId = parameters.getString("userId");
            String user_id = parameters.getString("userId");
            String message = "User Id : " + user_id + ", Public User Id : " + userId;

            callbackContext.success(message);
            return true;
        } else {
            return false;
        }
    }
}