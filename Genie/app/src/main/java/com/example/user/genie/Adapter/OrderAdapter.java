package com.example.user.genie.Adapter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.genie.Model.AllAddressModel;
import com.example.user.genie.Model.OrderModel;
import com.example.user.genie.ObjectNew.AllOrdersResponse;
import com.example.user.genie.ObjectNew.RentResponse;
import com.example.user.genie.R;

import java.util.ArrayList;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    private Context context;
    private ArrayList<AllOrdersResponse.allOrdersArrayListResponse> ordersArrayListResponses;

    public OrderAdapter(Context context, ArrayList<AllOrdersResponse.allOrdersArrayListResponse> ordersArrayListResponses) {
        this.ordersArrayListResponses = ordersArrayListResponses;
        this.context=context;
    }
    @Override
    public OrderAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_orders,parent,false);
        return new OrderAdapter.ViewHolder(v);
    }

    @Override

    public void onBindViewHolder(OrderAdapter.ViewHolder holder, int position) {
        AllOrdersResponse.allOrdersArrayListResponse listItem = ordersArrayListResponses.get(position);

      /*  Typeface tf = Typeface.createFromAsset(context.getAssets(), fontPath);
        Typeface tf2 = Typeface.createFromAsset(context.getAssets(), fontPath2);
        Typeface tf3 = Typeface.createFromAsset(context.getAssets(), fontPath3);*/


        //   holder.samagri_amt.setTypeface(tf3);
        holder.name.setText(listItem.getFirst_name());
        holder.service_name.setText(listItem.getServiceName());
        holder.transactiondate.setText(listItem.getTransactionDate());
        holder.orderstatus.setText(listItem.getStatus());
        holder.amount.setText(context.getResources().getString(R.string.rupee)+listItem.getAmount());
        holder.errorMessage.setText(listItem.getErrorMessage());
        holder.operatorRef.setText(listItem.getOperatorRef());
        holder.rechargeno.setText(listItem.getRecharge_no());
        holder.itemname.setText(listItem.getItem_name());
        holder.quantity.setText(listItem.getQuantity());
        holder.unit.setText(listItem.getUnit());
        holder.price.setText(listItem.getPrice());
        holder.subtotal.setText(listItem.getSubtotal());
        holder.taxamount.setText(listItem.getTax_amount());
        holder.serviceorderid.setText(listItem.getService_order_id());


    }

    @Override
    public int getItemCount() {
        return ordersArrayListResponses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView id,name,service_name,transactiondate,orderstatus,amount,errorMessage,
                operatorRef,rechargeno,itemname,quantity,unit,price,subtotal,taxamount,serviceorderid;

        public ViewHolder(View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.id);
            name = itemView.findViewById(R.id.name);
            service_name = itemView.findViewById(R.id.service_name);
            transactiondate = itemView.findViewById(R.id.transactiondate);
            orderstatus = itemView.findViewById(R.id.orderstatus);
            amount = itemView.findViewById(R.id.amount);
            errorMessage = itemView.findViewById(R.id.errorMessage);
            operatorRef = itemView.findViewById(R.id.operatorRef);
            rechargeno = itemView.findViewById(R.id.rechargeno);
            itemname = itemView.findViewById(R.id.itemname);
            quantity = itemView.findViewById(R.id.quantity);
            unit = itemView.findViewById(R.id.unit);
            price = itemView.findViewById(R.id.price);
            subtotal = itemView.findViewById(R.id.subtotal);
            taxamount = itemView.findViewById(R.id.taxamount);
            serviceorderid = itemView.findViewById(R.id.serviceorderid);

        }
    }
}


