package ratna.genie1.user.genie.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import ratna.genie1.user.genie.ObjectNew.MyWalletData;
import ratna.genie1.user.genie.ObjectNew.RequestAdminList;
import ratna.genie1.user.genie.ObjectNew.RequestData;
import ratna.genie1.user.genie.R;

/**
 * Created by RatnaDev008 on 10/29/2018.
 */

public class RequestApprovalAdapter extends RecyclerView.Adapter<RequestApprovalAdapter.ViewHolder> {

    private ArrayList<RequestAdminList> requestData;
    private Context context;

    public RequestApprovalAdapter(ArrayList<RequestAdminList> requestData, Context context) {
        this.requestData = requestData;
        this.context=context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.approvehistory, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder,final int position) {

        final RequestAdminList data=requestData.get(position);
        holder.balanceTv.setText(context.getResources().getString(R.string.rupee)+" "+data.getAmount());
        holder.transactionId.setText(data.getTransaction_id());
        holder.refNo.setText(data.getRef_no());
        holder.UserName.setText(data.getRequesterName());
      //  holder.balanceTv.setText(context.getResources().getString(R.string.rupee)+" "+data.getAmount());

    }

    @Override
    public int getItemCount() {
        return requestData.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView balanceTv,UserName,transactionId,refNo;


        ViewHolder(View itemView) {
            super(itemView);

            UserName = (TextView) itemView.findViewById(R.id.UserName);
            balanceTv = (TextView) itemView.findViewById(R.id.balanceTv);
            transactionId = (TextView) itemView.findViewById(R.id.transactionId);
            refNo = (TextView) itemView.findViewById(R.id.refNo);

        }
    }
}
