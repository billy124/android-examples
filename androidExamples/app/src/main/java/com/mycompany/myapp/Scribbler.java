package com.mycompany.myapp;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.RelativeLayout;

public class Scribbler extends Activity {
    DrawView drawView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scribbler);

        RelativeLayout parent = (RelativeLayout) findViewById(R.id.linearLayout);
        drawView = new DrawView(this);
        parent.addView(drawView);

    }
}