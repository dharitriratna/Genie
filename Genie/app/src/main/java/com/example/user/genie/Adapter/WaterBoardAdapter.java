package com.example.user.genie.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.genie.MobileRecharge;
import com.example.user.genie.Model.DTHOperatorsModel;
import com.example.user.genie.Model.ElectricityBoardModel;
import com.example.user.genie.Model.WaterBoardModel;
import com.example.user.genie.R;
import com.example.user.genie.WaterBill;
import com.example.user.genie.helper.RegPrefManager;

import java.util.ArrayList;
import java.util.List;

public class WaterBoardAdapter extends RecyclerView.Adapter<WaterBoardAdapter.ViewHolder> {

    private List<WaterBoardModel> waterBoardModels;
    private Context context;

   /* String fontPath = "fonts/Raleway-Light.ttf";
    String fontPath2 = "fonts/Raleway-Thin.ttf";
    String fontPath3 = "fonts/Raleway_SemiBold.ttf";*/

    public WaterBoardAdapter(List<WaterBoardModel> waterBoardModels, Context context) {
        this.waterBoardModels = waterBoardModels;
        this.context = context;
    }

    @Override
    public WaterBoardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_water_boards,parent,false);
        return new WaterBoardAdapter.ViewHolder(v);
    }


    @Override

    public void onBindViewHolder(WaterBoardAdapter.ViewHolder holder, int position) {
       final WaterBoardModel listItem = waterBoardModels.get(position);

      /*  Typeface tf = Typeface.createFromAsset(context.getAssets(), fontPath);
        Typeface tf2 = Typeface.createFromAsset(context.getAssets(), fontPath2);
        Typeface tf3 = Typeface.createFromAsset(context.getAssets(), fontPath3);*/


        //   holder.samagri_amt.setTypeface(tf3);
        holder.mob_operator_id.setText(listItem.getWater_board_id());
        holder.operator_name.setText(listItem.getWater_board_name());
        holder.operator_code.setText(listItem.getWater_board_code());
        holder.service_type.setText(listItem.getWater_board_type());


        holder.operator_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RegPrefManager.getInstance(context).setWaterBoard(listItem.getWater_board_name(),listItem.getWater_board_code());

                context.startActivity(new Intent(context,WaterBill.class));
                 ((Activity)context).finish();
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
        return waterBoardModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mob_operator_id,operator_name,service_type,operator_code;


        public ViewHolder(View itemView) {
            super(itemView);
            mob_operator_id = itemView.findViewById(R.id.mob_operator);
            operator_name = itemView.findViewById(R.id.operator_name);
            service_type = itemView.findViewById(R.id.service_type);
            operator_code = itemView.findViewById(R.id.operator_code);

        }
    }
    public void filterList(ArrayList<WaterBoardModel> filterdNames) {
        this.waterBoardModels = filterdNames;
        notifyDataSetChanged();
    }
}
