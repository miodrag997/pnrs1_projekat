package com.example.pnrs1_projekat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DetailsActivity extends AppCompatActivity {

    TextView location;
    String s;

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
    }
}