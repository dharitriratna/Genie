package com.example.user.genie;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.user.genie.helper.DatePickerFragmentFrom;
import com.example.user.genie.helper.RegPrefManager;

import java.util.Calendar;


public class CabBookingActivity extends AppCompatActivity implements View.OnClickListener {
private Toolbar toolbar;
private RadioButton onewayRB,roundtrip;
private EditText fromEd,toEd,deppEd,depEd,arrivalEd,timeEd;
private Button bookingBtn;
    private int depYear,depMonth,depDay,mHour, mMinute;
    private boolean trip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cab_booking);
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

            depEd.setText(datemain_from);


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
                returnDatePicker();
                break;
            case R.id.depEd:
                bookingDatePicker();
                break;
            case R.id.roundtrip:
                arrivalEd.setVisibility(View.VISIBLE);
                depEd.setVisibility(View.VISIBLE);
                deppEd.setVisibility(View.GONE);
                break;

            case R.id.onewayRB:
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
                    startActivity(new Intent(CabBookingActivity.this,PlaceCabActivity.class));
                    finish();
                break;
            case R.id.fromEd:
                startActivity(new Intent(CabBookingActivity.this,PlaceCabActivity.class));
                finish();
                break;

        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        fromEd.setText(RegPrefManager.getInstance(this).getTainFromPlace());

        toEd.setText(RegPrefManager.getInstance(this).getTainToPlace());
    }
}
