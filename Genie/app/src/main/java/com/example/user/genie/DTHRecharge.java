package com.example.user.genie;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DTHRecharge extends AppCompatActivity {
    Toolbar toolbar;
    TextView dth_operator_name;
    EditText customer_id, dth_amount;
    LinearLayout browse_dth_plans;
    String operator_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dthrecharge);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        dth_operator_name = findViewById(R.id.dth_operator_name);
        customer_id = findViewById(R.id.customer_id);
        dth_amount = findViewById(R.id.dth_amount);
        browse_dth_plans = findViewById(R.id.browse_dth_plans);

        dth_operator_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),DTHOperators.class));
                finish();
            }
        });

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if(bundle != null) {

            operator_name = bundle.getString("OPERATOR_NAME");
            dth_operator_name.setText(operator_name);
        }

    }
}
