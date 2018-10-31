package com.example.user.genie;

import android.app.ProgressDialog;
import android.content.Intent;
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

import com.example.user.genie.Adapter.LandlineOperatorsAdapter;
import com.example.user.genie.Model.DTHOperatorsModel;
import com.example.user.genie.Model.LandlineOperatorsModel;
import com.example.user.genie.Model.MobileOperatorsModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LandlineOperator extends AppCompatActivity {
    Toolbar toolbar;
    ProgressDialog progressDialog;
    int i=0;
    private LandlineOperatorsAdapter landlineOperatorsAdapter;
    private List<LandlineOperatorsModel> landlineOperatorsModels;
    RecyclerView landlines_recyclerview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landline_operator);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        progressDialog = new ProgressDialog(this);
        landlines_recyclerview = findViewById(R.id.landlines_recyclerview);

        GridLayoutManager manager = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);
        landlines_recyclerview.setLayoutManager(manager);

        landlineOperatorsModels = new ArrayList<>();
        getLandlineOperators();

        landlines_recyclerview.addOnItemTouchListener(new RecyclerTouchListener(this, landlines_recyclerview, new RecyclerTouchListener.ClickListener() {
            @Override
            public boolean onClick(View view, int position) {
                LandlineOperatorsModel list = landlineOperatorsModels.get(position);
                String operator_name = list.getOperator_name();

                Intent intent = new Intent(LandlineOperator.this,LandLine.class);
                intent.putExtra("OPERATOR_NAME",operator_name);
                startActivity(intent);
                finish();

                return true;
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

    }

    private void getLandlineOperators() {
        progressDialog.setMessage("Loading");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                "http://demo.ratnatechnology.co.in/genie/api/service/getlandline",
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
                                        String operator_id = "", operator_name = "", operator_code="", service_type = "";
                                        JSONObject o = array.getJSONObject(i);

                                        operator_id = o.getString("id");
                                        operator_name = o.getString("operator_name");

                                        operator_code = o.getString("operator_code");
                                        service_type = o.getString("service_type");

                                        LandlineOperatorsModel item = new LandlineOperatorsModel(
                                                operator_id,operator_name, operator_code, service_type);


                                        Log.d("operator_name",operator_name);
                                        landlineOperatorsModels.add(item);

                                    }
                                }
                                else {
                                /*Toast.makeText(getApplicationContext(), "No Data Found",
                                        Toast.LENGTH_LONG).show();*/
                                    landlines_recyclerview.setVisibility(View.GONE);
                                    // no_orders_text.setVisibility(View.VISIBLE);
                                }
                            }


                            landlineOperatorsAdapter = new LandlineOperatorsAdapter(landlineOperatorsModels, getApplicationContext());
                            landlines_recyclerview.setAdapter(landlineOperatorsAdapter);


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
                        Toast.makeText(LandlineOperator.this, "Check your network connection.",
                                Toast.LENGTH_LONG).show();
                    }
                } else
                    Toast.makeText(LandlineOperator.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(LandlineOperator.this);
        requestQueue.add(stringRequest);
    }

}
