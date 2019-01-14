package ratna.genie1.user.genie;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
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
import android.widget.TextView;
import android.widget.Toast;

import ratna.genie1.user.genie.helper.RegPrefManager;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ratna.genie1.user.genie.helper.RegPrefManager;

public class AddMoneyActivity extends AppCompatActivity {
    Toolbar toolbar;
    EditText senderPhone,amountTv,otpTv;
    Button btnProceed, btnSend;
    String group_id;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    String login_user="";
    private AlertDialog.Builder alertDialog;
    String SenderNumber, SendingAmount, OTPcode;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_money);
        checkAndRequestPermissions();
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
        alertDialog=new AlertDialog.Builder(this);
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
                    if (isNetworkAvailable()) {
                        new Asynctask().execute();

                    }
                    else {
                        noNetwrokErrorMessage();
                    }

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
                    if (isNetworkAvailable()) {
                        new AsyncSubmit().execute();
                    }
                    else {
                        noNetwrokErrorMessage();
                    }

                }
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

    @SuppressLint("StaticFieldLeak")
    private class Asynctask extends AsyncTask<Void, Void, Void> {
        ProgressDialog pDialog;
        String success = null,message="",status;

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
           try {

                if (status.equals("true")) {
                    Toast.makeText(getApplicationContext(), "Check OTP", Toast.LENGTH_LONG).show();
                    otpTv.setVisibility(View.VISIBLE);
                    btnSend.setVisibility(View.VISIBLE);
                    btnProceed.setVisibility(View.GONE);
                    senderPhone.setEnabled(false);
                    amountTv.setEnabled(false);

                    //   startActivity(new Intent(getApplicationContext(),VerifyWalletOTPActivity.class));
                    // finish();

                    //    startActivity(new Intent(AddMoneyActivity.this,OTPActivity.class));finish();
                } else {
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                }
            }
            catch(Exception e) {
                Toast.makeText(getApplicationContext(), "Genie is away! Try after sometime", Toast.LENGTH_LONG).show();
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
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase("otp")) {
                final String message = intent.getStringExtra("message");


                otpTv.setText(message);
            }
        }
    };


    @Override
    public void onResume() {
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter("otp"));
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }

}
