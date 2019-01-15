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
        holder.balanceTv.setText(context.getResources().getString(R.string.rupee)+" "+data.getBalance());

    }

    @Override
    public int getItemCount() {
        return plansList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView balanceTv;


        ViewHolder(View itemView) {
            super(itemView);

            balanceTv = (TextView) itemView.findViewById(R.id.balanceTv);

        }
    }
}
