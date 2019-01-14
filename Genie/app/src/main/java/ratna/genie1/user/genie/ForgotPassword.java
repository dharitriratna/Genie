package ratna.genie1.user.genie;

import android.app.ProgressDialog;
import android.content.Intent;
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
import org.json.JSONObject;

import java.util.ArrayList;

public class ForgotPassword extends AppCompatActivity {
    Toolbar toolbar;
    Button btn_next;
    EditText user_mail;
    String user_email;
    String message = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
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
        btn_next = findViewById(R.id.btn_next);

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_email = user_mail.getText().toString().trim();
                 if (user_email.length()<1){
                     user_mail.setError("Enter Your Valid Email");
                 }
                 else {
                    // startActivity(new Intent(ForgotPassword.this,LogIn.class));
                     new AsyncForgotPassword().execute();

                 }
            }
        });
    }
    private class AsyncForgotPassword extends AsyncTask<Void, Void, Void> {
        ProgressDialog pDialog;
        String success = null;
        @Override
        protected Void doInBackground(Void... params) {
            pDialog.show();
            ArrayList<NameValuePair> cred = new ArrayList<NameValuePair>();
            cred.add(new BasicNameValuePair("user_email",user_email));//user_email
            Log.v("RES","Sending data" + user_email  );

            String urlRouteList="https://genieservice.in/api/user/forgot_password";
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
            try {

                if (message.equals("Check your email for passcode")) {
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                    startActivity(new Intent(ForgotPassword.this, PasscodeVerification.class));
                } else {
                    Toast.makeText(ForgotPassword.this, "Invalid email id", Toast.LENGTH_SHORT).show();
                }
            }catch(Exception e){
                Toast.makeText(getApplicationContext(), "Genie is away! Try after sometime", Toast.LENGTH_LONG).show();
            }
        }
        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(ForgotPassword.this);
            pDialog.setMessage("Loading In...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
    }
}
