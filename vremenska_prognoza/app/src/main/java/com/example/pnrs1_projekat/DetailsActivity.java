package com.example.pnrs1_projekat;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import static android.view.View.GONE;

public class DetailsActivity extends AppCompatActivity implements View.OnClickListener {

    TextView location;
    TextView dateTextView;
    TextView textViewLastUpdate;
    Button buttonUpdate, buttonStatistics;
    String s;
    FrameLayout frameLayout;
    LinearLayout temperatureLayout, sunRiseSetLayout, windLayout;
    Button temperatureButton, sunRiseSetButton, windButton;
    Spinner unitForDegrees;
    TextView temperature, windSpeed, pressure, humidity, sunRise, sunSet, windDir;

    ////////HTTP//////
    private HttpHelper httpHelper;
    public static String GET_CITY = "https://api.openweathermap.org/data/2.5/weather?q=";
    public static String API_KEY = "&APPID=6e2c4ea501a981241b6224f707c04e49";
    public static String city = null;
    String sTemperature;
    String sWind;
    String sPressure;
    String sHumidity;
    String sSunRise;
    String sSunSet;
    int sWindDir;
    String direction = null;
    String today, lastUpdateDay;
    String time, time0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        /* LOKACIJA */
        location = findViewById(R.id.location);
        Intent myIntent = getIntent();
        Bundle b = myIntent.getExtras();
        s = b.getString("location");
        city = s;
        s = location.getText() + " " + s;
        location.setText(s);

        /* DATUM */
        Date date = Calendar.getInstance().getTime();
        today = new SimpleDateFormat("dd.MM.yyyy.").format(date);
        dateTextView = findViewById(R.id.dateTextView);

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
        buttonUpdate = findViewById(R.id.buttonUpdate);
        buttonStatistics = findViewById(R.id.buttonStatistics);

        temperatureButton.setOnClickListener(this);
        sunRiseSetButton.setOnClickListener(this);
        windButton.setOnClickListener(this);
        buttonUpdate.setOnClickListener(this);
        buttonStatistics.setOnClickListener(this);

        temperature = findViewById(R.id.temperature);
        windSpeed = findViewById(R.id.windSpeedTextView);
        pressure = findViewById(R.id.pressureTextView);
        humidity = findViewById(R.id.humidityTextView);
        sunRise = findViewById(R.id.sunRiseTextView);
        sunSet = findViewById(R.id.sunSetTextView);
        windDir = findViewById(R.id.windDirectionTextView);
        textViewLastUpdate = findViewById(R.id.textViewLastUpdate);


        ContentResolver resolver = getContentResolver();
        Cursor cursor = resolver.query(Uri.parse(ElementProvider.CONTENT_URI + "/" + city), null, ElementDbHelper.COLUMN_CITY+"=?", null,
                null);

        if (cursor.getCount() <= 0) {
            dateTextView.setText(dateTextView.getText().toString() + " " + today);
            textViewLastUpdate.setVisibility(View.INVISIBLE);
            buttonUpdate.setVisibility(View.INVISIBLE);

            getDataFromInternet();

        }else {
            cursor.moveToLast();
            lastUpdateDay = cursor.getString(cursor.getColumnIndex(ElementDbHelper.COLUMN_DATE));
            sTemperature = cursor.getString(cursor.getColumnIndex(ElementDbHelper.COLUMN_TEMPERATURE));
            sHumidity = cursor.getString(cursor.getColumnIndex(ElementDbHelper.COLUMN_HUMIDITY));
            sPressure = cursor.getString(cursor.getColumnIndex(ElementDbHelper.COLUMN_PREASSURE));
            time = cursor.getString(cursor.getColumnIndex(ElementDbHelper.COLUMN_SUNRISE));
            time0 = cursor.getString(cursor.getColumnIndex(ElementDbHelper.COLUMN_SUNSET));
            sWind = cursor.getString(cursor.getColumnIndex(ElementDbHelper.COLUMN_WIND_SPEED));
            direction = cursor.getString(cursor.getColumnIndex(ElementDbHelper.COLUMN_WIND_DIRECTION));

            dateTextView.setText(dateTextView.getText().toString() + " " + lastUpdateDay);
            temperature.setText(sTemperature + " °C");
            humidity.setText(/*R.string.airHumidityActivityDetails*/"Vlaznost vazduha: " + sHumidity + " %");
            pressure.setText("Pritisak: " + sPressure + " mb");
            sunRise.setText("Izlazak sunca: " + time + " h");
            sunSet.setText("Izlazak sunca: " + time0 + " h");
            windSpeed.setText("Brzina: " + sWind + " m/s");
            windDir.setText("Pravac: " + direction);
            cursor.getString(cursor.getColumnIndex(ElementDbHelper.COLUMN_CITY));

            if (today.equals(lastUpdateDay)) {
                textViewLastUpdate.setVisibility(View.INVISIBLE);
                buttonUpdate.setVisibility(View.INVISIBLE);
            }
        }
        unitForDegrees = findViewById(R.id.spinnerJedinica);
        String[] items = new String[]{"°C", "°F"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        unitForDegrees.setAdapter(adapter);

        unitForDegrees.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            private String selecteditem, previousItem = null;
            private double degrees = 0;
            @Override
            public void onItemSelected(AdapterView adapter, View v, int i, long lng) {

                selecteditem =  adapter.getItemAtPosition(i).toString();
                if(selecteditem == "°C" && previousItem == "°F"){
                    degrees = Double.parseDouble(temperature.getText().toString().substring(0, temperature.getText().toString().length()-3));
                    degrees = (degrees-32)*5/9;
                    degrees = round(degrees, 2);
                    temperature.setText(String.valueOf(degrees) + " °C");
                    previousItem = "°C";
                }
                if(selecteditem == "°F"){
                    degrees = Double.parseDouble(temperature.getText().toString().substring(0, temperature.getText().toString().length()-3));
                    degrees = degrees*9/5+32;
                    degrees = round(degrees, 2);
                    temperature.setText(String.valueOf(degrees) + " °F");
                    previousItem = "°F";
                }
            }

            private double round(double value, int places) {
                if (places < 0) throw new IllegalArgumentException();

                long factor = (long) Math.pow(10, places);
                value = value * factor;
                long tmp = Math.round(value);
                return (double) tmp / factor;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.temperatureButton:
                temperatureLayout.setVisibility(v.VISIBLE);
                sunRiseSetLayout.setVisibility(v.INVISIBLE);
                windLayout.setVisibility(v.INVISIBLE);
                break;
            case R.id.sunRiseSetButton:
                temperatureLayout.setVisibility(v.INVISIBLE);
                sunRiseSetLayout.setVisibility(v.VISIBLE);
                windLayout.setVisibility(v.INVISIBLE);
                break;
            case R.id.windButton:
                temperatureLayout.setVisibility(v.INVISIBLE);
                sunRiseSetLayout.setVisibility(v.INVISIBLE);
                windLayout.setVisibility(v.VISIBLE);
                break;
            case R.id.buttonUpdate:
                getDataFromInternet();
                textViewLastUpdate.setVisibility(v.INVISIBLE);
                buttonUpdate.setVisibility(v.INVISIBLE);
                break;
            case R.id.buttonStatistics:
                //TODO//
                break;
            default:
        }
    }

    private void getDataFromInternet(){
        httpHelper = new HttpHelper();
        new Thread(new Runnable() {
            public void run() {
                try {
                    final String FINAL_URL = GET_CITY + city + "&units=metric" + API_KEY;
                    JSONObject jsonobject = httpHelper.getJSONObjectFromURL(FINAL_URL);
                    JSONObject main = (JSONObject) jsonobject.get("main");
                    JSONObject sys = (JSONObject) jsonobject.get("sys");
                    JSONObject wind = (JSONObject) jsonobject.get("wind");

                    sTemperature = main.getString("temp");
                    sHumidity = main.getString("humidity");
                    sPressure = main.getString("pressure");
                    sSunRise = sys.getString("sunrise");
                    sSunSet = sys.getString("sunset");

                    temperature.setText(sTemperature + " °C");
                    humidity.setText(/*R.string.airHumidityActivityDetails*/"Vlaznost vazduha: " + sHumidity + " %");
                    pressure.setText("Pritisak: " + sPressure + " mb");

                    TimeZone tz = TimeZone.getTimeZone("GMT+2");
                    SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
                    df.setTimeZone(tz);

                    Date sunRiseDate = new java.util.Date(Integer.parseInt(sSunRise)*1000L);
                    time = df.format(sunRiseDate);
                    sunRise.setText("Izlazak sunca: " + time + " h");

                    Date sunSetDate = new java.util.Date(Integer.parseInt(sSunSet)*1000L);
                    time0 = df.format(sunSetDate);
                    sunSet.setText("Izlazak sunca: " + time0 + " h");

                    try{
                        sWind = wind.getString("speed");
                        windSpeed.setText("Brzina: "+ sWind + " m/s");
                    }catch(JSONException e) {
                        windSpeed.setText("Brzina: 0 m/s");
                        sWind = "0";
                    }
                    try{
                        sWindDir = Integer.parseInt(wind.getString("deg"));


                        if(sWindDir > 337 || sWindDir <= 22) direction = "Sever";
                        if(sWindDir > 22 || sWindDir <= 67) direction = "Sever-Istok";
                        if(sWindDir > 67 || sWindDir <= 112) direction = "Istok";
                        if(sWindDir > 112 || sWindDir <= 157) direction = "Jug-Istok";
                        if(sWindDir > 157 || sWindDir <= 202) direction = "Jug";
                        if(sWindDir > 202 || sWindDir <= 247) direction = "Jug-Zapad";
                        if(sWindDir > 247 || sWindDir <= 292) direction = "Zapad";
                        if(sWindDir > 292 || sWindDir <= 337) direction = "Sever-Zapad";

                        windDir.setText("Pravac: " + direction);
                    }catch(JSONException e) {
                        windDir.setText("Pravac: Nema informacija");
                        direction = "Nema informacija";
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                ContentValues values = new ContentValues();
                values.put(ElementDbHelper.COLUMN_DATE, today);
                values.put(ElementDbHelper.COLUMN_CITY, city);
                values.put(ElementDbHelper.COLUMN_TEMPERATURE, sTemperature);
                values.put(ElementDbHelper.COLUMN_PREASSURE, sPressure);
                values.put(ElementDbHelper.COLUMN_HUMIDITY, sHumidity);
                values.put(ElementDbHelper.COLUMN_SUNRISE, time);
                values.put(ElementDbHelper.COLUMN_SUNSET, time0);
                values.put(ElementDbHelper.COLUMN_WIND_SPEED, sWind);
                values.put(ElementDbHelper.COLUMN_WIND_DIRECTION, direction);

                ContentResolver resolver = getContentResolver();
                resolver.insert(ElementProvider.CONTENT_URI, values);

            }
        }).start();
    }
}