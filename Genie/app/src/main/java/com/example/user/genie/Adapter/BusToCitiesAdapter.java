package com.example.user.genie.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.genie.BusBookingActivity;
import com.example.user.genie.ObjectNew.destinationCities;
import com.example.user.genie.R;
import com.example.user.genie.helper.RegPrefManager;

import java.util.ArrayList;

public class BusToCitiesAdapter extends RecyclerView.Adapter<BusToCitiesAdapter.ViewHolder> {

    private ArrayList<destinationCities> destinationCitiesModels;
    private Context context;

   /* String fontPath = "fonts/Raleway-Light.ttf";
    String fontPath2 = "fonts/Raleway-Thin.ttf";
    String fontPath3 = "fonts/Raleway_SemiBold.ttf";*/

    public BusToCitiesAdapter(ArrayList<destinationCities> destinationCitiesModels, Context context) {
        this.destinationCitiesModels = destinationCitiesModels;
        this.context = context;

    }


    @Override
    public BusToCitiesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_bustocities_boards, parent, false);
        return new BusToCitiesAdapter.ViewHolder(v);
    }


    @Override

    public void onBindViewHolder(BusToCitiesAdapter.ViewHolder holder, final int position) {
        destinationCities listItem = destinationCitiesModels.get(position);

      /*  Typeface tf = Typeface.createFromAsset(context.getAssets(), fontPath);
        Typeface tf2 = Typeface.createFromAsset(context.getAssets(), fontPath2);
        Typeface tf3 = Typeface.createFromAsset(context.getAssets(), fontPath3);*/
        //   holder.samagri_amt.setTypeface(tf3);
       // holder.origin_id.setText(listItem.getOriginId());
        holder.origin_name.setText(listItem.getDestinationName());
        holder.origin_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                destinationCities cities=destinationCitiesModels.get(position);
                int id=cities.getDestinationId();
                RegPrefManager.getInstance(context).setBusToID(String.valueOf(id));
                RegPrefManager.getInstance(context).setBusToName(cities.getDestinationName());

                Intent intent = new Intent(context,BusBookingActivity.class);
                context.startActivity(intent);


            }
        });

       /* holder.mob_operator_id.setText(listItem.getOperator_id());
        holder.operator_name.setText(listItem.getOperator_name());
        holder.service_type.setText(listItem.getService_type());
        //   holder.pro_name.setTypeface(tf3);
        holder.operator_code.setText(listItem.getOperator_code());*/

    }

    @Override
    public int getItemCount() {
        return destinationCitiesModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView origin_name, origin_id;


        public ViewHolder(View itemView) {
            super(itemView);
        //    origin_id = itemView.findViewById(R.id.origin_id);
            origin_name = itemView.findViewById(R.id.origin_name);


        }
    }

    public void filterList(ArrayList<destinationCities> filterdNames) {
        this.destinationCitiesModels = filterdNames;
        notifyDataSetChanged();
    }
}