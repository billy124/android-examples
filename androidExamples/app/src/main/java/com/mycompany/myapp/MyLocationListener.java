package com.mycompany.myapp;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.widget.EditText;

/**
 * Created by billy on 13/01/15.
 */
public final class MyLocationListener implements LocationListener {

    public static final int OUT_OF_SERVICE = 0;
    public static final int TEMPORARILY_UNAVAILABLE = 1;
    public static final int AVAILABLE = 2;

    EditText locationText;

    @Override
    public void onLocationChanged(Location locFromGps) {
        // called when the listener is notified with a location update from the GPS
        Double lat = (Double) locFromGps.getLatitude();
        Double lon = (Double) locFromGps.getLongitude();

        locationText.setText("Lat:"+lat.toString()+" lon:"+lon.toString());
    }

    @Override
    public void onProviderDisabled(String provider) {
        // called when the GPS provider is turned off (user turning off the GPS on the phone)
    }

    @Override
    public void onProviderEnabled(String provider) {
        // called when the GPS provider is turned on (user turning on the GPS on the phone)
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // called when the status of the GPS provider changes
    }
}