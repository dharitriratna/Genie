package ratna.genie1.user.genie.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ratna.genie1.user.genie.Model.OrderModel;
import ratna.genie1.user.genie.Model.RechargeOrderModel;
import ratna.genie1.user.genie.R;

import java.util.List;

public class RechargeAndBillOrderAdapter extends RecyclerView.Adapter<RechargeAndBillOrderAdapter.ViewHolder> {

    private List<RechargeOrderModel> orderModels;
    private Context context;

   /* String fontPath = "fonts/Raleway-Light.ttf";
    String fontPath2 = "fonts/Raleway-Thin.ttf";
    String fontPath3 = "fonts/Raleway_SemiBold.ttf";*/

    public RechargeAndBillOrderAdapter(List<RechargeOrderModel> orderModels, Context context) {
        this.orderModels = orderModels;
        this.context = context;
    }

    @Override
    public RechargeAndBillOrderAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.rechargerowlayout,parent,false);
        return new RechargeAndBillOrderAdapter.ViewHolder(v);
    }

    @Override

    public void onBindViewHolder(RechargeAndBillOrderAdapter.ViewHolder holder, int position) {
        RechargeOrderModel listItem = orderModels.get(position);

      /*  Typeface tf = Typeface.createFromAsset(context.getAssets(), fontPath);
        Typeface tf2 = Typeface.createFromAsset(context.getAssets(), fontPath2);
        Typeface tf3 = Typeface.createFromAsset(context.getAssets(), fontPath3);*/


        //   holder.samagri_amt.setTypeface(tf3);


        holder.orderNo.setText("Order No: "+listItem.getOrderNumber());
        //   holder.pro_name.setTypeface(tf3);
        holder.orderdateandtime.setText(listItem.getOrderDateandtime());
        holder.orderstatus.setText(listItem.getOrderStatus());
        holder.operatorno.setText(listItem.getCustomerNumber());
        holder.operatorname.setText(listItem.getOperatorName());
        holder.rechargeamount.setText(context.getResources().getString(R.string.rupee)+listItem.getRechargeAmount());
        holder.rechargetype.setText(listItem.getRechargeType());


    }

    @Override
    public int getItemCount() {
        return orderModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView id,orderNo,orderdateandtime,orderstatus,userid,operatorno,operatorname,rechargeamount,rechargetype;

        public ViewHolder(View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.id);
            orderNo = itemView.findViewById(R.id.orderNo);
            orderdateandtime = itemView.findViewById(R.id.orderdateandtime);
            orderstatus = itemView.findViewById(R.id.orderstatus);
            userid = itemView.findViewById(R.id.userid);
            operatorno = itemView.findViewById(R.id.operatorno);
            operatorname = itemView.findViewById(R.id.operatorname);
            rechargeamount = itemView.findViewById(R.id.rechargeamount);
            rechargetype = itemView.findViewById(R.id.rechargetype);
        }
    }
}


