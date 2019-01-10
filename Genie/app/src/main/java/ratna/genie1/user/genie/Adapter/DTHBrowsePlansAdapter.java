package ratna.genie1.user.genie.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ratna.genie1.user.genie.DTHRecharge;
import ratna.genie1.user.genie.MobileRecharge;
import ratna.genie1.user.genie.Model.DTHOperatorsModel;
import ratna.genie1.user.genie.ObjectNew.planDescription;
import ratna.genie1.user.genie.R;
import ratna.genie1.user.genie.helper.RegPrefManager;

import java.util.ArrayList;

/**
 * Created by RatnaDev008 on 10/29/2018.
 */

public class DTHBrowsePlansAdapter extends RecyclerView.Adapter<DTHBrowsePlansAdapter.ViewHolder> {

    private ArrayList<planDescription> plansList;
    private Context context;



    public DTHBrowsePlansAdapter(ArrayList<planDescription> plansList, Context context) {
        this.plansList = plansList;
        this.context=context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.browseplansdetails, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder,final int position) {

        final planDescription data=plansList.get(position);

        holder.planId.setText(data.getId());
        holder.recharge_amount.setText("Amount-"+" "+context.getResources().getString(R.string.rupee)+data.getRecharge_amount());
        holder.shortDesc.setText(data.getRecharge_short_desc());
        holder.talktime.setText("Talktime-"+" "+data.getRecharge_talktime());
        holder.talktime.setVisibility(View.GONE);
        holder.validity.setText("Validity-"+data.getRecharge_validity());
        holder.recharge_type.setText("RC Type-"+data.getRecharge_type());
        holder.descLong.setText(data.getRecharge_long_desc());

        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RegPrefManager.getInstance(context).setMobileRechargeAmount(data.getRecharge_amount());

                context.startActivity(new Intent(context,DTHRecharge.class));
            }
        });

    }

    @Override
    public int getItemCount() {
        return plansList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView planId,recharge_amount,shortDesc,talktime,validity,recharge_type,descLong;
        CardView card_view;

        ViewHolder(View itemView) {
            super(itemView);

            planId = (TextView) itemView.findViewById(R.id.planId);
            recharge_amount = (TextView) itemView.findViewById(R.id.recharge_amount);
            shortDesc= (TextView) itemView.findViewById(R.id.shortDesc);
            talktime= (TextView) itemView.findViewById(R.id.talktime);
            validity= (TextView) itemView.findViewById(R.id.validity);
            recharge_type= (TextView) itemView.findViewById(R.id.recharge_type);
            descLong= (TextView) itemView.findViewById(R.id.descLong);


            card_view=(CardView)itemView.findViewById(R.id.card_view);
        }

    }
    public void filterList(ArrayList<planDescription> filterdNames) {
        this.plansList = filterdNames;
        notifyDataSetChanged();
    }
}
