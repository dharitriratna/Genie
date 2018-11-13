package com.example.user.genie;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;


import com.example.user.genie.Adapter.CityMoviesCustomAdapter;
import com.example.user.genie.Adapter.RentCustomAdapter;
import com.example.user.genie.Adapter.RentCustomAdapter1;
import com.example.user.genie.Model.RentFilterModel;
import com.example.user.genie.ObjectNew.RentResponse;
import com.example.user.genie.client.ApiClientGenie;
import com.example.user.genie.client.ApiInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RentActivity extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemSelectedListener {
    private RecyclerView rentRecyclerview;
    private RentCustomAdapter rentCustomAdapter;
    private RentCustomAdapter1 rentCustomAdapter1;
    private ArrayList<RentResponse.Data> rentarray;
    private ArrayList<RentFilterModel> filtterarray;
    private FrameLayout sellFrame;
    private Toolbar toolbar;
    ApiInterface apiService;
    ProgressDialog progressDialog;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    String login_user="";
    private TextView noMesgTv;
    private Spinner spinner;
    String[] rent = {"All","Room Rents", "Office Rents", "Shop rent"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent);
        apiService =
                ApiClientGenie.getClient().create(ApiInterface.class);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RentActivity.this,MainActivity.class));
                finish();
            }
        });
        progressDialog =new ProgressDialog(this);
        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        login_user=sharedpreferences.getString("FLAG", "");
        editor.commit(); // commit changes


        Log.d("login_user", login_user);

        rentRecyclerview=findViewById(R.id.rentRecyclerview);
        sellFrame=findViewById(R.id.sellFrame);
        spinner = findViewById(R.id.spinner);
        noMesgTv=findViewById(R.id.noMesgTv);
        sellFrame.setOnClickListener(this);
        rentarray=new ArrayList<>();
        filtterarray=new ArrayList<>();
        rentRecyclerview.setHasFixedSize(true);
        rentRecyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        spinnerdata();

       getResponse();
    }

    private void spinnerdata() {

//Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, rent);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spinner.setAdapter(aa);

        spinner.setOnItemSelectedListener(this);

    }
    private void getResponse(){
        //long  pnr=2352547216L;
        progressDialog.setMessage("Please wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        Call<RentResponse> call=apiService.getRentResponse(Integer.parseInt(login_user));
        call.enqueue(new Callback<RentResponse>() {
            @Override
            public void onResponse(Call<RentResponse> call, Response<RentResponse> response) {
                progressDialog.dismiss();
                boolean status=response.body().isStatus();
                Log.d("Tag","Data");
                if(status==true){
                    rentarray=response.body().getData();
                    if(rentarray.size()>0) {
                        rentRecyclerview.setVisibility(View.VISIBLE);
                        noMesgTv.setVisibility(View.GONE);
                        rentCustomAdapter = new RentCustomAdapter(RentActivity.this, rentarray);

                        rentRecyclerview.setAdapter(rentCustomAdapter);
                    }
                    else {
                        rentRecyclerview.setVisibility(View.GONE);
                        noMesgTv.setVisibility(View.VISIBLE);
                    }
                }
                else {
                    rentRecyclerview.setVisibility(View.GONE);
                    noMesgTv.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<RentResponse> call, Throwable t) {
                Log.d("Tag","Error");
                progressDialog.dismiss();
                rentRecyclerview.setVisibility(View.GONE);
                noMesgTv.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.sellFrame:
                    startActivity(new Intent(RentActivity.this,SellRentActivity.class));
                    finish();
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String spinSelect=rent[i].toString();
        if(spinSelect.equals("Room Rents")){
            filtterarray.clear();
            for(int ii=0;ii<rentarray.size();ii++){
                RentResponse.Data data=rentarray.get(ii);
                if(data.getCategory().equals("Room Rents")){
                    RentFilterModel filterModel=new RentFilterModel();
                    filterModel.setId(data.getId());
                    filterModel.setAddress(data.getAddress());
                    filterModel.setCategory(data.getCategory());
                    filterModel.setDescription(data.getDescription());
                    filterModel.setPrice(data.getPrice());
                    filterModel.setUser_id(data.getUser_id());
                    filterModel.setPhone(data.getPhone());
                    filterModel.setImage_url(data.getImage_url());

                    filtterarray.add(filterModel);

                }
            }
            if(filtterarray.size()>0) {
                rentRecyclerview.setVisibility(View.VISIBLE);
                noMesgTv.setVisibility(View.GONE);
                rentCustomAdapter1 = new RentCustomAdapter1(RentActivity.this, filtterarray);

                rentRecyclerview.setAdapter(rentCustomAdapter1);
            }
            else {
                rentRecyclerview.setVisibility(View.GONE);
                noMesgTv.setVisibility(View.VISIBLE);
            }

        }
        if(spinSelect.equals("Office Rents")){
            filtterarray.clear();
            for(int ii=0;ii<rentarray.size();ii++){
                RentResponse.Data data=rentarray.get(ii);
                if(data.getCategory().equals("Office Rents")){
                    RentFilterModel filterModel=new RentFilterModel();
                    filterModel.setId(data.getId());
                    filterModel.setAddress(data.getAddress());
                    filterModel.setCategory(data.getCategory());
                    filterModel.setDescription(data.getDescription());
                    filterModel.setPrice(data.getPrice());
                    filterModel.setUser_id(data.getUser_id());
                    filterModel.setPhone(data.getPhone());
                    filterModel.setImage_url(data.getImage_url());

                    filtterarray.add(filterModel);

                }
            }
            if(filtterarray.size()>0) {
                rentRecyclerview.setVisibility(View.VISIBLE);
                noMesgTv.setVisibility(View.GONE);
                rentCustomAdapter1 = new RentCustomAdapter1(RentActivity.this, filtterarray);

                rentRecyclerview.setAdapter(rentCustomAdapter1);
            }
            else {
                rentRecyclerview.setVisibility(View.GONE);
                noMesgTv.setVisibility(View.VISIBLE);
            }

        }
        if(spinSelect.equals("Shop rent")){
            filtterarray.clear();
            for(int ii=0;ii<rentarray.size();ii++){
                RentResponse.Data data=rentarray.get(ii);
                if(data.getCategory().equals("Shop rent")){
                    RentFilterModel filterModel=new RentFilterModel();
                    filterModel.setId(data.getId());
                    filterModel.setAddress(data.getAddress());
                    filterModel.setCategory(data.getCategory());
                    filterModel.setDescription(data.getDescription());
                    filterModel.setPrice(data.getPrice());
                    filterModel.setUser_id(data.getUser_id());
                    filterModel.setPhone(data.getPhone());
                    filterModel.setImage_url(data.getImage_url());

                    filtterarray.add(filterModel);

                }
            }
            if(filtterarray.size()>0) {
                rentRecyclerview.setVisibility(View.VISIBLE);
                noMesgTv.setVisibility(View.GONE);
                rentCustomAdapter1 = new RentCustomAdapter1(RentActivity.this, filtterarray);

                rentRecyclerview.setAdapter(rentCustomAdapter1);
            }
            else {
                rentRecyclerview.setVisibility(View.GONE);
                noMesgTv.setVisibility(View.VISIBLE);
            }

        }
        if(spinSelect.equals("All")){
            filtterarray.clear();
            if(rentarray.size()>0) {
                rentRecyclerview.setVisibility(View.VISIBLE);
                noMesgTv.setVisibility(View.GONE);
                rentCustomAdapter = new RentCustomAdapter(RentActivity.this, rentarray);

                rentRecyclerview.setAdapter(rentCustomAdapter);
            }
            else {
                rentRecyclerview.setVisibility(View.GONE);
                noMesgTv.setVisibility(View.VISIBLE);
            }

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        if(rentarray.size()>0) {
            rentRecyclerview.setVisibility(View.VISIBLE);
            noMesgTv.setVisibility(View.GONE);
            rentCustomAdapter = new RentCustomAdapter(RentActivity.this, rentarray);

            rentRecyclerview.setAdapter(rentCustomAdapter);
        }
        else {
            rentRecyclerview.setVisibility(View.GONE);
            noMesgTv.setVisibility(View.VISIBLE);
        }
    }
}
