package com.example.user.genie;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.user.genie.Adapter.AllAddressAdapter;
import com.example.user.genie.Adapter.OrderAdapter;
import com.example.user.genie.Model.AllAddressModel;
import com.example.user.genie.Model.OrderModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MyOrders extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView orders_recyclerview;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    String login_user="";
    ProgressDialog progressDialog;
    int i=0;
    private OrderAdapter orderAdapter;
    private List<OrderModel> orderModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        progressDialog = new ProgressDialog(this);
        orders_recyclerview = findViewById(R.id.orders_recyclerview);
        GridLayoutManager manager = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);
        orders_recyclerview.setLayoutManager(manager);

        orderModels = new ArrayList<>();

        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedpreferences.edit();
        login_user=sharedpreferences.getString("FLAG", "");
        editor.commit(); // commit changes

        Log.d("login_user", login_user);
        getOrders();
    }

    private void getOrders() {
        progressDialog.setMessage("Loading");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                "http://demo.ratnatechnology.co.in/genie/index.php/api/service/getorder_service?user_id="+login_user,
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
                                        String id = "", name = "", lane = "", landmark = "", city = "", state = "", country = "", pin = "", amount="", subtotal="";
                                        JSONObject o = array.getJSONObject(i);
                                        id = o.getString("id");
                                        name = o.getString("billing_name");
                                        lane = o.getString("billing_line1");
                                        landmark = o.getString("service_name");
                                        city = o.getString("billing_city");
                                        state = o.getString("billing_state");
                                        country = o.getString("billing_country");
                                        pin = o.getString("pin");
                                        amount = o.getString("price");
                                        subtotal = o.getString("service_sub_total");

                                        Log.d("lane", lane);
                                        OrderModel item = new OrderModel(
                                                id,name, lane, landmark, city, state, country, pin,amount,subtotal);


                                        orderModels.add(item);

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
                                    orders_recyclerview.setVisibility(View.GONE);
                                    // no_orders_text.setVisibility(View.VISIBLE);
                                }
                            }



                            orderAdapter = new OrderAdapter(orderModels, getApplicationContext());
                            orders_recyclerview.setAdapter(orderAdapter);


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
                        Toast.makeText(MyOrders.this, "Check your network connection.",
                                Toast.LENGTH_LONG).show();
                    }
                } else
                    Toast.makeText(MyOrders.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(MyOrders.this);
        requestQueue.add(stringRequest);
    }

}
