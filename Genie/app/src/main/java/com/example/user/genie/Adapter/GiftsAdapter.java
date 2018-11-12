package com.example.user.genie.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.genie.Model.EventsModel;
import com.example.user.genie.Model.GiftsModel;
import com.example.user.genie.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class GiftsAdapter extends RecyclerView.Adapter<GiftsAdapter.ViewHolder> {

    private List<GiftsModel> giftsModels;
    private Context context;
    String demo;

   /* String fontPath = "fonts/Raleway-Light.ttf";
    String fontPath2 = "fonts/Raleway-Thin.ttf";
    String fontPath3 = "fonts/Raleway_SemiBold.ttf";*/

    public GiftsAdapter(List<GiftsModel> giftsModels, Context context) {
        this.giftsModels = giftsModels;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_gifts_row, parent, false);
        return new ViewHolder(v);
    }


    @Override

    public void onBindViewHolder(ViewHolder holder, int position) {
        GiftsModel listItem = giftsModels.get(position);

      /*  Typeface tf = Typeface.createFromAsset(context.getAssets(), fontPath);
        Typeface tf2 = Typeface.createFromAsset(context.getAssets(), fontPath2);
        Typeface tf3 = Typeface.createFromAsset(context.getAssets(), fontPath3);*/


        //   holder.samagri_amt.setTypeface(tf3);
        holder.gift_id.setText(listItem.getGift_id());
        holder.gift_name.setText(listItem.getGift_name());
        holder.gift_price.setText(context.getResources().getString(R.string.rupee) + listItem.getGift_price());
        Picasso.with(context).load(listItem.getGift_image()).into(holder.gift_image);


       /* holder.mob_operator_id.setText(listItem.getOperator_id());
        holder.operator_name.setText(listItem.getOperator_name());
        holder.service_type.setText(listItem.getService_type());
        //   holder.pro_name.setTypeface(tf3);
        holder.operator_code.setText(listItem.getOperator_code());*/

    }

    @Override
    public int getItemCount() {
        return giftsModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView gift_name, gift_price, gift_id;
        private ImageView gift_image;


        public ViewHolder(View itemView) {
            super(itemView);
            gift_name = itemView.findViewById(R.id.gift_name);
            gift_price = itemView.findViewById(R.id.gift_price);
            gift_id = itemView.findViewById(R.id.gift_id);
            gift_image = itemView.findViewById(R.id.gift_image);

        }
    }
}
