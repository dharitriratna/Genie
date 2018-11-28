package com.example.user.genie;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.genie.ObjectNew.CabResponse;
import com.example.user.genie.ObjectNew.DatacardResponse;
import com.example.user.genie.ObjectNew.InsurancePaymentResponse;
import com.example.user.genie.ObjectNew.LandlineResponse;
import com.example.user.genie.client.ApiClientGenie;
import com.example.user.genie.client.ApiClientGenie1;
import com.example.user.genie.client.ApiInterface;
import com.example.user.genie.helper.RegPrefManager;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentCartActivity extends AppCompatActivity implements View.OnClickListener {
    Toolbar toolbar;
    private AlertDialog.Builder alertDialog;
    ApiInterface apiService;
    TextView servicenameTv,mobileTv,amountpTv,walletpTv;
    CheckBox checkBox1;
    String back;
    Button btn_order;
    ProgressDialog progressDialog;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    String login_user="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_cart);
        toolbar = findViewById(R.id.toolbar);
        apiService =
                ApiClientGenie1.getClient().create(ApiInterface.class);
        progressDialog =new ProgressDialog(this);
        alertDialog=new AlertDialog.Builder(this);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);

          back= RegPrefManager.getInstance(this).getBackService();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(back.equals("Insurance")){
                    startActivity(new Intent(PaymentCartActivity.this,InsuranceDetailsActivity.class));
                    finish();
                }
                else if(back.equals("Landline")){
                    startActivity(new Intent(PaymentCartActivity.this,LandLine.class));
                    finish();
                }else if(back.equals("Tour")){
                    startActivity(new Intent(PaymentCartActivity.this,CabBookingActivity.class));
                    finish();
                }
                else if(back.equals("Datacard")){
                    startActivity(new Intent(PaymentCartActivity.this,DataCardActivity.class));
                    finish();
                }

            }
        });
        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor1 = sharedpreferences.edit();
        login_user=sharedpreferences.getString("FLAG", "");
        editor1.commit(); // commit changes


        Log.d("login_user", login_user);

        intialize();
    }
    private void intialize(){
        servicenameTv=findViewById(R.id.servicenameTv);
        mobileTv=findViewById(R.id.mobileTv);
        amountpTv=findViewById(R.id.amountpTv);
        walletpTv=findViewById(R.id.walletpTv);
        checkBox1=findViewById(R.id.checkBox1);
        btn_order=findViewById(R.id.btn_order);
        btn_order.setOnClickListener(this);




    }

    @Override
    public void onResume(){
        super.onResume();
        if(back.equals("Insurance")){
            servicenameTv.setText(RegPrefManager.getInstance(this).getServiceName());
            mobileTv.setText(""+RegPrefManager.getInstance(this).getPolicyId());
            amountpTv.setText(RegPrefManager.getInstance(this).getServiceAmount());
        }

        else if(back.equals("Landline")){
            servicenameTv.setText(RegPrefManager.getInstance(this).getServiceName());
            mobileTv.setText(RegPrefManager.getInstance(this).getLandlineBroadbandoperatorid());
            amountpTv.setText(RegPrefManager.getInstance(this).getLandlineBroadbandamount());

        }else if(back.equals("Tour")){
            servicenameTv.setText(RegPrefManager.getInstance(this).getServiceName());
            mobileTv.setText(RegPrefManager.getInstance(this).getCABFORM()+" - "+RegPrefManager.getInstance(this).getCABTO());

        }else   if(back.equals("Datacard")) {
            servicenameTv.setText(RegPrefManager.getInstance(this).getServiceName());
            mobileTv.setText(RegPrefManager.getInstance(this).getDatacardCus());
            amountpTv.setText(RegPrefManager.getInstance(this).getDatacardAmount());
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_order:
                if(back.equals("Insurance")){
                    if (isNetworkAvailable()) {
                        InsurancePayment();
                    } else {
                        noNetwrokErrorMessage();
                    }

                }
                if(back.equals("Landline")){
                    if (isNetworkAvailable()) {
                        networkLandlineService();
                    } else {
                        noNetwrokErrorMessage();
                    }
                }
                if (back.equals("Tour")){
                    if (isNetworkAvailable()) {
                        networkCabService();

                    } else {
                        noNetwrokErrorMessage();
                    }
                }
                if(back.equals("Datacard")) {
                    if (isNetworkAvailable()) {
                        networkDataCardRecharge();
                    } else {
                        noNetwrokErrorMessage();
                    }
                }
                break;
        }
    }
    private void InsurancePayment(){
        progressDialog.setMessage("Please wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        String policyno=RegPrefManager.getInstance(this).getPolicyId();
        String reqid=RegPrefManager.getInstance(this).getReqId();
        Call<InsurancePaymentResponse> call=apiService.postInsurancePayment(login_user,reqid,policyno);
        call.enqueue(new Callback<InsurancePaymentResponse>() {
            @Override
            public void onResponse(Call<InsurancePaymentResponse> call, Response<InsurancePaymentResponse> response) {
                progressDialog.dismiss();
                String status=response.body().getData().getStatus();
                if(status.equals("success")){
                    RegPrefManager.getInstance(PaymentCartActivity.this).setInsuranceMessage(response.body().getData().getMessage());
                    //  Toast.makeText(getApplicationContext(),response.body().getData().getMessage(),Toast.LENGTH_LONG).show();
                    startActivity(new Intent(PaymentCartActivity.this,ThankuActivity.class));
                    finish();
                }
                else {
                    progressDialog.dismiss();
                    startActivity(new Intent(PaymentCartActivity.this,FailureActivity.class));
                    finish();
                    Toast.makeText(getApplicationContext(),"Failed.Try again!",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<InsurancePaymentResponse> call, Throwable t) {
                progressDialog.dismiss();
                startActivity(new Intent(PaymentCartActivity.this,FailureActivity.class));
                finish();
                Toast.makeText(getApplicationContext(),"Failed.Try again!",Toast.LENGTH_LONG).show();
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

    private void networkLandlineService(){
        progressDialog.setMessage("Please wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        String customer_id=RegPrefManager.getInstance(this).getLandlineBroadbandCustid();
        String operator=RegPrefManager.getInstance(this).getLandlineBroadbandoperatorid();
        String back= RegPrefManager.getInstance(this).getBack();
        String circle;
        if(back.equals("Landline1"))
        {
            circle=RegPrefManager.getInstance(this).getLandlinecirclecode();
        }else {
            circle=RegPrefManager.getInstance(this).gettDDataCardCircleCode();
        }
        // String circle=circleTv.getText().toString();
        String accountno=RegPrefManager.getInstance(this).getLandlineBroadbandaccountno();
        String amount=RegPrefManager.getInstance(this).getLandlineBroadbandamount();
        String std="00";

        Call<LandlineResponse> call=apiService.postLandlineRecharge(Integer.parseInt(login_user),
                customer_id,operator,Integer.parseInt(circle),Integer.parseInt(amount),accountno,std);

        call.enqueue(new Callback<LandlineResponse>() {
            @Override
            public void onResponse(Call<LandlineResponse> call, Response<LandlineResponse> response) {
                progressDialog.dismiss();
                String status=response.body().getData().getStatus();
                if(status.equals("FAILURE")){
                    startActivity(new Intent(PaymentCartActivity.this,FailureActivity.class));
                    finish();
                }
                else {
                    startActivity(new Intent(PaymentCartActivity.this,ThankuActivity.class));
                    finish();
                }
             //  Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call<LandlineResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),"Recharge Failed, Try again",Toast.LENGTH_LONG).show();
                startActivity(new Intent(PaymentCartActivity.this,FailureActivity.class));
                finish();
            }
        });

    }

    private void networkCabService(){
        progressDialog.setMessage("Loading");
        progressDialog.show();

        Call<CabResponse> call=apiService.postCabBooking(Integer.parseInt(login_user),Integer.parseInt(
                RegPrefManager.getInstance(this).getCABTRIPVALUE()),
                RegPrefManager.getInstance(this).getCABFORM(),
                RegPrefManager.getInstance(this).getCABTO(),
                RegPrefManager.getInstance(this).getCABTRAVELDATE(),
                RegPrefManager.getInstance(this).getCABRETURNDATE(),
                RegPrefManager.getInstance(this).getCABTIME());
        call.enqueue(new Callback<CabResponse>() {
            @Override
            public void onResponse(Call<CabResponse> call, Response<CabResponse> response) {
                progressDialog.dismiss();
                boolean status=response.body().isStatus();
                String data=response.body().getData();
                Toast.makeText(getApplicationContext(),data,Toast.LENGTH_SHORT).show();
                RegPrefManager.getInstance(PaymentCartActivity.this).setBack("CabBook");
                startActivity(new Intent(PaymentCartActivity.this,ThankuActivity.class));
                finish();
                /*fromEd.getText().clear();
                toEd.getText().clear();
                deppEd.getText().clear();
                depEd.getText().clear();
                arrivalEd.getText().clear();
                timeEd.getText().clear();*/
                //  Log.d("Tag","sta");

            }

            @Override
            public void onFailure(Call<CabResponse> call, Throwable t) {
                progressDialog.dismiss();
                startActivity(new Intent(PaymentCartActivity.this,FailureActivity.class));
                finish();
                Log.d("Tag","Failure");
            }
        });
    }

    private void networkDataCardRecharge(){
        progressDialog.setMessage("Please wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        String v=RegPrefManager.getInstance(this).getDatacardCus();
        Log.d("Tag:value",v);
        String cus=RegPrefManager.getInstance(this).getDatacardCus();

        int amount=Integer.parseInt(RegPrefManager.getInstance(this).getDatacardAmount());
        //if (prepaid.isChecked()) {
          int  circlecode = Integer.parseInt(RegPrefManager.getInstance(this).gettDDataCardCircleCode());
        //}else {
        //    circlecode=0;
        //}

        String operatorcode=RegPrefManager.getInstance(this).getDataCardOperatorCode();
        Call<DatacardResponse> call=apiService.postDatacardRecharge(Integer.parseInt(login_user),cus,operatorcode,circlecode,amount);
        call.enqueue(new Callback<DatacardResponse>() {
            @Override
            public void onResponse(Call<DatacardResponse> call, Response<DatacardResponse> response) {
                progressDialog.dismiss();
                String id=response.body().getData().getApiTransID();
                String status=response.body().getData().getStatus();
                if (status.equals("Failure")){
                    startActivity(new Intent(PaymentCartActivity.this,FailureActivity.class));
                    finish();
                }else {

                    Toast.makeText(getApplicationContext(), status, Toast.LENGTH_LONG).show();
                    startActivity(new Intent(PaymentCartActivity.this, ThankuActivity.class));
                    finish();
                }


            }

            @Override
            public void onFailure(Call<DatacardResponse> call, Throwable t) {
                progressDialog.dismiss();
                startActivity(new Intent(PaymentCartActivity.this,FailureActivity.class));
                finish();
                Toast.makeText(getApplicationContext(),"Failure",Toast.LENGTH_LONG).show();
            }
        });
    }
}
