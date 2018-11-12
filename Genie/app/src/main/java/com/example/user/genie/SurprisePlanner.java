package com.example.user.genie;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class SurprisePlanner extends AppCompatActivity {
    Toolbar toolbar;
    Button btn_proceed;
    TextView date, time, gift_price, gift_name, gift_id;
    ImageView gift_image;
    private DatePickerDialog fromDatePickerDialog;
    Calendar mcurrenttime;
    private SimpleDateFormat dateFormatter;
    String Date_;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    String login_user="";
    String giftId, giftName, giftPrice, giftImage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surprise_planner);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mcurrenttime = Calendar.getInstance();
        dateFormatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);

        date = findViewById(R.id.date);
        time = findViewById(R.id.time);
        gift_price = findViewById(R.id.gift_price);
        gift_name = findViewById(R.id.gift_name);
        gift_image = findViewById(R.id.gift_image);
        gift_id = findViewById(R.id.gift_id);


        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedpreferences.edit();
        login_user=sharedpreferences.getString("FLAG", "");
        editor.commit(); // commit changes


        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if(bundle != null){

            giftId = bundle.getString("GIFT_ID");
            giftName=bundle.getString("GIFT_NAME");
            giftPrice = bundle.getString("GIFT_PRICE");
            giftImage  = bundle.getString("GIFT_IMAGE");

            //    deiverydate=bundle.getString("DELIVERYDATE");

            //    Log.d("deiverydate",deiverydate);
            gift_name.setText(giftName);
            gift_price.setText(this.getResources().getString(R.string.rupee)+giftPrice);
            gift_id.setText(giftId);
            Picasso.with(this).load(giftImage).into(gift_image);
        }
        btn_proceed = findViewById(R.id.btn_proceed);
        btn_proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SurprisePlanner.this,GiftsList.class));
            }
        });

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                fromDatePickerDialog = new DatePickerDialog(SurprisePlanner.this, new DatePickerDialog.OnDateSetListener() {

                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, monthOfYear, dayOfMonth);

                        Date_ = dateFormatter.format(newDate.getTime());
                        date.setText(Date_);
                        date.setCursorVisible(false);

                    }},
                        mcurrenttime.get(Calendar.YEAR), mcurrenttime.get(Calendar.MONTH), mcurrenttime.get(Calendar.DAY_OF_MONTH));
                fromDatePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                fromDatePickerDialog.show();
            }
        });

        time.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(SurprisePlanner.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        time.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, false);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

    }
}
