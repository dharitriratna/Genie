package com.example.user.genie.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.genie.DataCardActivity;
import com.example.user.genie.LandLine;
import com.example.user.genie.Model.BeneficiaryDetailsResponse;
import com.example.user.genie.ObjectNew.DataCardCircleResponse;
import com.example.user.genie.R;
import com.example.user.genie.helper.RegPrefManager;

import java.util.ArrayList;

/**
 * Created by RatnaDev008 on 10/29/2018.
 */

public class RemiterDetailsCustomAdapter extends RecyclerView.Adapter<RemiterDetailsCustomAdapter.ViewHolder> {
    private Context context;
    private ArrayList<BeneficiaryDetailsResponse> operatorList;



    public RemiterDetailsCustomAdapter(Context context, ArrayList<BeneficiaryDetailsResponse> operatorList) {
        this.operatorList = operatorList;
        this.context=context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.beneficarydetailslayout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder,final int position) {

        final BeneficiaryDetailsResponse data=operatorList.get(position);

        holder.accountTv.setText(data.getAccount());
        holder.banknameTv.setText(data.getBank());
        holder.ifscTv.setText(data.getIfsc());
        holder.nameTv.setText(data.getName());
        holder.mobileTv.setText(data.getMobile());


       /* holder.textViewName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String back= RegPrefManager.getInstance(context).getBack();
                if(back.equals("Landline")) {
                    RegPrefManager.getInstance(context).setLandlineCircle(data.getCircle_name(), data.getCircle_code());

                    context.startActivity(new Intent(context, LandLine.class));
                }
                else {
                    RegPrefManager.getInstance(context).setDataCardCircle(data.getCircle_name(), data.getCircle_code());

                    context.startActivity(new Intent(context, DataCardActivity.class));
                }
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return operatorList.size();
    }




    class ViewHolder extends RecyclerView.ViewHolder {

        TextView accountTv,banknameTv,ifscTv,nameTv,mobileTv;
        ImageView sendImg,deleteImg;

        ViewHolder(View itemView) {
            super(itemView);

            accountTv = (TextView) itemView.findViewById(R.id.accountTv);
            banknameTv= (TextView) itemView.findViewById(R.id.banknameTv);
            ifscTv= (TextView) itemView.findViewById(R.id.ifscTv);
            nameTv= (TextView) itemView.findViewById(R.id.nameTv);
            mobileTv= (TextView) itemView.findViewById(R.id.mobileTv);

            sendImg=(ImageView)itemView.findViewById(R.id.sendImg);
            deleteImg=(ImageView)itemView.findViewById(R.id.deleteImg);
        }
    }

    //This method will filter the list
    //here we are passing the filtered data
    //and assigning it to the list with notifydatasetchanged method
    public void filterList(ArrayList<BeneficiaryDetailsResponse > filterdNames) {
        this.operatorList = filterdNames;
        notifyDataSetChanged();
    }




}
