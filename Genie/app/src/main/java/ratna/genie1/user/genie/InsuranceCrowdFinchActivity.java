package ratna.genie1.user.genie;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import ratna.genie1.user.genie.helper.RegPrefManager;

public class InsuranceCrowdFinchActivity extends AppCompatActivity {
    Toolbar toolbar;
    String groupId;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    String login_user="";
    TextView select_board;
    EditText select_state, select_city, consumer_number, paidAmount;

    RadioGroup radioGroup;
    RadioButton electricity_boards, apartments;
    String boardName,boardCode;
    String stateName, consumerId;
    Button btnproceedToPay;
    String circle;
    String amount;
    String landline_ca_number;
    String other_values;
    String service_id;
    TextView serviceId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insurance_crowd_finch);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        groupId = RegPrefManager.getInstance(getApplicationContext()).getUserGroup();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
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

        radioGroup = findViewById(R.id.radioGroup);
       /* radioGroup.clearCheck();
        int selectedId=radioGroup.getCheckedRadioButtonId();*/
        electricity_boards = findViewById(R.id.electricity_boards);
        apartments = findViewById(R.id.apartments);
       /* select_state_layout = findViewById(R.id.select_state_layout);
        select_board_layout = findViewById(R.id.select_board_layout);
        select_city_layout = findViewById(R.id.select_city_layout);
        consumer_number_layout = findViewById(R.id.consumer_number_layout);
        amountLayout = findViewById(R.id.amountLayout);*/
        btnproceedToPay = findViewById(R.id.btnproceedToPay);

       // select_state = findViewById(R.id.select_state);
        select_board = findViewById(R.id.select_board);
      //  select_city = findViewById(R.id.select_city);
        consumer_number = findViewById(R.id.consumer_number);
        paidAmount = findViewById(R.id.paidAmount);


        serviceId=findViewById(R.id.serviceId);
        service_id = serviceId.getText().toString().trim();
        Log.d("tag", service_id);
        RegPrefManager.getInstance(InsuranceCrowdFinchActivity.this).setServiceId(service_id);


        boardName= RegPrefManager.getInstance(InsuranceCrowdFinchActivity.this).getInsurerName();
        select_board.setText(boardName);


        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedpreferences.edit();
        login_user = sharedpreferences.getString("FLAG", "");
        editor.commit(); // commit changes


        select_board.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), InsurersCFActivity.class));
                finish();
            }
        });

        btnproceedToPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  stateName = select_state.getText().toString().trim();
                consumerId = consumer_number.getText().toString().trim();
                amount = paidAmount.getText().toString().trim();

              /*  if (stateName.length() < 1) {
                    select_state.setError("Select Your State");
                } else*/ if (boardName.length() < 1) {
                    select_board.setError("Select Your Insurer");
                } else if (consumerId.length() < 1) {
                    consumer_number.setError("Enter Account Number");
                } else if (amount.length() < 1) {
                    paidAmount.setError("Enter Amount");
                } else {

                    RegPrefManager.getInstance(InsuranceCrowdFinchActivity.this).setBackService("InsuranceCF");
                    RegPrefManager.getInstance(InsuranceCrowdFinchActivity.this).setServiceName("InsuranceCF");
                    Intent intent = new Intent(InsuranceCrowdFinchActivity.this,PaymentCartActivity.class);
                    //  intent.putExtra("StateName",stateName);
                    intent.putExtra("ConsumerID", consumerId);
                    intent.putExtra("Amount", amount);
                    startActivity(intent);
                    //   new AsynBillSubmit().execute();
                }
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
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
        //  return;
    }

}
