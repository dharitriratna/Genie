package ratna.genie1.user.genie.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ratna.genie1.user.genie.ApprovalActivity;
import ratna.genie1.user.genie.ObjectNew.ApproveResponse;
import ratna.genie1.user.genie.ObjectNew.MyWalletData;
import ratna.genie1.user.genie.ObjectNew.RequestAdminList;
import ratna.genie1.user.genie.ObjectNew.RequestData;
import ratna.genie1.user.genie.R;
import ratna.genie1.user.genie.client.ApiClientGenie;
import ratna.genie1.user.genie.client.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by RatnaDev008 on 10/29/2018.
 */

public class RequestApprovalAdapter extends RecyclerView.Adapter<RequestApprovalAdapter.ViewHolder> {

    private ArrayList<RequestAdminList> requestData;
    private Context context;
    ApiInterface apiService;
    ProgressDialog progressDialog;
    public RequestApprovalAdapter(ArrayList<RequestAdminList> requestData, Context context) {
        this.requestData = requestData;
        this.context=context;
        apiService =
                ApiClientGenie.getClient().create(ApiInterface.class);
        progressDialog = new ProgressDialog(context);
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
        holder.approveTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                approveResponse(data.getId());
            }
        });
      //  holder.balanceTv.setText(context.getResources().getString(R.string.rupee)+" "+data.getAmount());

    }

    @Override
    public int getItemCount() {
        return requestData.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView balanceTv,UserName,transactionId,refNo;
        Button approveTv;
        CardView card_view;


        ViewHolder(View itemView) {
            super(itemView);

            UserName = (TextView) itemView.findViewById(R.id.UserName);
            balanceTv = (TextView) itemView.findViewById(R.id.balanceTv);
            transactionId = (TextView) itemView.findViewById(R.id.transactionId);
            refNo = (TextView) itemView.findViewById(R.id.refNo);
            approveTv=itemView.findViewById(R.id.approveTv);
            card_view = itemView.findViewById(R.id.card_view);

        }
    }
    private void approveResponse(String id){
        progressDialog.setMessage("Please wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        Call<ApproveResponse> call = apiService.postApproveResponse(id,"1");
        call.enqueue(new Callback<ApproveResponse>() {
            @Override
            public void onResponse(Call<ApproveResponse> call, Response<ApproveResponse> response) {
                boolean status=response.body().getStatus();
                if(status==true){
                    progressDialog.dismiss();
                    String msg=response.body().getMessage();
                    notifyDataSetChanged();
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                    context.startActivity(new Intent(context, ApprovalActivity.class));
                }
                else {
                    Toast.makeText(context, "Try Again", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApproveResponse> call, Throwable t) {
                Toast.makeText(context, "Try Again", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
