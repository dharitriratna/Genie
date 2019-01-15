package ratna.genie1.user.genie;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import ratna.genie1.user.genie.Adapter.RequestApprovalAdapter;
import ratna.genie1.user.genie.Adapter.WalletHistoryAdapter;
import ratna.genie1.user.genie.ObjectNew.MyWalletData;
import ratna.genie1.user.genie.ObjectNew.RequestAdminList;
import ratna.genie1.user.genie.ObjectNew.RequestData;
import ratna.genie1.user.genie.ObjectNew.RequestResponse;
import ratna.genie1.user.genie.ObjectNew.WalletTotalBalanceResponse;
import ratna.genie1.user.genie.client.ApiClientGenie;
import ratna.genie1.user.genie.client.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApprovalActivity extends AppCompatActivity {
    RecyclerView requestRecyclerview;
    Toolbar toolbar;
    private RequestApprovalAdapter requestApprovalAdapter;
    private AlertDialog.Builder alertDialog;
    ApiInterface apiService;
    private ArrayList<RequestAdminList> requestDataArraylist;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    String login_user="";
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approval);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        requestDataArraylist = new ArrayList<>();

        requestRecyclerview = findViewById(R.id.requestRecyclerview);
        GridLayoutManager manager = new GridLayoutManager(ApprovalActivity.this, 1, GridLayoutManager.VERTICAL, false);
        requestRecyclerview.setLayoutManager(manager);

        apiService =
                ApiClientGenie.getClient().create(ApiInterface.class);
        progressDialog =new ProgressDialog(this);
        alertDialog=new AlertDialog.Builder(this);
        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor1 = sharedpreferences.edit();
        login_user=sharedpreferences.getString("FLAG", "");
        editor1.commit(); // commit changes


        Log.d("login_user", login_user);

        if (isNetworkAvailable()) {
            getRequestWalletBalance();

            //  networkDataCardRecharge();

        } else {
            noNetwrokErrorMessage();
        }
    }


    private void getRequestWalletBalance(){
        progressDialog.setMessage("Please wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        Call<RequestResponse> call = apiService.getRequestResponse(Integer.parseInt(login_user));
        call.enqueue(new Callback<RequestResponse>() {
            @Override
            public void onResponse(Call<RequestResponse> call, Response<RequestResponse> response) {
                boolean status=response.body().isStatus();
                if(status==true){
                    progressDialog.dismiss();

                    //  balanceTv.setText("Balance: "+getResources().getString(R.string.rupee)+ balance);
                    requestDataArraylist=new ArrayList<>();
                    requestDataArraylist=response.body().getData();

                        requestApprovalAdapter = new RequestApprovalAdapter(requestDataArraylist, ApprovalActivity.this);
                        requestRecyclerview.setVisibility(View.VISIBLE);
                        // browsing_plans.setBackgroundColor(R.color.colorPrimaryDark);
                        requestRecyclerview.setAdapter(requestApprovalAdapter);

                }else {
                    Toast.makeText(ApprovalActivity.this, "No Data Found", Toast.LENGTH_SHORT).show();
                 /*   noWalletTv.setVisibility(View.VISIBLE);
                    walletImg.setVisibility(View.GONE);
                    balanceTv.setVisibility(View.GONE);*/
                }
            }

            @Override
            public void onFailure(Call<RequestResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(ApprovalActivity.this, "Try Again", Toast.LENGTH_SHORT).show();


            }
        });
    }

    public boolean isNetworkAvailable(){
        ConnectivityManager connectivityManager= (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    public void noNetwrokErrorMessage(){
        alertDialog.setTitle("Error!");
        alertDialog.setMessage("No internet connection. Please check your internet setting.");
        alertDialog.setCancelable(true);
        alertDialog.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alert=alertDialog.create();
        alert.show();

    }
}
