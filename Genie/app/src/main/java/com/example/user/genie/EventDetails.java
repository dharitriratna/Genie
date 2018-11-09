package com.example.user.genie;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.user.genie.Adapter.ViewPagerAdapter;
import com.example.user.genie.Fragments.AboutEventsFragment;
import com.example.user.genie.Fragments.ShowTimeFragment;
import com.example.user.genie.Fragments.TrailerFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class EventDetails extends AppCompatActivity {

    private CollapsingToolbarLayout collapsing_toolbar;
    private Toolbar toolbar;
    AppBarLayout appBarLayout;
    TextView event_price;
    EditText organizer_name, organizer_number, organizer_address;
    Button btn_submit;
    String name;
    String event_name;
    String organizerName, organizerNumber, organizerAddress;
    TextView date, time;

    private DatePickerDialog fromDatePickerDialog;
    Calendar mcurrenttime;
    private SimpleDateFormat dateFormatter;
    String Date_;



    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
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
        dateFormatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);

       event_price = findViewById(R.id.event_price);
       organizer_name = findViewById(R.id.organizer_name);
       organizer_number = findViewById(R.id.organizer_number);
       organizer_address = findViewById(R.id.organizer_address);
       btn_submit = findViewById(R.id.btn_submit);
       date = findViewById(R.id.date);
       time = findViewById(R.id.time);

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
                       time.setText( selectedHour + ":" + selectedMinute);
                   }
               }, hour, minute, true);//Yes 24 hour time
               mTimePicker.setTitle("Select Time");
               mTimePicker.show();
           }
       });

       btn_submit.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               organizerName = organizer_name.getText().toString().trim();
               organizerNumber = organizer_number.getText().toString().trim();
               organizerAddress = organizer_address.getText().toString().trim();

               if (organizerName.length() < 1){
                   organizer_name.setError("Enter Name");
               }
               else if (organizerNumber.length() < 1){
                   organizer_number.setError("Enter Phone Number");
               }
               else if (organizerAddress.length() < 1){
                   organizer_address.setError("Enter Address");
               }
               else{
                 //  new AsynEventSubmit().execute();
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
                    collapsing_toolbar.setTitle("Event Name");
                    collapsing_toolbar.setCollapsedTitleTextAppearance(View.TEXT_ALIGNMENT_CENTER);
                    collapsing_toolbar.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);
                    isShow = true;
                } else if (isShow) {
                    collapsing_toolbar.setTitle(" ");//carefull there should a space between double quote otherwise it wont work
                    isShow = false;
                }
            }
        });

       /* viewPager = (ViewPager) findViewById(R.id.viewPager);
        setupViewPager(viewPager);

        events_tabs = findViewById(R.id.events_tabs);
        events_tabs.setupWithViewPager(viewPager);
        setupTabIcons();*/
    }


  /*  @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void setupTabIcons() {

        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tablayout, null);
        tabOne.setText("About");
        tabOne.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        // tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_tab_favourite, 0, 0);
        events_tabs.getTabAt(0).setCustomView(tabOne);

        TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tablayout, null);
        tabTwo.setText("Venue");
        tabTwo.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        // tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_tab_call, 0, 0);
        events_tabs.getTabAt(1).setCustomView(tabTwo);


    }
*/
   /* private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new AboutEventsFragment(), "About");
        adapter.addFragment(new TrailerFragment(), "Trailers & More");

        viewPager.setAdapter(adapter);
    }*/
}
