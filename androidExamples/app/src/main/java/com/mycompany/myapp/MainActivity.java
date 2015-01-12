package com.mycompany.myapp;

import android.app.*;
import android.os.*;
import android.widget.*;
import android.content.pm.*;
import android.content.*;
import android.speech.*;
import java.util.List;
import android.view.View.*;
import android.view.*;

import java.util.ArrayList;


public class MainActivity extends Activity 
{
	private static final int REQUEST_CODE = 1234;
    private ListView wordsList;
	
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		
		Button btnSpeak = (Button) findViewById(R.id.btnSpeak);
		
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
					// TODO: Implement this method
					Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
					intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
									RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
					intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Voice recognition Demo...");
					startActivityForResult(intent, REQUEST_CODE);
				
				}
			});
		
			
    }
	
	/**
     * Handle the results from the voice recognition activity.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK)
        {
            // Populate the wordsList with the String values the recognition engine thought it heard
            ArrayList<String> matches = data.getStringArrayListExtra(
				RecognizerIntent.EXTRA_RESULTS);
            wordsList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
														  matches));
        }
        
		super.onActivityResult(requestCode, resultCode, data);
    }
	
}
