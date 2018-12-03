package com.example.user.genie;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.user.genie.Adapter.DTHOperatorAdapter;
import com.example.user.genie.Model.DTHOperatorsModel;
import com.example.user.genie.Model.MobileOperatorCircleModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DTHOperators extends AppCompatActivity {
    Toolbar toolbar;
    ProgressDialog progressDialog;
    int i=0;
    private DTHOperatorAdapter dthOperatorAdapter;
    private List<DTHOperatorsModel> dthOperatorsModels;
    RecyclerView dth_operators_recyclerview;
    EditText searchEd;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dthoperators);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        searchEd = findViewById(R.id.searchEd);
        searchEd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());
            }
        });


        progressDialog = new ProgressDialog(this);
        dth_operators_recyclerview = findViewById(R.id.dth_operators_recyclerview);

        GridLayoutManager manager = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);
        dth_operators_recyclerview.setLayoutManager(manager);

        dthOperatorsModels = new ArrayList<>();
        getMobileOperators();
/*
        dth_operators_recyclerview.addOnItemTouchListener(new RecyclerTouchListener(this, dth_operators_recyclerview, new RecyclerTouchListener.ClickListener() {
            @Override
            public boolean onClick(View view, int position) {
                DTHOperatorsModel list = dthOperatorsModels.get(position);
                String operator_name = list.getDth_operator_name();
                String operator_code = list.getDth_operator_code();

                Intent intent = new Intent(DTHOperators.this,DTHRecharge.class);
                intent.putExtra("OPERATOR_NAME",operator_name);
                intent.putExtra("DTH_OPERATOR_CODE",operator_code);
                startActivity(intent);
                finish();

                return true;
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));*/
    }

    private void filter(String text) {
        //new array list that will hold the filtered data
        ArrayList<DTHOperatorsModel> filterdNames = new ArrayList<>();

        for(int i=0;i<dthOperatorsModels.size();i++) {
            //looping through existing elements
            /*for (String s : placeList) {
                //if the existing elements contains the search input
                if (s.toLowerCase().contains(text.toLowerCase())) {
                    //adding the element to filtered list
                    filterdNames.add(s);
                }
            }*/
            DTHOperatorsModel circleModel=dthOperatorsModels.get(i);
            if(circleModel.getDth_operator_name().toLowerCase().contains(text.toLowerCase())){
                filterdNames.add(circleModel);
            }
        }

        //calling a method of the adapter class and passing the filtered list
        dthOperatorAdapter.filterList(filterdNames);
    }

    private void getMobileOperators() {
        progressDialog.setMessage("Loading");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                "http://demo.ratnatechnology.co.in/genie/api/service/getdth",
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
                                        String dth_operator_id = "", dth_operator_name = "", dth_operator_code="", dth_service_type = "";
                                        JSONObject o = array.getJSONObject(i);

                                        dth_operator_id = o.getString("id");
                                        dth_operator_name = o.getString("operator_name");

                                        dth_operator_code = o.getString("operator_code");
                                        dth_service_type = o.getString("service_type");

                                        DTHOperatorsModel item = new DTHOperatorsModel(
                                                dth_operator_id,dth_operator_name, dth_operator_code, dth_service_type);


                                        Log.d("operator_name",dth_operator_name);
                                        dthOperatorsModels.add(item);

                                    }
                                }
                                else {
                                Toast.makeText(getApplicationContext(), "No Data Found",
                                        Toast.LENGTH_LONG).show();
                                    dth_operators_recyclerview.setVisibility(View.GONE);
                                    // no_orders_text.setVisibility(View.VISIBLE);
                                }
                            }


                            dthOperatorAdapter = new DTHOperatorAdapter(dthOperatorsModels, getApplicationContext());
                            dth_operators_recyclerview.setAdapter(dthOperatorAdapter);


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
                        Toast.makeText(DTHOperators.this, "Check your network connection.",
                                Toast.LENGTH_LONG).show();
                    }
                } else
                    Toast.makeText(DTHOperators.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(DTHOperators.this);
        requestQueue.add(stringRequest);
    }

}
