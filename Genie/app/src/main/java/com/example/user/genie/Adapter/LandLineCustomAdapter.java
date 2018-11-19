package com.example.user.genie.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.genie.DataCardActivity;
import com.example.user.genie.LandLine;
import com.example.user.genie.ObjectNew.DataCardCircleResponse;
import com.example.user.genie.ObjectNew.LandLineData;
import com.example.user.genie.ObjectNew.LandlineResponseModel;
import com.example.user.genie.R;
import com.example.user.genie.helper.RegPrefManager;

import java.util.ArrayList;

/**
 * Created by RatnaDev008 on 10/29/2018.
 */

public class LandLineCustomAdapter extends RecyclerView.Adapter<LandLineCustomAdapter.ViewHolder> {
    private Context context;
    private ArrayList<LandLineData> operatorList;



    public LandLineCustomAdapter(Context context, ArrayList<LandLineData> operatorList) {
        this.operatorList = operatorList;
        this.context=context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_landline_operators, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder,final int position) {

        final LandLineData data=operatorList.get(position);

        holder.operator_name.setText(data.getOperator_name());
        holder.service_type.setText(data.getService_type());

        holder.operator_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RegPrefManager.getInstance(context).setLandlineOperator(data.getOperator_name(),data.getOperator_code());

                context.startActivity(new Intent(context,LandLine.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return operatorList.size();
    }




    class ViewHolder extends RecyclerView.ViewHolder {

        TextView operator_name,service_type;

        ViewHolder(View itemView) {
            super(itemView);

            operator_name = (TextView) itemView.findViewById(R.id.operator_name);
            service_type=(TextView)itemView.findViewById(R.id.service_type);
        }
    }

    //This method will filter the list
    //here we are passing the filtered data
    //and assigning it to the list with notifydatasetchanged method
    public void filterList(ArrayList<LandLineData > filterdNames) {
        this.operatorList = filterdNames;
        notifyDataSetChanged();
    }




}
