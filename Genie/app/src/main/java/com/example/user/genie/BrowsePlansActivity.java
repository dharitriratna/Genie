package com.example.user.genie;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.user.genie.Adapter.BrowsePlansAdapter;
import com.example.user.genie.Adapter.RemiterDetailsCustomAdapter;
import com.example.user.genie.Model.BeneficiaryDetailsResponse;
import com.example.user.genie.ObjectNew.BrowsePlansResponse;
import com.example.user.genie.ObjectNew.RemiterDetailsResponse;
import com.example.user.genie.ObjectNew.planDescription;
import com.example.user.genie.client.ApiClientGenie1;
import com.example.user.genie.client.ApiInterface;
import com.example.user.genie.helper.RegPrefManager;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BrowsePlansActivity extends AppCompatActivity {
    Toolbar toolbar;
    int i=0;
    ApiInterface apiService;
    private AlertDialog.Builder alertDialog;
    ProgressDialog progressDialog;
    EditText searchEd;
    private RecyclerView browsing_plans;;
    private BrowsePlansAdapter browsePlansAdapter;
    private ArrayList<planDescription> operatorList;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    String login_user="";
    String phoneNumber;
    String operatorName;
    String circleName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_plans);
        apiService =
                ApiClientGenie1.getClient().create(ApiInterface.class);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        browsing_plans = findViewById(R.id.browsing_plans);
        progressDialog = new ProgressDialog(this);
        searchEd = findViewById(R.id.searchEd);

        operatorList=new ArrayList<>();

        if (isNetworkAvailable()) {
            networkBrowsePlans();
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

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if(bundle != null){

            phoneNumber = bundle.getString("PhoneNumber");
            operatorName=bundle.getString("OperatorName");
            circleName = bundle.getString("CircleName");

            //    deiverydate=bundle.getString("DELIVERYDATE");

            //    Log.d("deiverydate",deiverydate);
        }

        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedpreferences.edit();
        login_user=sharedpreferences.getString("FLAG", "");
        editor.commit(); // commit changes


        Log.d("login_user", login_user);
    }

    private void filter(String text) {
        //new array list that will hold the filtered data
        ArrayList<planDescription> filterdNames = new ArrayList<>();

        for(int i=0;i<operatorList.size();i++) {
            //looping through existing elements
            /*for (String s : placeList) {
                //if the existing elements contains the search input
                if (s.toLowerCase().contains(text.toLowerCase())) {
                    //adding the element to filtered list
                    filterdNames.add(s);
                }
            }*/
            planDescription model=operatorList.get(i);
            if(model.getRecharge_type().toLowerCase().contains(text.toLowerCase())){
                filterdNames.add(model);
            }
        }

        //calling a method of the adapter class and passing the filtered list
        browsePlansAdapter.filterList(filterdNames);
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

    private void networkBrowsePlans(){
        progressDialog.setMessage("Please wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        Call<BrowsePlansResponse> call=apiService.postPlan_Fetch(login_user,phoneNumber,operatorName,circleName);
        call.enqueue(new Callback<BrowsePlansResponse>() {
            @Override
            public void onResponse(Call<BrowsePlansResponse> call, Response<BrowsePlansResponse> response) {
                progressDialog.dismiss();
                boolean status=response.body().isStatus();
                if(status==true) {

                    operatorList = response.body().getData().getPlanDescription();
                    if (operatorList.size() > 0) {
                        //  noMesgTv.setVisibility(View.GONE);
                        browsing_plans.setVisibility(View.VISIBLE);
                        browsing_plans.setHasFixedSize(true);
                        browsing_plans.setLayoutManager(new LinearLayoutManager(BrowsePlansActivity.this));
                        //placeRecyclerview.setItemAnimator(new DefaultItemAnimator());
                        browsePlansAdapter = new BrowsePlansAdapter(BrowsePlansActivity.this, operatorList);
                        browsing_plans.setAdapter(browsePlansAdapter);
                    } else {
                        //  noMesgTv.setVisibility(View.VISIBLE);
                        browsing_plans.setVisibility(View.GONE);
                    }
                }
                else {
                    browsing_plans.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<BrowsePlansResponse> call, Throwable t) {
                progressDialog.dismiss();
               // noMesgTv.setVisibility(View.VISIBLE);
                browsing_plans.setVisibility(View.GONE);

            }
        });
    }


}
