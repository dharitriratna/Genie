package com.example.user.genie;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.user.genie.Adapter.FlightBaggageCustomAdapter;
import com.example.user.genie.helper.RegPrefManager;

import java.util.ArrayList;

public class FlightBaggageActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView flightListRecyclerview;
    private Toolbar toolbar;
    private TextView listTv,priceTv;
    private ArrayList<String> names;
    FlightBaggageCustomAdapter flightBaggageCustomAdapter;
    private RelativeLayout toolbarRelative;
    private Button continueBtn;

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
        continueBtn=findViewById(R.id.continueBtn);
        priceTv=findViewById(R.id.priceTv);
        priceTv.setText(getResources().getString(R.string.rupee)+"22,600");
        continueBtn.setOnClickListener(this);
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

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.continueBtn:
                startActivity(new Intent(FlightBaggageActivity.this,FlightTravellerInfoActivity.class));
                finish();
                break;
        }
    }
}
