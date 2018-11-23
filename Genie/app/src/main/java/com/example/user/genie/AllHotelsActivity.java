package com.example.user.genie;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.user.genie.Adapter.AllHotelAdapter;
import com.example.user.genie.Fragments.FlightBottomFragment;
import com.example.user.genie.Fragments.HotelBottomFragment;
import com.example.user.genie.Model.EventsModel;

public class AllHotelsActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView allhotelsRecyclerView;
    TextView checkIn, cityName, sort, filter, editOption;
    HotelBottomFragment hotelBottomFragment;
    String[] bookingId={"1","2","3","4"};
    String[] details={"Price 1200", "Price 1800", "Price 500", "Price 780"};

    AllHotelAdapter allHotelAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_hotels);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        checkIn = findViewById(R.id.checkIn);
        cityName = findViewById(R.id.cityName);
        sort = findViewById(R.id.sort);
        filter = findViewById(R.id.filter);
        editOption = findViewById(R.id.editOption);
        editOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hotelBottomFragment=new HotelBottomFragment();
                hotelBottomFragment.show(getSupportFragmentManager(),hotelBottomFragment.getTag());
            }
        });

        sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        allhotelsRecyclerView = findViewById(R.id.allhotelsRecyclerView);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(AllHotelsActivity.this);
        allhotelsRecyclerView.setLayoutManager(mLayoutManager);
        allHotelAdapter = new AllHotelAdapter(AllHotelsActivity.this, bookingId,details);
        allhotelsRecyclerView.setAdapter(allHotelAdapter);

        allhotelsRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, allhotelsRecyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public boolean onClick(View view, int position) {
              startActivity(new Intent(AllHotelsActivity.this,HotelDetails.class));
                // finish();

                return true;
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


    }
}
