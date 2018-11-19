package com.example.user.genie;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import static com.example.user.genie.MobileRecharge.hasPermissions;

public class PayForElectricity extends AppCompatActivity {
    Toolbar toolbar;
    LinearLayout select_state_layout, select_board_layout, select_city_layout, consumer_number_layout;
    EditText select_state, select_city, consumer_number;
    TextView select_board;
    RadioGroup radioGroup;
    RadioButton electricity_boards, apartments;
    String boardName,boardCode;
    String stateName, consumerId;
    Button btnproceedToPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_for_electricity);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        radioGroup = findViewById(R.id.radioGroup);
       /* radioGroup.clearCheck();
        int selectedId=radioGroup.getCheckedRadioButtonId();*/
        electricity_boards = findViewById(R.id.electricity_boards);
        apartments = findViewById(R.id.apartments);
        select_state_layout = findViewById(R.id.select_state_layout);
        select_board_layout = findViewById(R.id.select_board_layout);
        select_city_layout = findViewById(R.id.select_city_layout);
        consumer_number_layout = findViewById(R.id.consumer_number_layout);
        btnproceedToPay = findViewById(R.id.btnproceedToPay);

        select_state = findViewById(R.id.select_state);
        select_board = findViewById(R.id.select_board);
        select_city = findViewById(R.id.select_city);
        consumer_number = findViewById(R.id.consumer_number);

        electricity_boards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select_state_layout.setVisibility(View.VISIBLE);
                select_board_layout.setVisibility(View.VISIBLE);
                select_city_layout.setVisibility(View.GONE);
                consumer_number_layout.setVisibility(View.VISIBLE);
            }
        });
        apartments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select_state_layout.setVisibility(View.GONE);
                select_board_layout.setVisibility(View.GONE);
                select_city_layout.setVisibility(View.VISIBLE);
                consumer_number_layout.setVisibility(View.GONE);
            }
        });

        select_board.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ElectricityBoards.class));
                finish();
            }
        });

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if(bundle != null) {

            boardName = bundle.getString("BOARD_NAME");
            boardCode = bundle.getString("BOARD_CODE");
        }

        select_board.setText(boardName);

        btnproceedToPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stateName = select_state.getText().toString().trim();
                consumerId = consumer_number.getText().toString().trim();

                if (stateName.length() < 1){
                    select_state.setError("Select Your State");
                }
                else if (boardName.length() < 1){
                    select_board.setError("Select Your Board");
                }
                else if (consumerId.length() < 1){
                    consumer_number.setError("Enter Consumer Number");
                }
                else {

                }
            }
        });


        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

// Define a listener that responds to location updates
        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                // Called when a new location is found by the network location provider.
                makeUseOfNewLocation(location);
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }
        };

// Register the listener with the Location Manager to receive location updates
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
    }

    private void makeUseOfNewLocation(Location location) {
        select_state.setText((CharSequence) location);
    }


}
