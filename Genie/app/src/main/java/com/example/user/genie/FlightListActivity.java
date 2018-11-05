package com.example.user.genie;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.user.genie.Adapter.FlightListAdapter;
import com.example.user.genie.helper.RegPrefManager;

import java.util.ArrayList;

public class FlightListActivity extends AppCompatActivity {
    private RecyclerView flightListRecyclerview;
    private Toolbar toolbar;
    private TextView listTv;
    FlightListAdapter flightListAdapter;
    ArrayList<String> flightlist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_list);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //onBackPressed();
                startActivity(new Intent(FlightListActivity.this,FlightActivity.class));
            }
        });
        listTv=toolbar.findViewById(R.id.listTv);
        String fromplace= RegPrefManager.getInstance(this).getFromPlace();
        String toplace= RegPrefManager.getInstance(this).getToPlace();
        listTv.setText(fromplace+" - "+toplace);
        flightlist=new ArrayList<>();
        intialize();
    }
    private void intialize(){
        flightlist.add("10:55 - 16:15 \n  07:20 - 09:55");
        flightlist.add("07:20 - 09:55 \n 20:10 - 22:50" );
        flightlist.add("13:20 - 11:55 \n 23:10 - 12:50" );
        flightlist.add("07:20 - 09:55 \n 20:10 - 22:50" );
        flightlist.add("11:20 - 09:55 \n 2:10 - 22:50" );
        flightlist.add("05:20 - 09:55 \n 22:10 - 12:50" );
        flightlist.add("07:20 - 09:55 \n  20:10 - 22:50" );

        flightListRecyclerview=findViewById(R.id.flightListRecyclerview);
        flightListRecyclerview.setHasFixedSize(true);
        flightListRecyclerview.setLayoutManager(new LinearLayoutManager(this));

        flightListAdapter = new FlightListAdapter(FlightListActivity.this,flightlist);

        flightListRecyclerview.setAdapter(flightListAdapter);
    }
}
