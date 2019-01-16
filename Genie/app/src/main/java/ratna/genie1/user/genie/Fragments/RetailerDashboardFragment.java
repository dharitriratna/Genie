package ratna.genie1.user.genie.Fragments;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
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
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import ratna.genie1.user.genie.Adapter.DashboardAdapter;
import ratna.genie1.user.genie.ObjectNew.DashboardList;
import ratna.genie1.user.genie.ObjectNew.DashboardResponse;
import ratna.genie1.user.genie.R;
import ratna.genie1.user.genie.client.ApiClientGenie;
import ratna.genie1.user.genie.client.ApiInterface;
import ratna.genie1.user.genie.helper.RegPrefManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetailerDashboardFragment extends Fragment {
    RecyclerView fsedashboardRecyclerview;
    ProgressDialog progressDialog;
    int i=0;
    private DashboardAdapter dashboardAdapter;
    private ArrayList<DashboardList> dashboardLists;
    private AlertDialog.Builder alertDialog;
    ApiInterface apiService;

    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    String login_user="";

    private DatePickerDialog fromDatePickerDialog;
    Calendar mcurrenttime;
    private SimpleDateFormat dateFormatter;
    String Date_;
    TextView dept_date;
    Button btnProceed;
    TextView noWalletTv;

    public static Fragment getInstance(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("pos", position);
        RetailerDashboardFragment fragment = new RetailerDashboardFragment();
        fragment.setArguments(bundle);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fsedashboard, container, false);
        apiService =
                ApiClientGenie.getClient().create(ApiInterface.class);
        fsedashboardRecyclerview = v.findViewById(R.id.fsedashboardRecyclerview);
        progressDialog = new ProgressDialog(getActivity());
        dept_date = v.findViewById(R.id.dept_date);
        btnProceed = v.findViewById(R.id.btnProceed);
        noWalletTv = v.findViewById(R.id.noWalletTv);

        mcurrenttime = Calendar.getInstance();
     //   dateFormatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);
        dateFormatter = new SimpleDateFormat("yyyy-MMM-dd", Locale.US);



        dept_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                fromDatePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, monthOfYear, dayOfMonth);

                        Date_ = dateFormatter.format(newDate.getTime());
                        dept_date.setText(Date_);
                        dept_date.setCursorVisible(false);

                    }},
                        mcurrenttime.get(Calendar.YEAR), mcurrenttime.get(Calendar.MONTH), mcurrenttime.get(Calendar.DAY_OF_MONTH));
                fromDatePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                fromDatePickerDialog.show();
            }
        });


        //  textView = (TextView) v.findViewById(ratna.genie1.user.genie.R.id.textView);


        // textView.setText("Fragment " + (position + 1));


        sharedpreferences = getActivity().getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

        SharedPreferences.Editor editor1 = sharedpreferences.edit();
        login_user=sharedpreferences.getString("FLAG", "");
        editor1.commit(); // commit changes


        Log.d("login_user", login_user);

        dashboardLists = new ArrayList<>();

        GridLayoutManager manager = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        fsedashboardRecyclerview.setLayoutManager(manager);

        btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date_ = dept_date.getText().toString().trim();

                if (Date_.length()< 1) {
                    dept_date.setError("Please select a date");
                }
                else if (isNetworkAvailable()){
                    networkCircleService();
                }else {
                    noNetwrokErrorMessage();
                }
            }
        });
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
    /*    Runnable progressRunnable = new Runnable() {
            @Override
            public void run() {
                progressDialog.cancel();
            }
        };

        Handler pdCanceller = new Handler();
        pdCanceller.postDelayed(progressRunnable, 3000);*/
        String groupid="3";
        String phone= RegPrefManager.getInstance(getActivity()).getPhoneNo();
        Call<DashboardResponse> call = apiService.postDashboard(login_user,groupid,Date_);
        call.enqueue(new Callback<DashboardResponse>() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onResponse(Call<DashboardResponse> call, Response<DashboardResponse> response) {
                progressDialog.dismiss();
                boolean status=response.body().isStatus();
                try{
                    if(status==true){
                        // dashboardLists=response.body().getData().getPlanDescription();
                        dashboardLists = response.body().getData();

                        if(dashboardLists.size()>0){
                            dashboardAdapter = new DashboardAdapter(dashboardLists, getActivity());
                            fsedashboardRecyclerview.setVisibility(View.VISIBLE);
                            // browsing_plans.setBackgroundColor(R.color.colorPrimaryDark);
                            fsedashboardRecyclerview.setAdapter(dashboardAdapter);
                        }
                        else if (response.body().getData().equals("")){
                            noWalletTv.setVisibility(View.VISIBLE);
                            Toast.makeText(getActivity(), "No Data Found", Toast.LENGTH_SHORT).show();
                        }

                        else {
                            noWalletTv.setVisibility(View.VISIBLE);
                            Toast.makeText(getActivity(), "No Data Found",
                                    Toast.LENGTH_LONG).show();
                            fsedashboardRecyclerview.setVisibility(View.GONE);
                            // no_orders_text.setVisibility(View.VISIBLE);
                        }
                    }else {
                        noWalletTv.setVisibility(View.VISIBLE);
                        Toast.makeText(getActivity(),"Try again after some time",Toast.LENGTH_LONG).show();
                    }
                }catch (Exception e){
                    noWalletTv.setVisibility(View.VISIBLE);
                   // Toast.makeText(getActivity(), "No data found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DashboardResponse> call, Throwable t) {

                progressDialog.dismiss();
                //  Toast.makeText(getActivity(),"Try again!",Toast.LENGTH_LONG).show();
            }
        });

    }


}
