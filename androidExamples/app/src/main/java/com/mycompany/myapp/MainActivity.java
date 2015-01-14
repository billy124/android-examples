package com.mycompany.myapp;

import android.app.*;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.*;
import android.text.format.DateFormat;
import android.widget.*;
import android.content.pm.*;
import android.content.*;
import android.speech.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import android.view.View.*;
import android.view.*;

import java.util.ArrayList;
import java.util.Locale;
import java.io.*;
import android.util.*;


public class MainActivity extends Activity
{
	private static final int REQUEST_CODE = 1234;
    private ListView wordsList;

    EditText search;
    EditText locationText;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

		ActionBar ab = getActionBar();
		ab.hide();
		
        search = (EditText) findViewById(R.id.search);
        locationText = (EditText) findViewById(R.id.location);

		Button btnSpeak = (Button) findViewById(R.id.btnSpeak);
        Button location = (Button) findViewById(R.id.btnLocation);


		PackageManager pm = getPackageManager();
		List<ResolveInfo> activities = pm.queryIntentActivities(new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
		
		if(activities.size() == 0)
		{
			btnSpeak.setEnabled(false);
			btnSpeak.setText("Recognizer not present");
		}
		
		
		btnSpeak.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v)
				{
                    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                    // Start the activity, the intent will be populated with the speech text
                    startActivityForResult(intent, REQUEST_CODE);
				
				}
			});

        location.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v)
            {
                boolean is_gps_available = false;

                // get the users last best known location
                LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                android.location.Location lastKnownLocation = locationManager.getLastKnownLocation(locationManager.NETWORK_PROVIDER);

                if(locationManager == null){
                    locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
                }

                try{
                    is_gps_available = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                } catch(Exception ex){}
                try{
                    is_gps_available = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
                } catch(Exception ex){

                }

                if(is_gps_available == true) {
                    //LocationListener locationListener = new MyLocationListener();
                    //locationManager.requestLocationUpdates(locationManager.PASSIVE_PROVIDER, 5000, 10, locationListener);

                    Geocoder userLocation = new Geocoder(getApplicationContext(), Locale.getDefault());

                    List<Address> addresses = null;
                    try {
                        addresses = userLocation.getFromLocation(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude(), 1);
                    } catch (IOException e) {
                        Log.e("MainActivity", "IO Error");

                    }

                    if (addresses.size() > 0) {
                        Address address = addresses.get(0);
                        String addressText = String.format(
                                "%s, %s, %s, %s",
                                // If there's a street address, add it
                                address.getMaxAddressLineIndex() > 0 ?
                                        address.getAddressLine(0) : "",
                                // Locality is usually a city
                                address.getLocality(),
                                // The country of the address
                                address.getCountryName(),
                                address.getPostalCode());

                        locationText.setText(addressText);

                    }


                    try {
                        Double lat = (Double) lastKnownLocation.getLatitude();
                        Double lon = (Double) lastKnownLocation.getLongitude();

                        //locationText.setText("Lat:"+lat.toString()+" lon:"+lon.toString());

                    } catch (NullPointerException e) {
                        locationText.setText(e.toString());

                    }
                } else {
                    Intent gpsOptionsIntent = new Intent(
                            android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(gpsOptionsIntent);
                }

            }
        });
		
			
    }




    // This callback is invoked when the Speech Recognizer returns.
    // This is where you process the intent and extract the speech text from the intent.
    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            List<String> results = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);
            String spokenText = results.get(0);

            search.setText(spokenText);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
	
}

