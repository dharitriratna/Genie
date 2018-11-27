package com.example.user.genie.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.genie.Model.BusCitiesModel;
import com.example.user.genie.ObjectNew.oRiginCities;
import com.example.user.genie.R;
import com.example.user.genie.ToCitesActivity;
import com.example.user.genie.helper.RegPrefManager;

import java.util.ArrayList;
import java.util.List;

public class BusCitiesAdapter extends RecyclerView.Adapter<BusCitiesAdapter.ViewHolder> {

    private ArrayList<oRiginCities> busCitiesModels;
    private Context context;

   /* String fontPath = "fonts/Raleway-Light.ttf";
    String fontPath2 = "fonts/Raleway-Thin.ttf";
    String fontPath3 = "fonts/Raleway_SemiBold.ttf";*/

    public BusCitiesAdapter(ArrayList<oRiginCities> busCitiesModels, Context context) {
        this.busCitiesModels = busCitiesModels;
        this.context = context;
    }

    @Override
    public BusCitiesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_buscities_boards, parent, false);
        return new BusCitiesAdapter.ViewHolder(v);
    }


    @Override

    public void onBindViewHolder(BusCitiesAdapter.ViewHolder holder, final int position) {
        oRiginCities listItem = busCitiesModels.get(position);

      /*  Typeface tf = Typeface.createFromAsset(context.getAssets(), fontPath);
        Typeface tf2 = Typeface.createFromAsset(context.getAssets(), fontPath2);
        Typeface tf3 = Typeface.createFromAsset(context.getAssets(), fontPath3);*/


        //   holder.samagri_amt.setTypeface(tf3);
       // holder.origin_id.setText(listItem.getOriginId());
        holder.origin_name.setText(listItem.getOriginName());
        holder.origin_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oRiginCities cities=busCitiesModels.get(position);
                int id=cities.getOriginId();
                RegPrefManager.getInstance(context).setBusFromID(String.valueOf(id));
                RegPrefManager.getInstance(context).setBusFromName(cities.getOriginName());
                context.startActivity(new Intent(context, ToCitesActivity.class));

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
        return busCitiesModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView origin_name, origin_id;


        public ViewHolder(View itemView) {
            super(itemView);
        //    origin_id = itemView.findViewById(R.id.origin_id);
            origin_name = itemView.findViewById(R.id.origin_name);


        }
    }

    public void filterList(ArrayList<oRiginCities> filterdNames) {
        this.busCitiesModels = filterdNames;
        notifyDataSetChanged();
    }
}