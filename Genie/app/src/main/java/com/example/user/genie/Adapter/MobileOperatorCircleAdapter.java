package com.example.user.genie.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.genie.Model.MobileOperatorCircleModel;
import com.example.user.genie.Model.MobileOperatorsModel;
import com.example.user.genie.R;

import java.util.List;

public class MobileOperatorCircleAdapter extends RecyclerView.Adapter<MobileOperatorCircleAdapter.ViewHolder> {

    private List<MobileOperatorCircleModel> operatorCircleModels;
    private Context context;

   /* String fontPath = "fonts/Raleway-Light.ttf";
    String fontPath2 = "fonts/Raleway-Thin.ttf";
    String fontPath3 = "fonts/Raleway_SemiBold.ttf";*/

    public MobileOperatorCircleAdapter(List<MobileOperatorCircleModel> operatorCircleModels, Context context) {
        this.operatorCircleModels = operatorCircleModels;
        this.context = context;
    }

    @Override
    public MobileOperatorCircleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_mobile_operator_circle,parent,false);
        return new MobileOperatorCircleAdapter.ViewHolder(v);
    }


    @Override

    public void onBindViewHolder(MobileOperatorCircleAdapter.ViewHolder holder, int position) {
        MobileOperatorCircleModel listItem = operatorCircleModels.get(position);

      /*  Typeface tf = Typeface.createFromAsset(context.getAssets(), fontPath);
        Typeface tf2 = Typeface.createFromAsset(context.getAssets(), fontPath2);
        Typeface tf3 = Typeface.createFromAsset(context.getAssets(), fontPath3);*/

        //   holder.samagri_amt.setTypeface(tf3);
        holder.operator_circle_id.setText(listItem.getOperator_circle_id());
        holder.operator_circle_name.setText(listItem.getOperator_circle_name());
        holder.operator_circle_code.setText(listItem.getOperator_circle_code());

       /* holder.mob_operator_id.setText(listItem.getOperator_id());
        holder.operator_name.setText(listItem.getOperator_name());
        holder.service_type.setText(listItem.getService_type());
        //   holder.pro_name.setTypeface(tf3);
        holder.operator_code.setText(listItem.getOperator_code());*/
    }

    @Override
    public int getItemCount() {
        return operatorCircleModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView operator_circle_id,operator_circle_name,operator_circle_code;

        public ViewHolder(View itemView) {
            super(itemView);
            operator_circle_id = itemView.findViewById(R.id.operator_circle_id);
            operator_circle_name = itemView.findViewById(R.id.operator_circle_name);
            operator_circle_code = itemView.findViewById(R.id.operator_circle_code);
        }

    }
}
