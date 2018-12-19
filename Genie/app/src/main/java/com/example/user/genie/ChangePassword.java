package com.example.user.genie;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;

public class ChangePassword extends AppCompatActivity {
    Toolbar toolbar;
    EditText user_mail, password;
    String user_email, new_password;
    Button btn_change;
    String message = "";
    ImageView eye;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
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
        password = findViewById(R.id.new_password);
        btn_change = findViewById(R.id.btn_change);

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

        btn_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_email = user_mail.getText().toString().trim();
                new_password = password.getText().toString().trim();

                if (user_email.length()<1){
                    user_mail.setError("Enter Your Valid Mail");
                }
                else if (password.length()<1){
                    password.setError("Enter A New Password");
                }
                else {
//                    Toast.makeText(ChangePassword.this, "password changed successfully", Toast.LENGTH_SHORT).show();
//                    startActivity(new Intent(ChangePassword.this,MainActivity.class));
                    new AsynSignInDetails().execute();
                }
            }
        });
    }

    private class AsynSignInDetails extends AsyncTask<Void, Void, Void> {
        ProgressDialog pDialog;
        String success = null;
        @Override
        protected Void doInBackground(Void... params) {
            ArrayList<NameValuePair> cred = new ArrayList<NameValuePair>();
            cred.add(new BasicNameValuePair("user_email",user_email));//user_email
            cred.add(new BasicNameValuePair("new_password",new_password));//user_email
            Log.v("RES","Sending data "+user_email+ new_password);

            String urlRouteList="https://genieservice.in/api/user/forgot_password_reset";
            try {
                String route_response = CustomHttpClient.executeHttpPost(urlRouteList, cred);

                success = route_response;
                JSONObject jsonObject = new JSONObject(success);
                //user_email=jsonObject.getString("user_email");
                message = jsonObject.getString("message");
            } catch (Exception e)

            {
                Log.v("ONMESSAGE", e.toString());

            }
            return null;
        }
        protected void onPostExecute(Void result) {
            pDialog.dismiss();
            Toast.makeText(getApplicationContext(),message, Toast.LENGTH_LONG).show();
            if (message.equals("Password Changed Successfully"))
            {
                startActivity(new Intent(ChangePassword.this,ChangePassword.class));
            }
            else
            {
                Toast.makeText(ChangePassword.this, "Invalid Email id", Toast.LENGTH_SHORT).show();
            }
        }
        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(ChangePassword.this);
            pDialog.setMessage("Loading In...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
    }
}
