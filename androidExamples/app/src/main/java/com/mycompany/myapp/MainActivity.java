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

                LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

                android.location.Location location = locationManager.getLastKnownLocation(locationManager.PASSIVE_PROVIDER);

				Geocoder userLocation = new Geocoder(getApplicationContext(), Locale.getDefault());
				
				List<Address> addresses = null;
				try{
					addresses = userLocation.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
				} catch(IOException e) {
					Log.e("MainActivity", "IO Error");
					
				}
				
				if(addresses.size() > 0){
					Address address = addresses.get(0);
					String addressText = (String) address.getAddressLine(0);
					

					locationText.setText(addressText);
				
				}
                try {
                    Double lat = (Double) location.getLatitude();
                    Double lon = (Double) location.getLongitude();

                    //locationText.setText("Lat:"+lat.toString()+" lon:"+lon.toString());

                }
                catch (NullPointerException e){
                    locationText.setText(e.toString());

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

