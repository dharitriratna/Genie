package com.example.user.genie;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.user.genie.helper.RegPrefManager;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ViewRetailerProfileActivity extends AppCompatActivity {
    Toolbar toolbar;
    ImageView edit, fseuserimage;
    TextView ownername, owneremail, ownernumber, shopaddress, city, pincode, state, country;
    ProgressDialog progressDialog;
    int i = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_fseprofile);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        progressDialog = new ProgressDialog(this);
        edit=findViewById(R.id.edit);
        fseuserimage=findViewById(R.id.shopimage);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),UpdateRetailerProfileActivity.class));
                finish();
            }
        });

        ownername = findViewById(R.id.ownername);
        owneremail = findViewById(R.id.owneremail);
        ownernumber = findViewById(R.id.ownernumber);
        shopaddress = findViewById(R.id.shopaddress);
        city = findViewById(R.id.city);
        pincode = findViewById(R.id.pincode);
        state = findViewById(R.id.state);
        country = findViewById(R.id.country);

        getProfileDetails();
    }

    private void getProfileDetails() {
        progressDialog.setMessage("loading...");
        progressDialog.show();
        StringRequest stringRequest=new
                StringRequest(Request.Method.GET, "https://genieservice.in/api/user/getprofile?user_id=91",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        //   Toast.makeText(UpdateProfile.this, response, Toast.LENGTH_SHORT).show();
                        try {
                            // Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                            Log.d("onResponse:", response);
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray array = jsonObject.getJSONArray("data");

                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object = array.getJSONObject(i);
                                String user_id=object.getString("id");
                                String name = object.getString("first_name");
                                String mobile=object.getString("phone");
                                String email_address=object.getString("email");


                                /*Picasso.with(getApplicationContext())
                                        .load(profile_image)
                                        .placeholder(R.drawable.round_background)   // optional
                                        .error(R.mipmap.ic_launcher)
                                        .into(imageView);


                                Picasso.with(getApplicationContext())
                                        .load("http://demo.ratnatechnology.co.in/dogai/uploads/IMG_20180612_150603.jpg")
                                        .placeholder(R.drawable.round_background)   // optional
                                        .error(R.mipmap.ic_launcher)
                                        .into(view_img);*/


                              /*  full_name.setText(name);
                                phone_no.setText(mobile);
                                email_id.setText(email_address);*/

                                RegPrefManager.getInstance(ViewRetailerProfileActivity.this).setPhoneNo(mobile);
                                RegPrefManager.getInstance(ViewRetailerProfileActivity.this).setUserName(name);
                                RegPrefManager.getInstance(ViewRetailerProfileActivity.this).setUserEmail(email_address);



//                                userid.setText(user_id);

                              /* if (spin_gender.equals(gender)){
                                spin_gender.getSelectedItem();
                            }
                            if (spin_country.equals(country)){
                                spin_country.getSelectedItem();
                            }*/
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
    },new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            if (error.getMessage() == null) {
                if (i < 3) {
                    Log.e("Retry due to error ", "for time : " + i);
                    i++;
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(ViewRetailerProfileActivity.this, "Check your network connection.",
                            Toast.LENGTH_LONG).show();
                }
            } else
                Toast.makeText(ViewRetailerProfileActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
        }
    });

    RequestQueue requestQueue= Volley.newRequestQueue(ViewRetailerProfileActivity.this);
        requestQueue.add(stringRequest);
}
}
