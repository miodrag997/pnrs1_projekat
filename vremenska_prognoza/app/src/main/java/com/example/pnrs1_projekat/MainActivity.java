package com.example.pnrs1_projekat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private final String TAG = "main";
    Button buttonMainActivity;
    EditText myLocation;
    ListView listOfCities;
    ElementRowAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonMainActivity = findViewById(R.id.buttonMainActivity);
        myLocation = (EditText) findViewById(R.id.location);
        buttonMainActivity.setOnClickListener(this);

        adapter = new ElementRowAdapter(this);
        adapter.addElement(new ElementRow("Novi Sad"));
        adapter.addElement(new ElementRow("Belgrade"));
        adapter.addElement(new ElementRow("Bijeljina"));

        listOfCities = (ListView) findViewById(R.id.listOfCities);
        listOfCities.setAdapter(adapter);

        listOfCities.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
            {
                adapter.removeElement(position);
                return true;
            }
        });

    }

    @Override
        public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonMainActivity:
                if(myLocation.getText().toString().matches("")){
                    myLocation.setHint("Unesite grad!");
                }else{
                    adapter.addElement(new ElementRow(myLocation.getText().toString()));
                    myLocation.setText("");
                    myLocation.setHint("");
                }
                break;
        }
    }
}