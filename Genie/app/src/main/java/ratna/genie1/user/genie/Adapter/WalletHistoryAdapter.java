package ratna.genie1.user.genie.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import ratna.genie1.user.genie.MobileRecharge;
import ratna.genie1.user.genie.ObjectNew.MyWalletData;
import ratna.genie1.user.genie.ObjectNew.planDescription;
import ratna.genie1.user.genie.R;
import ratna.genie1.user.genie.helper.RegPrefManager;

/**
 * Created by RatnaDev008 on 10/29/2018.
 */

public class WalletHistoryAdapter extends RecyclerView.Adapter<WalletHistoryAdapter.ViewHolder> {

    private ArrayList<MyWalletData> plansList;
    private Context context;

    public WalletHistoryAdapter(ArrayList<MyWalletData> plansList, Context context) {
        this.plansList = plansList;
        this.context=context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.wallethistory, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder,final int position) {

        final MyWalletData data=plansList.get(position);
        holder.balanceTv.setText("Amount"+context.getResources().getString(R.string.rupee)+" "+data.getBalance());
        holder.shortDesc.setText(data.getSenderFirstName());
        holder.createdonDt.setText(data.getCreated_on());
        holder.createdonDt.setVisibility(View.GONE);
        holder.updatedonDt.setText(data.getUpdated_on());
        holder.service_id.setText("Service Name - " + data.getService_id());
        holder.description.setText("Description - " + data.getDescription());

    }

    @Override
    public int getItemCount() {
        return plansList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView balanceTv,shortDesc,createdonDt,updatedonDt,service_id,description;


        ViewHolder(View itemView) {
            super(itemView);

            balanceTv = (TextView) itemView.findViewById(R.id.balanceTv);
            shortDesc = (TextView) itemView.findViewById(R.id.shortDesc);
            createdonDt = (TextView) itemView.findViewById(R.id.createdonDt);
            updatedonDt = (TextView) itemView.findViewById(R.id.updatedonDt);
            service_id = (TextView) itemView.findViewById(R.id.service_id);
            description = (TextView) itemView.findViewById(R.id.description);

        }
    }
}
