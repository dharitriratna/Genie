package com.example.user.genie;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class SurprisePlanner extends AppCompatActivity {
    Toolbar toolbar;
    Button btn_proceed;
    TextView date, time, gift_price, gift_name, gift_id;
    TextView to_name,to_number, to_address, from_address;
    ImageView gift_image;
    private DatePickerDialog fromDatePickerDialog;
    Calendar mcurrenttime;
    private SimpleDateFormat dateFormatter;
    String Date_;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    String login_user="";
    String giftId, giftName, giftPrice, giftImage;
    String demo;

    String toName, toNumber, entry_date,  entry_time, toAddress, fromAddress;



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
        to_name = findViewById(R.id.to_name);
        to_number = findViewById(R.id.to_number);
        to_address = findViewById(R.id.to_address);
        from_address = findViewById(R.id.from_address);
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
                toName = to_name.getText().toString().trim();
                toNumber = to_number.getText().toString().trim();
                entry_date = date.getText().toString().trim();
                entry_time = time.getText().toString().trim();
                toAddress = to_address.getText().toString().trim();
                fromAddress = from_address.getText().toString().trim();

                if (toName.length() < 1){
                    to_name.setError("Enter Name");
                }
                else if (toNumber.length() < 1){
                    to_number.setError("Enter Phone Number");
                }
                else if (entry_date.length() < 1){
                    date.setError("Enter Date");
                }
                else if (entry_time.length() < 1){
                    time.setError("Enter Time");
                }
                else if (toAddress.length() < 1){
                    to_address.setError("Enter To Address");
                }
                else if (fromAddress.length() < 1){
                    from_address.setError("Enter From Address");
                }
                else {
                    new AsynGiftSubmit().execute();
                }
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
                        String AM_PM ;
                        if(selectedHour < 12) {
                            AM_PM = "AM";
                        }else if(selectedHour==12){
                            AM_PM="PM";
                        }
                        else {
                            AM_PM = "PM";
                        }

                        time.setText( selectedHour + ":" + selectedMinute+" "+AM_PM);
                    }
                }, hour, minute, false);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });
    }

    private class AsynGiftSubmit extends AsyncTask<Void, Void, Void> {
        ProgressDialog pDialog;
        String success = null,data="",status="true";

        @Override
        protected Void doInBackground(Void... params) {
            pDialog.show();
            ArrayList<NameValuePair> cred = new ArrayList<NameValuePair>();
            cred.add(new BasicNameValuePair("user_id",login_user));//user_email
            cred.add(new BasicNameValuePair("gift_id",giftId ));
            cred.add(new BasicNameValuePair("to_name",toName ));
            cred.add(new BasicNameValuePair("phone",toNumber ));
            cred.add(new BasicNameValuePair("date",entry_date ));
            cred.add(new BasicNameValuePair("time",entry_time ));
            cred.add(new BasicNameValuePair("to_address",toAddress ));
            cred.add(new BasicNameValuePair("from_address",fromAddress ));
            Log.v("RES","Sending data" +login_user+ giftId+ toName+ toNumber +entry_date+entry_time
                    +toAddress+fromAddress);


            String urlRouteList = "http://demo.ratnatechnology.co.in/genie/index.php/api/service/addgift";
            try {
                String route_response = CustomHttpClient.executeHttpPost(urlRouteList, cred);

                success = route_response;
                JSONObject jsonObject = new JSONObject(success);

                // user_email=jsonObject.getString("user_email");
                status=jsonObject.getString("status");
                if(status.equals("false")) {
                    data = jsonObject.getString("data");
                }
                String data=jsonObject.getString("data");
                // Toast.makeText(Cart.this, sum_total, Toast.LENGTH_SHORT).show();
                JSONObject jsonObject1 = new JSONObject(data);

               /*  String user_id=jsonObject1.getString("user_id");
                String user_email=jsonObject1.getString("user_email");*/


            } catch (Exception e)

            {
                Log.v("Connection error", e.toString());

            }return null;
        }
        protected void onPostExecute(Void result) {
            pDialog.dismiss();

            if(status.equals("true"))
            {

                startActivity(new Intent(SurprisePlanner.this,PaymentActivity.class));finish();
            }
            else{
                Toast.makeText(getApplicationContext(),data, Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(SurprisePlanner.this);
            pDialog.setMessage("Loading In...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
    }

}
