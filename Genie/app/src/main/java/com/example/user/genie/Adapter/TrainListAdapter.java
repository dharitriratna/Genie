package com.example.user.genie.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.user.genie.FlightBaggageActivity;
import com.example.user.genie.Model.TrainSeatAvailableModel;
import com.example.user.genie.R;
import com.example.user.genie.TrainSeatAvailableActivity;

import java.util.ArrayList;

/**
 * Created by RatnaDev008 on 10/29/2018.
 */

public class TrainListAdapter extends RecyclerView.Adapter<TrainListAdapter.ViewHolder> {
    private Context context;
    private ArrayList<TrainSeatAvailableModel> names;
    private ArrayList<String> seatAvail;
    private TrainSeatAdapter seatAdapter;

    public TrainListAdapter(Context context, ArrayList<TrainSeatAvailableModel> names) {
        this.names = names;
        this.context=context;
        seatAvail=new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trainlistlayout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        TrainSeatAvailableModel trainSeatAvailableModel=names.get(position);
        holder.trainNameTv.setText(trainSeatAvailableModel.getTrainName());
        /*destTimeTv,arriveTimeTv,destplaceTv,arriveplaceTv*/

        holder.destTimeTv.setText(trainSeatAvailableModel.getDestTime());
        holder.arriveTimeTv.setText(trainSeatAvailableModel.getArriveTime());
        holder.destplaceTv.setText(trainSeatAvailableModel.getDestPlaceName());
        holder.arriveplaceTv.setText(trainSeatAvailableModel.getArrivPlaceName());


        holder.checkAvilBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              /*  String flight= (String) holder.textViewName.getText();
                Intent i=new Intent(context, FlightBaggageActivity.class);
               // RegPrefManager.getInstance(context).setCity(city);
                context.startActivity(i);*/
                SeatDialog();
            }
        });
    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView trainNameTv,destTimeTv,arriveTimeTv,destplaceTv,arriveplaceTv;
        Button checkAvilBut;

        ViewHolder(View itemView) {
            super(itemView);

            trainNameTv = (TextView) itemView.findViewById(R.id.trainNameTv);
            destTimeTv=(TextView)itemView.findViewById(R.id.destTimeTv);
            arriveTimeTv=(TextView)itemView.findViewById(R.id.arriveTimeTv);
            destplaceTv=(TextView)itemView.findViewById(R.id.destplaceTv);
            arriveplaceTv=(TextView)itemView.findViewById(R.id.arriveplaceTv);
            checkAvilBut=(Button) itemView.findViewById(R.id.checkAvilBut);
        }
    }
    //This method will filter the list
    //here we are passing the filtered data
    //and assigning it to the list with notifydatasetchanged method
    public void filterList(ArrayList<TrainSeatAvailableModel> filterdNames) {
        this.names = filterdNames;
        notifyDataSetChanged();
    }

    public void SeatDialog() {
        final Dialog dialog = new Dialog(context);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // Include dialog.xml file
        dialog.setContentView(R.layout.trainavailablelayout);

        // Set dialog title



        RecyclerView seatRecycler = (RecyclerView) dialog.findViewById(R.id.seatRecycler);
        seatAvail.clear();
        seatAvail.add("3A - AC 3 Tier");
        seatAvail.add("2A - AC 2 Tier");
        seatAvail.add("1A - First Class AC");



        seatRecycler.setHasFixedSize(true);
        seatRecycler.setLayoutManager(new LinearLayoutManager(context));

        seatAdapter = new TrainSeatAdapter(context,seatAvail);

        seatRecycler.setAdapter(seatAdapter);



        dialog.show();
    }
}
