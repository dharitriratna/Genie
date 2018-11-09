package com.example.user.genie;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class HomeDeliveryGrocery extends AppCompatActivity {
    Toolbar toolbar;
    RadioGroup radioGroup;
    RadioButton add_item, add_image;
    LinearLayout item_layout;
    Button select_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_delivery_grocery);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        radioGroup = findViewById(R.id.radioGroup);
        add_item = findViewById(R.id.add_item);
        add_image = findViewById(R.id.add_image);
        item_layout = findViewById(R.id.item_layout);
        select_btn = findViewById(R.id.select_btn);

        add_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item_layout.setVisibility(View.VISIBLE);
                select_btn.setVisibility(View.GONE);
            }
        });

        add_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item_layout.setVisibility(View.GONE);
                select_btn.setVisibility(View.VISIBLE);
            }
        });
    }
}
