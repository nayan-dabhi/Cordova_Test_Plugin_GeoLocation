package com.nd.bgGeoLocation;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.util.Log;
import android.widget.Toast;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

import android.os.AsyncTask;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.net.URL;
import java.net.HttpURLConnection;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.util.EntityUtils;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;



public class BackgroundLocationReceiver extends BroadcastReceiver
{
    GPSTracker gps;
    ConnectivityManager mConnectivityManager;
    NetworkInfo mNetworkInfo;

    public Context mContext;
    public List<NameValuePair> nameValuePairs;
    public double latitude, longitude;
    public String postResponse, userId, userEmail, userLoginToken, postURL;

    @Override
    public void onReceive(Context context, Intent intent)
    {
        mContext = context;
        gps = new GPSTracker(context);

        SharedPreferences mSharedPreferences = context.getSharedPreferences("locationPref", 0);
        userId = mSharedPreferences.getString("userId","");
        userEmail = mSharedPreferences.getString("userEmail","");
        userLoginToken = mSharedPreferences.getString("userLoginToken","");
        postURL = mSharedPreferences.getString("postURL","");

        if(gps.canGetLocation()){
            latitude = gps.getLatitude();
            longitude = gps.getLongitude();
            // Toast.makeText(context, "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();

            mConnectivityManager= (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
            mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();

            if(mNetworkInfo != null && mNetworkInfo.isConnectedOrConnecting()){
                setUpdateLocation(context);
            } else {
                Toast.makeText(context, "No internet connection available.", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(context, "Unable to get user position.", Toast.LENGTH_LONG).show();
        }
    }

    public void setUpdateLocation(Context context){
        nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("user_id", userId));
        nameValuePairs.add(new BasicNameValuePair("latitude", Double.toString(latitude)));
        nameValuePairs.add(new BasicNameValuePair("longitude", Double.toString(longitude)));
        new updateLocation().execute();
    }

    public class ServiceHandler {
        public ServiceHandler() {
        }


        public String makeServiceCall(){
            try {
                int timeoutConnection = 60000;
                HttpResponse response = null;
                Log.d("URL",postURL);
                URL urlToRequest = new URL(postURL);

                HttpURLConnection urlConnection = (HttpURLConnection) urlToRequest.openConnection();
                urlConnection.setDoOutput(true);
                urlConnection.setRequestMethod("POST");
                //urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

                HttpPost httppost = new HttpPost(postURL);
                //httppost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
                httppost.addHeader("X-Email", userEmail);
                httppost.addHeader("X-Auth-Token", userLoginToken);

                HttpParams httpParameters = new BasicHttpParams();
                HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
                HttpClient httpclient = new DefaultHttpClient(httpParameters);

                MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
                if (nameValuePairs != null) {
                    for (int index = 0; index < nameValuePairs.size(); index++) {
                        String paramName = nameValuePairs.get(index).getName();
                        String paramValue = nameValuePairs.get(index).getValue();

                        System.out.println("paramName " + paramName);
                        System.out.println("paramValue " + paramValue);

                        entity.addPart(paramName, new StringBody(paramValue));
                    }
                    //httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
                    httppost.setEntity(entity);
                }

                // Execute HTTP Post Request
                response = httpclient.execute(httppost);
                if(response.getStatusLine().getStatusCode() == HttpURLConnection.HTTP_OK){
                    postResponse = EntityUtils.toString(response.getEntity());
                } else {
                    postResponse = "{'status':'success', 'insert': false, 'message': 'Data Loading Failed.'}";
                }
            } catch (Exception e){
                postResponse = "{'status':'success', 'insert': false, 'message': 'Data Loading Failed.'}";
            }

            return postResponse;
        }
    }

    public class updateLocation extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg1) {
            ServiceHandler sh = new ServiceHandler();
            sh.makeServiceCall();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            Log.d("post execute res : ", postResponse);

            if(postResponse != null){
                try {
                    JSONObject jsonObj = new JSONObject(postResponse);
                    String resStatus = jsonObj.getString("status");
                    Boolean update = jsonObj.getBoolean("insert");

                    if(resStatus.equalsIgnoreCase("success") && update){
                        // Toast.makeText(mContext, postResponse, Toast.LENGTH_LONG).show();
                        Toast.makeText(mContext, "User location is updated.", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(mContext, jsonObj.getString("message"), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(mContext, "User location does not save.", Toast.LENGTH_LONG).show();
            }
        }
    }
}