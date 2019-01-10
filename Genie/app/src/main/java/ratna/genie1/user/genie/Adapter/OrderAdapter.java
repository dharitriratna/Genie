package ratna.genie1.user.genie.Adapter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ratna.genie1.user.genie.Model.AllAddressModel;
import ratna.genie1.user.genie.Model.OrderModel;
import ratna.genie1.user.genie.ObjectNew.AllOrdersResponse;
import ratna.genie1.user.genie.ObjectNew.RentResponse;
import ratna.genie1.user.genie.R;

import java.util.ArrayList;
import java.util.List;

import ratna.genie1.user.genie.ObjectNew.AllOrdersResponse;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    private Context context;
    private ArrayList<AllOrdersResponse.allOrdersArrayListResponse> ordersArrayListResponses;

    public OrderAdapter(Context context, ArrayList<AllOrdersResponse.allOrdersArrayListResponse> ordersArrayListResponses) {
        this.ordersArrayListResponses = ordersArrayListResponses;
        this.context=context;
    }
    @Override
    public OrderAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(ratna.genie1.user.genie.R.layout.row_orders,parent,false);
        return new OrderAdapter.ViewHolder(v);
    }

    @Override

    public void onBindViewHolder(OrderAdapter.ViewHolder holder, int position) {
        AllOrdersResponse.allOrdersArrayListResponse listItem = ordersArrayListResponses.get(position);

        holder.name.setText(listItem.getFirst_name());
        holder.service_name.setText(listItem.getServiceName());
        holder.transactiondate.setText(listItem.getTransactionDate());
        holder.orderstatus.setText(listItem.getStatus());
        holder.amount.setText(context.getResources().getString(ratna.genie1.user.genie.R.string.rupee)+listItem.getAmount());
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
            id = itemView.findViewById(ratna.genie1.user.genie.R.id.id);
            name = itemView.findViewById(ratna.genie1.user.genie.R.id.name);
            service_name = itemView.findViewById(ratna.genie1.user.genie.R.id.service_name);
            transactiondate = itemView.findViewById(ratna.genie1.user.genie.R.id.transactiondate);
            orderstatus = itemView.findViewById(ratna.genie1.user.genie.R.id.orderstatus);
            amount = itemView.findViewById(ratna.genie1.user.genie.R.id.amount);
            errorMessage = itemView.findViewById(ratna.genie1.user.genie.R.id.errorMessage);
            operatorRef = itemView.findViewById(ratna.genie1.user.genie.R.id.operatorRef);
            rechargeno = itemView.findViewById(ratna.genie1.user.genie.R.id.rechargeno);
            itemname = itemView.findViewById(ratna.genie1.user.genie.R.id.itemname);
            quantity = itemView.findViewById(ratna.genie1.user.genie.R.id.quantity);
            unit = itemView.findViewById(ratna.genie1.user.genie.R.id.unit);
            price = itemView.findViewById(ratna.genie1.user.genie.R.id.price);
            subtotal = itemView.findViewById(ratna.genie1.user.genie.R.id.subtotal);
            taxamount = itemView.findViewById(ratna.genie1.user.genie.R.id.taxamount);
            serviceorderid = itemView.findViewById(ratna.genie1.user.genie.R.id.serviceorderid);

        }
    }
}


