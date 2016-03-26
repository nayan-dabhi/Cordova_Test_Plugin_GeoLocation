package com.nd.bgGeoLocation;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class GPSTracker extends Service implements LocationListener {

	private final Context mContext;
	protected LocationManager locationManager = null;
	private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters
	public Integer MIN_TIME_BW_UPDATES;

	boolean isGPSEnabled = false;
	boolean isNetworkEnabled = false;
	boolean canGetLocation = false;

	Location location;
	double latitude;
	double longitude;

	public GPSTracker(Context context) {
		this.mContext = context;
		SharedPreferences mSharedPreferences = context.getSharedPreferences("locationPref", 0);
		MIN_TIME_BW_UPDATES = mSharedPreferences.getInt("timerInterval", 60000);
		getLocation();
	}

	public Location getLocation() {
		try {
			if(locationManager == null) {
				locationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);
			}

			try {
				isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
				// Toast.makeText(mContext, "GPS Enabled : " + String.valueOf(isGPSEnabled), Toast.LENGTH_LONG).show();
			} catch (Exception e){
				e.printStackTrace();
			}

			try {
				isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
				// Toast.makeText(mContext, "Network Enabled : " + String.valueOf(isNetworkEnabled), Toast.LENGTH_LONG).show();
			} catch (Exception e){
				e.printStackTrace();
			}

			if (!isGPSEnabled && !isNetworkEnabled) {
				this.canGetLocation = false;
			} else {
				if (isGPSEnabled) {
					if (location == null) {
						locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

						if (locationManager != null) {
							location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
							if (location != null) {
								latitude = location.getLatitude();
								longitude = location.getLongitude();
								this.canGetLocation = true;
								// Toast.makeText(mContext,"get location from gps.", Toast.LENGTH_LONG).show();
							} else {
								this.canGetLocation = false;
								location = null;
								// Toast.makeText(mContext,"can't get location from gps.", Toast.LENGTH_LONG).show();
							}
						} else {
							location = null;
						}
					}
				}

				if(location == null){
					// Toast.makeText(mContext,"location null & get network location.", Toast.LENGTH_LONG).show();

					if (isNetworkEnabled) {
						locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

						if (locationManager != null) {
							location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
							if (location != null) {
								latitude = location.getLatitude();
								longitude = location.getLongitude();
								this.canGetLocation = true;
								// Toast.makeText(mContext,"get location from network.", Toast.LENGTH_LONG).show();
							} else {
								this.canGetLocation = false;
								location = null;
								// Toast.makeText(mContext,"can't get location from network.", Toast.LENGTH_LONG).show();
							}
						} else {
							location = null;
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return location;
	}

	public void stopUsingGPS(){
		if(locationManager != null){
			locationManager.removeUpdates(GPSTracker.this);
		}		
	}

	public double getLatitude(){
		if(location != null){
			latitude = location.getLatitude();
		}
		return latitude;
	}

	public double getLongitude(){
		if(location != null){
			longitude = location.getLongitude();
		}
		return longitude;
	}

	public boolean canGetLocation() {
		return this.canGetLocation;
	}

	@Override
	public void onLocationChanged(Location location) {
	}

	@Override
	public void onProviderDisabled(String provider) {
	}

	@Override
	public void onProviderEnabled(String provider) {
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}
}