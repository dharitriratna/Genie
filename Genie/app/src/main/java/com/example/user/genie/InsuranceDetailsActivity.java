package com.example.user.genie;

import android.app.DatePickerDialog;
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

import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.genie.ObjectNew.InsuranceDetailResponse;
import com.example.user.genie.ObjectNew.InsurancePaymentResponse;
import com.example.user.genie.client.ApiClientGenie;
import com.example.user.genie.client.ApiInterface;
import com.example.user.genie.helper.RegPrefManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InsuranceDetailsActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView merchantnameTv,tvPolicyNumber,tvCustomerName,
        tvDueDate,tvDueTo,tvInstallmentNum,tvTotalPremium,tvDueAmount;
    private Button btnPayment,btnSubmit;
    private Toolbar toolbar;
    private AlertDialog.Builder alertDialog;
    ApiInterface apiService;
    ProgressDialog progressDialog;
    private EditText policynoEd,dobEd;
    private LinearLayout detailsLn;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    String login_user="";
    private DatePickerDialog dobdatepicker;
    private Calendar newCalendar;
    private SimpleDateFormat simpleDateFormat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insurance_details);
        apiService =
                ApiClientGenie.getClient().create(ApiInterface.class);
        progressDialog =new ProgressDialog(this);
        alertDialog=new AlertDialog.Builder(this);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(InsuranceDetailsActivity.this,AllInsuranseActivity.class));
                finish();

            }
        });
        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

        SharedPreferences.Editor editor1 = sharedpreferences.edit();
        login_user=sharedpreferences.getString("FLAG", "");
        editor1.commit(); // commit changes


        Log.d("login_user", login_user);
        dobdatepicker();

        intialize();
    }
    private void intialize(){
        btnPayment=findViewById(R.id.btnPayment);
        merchantnameTv=findViewById(R.id.merchantnameTv);
        tvPolicyNumber=findViewById(R.id.tvPolicyNumber);
        tvCustomerName=findViewById(R.id.tvCustomerName);
        tvDueDate=findViewById(R.id.tvDueDate);
        tvDueTo=findViewById(R.id.tvDueTo);
        tvInstallmentNum=findViewById(R.id.tvInstallmentNum);
        tvTotalPremium=findViewById(R.id.tvTotalPremium);
        tvDueAmount=findViewById(R.id.tvDueAmount);
        policynoEd=findViewById(R.id.policynoEd);
        dobEd=findViewById(R.id.dobEd);
        btnSubmit=findViewById(R.id.btnSubmit);
        detailsLn=findViewById(R.id.detailsLn);
        btnSubmit.setOnClickListener(this);
        dobEd.setOnClickListener(this);
        btnPayment.setOnClickListener(this);

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

    private void networkInsuranceService(){
        progressDialog.setMessage("Please wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        String policynumber=policynoEd.getText().toString();
        String dob=dobEd.getText().toString();
        String id= RegPrefManager.getInstance(this).getInsuranceId();

        Call<InsuranceDetailResponse> call=apiService.postVerifyInsurance(login_user,id,policynumber,dob);
        call.enqueue(new Callback<InsuranceDetailResponse>() {
            @Override
            public void onResponse(Call<InsuranceDetailResponse> call, Response<InsuranceDetailResponse> response) {
                progressDialog.dismiss();
                String status=response.body().getData().getStatus();
                if(status.equals("sucess")){
                    policynoEd.setVisibility(View.GONE);
                    dobEd.setVisibility(View.GONE);
                    btnSubmit.setVisibility(View.GONE);
                    detailsLn.setVisibility(View.VISIBLE);
                    merchantnameTv.setText(response.body().getData().getMerchant_Name());
                    tvPolicyNumber.setText("Policy Number: "+response.body().getData().getPolicy_Number());
                    tvCustomerName.setText("Name: "+response.body().getData().getCustomer_Name());
                    tvDueDate.setText("Due From: "+response.body().getData().getDue_Form());
                    tvDueTo.setText("Due To: "+response.body().getData().getDue_To());
                    tvInstallmentNum.setText("Number of Installment: "+response.body().getData().getNumber_of_Installment());
                    tvTotalPremium.setText("Total Premium: "+response.body().getData().getTotal_Premium());
                    tvDueAmount.setText("Due Amount: "+response.body().getData().getDue_Amount());
                    RegPrefManager.getInstance(InsuranceDetailsActivity.this).setReqId(response.body().getData().getRequestId());



                }
                else {
                    policynoEd.setVisibility(View.VISIBLE);
                    dobEd.setVisibility(View.VISIBLE);
                    btnSubmit.setVisibility(View.VISIBLE);
                    detailsLn.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(),"Try Again",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<InsuranceDetailResponse> call, Throwable t) {
                progressDialog.dismiss();
                policynoEd.setVisibility(View.VISIBLE);
                dobEd.setVisibility(View.VISIBLE);
                btnSubmit.setVisibility(View.VISIBLE);
                detailsLn.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(),"Try Again",Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnSubmit:
                if(validationNew()) {
                    if (isNetworkAvailable()) {
                        networkInsuranceService();
                    } else {
                        noNetwrokErrorMessage();
                    }
                }

                break;
            case R.id.dobEd:
                View view1 = this.getCurrentFocus();
                if (view1 != null) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view1.getWindowToken(), 0);
                }
                dobdatepicker.show();
                break;
            case R.id.btnPayment:
                if (isNetworkAvailable()) {
                paymentNetwork();
                } else {
                    noNetwrokErrorMessage();
                }
                break;
        }
    }

    private Boolean validationNew() {


        if (policynoEd.getText().toString().trim().isEmpty()) {

            policynoEd.setError("Please Enter Your Policy Number");

            return false;
        }


        if (dobEd.getText().toString().trim().isEmpty()) {

            dobEd.setError("Please Enter Your DOB");

            return false;
        }

        return true;
    }

    public void dobdatepicker(){
        newCalendar = Calendar.getInstance();
        dobdatepicker= new DatePickerDialog(this,R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);


                Date today=Calendar.getInstance().getTime();
                Date date1=newDate.getTime();

                String todayvalue=simpleDateFormat.format(today);
                String selectdatevalue=simpleDateFormat.format(date1);



                if(selectdatevalue.equals(todayvalue)){



                    Toast.makeText(getApplicationContext(),"Please Select The Correct Date",Toast.LENGTH_LONG).show();
                    dobEd.setText(" ");
                }
                else {
                    dobEd.setText(simpleDateFormat.format(newDate.getTime()));
                }



            }


        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        dobdatepicker.getDatePicker().setMaxDate(System.currentTimeMillis());

    }
    private void paymentNetwork(){
        progressDialog.setMessage("Please wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        String policyno=policynoEd.getText().toString();
        String reqid=RegPrefManager.getInstance(this).getReqId();
        Call<InsurancePaymentResponse> call=apiService.postInsurancePayment(login_user,reqid,policyno);
        call.enqueue(new Callback<InsurancePaymentResponse>() {
            @Override
            public void onResponse(Call<InsurancePaymentResponse> call, Response<InsurancePaymentResponse> response) {
                progressDialog.dismiss();
                String status=response.body().getData().getStatus();
                if(status.equals("success")){
                Toast.makeText(getApplicationContext(),response.body().getData().getMessage(),Toast.LENGTH_LONG).show();
                startActivity(new Intent(InsuranceDetailsActivity.this,ThankYouActivity.class));
                finish();
                }
                else {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(),"Failed.Try again!",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<InsurancePaymentResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),"Failed.Try again!",Toast.LENGTH_LONG).show();
            }
        });
    }

}
