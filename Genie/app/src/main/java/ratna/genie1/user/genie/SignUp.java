package ratna.genie1.user.genie;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;


import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import ratna.genie1.user.genie.helper.RegPrefManager;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ratna.genie1.user.genie.helper.RegPrefManager;

public class SignUp extends AppCompatActivity {
    Toolbar toolbar;
    EditText full_name, email_id, phone_no, address, password;
    String fullName, email, mobile_no, userAddress, userPassword;
    Button btn_signup;
    String message = "";
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    String login_user="";
    Spinner userTypespinner;
    String URL="http://demo.ratnatechnology.co.in/genie/api/user/getgroups";
    ArrayList<String> UserTypeName;
    String userSpinner;
    String userTypeId;
    ImageView eye;

    @SuppressLint("ClickableViewAccessibility")
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
        UserTypeName=new ArrayList<>();
        userTypespinner = findViewById(R.id.userTypespinner);
        full_name = findViewById(R.id.full_name);
        email_id = findViewById(R.id.email_id);
        phone_no = findViewById(R.id.phone_no);
        address = findViewById(R.id.address);
        password = findViewById(R.id.password);
        btn_signup = findViewById(R.id.btn_signup);

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



        loadSpinnerData(URL);
        userTypespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String userType=   userTypespinner.getItemAtPosition(userTypespinner.getSelectedItemPosition()).toString();
             //   Toast.makeText(getApplicationContext(),userType,Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });


        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fullName = full_name.getText().toString().trim();
                email = email_id.getText().toString().trim();
                mobile_no = phone_no.getText().toString().trim();
                userAddress = address.getText().toString().trim();
                userPassword = password.getText().toString().trim();

                if (userTypespinner.getSelectedItem().toString().equals("User Type")){
                    Toast.makeText(SignUp.this, "Please select User type", Toast.LENGTH_SHORT).show();
                }
                else if (fullName.length()<1){
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

    private void loadSpinnerData(String url) {
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject=new JSONObject(response);
                    if(jsonObject.getBoolean("status")==true){
                        JSONArray jsonArray=jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject1=jsonArray.getJSONObject(i);
                            userTypeId =jsonObject1.getString("id");
                            String userType=jsonObject1.getString("name");
                            String userDesc=jsonObject1.getString("description");
                            UserTypeName.add(userType);
                        }
                    }
                    userTypespinner.setAdapter(new ArrayAdapter<String>(SignUp.this, android.R.layout.simple_spinner_dropdown_item, UserTypeName));
                }catch (JSONException e){e.printStackTrace();}
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }



    @SuppressLint("StaticFieldLeak")
    private class AsynSignInDetails extends AsyncTask<Void, Void, Void> {
        ProgressDialog pDialog;
        String success = null,message="",status="true";

        @Override
        protected Void doInBackground(Void... params) {
            pDialog.show();
            ArrayList<NameValuePair> cred = new ArrayList<NameValuePair>();
            cred.add(new BasicNameValuePair("groups",userTypeId));
            cred.add(new BasicNameValuePair("first_name",fullName));//user_email
            cred.add(new BasicNameValuePair("email",email ));
            cred.add(new BasicNameValuePair("phone",mobile_no ));
            cred.add(new BasicNameValuePair("password",userPassword ));
            Log.v("RES","Sending data " +fullName+ email+ mobile_no +userPassword );

            String urlRouteList="https://genieservice.in/api/user/register";
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
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("FLAG", user_id);
                Log.d("user_id", user_id);
                editor.commit();
                String user_phone = jsonObject1.getString("phone");
                RegPrefManager.getInstance(SignUp.this).setPhoneNo(user_phone);
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
                Toast.makeText(getApplicationContext(),"Registration Successful", Toast.LENGTH_LONG).show();

                startActivity(new Intent(SignUp.this,OTPActivity.class));finish();
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
