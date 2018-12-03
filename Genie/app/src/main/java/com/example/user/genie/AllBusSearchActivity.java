package com.example.user.genie;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;

import com.example.user.genie.Adapter.AllBusSearchAdapter;


public class AllBusSearchActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView busRecyclerView;
    String[] bookingId={"1","2","3","4"};
    String[] busName={"Dolphin","Volvo","Chakadola","Mo Bus"};
    String[] details={"Price 1200", "Price 1800", "Price 500", "Price 780"};
    String[] journeyDuration={"2h 30min", "3h", "4h 40min", "3h 10min"};
    AllBusSearchAdapter allBusSearchAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_bus_search);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        busRecyclerView = findViewById(R.id.busRecyclerView);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(AllBusSearchActivity.this);
        busRecyclerView.setLayoutManager(mLayoutManager);
        allBusSearchAdapter = new AllBusSearchAdapter(AllBusSearchActivity.this, bookingId,busName,details,journeyDuration);
        busRecyclerView.setAdapter(allBusSearchAdapter);

        busRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, busRecyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public boolean onClick(View view, int position) {
                startActivity(new Intent(AllBusSearchActivity.this,BusSeatBookingActivity.class));
                // finish();

                return true;
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }
}
