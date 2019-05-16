package com.example.pnrs1_projekat;

import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class StatisticsActivity extends AppCompatActivity {

    //ElementDbHelper DATABASE 'weather.db' AS stf;

    TextView city,monday, mondayTemperature, mondayPreassure, mondayHumidity;
    TextView tuesday, tuesdayTemperature, tuesdayPreassure, tuesdayHumidity;
    TextView wednesday, wednesdayTemperature, wednesdayPreassure, wednesdayHumidity;
    TextView thursday, thursdayTemperature, thursdayPreassure, thursdayHumidity;
    TextView friday, fridayTemperature, fridayPreassure, fridayHumidity;
    TextView saturday, saturdayTemperature, saturdayPreassure, saturdayHumidity;
    TextView sunday, sundayTemperature, sundayPreassure, sundayHumidity;

    String sCity, sMonday, sMondayTemperature, sMondayPreassure, sMondayHumidity;
    String sTuesday, sTuesdayTemperature, sTuesdayPreassure, sTuesdayHumidity;
    String sWednesday, sWednesdayTemperature, sWednesdayPreassure, sWednesdayHumidity;
    String sThursday, sThursdayTemperature, sThursdayPreassure, sThursdayHumidity;
    String sFriday, sFridayTemperature, sFridayPreassure, sFridayHumidity;
    String sSaturday, sSaturdayTemperature, sSaturdayPreassure, sSaturdayHumidity;
    String sSunday, sSundayTemperature, sSundayPreassure, sSundayHumidity;

    //private ElementDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        city = findViewById(R.id.city);
        monday = findViewById(R.id.monday);
        tuesday = findViewById(R.id.tuesday);
        wednesday = findViewById(R.id.wednesday);
        thursday = findViewById(R.id.thursday);
        friday = findViewById(R.id.friday);
        saturday = findViewById(R.id.saturday);
        sunday = findViewById(R.id.sunday);

        mondayTemperature = findViewById(R.id.mondayTemperature);
        tuesdayTemperature = findViewById(R.id.tuesdayTemperature);
        wednesdayTemperature = findViewById(R.id.wednesdayTemperature);
        thursdayTemperature = findViewById(R.id.thursdayTemperature);
        fridayTemperature = findViewById(R.id.fridayTemperature);
        saturdayTemperature = findViewById(R.id.saturdayTemperature);
        sundayTemperature = findViewById(R.id.sundayTemperature);

        mondayPreassure = findViewById(R.id.mondayPreassure);
        tuesdayPreassure = findViewById(R.id.tuesdayPreassure);
        wednesdayPreassure = findViewById(R.id.wednesdayPreassure);
        thursdayPreassure = findViewById(R.id.thursdayPreassure);
        fridayPreassure = findViewById(R.id.fridayPreassure);
        saturdayPreassure = findViewById(R.id.saturdayPreassure);
        sundayPreassure = findViewById(R.id.sundayPreassure);

        mondayHumidity = findViewById(R.id.mondayHumidity);
        tuesdayHumidity = findViewById(R.id.tuesdayHumidity);
        wednesdayHumidity = findViewById(R.id.wednesdayHumidity);
        thursdayHumidity = findViewById(R.id.thursdayHumidity);
        fridayHumidity = findViewById(R.id.fridayHumidity);
        saturdayHumidity = findViewById(R.id.saturdayHumidity);
        sundayHumidity = findViewById(R.id.sundayHumidity);

        Intent myIntent = getIntent();
        Bundle b = new Bundle();
        b = myIntent.getExtras();
        String[] stringList = new String[21];
        stringList = b.getStringArray("city");
        mondayTemperature.setText(stringList[0]);
        Log.d("test", "poslije for petlje");


    }
}
