package com.example.user.genie;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.user.genie.Adapter.FlightBaggageCustomAdapter;
import com.example.user.genie.helper.RegPrefManager;

import java.util.ArrayList;

public class FlightBaggageActivity extends AppCompatActivity {
    private RecyclerView flightListRecyclerview;
    private Toolbar toolbar;
    private TextView listTv;
    private ArrayList<String> names;
    FlightBaggageCustomAdapter flightBaggageCustomAdapter;
    private RelativeLayout toolbarRelative;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_baggage);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        listTv=toolbar.findViewById(R.id.listTv);
        String fromplace= RegPrefManager.getInstance(this).getFromPlace();
        String toplace= RegPrefManager.getInstance(this).getToPlace();
        listTv.setText(fromplace+" - "+toplace);

        intialize();

    }
    private void intialize(){
        flightListRecyclerview=findViewById(R.id.flightListRecyclerview);
        toolbarRelative=findViewById(R.id.toolbarRelative);

        names=new ArrayList<>();

        names.add("Indio 6E-115");
        names.add("Indio 6E-312");
        names.add("Indio 6E-512");
        names.add("Indio 6E-123");


        flightListRecyclerview.setHasFixedSize(true);
        flightListRecyclerview.setLayoutManager(new LinearLayoutManager(this));

        flightBaggageCustomAdapter = new FlightBaggageCustomAdapter(FlightBaggageActivity.this,names);

        flightListRecyclerview.setAdapter(flightBaggageCustomAdapter);

    }
}
