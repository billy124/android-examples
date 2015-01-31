package com.mycompany.myapp;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;

public class Scribbler extends Activity {
    DrawView drawView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scribbler);

        LinearLayout parent = (LinearLayout) findViewById(R.id.linearLayout);
        drawView = new DrawView(this);
        drawView.setBackgroundColor(Color.WHITE);
        parent.addView(drawView);
        drawView.requestFocus();

    }
}