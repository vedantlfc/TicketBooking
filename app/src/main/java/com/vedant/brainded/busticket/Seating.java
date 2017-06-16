package com.vedant.brainded.busticket;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

import static java.sql.Types.NULL;

/**
 * Created by Dell on 6/14/2017.
 */

public class Seating extends AppCompatActivity implements View.OnClickListener{


    int numOfSeats = 20;
    DatabaseHelper db;


    int[] seatList = new int[numOfSeats];

    //ArrayList<Integer> seatList = new ArrayList<>(numOfSeats);
    Button confirmBtn;
    Button cancelBtn;

    Button buttons[] = new Button[numOfSeats];

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seat_chart);

        confirmBtn = (Button) findViewById(R.id.confirmBtn);
        cancelBtn = (Button) findViewById(R.id.cancelbtn);

        db = new DatabaseHelper(this);

        Intent inte = new Intent();

        final String name = inte.getStringExtra("name");
        final String phone = inte.getStringExtra("phone");
        final String email = inte.getStringExtra("email");


        for (int i = 0; i < numOfSeats; i++) {
            String buttonID = "seat" + (i+1);
            int resID = getResources().getIdentifier(buttonID, "id",
                    "com.vedant.brainded.busticket");
            buttons[i] = (Button) findViewById(resID);
            if(db.CheckIsDataAlreadyInDBorNot("users", "seat", buttons[i].getTag().toString())){
                buttons[i].setEnabled(false);


            }
            buttons[i].setOnClickListener(this);
        }

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i = 0; i<numOfSeats; i++){
                    if(seatList[i] != NULL) {
                        db.addData(name, phone, email, seatList[i]);
                    }
                }
                goToMain();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMain();
            }
        });


    }

    private void goToMain(){
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
    }

    public void onBackPressed(){
        finish();
    }


    @Override
    public void onClick(View v) {
        int tag = Integer.parseInt(v.getTag().toString());
        if (!(seatList[tag-1] == tag) ) {
            seatList[tag-1] = tag;
        } else {
            seatList[tag-1] = NULL;
        }

        for(int i = 0; i<numOfSeats; i++){
            if(seatList[i] != NULL) {
                Log.i("WORD", Integer.toString(seatList[i]));
            }
            }

    }
}
