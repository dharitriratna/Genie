package com.example.user.genie;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class GasBillActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView water_board;
    EditText acc_user_name;
    Button btn_water_proceed;
    String operator_circle_name;
    String consumerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gas_bill);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        water_board = findViewById(R.id.water_board);
        acc_user_name = findViewById(R.id.acc_user_name);
        btn_water_proceed = findViewById(R.id.btn_water_proceed);

        water_board.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),GasproviderActivity.class));
                finish();
            }
        });

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if(bundle != null) {

            operator_circle_name = bundle.getString("OPERATOR_NAME");
            water_board.setText(operator_circle_name);
        }

        btn_water_proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                consumerId = acc_user_name.getText().toString().trim();

                if (operator_circle_name.length()< 1){
                    water_board.setError("Select Operator");
                }
                else if (consumerId.length() < 1){
                    acc_user_name.setError("Enter Consumer Id");
                }

                else {

                }
            }
        });
    }
}
