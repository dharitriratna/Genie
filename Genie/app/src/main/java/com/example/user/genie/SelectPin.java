package com.example.user.genie;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

public class SelectPin extends AppCompatActivity {
    RecyclerView select_pin_recyclerview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_pin);
        select_pin_recyclerview = findViewById(R.id.select_pin_recyclerview);

    }
}
