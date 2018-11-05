package com.example.user.genie.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.user.genie.Model.TrainSeatAvailableModel;
import com.example.user.genie.R;

import java.util.ArrayList;

/**
 * Created by RatnaDev008 on 10/29/2018.
 */

public class TrainSeatAdapter extends RecyclerView.Adapter<TrainSeatAdapter.ViewHolder> {
    private Context context;
    private ArrayList<String> names;

    public TrainSeatAdapter(Context context, ArrayList<String> names) {
        this.names = names;
        this.context=context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trainseatlayout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
       // TrainSeatAvailableModel trainSeatAvailableModel=names.get(position);
        holder.seatTv.setText(names.get(position));
        /*destTimeTv,arriveTimeTv,destplaceTv,arriveplaceTv*/





    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView seatTv,priceTv,availableTv;


        ViewHolder(View itemView) {
            super(itemView);

            seatTv = (TextView) itemView.findViewById(R.id.seatTv);
            priceTv=(TextView)itemView.findViewById(R.id.priceTv);
            availableTv=(TextView)itemView.findViewById(R.id.availableTv);

        }
    }
    //This method will filter the list
    //here we are passing the filtered data
    //and assigning it to the list with notifydatasetchanged method
    public void filterList(ArrayList<String> filterdNames) {
        this.names = filterdNames;
        notifyDataSetChanged();
    }
}
