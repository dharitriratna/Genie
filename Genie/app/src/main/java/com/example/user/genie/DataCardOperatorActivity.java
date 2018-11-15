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

import com.example.user.genie.Adapter.DatacardOperatorCustomAdapter;
import com.example.user.genie.Model.DataOperatorListModel;
import com.example.user.genie.ObjectNew.getDataCardOperatorResponse;
import com.example.user.genie.client.ApiClientGenie;
import com.example.user.genie.client.ApiInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataCardOperatorActivity extends AppCompatActivity {
    private RecyclerView opeartorRecyclerview;
    private TextView noMesgTv;
    private EditText searchEd;
    private DatacardOperatorCustomAdapter adapter;
    private Toolbar toolbar;
    private AlertDialog.Builder alertDialog;
    ApiInterface apiService;
    ProgressDialog progressDialog;
    private ArrayList<DataOperatorListModel> orginalList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_card_operator);
        apiService =
                ApiClientGenie.getClient().create(ApiInterface.class);
        progressDialog =new ProgressDialog(this);
        alertDialog=new AlertDialog.Builder(this);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DataCardOperatorActivity.this,DataCardActivity.class));
                finish();
            }
        });

        intialize();
    }
    private void intialize(){
        opeartorRecyclerview=findViewById(R.id.opeartorRecyclerview);
        noMesgTv=findViewById(R.id.noMesgTv);
        searchEd=findViewById(R.id.searchEd);
        orginalList=new ArrayList<>();
        if (isNetworkAvailable()) {
            networkService();
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
        ArrayList<DataOperatorListModel> filterdNames = new ArrayList<>();

        for(int i=0;i<orginalList.size();i++) {
            //looping through existing elements
            /*for (String s : placeList) {
                //if the existing elements contains the search input
                if (s.toLowerCase().contains(text.toLowerCase())) {
                    //adding the element to filtered list
                    filterdNames.add(s);
                }
            }*/
            DataOperatorListModel model=orginalList.get(i);
            if(model.getOperator_name().toLowerCase().contains(text.toLowerCase())){
                filterdNames.add(model);
            }
        }

        //calling a method of the adapter class and passing the filtered list
        adapter.filterList(filterdNames);
    }

    private void networkService(){
        progressDialog.setMessage("Please wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        Call<getDataCardOperatorResponse> call=apiService.getDataCardOperator();
        call.enqueue(new Callback<getDataCardOperatorResponse>() {
            @Override
            public void onResponse(Call<getDataCardOperatorResponse> call, Response<getDataCardOperatorResponse> response) {
                progressDialog.dismiss();
                ArrayList<getDataCardOperatorResponse.Data> data=response.body().getData();
                orginalList.clear();
                //orginalList=response.body().getData();
                if(data.size()>0){
                    for(int i=0;i<data.size();i++) {
                        DataOperatorListModel dataOperatorListModel = new DataOperatorListModel();
                        dataOperatorListModel.setId(data.get(i).getId());
                        dataOperatorListModel.setAccount_display(data.get(i).getAccount_display());
                        dataOperatorListModel.setAmount_range(data.get(i).getAmount_range());
                        dataOperatorListModel.setBilling_model(data.get(i).getBilling_model());
                        dataOperatorListModel.setHsn_sac(data.get(i).getHsn_sac());
                        dataOperatorListModel.setOperator_code(data.get(i).getOperator_code());
                        dataOperatorListModel.setOperator_name(data.get(i).getOperator_name());
                        dataOperatorListModel.setOptional_parameter(data.get(i).getOptional_parameter());
                        dataOperatorListModel.setPartial_pay(data.get(i).getPartial_pay());
                        dataOperatorListModel.setService_type(data.get(i).getService_type());
                        dataOperatorListModel.setTds(data.get(i).getTds());
                        orginalList.add(dataOperatorListModel);
                    }
                    noMesgTv.setVisibility(View.GONE);
                    opeartorRecyclerview.setVisibility(View.VISIBLE);
                    opeartorRecyclerview.setHasFixedSize(true);
                    opeartorRecyclerview.setLayoutManager(new LinearLayoutManager(DataCardOperatorActivity.this));
                    //placeRecyclerview.setItemAnimator(new DefaultItemAnimator());
                    adapter=new DatacardOperatorCustomAdapter(DataCardOperatorActivity.this,orginalList);
                    opeartorRecyclerview.setAdapter(adapter);
                }else {
                    noMesgTv.setVisibility(View.GONE);
                    opeartorRecyclerview.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<getDataCardOperatorResponse> call, Throwable t) {
                progressDialog.dismiss();
                noMesgTv.setVisibility(View.VISIBLE);
                opeartorRecyclerview.setVisibility(View.GONE);
            }
        });

    }

}
