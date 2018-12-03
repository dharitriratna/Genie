package com.example.user.genie.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;

import com.example.user.genie.LandLine;
import com.example.user.genie.MobileOperators;
import com.example.user.genie.MobileRecharge;
import com.example.user.genie.Model.DataOperatorListModel;
import com.example.user.genie.Model.MobileOperatorsModel;
import com.example.user.genie.ObjectNew.MobileOperatorData;
import com.example.user.genie.R;
import com.example.user.genie.helper.RegPrefManager;

import java.util.ArrayList;
import java.util.List;

public class MobileOperatorsAdapter extends RecyclerView.Adapter<MobileOperatorsAdapter.ViewHolder> {

    private List<MobileOperatorsModel> operatorsModels;
    private Context context;
    private Filter filter;


   /* String fontPath = "fonts/Raleway-Light.ttf";
    String fontPath2 = "fonts/Raleway-Thin.ttf";
    String fontPath3 = "fonts/Raleway_SemiBold.ttf";*/

    public MobileOperatorsAdapter(List<MobileOperatorsModel> operatorsModels, Context context) {
        this.operatorsModels = operatorsModels;
        this.context = context;

    }




    @Override
    public MobileOperatorsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_mobile_operators,parent,false);
        return new MobileOperatorsAdapter.ViewHolder(v);
    }


    @Override

    public void onBindViewHolder(MobileOperatorsAdapter.ViewHolder holder, int position) {
        final MobileOperatorsModel listItem = operatorsModels.get(position);

      /*  Typeface tf = Typeface.createFromAsset(context.getAssets(), fontPath);
        Typeface tf2 = Typeface.createFromAsset(context.getAssets(), fontPath2);
        Typeface tf3 = Typeface.createFromAsset(context.getAssets(), fontPath3);*/


        //   holder.samagri_amt.setTypeface(tf3);
        holder.mob_operator_id.setText(listItem.getOperator_id());
        holder.operator_name.setText(listItem.getOperator_name());
        holder.operator_code.setText(listItem.getOperator_code());
        holder.service_type.setText(listItem.getService_type());

        holder.operator_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RegPrefManager.getInstance(context).setMobileOperator(listItem.getOperator_name(),listItem.getOperator_code());

                context.startActivity(new Intent(context,MobileRecharge.class));
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
        return operatorsModels.size();
    }


    private void notifyDataSetInvalidated() {
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

    public void filterList(ArrayList<MobileOperatorsModel> filterdNames) {
        this.operatorsModels = filterdNames;
        notifyDataSetChanged();
    }
}
