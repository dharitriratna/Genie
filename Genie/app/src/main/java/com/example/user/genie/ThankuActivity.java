package com.example.user.genie;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.user.genie.helper.RegPrefManager;

public class ThankuActivity extends AppCompatActivity {
    Toolbar toolbar;
    String back,id;
    private TextView transactionTV,successTv;
    Button continuebtn;
  //  private Button continue_shopping;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanku);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        back=RegPrefManager.getInstance(this).getBackService();
        id=RegPrefManager.getInstance(this).getSuccessID();
        continuebtn = findViewById(R.id.continuebtn);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(back.equals("Insurance")){
                    startActivity(new Intent(ThankuActivity.this,AllInsuranseActivity.class));
                    finish();
                }
                else if(back.equals("Landline")){
                    startActivity(new Intent(ThankuActivity.this,LandLine.class));
                    finish();
                }else if(back.equals("Tour")){
                    startActivity(new Intent(ThankuActivity.this,CabBookingActivity.class));
                    finish();
                }else if(back.equals("DTH")){
                    startActivity(new Intent(ThankuActivity.this,DTHRecharge.class));
                    finish();
                }
            }
        });
        continuebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            }
        });

        successTv=findViewById(R.id.successTv);

        transactionTV=findViewById(R.id.transactionTV);
        //continue_shopping=findViewById(R.id.continue_shopping);
        if(back.equals("Insurance")) {
            successTv.setText(RegPrefManager.getInstance(ThankuActivity.this).getInsuranceMessage());
        }
        else if(back.equals("Landline")){
            successTv.setText("Success!!!");

        }
        else if(back.equals("Tour")){
            successTv.setText("Success!!!");
        }
        else if(back.equals("DTH")){

            if(id!=null) {
                transactionTV.setVisibility(View.VISIBLE);
                transactionTV.setText(id);
            }
            successTv.setText("Success!!!");
        }
     /*   continue_shopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ThankuActivity.this,ThankuActivity.class));
                finish();
            }
        });*/
    }


}
