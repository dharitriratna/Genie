package ratna.genie1.user.genie.Fragments;

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
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import ratna.genie1.user.genie.Adapter.BrowsePlansAdapter;
import ratna.genie1.user.genie.ObjectNew.BrowsePlansResponse;
import ratna.genie1.user.genie.ObjectNew.planDescription;
import ratna.genie1.user.genie.R;
import ratna.genie1.user.genie.client.ApiClientGenie;
import ratna.genie1.user.genie.client.ApiInterface;
import ratna.genie1.user.genie.helper.RegPrefManager;

import java.util.ArrayList;

import ratna.genie1.user.genie.ObjectNew.BrowsePlansResponse;
import ratna.genie1.user.genie.ObjectNew.planDescription;
import ratna.genie1.user.genie.client.ApiClientGenie;
import ratna.genie1.user.genie.client.ApiInterface;
import ratna.genie1.user.genie.helper.RegPrefManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BrowsePlanDetailsFragment4g extends Fragment {
    private RecyclerView browsingPlansRecyclerView;
    ProgressDialog progressDialog;
    int i=0;
    private BrowsePlansAdapter plansAdapter;
    private ArrayList<planDescription> plansList;
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
        BrowsePlanDetailsFragment4g fragment = new BrowsePlanDetailsFragment4g();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v= inflater.inflate(ratna.genie1.user.genie.R.layout.browseplansfragment, container, false);
        apiService =
                ApiClientGenie.getClient().create(ApiInterface.class);
        browsingPlansRecyclerView = v.findViewById(ratna.genie1.user.genie.R.id.browsingPlansRecyclerView);
        progressDialog = new ProgressDialog(getActivity());

        textView = (TextView) v.findViewById(ratna.genie1.user.genie.R.id.textView);


       // textView.setText("Fragment " + (position + 1));


        sharedpreferences = getActivity().getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

        SharedPreferences.Editor editor1 = sharedpreferences.edit();
        login_user=sharedpreferences.getString("FLAG", "");
        editor1.commit(); // commit changes


        Log.d("login_user", login_user);

        plansList = new ArrayList<>();

        GridLayoutManager manager = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        browsingPlansRecyclerView.setLayoutManager(manager);




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
        Runnable progressRunnable = new Runnable() {

            @Override
            public void run() {
                progressDialog.cancel();
            }
        };

        Handler pdCanceller = new Handler();
        pdCanceller.postDelayed(progressRunnable, 3000);
        String rctype="4G";
        String phone= RegPrefManager.getInstance(getActivity()).getPhoneNo();
        String opId = RegPrefManager.getInstance(getActivity()).getMobileOperatorCode();
        String ciId = RegPrefManager.getInstance(getActivity()).getMobileCircleCode();
        Call<BrowsePlansResponse> call = apiService.postPlan_Fetch(login_user,phone,opId,ciId,rctype);
        call.enqueue(new Callback<BrowsePlansResponse>() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onResponse(Call<BrowsePlansResponse> call, Response<BrowsePlansResponse> response) {
                progressDialog.dismiss();
                boolean status=response.body().isStatus();
                if(status==true){
                    plansList=response.body().getData().getPlanDescription();
                    if(plansList.size()>0){
                        plansAdapter = new BrowsePlansAdapter(plansList, getActivity());
                        browsingPlansRecyclerView.setVisibility(View.VISIBLE);
                       // browsing_plans.setBackgroundColor(R.color.colorPrimaryDark);
                        browsingPlansRecyclerView.setAdapter(plansAdapter);
                    }
                    /*else if (response.body().getData().equals("")){
                        Toast.makeText(getActivity(), "No Data", Toast.LENGTH_SHORT).show();
                    }*/
                        else {
                        Toast.makeText(getActivity(), "No Data Found",
                                Toast.LENGTH_LONG).show();
                        browsingPlansRecyclerView.setVisibility(View.GONE);
                        // no_orders_text.setVisibility(View.VISIBLE);
                    }
                }else {
                    Toast.makeText(getActivity(),"Try again. After some times",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<BrowsePlansResponse> call, Throwable t) {

                progressDialog.dismiss();
               // Toast.makeText(getActivity(),"Try again!",Toast.LENGTH_LONG).show();
            }
        });

    }

}
