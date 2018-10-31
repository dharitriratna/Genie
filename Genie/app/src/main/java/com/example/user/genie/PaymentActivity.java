package com.example.user.genie;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class PaymentActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView month;
    String strDateFormat = "MM";
    SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
    Date date = new Date();
    String month_no;

    private DatePickerDialog fromDatePickerDialog;
    Calendar mcurrenttime;
    private SimpleDateFormat dateFormatter;
    String Date_;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
      //  month = findViewById(R.id.month);

/*
        month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strDateFormat = "MM";
                sdf = new SimpleDateFormat(strDateFormat);
              //  System.out.println("Current Month in MM format : " + sdf.format(date));
                month_no = ( sdf.format(date));
                Toast.makeText(PaymentActivity.this, month_no, Toast.LENGTH_SHORT).show();
            }
        });
*/


    }
}
