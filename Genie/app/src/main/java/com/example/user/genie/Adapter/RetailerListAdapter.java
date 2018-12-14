package com.example.user.genie.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.genie.Model.FSEListModel;
import com.example.user.genie.Model.RetailerListModel;
import com.example.user.genie.R;

import java.util.List;

public class RetailerListAdapter extends RecyclerView.Adapter<RetailerListAdapter.ViewHolder> {

    private List<RetailerListModel> retailerListModels;
    private Context context;
    String demo;

   /* String fontPath = "fonts/Raleway-Light.ttf";
    String fontPath2 = "fonts/Raleway-Thin.ttf";
    String fontPath3 = "fonts/Raleway_SemiBold.ttf";*/

    public RetailerListAdapter(List<RetailerListModel> retailerListModels, Context context) {
        this.retailerListModels = retailerListModels;
        this.context = context;
    }

    @Override
    public RetailerListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_fse_lists, parent, false);
        return new RetailerListAdapter.ViewHolder(v);
    }


    @Override

    public void onBindViewHolder(RetailerListAdapter.ViewHolder holder, int position) {
        RetailerListModel listItem = retailerListModels.get(position);

      /*  Typeface tf = Typeface.createFromAsset(context.getAssets(), fontPath);
        Typeface tf2 = Typeface.createFromAsset(context.getAssets(), fontPath2);
        Typeface tf3 = Typeface.createFromAsset(context.getAssets(), fontPath3);*/


        //   holder.samagri_amt.setTypeface(tf3);
        holder.userId.setText(listItem.getId());
        holder.userName.setText(listItem.getFirst_name());
        holder.userEmail.setText(listItem.getEmail());
        holder.userNumber.setText(listItem.getPhone());
      //  Picasso.with(context).load(listItem.getGift_image()).into(holder.gift_image);


       /* holder.mob_operator_id.setText(listItem.getOperator_id());
        holder.operator_name.setText(listItem.getOperator_name());
        holder.service_type.setText(listItem.getService_type());
        //   holder.pro_name.setTypeface(tf3);
        holder.operator_code.setText(listItem.getOperator_code());*/

    }

    @Override
    public int getItemCount() {
        return retailerListModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView userName, userEmail,userNumber, userId;
        private ImageView userImage;


        public ViewHolder(View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.userName);
            userEmail = itemView.findViewById(R.id.userEmail);
            userNumber = itemView.findViewById(R.id.userNumber);
            userId = itemView.findViewById(R.id.userId);
            userImage = itemView.findViewById(R.id.userImage);

        }
    }
}
