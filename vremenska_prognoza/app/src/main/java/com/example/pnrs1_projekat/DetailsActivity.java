package com.example.pnrs1_projekat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.view.View.GONE;

public class DetailsActivity extends AppCompatActivity implements View.OnClickListener {

    TextView location;
    TextView day;
    String s;
    FrameLayout frameLayout;
    LinearLayout temperatureLayout, sunRiseSetLayout, windLayout;
    Button temperatureButton, sunRiseSetButton, windButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        location = findViewById(R.id.location);
        Intent myIntent = getIntent();
        Bundle b = myIntent.getExtras();
        s = b.getString("location");
        s = location.getText()+s;
        location.setText(s);


        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date d = new Date();
        String dayOfTheWeek = sdf.format(d);
        day = findViewById(R.id.day);
        day.setText(dayOfTheWeek);

        temperatureLayout = findViewById(R.id.temperatureLayout);
        sunRiseSetLayout = findViewById(R.id.sunRiseSetLayout);
        windLayout = findViewById(R.id.windLayout);
        frameLayout = findViewById(R.id.frameLayout);

        temperatureLayout.setVisibility(GONE);
        sunRiseSetLayout.setVisibility(GONE);
        windLayout.setVisibility(GONE);

        temperatureButton = findViewById(R.id.temperatureButton);
        sunRiseSetButton = findViewById(R.id.sunRiseSetButton);
        windButton = findViewById(R.id.windButton);

        temperatureButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.temperatureButton:
                temperatureLayout.setVisibility(v.VISIBLE);

                break;
            case R.id.sunRiseSetButton:
                break;
            case R.id.windButton:
                break;
        }
    }
}