package com.example.user.genie;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.example.user.genie.Adapter.FSEListAdapter;
import com.example.user.genie.Adapter.RetailerListAdapter;
import com.example.user.genie.Model.FSEListModel;
import com.example.user.genie.Model.RetailerListModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class RetailersListActivity extends AppCompatActivity {
    Toolbar toolbar;
    ImageView add_retailer;
    private RecyclerView retailerslistRecyclerView;
    private ProgressDialog progressDialog;
    private int i =0;
    private RetailerListAdapter retailerListAdapter;
    private List<RetailerListModel> retailerListModels;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    String login_user="";
    TextView no_orders_text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retailers_list);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RetailersListActivity.this,MainActivity.class));
                finish();
            }
        });

        add_retailer = findViewById(R.id.add_retailer);
        add_retailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),RetailerSignupActivity.class));
            }
        });
        no_orders_text=findViewById(R.id.no_orders_text);

        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedpreferences.edit();
        login_user=sharedpreferences.getString("FLAG", "");
        editor.commit(); // commit c


        retailerslistRecyclerView = findViewById(R.id.retailerslistRecyclerView);
        progressDialog = new ProgressDialog(this);

        GridLayoutManager manager = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);
        retailerslistRecyclerView.setLayoutManager(manager);

        retailerListModels = new ArrayList<>();
        getRetailerLists();

        retailerslistRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, retailerslistRecyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public boolean onClick(View view, int position) {
                RetailerListModel list = retailerListModels.get(position);
               /* String giftId = list.getGift_id();
                String giftName = list.getGift_name();
                String giftPrice = list.getGift_price();
                String giftImage = list.getGift_image();

                Intent intent = new Intent(GiftsList.this,SurprisePlanner.class);
                intent.putExtra("GIFT_ID",giftId);
                intent.putExtra("GIFT_NAME",giftName);
                intent.putExtra("GIFT_PRICE",giftPrice);
                intent.putExtra("GIFT_IMAGE",giftImage);
                startActivity(intent);*/
                // finish();

                return true;
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    private void getRetailerLists() {
        progressDialog.setMessage("Loading");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                "http://demo.ratnatechnology.co.in/genie/index.php/api/user/getListRetailer?user_id="+login_user,
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
                                        String id = "", username = "", phone="", email = "", first_name="",address_proof="",line1="";
                                        JSONObject o = array.getJSONObject(i);

                                        id = o.getString("id");
                                        username = o.getString("username");

                                        phone = o.getString("phone");
                                        email = o.getString("email");
                                        first_name = o.getString("first_name");
                                        address_proof = o.getString("address_proof");
                                        line1 = o.getString("line1");

                                        RetailerListModel item = new RetailerListModel(
                                                id,username, phone, email,first_name,address_proof,line1);


                                        //   Log.d("operator_name",event_name);
                                        retailerListModels.add(item);

                                    }
                                }
                                else {
                                    Toast.makeText(getApplicationContext(), "No Data Found",
                                            Toast.LENGTH_LONG).show();
                                    retailerslistRecyclerView.setVisibility(View.GONE);
                                    no_orders_text.setVisibility(View.VISIBLE);
                                }
                            }


                            retailerListAdapter = new RetailerListAdapter(retailerListModels, getApplicationContext());
                            retailerslistRecyclerView.setAdapter(retailerListAdapter);


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
                        Toast.makeText(RetailersListActivity.this, "Check your network connection.",
                                Toast.LENGTH_LONG).show();
                    }
                } else
                    Toast.makeText(RetailersListActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(RetailersListActivity.this);
        requestQueue.add(stringRequest);
    }

}
