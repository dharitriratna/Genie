package com.example.user.genie.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.genie.Model.GasProviderModel;
import com.example.user.genie.Model.HotelCitiesModel;
import com.example.user.genie.R;

import java.util.ArrayList;
import java.util.List;

public class HotelCitiesAdapter extends RecyclerView.Adapter<HotelCitiesAdapter.ViewHolder> {

    private List<HotelCitiesModel> hotelCitiesModels;
    private Context context;

   /* String fontPath = "fonts/Raleway-Light.ttf";
    String fontPath2 = "fonts/Raleway-Thin.ttf";
    String fontPath3 = "fonts/Raleway_SemiBold.ttf";*/

    public HotelCitiesAdapter(List<HotelCitiesModel> hotelCitiesModels, Context context) {
        this.hotelCitiesModels = hotelCitiesModels;
        this.context = context;
    }

    @Override
    public HotelCitiesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_cities_hotels,parent,false);
        return new HotelCitiesAdapter.ViewHolder(v);
    }


    @Override

    public void onBindViewHolder(HotelCitiesAdapter.ViewHolder holder, int position) {
        HotelCitiesModel listItem = hotelCitiesModels.get(position);

      /*  Typeface tf = Typeface.createFromAsset(context.getAssets(), fontPath);
        Typeface tf2 = Typeface.createFromAsset(context.getAssets(), fontPath2);
        Typeface tf3 = Typeface.createFromAsset(context.getAssets(), fontPath3);*/


        //   holder.samagri_amt.setTypeface(tf3);
       /* holder.mob_operator_id.setText(listItem.getGas_board_id());
        holder.operator_name.setText(listItem.getGas_board_name());
        holder.operator_code.setText(listItem.getGas_board_code());
        holder.service_type.setText(listItem.getGas_board_type());*/


       /* holder.mob_operator_id.setText(listItem.getOperator_id());
        holder.operator_name.setText(listItem.getOperator_name());
        holder.service_type.setText(listItem.getService_type());
        //   holder.pro_name.setTypeface(tf3);
        holder.operator_code.setText(listItem.getOperator_code());*/

    }

    @Override
    public int getItemCount() {
        return hotelCitiesModels.size();
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
    public void filterList(ArrayList<HotelCitiesModel> filterdNames) {
        this.hotelCitiesModels = filterdNames;
        notifyDataSetChanged();
    }
}
