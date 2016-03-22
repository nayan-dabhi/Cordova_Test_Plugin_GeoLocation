package com.nd.bgGeoLocation;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.widget.Toast;

public class BackgroundLocationReceiver extends BroadcastReceiver
{
    Location mCurrentLocation;
    BgGeoLocation bgGeoLoc;
    GPSTracker gps;

    @Override
    public void onReceive(Context context, Intent intent)
    {
        gps = new GPSTracker(context);

        if(gps.canGetLocation()){
            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();

            Toast.makeText(context, "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
            Toast.makeText(context, "UserId is - " + bgGeoLoc.userId, Toast.LENGTH_LONG).show();
            Toast.makeText(context, "UserEmail is - " + bgGeoLoc.userEmail, Toast.LENGTH_LONG).show();
            Toast.makeText(context, "UserLoginToken is - " + bgGeoLoc.userLoginToken, Toast.LENGTH_LONG).show();
            Toast.makeText(context, "Post URL - " + bgGeoLoc.postURL, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "Location if off.", Toast.LENGTH_LONG).show();
        }
    }

    public class updateLocation extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg1) {
            return null;
        }

        @Override
        protected void onPostExecute(Void arg1) {
            super.onPostExecute(arg1);
        }
    }
}