package com.example.user.genie;

import android.annotation.SuppressLint;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;

public class LogIn extends AppCompatActivity {
    Toolbar toolbar;
    TextView signup_word, forgot_pass;
    EditText phone, password;
    Button btn_login;
    String user_phone, user_pwd, user_id = "";
    ImageView eye;
    TextView terms;

    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    String login_user="";
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        signup_word = findViewById(R.id.signup_word);
        forgot_pass = findViewById(R.id.forgot_pass);
        phone = findViewById(R.id.phone);
        terms = findViewById(R.id.terms);
        password = findViewById(R.id.password);
        btn_login = findViewById(R.id.btn_login);
        signup_word.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LogIn.this,SignUp.class));
            }
        });
        forgot_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LogIn.this,ForgotPassword.class));
            }
        });

        terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LogIn.this,TermsAndConditions.class));
            }
        });


        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedpreferences.edit();
        login_user=sharedpreferences.getString("LOGGED_IN_AS", "");
        editor.commit(); // commit changes

        if (login_user.equals("1")){startActivity(new Intent(this,MainActivity.class));finish();}

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_phone = phone.getText().toString().trim();
                user_pwd = password.getText().toString().trim();

                if (user_phone.length()<1){
                    phone.setError("Enter Your Phone Number");
                }
                else if (user_pwd.length()<1){
                    password.setError("Enter Your Password");
                }
                else {
                    new AsynSignInDetails().execute();
                //  startActivity(new Intent(LogIn.this,MainActivity.class));
                }
            }
        });

/*
        eye.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {

                switch ( event.getAction() ) {

                    case MotionEvent.ACTION_UP:
                        password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        break;

                    case MotionEvent.ACTION_DOWN:
                        password.setInputType(InputType.TYPE_CLASS_TEXT);
                        break;
                }
                return true;
            }
        });
*/
    }

    private class AsynSignInDetails extends AsyncTask<Void, Void, Void> {
        ProgressDialog pDialog;
        String success = null,message="",status="true";

        @Override
        protected Void doInBackground(Void... params) {
            pDialog.show();
            ArrayList<NameValuePair> cred = new ArrayList<NameValuePair>();
            cred.add(new BasicNameValuePair("phone",user_phone));//user_email
            cred.add(new BasicNameValuePair("user_pwd",user_pwd ));
            Log.v("RES","Sending data " +user_phone+ user_pwd  );

            String urlRouteList="http://demo.ratnatechnology.co.in/genie/index.php/api/user/login";
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

                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("FLAG", user_id);
                Log.d("user_id", user_id);
                editor.commit();



            } catch (Exception e)

            {
                Log.v("Connection error", e.toString());

            }return null;
        }


        protected void onPostExecute(Void result) {
            pDialog.dismiss();

            if(status.equals("true"))
            {
                Toast.makeText(getApplicationContext(),"Login Successful", Toast.LENGTH_LONG).show();
                sharedpreferences = getSharedPreferences(mypreference,
                        Context.MODE_MULTI_PROCESS);
                SharedPreferences.Editor editor = sharedpreferences.edit();  //deb done code for one time login
                editor.putString("LOGGED_IN_AS", "1");
                editor.commit();
                startActivity(new Intent(LogIn.this,MainActivity.class));finish();


            }
            else{
                Toast.makeText(getApplicationContext(),message, Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(LogIn.this);
            pDialog.setMessage("Loading In...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
    }

}
