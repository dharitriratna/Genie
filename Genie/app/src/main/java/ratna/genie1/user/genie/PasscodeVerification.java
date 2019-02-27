package ratna.genie1.user.genie;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
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
import org.json.JSONObject;

import java.util.ArrayList;

import ratna.genie1.user.genie.helper.RegPrefManager;

public class PasscodeVerification extends AppCompatActivity {
    Toolbar toolbar;
    EditText user_mail, user_passcode;
    Button btn_next;

    String user_email, passcode;
    private AlertDialog.Builder alertDialog;
    String message = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passcode_verification);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        user_mail = findViewById(R.id.user_mail);
        user_passcode = findViewById(R.id.passcode);
        btn_next = findViewById(R.id.btn_next);
        alertDialog=new AlertDialog.Builder(this);

        user_email = RegPrefManager.getInstance(PasscodeVerification.this).getUserEmail();
        user_mail.setText(user_email);

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passcode = user_passcode.getText().toString().trim();
                user_email = user_mail.getText().toString().trim();
                if (user_email.length()<1){
                    user_mail.setError("Enter Your Email");
                }
                else if (passcode.length()<1){
                    user_passcode.setError("Enter Your Passcode");
                }
                else{
                    if (isNetworkAvailable()) {
                        new AsynCodeVerify().execute();//register add beneficiary
                    }
                    else {
                        noNetwrokErrorMessage();
                    }
//                    Toast.makeText(PasscodeVerification.this, "Verified", Toast.LENGTH_SHORT).show();
//                    startActivity(new Intent(PasscodeVerification.this,ChangePassword.class));
                  //  new AsynCodeVerify().execute();
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

    private class AsynCodeVerify extends AsyncTask<Void, Void, Void> {
        ProgressDialog pDialog;
        String success = null;
        @Override
        protected Void doInBackground(Void... params) {
            pDialog.show();
            ArrayList<NameValuePair> cred = new ArrayList<NameValuePair>();
            cred.add(new BasicNameValuePair("user_email",user_email));//user_email
            cred.add(new BasicNameValuePair("passcode",passcode));//user_email
            Log.v("RES","Sending data "+user_email+ passcode);

            String urlRouteList="https://genieservice.in/api/user/forgot_password_verify_code";
            try {
                String route_response = CustomHttpClient.executeHttpPost(urlRouteList, cred);

                success = route_response;
                JSONObject jsonObject = new JSONObject(success);
                //user_email=jsonObject.getString("user_email");
                message = jsonObject.getString("message");
            }
            catch (Exception e)
            {
                Log.v("ONMESSAGE", e.toString());
            }
            return null;
        }

        protected void onPostExecute(Void result) {
            pDialog.dismiss();
            Toast.makeText(getApplicationContext(),message, Toast.LENGTH_LONG).show();
            if (message.equals("Passcode mached"))
            {
                startActivity(new Intent(PasscodeVerification.this,ChangePassword.class));
            }
            else
            {
                Toast.makeText(PasscodeVerification.this, "Invalid passcode", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(PasscodeVerification.this);
            pDialog.setMessage("Loading In...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
    }
}
