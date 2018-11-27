package com.example.user.genie;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.user.genie.MobileRecharge.hasPermissions;

public class PayForElectricity extends AppCompatActivity {
    Toolbar toolbar;
    LinearLayout select_state_layout, select_board_layout, select_city_layout, consumer_number_layout, amountLayout;
    EditText select_state, select_city, consumer_number, paidAmount;
    TextView select_board;
    RadioGroup radioGroup;
    RadioButton electricity_boards, apartments;
    String boardName,boardCode;
    String stateName, consumerId;
    Button btnproceedToPay;
    String circle;
    String amount;
    String landline_ca_number;
    String other_values;

    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    String login_user="";

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
        amountLayout = findViewById(R.id.amountLayout);
        btnproceedToPay = findViewById(R.id.btnproceedToPay);

        select_state = findViewById(R.id.select_state);
        select_board = findViewById(R.id.select_board);
        select_city = findViewById(R.id.select_city);
        consumer_number = findViewById(R.id.consumer_number);
        paidAmount = findViewById(R.id.paidAmount);

        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedpreferences.edit();
        login_user = sharedpreferences.getString("FLAG", "");
        editor.commit(); // commit changes


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

        if (bundle != null) {

            boardName = bundle.getString("BOARD_NAME");
            boardCode = bundle.getString("BOARD_CODE");
        }

        select_board.setText(boardName);

        btnproceedToPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stateName = select_state.getText().toString().trim();
                consumerId = consumer_number.getText().toString().trim();
                amount = paidAmount.getText().toString().trim();

                if (stateName.length() < 1) {
                    select_state.setError("Select Your State");
                } else if (boardName.length() < 1) {
                    select_board.setError("Select Your Board");
                } else if (consumerId.length() < 1) {
                    consumer_number.setError("Enter Consumer Number");
                } else if (amount.length() < 1) {
                    paidAmount.setError("Enter Amount");
                } else {
                    new AsynBillSubmit().execute();
                }
            }
        });
    }


    private class AsynBillSubmit extends AsyncTask<Void, Void, Void> {
        ProgressDialog pDialog;
        String success = null,data="",status="true";

        @Override
        protected Void doInBackground(Void... params) {
            pDialog.show();
            ArrayList<NameValuePair> cred = new ArrayList<NameValuePair>();
            cred.add(new BasicNameValuePair("user_id",login_user));//user_email
            cred.add(new BasicNameValuePair("customer_id",consumerId ));
            cred.add(new BasicNameValuePair("operator",boardCode ));
            cred.add(new BasicNameValuePair("circle",circle ));
            cred.add(new BasicNameValuePair("amount",amount ));
            cred.add(new BasicNameValuePair("landline_ca_number",landline_ca_number ));
            cred.add(new BasicNameValuePair("other_values",other_values ));
            Log.v("RES","Sending data" +login_user+ consumerId+ boardCode +circle+amount
                    +landline_ca_number+other_values);


            String urlRouteList="http://demo.ratnatechnology.co.in/genie/index.php/api/service/electricity_insurance_gas_water";
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
                Toast.makeText(getApplicationContext(),"Bill Paid Successfully", Toast.LENGTH_LONG).show();

                startActivity(new Intent(PayForElectricity.this,ThankYouActivity.class));finish();
            }
            else{
                Toast.makeText(getApplicationContext(),data, Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(PayForElectricity.this);
            pDialog.setMessage("Loading In...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
    }



   /* LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

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
    }*/



}
