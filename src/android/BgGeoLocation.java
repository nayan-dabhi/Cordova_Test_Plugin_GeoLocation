package com.nd.bgGeoLocation;

import org.apache.cordova.*;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

public class BgGeoLocation extends CordovaPlugin {

    public static String userId;
    public static String postURL;

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("start")) {
            JSONObject parameters = args.getJSONObject(0);

            userId = parameters.getString("userId");
            postURL = parameters.getString("postURL");

            callbackContext.success(" User Id : " + userId + ", \n postURL : "+ postURL);
            return true;
        } else {
            return false;
        }
    }
}