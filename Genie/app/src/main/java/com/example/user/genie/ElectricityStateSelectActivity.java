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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.user.genie.Adapter.DatacardOperatorCircleCustomAdapter;
import com.example.user.genie.Adapter.StateCircleCustomAdapter;
import com.example.user.genie.Model.MobileOperatorCircleModel;
import com.example.user.genie.ObjectNew.DataCardCircleResponse;
import com.example.user.genie.ObjectNew.getDataCardCircle;
import com.example.user.genie.client.ApiClientGenie;
import com.example.user.genie.client.ApiInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ElectricityStateSelectActivity extends AppCompatActivity {

    private RecyclerView stateRecyclerview;
    private TextView noMesgTv;
    private EditText searchEd;
    private StateCircleCustomAdapter adapter;
    private Toolbar toolbar;
    private AlertDialog.Builder alertDialog;
    ApiInterface apiService;
    ProgressDialog progressDialog;
    ArrayList<DataCardCircleResponse> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electricity_state_select);
        apiService =
                ApiClientGenie.getClient().create(ApiInterface.class);
        progressDialog =new ProgressDialog(this);
        alertDialog=new AlertDialog.Builder(this);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        intialize();
    }

    private void intialize(){
        stateRecyclerview=findViewById(R.id.stateRecyclerview);
        noMesgTv=findViewById(R.id.noMesgTv);
        searchEd=findViewById(R.id.searchEd);
        data=new ArrayList<>();
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

     /*   stateRecyclerview.addOnItemTouchListener(new RecyclerTouchListener(this, stateRecyclerview, new RecyclerTouchListener.ClickListener() {
            @Override
            public boolean onClick(View view, int position) {
                DataCardCircleResponse list = data.get(position);
                String name = list.getCircle_name();
                String circle_code = list.getCircle_code();


                Intent intent = new Intent(ElectricityStateSelectActivity.this,PayForElectricity.class);
                intent.putExtra("CIRCLE_NAME", name);
                intent.putExtra("CIRCLE_CODE", circle_code);
               *//* intent.putExtra("OPERATOR_NAME", operator_name);
                intent.putExtra("OPERATOR_CODE", operator_code);*//*
                startActivity(intent);
                finish();

                return true;
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));*/
    }

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
        ArrayList<DataCardCircleResponse> filterdNames = new ArrayList<>();

        for(int i=0;i<data.size();i++) {
            //looping through existing elements
             /*for (String s : placeList) {
                //if the existing elements contains the search input
                if (s.toLowerCase().contains(text.toLowerCase())) {
                    //adding the element to filtered list
                    filterdNames.add(s);
                }
            }*/
            DataCardCircleResponse model=data.get(i);
            if(model.getCircle_name().toLowerCase().contains(text.toLowerCase())){
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
        Call<getDataCardCircle> call=apiService.getDataCardCircle();
        call.enqueue(new Callback<getDataCardCircle>() {
            @Override
            public void onResponse(Call<getDataCardCircle> call, Response<getDataCardCircle> response) {
                progressDialog.dismiss();
                data=response.body().getData();
                if(data.size()>0){
                    noMesgTv.setVisibility(View.GONE);
                    stateRecyclerview.setVisibility(View.VISIBLE);
                    stateRecyclerview.setHasFixedSize(true);
                    stateRecyclerview.setLayoutManager(new LinearLayoutManager(ElectricityStateSelectActivity.this));
                    //placeRecyclerview.setItemAnimator(new DefaultItemAnimator());
                    adapter=new StateCircleCustomAdapter(ElectricityStateSelectActivity.this,data);
                    stateRecyclerview.setAdapter(adapter);
                }
                else {
                    noMesgTv.setVisibility(View.VISIBLE);
                    stateRecyclerview.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<getDataCardCircle> call, Throwable t) {
                progressDialog.dismiss();
                noMesgTv.setVisibility(View.VISIBLE);
                stateRecyclerview.setVisibility(View.GONE);
            }
        });
    }



}
