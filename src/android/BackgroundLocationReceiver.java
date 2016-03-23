package com.nd.bgGeoLocation;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.widget.Toast;

import android.os.AsyncTask;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class BackgroundLocationReceiver extends BroadcastReceiver
{
    GPSTracker gps;
    BgGeoLocation bgGeoLoc;
    Location mCurrentLocation;
    ConnectivityManager mConnectivityManager;
    NetworkInfo mNetworkInfo;

    @Override
    public void onReceive(Context context, Intent intent)
    {
        gps = new GPSTracker(context);

        if(gps.canGetLocation()){
            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();

            Toast.makeText(context, "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
            // Toast.makeText(context, "UserId is - " + bgGeoLoc.userId, Toast.LENGTH_LONG).show();
            // Toast.makeText(context, "UserEmail is - " + bgGeoLoc.userEmail, Toast.LENGTH_LONG).show();
            // Toast.makeText(context, "UserLoginToken is - " + bgGeoLoc.userLoginToken, Toast.LENGTH_LONG).show();
            // Toast.makeText(context, "Post URL - " + bgGeoLoc.postURL, Toast.LENGTH_LONG).show();

            mConnectivityManager= (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
            mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if(mNetworkInfo != null && mNetworkInfo.isConnectedOrConnecting()){
                Toast.makeText(context, "Internet is on.", Toast.LENGTH_LONG).show(); 
            } else {
                Toast.makeText(context, "Internet is off.", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(context, "Location is off.", Toast.LENGTH_LONG).show();
        }
    }

    // public class Utils {
    //     ConnectivityManager mConnectivityManager;
    //     NetworkInfo mNetworkInfo;
    //     boolean isNetError;

    //     public boolean check_Internet()
    //     {
    //         mConnectivityManager= (ConnectivityManager)mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
    //         mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();

    //         if(mNetworkInfo != null && mNetworkInfo.isConnectedOrConnecting())
    //         {
    //             isNetError = false;
    //             return true;
    //         }
    //         else
    //         {
    //             isNetError = true;
    //             return false;
    //         }
    //     }
    // }

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