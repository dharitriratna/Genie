package com.example.user.genie;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
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
import com.example.user.genie.Adapter.MobileOperatorsAdapter;
import com.example.user.genie.Model.DataOperatorListModel;
import com.example.user.genie.Model.MobileOperatorsModel;
import com.example.user.genie.ObjectNew.LandLineData;
import com.example.user.genie.ObjectNew.LandlineResponseModel;
import com.example.user.genie.ObjectNew.MobileOperatorData;
import com.example.user.genie.ObjectNew.MobileOperatorResponse;
import com.example.user.genie.client.ApiClientGenie;
import com.example.user.genie.client.ApiInterface;
import com.example.user.genie.helper.RegPrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class MobileOperators extends AppCompatActivity {
    Toolbar toolbar;
    ProgressDialog progressDialog;
    int i=0;
    private MobileOperatorsAdapter mobileOperatorsAdapter;
    private List<MobileOperatorsModel> operatorsModels;
    RecyclerView mob_operators_recyclerview;
    private AlertDialog.Builder alertDialog;
    ApiInterface apiService;

    EditText searchEd;
    String opSearch;
    TextView noMesgTv;

    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_operators);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MobileOperators.this,MobileRecharge.class));
                finish();
            }
        });
        searchEd = findViewById(R.id.searchEd);
        noMesgTv = findViewById(R.id.noMesgTv);
/*
        if (isNetworkAvailable()) {
            networkCircleService();
        } else {
            noNetwrokErrorMessage();
        }*/

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



      /*  Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if(bundle != null) {

            number = bundle.getString("NUMBER");

            Log.d("number", number);
        }*/



        progressDialog = new ProgressDialog(this);
        mob_operators_recyclerview = findViewById(R.id.mob_operators_recyclerview);
        apiService =
                ApiClientGenie.getClient().create(ApiInterface.class);

        GridLayoutManager manager = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);
        mob_operators_recyclerview.setLayoutManager(manager);



        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        // commit changes
        String number=pref.getString("PHONE_NUMBER", null);
        editor.commit();
       // Toast.makeText(this, number, Toast.LENGTH_SHORT).show();
     //   Log.d("number", number);


        operatorsModels = new ArrayList<>();

        getMobileOperators();

      /*  mob_operators_recyclerview.addOnItemTouchListener(new RecyclerTouchListener(this, mob_operators_recyclerview, new RecyclerTouchListener.ClickListener() {
            @Override
            public boolean onClick(View view, int position) {
                for (int i=0;i<operatorsModels.size();i++) {
                    MobileOperatorsModel list = operatorsModels.get(position);
                    String operator_name = list.getOperator_name();
                    String operator_code = list.getOperator_code();

                    Intent intent = new Intent(MobileOperators.this, MobileOperatorCircle.class);
                    intent.putExtra("OPERATOR_NAME", operator_name);
                    intent.putExtra("OPERATOR_CODE", operator_code);
                    startActivity(intent);
                    finish();
                }
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


 /*   private void displayFromListView() {
        int position = 0;
        MobileOperatorsModel mobileOperatorsModel = operatorsModels.get(position);
        opSearch = mobileOperatorsModel.getOperator_code().toString();
    }*/

    private void filter(String text) {
        //new array list that will hold the filtered data
        ArrayList<MobileOperatorsModel> filterdNames = new ArrayList<>();

        for(int i=0;i<operatorsModels.size();i++) {
            //looping through existing elements
            /*for (String s : placeList) {
                //if the existing elements contains the search input
                if (s.toLowerCase().contains(text.toLowerCase())) {
                    //adding the element to filtered list
                    filterdNames.add(s);
                }
            }*/
            MobileOperatorsModel model=operatorsModels.get(i);
            if(model.getOperator_name().toLowerCase().contains(text.toLowerCase())){
                filterdNames.add(model);
            }
        }

        //calling a method of the adapter class and passing the filtered list
        mobileOperatorsAdapter.filterList(filterdNames);
    }

/*
    private void networkCircleService(){
        progressDialog.setMessage("Please wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        Call<MobileOperatorResponse> call=apiService.get_mobileoperator();
        call.enqueue(new Callback<MobileOperatorResponse>() {
            @Override
            public void onResponse(Call<MobileOperatorResponse> call, retrofit2.Response<MobileOperatorResponse> response) {
                progressDialog.dismiss();
                data=response.body().getData();
                if(data.size()>0){
                    noMesgTv.setVisibility(View.GONE);
                    mob_operators_recyclerview.setVisibility(View.VISIBLE);
                    mob_operators_recyclerview.setHasFixedSize(true);
                    mob_operators_recyclerview.setLayoutManager(new LinearLayoutManager(MobileOperators.this));
                    //placeRecyclerview.setItemAnimator(new DefaultItemAnimator());
                    mobileOperatorsAdapter=new MobileOperatorsAdapter(MobileOperators.this,data);
                    mob_operators_recyclerview.setAdapter(mobileOperatorsAdapter);
                }
                else {
                    noMesgTv.setVisibility(View.GONE);
                    landlines_recyclerview.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<MobileOperatorResponse> call, Throwable t) {
                progressDialog.dismiss();
                noMesgTv.setVisibility(View.VISIBLE);
                landlines_recyclerview.setVisibility(View.GONE);
            }
        });
    }
*/



    private void getMobileOperators() {
        progressDialog.setMessage("Loading");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                "https://genieservice.in/api/service/get_mobileoperator",
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
                                        String operator_id = "", operator_name = "", operator_code="", service_type = "";
                                        JSONObject o = array.getJSONObject(i);

                                        operator_id = o.getString("id");
                                        operator_name = o.getString("operator_name");

                                        operator_code = o.getString("operator_code");
                                        /*SharedPreferences sharedPreferences = getSharedPreferences(mypreference,
                                                Context.MODE_MULTI_PROCESS);
                                        SharedPreferences.Editor editor = sharedpreferences.edit();  //deb done code for one time login
                                        editor.putString("Op_Code", operator_code);
                                        editor.commit();*/
                                        service_type = o.getString("service_type");

                                        MobileOperatorsModel item = new MobileOperatorsModel(
                                                operator_id,operator_name, operator_code, service_type);


                                        Log.d("operator_name",operator_name);
                                        Log.d("op_code",operator_code);
                                        operatorsModels.add(item);

                                    }
                                }
                                else {
                                Toast.makeText(getApplicationContext(), "No Data Found",
                                        Toast.LENGTH_LONG).show();
                                    mob_operators_recyclerview.setVisibility(View.GONE);
                                     noMesgTv.setVisibility(View.VISIBLE);
                                }
                            }


                            mobileOperatorsAdapter = new MobileOperatorsAdapter(operatorsModels, getApplicationContext());
                            mob_operators_recyclerview.setAdapter(mobileOperatorsAdapter);



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
                        Toast.makeText(MobileOperators.this, "Check your network connection.",
                                Toast.LENGTH_LONG).show();
                    }
                } else
                    Toast.makeText(MobileOperators.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(MobileOperators.this);
        requestQueue.add(stringRequest);
    }

}
