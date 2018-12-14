package com.example.user.genie.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.user.genie.Adapter.OrderAdapter;
import com.example.user.genie.Model.OrderModel;
import com.example.user.genie.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GiftcardOrdersFragment extends Fragment {
    private RecyclerView allorders_recyclerview;
    private OrderAdapter orderAdapter;
    private List<OrderModel> orderModels;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    String login_user="";
    ProgressDialog progressDialog;
    int i=0;


    public static Fragment getInstance(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("pos", position);
        AllOrdersFragment fragment = new AllOrdersFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.giftsordersfragment, container, false);
        allorders_recyclerview=v.findViewById(R.id.allorders_recyclerview);
        progressDialog = new ProgressDialog(getActivity());
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        allorders_recyclerview.setLayoutManager(manager);

        orderModels = new ArrayList<>();

        sharedpreferences = getActivity().getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedpreferences.edit();
        login_user=sharedpreferences.getString("FLAG", "");
        editor.commit(); // commit changes

        Log.d("login_user", login_user);
      //  getOrders();
        return v;
    }

/*
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


                                    }
                                }
                                else {
                                */
/*Toast.makeText(getApplicationContext(), "No Data Found",
                                        Toast.LENGTH_LONG).show();*//*

                                    allorders_recyclerview.setVisibility(View.GONE);
                                    // no_orders_text.setVisibility(View.VISIBLE);
                                }
                            }

                            orderAdapter = new OrderAdapter(orderModels, getActivity());
                            allorders_recyclerview.setAdapter(orderAdapter);


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
                        Toast.makeText(getActivity(), "Check your network connection.",
                                Toast.LENGTH_LONG).show();
                    }
                } else
                    Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }
*/



}
