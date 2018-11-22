package com.example.user.genie;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.genie.Model.RemiterDetails;
import com.example.user.genie.MoneyTransfer.MoneyTransferActivity;
import com.example.user.genie.ObjectNew.BeneficiaryDeleteResponse;
import com.example.user.genie.ObjectNew.BeneficiaryDeleteValidateResponse;
import com.example.user.genie.ObjectNew.BeneficiaryValidateResponse;
import com.example.user.genie.client.ApiClientGenie;
import com.example.user.genie.client.ApiClientGenie1;
import com.example.user.genie.client.ApiInterface;
import com.example.user.genie.helper.RegPrefManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BeneficiaryDeleteActivity extends AppCompatActivity implements View.OnClickListener{

    private String beneID,accountName,bankName,ifsc,beneName,beneMobile,beneLastAccess,remitterId;
    private TextView accountTv,banknameTv,ifscTv,nameTv,mobileTv,lastAccessTv,moneyTransferTV,noDataTv;
    private LinearLayout linear;
    private EditText otpTv;
    private ImageView deleteImg;
    Toolbar toolbar;
    private AlertDialog.Builder alertDialog;
    ApiInterface apiService;
    ProgressDialog progressDialog;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    private String otp_name;
    private boolean flag;
    Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beneficiary_delete);
        toolbar = findViewById(R.id.toolbar);
        apiService =
                ApiClientGenie1.getClient().create(ApiInterface.class);
        progressDialog =new ProgressDialog(this);
        alertDialog=new AlertDialog.Builder(this);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BeneficiaryDeleteActivity.this,RemiterDetailsActivity.class));
                finish();
            }
        });
        checkAndRequestPermissions();



        intialize();
    }
    private void intialize(){
        accountTv=findViewById(R.id.accountTv);
        banknameTv=findViewById(R.id.banknameTv);
        ifscTv=findViewById(R.id.ifscTv);
        nameTv=findViewById(R.id.nameTv);
        mobileTv=findViewById(R.id.mobileTv);
        lastAccessTv=findViewById(R.id.lastAccessTv);
        moneyTransferTV=findViewById(R.id.moneyTransferTV);
        deleteImg=findViewById(R.id.deleteImg);

        deleteImg.setOnClickListener(this);
        moneyTransferTV.setOnClickListener(this);

    }

    private  boolean checkAndRequestPermissions() {
        int permissionSendMessage = ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS);
        int receiveSMS = ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS);
        int readSMS = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (receiveSMS != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.RECEIVE_MMS);
        }
        if (readSMS != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_SMS);
        }
        if (permissionSendMessage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.SEND_SMS);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this,
                    listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),
                    REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
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
    @Override
    public void onResume() {

        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter("otp"));
        super.onResume();
        HashMap<String, String> remiter = RegPrefManager.getInstance(this).getRemiterDetails();
        beneID=remiter.get("BeneID");
        accountName=remiter.get("Account");
        bankName=remiter.get("BankName");
        ifsc=remiter.get("IFSC");
        beneName=remiter.get("Name");
        beneMobile=remiter.get("Mobile");
        beneLastAccess=remiter.get("LastAccessDate");
        remitterId=RegPrefManager.getInstance(this).getRemitterId();

        accountTv.setText("A/c: "+accountName);
        banknameTv.setText("Bank Name: "+bankName);
        ifscTv.setText("IFSC code: "+ifsc);
        nameTv.setText("Name: "+beneName);
        mobileTv.setText("Mobile: "+beneMobile);
        lastAccessTv.setText("Last Access: "+beneLastAccess);

    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase("otp")) {
                final String message = intent.getStringExtra("message");
                otp_name=message;
                otp_name=otp_name.replaceAll("[^0-9]","");
                otpTv.setText(otp_name);
                Log.d("TagOTP",otp_name);
            }
        }
    };



    private void DialogShow(){
        flag=true;
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.otpverifylayout);
        dialog.setTitle("Title...");

        // set the custom dialog components - text, image and button
        otpTv = (EditText) dialog.findViewById(R.id.otpTv);
        //resendTv=(TextView)dialog.findViewById(R.id.resendTv);

        Button continueBtn=(Button)dialog.findViewById(R.id.continueBtn);
        // if button is clicked, close the custom dialog
        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                validateNetwork(); // validate beneficiary
               /*startActivity(new Intent(AddBeneficiaryActivity.this,RemiterDetailsActivity.class));
               finish();*/
            }
        });

       /* resendTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

                networkResend();
            }
        });
*/
        dialog.show();
    }
    private void deleteNetwork(){
        progressDialog.setMessage("Please wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        Call<BeneficiaryDeleteResponse> call=apiService.postBeneficiaryDelete(remitterId,beneID);
        call.enqueue(new Callback<BeneficiaryDeleteResponse>() {
            @Override
            public void onResponse(Call<BeneficiaryDeleteResponse> call, Response<BeneficiaryDeleteResponse> response) {
                progressDialog.dismiss();
                boolean status=response.body().isStatus();
                if(status==true){
                    DialogShow();
                   //Toast.makeText(getApplicationContext(),"Successfully Delete.",Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(getApplicationContext(),"Try again After Sometimes.",Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<BeneficiaryDeleteResponse> call, Throwable t) {
            progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),"Try again After Sometimes.",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void validateNetwork(){
        progressDialog.setMessage("Please wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        Call<BeneficiaryDeleteValidateResponse> call=apiService.postBeneficiaryDeleteValidate(remitterId,beneID,otp_name);

        call.enqueue(new Callback<BeneficiaryDeleteValidateResponse>() {
            @Override
            public void onResponse(Call<BeneficiaryDeleteValidateResponse> call, Response<BeneficiaryDeleteValidateResponse> response) {
                progressDialog.dismiss();
                boolean status=response.body().isStatus();
                if (status==true){
                    String statuss=response.body().getData().getStatus();
                    Toast.makeText(getApplicationContext(),statuss,Toast.LENGTH_LONG).show();
                    startActivity(new Intent(BeneficiaryDeleteActivity.this,RemiterDetailsActivity.class));
                    finish();
                }else {
                    Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<BeneficiaryDeleteValidateResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.deleteImg:
                if (isNetworkAvailable()) {
                    deleteNetwork(); //register add beneficiary
                }
                else {
                    noNetwrokErrorMessage();
                }
                break;
            case R.id.moneyTransferTV:
                startActivity(new Intent(BeneficiaryDeleteActivity.this, MoneyTransferActivity.class));
                finish();
                break;
        }
    }
}
