package com.vedant.brainded.busticket;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import java.io.Serializable;

public class MainActivity extends AppCompatActivity implements Serializable{

    Button loginBtn;
    Button createNewBtn;
    Button flushBtn;
    Intent inte;
    Boolean loggedIn;


    DatabaseHelper mDatabaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginBtn = (Button) findViewById(R.id.login);
        createNewBtn = (Button) findViewById(R.id.createNew);
        flushBtn = (Button) findViewById(R.id.flushBtn);
        loggedIn = false;




        mDatabaseHelper = new DatabaseHelper(this);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);

                startActivityForResult(i, 2);
            }
        });

        createNewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Loginezz", String.valueOf(loggedIn));
                if(loggedIn){
                    Intent i = new Intent(getApplicationContext(), NewPassenger.class);

                    startActivity(i);
                } else {
                    Toast.makeText(getApplicationContext(), "Login First!", Toast.LENGTH_LONG).show();
                }


            }
        });

        flushBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabaseHelper.deleteAll("users");
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 2) {
            if(resultCode == Activity.RESULT_OK){
                loggedIn = data.getExtras().getBoolean("LoggedInStatus");
                Log.i("Loginez", String.valueOf(loggedIn));
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(), "No Result!", Toast.LENGTH_LONG).show();
                //Write your code if there's no result
            }
        }
    }

    public void onBackPressed(){
        
        finish();
    }

    public void onDestroy(){
        super.onDestroy();

    }



}
