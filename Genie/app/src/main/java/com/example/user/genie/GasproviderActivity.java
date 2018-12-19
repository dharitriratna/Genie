package com.example.user.genie;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.example.user.genie.Adapter.GasProviderAdapter;
import com.example.user.genie.Adapter.WaterBoardAdapter;
import com.example.user.genie.Model.GasProviderModel;
import com.example.user.genie.Model.WaterBoardModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GasproviderActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView gasProviderRecyclerView;
    ProgressDialog progressDialog;
    int i=0;
    private GasProviderAdapter gasProviderAdapter;
    private List<GasProviderModel> gasProviderModels;
    EditText searchEd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gasprovider);
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
        gasProviderRecyclerView = findViewById(R.id.gasProviderRecyclerView);

        GridLayoutManager manager = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);
        gasProviderRecyclerView.setLayoutManager(manager);

        gasProviderModels = new ArrayList<>();
        getGasBoards();

/*
        gasProviderRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, gasProviderRecyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public boolean onClick(View view, int position) {
                GasProviderModel list = gasProviderModels.get(position);
                String operator_name = list.getGas_board_name();

                Intent intent = new Intent(GasproviderActivity.this,GasBillActivity.class);
                intent.putExtra("OPERATOR_NAME",operator_name);
                startActivity(intent);
                finish();

                return true;
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
*/
    }


    private void filter(String text) {
        //new array list that will hold the filtered data
        ArrayList<GasProviderModel> filterdNames = new ArrayList<>();

        for(int i=0;i<gasProviderModels.size();i++) {
            //looping through existing elements
            /*for (String s : placeList) {
                //if the existing elements contains the search input
                if (s.toLowerCase().contains(text.toLowerCase())) {
                    //adding the element to filtered list
                    filterdNames.add(s);
                }
            }*/
            GasProviderModel circleModel=gasProviderModels.get(i);
            if(circleModel.getGas_board_name().toLowerCase().contains(text.toLowerCase())){
                filterdNames.add(circleModel);
            }
        }

        //calling a method of the adapter class and passing the filtered list
        gasProviderAdapter.filterList(filterdNames);
    }

    private void getGasBoards() {
        progressDialog.setMessage("Loading");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                "https://genieservice.in/api/service/getGas",
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
                                        String gas_board_id = "", gas_board_name = "", gas_board_code="", gas_board_type = "";
                                        JSONObject o = array.getJSONObject(i);

                                        gas_board_id = o.getString("id");
                                        gas_board_name = o.getString("operator_name");

                                        gas_board_code = o.getString("operator_code");
                                        gas_board_type = o.getString("service_type");

                                        GasProviderModel item = new GasProviderModel(
                                                gas_board_id,gas_board_name, gas_board_code, gas_board_type);


                                        gasProviderModels.add(item);

                                    }
                                }
                                else {
                                    Toast.makeText(getApplicationContext(), "No Data Found",
                                            Toast.LENGTH_LONG).show();
                                    gasProviderRecyclerView.setVisibility(View.GONE);
                                    // no_orders_text.setVisibility(View.VISIBLE);
                                }
                            }

                            gasProviderAdapter = new GasProviderAdapter(gasProviderModels, getApplicationContext());
                            gasProviderRecyclerView.setAdapter(gasProviderAdapter);


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
                        Toast.makeText(GasproviderActivity.this, "Check your network connection.",
                                Toast.LENGTH_LONG).show();
                    }
                } else
                    Toast.makeText(GasproviderActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(GasproviderActivity.this);
        requestQueue.add(stringRequest);
    }

}
