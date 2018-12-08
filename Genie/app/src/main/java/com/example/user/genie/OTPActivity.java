package com.example.user.genie;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.user.genie.R;
import com.example.user.genie.helper.RegPrefManager;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.user.genie.BeneficiaryDeleteActivity.REQUEST_ID_MULTIPLE_PERMISSIONS;

public class OTPActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private Button verifyBtn;
    EditText otpEd;
    String otp;
    String message;

    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    String login_user="";
    String phoneNo;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OTPActivity.this, SignUp.class));
                finish();
            }
        });
      /*  if (checkAndRequestPermissions()) {
            // carry on the normal flow, as the case of  permissions  granted.
        }
*/
        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedpreferences.edit();
        login_user=sharedpreferences.getString("FLAG", "");
        editor.commit(); // commit changes

        phoneNo = RegPrefManager.getInstance(getApplicationContext()).getPhoneNo();

        Log.d("login_user", login_user);

        otpEd = findViewById(R.id.otpEd);
        verifyBtn = findViewById(R.id.verifyBtn);

        verifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otp = otpEd.getText().toString().trim();

                if (otp.length() < 1){
                    otpEd.setError("Please Enter OTP");
                }
                else {
                    new AsynVerifyOtp().execute();
                }
            }
        });
    }

  /*  private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase("otp")) {
                final String message = intent.getStringExtra("OTP");


                otpEd.setText(message);
            }
        }
    };*/

   /* private  boolean checkAndRequestPermissions() {
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
    }*/


  /*  @Override
    public void onResume() {
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter("otp"));
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }*/
    private class AsynVerifyOtp extends AsyncTask<Void, Void, Void> {
        ProgressDialog pDialog;
        String success = null;
        String status="true";
        @Override
        protected Void doInBackground(Void... params) {
            ArrayList<NameValuePair> cred = new ArrayList<NameValuePair>();
            cred.add(new BasicNameValuePair("otp",otp));
            cred.add(new BasicNameValuePair("user_id",login_user));
            cred.add(new BasicNameValuePair("phone",phoneNo));

            Log.v("RES","Sending data " +otp+login_user+phoneNo);

            String urlRouteList="http://demo.ratnatechnology.co.in/genie/api/user/verify_otp";
            try {
                String route_response = CustomHttpClient.executeHttpPost(urlRouteList, cred);
                Log.v(" ", "Response is " + route_response);

                success = route_response;
                JSONObject jsonObject = new JSONObject(success);
                // status =jsonObject.getString("status");
                message = jsonObject.getString("data");

            } catch (Exception e)

            {
                Log.v("ONMESSAGE", e.toString());

            }return null;
        }

        protected void onPostExecute(Void result) {
            pDialog.dismiss();

            // Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
            if (message.equals("Mobile verified"))
            {
                Toast.makeText(OTPActivity.this, "OTP Verified", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(OTPActivity.this,LogIn.class));
                finish();
            }
            else
            {
                Toast.makeText(OTPActivity.this, "Invalid OTP", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(OTPActivity.this);
            pDialog.setMessage("Verifying...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }
    }

}
