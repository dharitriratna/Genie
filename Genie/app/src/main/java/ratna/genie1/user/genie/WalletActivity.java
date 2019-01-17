package ratna.genie1.user.genie;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ratna.genie1.user.genie.Adapter.BrowsePlansAdapter;
import ratna.genie1.user.genie.Adapter.MobileOperatorsAdapter;
import ratna.genie1.user.genie.Adapter.WalletHistoryAdapter;
import ratna.genie1.user.genie.Model.MobileOperatorsModel;
import ratna.genie1.user.genie.ObjectNew.DatacardResponse;
import ratna.genie1.user.genie.ObjectNew.MyWalletData;
import ratna.genie1.user.genie.ObjectNew.MyWalletResponse;
import ratna.genie1.user.genie.ObjectNew.WalletTotalBalanceResponse;
import ratna.genie1.user.genie.client.ApiClientGenie;
import ratna.genie1.user.genie.client.ApiClientGenie1;
import ratna.genie1.user.genie.client.ApiInterface;
import ratna.genie1.user.genie.helper.RegPrefManager;

import java.util.ArrayList;

import ratna.genie1.user.genie.client.ApiClientGenie;
import ratna.genie1.user.genie.client.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WalletActivity extends AppCompatActivity {
    Toolbar toolbar;
    ProgressDialog progressDialog;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    String login_user="";
    private AlertDialog.Builder alertDialog;
    ApiInterface apiService;
    private TextView balanceTv,noWalletTv,errortext;
    private ImageView walletImg;
    private ArrayList<MyWalletData> dataArrayList;
    ImageView add_money;
    String groupId;
    int i = 0;
    RecyclerView transactiondetailsRecyclerview;
    private WalletHistoryAdapter walletHistoryAdapter;

    private ArrayList<MyWalletData> myWalletData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        groupId = RegPrefManager.getInstance(getApplicationContext()).getUserGroup();
        transactiondetailsRecyclerview=findViewById(R.id.transactiondetailsRecyclerview);

        GridLayoutManager manager = new GridLayoutManager(WalletActivity.this, 1, GridLayoutManager.VERTICAL, false);
        transactiondetailsRecyclerview.setLayoutManager(manager);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (groupId.equals("4")){
                    startActivity(new Intent(getApplicationContext(),MainActivity2.class));
                    finish();
                }
                else if (groupId.equals("5")){
                    startActivity(new Intent(getApplicationContext(),MainActivity3.class));
                    finish();
                }
                else if (groupId.equals("3")){
                    startActivity(new Intent(getApplicationContext(),MainActivity4.class));
                    finish();
                }
                else if (groupId.equals("2")){
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    finish();
                }
            }
        });
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
        intialize();
    }

    private void intialize(){
        walletImg=findViewById(R.id.walletImg);
        balanceTv=findViewById(R.id.balanceTv);
        noWalletTv=findViewById(R.id.noWalletTv);
        add_money = findViewById(R.id.add_money);
        errortext = findViewById(R.id.errortext);
        add_money.setVisibility(View.GONE);
        dataArrayList=new ArrayList<>();
        myWalletData=new ArrayList<>();
        add_money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] options_array = {"Add Money", "Send Money"};

                AlertDialog.Builder builder = new AlertDialog.Builder(WalletActivity.this);
                builder.setTitle("Choose");
                builder.setItems(options_array, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (options_array[item].equals("Add Money")) {
                          //  dialog();

                        } else if (options_array[item].equals("Send Money")) {
                         //   dailog();
                        }
                    }
                });
                builder.show();
            }
        });


        if (isNetworkAvailable()) {
            getWalletBalance();

          //  networkDataCardRecharge();

        } else {
            noNetwrokErrorMessage();
        }
    }

    private void getWalletBalance(){
        progressDialog.setMessage("Please wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        Call<WalletTotalBalanceResponse>call = apiService.postWalletTotalBalance(login_user);
        call.enqueue(new Callback<WalletTotalBalanceResponse>() {
            @Override
            public void onResponse(Call<WalletTotalBalanceResponse> call, Response<WalletTotalBalanceResponse> response) {
                progressDialog.dismiss();
                boolean status = response.body().isStatus();
                if (status==true){
                    String balance = response.body().getTotalWalletBalnce();
                      balanceTv.setText("Balance: "+getResources().getString(R.string.rupee)+ balance);
                      errortext.setVisibility(View.GONE);
                      networkDataCardRecharge();
                }
            }

            @Override
            public void onFailure(Call<WalletTotalBalanceResponse> call, Throwable t) {
                errortext.setVisibility(View.VISIBLE);
                walletImg.setVisibility(View.GONE);
                balanceTv.setVisibility(View.GONE);
            }
        });
    }

    private void networkDataCardRecharge(){
       /* progressDialog.setMessage("Please wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();*/
       // login_user="40";
        Call<MyWalletResponse> call=apiService.postWallet(login_user);

        call.enqueue(new Callback<MyWalletResponse>() {
            @Override
            public void onResponse(Call<MyWalletResponse> call, Response<MyWalletResponse> response) {
                progressDialog.dismiss();
                boolean status=response.body().isStatus();
                if(status==true){

                  //  balanceTv.setText("Balance: "+getResources().getString(R.string.rupee)+ balance);

                    myWalletData=response.body().getData();
                    walletHistoryAdapter = new WalletHistoryAdapter(myWalletData, WalletActivity.this);
                    transactiondetailsRecyclerview.setVisibility(View.VISIBLE);
                    // browsing_plans.setBackgroundColor(R.color.colorPrimaryDark);
                    transactiondetailsRecyclerview.setAdapter(walletHistoryAdapter);
                }else {
                    noWalletTv.setVisibility(View.VISIBLE);
                    walletImg.setVisibility(View.GONE);
                    balanceTv.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<MyWalletResponse> call, Throwable t) {
                progressDialog.dismiss();
                noWalletTv.setVisibility(View.VISIBLE);
                walletImg.setVisibility(View.GONE);
                balanceTv.setVisibility(View.GONE);
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
