package com.vedant.brainded.busticket;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


import static com.google.android.gms.R.id.progressBar;
import static java.sql.Types.NULL;

public class Seating extends AppCompatActivity implements View.OnClickListener{
    //**
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private DatabaseReference mTicketDB;
    private String userEmail;
    private String userId;
    private String userName;
    private FirebaseAuth auth;
    //**

    private Boolean seatTaken;
    private ProgressBar pBar;

    private LinearLayout loadingBg;

    int numOfSeats = 21;
    DatabaseHelper db;


    final int[] seatList = new int[numOfSeats];

    //ArrayList<Integer> seatList = new ArrayList<>(numOfSeats);
    Button confirmBtn;
    Button cancelBtn;

    Button buttons[] = new Button[numOfSeats];

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seat_chart);

        confirmBtn = (Button) findViewById(R.id.confirmBtn);
        cancelBtn = (Button) findViewById(R.id.cancelbtn);

        pBar = (ProgressBar) findViewById(R.id.pBar);

        loadingBg = (LinearLayout) findViewById(R.id.loading_bg);



        db = new DatabaseHelper(this);

        Intent inte = new Intent();

        final String name = inte.getStringExtra("name");
        final String phone = inte.getStringExtra("phone");
        final String email = inte.getStringExtra("email");

        auth = FirebaseAuth.getInstance();
        //*
        mFirebaseInstance = FirebaseDatabase.getInstance();

        mFirebaseDatabase = mFirebaseInstance.getReference("users");

        mTicketDB = mFirebaseInstance.getReference("Tickets");

        mFirebaseInstance.getReference("app_title").setValue("Users and Tickets");
        //*


        for (int i = 0; i < numOfSeats; i++) {
            String buttonID = "seat" + (i+1);
            int resID = getResources().getIdentifier(buttonID, "id",
                    "com.vedant.brainded.busticket");
            buttons[i] = (Button) findViewById(resID);


            String seatNum = buttons[i].getTag().toString();

            if(i==numOfSeats-1){

                if(pBar.getVisibility()!=View.VISIBLE){
                    pBar.setVisibility(View.VISIBLE);
                }

                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                loadingBg.setVisibility(View.VISIBLE);


            }

            checkIfSeatSelected(seatNum, buttons[i]);


           // if(seatFilled){
            //    buttons[i].setEnabled(false);
           // }




            //if(db.CheckIsDataAlreadyInDBorNot("users", "seat", seatNum)){
            //    buttons[i].setEnabled(false);


            //}
            buttons[i].setOnClickListener(this);
        }



        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(pBar.getVisibility() == View.VISIBLE){
                    toaster("Loading!");

                } else {
                    for (int i = 0; i < numOfSeats; i++) {
                        if (seatList[i] != NULL) {

                            final String seatlistI = Integer.toString(seatList[i]);

                            userEmail = auth.getCurrentUser().getEmail().toString();

                            mTicketDB.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if(!dataSnapshot.child(seatlistI).exists()){

                                        Query q = mFirebaseDatabase.orderByChild("email").equalTo(userEmail);
                                        q.addChildEventListener(new ChildEventListener() {
                                            @Override
                                            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                                                Customer cus = dataSnapshot.getValue(Customer.class);
                                                userName = cus.getName().toString();
                                                userId = dataSnapshot.getKey();
                                                Ticket ticket = new Ticket(userName, seatlistI);


                                                mTicketDB.child(seatlistI).setValue(ticket);
                                                toaster("Ticket Reserved!");



                                            }

                                            @Override
                                            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                                            }

                                            @Override
                                            public void onChildRemoved(DataSnapshot dataSnapshot) {

                                            }

                                            @Override
                                            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {

                                            }
                                        });



                                    } else {
                                        toaster("Sorry");

                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });



                            db.addData(name, phone, email, seatList[i]);
                        }
                    }
                    goToMain();
                }

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

    private void toaster(String toastMsg){
        Toast.makeText(getApplicationContext(), toastMsg, Toast.LENGTH_SHORT).show();
    }


    //checks selected seats and grays out the selected ones
    private void checkIfSeatSelected(final String seatNum, final Button btn){
        Query q = mTicketDB.orderByChild("seatNum").equalTo(seatNum);



        q.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.i("Flexzo", "added");

                if(dataSnapshot.getValue()!=null){
                    btn.setEnabled(false);

                        pBar.setVisibility(View.GONE);
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                        loadingBg.setVisibility(View.GONE);




                }


            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Log.i("Flexo", dataSnapshot.getValue().toString());
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }


    @Override
    public void onClick(View v) {
        int tag = Integer.parseInt(v.getTag().toString());

            if (!(seatList[tag - 1] == tag)) {
                seatList[tag - 1] = tag;
            } else {
                seatList[tag - 1] = NULL;
            }


    }
}