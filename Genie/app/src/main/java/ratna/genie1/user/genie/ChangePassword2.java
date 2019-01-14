package ratna.genie1.user.genie;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;

import ratna.genie1.user.genie.helper.RegPrefManager;

public class ChangePassword2 extends AppCompatActivity {
    Toolbar toolbar;

    EditText currentPassword, newPassword, retypePassword;
    ImageView eye, eye1, eye2;
    private AlertDialog.Builder alertDialog;
    ProgressDialog progressDialog;
    String loginNumber;
    String oldpass, newpass, repass;
    Button btnSubmit;
    String message;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    String login_user="";

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password2);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedpreferences.edit();
        login_user=sharedpreferences.getString("FLAG", "");
        editor.commit(); // commit changes

        Log.d("login_user", login_user);


        loginNumber = RegPrefManager.getInstance(getApplicationContext()).getLoggedInPhoneNo();
        Log.d("phone", loginNumber);
        progressDialog =new ProgressDialog(this);
        alertDialog=new AlertDialog.Builder(this);
        eye = findViewById(R.id.eye);
        eye1 = findViewById(R.id.eye1);
        eye2 = findViewById(R.id.eye2);
        currentPassword = findViewById(R.id.currentPassword);
        newPassword = findViewById(R.id.newPassword);
        retypePassword = findViewById(R.id.retypePassword);
        btnSubmit = findViewById(R.id.btnSubmit);

        eye.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {

                switch ( event.getAction() ) {

                    case MotionEvent.ACTION_UP:
                        currentPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        break;

                    case MotionEvent.ACTION_DOWN:
                        currentPassword.setInputType(InputType.TYPE_CLASS_TEXT);
                        break;
                }
                return true;
            }
        });

        eye1.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {

                switch ( event.getAction() ) {

                    case MotionEvent.ACTION_UP:
                        newPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        break;

                    case MotionEvent.ACTION_DOWN:
                        newPassword.setInputType(InputType.TYPE_CLASS_TEXT);
                        break;
                }
                return true;
            }
        });

        eye2.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {

                switch ( event.getAction() ) {

                    case MotionEvent.ACTION_UP:
                        retypePassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        break;

                    case MotionEvent.ACTION_DOWN:
                        retypePassword.setInputType(InputType.TYPE_CLASS_TEXT);
                        break;
                }
                return true;
            }
        });


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oldpass = currentPassword.getText().toString().trim();
                newpass = newPassword.getText().toString().trim();
                repass = retypePassword.getText().toString().trim();

                if (oldpass.length() < 1){
                    currentPassword.setError("Please Enter Current Password");
                }
                else if (newpass.length() < 1){
                    newPassword.setError("Please Enter New Password");
                }
                else if (repass.length() < 1){
                    retypePassword.setError("Please Retype New Password");
                }
                else{
                    if (isNetworkAvailable()) {
                        new AsynChangePassword().execute();//register add beneficiary
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

    private class AsynChangePassword extends AsyncTask<Void, Void, Void> {
        ProgressDialog pDialog;
        String success = null,message="",status;

        @Override
        protected Void doInBackground(Void... params) {
            pDialog.show();
            ArrayList<NameValuePair> cred = new ArrayList<NameValuePair>();
            cred.add(new BasicNameValuePair("user_id",login_user));
            cred.add(new BasicNameValuePair("phone",loginNumber ));
            cred.add(new BasicNameValuePair("oldpass",oldpass ));
            cred.add(new BasicNameValuePair("newpass",newpass ));
            cred.add(new BasicNameValuePair("repass",repass ));
            Log.v("RES","Sending data " +login_user +loginNumber+oldpass+newpass+repass );

            String urlRouteList="https://genieservice.in/api/user/change_password";
            try {
                String route_response = CustomHttpClient.executeHttpPost(urlRouteList, cred);

                success = route_response;
                JSONObject jsonObject = new JSONObject(success);

                // user_email=jsonObject.getString("user_email");
                status=jsonObject.getString("status");
                if(status.equals("false")) {
                    message = jsonObject.getString("message");
                }
                String data=jsonObject.getString("message");
                // Toast.makeText(Cart.this, sum_total, Toast.LENGTH_SHORT).show();
              //  JSONObject jsonObject1 = new JSONObject(data);
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
                    Toast.makeText(getApplicationContext(), "Password successfully changed", Toast.LENGTH_LONG).show();
                    //   startActivity(new Intent(getApplicationContext(),VerifyWalletOTPActivity.class));
                    //finish();

                    //    startActivity(new Intent(AddMoneyActivity.this,OTPActivity.class));finish();
                } else {
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                }

            }catch (Exception e){
                Toast.makeText(getApplicationContext(), "Genie is away! Try after sometime", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(ChangePassword2.this);
            pDialog.setMessage("Loading In...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
    }

}
