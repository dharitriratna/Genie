package ratna.genie1.user.genie;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import ratna.genie1.user.genie.helper.RegPrefManager;

import ratna.genie1.user.genie.helper.RegPrefManager;

public class FailureActivity extends AppCompatActivity {
    Toolbar toolbar;
    private TextView transactionTV,successTv;
    String back;
    Button continuebtn;
    TextView dateAndtime;
    String groupId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_failure);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        groupId = RegPrefManager.getInstance(getApplicationContext()).getUserGroup();
        back=  RegPrefManager.getInstance(this).getBackService();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(back.equals("Insurance")){
                    startActivity(new Intent(FailureActivity.this,PaymentCartActivity.class));
                    finish();
                }
                else if(back.equals("Landline")){
                    startActivity(new Intent(FailureActivity.this,PaymentCartActivity.class));
                    finish();
                }
                else if(back.equals("MobileRecharge")){
                    startActivity(new Intent(FailureActivity.this,PaymentCartActivity.class));
                    finish();

                }
                else if(back.equals("DTH")){
                    startActivity(new Intent(FailureActivity.this,PaymentCartActivity.class));
                    finish();
                }
            }
        });
        String dt=RegPrefManager.getInstance(this).getDateAndTime();
        dateAndtime = findViewById(R.id.date_time);

        continuebtn = findViewById(R.id.continuebtn);
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

        successTv=findViewById(R.id.successTv);

        transactionTV=findViewById(R.id.transactionTV);
        transactionTV.setText("Transation id is: "+RegPrefManager.getInstance(FailureActivity.this).getSuccessID());
        dateAndtime.setText("Date and Time : "+RegPrefManager.getInstance(FailureActivity.this).getDateAndTime());
        //continue_shopping=findViewById(R.id.continue_shopping);
       // successTv.setText(RegPrefManager.getInstance(FailureActivity.this).getInsuranceMessage());


    }
}
