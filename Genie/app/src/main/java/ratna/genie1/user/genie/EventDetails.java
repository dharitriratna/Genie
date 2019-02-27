package ratna.genie1.user.genie;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import ratna.genie1.user.genie.helper.RegPrefManager;
import com.squareup.picasso.Picasso;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class EventDetails extends AppCompatActivity {

    private CollapsingToolbarLayout collapsing_toolbar;
    private Toolbar toolbar;
    AppBarLayout appBarLayout;
    TextView event_price,eventid;
    EditText organizer_name, organizer_number, organizer_address;
    Button btn_submit;
    String name;
    String event_name;
    String organizerName, organizerNumber, organizerAddress;
    TextView date, time;
    ImageView eventimage;

    private DatePickerDialog fromDatePickerDialog;
    Calendar mcurrenttime;
    private SimpleDateFormat dateFormatter;
    String Date_;
    String entry_date, entry_time;
    String event_id;
    String eventprice;
    String event_image;

    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    String login_user="";


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mcurrenttime = Calendar.getInstance();
        dateFormatter = new SimpleDateFormat("dd MMM yyyy", Locale.US);


       event_price = findViewById(R.id.event_price);
       eventimage = findViewById(R.id.eventimage);
       eventid = findViewById(R.id.eventid);
       organizer_name = findViewById(R.id.organizer_name);
       organizer_number = findViewById(R.id.organizer_number);
       organizer_number.setText(RegPrefManager.getInstance(this).getPhoneNo());
       organizer_address = findViewById(R.id.organizer_address);
       btn_submit = findViewById(R.id.btn_submit);
       date = findViewById(R.id.date);
       time = findViewById(R.id.time);


        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedpreferences.edit();
        login_user=sharedpreferences.getString("FLAG", "");
        editor.commit(); // commit changes

        Log.d("login_user", login_user);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if(bundle != null){

            event_id = bundle.getString("EVENT_ID");
            event_name=bundle.getString("EVENT_NAME");
            eventprice = bundle.getString("EVENT_PRICE");
            event_image  = bundle.getString("EVENT_IMAGE");

            //    deiverydate=bundle.getString("DELIVERYDATE");

            //    Log.d("deiverydate",deiverydate);
            event_price.setText("Starting from" +" "+this.getResources().getString(R.string.rupee)+eventprice);
            eventid.setText(event_id);
            Picasso.with(this).load(event_image).into(eventimage);
        }

       date.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
               fromDatePickerDialog = new DatePickerDialog(EventDetails.this, new DatePickerDialog.OnDateSetListener() {

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
               mTimePicker = new TimePickerDialog(EventDetails.this, new TimePickerDialog.OnTimeSetListener() {
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

       btn_submit.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               organizerName = organizer_name.getText().toString().trim();
               organizerNumber = organizer_number.getText().toString().trim();
               entry_date = date.getText().toString().trim();
               entry_time = time.getText().toString().trim();
               organizerAddress = organizer_address.getText().toString().trim();

               if (organizerName.length() < 1){
                   organizer_name.setError("Enter Name");
               }
               else if (organizerNumber.length() < 1){
                   organizer_number.setError("Enter Phone Number");
               }
               else if (entry_date.length() < 1){
                   date.setError("Enter Date");
               }
               else if (entry_time.length() < 1){
                   time.setError("Enter Time");
               }
               else if (organizerAddress.length() < 1){
                   organizer_address.setError("Enter Address");
               }
               else{
                  new AsynEventSubmit().execute();
               }
           }
       });

        appBarLayout = findViewById(R.id.appbar);
        collapsing_toolbar = findViewById(R.id.collapsing_toolbar);

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = true;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsing_toolbar.setTitle(event_name);
                    collapsing_toolbar.setCollapsedTitleTextAppearance(View.TEXT_ALIGNMENT_CENTER);
                    collapsing_toolbar.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);
                    isShow = true;
                } else if (isShow) {
                    collapsing_toolbar.setTitle(" ");//carefull there should a space between double quote otherwise it wont work
                    isShow = false;
                }
            }
        });

    }


    private class AsynEventSubmit extends AsyncTask<Void, Void, Void> {
        ProgressDialog pDialog;
        String success = null,data="",status="true";

        @Override
        protected Void doInBackground(Void... params) {
            pDialog.show();
            ArrayList<NameValuePair> cred = new ArrayList<NameValuePair>();
            cred.add(new BasicNameValuePair("user_id",login_user));//user_email
            cred.add(new BasicNameValuePair("event_id",event_id ));
            cred.add(new BasicNameValuePair("organiser_name",organizerName ));
            cred.add(new BasicNameValuePair("organiser_phone",organizerNumber ));
            cred.add(new BasicNameValuePair("date",entry_date ));
            cred.add(new BasicNameValuePair("time",entry_time ));
            cred.add(new BasicNameValuePair("address",organizerAddress ));
            Log.v("RES","Sending data" +login_user+ event_id+ organizerName +organizerNumber+entry_date
                    +entry_time+organizerAddress);


            String urlRouteList="https://genieservice.in/api/service/event";
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
                Toast.makeText(getApplicationContext(),"Event Registered Successfully", Toast.LENGTH_LONG).show();

                startActivity(new Intent(EventDetails.this,ThankYouActivity.class));finish();
            }
            else{
                Toast.makeText(getApplicationContext(),data, Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(EventDetails.this);
            pDialog.setMessage("Loading In...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
    }
}
