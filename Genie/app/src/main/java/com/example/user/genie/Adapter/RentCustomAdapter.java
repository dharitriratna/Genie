package com.example.user.genie.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.genie.MoviesListActivity;
import com.example.user.genie.ObjectNew.RentResponse;
import com.example.user.genie.R;
import com.example.user.genie.helper.RegPrefManager;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by RatnaDev008 on 10/29/2018.
 */

public class RentCustomAdapter extends RecyclerView.Adapter<RentCustomAdapter.ViewHolder> {
    private Context context;
    private ArrayList<RentResponse.Data> names;

    public RentCustomAdapter(Context context, ArrayList<RentResponse.Data> names) {
        this.names = names;
        this.context=context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rentlayout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder,final int position) {
      //  holder.nameTv.setText(names.get(position));

        /*holder.textViewName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String city= (String) holder.textViewName.getText();
                Intent i=new Intent(context, MoviesListActivity.class);
                RegPrefManager.getInstance(context).setCity(city);
                context.startActivity(i);
            }
        });*/
        RentResponse.Data data=names.get(position);
        holder.nameTv.setText(data.getCategory());
        holder.addressTv.setText("Address"+"\n"+data.getAddress());
        holder.priceTv.setText("Rs."+data.getPrice());
        holder.phoneTv.setText("Phone: "+data.getPhone());
        String image=data.getImage_url();
         String latitude=data.getLatitude();
         String longitude=data.getLongitude();
        final double lat=Double.valueOf(latitude);
        final double lang=Double.valueOf(longitude);
        if(data.getImage_url()!=null){
            Picasso.with(context).load(data.getImage_url()).fit().into(holder.image);
        }
        holder.phoneTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RentResponse.Data data=names.get(position);
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+data.getPhone()));
                context.startActivity(intent);
            }
        });
        holder.addressTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(android.content.Intent.ACTION_VIEW);
                intent.setData(Uri.parse("geo:"+lat+","+lang));
                Intent.createChooser(intent,"Launch Maps");
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;

        TextView nameTv,priceTv,addressTv,phoneTv;

        ViewHolder(View itemView) {
            super(itemView);

            addressTv = (TextView) itemView.findViewById(R.id.addressTv);
            priceTv = (TextView) itemView.findViewById(R.id.priceTv);
            nameTv = (TextView) itemView.findViewById(R.id.nameTv);
            image = (ImageView) itemView.findViewById(R.id.image);
            phoneTv=(TextView)itemView.findViewById(R.id.phoneTv);
        }
    }
    //This method will filter the list
    //here we are passing the filtered data
    //and assigning it to the list with notifydatasetchanged method
    public void filterList(ArrayList<RentResponse.Data> filterdNames) {
        this.names = filterdNames;
        notifyDataSetChanged();
    }
}
