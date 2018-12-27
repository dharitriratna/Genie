package com.example.user.genie;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.genie.ObjectNew.LoginResponse;
import com.example.user.genie.client.ApiClientGenie;
import com.example.user.genie.client.ApiClientGenie1;
import com.example.user.genie.client.ApiInterface;
import com.example.user.genie.helper.RegPrefManager;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LogIn extends AppCompatActivity {
    Toolbar toolbar;
    TextView signup_word, forgot_pass;
    EditText phone, password;
    Button btn_login;
    String user_phone, user_pwd, user_id = "";
    ImageView eye;
    TextView terms;
    private AlertDialog.Builder alertDialog;
    ApiInterface apiService;
    ProgressDialog progressDialog;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    String login_user="";

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        apiService =
                ApiClientGenie1.getClient().create(ApiInterface.class);
        progressDialog =new ProgressDialog(this);
        alertDialog=new AlertDialog.Builder(this);
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
        eye = findViewById(R.id.eye);

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


        signup_word.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  startActivity(new Intent(LogIn.this,SignUp.class));

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(LogIn.this);

             //   alertDialogBuilder.setTitle("Log Out");

                alertDialogBuilder
                        .setMessage("You are not authorized user to use this service" +
                                "For more please contact us : info@genieservice.in")
                        .setCancelable(false)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // if this button is clicked, close
                                // current activity
                              /*  sharedpreferences = getSharedPreferences(mypreference,
                                        Context.MODE_PRIVATE);
                                Intent i = new Intent(LogIn.this, LogIn.class);
                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.remove("LOGGED_IN_AS"); // will delete key email
                                editor.commit(); // commit changes
                                startActivity(i);*/

                            }
                        });
                      /*  .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // if this button is clicked, just close
                                // the dialog box and do nothing
                                dialog.cancel();
                            }
                        });*/

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();
                // show it
                alertDialog.show();
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

        if (login_user.equals("1")){startActivity(new Intent(this,MainActivity2.class));finish();}
        if (login_user.equals("2")){startActivity(new Intent(this,MainActivity3.class));finish();}
        if (login_user.equals("3")){startActivity(new Intent(this,MainActivity4.class));finish();}
        if (login_user.equals("4")){startActivity(new Intent(this,MainActivity.class));finish();}

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
                    RegPrefManager.getInstance(LogIn.this).setPhoneNo(user_phone);
                    /*if (isNetworkAvailable()) {
                        networkRegister(); //register add beneficiary
                    }
                    else {
                        noNetwrokErrorMessage();
                    }*/
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


    private class AsynSignInDetails extends AsyncTask<Void, Void, Void> {
        ProgressDialog pDialog;
        String success = null,message="";
        boolean status=true;
        String user_groups;

        @Override
        protected Void doInBackground(Void... params) {
            pDialog.show();
            ArrayList<NameValuePair> cred = new ArrayList<NameValuePair>();
            cred.add(new BasicNameValuePair("phone",user_phone));//user_email
            cred.add(new BasicNameValuePair("user_pwd",user_pwd ));
            Log.v("RES","Sending data " +user_phone+ user_pwd  );

            String urlRouteList="https://genieservice.in/api/user/login";
            try {
                String route_response = CustomHttpClient.executeHttpPost(urlRouteList, cred);

                success = route_response;
                JSONObject jsonObject = new JSONObject(success);

                // user_email=jsonObject.getString("user_email");
                //    status=jsonObject.getString("status");
                status =jsonObject.getBoolean("status");
                message = jsonObject.getString("message");

                String data=jsonObject.getString("data");
                // Toast.makeText(Cart.this, sum_total, Toast.LENGTH_SHORT).show();

                JSONObject jsonObject1 = new JSONObject(data);
                String user_id=jsonObject1.getString("user_id");
                String user_email=jsonObject1.getString("user_email");

                String user_name = jsonObject1.getString("user_name");
                String user_phone = jsonObject1.getString("user_phone");
                user_groups = jsonObject1.getString("user_groups");
                RegPrefManager.getInstance(LogIn.this).setUserGroup(user_groups);
                RegPrefManager.getInstance(LogIn.this).setPhoneNo(user_phone);
                RegPrefManager.getInstance(LogIn.this).setUserName(user_name);
                RegPrefManager.getInstance(LogIn.this).setUserEmail(user_email);
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

            if(status==true&&user_groups.equals("4"))
            {
                Toast.makeText(getApplicationContext(),"Login Successful As Distributor", Toast.LENGTH_LONG).show();
                sharedpreferences = getSharedPreferences(mypreference,
                        Context.MODE_MULTI_PROCESS);
                SharedPreferences.Editor editor = sharedpreferences.edit();  //deb done code for one time login
                editor.putString("LOGGED_IN_AS", "1");
                editor.commit();
                startActivity(new Intent(LogIn.this,MainActivity2.class));finish();
            }
            else if (status==true&&user_groups.equals("5")){

                Toast.makeText(getApplicationContext(),"Login Successful As FSE", Toast.LENGTH_LONG).show();
                sharedpreferences = getSharedPreferences(mypreference,
                        Context.MODE_MULTI_PROCESS);
                SharedPreferences.Editor editor = sharedpreferences.edit();  //deb done code for one time login
                editor.putString("LOGGED_IN_AS", "2");
                editor.commit();
                startActivity(new Intent(LogIn.this,MainActivity3.class));finish();
            }

            else if (status==true&&user_groups.equals("3")){
                Toast.makeText(LogIn.this, "Login Successful As Retailer", Toast.LENGTH_SHORT).show();

                sharedpreferences = getSharedPreferences(mypreference,
                        Context.MODE_MULTI_PROCESS);
                SharedPreferences.Editor editor = sharedpreferences.edit();  //deb done code for one time login
                editor.putString("LOGGED_IN_AS", "3");
                editor.commit();
                startActivity(new Intent(LogIn.this,MainActivity4.class));finish();
            }
            else if (status==true&&user_groups.equals("2")){
                Toast.makeText(LogIn.this, "Login Successful As Customer", Toast.LENGTH_SHORT).show();

                sharedpreferences = getSharedPreferences(mypreference,
                        Context.MODE_MULTI_PROCESS);
                SharedPreferences.Editor editor = sharedpreferences.edit();  //deb done code for one time login
                editor.putString("LOGGED_IN_AS", "4");
                editor.commit();
                startActivity(new Intent(LogIn.this,MainActivity.class));finish();
            }
            else {
                Toast.makeText(LogIn.this, message, Toast.LENGTH_SHORT).show();
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

/*
    private void networkRegister(){
        progressDialog.setMessage("Please wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();




        Call<LoginResponse> call=apiService.postLogin(user_phone,user_pwd);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                progressDialog.dismiss();
                boolean status=response.body().isStatus();

                    if (status == true) {
                        String message = response.body().getMessage();
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                        String user_id = response.body().getData().getUser_id();
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString("FLAG", user_id);
                        Log.d("user_id", user_id);
                        editor.commit();

                        String user_email = response.body().getData().getUser_email();
                        String user_name = response.body().getData().getUser_name();
                        String user_phone = response.body().getData().getUser_phone();
                        String user_groups = response.body().getData().getUser_groups();
                        RegPrefManager.getInstance(LogIn.this).setUserGroup(user_groups);
                        RegPrefManager.getInstance(LogIn.this).setPhoneNo(user_phone);
                        RegPrefManager.getInstance(LogIn.this).setUserName(user_name);
                        RegPrefManager.getInstance(LogIn.this).setUserEmail(user_email);

                        SharedPreferences.Editor editor1 = sharedpreferences.edit();
                        editor1.putString("LOGGED_IN_AS", "1");
                        editor1.commit();
                        startActivity(new Intent(LogIn.this, MainActivity.class));
                        finish();


                    } else {
                        String message = response.body().getMessage();
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                    }
                }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),"Network Problem!!!", Toast.LENGTH_LONG).show();

            }
        });
    }
*/


}
