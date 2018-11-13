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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.genie.Adapter.CabPlaceCustomAdapter;
import com.example.user.genie.Adapter.TrainPlaceCustomAdapter;
import com.example.user.genie.ObjectNew.PlaceCabResponse;
import com.example.user.genie.client.ApiClientGenie;
import com.example.user.genie.client.ApiInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlaceCabActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar toolbar;
    private ImageView cancelImg;
    private EditText searchEd;
    private RecyclerView placeRecyclerview;
    private CabPlaceCustomAdapter flightPlaceCustomAdapter;
    // private CityMoviesCustomAdapter cityMoviesCustomAdapter;
    private ArrayList<String> placeList;

    private AlertDialog.Builder alertDialog;
    ApiInterface apiService;
    ProgressDialog progressDialog;
    private TextView noMesgTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_cab);
        apiService =
                ApiClientGenie.getClient().create(ApiInterface.class);
        progressDialog =new ProgressDialog(this);
        alertDialog=new AlertDialog.Builder(this);
        toolbar = findViewById(R.id.toolbar);
        cancelImg=toolbar.findViewById(R.id.cancelImg);
        cancelImg.setOnClickListener(this);
        placeList=new ArrayList<>();
        noMesgTv=findViewById(R.id.noMesgTv);
        intialize();
    }

    private void intialize(){

        searchEd=findViewById(R.id.searchEd);
        noMesgTv=findViewById(R.id.noMesgTv);
        placeRecyclerview=findViewById(R.id.placeRecyclerview);


        if (isNetworkAvailable()) {
            networkService();
        } else {
            noNetwrokErrorMessage();
        }


        //flightPlaceCustomAdapter.setClickListener(this);

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
    private void filter(String text) {
        //new array list that will hold the filtered data
        ArrayList<String> filterdNames = new ArrayList<>();

        //looping through existing elements
        for (String s : placeList) {
            //if the existing elements contains the search input
            if (s.toLowerCase().contains(text.toLowerCase())) {
                //adding the element to filtered list
                filterdNames.add(s);
            }
        }

        //calling a method of the adapter class and passing the filtered list
        flightPlaceCustomAdapter.filterList(filterdNames);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.cancelImg:
                startActivity(new Intent(PlaceCabActivity.this,CabBookingActivity.class));
                finish();
                break;

        }
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
    private void networkService(){
        progressDialog.setMessage("Please wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        Call<PlaceCabResponse> call=apiService.getCabServiceLocation();
        call.enqueue(new Callback<PlaceCabResponse>() {
            @Override
            public void onResponse(Call<PlaceCabResponse> call, Response<PlaceCabResponse> response) {
                progressDialog.dismiss();
                ArrayList<PlaceCabResponse.Data> data=response.body().getData();
                if(data.size()>0){
                    for(int i=0;i<data.size();i++){
                        String cityname=data.get(i).getArea_name();
                        placeList.add(cityname);

                    }
                    noMesgTv.setVisibility(View.GONE);
                    placeRecyclerview.setVisibility(View.VISIBLE);
                    placeRecyclerview.setHasFixedSize(true);
                    placeRecyclerview.setLayoutManager(new LinearLayoutManager(PlaceCabActivity.this));
                    //placeRecyclerview.setItemAnimator(new DefaultItemAnimator());
                    flightPlaceCustomAdapter=new CabPlaceCustomAdapter(PlaceCabActivity.this,placeList);
                    placeRecyclerview.setAdapter(flightPlaceCustomAdapter);
                }
                else {
                    noMesgTv.setVisibility(View.VISIBLE);
                    placeRecyclerview.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<PlaceCabResponse> call, Throwable t) {
                progressDialog.dismiss();
                noMesgTv.setVisibility(View.VISIBLE);
                placeRecyclerview.setVisibility(View.GONE);
            }
        });

    }
}
