package com.example.user.genie.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.genie.Model.EventsModel;
import com.example.user.genie.Model.WaterBoardModel;
import com.example.user.genie.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder> {

    private List<EventsModel> eventsModels;
    private Context context;

   /* String fontPath = "fonts/Raleway-Light.ttf";
    String fontPath2 = "fonts/Raleway-Thin.ttf";
    String fontPath3 = "fonts/Raleway_SemiBold.ttf";*/

    public EventsAdapter(List<EventsModel> eventsModels, Context context) {
        this.eventsModels = eventsModels;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_events,parent,false);
        return new ViewHolder(v);
    }


    @Override

    public void onBindViewHolder(ViewHolder holder, int position) {
        EventsModel listItem = eventsModels.get(position);

      /*  Typeface tf = Typeface.createFromAsset(context.getAssets(), fontPath);
        Typeface tf2 = Typeface.createFromAsset(context.getAssets(), fontPath2);
        Typeface tf3 = Typeface.createFromAsset(context.getAssets(), fontPath3);*/


        //   holder.samagri_amt.setTypeface(tf3);
        holder.event_id.setText(listItem.getEvent_id());
        holder.event_name.setText(listItem.getEvent_name());
        holder.event_entry_fees.setText("Starting From" +" "+context.getResources().getString(R.string.rupee)+listItem.getEvent_price());
        Picasso.with(context).load(listItem.getEvent_image()).into(holder.event_image);


       /* holder.mob_operator_id.setText(listItem.getOperator_id());
        holder.operator_name.setText(listItem.getOperator_name());
        holder.service_type.setText(listItem.getService_type());
        //   holder.pro_name.setTypeface(tf3);
        holder.operator_code.setText(listItem.getOperator_code());*/
    }

    @Override
    public int getItemCount() {
        return eventsModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView event_name,event_entry_fees,event_id;
        private ImageView event_image;


        public ViewHolder(View itemView) {
            super(itemView);
            event_name = itemView.findViewById(R.id.event_name);
            event_entry_fees = itemView.findViewById(R.id.event_entry_fees);
            event_id = itemView.findViewById(R.id.event_id);
            event_image = itemView.findViewById(R.id.event_image);

        }

    }
}
