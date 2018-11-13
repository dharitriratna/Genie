package com.example.user.genie;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.user.genie.Adapter.AllAddressAdapter;
import com.example.user.genie.Adapter.CardAdapter;
import com.example.user.genie.Model.AllAddressModel;
import com.example.user.genie.Model.CardModel;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Adress extends AppCompatActivity {
    Toolbar toolbar;
    EditText user_name, user_address, user_landmark, user_city, user_state, user_country;
    String name, address, landmark, city, state, country;
    Button btn_post_next;
    String service_id,service_name,pin,service_fees;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    String login_user="";
    RecyclerView get_address_recyclerview;
    ProgressDialog progressDialog;
    int i=0;
    private AllAddressAdapter allAddressAdapter;
    private List<AllAddressModel> addressModels;

    ImageView refresh;
    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adress);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        progressDialog = new ProgressDialog(this);
        get_address_recyclerview = findViewById(R.id.get_address_recyclerview);
        refresh = findViewById(R.id.refresh);

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAddress();
            }
        });

        GridLayoutManager manager = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);
        get_address_recyclerview.setLayoutManager(manager);

        addressModels = new ArrayList<>();


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
            pin = bundle.getString("PINCODE");

            Log.d("url", pin);
        }

        user_name = findViewById(R.id.user_name);
        user_address = findViewById(R.id.user_address);
        user_landmark = findViewById(R.id.user_landmark);
        user_city = findViewById(R.id.user_city);
        user_state = findViewById(R.id.user_state);
        user_country = findViewById(R.id.user_country);
        btn_post_next = findViewById(R.id.btn_post_next);

        btn_post_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = user_name.getText().toString().trim();
                address = user_address.getText().toString().trim();
                landmark = user_landmark.getText().toString().trim();
                city = user_city.getText().toString().trim();
                state = user_state.getText().toString().trim();
                country = user_country.getText().toString().trim();

                if (name.length() < 1){
                    user_name.setError("Please Enter Your Name");
                }
                else if (address.length()<1){
                    user_address.setError("Please Enter Your Address");
                }
                else if (landmark.length()<1){
                  user_landmark.setError("Please Enter Your Landmark");
                }
                else if (city.length()<1){
                    user_city.setError("Please Enter Your City");
                }
                else if (state.length()<1){
                    user_state.setError("Please Enter Your State");
                }
                else if (country.length()<1){
                    user_country.setError("Please Enter Your Country");
                }
                else {
                    new AsynSignInDetails().execute();
                }
            }
        });

        getAddress();
    }


    private void getAddress() {
        progressDialog.setMessage("Loading");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                "http://demo.ratnatechnology.co.in/genie/index.php/api/service/getaddress?user_id="+login_user,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        progressDialog.dismiss();
                        try {
                            Log.d("onResponse:", s);
                            JSONObject jsonObject = new JSONObject(s);
                            JSONObject object=null;
                            String message="";

                            if (jsonObject!=null){
                                JSONArray array = jsonObject.getJSONArray("data");
                                if (array.length()>0) {

                                    for (int i = 0; i < array.length(); i++) {
                                        String id = "", name = "", lane = "", landmark = "", city = "", state = "", country = "", pin = "";
                                        JSONObject o = array.getJSONObject(i);
                                        id = o.getString("id");
                                        name = o.getString("name");
                                        lane = o.getString("line1");
                                        landmark = o.getString("landmark");
                                        city = o.getString("city");
                                        state = o.getString("state");
                                        country = o.getString("country");
                                        pin = o.getString("pin");

                                        Log.d("lane", lane);
                                        AllAddressModel item = new AllAddressModel(
                                                id,name, lane, landmark, city, state, country, pin);


                                        addressModels.add(item);

                                    /*String strCurrentDate = order_date;
                                    SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy hh:mm:ss Z");
                                    Date newDate = format.parse(strCurrentDate);

                                    format = new SimpleDateFormat("MMM dd,yyyy hh:mm a");
                                    String date = format.format(newDate);*/
                                    }
                                }
                                else {
                                /*Toast.makeText(getApplicationContext(), "No Data Found",
                                        Toast.LENGTH_LONG).show();*/
                                    get_address_recyclerview.setVisibility(View.GONE);
                                    // no_orders_text.setVisibility(View.VISIBLE);
                                }
                            }

                            allAddressAdapter = new AllAddressAdapter(addressModels, getApplicationContext());
                            get_address_recyclerview.setAdapter(allAddressAdapter);


                        } catch (JSONException e) {

                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.getMessage() == null) {
                    if (i < 3) {
                        Log.e("Retry due to error ", "for time : " + i);
                        i++;
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(Adress.this, "Check your network connection.",
                                Toast.LENGTH_LONG).show();
                    }
                } else
                    Toast.makeText(Adress.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(Adress.this);
        requestQueue.add(stringRequest);
    }

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
            cred.add(new BasicNameValuePair("pincode",pin ));
            Log.v("RES","Sending data " +login_user+ name+ address +landmark+city+state+country+service_id
                    +service_fees+pin);

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

                startActivity(new Intent(Adress.this,ThankYouActivity.class));finish();
            }
            else{
                Toast.makeText(getApplicationContext(),data, Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(Adress.this);
            pDialog.setMessage("Loading In...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
    }
}
