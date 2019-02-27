package ratna.genie1.user.genie.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ratna.genie1.user.genie.ApprovalActivity;
import ratna.genie1.user.genie.MainActivity2;
import ratna.genie1.user.genie.ObjectNew.ApproveResponse;
import ratna.genie1.user.genie.ObjectNew.DeleteResponse;
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
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    String login_user="";
    String msg;
    public RequestApprovalAdapter(ArrayList<RequestAdminList> requestData, Context context) {
        this.requestData = requestData;
        this.context=context;
        apiService =
                ApiClientGenie.getClient().create(ApiInterface.class);
        progressDialog = new ProgressDialog(context);
        sharedpreferences = context.getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedpreferences.edit();
        login_user=sharedpreferences.getString("FLAG", "");
        editor.commit(); // commit changes

        Log.d("login_user", login_user);
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
        holder.balanceTv.setText("Phone No - " +data.getPhone());
        holder.transactionId.setText("Trxn Id - " +data.getTransaction_id());
        holder.refNo.setText("Date - " +data.getCreated_date());
        holder.amountTv.setText(context.getResources().getString(R.string.rupee)+ "" + data.getAmount());
        holder.UserName.setText(data.getBusiness_name());
        holder.approveTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   Toast.makeText(context, "msg", Toast.LENGTH_SHORT).show();
                approveResponse(data.getId());
            }
        });

        holder.deleteTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  Toast.makeText(context, "msg", Toast.LENGTH_SHORT).show();
                deleteResponse(data.getId());
            }
        });
      //  holder.balanceTv.setText(context.getResources().getString(R.string.rupee)+" "+data.getAmount());

    }

    @Override
    public int getItemCount() {
        return requestData.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView balanceTv,UserName,transactionId,refNo,amountTv;
        Button approveTv, deleteTv;
        CardView card_view;


        ViewHolder(View itemView) {
            super(itemView);

            UserName = (TextView) itemView.findViewById(R.id.UserName);
            balanceTv = (TextView) itemView.findViewById(R.id.balanceTv);
            transactionId = (TextView) itemView.findViewById(R.id.transactionId);
            refNo = (TextView) itemView.findViewById(R.id.refNo);
            amountTv = (TextView) itemView.findViewById(R.id.amountTv);
            approveTv=(Button) itemView.findViewById(R.id.approveTv);
            deleteTv=(Button) itemView.findViewById(R.id.deleteTv);
            card_view = itemView.findViewById(R.id.card_view);

        }
    }
    private void approveResponse(String id){
        progressDialog.setMessage("Please wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        Call<ApproveResponse> call = apiService.postApproveResponse(login_user,id,"1");
        call.enqueue(new Callback<ApproveResponse>() {
            @Override
            public void onResponse(Call<ApproveResponse> call, Response<ApproveResponse> response) {
                boolean status=response.body().getStatus();
                if(status==true){
                    progressDialog.dismiss();
                    msg =response.body().getMessage();
                    notifyDataSetChanged();
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                    context.startActivity(new Intent(context, MainActivity2.class));

                }
                else {
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApproveResponse> call, Throwable t) {
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void deleteResponse(String id){
        progressDialog.setMessage("Please wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        Call<DeleteResponse> call = apiService.deleteApproveResponse(login_user,id);
        call.enqueue(new Callback<DeleteResponse>() {
            @Override
            public void onResponse(Call<DeleteResponse> call, Response<DeleteResponse> response) {
                boolean status=response.body().getStatus();
                if(status==true){
                    progressDialog.dismiss();
                    msg =response.body().getMessage();
                    notifyDataSetChanged();
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                    context.startActivity(new Intent(context, MainActivity2.class));
                }
                else {
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DeleteResponse> call, Throwable t) {
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
