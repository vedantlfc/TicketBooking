package com.vedant.brainded.busticket;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity implements Serializable{

    Button loginBtn;
    Button newTicketBtn;
    Button flushBtn;
    Intent inte;
    Boolean loggedIn;
    String userId;

    private FirebaseAuth auth;


    DatabaseHelper mDatabaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginBtn = (Button) findViewById(R.id.login);
        newTicketBtn = (Button) findViewById(R.id.createNew);
        flushBtn = (Button) findViewById(R.id.flushBtn);
        loggedIn = false;

        auth = FirebaseAuth.getInstance();


        mDatabaseHelper = new DatabaseHelper(this);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);

                startActivityForResult(i, 2);
            }
        });

        newTicketBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Loginezz", String.valueOf(loggedIn));
                if(auth.getCurrentUser() != null){
                    Intent i = new Intent(getApplicationContext(), Seating.class);

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
                userId = data.getExtras().getString("UserId");
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
