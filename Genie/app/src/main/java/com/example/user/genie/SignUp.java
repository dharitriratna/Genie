package com.example.user.genie;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SignUp extends AppCompatActivity {
    Toolbar toolbar;
    EditText full_name, email_id, phone_no, address, password;
    String fullName, email, mobile_no, userAddress, userPassword;
    Button btn_signup;
    String message = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        full_name = findViewById(R.id.full_name);
        email_id = findViewById(R.id.email_id);
        phone_no = findViewById(R.id.phone_no);
        address = findViewById(R.id.address);
        password = findViewById(R.id.password);
        btn_signup = findViewById(R.id.btn_signup);

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fullName = full_name.getText().toString().trim();
                email = email_id.getText().toString().trim();
                mobile_no = phone_no.getText().toString().trim();
                userAddress = address.getText().toString().trim();
                userPassword = password.getText().toString().trim();

                if (fullName.length()<1){
                    full_name.setError("Enter Your Name");
                }
                else if (email.length()<1){
                    email_id.setError("Enter Your Email");
                }
                else if (mobile_no.length()<1){
                    phone_no.setError("Enter Your Phone Number");
                }

                else if (userPassword.length()<1){
                    password.setError("Enter Your Password");
                }
                else {
                   new AsynSignInDetails().execute();
                //    upload();
                   // startActivity(new Intent(SignUp.this,MainActivity.class));
              }
            }
        });
    }

    private class AsynSignInDetails extends AsyncTask<Void, Void, Void> {
        ProgressDialog pDialog;
        String success = null,message="",status="true";

        @Override
        protected Void doInBackground(Void... params) {
            pDialog.show();
            ArrayList<NameValuePair> cred = new ArrayList<NameValuePair>();
            cred.add(new BasicNameValuePair("first_name",fullName));//user_email
            cred.add(new BasicNameValuePair("email",email ));
            cred.add(new BasicNameValuePair("phone",mobile_no ));
            cred.add(new BasicNameValuePair("password",userPassword ));
            Log.v("RES","Sending data " +fullName+ email+ mobile_no +userPassword );

            String urlRouteList="http://demo.ratnatechnology.co.in/genie/index.php/api/user/register";
            try {
                String route_response = CustomHttpClient.executeHttpPost(urlRouteList, cred);

                success = route_response;
                JSONObject jsonObject = new JSONObject(success);

                // user_email=jsonObject.getString("user_email");
                status=jsonObject.getString("status");
                if(status.equals("false")) {
                    message = jsonObject.getString("message");
                }
                String data=jsonObject.getString("data");
                // Toast.makeText(Cart.this, sum_total, Toast.LENGTH_SHORT).show();
                JSONObject jsonObject1 = new JSONObject(data);
                String user_id=jsonObject1.getString("user_id");
                String user_email=jsonObject1.getString("user_email");

            } catch (Exception e)

            {
                Log.v("Connection error", e.toString());

            }return null;
        }
        protected void onPostExecute(Void result) {
            pDialog.dismiss();

            if(status.equals("true"))
            {
                Toast.makeText(getApplicationContext(),"Registration Successful", Toast.LENGTH_LONG).show();

                startActivity(new Intent(SignUp.this,LogIn.class));finish();
            }
            else{
                Toast.makeText(getApplicationContext(),message, Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(SignUp.this);
            pDialog.setMessage("Loading In...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
    }



}
