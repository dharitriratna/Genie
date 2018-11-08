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
import android.widget.FrameLayout;
import android.widget.TextView;


import com.example.user.genie.Adapter.CityMoviesCustomAdapter;
import com.example.user.genie.Adapter.RentCustomAdapter;
import com.example.user.genie.ObjectNew.RentResponse;
import com.example.user.genie.client.ApiClientGenie;
import com.example.user.genie.client.ApiInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RentActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView rentRecyclerview;
    private RentCustomAdapter rentCustomAdapter;
    private ArrayList<RentResponse.Data> rentarray;
    private FrameLayout sellFrame;
    private Toolbar toolbar;
    ApiInterface apiService;
    ProgressDialog progressDialog;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    String login_user="";


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
        sellFrame.setOnClickListener(this);
        rentarray=new ArrayList<>();
        rentRecyclerview.setHasFixedSize(true);
        rentRecyclerview.setLayoutManager(new LinearLayoutManager(this));

       /* rentarray = new ArrayList<>();

        rentarray.add("Abohar");
        rentarray.add("Achmpet");
        rentarray.add("Azad");
        rentarray.add("Badami");
        rentarray.add("Badepally");
        rentarray.add("Cheeka");
        rentarray.add("Cheepurupalli");
        rentarray.add("Dahod");
        rentarray.add("Dhar");




        rentRecyclerview.setHasFixedSize(true);
        rentRecyclerview.setLayoutManager(new LinearLayoutManager(this));

        rentCustomAdapter = new RentCustomAdapter(RentActivity.this,rentarray);

        rentRecyclerview.setAdapter(rentCustomAdapter);*/

       getResponse();
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
                        rentCustomAdapter = new RentCustomAdapter(RentActivity.this, rentarray);

                        rentRecyclerview.setAdapter(rentCustomAdapter);
                    }
                    else {

                    }
                }
                else {

                }
            }

            @Override
            public void onFailure(Call<RentResponse> call, Throwable t) {

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
}
