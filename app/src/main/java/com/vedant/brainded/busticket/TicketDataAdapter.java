package com.vedant.brainded.busticket;


/**
 * Created by Dell on 6/16/2017.
 */
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

public class TicketDataAdapter extends RecyclerView.Adapter<TicketDataAdapter.MyViewHolder> {

    public class MyViewHolder extends RecyclerView.ViewHolder{

        public MyViewHolder(View view){
            super(view);
        }
    }


    @Override
    public TicketDataAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(TicketDataAdapter.MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
