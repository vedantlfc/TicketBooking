package com.vedant.brainded.busticket;


import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;

public class TicketInfo extends AppCompatActivity {


    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private DatabaseReference mTicketDB;
    private FirebaseAuth auth;
    private ProgressBar pBar;




    Context context;
    RecyclerView recyclerView;
    RelativeLayout relativeLayout;
    RecyclerView.Adapter recyclerViewAdapter;
    RecyclerView.LayoutManager recylerViewLayoutManager;
    ArrayList<Ticket> ticketo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_ACTION_BAR);



        setContentView(R.layout.ticket_info);

        context = getApplicationContext();
        pBar = (ProgressBar) findViewById(R.id.pBarr);

        ticketo = new ArrayList<Ticket>();
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference("users");
        mTicketDB = mFirebaseInstance.getReference("Tickets");
        mFirebaseInstance.getReference("app_title").setValue("Users and Tickets");
        auth = FirebaseAuth.getInstance();

        relativeLayout = (RelativeLayout) findViewById(R.id.relativelayout1);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview1);

        recylerViewLayoutManager = new LinearLayoutManager(context);

        recyclerView.setLayoutManager(recylerViewLayoutManager);

        //edited


        pBar.setVisibility(View.VISIBLE);

        observeAndReport();
        new CountDownTimer(10000, 1000) {

            public void onTick(long millisUntilFinished) {

            }

            public void onFinish() {
                if(pBar.getVisibility()==View.VISIBLE){
                    pBar.setVisibility(View.GONE);
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                    Toast.makeText(getApplicationContext(), "You don't have any booked tickets!", Toast.LENGTH_SHORT).show();

                }
            }
        }.start();



    }

    private void observeAndReport(){
        final String userEmail = auth.getCurrentUser().getEmail();

        Query q1 = mFirebaseDatabase.orderByChild("email").equalTo(userEmail);

        q1.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Customer cus = dataSnapshot.getValue(Customer.class);
                final String userName = cus.getName();
                Log.i("Flexzo", "added1");

                Query q2 = mTicketDB.orderByChild("name").equalTo(userName);
                q2.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {


                        Log.i("Flexzo", "added2");
                        Ticket tick = dataSnapshot.getValue(Ticket.class);
                        ticketo.add(tick);

                        recyclerViewAdapter = new RecyclerViewAdapter(context, ticketo);

                        recyclerView.setAdapter(recyclerViewAdapter);
                        pBar.setVisibility(View.GONE);
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

    }
}
