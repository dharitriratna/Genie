package com.example.user.genie;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.genie.helper.RegPrefManager;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;

public class GasBillActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView water_board;
    EditText acc_user_name, paidAmount;
    Button btn_gas_proceed;
    String operator_circle_name;
    String operator_circle_code;
    String consumerId;
    String circle;
    String amount;
    String landline_ca_number;
    String other_values;

    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    String login_user="";

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
        paidAmount = findViewById(R.id.paidAmount);
        btn_gas_proceed = findViewById(R.id.btn_gas_proceed);

        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedpreferences.edit();
        login_user=sharedpreferences.getString("FLAG", "");
        editor.commit(); //


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
            operator_circle_code = bundle.getString("OPERATOR_CODE");
            water_board.setText(operator_circle_name);
        }

        btn_gas_proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                operator_circle_name = water_board.getText().toString().trim();
                consumerId = acc_user_name.getText().toString().trim();
                amount = paidAmount.getText().toString().trim();

                if (operator_circle_name.length()< 1){
                    water_board.setError("Select Operator");
                }
                else if (consumerId.length() < 1){
                    acc_user_name.setError("Enter Consumer Id");
                }
                else if (amount.length() < 1){
                    paidAmount.setError("Enter Amount");
                }
                else {
                    RegPrefManager.getInstance(GasBillActivity.this).setBackService("Gas");
                    RegPrefManager.getInstance(GasBillActivity.this).setServiceName("Gas");
                    Intent intent = new Intent(GasBillActivity.this,PaymentCartActivity.class);
                    intent.putExtra("OperatorName",operator_circle_name);
                    intent.putExtra("ConsumerID", consumerId);
                    intent.putExtra("Amount", amount);
                    startActivity(intent);
                  //  new AsynBillSubmit().execute();
                }
            }
        });
    }

    private class AsynBillSubmit extends AsyncTask<Void, Void, Void> {
        ProgressDialog pDialog;
        String success = null,data="",status="true";

        @Override
        protected Void doInBackground(Void... params) {
            pDialog.show();
            ArrayList<NameValuePair> cred = new ArrayList<NameValuePair>();
            cred.add(new BasicNameValuePair("user_id",login_user));//user_email
            cred.add(new BasicNameValuePair("customer_id",consumerId ));
            cred.add(new BasicNameValuePair("operator",operator_circle_code ));
            cred.add(new BasicNameValuePair("circle",circle ));
            cred.add(new BasicNameValuePair("amount",amount ));
            cred.add(new BasicNameValuePair("landline_ca_number",landline_ca_number ));
            cred.add(new BasicNameValuePair("other_values",other_values ));
            Log.v("RES","Sending data" +login_user+ consumerId+ operator_circle_code +circle+amount
                    +landline_ca_number+other_values);


            String urlRouteList="http://demo.ratnatechnology.co.in/genie/index.php/api/service/electricity_insurance_gas_water";
            try {
                String route_response = CustomHttpClient.executeHttpPost(urlRouteList, cred);

                success = route_response;
                JSONObject jsonObject = new JSONObject(success);

                // user_email=jsonObject.getString("user_email");
                status=jsonObject.getString("status");
                if(status.equals("false")) {
                    data = jsonObject.getString("data");
                }
                String data=jsonObject.getString("data");
                // Toast.makeText(Cart.this, sum_total, Toast.LENGTH_SHORT).show();
                JSONObject jsonObject1 = new JSONObject(data);

               /*  String user_id=jsonObject1.getString("user_id");
                String user_email=jsonObject1.getString("user_email");*/


            } catch (Exception e)

            {
                Log.v("Connection error", e.toString());

            }return null;
        }
        protected void onPostExecute(Void result) {
            pDialog.dismiss();

            if(status.equals("true"))
            {
                Toast.makeText(getApplicationContext(),"Bill Paid Successfully", Toast.LENGTH_LONG).show();

                startActivity(new Intent(GasBillActivity.this,ThankYouActivity.class));finish();
            }
            else{
                Toast.makeText(getApplicationContext(),data, Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(GasBillActivity.this);
            pDialog.setMessage("Loading In...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
    }

}
