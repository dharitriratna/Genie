package com.example.user.genie.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.genie.Model.AllAddressModel;
import com.example.user.genie.Model.OrderModel;
import com.example.user.genie.R;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    private List<OrderModel> orderModels;
    private Context context;

   /* String fontPath = "fonts/Raleway-Light.ttf";
    String fontPath2 = "fonts/Raleway-Thin.ttf";
    String fontPath3 = "fonts/Raleway_SemiBold.ttf";*/

    public OrderAdapter(List<OrderModel> orderModels, Context context) {
        this.orderModels = orderModels;
        this.context = context;
    }

    @Override
    public OrderAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_orders,parent,false);
        return new OrderAdapter.ViewHolder(v);
    }

    @Override

    public void onBindViewHolder(OrderAdapter.ViewHolder holder, int position) {
        OrderModel listItem = orderModels.get(position);

      /*  Typeface tf = Typeface.createFromAsset(context.getAssets(), fontPath);
        Typeface tf2 = Typeface.createFromAsset(context.getAssets(), fontPath2);
        Typeface tf3 = Typeface.createFromAsset(context.getAssets(), fontPath3);*/


        //   holder.samagri_amt.setTypeface(tf3);

        holder.id.setText(listItem.getOrder_id());
        holder.name.setText(listItem.getName());
        //   holder.pro_name.setTypeface(tf3);
        holder.lane.setText(listItem.getOrder_line());
        holder.landmark.setText(listItem.getOrder_landmark());
        holder.city.setText("City : "+listItem.getOrder_city());
        holder.state.setText("State : "+listItem.getOrder_state());
        holder.country.setText("Country : "+listItem.getOrder_country());
        holder.pin.setText("Pin : "+listItem.getOrder_pin());
        holder.amount.setText("Amount : "+listItem.getOrder_amount());
        holder.subtotal.setText("Subtotal : "+listItem.getOrder_subtotal());

    }

    @Override
    public int getItemCount() {
        return orderModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView id,name,lane,landmark,city,state,country,pin,amount,subtotal;

        public ViewHolder(View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.id);
            name = itemView.findViewById(R.id.name);
            lane = itemView.findViewById(R.id.lane);
            landmark = itemView.findViewById(R.id.landmark);
            city = itemView.findViewById(R.id.city);
            state = itemView.findViewById(R.id.state);
            country = itemView.findViewById(R.id.country);
            pin = itemView.findViewById(R.id.pin);
            amount = itemView.findViewById(R.id.amount);
            subtotal = itemView.findViewById(R.id.subtotal);
        }
    }
}


