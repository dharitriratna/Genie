package com.example.user.genie;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.user.genie.Adapter.CityMoviesCustomAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MovieActivity extends AppCompatActivity {
    Toolbar toolbar;
    private EditText cityEd;
    private RecyclerView cityRecyclerview;
    ArrayList<String> names;
    private static final int PERMISSION_REQUEST_CODE = 1;
    CityMoviesCustomAdapter adapter;
    private TextView currentlocationTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        intialize();
    }

    private void intialize() {
        cityEd = findViewById(R.id.cityEd);
        cityRecyclerview = findViewById(R.id.cityRecyclerview);
        currentlocationTv=findViewById(R.id.currentlocationTv);
        names = new ArrayList<>();

        names.add("Abohar");
        names.add("Achmpet");
        names.add("Azad");
        names.add("Badami");
        names.add("Badepally");
        names.add("Cheeka");
        names.add("Cheepurupalli");
        names.add("Dahod");
        names.add("Dhar");


        cityRecyclerview = (RecyclerView) findViewById(R.id.cityRecyclerview);


        cityRecyclerview.setHasFixedSize(true);
        cityRecyclerview.setLayoutManager(new LinearLayoutManager(this));

        adapter = new CityMoviesCustomAdapter(MovieActivity.this,names);

        cityRecyclerview.setAdapter(adapter);


        cityEd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                //after the change calling the method and passing the search input
                filter(editable.toString());
            }
        });

        currentlocationTv.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                addPermission();
            }
        });


    }

    private void filter(String text) {
        //new array list that will hold the filtered data
        ArrayList<String> filterdNames = new ArrayList<>();

        //looping through existing elements
        for (String s : names) {
            //if the existing elements contains the search input
            if (s.toLowerCase().contains(text.toLowerCase())) {
                //adding the element to filtered list
                filterdNames.add(s);
            }
        }

        //calling a method of the adapter class and passing the filtered list
        adapter.filterList(filterdNames);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void addPermission() {
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSION_REQUEST_CODE);

            // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
            // app-defined int constant

            return;
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, do your work....
                    locationFind();
                } else {
                    // permission denied
                    // Disable the functionality that depends on this permission.
                }
                return;
            }

            // other 'case' statements for other permssions
        }
    }

    private void locationFind() {
        //TO get the location,manifest file is added with 2 permissions
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);


        //Best location provider is decided by the criteria
        Criteria criteria = new Criteria();
        //location manager will take the best location from the criteria
        locationManager.getBestProvider(criteria, true);

        //nce you know the name of the LocationProvider, you can call getLastKnownPosition() to find out where you were recently.
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, true));
            Geocoder gcd=new Geocoder(getBaseContext(), Locale.getDefault());
            //latTextView.setText(String.valueOf(location.getLatitude()));
            // longTextView.setText(String.valueOf(location.getLongitude()));
            // Log.d("Tag","1");
            List<Address> addresses;

            try {
                addresses=gcd.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                if(addresses.size()>0)

                {
                    //while(locTextView.getText().toString()=="Location") {
                    String cityname = addresses.get(0).getLocality().toString();
                    //   locTextView.setText(cityname);
                    // }
                }
            } catch (IOException e) {
                e.printStackTrace();

            }
            return;
        }

    }
}


