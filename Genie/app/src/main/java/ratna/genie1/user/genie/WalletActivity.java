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
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import ratna.genie1.user.genie.ObjectNew.DatacardResponse;
import ratna.genie1.user.genie.ObjectNew.MyWalletData;
import ratna.genie1.user.genie.ObjectNew.MyWalletResponse;
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
    private TextView balanceTv,noWalletTv;
    private ImageView walletImg;
    private ArrayList<MyWalletData> dataArrayList;
    ImageView add_money;
    String groupId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        groupId = RegPrefManager.getInstance(getApplicationContext()).getUserGroup();
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
        add_money.setVisibility(View.GONE);
        dataArrayList=new ArrayList<>();

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
                            dialog();

                        } else if (options_array[item].equals("Send Money")) {
                            dailog();

                        }
                    }
                });
                builder.show();

            }
        });

      /*  add_money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(WalletActivity.this);
                alertDialogBuilder.setTitle("Add Money");

                alertDialogBuilder
                        .setMessage("Do you want to add money to wallet")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent i = new Intent(WalletActivity.this, AddMoneyActivity.class);
                                startActivity(i);
                                finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // if this button is clicked, just close
                                // the dialog box and do nothing
                                dialog.cancel();
                            }
                        });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();
                // show it
                alertDialog.show();
            }
        });*/

        if (isNetworkAvailable()) {
            networkDataCardRecharge();
        } else {
            noNetwrokErrorMessage();
        }
    }

    private void dailog() {


        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(WalletActivity.this);
        alertDialogBuilder.setTitle("Send Money");

        alertDialogBuilder
                .setMessage("Do you want to send money")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent i = new Intent(WalletActivity.this, AddMoneyActivity.class);
                        startActivity(i);
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        // show it
        alertDialog.show();
    }

    private void dialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(WalletActivity.this);
        alertDialogBuilder.setTitle("Add Money");

        alertDialogBuilder
                .setMessage("Do you want to add money to wallet")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent i = new Intent(WalletActivity.this, RequestWalletActivity.class);
                        startActivity(i);
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        // show it
        alertDialog.show();
    }


    private void networkDataCardRecharge(){
        progressDialog.setMessage("Please wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
       // login_user="40";
        Call<MyWalletResponse> call=apiService.postWallet(login_user);

        call.enqueue(new Callback<MyWalletResponse>() {
            @Override
            public void onResponse(Call<MyWalletResponse> call, Response<MyWalletResponse> response) {
                progressDialog.dismiss();
                boolean status=response.body().isStatus();
                if(status==true){
                    noWalletTv.setVisibility(View.GONE);
                    walletImg.setVisibility(View.VISIBLE);
                    balanceTv.setVisibility(View.VISIBLE);
                    dataArrayList=response.body().getData();
                    String balance="";
                    for (int i=0;i<dataArrayList.size();i++){
                        balance=dataArrayList.get(i).getBalance();
                    }
                    balanceTv.setText("Balance: "+getResources().getString(R.string.rupee)+ balance);
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
