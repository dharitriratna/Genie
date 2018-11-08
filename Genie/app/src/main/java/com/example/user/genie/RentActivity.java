package com.example.user.genie;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;


import com.example.user.genie.Adapter.CityMoviesCustomAdapter;
import com.example.user.genie.Adapter.RentCustomAdapter;

import java.util.ArrayList;

public class RentActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView rentRecyclerview;
    private RentCustomAdapter rentCustomAdapter;
    private ArrayList<String> rentarray;
    private FrameLayout sellFrame;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RentActivity.this,MainActivity.class));
                finish();
            }
        });
        rentRecyclerview=findViewById(R.id.rentRecyclerview);
        sellFrame=findViewById(R.id.sellFrame);
        sellFrame.setOnClickListener(this);
        rentarray=new ArrayList<>();

        rentarray = new ArrayList<>();

        rentarray.add("Abohar");
        rentarray.add("Achmpet");
        rentarray.add("Azad");
        rentarray.add("Badami");
        rentarray.add("Badepally");
        rentarray.add("Cheeka");
        rentarray.add("Cheepurupalli");
        rentarray.add("Dahod");
        rentarray.add("Dhar");




        rentRecyclerview.setHasFixedSize(true);
        rentRecyclerview.setLayoutManager(new LinearLayoutManager(this));

        rentCustomAdapter = new RentCustomAdapter(RentActivity.this,rentarray);

        rentRecyclerview.setAdapter(rentCustomAdapter);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.sellFrame:
                    startActivity(new Intent(RentActivity.this,SellRentActivity.class));
                    finish();
                break;
        }
    }
}
