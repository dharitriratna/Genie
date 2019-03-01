package ratna.genie1.user.genie.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import ratna.genie1.user.genie.BeneficiaryDeleteActivity;
import ratna.genie1.user.genie.MobileRecharge;
import ratna.genie1.user.genie.Model.BeneficiaryDetailsResponse;
import ratna.genie1.user.genie.ObjectNew.planDescription;
import ratna.genie1.user.genie.R;
import ratna.genie1.user.genie.helper.RegPrefManager;

import java.util.ArrayList;

import ratna.genie1.user.genie.MobileRecharge;
import ratna.genie1.user.genie.ObjectNew.planDescription;
import ratna.genie1.user.genie.helper.RegPrefManager;

/**
 * Created by RatnaDev008 on 10/29/2018.
 */

public class BrowsePlansAdapter extends RecyclerView.Adapter<BrowsePlansAdapter.ViewHolder> {

    private ArrayList<planDescription> plansList;
    private Context context;

    public BrowsePlansAdapter(ArrayList<planDescription> plansList, Context context) {
        this.plansList = plansList;
        this.context=context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(ratna.genie1.user.genie.R.layout.browseplansdetails, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder,final int position) {

        final planDescription data=plansList.get(position);

        holder.planId.setText(data.getId());
        holder.recharge_amount.setText("Amount-"+" "+context.getResources().getString(ratna.genie1.user.genie.R.string.rupee)+data.getRecharge_amount());
        holder.shortDesc.setText(data.getRecharge_short_desc());
        holder.talktime.setText("Talktime-"+" "+data.getRecharge_talktime());
        holder.validity.setText("Validity-"+data.getRecharge_validity());
        holder.recharge_type.setText("RC Type-"+data.getRecharge_type());
        holder.descLong.setText(data.getRecharge_long_desc());

        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    RegPrefManager.getInstance(context).setMobileRechargeAmount(data.getRecharge_amount());
                    Intent myIntent = new Intent(context,MobileRecharge.class);
                    myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(myIntent);
                  //  context.startActivity(new Intent(context,MobileRecharge.class));
                }catch (RuntimeException ex){
                    ex.printStackTrace();
                }
                //  ((Activity)context).finish();
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

            planId = (TextView) itemView.findViewById(ratna.genie1.user.genie.R.id.planId);
            recharge_amount = (TextView) itemView.findViewById(ratna.genie1.user.genie.R.id.recharge_amount);
            shortDesc= (TextView) itemView.findViewById(ratna.genie1.user.genie.R.id.shortDesc);
            talktime= (TextView) itemView.findViewById(ratna.genie1.user.genie.R.id.talktime);
            validity= (TextView) itemView.findViewById(ratna.genie1.user.genie.R.id.validity);
            recharge_type= (TextView) itemView.findViewById(ratna.genie1.user.genie.R.id.recharge_type);
            descLong= (TextView) itemView.findViewById(ratna.genie1.user.genie.R.id.descLong);


            card_view=(CardView)itemView.findViewById(ratna.genie1.user.genie.R.id.card_view);
        }
    }
}
