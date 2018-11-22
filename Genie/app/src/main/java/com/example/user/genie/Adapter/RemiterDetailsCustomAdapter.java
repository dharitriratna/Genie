package com.example.user.genie.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.genie.BeneficiaryDeleteActivity;
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

        holder.accountTv.setText("A/c: "+data.getAccount());
        holder.banknameTv.setText(data.getBank());
        holder.ifscTv.setText(data.getIfsc());
        holder.nameTv.setText("Name: "+data.getName());
        holder.mobileTv.setText("Mobile: "+data.getMobile());


        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            RegPrefManager.getInstance(context).setRemiterDetails(data.getId(),data.getAccount(),
                    data.getBank(),data.getIfsc(),data.getName(),data.getMobile(),data.getLast_success_date());

             context.startActivity(new Intent(context, BeneficiaryDeleteActivity.class));

            }
        });
    }

    @Override
    public int getItemCount() {
        return operatorList.size();
    }




    class ViewHolder extends RecyclerView.ViewHolder {

        TextView accountTv,banknameTv,ifscTv,nameTv,mobileTv;
        ImageView sendImg,deleteImg;
        CardView card_view;

        ViewHolder(View itemView) {
            super(itemView);

            accountTv = (TextView) itemView.findViewById(R.id.accountTv);
            banknameTv= (TextView) itemView.findViewById(R.id.banknameTv);
            ifscTv= (TextView) itemView.findViewById(R.id.ifscTv);
            nameTv= (TextView) itemView.findViewById(R.id.nameTv);
            mobileTv= (TextView) itemView.findViewById(R.id.mobileTv);

            sendImg=(ImageView)itemView.findViewById(R.id.sendImg);
            deleteImg=(ImageView)itemView.findViewById(R.id.deleteImg);
            card_view=(CardView)itemView.findViewById(R.id.card_view);
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
