package com.example.user.genie.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.genie.Model.GasProviderModel;
import com.example.user.genie.Model.WaterBoardModel;
import com.example.user.genie.R;

import java.util.ArrayList;
import java.util.List;

public class GasProviderAdapter extends RecyclerView.Adapter<GasProviderAdapter.ViewHolder> {

    private List<GasProviderModel> gasProviderModels;
    private Context context;

   /* String fontPath = "fonts/Raleway-Light.ttf";
    String fontPath2 = "fonts/Raleway-Thin.ttf";
    String fontPath3 = "fonts/Raleway_SemiBold.ttf";*/

    public GasProviderAdapter(List<GasProviderModel> gasProviderModels, Context context) {
        this.gasProviderModels = gasProviderModels;
        this.context = context;
    }

    @Override
    public GasProviderAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_gas_boards,parent,false);
        return new GasProviderAdapter.ViewHolder(v);
    }


    @Override

    public void onBindViewHolder(GasProviderAdapter.ViewHolder holder, int position) {
        GasProviderModel listItem = gasProviderModels.get(position);

      /*  Typeface tf = Typeface.createFromAsset(context.getAssets(), fontPath);
        Typeface tf2 = Typeface.createFromAsset(context.getAssets(), fontPath2);
        Typeface tf3 = Typeface.createFromAsset(context.getAssets(), fontPath3);*/


        //   holder.samagri_amt.setTypeface(tf3);
        holder.mob_operator_id.setText(listItem.getGas_board_id());
        holder.operator_name.setText(listItem.getGas_board_name());
        holder.operator_code.setText(listItem.getGas_board_code());
        holder.service_type.setText(listItem.getGas_board_type());


       /* holder.mob_operator_id.setText(listItem.getOperator_id());
        holder.operator_name.setText(listItem.getOperator_name());
        holder.service_type.setText(listItem.getService_type());
        //   holder.pro_name.setTypeface(tf3);
        holder.operator_code.setText(listItem.getOperator_code());*/

    }

    @Override
    public int getItemCount() {
        return gasProviderModels.size();
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
    public void filterList(ArrayList<GasProviderModel> filterdNames) {
        this.gasProviderModels = filterdNames;
        notifyDataSetChanged();
    }
}
