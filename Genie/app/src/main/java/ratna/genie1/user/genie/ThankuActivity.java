package ratna.genie1.user.genie;

import android.app.Application;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import ratna.genie1.user.genie.helper.RegPrefManager;

import ratna.genie1.user.genie.helper.RegPrefManager;

public class ThankuActivity extends AppCompatActivity {
    Toolbar toolbar;
    String back,id,dt;
    private TextView transactionTV,successTv;
    Button continuebtn;
    TextView dateAndtime;
    String groupId;
  //  private Button continue_shopping;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanku);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        back= RegPrefManager.getInstance(this).getBackService();
        id=RegPrefManager.getInstance(this).getSuccessID();
        dt=RegPrefManager.getInstance(this).getDateAndTime();
        continuebtn = findViewById(R.id.continuebtn);
        dateAndtime = findViewById(R.id.date_time);

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
                }else if (back.equals("MobileRecharge")){
                    startActivity(new Intent(ThankuActivity.this,MobileRecharge.class));
                    finish();
                }
            }
        });

        groupId = RegPrefManager.getInstance(ThankuActivity.this).getUserGroup();
        continuebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (groupId.equals("4")){
                    startActivity(new Intent(getApplicationContext(),MainActivity2.class));
                    finish();
                }
                else if (groupId.equals("5")){
                    startActivity(new Intent(getApplicationContext(),MainActivity3.class));
                    finish();
                }
                else if (groupId.equals("3")){
                    startActivity(new Intent(getApplicationContext(),MainActivity4.class));
                    finish();
                }
                else if (groupId.equals("2")){
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    finish();
                }
            }
        });

        dateAndtime.setText("");

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
               // transactionTV.setText(id);
                transactionTV.setText("Transation id is: "+RegPrefManager.getInstance(ThankuActivity.this).getSuccessID());
                dateAndtime.setText("Date and Time is: "+RegPrefManager.getInstance(ThankuActivity.this).getDateAndTime());

            }
            successTv.setText("Success!");
        }
        else if (back.equals("MobileRecharge")){
            if(id!=null) {
                transactionTV.setVisibility(View.VISIBLE);
                // transactionTV.setText(id);
                transactionTV.setText("Transation id is: "+RegPrefManager.getInstance(ThankuActivity.this).getSuccessID());
                dateAndtime.setText("Date and Time : "+RegPrefManager.getInstance(ThankuActivity.this).getDateAndTime());


            }
            successTv.setText("Success!");
        }

        else if (back.equals("MONEYTRANSFER")){
            if (id!=null){
                transactionTV.setVisibility(View.VISIBLE);
                transactionTV.setText("Transation id is: "+RegPrefManager.getInstance(ThankuActivity.this).getSuccessID());
                dateAndtime.setText("Date and Time : "+RegPrefManager.getInstance(ThankuActivity.this).getDateAndTime());
            }
            successTv.setText("Success!");

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
