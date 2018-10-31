package com.example.user.genie;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.user.genie.Adapter.CardAdapter;
import com.example.user.genie.Model.CardModel;
import com.squareup.picasso.Picasso;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.example.user.genie.VerticalFragment.hasPermissions;

public class OrderGenerate extends AppCompatActivity {
    Toolbar toolbar;
    TextView edt;
    EditText serv_name, amount,user_name, user_address, user_landmark, user_city, user_state, user_country;
    String[] pincodes = {"", "", "", "", "", ""};
    String pincode,name, address, landmark, city, state, country;
    String service_id, service_name, service_fees, service_img;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    String login_user="";
    Button btn_next;
    ImageView service_image;
    int i=0;
    EditText edit_pin;
    TextView order_date;
    String URL="http://demo.ratnatechnology.co.in/genie/index.php/api/service/getpin";
    ProgressDialog progressDialog;
    ArrayList pinList;

    private DatePickerDialog fromDatePickerDialog;
    Calendar mcurrenttime;
    private SimpleDateFormat dateFormatter;
    String Date_;


    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_generate);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        mcurrenttime = Calendar.getInstance();
        dateFormatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);

        service_image = findViewById(R.id.service_image);
        edt = findViewById(R.id.edt);
        edit_pin = findViewById(R.id.edit_pin);
        order_date = findViewById(R.id.order_date);
        user_name = findViewById(R.id.user_name);
        user_address = findViewById(R.id.user_address);
        user_landmark = findViewById(R.id.user_landmark);
        user_city = findViewById(R.id.user_city);
        user_state = findViewById(R.id.user_state);
        user_country = findViewById(R.id.user_country);
        serv_name = findViewById(R.id.service_name);
        amount = findViewById(R.id.amount);
        btn_next = findViewById(R.id.btn_next);

        order_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                fromDatePickerDialog = new DatePickerDialog(OrderGenerate.this, new DatePickerDialog.OnDateSetListener() {

                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, monthOfYear, dayOfMonth);

                        Date_ = dateFormatter.format(newDate.getTime());
                        order_date.setText(Date_);
                        order_date.setCursorVisible(false);

                    }},
                        mcurrenttime.get(Calendar.YEAR), mcurrenttime.get(Calendar.MONTH), mcurrenttime.get(Calendar.DAY_OF_MONTH));
                fromDatePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                fromDatePickerDialog.show();
            }
        });


        progressDialog = new ProgressDialog(this);

        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedpreferences.edit();
        login_user=sharedpreferences.getString("FLAG", "");
        editor.commit(); // commit changes

        Log.d("login_user", login_user);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if(bundle != null) {

            service_id = bundle.getString("SERVICE_ID");
            service_name = bundle.getString("SERVICE_NAME");
            service_fees = bundle.getString("SERVICE_FEES");
            service_img = bundle.getString("SERVICE_IMAGE");

            Log.d("url", service_fees);
        }

        serv_name.setText(service_name);
        amount.setText(service_fees);
        Picasso.with(this).load(service_img).into(service_image);

/*
         edit_pin.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                 //openDialog();
             }
         });
*/

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pincode = edit_pin.getText().toString().trim();
                if (pincode.length() < 1) {
                    edit_pin.setError("Please Enter Your picode");
                } else {
                    Intent intent_send = new Intent(OrderGenerate.this, Adress.class);
                    intent_send.putExtra("PINCODE", pincode);
                    intent_send.putExtra("SERVICE_ID", service_id);
                    intent_send.putExtra("SERVICE_NAME", service_name);
                    intent_send.putExtra("SERVICE_FEES", service_fees);
                    startActivity(intent_send);

                }
            }
        });

/*
        private void loadSpinnerData(String url) {
            RequestQueue requestQueue=Volley.newRequestQueue(getApplicationContext());
            StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try{
                        JSONObject jsonObject=new JSONObject(response);
                        if(jsonObject.getInt("success")==1){
                            JSONArray jsonArray=jsonObject.getJSONArray("Name");
                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject1=jsonArray.getJSONObject(i);
                                String pincode=jsonObject1.getString("code");
                                pins.add(pincode);
                            }
                        }
                        edit_pin.setAdapter(new ArrayAdapter<String>(OrderGenerate.this, android.R.layout.simple_spinner_dropdown_item, pins));
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
*/
    }

    private void jsonObjectRequest(String s, Map<String, String> param) {
    }

/*
    private void loadSpinnerData(String url) {

        RequestQueue requestQueue=Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject=new JSONObject(response);
                    if(jsonObject.getInt("success")==1){
                        JSONArray jsonArray=jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject1=jsonArray.getJSONObject(i);
                            String pincode=jsonObject1.getString("code");
                            pins.add(pincode);
                        }
                    }
                    edit_pin.setAdapter(new ArrayAdapter<String>(OrderGenerate.this, android.R.layout.simple_spinner_dropdown_item, pins));
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
*/
    }





/*
    private class AsynSignInDetails extends AsyncTask<Void, Void, Void> {
        ProgressDialog pDialog;
        String success = null,data="",status="true";

        @Override
        protected Void doInBackground(Void... params) {
            pDialog.show();
            ArrayList<NameValuePair> cred = new ArrayList<NameValuePair>();
            cred.add(new BasicNameValuePair("user_id",login_user));//user_email
            cred.add(new BasicNameValuePair("name",name ));
            cred.add(new BasicNameValuePair("address",address ));
            cred.add(new BasicNameValuePair("landmark",landmark ));
            cred.add(new BasicNameValuePair("city",city ));
            cred.add(new BasicNameValuePair("state",state ));
            cred.add(new BasicNameValuePair("country",country ));
            cred.add(new BasicNameValuePair("service_id",service_id ));
            cred.add(new BasicNameValuePair("price",service_fees ));
            cred.add(new BasicNameValuePair("pincode",pincode ));
            Log.v("RES","Sending data " +login_user+ name+ address +landmark+city+state+country+service_id
                    +service_fees+pincode);

            String urlRouteList="http://demo.ratnatechnology.co.in/genie/index.php/api/service/addService";
            try {
                String route_response = CustomHttpClient.executeHttpPost(urlRouteList, cred);

                success = route_response;
                JSONObject jsonObject = new JSONObject(success);

                // user_email=jsonObject.getString("user_email");
                status=jsonObject.getString("status");
                if(status.equals("false")) {
                    data = jsonObject.getString("data");
                }
                String data=jsonObject.getString("data");
                // Toast.makeText(Cart.this, sum_total, Toast.LENGTH_SHORT).show();
                JSONObject jsonObject1 = new JSONObject(data);
               */
/* String user_id=jsonObject1.getString("user_id");
                String user_email=jsonObject1.getString("user_email");*//*


            } catch (Exception e)

            {
                Log.v("Connection error", e.toString());

            }return null;
        }
        protected void onPostExecute(Void result) {
            pDialog.dismiss();

            if(status.equals("true"))
            {
                Toast.makeText(getApplicationContext(),"Order Successful", Toast.LENGTH_LONG).show();

                startActivity(new Intent(OrderGenerate.this,MainActivity.class));finish();
            }
            else{
                Toast.makeText(getApplicationContext(),data, Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(OrderGenerate.this);
            pDialog.setMessage("Loading In...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
    }
*/


