package com.example.user.genie;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.user.genie.helper.RegPrefManager;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;

public class AddMoneyActivity extends AppCompatActivity {
    Toolbar toolbar;
    EditText senderPhone,amountTv,otpTv;
    Button btnProceed, btnSend;
    String group_id;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    String login_user="";

    String SenderNumber, SendingAmount, OTPcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_money);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        senderPhone = findViewById(R.id.senderPhone);
        amountTv = findViewById(R.id.amountTv);
        otpTv = findViewById(R.id.otpTv);
        btnProceed = findViewById(R.id.btnProceed);
        btnSend = findViewById(R.id.btnSend);

        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedpreferences.edit();
        login_user=sharedpreferences.getString("FLAG", "");
        editor.commit(); //

        group_id = RegPrefManager.getInstance(AddMoneyActivity.this).getUserGroup();

        btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SenderNumber = senderPhone.getText().toString().trim();
                SendingAmount = amountTv.getText().toString().trim();

                if (SenderNumber.length() < 1){
                    senderPhone.setText("Please Enter Number");
                }
                else if (SendingAmount.length() < 1){
                    amountTv.setText("Please Enter Amount");
                }
                else {
                    new Asynctask().execute();
                }
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OTPcode = otpTv.getText().toString().trim();

                if (OTPcode.length() < 1){
                    otpTv.setError("Please enter OTP");
                }
                else {
                    new AsyncSubmit().execute();
                }
            }
        });
    }


    @SuppressLint("StaticFieldLeak")
    private class Asynctask extends AsyncTask<Void, Void, Void> {
        ProgressDialog pDialog;
        String success = null,message="",status="true";

        @Override
        protected Void doInBackground(Void... params) {
            pDialog.show();
            ArrayList<NameValuePair> cred = new ArrayList<NameValuePair>();
            cred.add(new BasicNameValuePair("user_id",login_user));
            cred.add(new BasicNameValuePair("grp",group_id));//user_email
            cred.add(new BasicNameValuePair("senderPhone",SenderNumber ));
            cred.add(new BasicNameValuePair("amount",SendingAmount ));
            Log.v("RES","Sending data " +login_user+ group_id+ SenderNumber +SendingAmount );

            String urlRouteList="https://genieservice.in/api/user/walletFundTransfer";
            try {
                String route_response = CustomHttpClient.executeHttpPost(urlRouteList, cred);

                success = route_response;
                JSONObject jsonObject = new JSONObject(success);

                // user_email=jsonObject.getString("user_email");
                status=jsonObject.getString("status");
                if(status.equals("false")) {
                    message = jsonObject.getString("data");
                }
                String data=jsonObject.getString("data");
                // Toast.makeText(Cart.this, sum_total, Toast.LENGTH_SHORT).show();
                JSONObject jsonObject1 = new JSONObject(data);
               /* String user_id=jsonObject1.getString("user_id");
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("FLAG", user_id);
                Log.d("user_id", user_id);
                editor.commit();
                String user_phone = jsonObject1.getString("phone");
                RegPrefManager.getInstance(AddMoneyActivity.this).setPhoneNo(user_phone);*/
                //   String user_email=jsonObject1.getString("user_email");

            } catch (Exception e)

            {
                Log.v("Connection error", e.toString());

            }return null;
        }
        protected void onPostExecute(Void result) {
            pDialog.dismiss();

            if(status.equals("true"))
            {
                Toast.makeText(getApplicationContext(),"Check OTP", Toast.LENGTH_LONG).show();
                otpTv.setVisibility(View.VISIBLE);
                btnSend.setVisibility(View.VISIBLE);
                btnProceed.setVisibility(View.GONE);
             //   startActivity(new Intent(getApplicationContext(),VerifyWalletOTPActivity.class));
               // finish();

            //    startActivity(new Intent(AddMoneyActivity.this,OTPActivity.class));finish();
            }
            else{
                Toast.makeText(getApplicationContext(),message, Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(AddMoneyActivity.this);
            pDialog.setMessage("Loading In...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
    }


    @SuppressLint("StaticFieldLeak")
    private class AsyncSubmit extends AsyncTask<Void, Void, Void> {
        ProgressDialog pDialog;
        String success = null,message="",status="true";

        @Override
        protected Void doInBackground(Void... params) {
            pDialog.show();
            ArrayList<NameValuePair> cred = new ArrayList<NameValuePair>();
            cred.add(new BasicNameValuePair("user_id",login_user));
            cred.add(new BasicNameValuePair("otp",OTPcode));//user_email
            cred.add(new BasicNameValuePair("senderPhone",SenderNumber ));
            cred.add(new BasicNameValuePair("amount",SendingAmount ));
            Log.v("RES","Sending data " +login_user+ OTPcode+ SenderNumber +SendingAmount );

            String urlRouteList="https://genieservice.in/api/user/verifyWallet_otp";
            try {
                String route_response = CustomHttpClient.executeHttpPost(urlRouteList, cred);

                success = route_response;
                JSONObject jsonObject = new JSONObject(success);

                // user_email=jsonObject.getString("user_email");
                status=jsonObject.getString("status");
                if(status.equals("false")) {
                    message = jsonObject.getString("data");
                }
                String data=jsonObject.getString("data");
                // Toast.makeText(Cart.this, sum_total, Toast.LENGTH_SHORT).show();
                JSONObject jsonObject1 = new JSONObject(data);
               /* String user_id=jsonObject1.getString("user_id");
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("FLAG", user_id);
                Log.d("user_id", user_id);
                editor.commit();
                String user_phone = jsonObject1.getString("phone");
                RegPrefManager.getInstance(AddMoneyActivity.this).setPhoneNo(user_phone);*/
                //   String user_email=jsonObject1.getString("user_email");

            } catch (Exception e)

            {
                Log.v("Connection error", e.toString());

            }return null;
        }
        protected void onPostExecute(Void result) {
            pDialog.dismiss();

            if(status.equals("true"))
            {
                Toast.makeText(getApplicationContext(),"Amount Transferred", Toast.LENGTH_LONG).show();
                //   startActivity(new Intent(getApplicationContext(),VerifyWalletOTPActivity.class));
                otpTv.setVisibility(View.VISIBLE);
                btnSend.setVisibility(View.VISIBLE);
                btnProceed.setVisibility(View.GONE);

                //    startActivity(new Intent(AddMoneyActivity.this,OTPActivity.class));finish();
            }
            else{
                Toast.makeText(getApplicationContext(),message, Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(AddMoneyActivity.this);
            pDialog.setMessage("Loading In...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
    }

}
