package com.example.user.genie.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.user.genie.Adapter.BrowsePlansAdapter;
import com.example.user.genie.Adapter.BusToCitiesAdapter;
import com.example.user.genie.ObjectNew.BrowsePlansResponse;
import com.example.user.genie.ObjectNew.BusToCitiesResponse;
import com.example.user.genie.ObjectNew.destinationCities;
import com.example.user.genie.ObjectNew.planDescription;
import com.example.user.genie.R;
import com.example.user.genie.client.ApiClientGenie;
import com.example.user.genie.client.ApiInterface;
import com.example.user.genie.helper.RegPrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BrowsePlanDetailsFragment extends Fragment {
    private RecyclerView browsing_plans;
    ProgressDialog progressDialog;
    int i=0;
    private BrowsePlansAdapter plansAdapter;
    private ArrayList<planDescription> plansList;
    private AlertDialog.Builder alertDialog;
    ApiInterface apiService;

    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    String login_user="";


    public static BrowsePlanDetailsFragment newInstance() {

        return new BrowsePlanDetailsFragment();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.browseplansfragment, container, false);
        apiService =
                ApiClientGenie.getClient().create(ApiInterface.class);
        browsing_plans = v.findViewById(R.id.browsing_plans);
        progressDialog = new ProgressDialog(getActivity());


        sharedpreferences = getActivity().getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

        SharedPreferences.Editor editor1 = sharedpreferences.edit();
        login_user=sharedpreferences.getString("FLAG", "");
        editor1.commit(); // commit changes


        Log.d("login_user", login_user);

        plansList = new ArrayList<>();



        if (isNetworkAvailable()) {
            networkCircleService();
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


    private void networkCircleService(){
        progressDialog.setMessage("Please wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        String rctype = "";
        String phone= RegPrefManager.getInstance(getActivity()).getPhoneNo();
        String opId = RegPrefManager.getInstance(getActivity()).getMobileOperatorCode();
        String ciId = RegPrefManager.getInstance(getActivity()).getMobileCircleCode();
        Call<BrowsePlansResponse> call = apiService.postPlan_Fetch(login_user,phone,opId,ciId,rctype);
        call.enqueue(new Callback<BrowsePlansResponse>() {
            @Override
            public void onResponse(Call<BrowsePlansResponse> call, Response<BrowsePlansResponse> response) {
                progressDialog.dismiss();
                boolean status=response.body().isStatus();
                if(status==true){
                    plansList=response.body().getData().getPlanDescription();
                    if(plansList.size()>0){
                        plansAdapter = new BrowsePlansAdapter(plansList, getActivity());
                        browsing_plans.setAdapter(plansAdapter);

                    } else {
                        Toast.makeText(getActivity(), "No Data Found",
                                Toast.LENGTH_LONG).show();
                        browsing_plans.setVisibility(View.GONE);
                        // no_orders_text.setVisibility(View.VISIBLE);
                    }
                }else {
                    Toast.makeText(getActivity(),"Try again. After some times",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<BrowsePlansResponse> call, Throwable t) {

                progressDialog.dismiss();
                Toast.makeText(getActivity(),"Try again. After some times",Toast.LENGTH_LONG).show();
            }
        });
      /*  String request=new Gson().toJson(toCitesRequest);
        JsonElement je = new Gson().toJsonTree(request);
        JsonObject jsonObject=new JsonObject();
        jsonObject.add("DestinationInput",je);


        Log.d("Tag", String.valueOf(jsonObject));*/
    }

}
