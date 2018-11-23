package com.example.user.genie;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.user.genie.Adapter.BusCitiesAdapter;
import com.example.user.genie.Adapter.DatacardOperatorCircleCustomAdapter;
import com.example.user.genie.Adapter.WaterBoardAdapter;
import com.example.user.genie.Model.BusCitiesModel;
import com.example.user.genie.Model.WaterBoardModel;
import com.example.user.genie.ObjectNew.BusCitesResponse;
import com.example.user.genie.ObjectNew.getDataCardCircle;
import com.example.user.genie.ObjectNew.oRiginCities;
import com.example.user.genie.client.ApiClientGenie;
import com.example.user.genie.client.ApiInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class BusCitiesActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView citiesRecyclerView;
    ProgressDialog progressDialog;
    int i=0;
    private BusCitiesAdapter busCitiesAdapter;
    private ArrayList<oRiginCities> busCitiesModels;
    EditText searchEd;
    private AlertDialog.Builder alertDialog;
    ApiInterface apiService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_cities);
        apiService =
                ApiClientGenie.getClient().create(ApiInterface.class);
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
        citiesRecyclerView = findViewById(R.id.citiesRecyclerView);

        GridLayoutManager manager = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);
        citiesRecyclerView.setLayoutManager(manager);


        busCitiesModels = new ArrayList<>();


        if (isNetworkAvailable()) {
            networkCircleService();
        } else {
            noNetwrokErrorMessage();
        }

    }

    private void filter(String text) {
        //new array list that will hold the filtered data
        ArrayList<oRiginCities> filterdNames = new ArrayList<>();

        for(int i=0;i<busCitiesModels.size();i++) {
            //looping through existing elements
            /*for (String s : placeList) {
                //if the existing elements contains the search input
                if (s.toLowerCase().contains(text.toLowerCase())) {
                    //adding the element to filtered list
                    filterdNames.add(s);
                }
            }*/
            oRiginCities circleModel=busCitiesModels.get(i);
            if(circleModel.getOriginName().toLowerCase().contains(text.toLowerCase())){
                filterdNames.add(circleModel);
            }
        }

        //calling a method of the adapter class and passing the filtered list
        busCitiesAdapter.filterList(filterdNames);
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
    private void networkCircleService(){
        progressDialog.setMessage("Please wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        Call<BusCitesResponse> call=apiService.getBusCities();
        call.enqueue(new Callback<BusCitesResponse>() {
            @Override
            public void onResponse(Call<BusCitesResponse> call, retrofit2.Response<BusCitesResponse> response) {
                progressDialog.dismiss();
                boolean status=response.body().isStatus();
                if(status==true){
                    busCitiesModels=response.body().getData().getOriginOutput().getOriginCities();
                    if(busCitiesModels.size()>0){
                        busCitiesAdapter = new BusCitiesAdapter(busCitiesModels, getApplicationContext());
                        citiesRecyclerView.setAdapter(busCitiesAdapter);

                    } else {
                        Toast.makeText(getApplicationContext(), "No Data Found",
                                Toast.LENGTH_LONG).show();
                        citiesRecyclerView.setVisibility(View.GONE);
                        // no_orders_text.setVisibility(View.VISIBLE);
                    }
                }else {
                    Toast.makeText(getApplicationContext(),"Try again. After some times",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<BusCitesResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),"Try again. After some times",Toast.LENGTH_LONG).show();
            }
        });

    }
}
