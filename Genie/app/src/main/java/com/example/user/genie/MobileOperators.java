package com.example.user.genie;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import com.example.user.genie.Adapter.MobileOperatorsAdapter;
import com.example.user.genie.Model.MobileOperatorsModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MobileOperators extends AppCompatActivity {
    Toolbar toolbar;
    ProgressDialog progressDialog;
    int i=0;
    private MobileOperatorsAdapter mobileOperatorsAdapter;
    private List<MobileOperatorsModel> operatorsModels;
    RecyclerView mob_operators_recyclerview;


    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_operators);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

      /*  Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if(bundle != null) {

            number = bundle.getString("NUMBER");

            Log.d("number", number);
        }*/



        progressDialog = new ProgressDialog(this);
        mob_operators_recyclerview = findViewById(R.id.mob_operators_recyclerview);

        GridLayoutManager manager = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);
        mob_operators_recyclerview.setLayoutManager(manager);


        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        // commit changes
        String number=pref.getString("PHONE_NUMBER", null);
        editor.commit();
       // Toast.makeText(this, number, Toast.LENGTH_SHORT).show();
     //   Log.d("number", number);


        operatorsModels = new ArrayList<>();
        getMobileOperators();

        mob_operators_recyclerview.addOnItemTouchListener(new RecyclerTouchListener(this, mob_operators_recyclerview, new RecyclerTouchListener.ClickListener() {
            @Override
            public boolean onClick(View view, int position) {
                MobileOperatorsModel list = operatorsModels.get(position);
                String operator_name = list.getOperator_name();
                String operator_code = list.getOperator_code();

                Intent intent = new Intent(MobileOperators.this,MobileOperatorCircle.class);
                intent.putExtra("OPERATOR_NAME",operator_name);
                intent.putExtra("OPERATOR_CODE",operator_code);
                startActivity(intent);
                finish();

                return true;
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    private void getMobileOperators() {
        progressDialog.setMessage("Loading");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                "http://demo.ratnatechnology.co.in/genie/api/service/get_mobileoperator",
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

                                        MobileOperatorsModel item = new MobileOperatorsModel(
                                                operator_id,operator_name, operator_code, service_type);


                                        Log.d("operator_name",operator_name);
                                        operatorsModels.add(item);

                                    }
                                }
                                else {
                                /*Toast.makeText(getApplicationContext(), "No Data Found",
                                        Toast.LENGTH_LONG).show();*/
                                    mob_operators_recyclerview.setVisibility(View.GONE);
                                    // no_orders_text.setVisibility(View.VISIBLE);
                                }
                            }


                            mobileOperatorsAdapter = new MobileOperatorsAdapter(operatorsModels, getApplicationContext());
                            mob_operators_recyclerview.setAdapter(mobileOperatorsAdapter);


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
                        Toast.makeText(MobileOperators.this, "Check your network connection.",
                                Toast.LENGTH_LONG).show();
                    }
                } else
                    Toast.makeText(MobileOperators.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(MobileOperators.this);
        requestQueue.add(stringRequest);
    }

}
