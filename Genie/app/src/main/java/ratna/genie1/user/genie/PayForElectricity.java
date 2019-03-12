package ratna.genie1.user.genie;

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

import ratna.genie1.user.genie.helper.RegPrefManager;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;

import ratna.genie1.user.genie.helper.RegPrefManager;



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
    String service_id;
    TextView serviceId;


    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    String login_user="";

    String statename;
    String groupId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_for_electricity);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        groupId = RegPrefManager.getInstance(getApplicationContext()).getUserGroup();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (groupId.equals("4")){
                    startActivity(new Intent(getApplicationContext(),MainActivity2.class));
                    finish();
                }
                else if (groupId.equals("5")){
                    startActivity(new Intent(getApplicationContext(),MainActivity3.class));
                    finish();
                }
                else if (groupId.equals("3")){
                    startActivity(new Intent(getApplicationContext(),MainActivity4.class));
                    finish();
                }
                else if (groupId.equals("2")){
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    finish();
                }
            }
        });


        serviceId=findViewById(R.id.serviceId);
        service_id = serviceId.getText().toString().trim();
        Log.d("tag", service_id);
        RegPrefManager.getInstance(PayForElectricity.this).setServiceId(service_id);


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



        boardName= RegPrefManager.getInstance(PayForElectricity.this).getElectricityBoardName();
        select_board.setText(boardName);



        select_state.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(getApplicationContext(),ElectricityStateSelectActivity.class));
        }
    });
    sharedpreferences = getSharedPreferences(mypreference,
                                             Context.MODE_PRIVATE);

    SharedPreferences.Editor editor = sharedpreferences.edit();
    login_user = sharedpreferences.getString("FLAG", "");
        editor.commit(); // commit changes

      /*  Intent intent1 = getIntent();
        Bundle bundle1 = intent1.getExtras();

        if(bundle1 != null) {

            stateName = bundle1.getString("CIRCLE_NAME");

            select_state.setText(stateName);
        }*/

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

    /*    Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {

            boardName = bundle.getString("BOARD_NAME");
            boardCode = bundle.getString("BOARD_CODE");
        }

        select_board.setText(boardName);
*/
        btnproceedToPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  stateName = select_state.getText().toString().trim();
                consumerId = consumer_number.getText().toString().trim();
                amount = paidAmount.getText().toString().trim();

              /*  if (stateName.length() < 1) {
                    select_state.setError("Select Your State");
                } else*/ if (boardName.length() < 1) {
                    select_board.setError("Select Your Board");
                } else if (consumerId.length() < 1) {
                    consumer_number.setError("Enter Consumer Number");
                } else if (amount.length() < 1) {
                    paidAmount.setError("Enter Amount");
                } else {

                    RegPrefManager.getInstance(PayForElectricity.this).setBackService("Electricity");
                    RegPrefManager.getInstance(PayForElectricity.this).setServiceName("Electricity");
                    Intent intent = new Intent(PayForElectricity.this,PaymentCartActivity.class);
                  //  intent.putExtra("StateName",stateName);
                    intent.putExtra("ConsumerID", consumerId);
                    intent.putExtra("Amount", amount);
                    startActivity(intent);
                 //   new AsynBillSubmit().execute();
                }
            }
        });
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

    @Override
    public void onResume() {
        super.onResume();
        select_state.setText(RegPrefManager.getInstance(PayForElectricity.this).getElectricityCircle());
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (groupId.equals("4")){
            startActivity(new Intent(getApplicationContext(),MainActivity2.class));
            finish();
        }
        else if (groupId.equals("5")){
            startActivity(new Intent(getApplicationContext(),MainActivity3.class));
            finish();
        }
        else if (groupId.equals("3")){
            startActivity(new Intent(getApplicationContext(),MainActivity4.class));
            finish();
        }
        else if (groupId.equals("2")){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }
        //  return;
    }

}
