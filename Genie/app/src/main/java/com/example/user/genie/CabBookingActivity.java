package com.example.user.genie;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.user.genie.ObjectNew.CabResponse;
import com.example.user.genie.ObjectNew.ServiceImage;
import com.example.user.genie.client.ApiClientGenie;
import com.example.user.genie.client.ApiInterface;
import com.example.user.genie.helper.DatePickerFragmentFrom;
import com.example.user.genie.helper.RegPrefManager;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CabBookingActivity extends AppCompatActivity implements View.OnClickListener {
private Toolbar toolbar;
private RadioButton onewayRB,roundtrip;
private EditText fromEd,toEd,deppEd,depEd,arrivalEd,timeEd;
private Button bookingBtn;
    private int depYear,depMonth,depDay,mHour, mMinute;
    private boolean trip;
    ApiInterface apiService;
    ProgressDialog progressDialog;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    String login_user="";
    int trip_value=0;
    String traveldate,returndate="",time;
    private AlertDialog.Builder alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cab_booking);
        apiService =
                ApiClientGenie.getClient().create(ApiInterface.class);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //onBackPressed();
                startActivity(new Intent(CabBookingActivity.this,MainActivity.class));
                finish();
            }
        });
        progressDialog =new ProgressDialog(this);
        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        login_user=sharedpreferences.getString("FLAG", "");
        editor.commit(); // commit changes
        alertDialog=new AlertDialog.Builder(this);

        Log.d("login_user", login_user);

        intialize();
    }

    private void intialize(){
        fromEd=findViewById(R.id.fromEd);
        toEd=findViewById(R.id.toEd);
        deppEd=findViewById(R.id.deppEd);
        depEd=findViewById(R.id.depEd);
        arrivalEd=findViewById(R.id.arrivalEd);
        timeEd=findViewById(R.id.timeEd);
        bookingBtn=findViewById(R.id.bookingBtn);
        onewayRB=findViewById(R.id.onewayRB);
        roundtrip=findViewById(R.id.roundtrip);
        depEd.setOnClickListener(this);
        arrivalEd.setOnClickListener(this);
        timeEd.setOnClickListener(this);
        fromEd.setOnClickListener(this);
        toEd.setOnClickListener(this);
        onewayRB.setOnClickListener(this);
        roundtrip.setOnClickListener(this);
        bookingBtn.setOnClickListener(this);
        deppEd.setOnClickListener(this);

        fromEd.setText(RegPrefManager.getInstance(this).getCabFromPlace());

        toEd.setText(RegPrefManager.getInstance(this).getCabToPlace());

    }

    public void bookingDatePicker(){
        DatePickerFragmentFrom date = new DatePickerFragmentFrom();
        /**
         * Set Up Current Date Into dialog
         */
        Calendar calender = Calendar.getInstance();
        Bundle args = new Bundle();

        args.putInt("year", calender.get(Calendar.YEAR));
        args.putInt("month", calender.get(Calendar.MONTH));
        args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
        date.setArguments(args);
        /**
         * Set Call back to capture selected date
         */
        date.setCallBack(ondatefrom);
        date.show(getFragmentManager(), "Date Picker");
    }
    DatePickerDialog.OnDateSetListener ondatefrom = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            depYear=year;
            depMonth=monthOfYear;
            depDay=dayOfMonth;
            String datemain_from=String.valueOf(dayOfMonth)+ "/" +String.valueOf(monthOfYear+1)+"/"+String.valueOf(year);


            if(trip_value==0){

            }else if(trip_value==1){
                deppEd.setText(datemain_from);
            }else {
                depEd.setText(datemain_from);
            }


        }
    };

    public void returnDatePicker(){
        DatePickerFragmentFrom date = new DatePickerFragmentFrom();
        /**
         * Set Up Current Date Into dialog
         */
        Calendar calender = Calendar.getInstance();
        Bundle args = new Bundle();

        args.putInt("year", depYear);
        args.putInt("month", depMonth);
        args.putInt("day", depDay);
        date.setArguments(args);
        /**
         * Set Call back to capture selected date
         */
        date.setCallBack(ondatefromArrival);
        date.show(getFragmentManager(), "Date Picker");
    }
    DatePickerDialog.OnDateSetListener ondatefromArrival = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {

            String datemain_from=String.valueOf(dayOfMonth)+ "/" +String.valueOf(monthOfYear+1)+"/"+String.valueOf(year);
            if(monthOfYear<depMonth){
                Toast.makeText(getApplicationContext(),"Please Select Proper Date",Toast.LENGTH_SHORT).show();
            }else {
                if((monthOfYear==depMonth)){
                    if(dayOfMonth<depDay){
                        Toast.makeText(getApplicationContext(),"Please Select Proper Date",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        arrivalEd.setText(datemain_from);
                    }
                }else {
                    arrivalEd.setText(datemain_from);
                }

            }


        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.arrivalEd:
                if(trip_value!=0) {
                    returnDatePicker();
                }
                break;
            case R.id.depEd:
                if(trip_value!=0) {
                    bookingDatePicker();
                }else {
                    Toast.makeText(getApplicationContext(),"Please Select Trip",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.deppEd:
                if(trip_value!=0) {
                    bookingDatePicker();
                }else {
                    Toast.makeText(getApplicationContext(),"Please Select Trip",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.roundtrip:
                trip_value=2;
                RegPrefManager.getInstance(this).setTripRadio(String.valueOf(trip_value));
                arrivalEd.setVisibility(View.VISIBLE);
                depEd.setVisibility(View.VISIBLE);
                deppEd.setVisibility(View.GONE);
                break;

            case R.id.onewayRB:
                trip_value=1;
                RegPrefManager.getInstance(this).setTripRadio(String.valueOf(trip_value));
                arrivalEd.setVisibility(View.GONE);
                depEd.setVisibility(View.GONE);
                deppEd.setVisibility(View.VISIBLE);
                break;
            case R.id.timeEd:
                // Get Current Time
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                timeEd.setText(hourOfDay + ":" + minute);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
                break;
            case R.id.toEd:
                RegPrefManager.getInstance(this).setPlace("To");
                    startActivity(new Intent(CabBookingActivity.this,PlaceCabActivity.class));
                    finish();
                break;
            case R.id.fromEd:
                RegPrefManager.getInstance(this).setPlace("From");
                startActivity(new Intent(CabBookingActivity.this,PlaceCabActivity.class));
                finish();
                break;
            case R.id.bookingBtn:
                if(trip_value==1){
                    traveldate=deppEd.getText().toString();
                    returndate="";
                }else {
                    traveldate=depEd.getText().toString();
                    returndate=arrivalEd.getText().toString();
                }
                if(validationNew()) {
                    if(isNetworkAvailable()) {
                        networkService();
                    } else {
                        noNetwrokErrorMessage();
                    }
                }
                break;

        }
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

    @Override
    protected void onResume(){
        super.onResume();
        fromEd.setText(RegPrefManager.getInstance(this).getCabFromPlace());

        toEd.setText(RegPrefManager.getInstance(this).getCabToPlace());

        String tripv=RegPrefManager.getInstance(this).getTripRadio();
        if(tripv!=null) {
            if (tripv.equals("1")) {
                onewayRB.setChecked(true);
                roundtrip.setChecked(false);
                arrivalEd.setVisibility(View.GONE);
                depEd.setVisibility(View.GONE);
                deppEd.setVisibility(View.VISIBLE);

            } else {
                roundtrip.setChecked(true);
                onewayRB.setChecked(false);
                arrivalEd.setVisibility(View.VISIBLE);
                depEd.setVisibility(View.VISIBLE);
                deppEd.setVisibility(View.GONE);

            }
        }
    }
    private void networkService(){
        progressDialog.setMessage("Loading");
        progressDialog.show();

        Call<CabResponse> call=apiService.postCabBooking(Integer.parseInt(login_user),
                trip_value,fromEd.getText().toString().trim(),toEd.getText().toString().trim(),traveldate,returndate,
                timeEd.getText().toString().trim());
        call.enqueue(new Callback<CabResponse>() {
            @Override
            public void onResponse(Call<CabResponse> call, Response<CabResponse> response) {
                progressDialog.dismiss();
                boolean status=response.body().isStatus();
                String data=response.body().getData();
                Toast.makeText(getApplicationContext(),data,Toast.LENGTH_SHORT).show();
                startActivity(new Intent(CabBookingActivity.this,MainActivity.class));
                finish();
              //  Log.d("Tag","sta");

            }

            @Override
            public void onFailure(Call<CabResponse> call, Throwable t) {
                Log.d("Tag","Failure");
            }
        });
    }


    private Boolean validationNew() {



        if (fromEd.getText().toString().trim().isEmpty()) {

            fromEd.setError("Please Enter From Address");

            return false;
        }


        if (toEd.getText().toString().trim().isEmpty()) {

            toEd.setError("Please Enter To Address");

            return false;
        }

        if(trip_value==1){
            if(deppEd.getText().toString().trim().isEmpty()){
                deppEd.setError("Please Enter Travel date");
            }
        }
        else if(trip_value==2){
            if(depEd.getText().toString().trim().isEmpty()){
                depEd.setError("Please Enter Travel date");
            }

            if(arrivalEd.getText().toString().trim().isEmpty()){
                arrivalEd.setError("Please Enter Return  date");
            }
        }

        if (timeEd.getText().toString().trim().isEmpty()) {

            timeEd.setError("Please Enter Time");

            return false;
        }





        return true;
    }
}
