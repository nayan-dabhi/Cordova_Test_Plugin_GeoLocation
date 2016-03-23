package com.nd.bgGeoLocation;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.widget.Toast;

import android.os.AsyncTask;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public class BackgroundLocationReceiver extends BroadcastReceiver
{
    GPSTracker gps;
    BgGeoLocation bgGeoLoc;
    Location mCurrentLocation;
    ConnectivityManager mConnectivityManager;
    NetworkInfo mNetworkInfo;
    List<NameValuePair> nameValuePairs;

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
                // Toast.makeText(context, "Internet is on.", Toast.LENGTH_LONG).show();

                // nameValuePairs = new ArrayList<NameValuePair>();
                // nameValuePairs.add(new BasicNameValuePair("user_id", bgGeoLoc.userId));
                // nameValuePairs.add(new BasicNameValuePair("latitude", latitude));
                // nameValuePairs.add(new BasicNameValuePair("longitude", longitude));

                // Toast.makeText(context, nameValuePairs.toString(), Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(context, "Internet is off.", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(context, "Location is off.", Toast.LENGTH_LONG).show();
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