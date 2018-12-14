package com.example.user.genie.Fragments;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.user.genie.Adapter.BrowsePlansAdapter;
import com.example.user.genie.Adapter.DTHOperatorAdapter;
import com.example.user.genie.Adapter.OrderAdapter;
import com.example.user.genie.Adapter.RentCustomAdapter;
import com.example.user.genie.DTHOperators;
import com.example.user.genie.Model.DTHOperatorsModel;
import com.example.user.genie.Model.OrderModel;
import com.example.user.genie.MyOrders;
import com.example.user.genie.ObjectNew.AllOrdersResponse;
import com.example.user.genie.ObjectNew.BrowsePlansResponse;
import com.example.user.genie.ObjectNew.RentResponse;
import com.example.user.genie.ObjectNew.planDescription;
import com.example.user.genie.R;
import com.example.user.genie.RentActivity;
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

public class AllOrdersFragment extends Fragment {
    private RecyclerView allorders_recyclerview;
    ProgressDialog progressDialog;
    int i=0;
    private OrderAdapter plansAdapter;
    private ArrayList<AllOrdersResponse.allOrdersArrayListResponse> ordersArrayListResponses;
    private AlertDialog.Builder alertDialog;
    ApiInterface apiService;

    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    String login_user="";

    int position;
    TextView textView;

    public static Fragment getInstance(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("pos", position);
        BrowsePlanDetailsFragment fragment = new BrowsePlanDetailsFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
  /*  public static AllOrdersFragment newInstance() {

        return new AllOrdersFragment();
    }*/

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.allordersfragment, container, false);

        apiService =
                ApiClientGenie.getClient().create(ApiInterface.class);
        allorders_recyclerview = v.findViewById(R.id.allorders_recyclerview);
        progressDialog = new ProgressDialog(getActivity());

        textView = (TextView) v.findViewById(R.id.textView);


        // textView.setText("Fragment " + (position + 1));


        sharedpreferences = getActivity().getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

        SharedPreferences.Editor editor1 = sharedpreferences.edit();
        login_user=sharedpreferences.getString("FLAG", "");
        editor1.commit(); // commit changes


        Log.d("login_user", login_user);

        ordersArrayListResponses = new ArrayList<>();

        GridLayoutManager manager = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        allorders_recyclerview.setLayoutManager(manager);

        if (isNetworkAvailable()) {
            getResponse();

        } else {
            noNetwrokErrorMessage();
        }
        return v;
    }

    public boolean isNetworkAvailable(){
        ConnectivityManager connectivityManager= (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
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


    private void getResponse(){
        //long  pnr=2352547216L;
        progressDialog.setMessage("Please wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        String service_id = RegPrefManager.getInstance(getActivity()).getServiceId();
        Call<AllOrdersResponse> call=apiService.getAllOrderResponse(Integer.parseInt(login_user),service_id);
        call.enqueue(new Callback<AllOrdersResponse>() {
            @Override
            public void onResponse(Call<AllOrdersResponse> call, retrofit2.Response<AllOrdersResponse> response) {
                progressDialog.dismiss();
                boolean status=response.body().isStatus();
                Log.d("Tag","Data");
                if(status==true){
                    ordersArrayListResponses=response.body().getData();
                    if(ordersArrayListResponses.size()>0) {
                        allorders_recyclerview.setVisibility(View.VISIBLE);
                       // noMesgTv.setVisibility(View.GONE);
                        plansAdapter = new OrderAdapter(getActivity(), ordersArrayListResponses);

                        allorders_recyclerview.setAdapter(plansAdapter);
                    }
                    else {
                        allorders_recyclerview.setVisibility(View.GONE);
                      //  noMesgTv.setVisibility(View.VISIBLE);
                    }
                }
                else {
                    allorders_recyclerview.setVisibility(View.GONE);
                  //  noMesgTv.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<AllOrdersResponse> call, Throwable t) {
                Log.d("Tag","Error");
                progressDialog.dismiss();
                allorders_recyclerview.setVisibility(View.GONE);
              //  noMesgTv.setVisibility(View.VISIBLE);
            }
        });
    }




}
