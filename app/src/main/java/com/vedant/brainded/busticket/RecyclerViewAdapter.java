package com.vedant.brainded.busticket;

/**
 * Created by Dell on 6/30/2017.
 */
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by JUNED on 6/10/2016.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    //ed
    ArrayList<Ticket> ticketList;
    Context context;
    View view1;
    ViewHolder viewHolder1;
    TextView textView;

    public RecyclerViewAdapter(Context context1,ArrayList<Ticket> ticketList){

        this.ticketList = ticketList;
        context = context1;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textView;
        public TextView seatNum;


        public ViewHolder(View v){

            super(v);

            seatNum = (TextView)v.findViewById(R.id.subject_seatNum);

            textView = (TextView)v.findViewById(R.id.subject_textview);


        }
    }

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){

        view1 = LayoutInflater.from(context).inflate(R.layout.ticket_item,parent,false);

        viewHolder1 = new ViewHolder(view1);

        return viewHolder1;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        Ticket tick = ticketList.get(position);
        String num = Integer.toString(position+1)+".";

        holder.textView.setText(tick.getName());
        holder.seatNum.setText(tick.getSeatNum());

    }

    @Override
    public int getItemCount(){

        return ticketList.size();
    }
}