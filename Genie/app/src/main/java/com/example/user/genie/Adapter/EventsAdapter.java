package com.example.user.genie.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.genie.R;


public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.MyViewHolder> {
    private String[] bookingId;
    private String[] details;

    private int mSelectedPosition = 0;
    Context context;


    public EventsAdapter(Context context, String[] bookingId, String[] details) {
        this.bookingId = bookingId;
        this.details = details;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view1 = LayoutInflater.from(context).inflate(R.layout.custom_events, parent, false);
        MyViewHolder holder = new MyViewHolder(view1);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.event_date.setText(details[position]);

    }
    @Override
    public int getItemCount() {
        return bookingId.length;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView event_name, event_entry_fees, event_date;
        public ImageView event_image;


        public MyViewHolder(View view1) {
            super(view1);
            event_name = (TextView) view1.findViewById(R.id.event_name);
            event_entry_fees = (TextView) view1.findViewById(R.id.event_entry_fees);
            event_date= (TextView) view1.findViewById(R.id.event_date);
            event_image = (ImageView)view1.findViewById(R.id.event_image);

        }
    }
}




