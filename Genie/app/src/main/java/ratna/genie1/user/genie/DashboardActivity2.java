package ratna.genie1.user.genie;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
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

public class DashboardActivity2 extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView fsedashboardRecyclerview;
    ProgressDialog progressDialog;
    int i=0;
    private DashboardAdapter dashboardAdapter;
    private ArrayList<DashboardList> dashboardLists;
    ApiInterface apiService;

    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    String login_user="";

    private AlertDialog.Builder alertDialog;
    private DatePickerDialog fromDatePickerDialog;
    Calendar mcurrenttime;
    private SimpleDateFormat dateFormatter;
    String Date_,Date_1;
    TextView dept_date,end_date;
    TextView fseAdTv,countNumbers;
    Button btnProceed;
    TextView noWalletTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard2);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mcurrenttime = Calendar.getInstance();
        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        apiService =
                ApiClientGenie.getClient().create(ApiInterface.class);
        alertDialog = new AlertDialog.Builder(DashboardActivity2.this);
        fsedashboardRecyclerview = findViewById(R.id.fsedashboardRecyclerview);
        progressDialog = new ProgressDialog(DashboardActivity2.this);
        dept_date = findViewById(R.id.dept_date);
        end_date = findViewById(R.id.end_date);
        btnProceed = findViewById(R.id.btnProceed);
        noWalletTv = findViewById(R.id.noWalletTv);
        fseAdTv = findViewById(R.id.fseAdTv);
        countNumbers = findViewById(R.id.countNumbers);

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


        //   dateFormatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);



        dept_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                fromDatePickerDialog = new DatePickerDialog(DashboardActivity2.this, new DatePickerDialog.OnDateSetListener() {

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

        end_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                fromDatePickerDialog = new DatePickerDialog(getApplicationContext(), new DatePickerDialog.OnDateSetListener() {

                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, monthOfYear, dayOfMonth);

                        Date_1 = dateFormatter.format(newDate.getTime());
                        end_date.setText(Date_1);
                        end_date.setCursorVisible(false);

                    }},
                        mcurrenttime.get(Calendar.YEAR), mcurrenttime.get(Calendar.MONTH), mcurrenttime.get(Calendar.DAY_OF_MONTH));
                fromDatePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                fromDatePickerDialog.show();
            }
        });


        sharedpreferences = getApplicationContext().getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

        SharedPreferences.Editor editor1 = sharedpreferences.edit();
        login_user=sharedpreferences.getString("FLAG", "");
        editor1.commit(); // commit changes


        Log.d("login_user", login_user);

        dashboardLists = new ArrayList<>();

        GridLayoutManager manager = new GridLayoutManager(getApplicationContext(), 1, GridLayoutManager.VERTICAL, false);
        fsedashboardRecyclerview.setLayoutManager(manager);

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
        String groupid="3";
        Call<DashboardResponse> call = apiService.postDashboard(login_user,groupid,Date_,Date_1);
        call.enqueue(new Callback<DashboardResponse>() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onResponse(Call<DashboardResponse> call, Response<DashboardResponse> response) {
                progressDialog.dismiss();

                try{
                    boolean status=response.body().isStatus();
                    if(status==true){
                        // dashboardLists=response.body().getData().getPlanDescription();
                        int count_Numbers = response.body().getData();
                        fseAdTv.setText("Total Retailer Added");
                        fseAdTv.setVisibility(View.VISIBLE);
                        String count = String.valueOf(count_Numbers);
                        countNumbers.setVisibility(View.VISIBLE);
                        countNumbers.setText(count);


                    }
                    else {
                        noWalletTv.setVisibility(View.VISIBLE);
                        Toast.makeText(getApplicationContext(), "No Data Found",
                                Toast.LENGTH_LONG).show();
                        fsedashboardRecyclerview.setVisibility(View.GONE);
                        // no_orders_text.setVisibility(View.VISIBLE);
                    }
              /*  else {
                    noWalletTv.setVisibility(View.VISIBLE);
                    Toast.makeText(getActivity(),"Try again after some time",Toast.LENGTH_LONG).show();
                }*/
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
