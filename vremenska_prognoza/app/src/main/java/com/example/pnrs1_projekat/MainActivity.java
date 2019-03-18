package com.example.pnrs1_projekat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity{

    Button buttonMainActivity;
    EditText myLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonMainActivity = findViewById(R.id.buttonMainActivity);
        myLocation = (EditText) findViewById(R.id.location);

        buttonMainActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, DetailsActivity.class);
                Bundle b = new Bundle();
                b.putString("location", myLocation.getText().toString());
                myIntent.putExtras(b);
                startActivity(myIntent);

            }
        });
    }


}