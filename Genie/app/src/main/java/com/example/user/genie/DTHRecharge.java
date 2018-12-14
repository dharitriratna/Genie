package com.example.user.genie;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.genie.helper.RegPrefManager;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;

public class DTHRecharge extends AppCompatActivity {
    Toolbar toolbar;
    TextView dth_operator_name, circle;
    EditText customer_id, dth_amount;
    LinearLayout browse_dth_plans;
    String operator_name,operator_code,CircleName, CircleId, cutomerId,dth_recharge_amount;
    Button btn_dth_recharge;
    String service_id;
    TextView serviceId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dthrecharge);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(DTHRecharge.this,MainActivity.class));
               finish();
            }
        });


        dth_operator_name = findViewById(R.id.dth_operator_name);
        customer_id = findViewById(R.id.customer_id);
        dth_amount = findViewById(R.id.dth_amount);
        browse_dth_plans = findViewById(R.id.browse_dth_plans);
        btn_dth_recharge = findViewById(R.id.btn_dth_recharge);
        circle = findViewById(R.id.circle);
        serviceId=findViewById(R.id.serviceId);
        service_id = serviceId.getText().toString().trim();
        Log.d("tag", service_id);
        RegPrefManager.getInstance(DTHRecharge.this).setServiceId(service_id);


        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        // commit changes
        String number=pref.getString("CUS_ID", null);
        editor.commit();

        customer_id.setText(number);


        operator_name= RegPrefManager.getInstance(this).getMobileOperatorName();
        operator_code = RegPrefManager.getInstance(this).getMobileOperatorCode();
        dth_operator_name.setText(operator_name);

        CircleName= RegPrefManager.getInstance(this).getMobileCircleName();
        CircleId = RegPrefManager.getInstance(this).getMobileCircleCode();
        circle.setText(CircleName);

        dth_recharge_amount= RegPrefManager.getInstance(this).getMobileRechargeAmount();
        dth_amount.setText(dth_recharge_amount);



        circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),DTHOperatorCircle.class));
                finish();
            }
        });

        operator_name = RegPrefManager.getInstance(DTHRecharge.this).getDTHOperatorName();
        operator_code = RegPrefManager.getInstance(DTHRecharge.this).getDTHOperatorCode();
        dth_operator_name.setText(operator_name);


        browse_dth_plans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cutomerId = customer_id.getText().toString();
                operator_name = dth_operator_name.getText().toString();
                CircleName = circle.getText().toString();


                if (cutomerId.length() < 1){
                    customer_id.setError("Enter Customer Id");
                }
                else if (operator_name.length() < 1){
                    dth_operator_name.setError("Enter Operator Name");
                }
                else if (CircleName.length() < 1){
                    circle.setError("Enter Circle Name");
                }
                else {
                    startActivity(new Intent(getApplicationContext(), DTHBrowsePlansActivity.class));
                    RegPrefManager.getInstance(DTHRecharge.this).setCustomerId(cutomerId);
                }
            }
        });

       /* Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if(bundle != null) {

            operator_name = bundle.getString("OPERATOR_NAME");
            operator_code = bundle.getString("DTH_OPERATOR_CODE");
            dth_operator_name.setText(operator_name);
        }*/

        dth_operator_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("CUS_ID", cutomerId=customer_id.getText().toString());
                editor.commit();
                startActivity(new Intent(DTHRecharge.this,DTHOperators.class));
                //  Toast.makeText(MobileRecharge.this, phone_number, Toast.LENGTH_SHORT).show();
                finish();
            }
        });


        btn_dth_recharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                operator_name = dth_operator_name.getText().toString();
                cutomerId = customer_id.getText().toString();
                dth_recharge_amount = dth_amount.getText().toString();

                if (cutomerId.length() < 1){
                    customer_id.setError("Enter Your Id");
                }
               /* else if (phone_number.length()>1){
                    new AsyngetOperator().execute();
                }*/
                else if (dth_recharge_amount.length() < 1){
                    dth_amount.setError("Enter Your Amount");
                }
                else {
                   // RegPrefManager.getInstance(DTHRecharge.this).setBackService("DTH Bill");
                    RegPrefManager.getInstance(DTHRecharge.this).setServiceName("DTH Bill");
                    RegPrefManager.getInstance(DTHRecharge.this).setBackService("DTH");
                    Intent intent = new Intent(DTHRecharge.this,PaymentCartActivity.class);
                    intent.putExtra("OperatorName",operator_name);
                    intent.putExtra("OperatorCode",operator_code);
                    intent.putExtra("CostumerID", cutomerId);
                    intent.putExtra("DTHAmount", dth_recharge_amount);
                    startActivity(intent);
                    finish();
                  //  new AsynSignInDetails().execute();
                }
            }
        });
    }

    private class AsynSignInDetails extends AsyncTask<Void, Void, Void> {
        ProgressDialog pDialog;
        String success = null,data="",status="true";
        String status_response;
        String err_msg;

        @Override
        protected Void doInBackground(Void... params) {
            pDialog.show();
            ArrayList<NameValuePair> cred = new ArrayList<NameValuePair>();
            //  cred.add(new BasicNameValuePair("user_id",login_user));//user_email
            cred.add(new BasicNameValuePair("operator",operator_code ));
            cred.add(new BasicNameValuePair("customer_id",cutomerId ));
            cred.add(new BasicNameValuePair("amount",dth_recharge_amount ));
            Log.v("RES","Sending data " + operator_code+ operator_code +dth_recharge_amount);


            String urlRouteList="http://demo.ratnatechnology.co.in/genie/api/service/mobile_dth_datacard_recharge";
            try {
                String route_response = CustomHttpClient.executeHttpPost(urlRouteList, cred);

                success = route_response;
                JSONObject jsonObject = new JSONObject(success);

                // user_email=jsonObject.getString("user_email");
                data=jsonObject.getString("data");
                Toast.makeText(DTHRecharge.this, data, Toast.LENGTH_SHORT).show();
                if(status.equals("true")) {
                    data = jsonObject.getString("data");

                }
                else  {
                     Toast.makeText(DTHRecharge.this, data, Toast.LENGTH_SHORT).show();
                    data = jsonObject.getString("data");
                    JSONObject jsonObject1 = new JSONObject(data);
                    status_response = jsonObject1.getString("Status");
                    err_msg = jsonObject1.getString("ErrorMessage");
                    String transactionId = jsonObject1.getString("TransactionDate");
                }
            } catch (Exception e)

            {
                Log.v("Connection error", e.toString());

            }return null;
        }
        protected void onPostExecute(Void result) {
            pDialog.dismiss();

            if(status.equals("true"))
            {
                Toast.makeText(getApplicationContext(),status_response, Toast.LENGTH_LONG).show();

                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("AMOUNT", dth_recharge_amount = dth_amount.getText().toString());
                editor.commit();
                // Toast.makeText(MobileRecharge.this, recharge_amount, Toast.LENGTH_SHORT).show();
                // startActivity(new Intent(MobileRecharge.this,PaymentActivity.class));finish();
            }
            else{
                Toast.makeText(getApplicationContext(),err_msg, Toast.LENGTH_LONG).show();
                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.remove("OPERATOR_NAME");
                editor.clear();
                editor.commit();

            }
        }

        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(DTHRecharge.this);
            pDialog.setMessage("Loading In...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
    }

}
