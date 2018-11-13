package com.example.user.genie;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
    TextView paid_amount;
    String amount;
    RadioGroup radioGroup;
    RadioButton cod_method, debitcard_method, creditcard_method;
    Button proceed_payment_btn;

    private DatePickerDialog fromDatePickerDialog;
    Calendar mcurrenttime;
    private SimpleDateFormat dateFormatter;
    String Date_;
    LinearLayout payment_card_layout;

    @SuppressLint("ResourceAsColor")
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
        paid_amount = findViewById(R.id.paid_amount);
        proceed_payment_btn = findViewById(R.id.proceed_payment_btn);
        radioGroup = findViewById(R.id.radioGroup);
        cod_method = findViewById(R.id.cod_method);
        debitcard_method = findViewById(R.id.debitcard_method);
        creditcard_method = findViewById(R.id.creditcard_method);
        payment_card_layout = findViewById(R.id.payment_card_layout);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        // commit changes
        String price=pref.getString("GIFT_PRICE", null);
        editor.commit();

        paid_amount.setText(this.getResources().getString(R.string.rupee)+" "+price);

        cod_method.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proceed_payment_btn.setVisibility(View.VISIBLE);
                payment_card_layout.setVisibility(View.GONE);
            }
        });

        debitcard_method.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proceed_payment_btn.setVisibility(View.VISIBLE);
                payment_card_layout.setVisibility(View.VISIBLE);
            }
        });

        proceed_payment_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Gift Ordered Successfully", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(),ThankYouActivity.class));
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
