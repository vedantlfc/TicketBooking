package com.vedant.brainded.busticket;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

/**
 * Created by Dell on 6/8/2017.
 */

public class NewPassenger extends AppCompatActivity{
    EditText nameField;
    EditText emailField;
    EditText phoneField;

    Button nextBtn;
    Button cancelBtn;

   // Spinner seatsSpinner;
    Integer spinnerSelected;

    DatabaseHelper mDatabaseHelper;




    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_passenger);

        nameField = (EditText) findViewById(R.id.name);
        emailField = (EditText) findViewById(R.id.email);
        phoneField = (EditText) findViewById(R.id.phone);

        nextBtn = (Button) findViewById(R.id.next);
        cancelBtn = (Button) findViewById(R.id.cancel);

       // seatsSpinner = (Spinner) findViewById(R.id.seatsSpinner);
       // seatsSpinner.setOnItemSelectedListener(this);

        mDatabaseHelper = new DatabaseHelper(this);



        ArrayList<Integer> seatNum = new ArrayList<Integer>();
        for(int i = 1; i<=20; i++){

            if(!mDatabaseHelper.CheckIsDataAlreadyInDBorNot("users", "seat", Integer.toString(i))){
                seatNum.add(i);
            }

        }

        //ArrayAdapter<Integer> dataAdapter = new ArrayAdapter<Integer>(this, R.layout.support_simple_spinner_dropdown_item, seatNum);

        //dataAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        //seatsSpinner.setAdapter(dataAdapter);




        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendBackData();
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nameCus = nameField.getText().toString();
                String phoneCus = phoneField.getText().toString();
                String emailCus = emailField.getText().toString();
                //int seatCus = spinnerSelected;

                //mDatabaseHelper.addData(nameCus, phoneCus, emailCus, seatCus);

                Intent i = new Intent(getApplicationContext(), Seating.class);
                i.putExtra("name", nameCus);
                i.putExtra("phone", phoneCus);
                i.putExtra("email", emailCus);
                startActivity(i);

                //sendBackData();
            }
        });


    }



    private void sendBackData(){


        finish();

    }

    public void onBackPressed(){
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }


/*
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            spinnerSelected = Integer.parseInt(parent.getItemAtPosition(position).toString());
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    */
}
