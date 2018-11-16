package com.example.user.genie;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.example.user.genie.Adapter.LandLineCustomAdapter;
import com.example.user.genie.Adapter.LandlineOperatorsAdapter;
import com.example.user.genie.Model.DTHOperatorsModel;
import com.example.user.genie.Model.LandlineOperatorsModel;
import com.example.user.genie.Model.MobileOperatorsModel;
import com.example.user.genie.ObjectNew.DataCardCircleResponse;
import com.example.user.genie.ObjectNew.LandLineData;
import com.example.user.genie.ObjectNew.LandlineResponseModel;
import com.example.user.genie.client.ApiClientGenie;
import com.example.user.genie.client.ApiInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class LandlineOperator extends AppCompatActivity {
    Toolbar toolbar;

    int i=0;
    private LandLineCustomAdapter adapter;
    private ArrayList<LandLineData> data;
    RecyclerView landlines_recyclerview;
    private AlertDialog.Builder alertDialog;
    ApiInterface apiService;
    ProgressDialog progressDialog;
    private EditText searchEd;
    private TextView noMesgTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landline_operator);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(LandlineOperator.this,LandLine.class));
               finish();
            }
        });

        progressDialog = new ProgressDialog(this);
        landlines_recyclerview = findViewById(R.id.landlines_recyclerview);
        apiService =
                ApiClientGenie.getClient().create(ApiInterface.class);
        progressDialog =new ProgressDialog(this);
        alertDialog=new AlertDialog.Builder(this);
        searchEd=findViewById(R.id.searchEd);
        noMesgTv=findViewById(R.id.noMesgTv);

        if (isNetworkAvailable()) {
            networkCircleService();
        } else {
            noNetwrokErrorMessage();
        }



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

    }

    //flightPlaceCustomAdapter.setClickListener(this);
    public boolean isNetworkAvailable(){
        ConnectivityManager connectivityManager= (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    public void noNetwrokErrorMessage(){
        alertDialog.setTitle("Error!");
        alertDialog.setMessage("No internet connection. Please check your internet setting.");
        alertDialog.setCancelable(true);
        alertDialog.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alert=alertDialog.create();
        alert.show();

    }

    private void filter(String text) {
        //new array list that will hold the filtered data
        ArrayList<LandLineData> filterdNames = new ArrayList<>();

        for(int i=0;i<data.size();i++) {
            //looping through existing elements
            /*for (String s : placeList) {
                //if the existing elements contains the search input
                if (s.toLowerCase().contains(text.toLowerCase())) {
                    //adding the element to filtered list
                    filterdNames.add(s);
                }
            }*/
            LandLineData model=data.get(i);
            if(model.getOperator_name().toLowerCase().contains(text.toLowerCase())){
                filterdNames.add(model);
            }
        }

        //calling a method of the adapter class and passing the filtered list
        adapter.filterList(filterdNames);
    }

    private void networkCircleService(){
        progressDialog.setMessage("Please wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        Call<LandlineResponseModel> call=apiService.getLandlineOperator();
        call.enqueue(new Callback<LandlineResponseModel>() {
            @Override
            public void onResponse(Call<LandlineResponseModel> call, retrofit2.Response<LandlineResponseModel> response) {
                progressDialog.dismiss();
                data=response.body().getData();
                if(data.size()>0){
                    noMesgTv.setVisibility(View.GONE);
                    landlines_recyclerview.setVisibility(View.VISIBLE);
                    landlines_recyclerview.setHasFixedSize(true);
                    landlines_recyclerview.setLayoutManager(new LinearLayoutManager(LandlineOperator.this));
                    //placeRecyclerview.setItemAnimator(new DefaultItemAnimator());
                    adapter=new LandLineCustomAdapter(LandlineOperator.this,data);
                    landlines_recyclerview.setAdapter(adapter);
                }
                else {
                    noMesgTv.setVisibility(View.GONE);
                    landlines_recyclerview.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<LandlineResponseModel> call, Throwable t) {
                progressDialog.dismiss();
                noMesgTv.setVisibility(View.VISIBLE);
                landlines_recyclerview.setVisibility(View.GONE);
            }
        });
    }


}
