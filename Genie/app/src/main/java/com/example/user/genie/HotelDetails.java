package com.example.user.genie;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.user.genie.Fragments.HotelBottomFragment;

public class HotelDetails extends AppCompatActivity {
    private CollapsingToolbarLayout collapsing_toolbar;
    private Toolbar toolbar;
    AppBarLayout appBarLayout;
    TextView editOption;
    HotelBottomFragment hotelBottomFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_details);
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
        editOption = findViewById(R.id.editOption);
        editOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hotelBottomFragment=new HotelBottomFragment();
                hotelBottomFragment.show(getSupportFragmentManager(),hotelBottomFragment.getTag());
            }
        });
    }
}
