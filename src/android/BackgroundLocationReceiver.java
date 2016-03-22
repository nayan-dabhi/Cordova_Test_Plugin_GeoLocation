package com.nd.bgGeoLocation;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.widget.Toast;

public class BackgroundLocationReceiver extends BroadcastReceiver
{
    Location mCurrentLocation;
    GPSTracker gps;

    @Override
    public void onReceive(Context context, Intent intent)
    {
        Toast.makeText(context, "Your Location is ", Toast.LENGTH_LONG).show();

        gps = new GPSTracker(context);

        // check if GPS enabled
        if(gps.canGetLocation()){
            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();

            Toast.makeText(context, "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
        } else {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            // gps.showSettingsAlert();
        }
    }
}