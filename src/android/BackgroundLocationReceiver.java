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
        gps = new GPSTracker(context);

        if(gps.canGetLocation()){
            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();

            Toast.makeText(context, "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "Location if off.", Toast.LENGTH_LONG).show();
        }
    }
}