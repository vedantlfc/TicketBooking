package com.vedant.brainded.busticket;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity implements Serializable{

    Button viewChartBtn;
    Button createNewBtn;
    Button flushBtn;

    DatabaseHelper mDatabaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewChartBtn = (Button) findViewById(R.id.viewChart);
        createNewBtn = (Button) findViewById(R.id.createNew);
        flushBtn = (Button) findViewById(R.id.flushBtn);

        mDatabaseHelper = new DatabaseHelper(this);

        viewChartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Pressed", Toast.LENGTH_LONG).show();
            }
        });

        createNewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), NewPassenger.class);

                startActivity(i);
            }
        });

        flushBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabaseHelper.deleteAll("users");
            }
        });

    }



    public void onBackPressed(){
        finish();
    }

    public void onDestroy(){
        super.onDestroy();

    }



}
