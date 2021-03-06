package ratna.genie1.user.genie;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import ratna.genie1.user.genie.ObjectNew.CabResponse;
import ratna.genie1.user.genie.ObjectNew.DatacardResponse;
import ratna.genie1.user.genie.ObjectNew.InsurancePaymentResponse;
import ratna.genie1.user.genie.ObjectNew.LandlineResponse;
import ratna.genie1.user.genie.ObjectNew.LoginResponse;
import ratna.genie1.user.genie.ObjectNew.MobileRechargeResponse;
import ratna.genie1.user.genie.Utils.VolleySingleton;
import ratna.genie1.user.genie.client.ApiClientGenie;
import ratna.genie1.user.genie.client.ApiClientGenie1;
import ratna.genie1.user.genie.client.ApiInterface;
import ratna.genie1.user.genie.helper.RegPrefManager;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.JsonObject;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ratna.genie1.user.genie.client.ApiClientGenie1;
import ratna.genie1.user.genie.client.ApiInterface;
import ratna.genie1.user.genie.helper.RegPrefManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentCartActivity extends AppCompatActivity implements View.OnClickListener {
    Toolbar toolbar;
    private AlertDialog.Builder alertDialog;
    ApiInterface apiService;
    TextView numberTv,servicenameTv,mobileTv,amountpTv,walletpTv,amountTv;
    CheckBox checkBox1;
    String back;
    Button btn_order;
    ProgressDialog progressDialog;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    String login_user="";

    int i = 0;
    String status_response;
    String err_msg;

    String PhoneNumber;
    String OperatorName;
    String OperatorCode,ApiTransID;
    String CircleName;
    String CircleCode;
    String RechargeAmount;
    String responce;
    String errorMessage;
    boolean status;

    String operator;
    String circle;
    String landline_ca_number;
    String other_values;

    //electricity
    String SelectStateName;
    String ConsumerID;
    String BillAmount;
    String circle_Code;

    //water
    String Boardname;
    String ConsumerId;
    String PayAmount;
    String offerid;

    //DTH
    String DTHoperatorName;
    String DTHoperatorCode;
    String DTHcircleCode;
    String DTHcustomerId;
    String DTHbillAmount,Successid,DateAndTime;

    //Insurance
    String insurerOpName;
    String insurerOpCode;
    String accountNumber;
    String InAmount;

    CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_cart);
        toolbar = findViewById(R.id.toolbar);
        apiService =
                ApiClientGenie1.getClient().create(ApiInterface.class);
        progressDialog =new ProgressDialog(this);
        alertDialog=new AlertDialog.Builder(this);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        back= RegPrefManager.getInstance(this).getBackService();
        checkBox = findViewById(R.id.checkBox);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(back.equals("Insurance")){
                    startActivity(new Intent(PaymentCartActivity.this,InsuranceDetailsActivity.class));
                   // onBackPressed();
                    finish();
                }
                else if(back.equals("Landline")){
                    startActivity(new Intent(PaymentCartActivity.this,LandLine.class));
                   // onBackPressed();
                    finish();
                }else if(back.equals("Tour")){
                    startActivity(new Intent(PaymentCartActivity.this,CabBookingActivity.class));
                   // onBackPressed();
                    finish();
                }
                else if(back.equals("Datacard")){
                    startActivity(new Intent(PaymentCartActivity.this,DataCardActivity.class));
                   // onBackPressed();
                    finish();
                }
                else if(back.equals("MobileRecharge")){
                    startActivity(new Intent(PaymentCartActivity.this,MobileRecharge.class));
                   // onBackPressed();
                    finish();
                }
                else if(back.equals("Electricity")){
                    startActivity(new Intent(PaymentCartActivity.this,PayForElectricity.class));
                    //onBackPressed();
                    finish();
                }
                else if(back.equals("WaterBill")){
                    startActivity(new Intent(PaymentCartActivity.this,WaterBill.class));
                 //   onBackPressed();
                    finish();
                }
                else if (back.equals("Gas")){
                   // onBackPressed();
                   startActivity(new Intent(PaymentCartActivity.this,GasBillActivity.class));

                    finish();

                }

                else if(back.equals("DTH")){
                   startActivity(new Intent(PaymentCartActivity.this,DTHRecharge.class));
                   finish();
                }

                else if (back.equals("InsuranceCF")){
                    startActivity(new Intent(PaymentCartActivity.this,InsuranceCrowdFinchActivity.class));
                    finish();
                }
            }
        });
        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor1 = sharedpreferences.edit();
        login_user=sharedpreferences.getString("FLAG", "");
        editor1.commit(); // commit changes


        Log.d("login_user", login_user);

        DTHcircleCode=RegPrefManager.getInstance(this).getMobileCircleCode();

        intialize();
    }
    private void intialize(){
        numberTv=findViewById(R.id.numberTv);
        servicenameTv=findViewById(R.id.servicenameTv);
        mobileTv=findViewById(R.id.mobileTv);
        amountpTv=findViewById(R.id.amountpTv);
        walletpTv=findViewById(R.id.walletpTv);
        checkBox1=findViewById(R.id.checkBox1);
        btn_order=findViewById(R.id.btn_order);
        btn_order.setOnClickListener(this);
        amountTv=findViewById(R.id.amountTv);

    }

    @Override
    public void onResume(){
        super.onResume();
        if(back.equals("Insurance")){
            servicenameTv.setText(RegPrefManager.getInstance(this).getServiceName());
            mobileTv.setText(""+RegPrefManager.getInstance(this).getPolicyId());
            amountpTv.setText(RegPrefManager.getInstance(this).getServiceAmount());
        }

        else if(back.equals("Landline")){
            servicenameTv.setText(RegPrefManager.getInstance(this).getServiceName());
            mobileTv.setText(RegPrefManager.getInstance(this).getLandlineBroadbandoperatorid());
            amountpTv.setText(RegPrefManager.getInstance(this).getLandlineBroadbandamount());

        }else if(back.equals("Tour")){
            amountTv.setVisibility(View.GONE);
            servicenameTv.setText(RegPrefManager.getInstance(this).getServiceName());
            mobileTv.setText(RegPrefManager.getInstance(this).getCABFORM()+" - "+RegPrefManager.getInstance(this).getCABTO());

        }else if(back.equals("Datacard")) {
            servicenameTv.setText(RegPrefManager.getInstance(this).getServiceName());
            mobileTv.setText(RegPrefManager.getInstance(this).getDatacardCus());
            amountpTv.setText(RegPrefManager.getInstance(this).getDatacardAmount());
        }

        else if(back.equals("MobileRecharge")){
            numberTv.setText(RegPrefManager.getInstance(this).getPhoneNo());
            Intent intent = getIntent();
            Bundle bundle = intent.getExtras();

            if(bundle != null) {

                PhoneNumber = bundle.getString("PhoneNumber");
                OperatorName = bundle.getString("CarrierName");
                OperatorCode = bundle.getString("CarrierCode");
                CircleName = bundle.getString("CircleName");
                CircleCode = bundle.getString("CircleCode");
                RechargeAmount = bundle.getString("RechargeAmount");

                Log.d("url", RechargeAmount);

            }
            numberTv.setText(PhoneNumber);
            servicenameTv.setText(RegPrefManager.getInstance(this).getServiceName());
            mobileTv.setText(OperatorName);
            amountpTv.setText(getResources().getString(R.string.rupee)+RechargeAmount);
        }

        else if (back.equals("InsuranceCF")){
            Intent intent = getIntent();
            Bundle bundle = intent.getExtras();

            if (bundle != null){
                insurerOpName = bundle.getString("StateName");
                accountNumber = bundle.getString("ConsumerID");
                InAmount = bundle.getString("Amount");
                Log.d("am", InAmount);
            }

            numberTv.setText(accountNumber);
            servicenameTv.setText(RegPrefManager.getInstance(this).getInsurerName());
           // mobileTv.setText(insurerOpName);
            amountpTv.setText(getResources().getString(R.string.rupee)+InAmount);
        }

        else if (back.equals("Electricity")){
            Intent intent = getIntent();
            Bundle bundle = intent.getExtras();

            if(bundle != null) {

                SelectStateName = bundle.getString("StateName");
                ConsumerID = bundle.getString("ConsumerID");
                BillAmount = bundle.getString("Amount");

                Log.d("am", BillAmount);
            }

            numberTv.setText(ConsumerID);
            servicenameTv.setText(RegPrefManager.getInstance(this).getServiceName());
            mobileTv.setText(SelectStateName);
            amountpTv.setText(getResources().getString(R.string.rupee)+BillAmount);
        }

        else if (back.equals("WaterBill")){
            Intent intent = getIntent();
            Bundle bundle = intent.getExtras();

            if(bundle != null) {

                Boardname = bundle.getString("BoardName");
                ConsumerId = bundle.getString("ConsumerID");
                PayAmount = bundle.getString("Amount");
                Log.d("wp", PayAmount);
            }

            numberTv.setText(ConsumerID);
            servicenameTv.setText(RegPrefManager.getInstance(this).getServiceName());
            mobileTv.setText(Boardname);
            amountpTv.setText(getResources().getString(R.string.rupee)+PayAmount);
        }

        else if (back.equals("Gas")){
            Intent intent = getIntent();
            Bundle bundle = intent.getExtras();

            if(bundle != null) {

                SelectStateName = bundle.getString("OperatorName");
                ConsumerID = bundle.getString("ConsumerID");
                BillAmount = bundle.getString("Amount");

                Log.d("am", BillAmount);
            }

            numberTv.setText(ConsumerID);
            servicenameTv.setText(RegPrefManager.getInstance(this).getServiceName());
            mobileTv.setText(SelectStateName);
            amountpTv.setText(getResources().getString(R.string.rupee)+BillAmount);
        }


        else if (back.equals("DTH")){
            Intent intent = getIntent();
            Bundle bundle = intent.getExtras();

            if(bundle != null) {

                DTHoperatorName = bundle.getString("OperatorName");
                DTHoperatorCode = bundle.getString("OperatorCode");
                DTHcustomerId = bundle.getString("CostumerID");
                DTHbillAmount = bundle.getString("DTHAmount");
                Log.d("dtham", DTHbillAmount);
            }

            numberTv.setText(DTHcustomerId);
            servicenameTv.setText(RegPrefManager.getInstance(this).getServiceName());
            mobileTv.setText(DTHoperatorName);
            amountpTv.setText(getResources().getString(R.string.rupee)+DTHbillAmount);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_order:
                if(back.equals("Insurance")){
                    if (isNetworkAvailable()) {
                        InsurancePayment();
                    } else {
                        noNetwrokErrorMessage();
                    }

                }
                if(back.equals("Landline")){
                    if (checkBox.isChecked()&&isNetworkAvailable()) {
                        networkLandlineService();
                    }
                    else if (!checkBox.isChecked()){
                        Toast.makeText(this, "Pay From Wallet", Toast.LENGTH_SHORT).show();
                    }else {
                        noNetwrokErrorMessage();
                    }
                }

                if (back.equals("InsuranceCF")){
                    if (checkBox.isChecked()&&isNetworkAvailable()){
                        new AsyncInsurancePayment().execute();
                    }
                    else if (!checkBox.isChecked()){
                        Toast.makeText(this, "Pay From Wallet", Toast.LENGTH_SHORT).show();
                    }

                    else {
                        noNetwrokErrorMessage();
                    }
                }
                if (back.equals("Tour")){
                    if (isNetworkAvailable()) {
                        networkCabService();
                    } else {
                        noNetwrokErrorMessage();
                    }
                }
                if (back.equals("MobileRecharge")){
                    if (checkBox.isChecked()&& isNetworkAvailable()){
                      //  new AsynSignInDetails().execute();
                    //    getMobileRechargeResponse();
                        volleyResponse();
                    }
                    else if (!checkBox.isChecked()){
                        Toast.makeText(this, "Pay From Wallet", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        noNetwrokErrorMessage();
                    }
                }
                if (back.equals("Electricity")){
                    if (checkBox.isChecked()&&isNetworkAvailable()){
                        new AsynBillSubmit().execute();
                    }
                    else if (!checkBox.isChecked()){
                        Toast.makeText(this, "Pay From Wallet", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        noNetwrokErrorMessage();
                    }
                }
                if (back.equals("WaterBill")){
                    if (checkBox.isChecked()&&isNetworkAvailable()){
                        new AsynWaterBillSubmit().execute();
                    }
                    else if (!checkBox.isChecked()){
                        Toast.makeText(this, "Pay From Wallet", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        noNetwrokErrorMessage();
                    }
                }
                if (back.equals("Gas")){
                    if (checkBox.isChecked()&&isNetworkAvailable()){
                        new AsynWaterBillSubmit().execute();
                    }
                    else if (!checkBox.isChecked()){
                        Toast.makeText(this, "Pay From Wallet", Toast.LENGTH_SHORT).show();
                    }else {
                        noNetwrokErrorMessage();
                      //  new AsynBillSubmit().execute();
                    }
                }
                if (back.equals("DTH")){
                    if (checkBox.isChecked()&& isNetworkAvailable()){
                      //  new AsynDTHpayment().execute();
                     //   getDTHRechargeResponse();
                        volleyResponse1();
                    }
                    else if (!checkBox.isChecked()){
                        Toast.makeText(this, "Pay From Wallet", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        noNetwrokErrorMessage();
                    }
                }
                if(back.equals("Datacard")) {
                    if (checkBox.isChecked() && isNetworkAvailable()) {
                        networkDataCardRecharge();
                    }
                    else if (!checkBox.isChecked()){
                        Toast.makeText(this, "Pay From Wallet", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        noNetwrokErrorMessage();
                    }
                }
                break;
        }

    }
    private void InsurancePayment(){
        progressDialog.setMessage("Please wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        String policyno=RegPrefManager.getInstance(this).getPolicyId();
        String reqid=RegPrefManager.getInstance(this).getReqId();
        Call<InsurancePaymentResponse> call=apiService.postInsurancePayment(login_user,reqid,policyno);
        call.enqueue(new Callback<InsurancePaymentResponse>() {
            @Override
            public void onResponse(Call<InsurancePaymentResponse> call, Response<InsurancePaymentResponse> response) {
                progressDialog.dismiss();
                String status=response.body().getData().getStatus();
                if(status.equals("success")){
                    RegPrefManager.getInstance(PaymentCartActivity.this).setInsuranceMessage(response.body().getData().getMessage());
                    //  Toast.makeText(getApplicationContext(),response.body().getData().getMessage(),Toast.LENGTH_LONG).show();
                    startActivity(new Intent(PaymentCartActivity.this,ThankuActivity.class));
                    finish();
                }
                else {
                    progressDialog.dismiss();
                    startActivity(new Intent(PaymentCartActivity.this,FailureActivity.class));
                    finish();
                    Toast.makeText(getApplicationContext(),"Failed.Try again!",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<InsurancePaymentResponse> call, Throwable t) {
                progressDialog.dismiss();
                startActivity(new Intent(PaymentCartActivity.this,FailureActivity.class));
                finish();
                Toast.makeText(getApplicationContext(), "Genie is away! Try after sometime", Toast.LENGTH_LONG).show();
            }
        });
    }
    public boolean isNetworkAvailable(){
        ConnectivityManager connectivityManager= (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    public void noNetwrokErrorMessage(){
        alertDialog.setTitle("Error!");
        alertDialog.setMessage("No internet connection. Please check your internet setting.");
        alertDialog.setCancelable(true);
        alertDialog.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alert=alertDialog.create();
        alert.show();

    }

    private void networkLandlineService(){
        progressDialog.setMessage("Please wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        String customer_id=RegPrefManager.getInstance(this).getLandlineBroadbandCustid();
        String operator=RegPrefManager.getInstance(this).getLandlineBroadbandoperatorid();
        String back= RegPrefManager.getInstance(this).getBack();
        String circle;
        if(back.equals("Landline1"))
        {
            circle=RegPrefManager.getInstance(this).getLandlinecirclecode();
        }else {
            circle=RegPrefManager.getInstance(this).gettDDataCardCircleCode();
        }
        // String circle=circleTv.getText().toString();
        String accountno=RegPrefManager.getInstance(this).getLandlineBroadbandaccountno();
        String amount=RegPrefManager.getInstance(this).getLandlineBroadbandamount();
        String std="00";

        Call<LandlineResponse> call=apiService.postLandlineRecharge(Integer.parseInt(login_user),
                customer_id,operator,Integer.parseInt(circle),Integer.parseInt(amount),accountno,std);

        call.enqueue(new Callback<LandlineResponse>() {
            @Override
            public void onResponse(Call<LandlineResponse> call, Response<LandlineResponse> response) {
                progressDialog.dismiss();
                String  status=response.body().getData().getStatus();
                if(status.equals("FAILURE")){
                    RegPrefManager.getInstance(PaymentCartActivity.this).setBackService("Landline");
                    RegPrefManager.getInstance(PaymentCartActivity.this).setSuccessID(response.body().getData().getApiTransID());
                    Toast.makeText(getApplicationContext(),response.body().getData().getErrorMessage(),Toast.LENGTH_LONG).show();
                    startActivity(new Intent(PaymentCartActivity.this,FailureActivity.class));
                    finish();
                }
                else {
                    RegPrefManager.getInstance(PaymentCartActivity.this).setSuccessID(response.body().getData().getApiTransID());
                    startActivity(new Intent(PaymentCartActivity.this,ThankuActivity.class));
                    finish();
                }
             //  Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<LandlineResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),"Recharge Failed, Try again",Toast.LENGTH_LONG).show();
                startActivity(new Intent(PaymentCartActivity.this,FailureActivity.class));
                Toast.makeText(getApplicationContext(), "Genie is away! Try after sometime", Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }

    private void networkCabService(){
        progressDialog.setMessage("Loading");
        progressDialog.show();

        Call<CabResponse> call=apiService.postCabBooking(Integer.parseInt(login_user),Integer.parseInt(
                RegPrefManager.getInstance(this).getCABTRIPVALUE()),
                RegPrefManager.getInstance(this).getCABFORM(),
                RegPrefManager.getInstance(this).getCABTO(),
                RegPrefManager.getInstance(this).getCABTRAVELDATE(),
                RegPrefManager.getInstance(this).getCABRETURNDATE(),
                RegPrefManager.getInstance(this).getCABTIME());
        call.enqueue(new Callback<CabResponse>() {
            @Override
            public void onResponse(Call<CabResponse> call, Response<CabResponse> response) {
                progressDialog.dismiss();
                boolean status=response.body().isStatus();
                String data=response.body().getData();
                Toast.makeText(getApplicationContext(),data,Toast.LENGTH_SHORT).show();

                RegPrefManager.getInstance(PaymentCartActivity.this).setBackService("Tour");
                startActivity(new Intent(PaymentCartActivity.this,ThankuActivity.class));
                finish();
                /*fromEd.getText().clear();
                toEd.getText().clear();
                deppEd.getText().clear();
                depEd.getText().clear();
                arrivalEd.getText().clear();
                timeEd.getText().clear();*/
                //  Log.d("Tag","sta");
            }

            @Override
            public void onFailure(Call<CabResponse> call, Throwable t) {
                progressDialog.dismiss();
                startActivity(new Intent(PaymentCartActivity.this,FailureActivity.class));
                finish();
                Log.d("Tag","Failure");
                Toast.makeText(getApplicationContext(), "Genie is away! Try after sometime", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void networkDataCardRecharge(){
        progressDialog.setMessage("Please wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        String v=RegPrefManager.getInstance(this).getDatacardCus();
        Log.d("Tag:value",v);
        String cus=RegPrefManager.getInstance(this).getDatacardCus();

        int amount=Integer.parseInt(RegPrefManager.getInstance(this).getDatacardAmount());
        //if (prepaid.isChecked()) {
          int  circlecode = Integer.parseInt(RegPrefManager.getInstance(this).gettDDataCardCircleCode());
        //}else {
        //    circlecode=0;
        //}

        String operatorcode=RegPrefManager.getInstance(this).getDataCardOperatorCode();
        Call<DatacardResponse> call=apiService.postDatacardRecharge(Integer.parseInt(login_user),cus,operatorcode,circlecode,amount);
        call.enqueue(new Callback<DatacardResponse>() {
            @Override
            public void onResponse(Call<DatacardResponse> call, Response<DatacardResponse> response) {
                progressDialog.dismiss();
                String id=response.body().getData().getApiTransID();
                String status=response.body().getData().getStatus();
                if (status.equals("Failure")){
                    startActivity(new Intent(PaymentCartActivity.this,FailureActivity.class));
                    finish();
                }else {
                    Toast.makeText(getApplicationContext(), status, Toast.LENGTH_LONG).show();
                    startActivity(new Intent(PaymentCartActivity.this, ThankuActivity.class));
                    finish();
                }
            }

            @Override
            public void onFailure(Call<DatacardResponse> call, Throwable t) {
                progressDialog.dismiss();
                startActivity(new Intent(PaymentCartActivity.this,FailureActivity.class));
                finish();
                Toast.makeText(getApplicationContext(), "Genie is away! Try after sometime", Toast.LENGTH_LONG).show();
            }
        });
    }


    private class AsynSignInDetails extends AsyncTask<Void, Void, Void> {
        ProgressDialog pDialog;
        String success = null,data="",status="";
        String status_response;
        String err_msg;
        String value=RegPrefManager.getInstance(PaymentCartActivity.this).getMobileOperatorCode();
        String opName =RegPrefManager.getInstance(PaymentCartActivity.this).getMobileOperatorName();
        String cirle_code=RegPrefManager.getInstance(PaymentCartActivity.this).getMobileCircleCode();
        String service_id=RegPrefManager.getInstance(PaymentCartActivity.this).getServiceId();
     //   String constant = RegPrefManager.getInstance()
        @Override
        protected Void doInBackground(Void... params) {
            pDialog.show();
            ArrayList<NameValuePair> cred = new ArrayList<NameValuePair>();
            cred.add(new BasicNameValuePair("user_id",login_user));//user_email
            cred.add(new BasicNameValuePair("customer_id",PhoneNumber ));
            cred.add(new BasicNameValuePair("operator",value ));
            cred.add(new BasicNameValuePair("operator_name",opName ));
            cred.add(new BasicNameValuePair("circle",cirle_code ));
            Log.d("cn", CircleName);
            cred.add(new BasicNameValuePair("amount",RechargeAmount ));
            cred.add(new BasicNameValuePair("service_id",service_id ));
            Log.v("RES","Sending data " + PhoneNumber+ value +opName+cirle_code+RechargeAmount+service_id);

            String urlRouteList="https://genieservice.in/api/service/mobile_dth_datacard_recharge1";
            try {
                String route_response = CustomHttpClient.executeHttpPost(urlRouteList, cred);

                success = route_response;
                JSONObject jsonObject = new JSONObject(success);

                JSONObject jsonObject1=jsonObject.getJSONObject("data");
                status_response = jsonObject1.getString("Status");
                err_msg = jsonObject1.getString("ErrorMessage");
                Successid=jsonObject1.getString("ApiTransID");
                DateAndTime=jsonObject1.getString("TransactionDate");

                RegPrefManager.getInstance(PaymentCartActivity.this).setSuccessID(Successid);
                RegPrefManager.getInstance(PaymentCartActivity.this).setDateAndTime(DateAndTime);

            } catch (Exception e)

            {
                Log.v("Connection error", e.toString());

            }return null;
        }
        protected void onPostExecute(Void result) {
            pDialog.dismiss();
            Log.d("result===========>", String.valueOf(result));
            try {

                if (status_response.contains("S")) {
                    Toast.makeText(getApplicationContext(), "Recharge Successful", Toast.LENGTH_LONG).show();
                    RegPrefManager.getInstance(PaymentCartActivity.this).setBackService("MobileRecharge");
                    startActivity(new Intent(PaymentCartActivity.this, ThankuActivity.class));

                    SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("AMOUNT", RechargeAmount);
                    editor.commit();
                    // Toast.makeText(MobileRecharge.this, recharge_amount, Toast.LENGTH_SHORT).show();
                    // startActivity(new Intent(MobileRecharge.this,PaymentActivity.class));finish();
                } else if (status_response.contains("P")) {
                    Toast.makeText(getApplicationContext(), "Pending", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(PaymentCartActivity.this, PendingActivity.class));
                } else {
                    Toast.makeText(getApplicationContext(), "Recharge Failed! Try again", Toast.LENGTH_LONG).show();
                    RegPrefManager.getInstance(PaymentCartActivity.this).setBackService("MobileRecharge");
                    startActivity(new Intent(PaymentCartActivity.this, FailureActivity.class));
                    SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.remove("PHONE_NUMBER");
                    editor.clear();
                    editor.commit();
                }
            }catch (Exception e){
                Toast.makeText(getApplicationContext(), "Some Error Occured! Try after sometime", Toast.LENGTH_LONG).show();
                Toast.makeText(PaymentCartActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(PaymentCartActivity.this);
            pDialog.setMessage("Loading In...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
    }


    private void getMobileRechargeResponse(){
        progressDialog.setMessage("Please wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        String value=RegPrefManager.getInstance(PaymentCartActivity.this).getMobileOperatorCode();
        String opName =RegPrefManager.getInstance(PaymentCartActivity.this).getMobileOperatorName();
        String cirle_code=RegPrefManager.getInstance(PaymentCartActivity.this).getMobileCircleCode();
   //     String service=RegPrefManager.getInstance(PaymentCartActivity.this).getServiceId();
        String mobileServiceId = "13";

        Call<MobileRechargeResponse> call=apiService.postRecharge(login_user,PhoneNumber,value,opName,cirle_code,mobileServiceId,RechargeAmount);
        call.enqueue(new Callback<MobileRechargeResponse>() {
            @Override
            public void onResponse(Call<MobileRechargeResponse> call, Response<MobileRechargeResponse> response) {
                try{
                progressDialog.dismiss();
                boolean status =response.body().isStatus();

                if (status==true){
                    err_msg = response.body().getData().getErrorMessage();
                    status_response = response.body().getData().getStatus();
                    String opRefNo = response.body().getData().getOperatorRef();
                    Successid = response.body().getData().getApiTransID();
                    DateAndTime = response.body().getData().getTransactionDate();

                    RegPrefManager.getInstance(PaymentCartActivity.this).setSuccessID(Successid);
                    RegPrefManager.getInstance(PaymentCartActivity.this).setDateAndTime(DateAndTime);

                    if (status_response.contains("S")) {
                        Toast.makeText(getApplicationContext(), "Recharge Successful", Toast.LENGTH_LONG).show();
                        RegPrefManager.getInstance(PaymentCartActivity.this).setBackService("MobileRecharge");
                        startActivity(new Intent(PaymentCartActivity.this, ThankuActivity.class));

                     /*   SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("AMOUNT", RechargeAmount);
                        editor.commit();*/
                        // Toast.makeText(MobileRecharge.this, recharge_amount, Toast.LENGTH_SHORT).show();
                        // startActivity(new Intent(MobileRecharge.this,PaymentActivity.class));finish();
                    } else if (status_response.contains("P")) {
                        Toast.makeText(getApplicationContext(), "Pending", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(PaymentCartActivity.this, PendingActivity.class));
                    } else {
                        Toast.makeText(getApplicationContext(), "Recharge Failed! Try again", Toast.LENGTH_LONG).show();
                        RegPrefManager.getInstance(PaymentCartActivity.this).setBackService("MobileRecharge");
                        startActivity(new Intent(PaymentCartActivity.this, FailureActivity.class));
                        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                        SharedPreferences.Editor editor = pref.edit();
                        editor.remove("PHONE_NUMBER");
                        editor.clear();
                        editor.commit();
                    }
                }
                else {
                    err_msg = response.body().getData().getErrorMessage();
                    Toast.makeText(PaymentCartActivity.this, err_msg, Toast.LENGTH_SHORT).show();
                }
            }catch (Exception ex){
                    ex.printStackTrace();
                    Toast.makeText(PaymentCartActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MobileRechargeResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),"Network Problem!!!", Toast.LENGTH_LONG).show();
            }
        });

    }


    private class AsynBillSubmit extends AsyncTask<Void, Void, Void> {
        ProgressDialog pDialog;
        String success = null,data="";
        boolean status;
        String status_response;
        String err_msg;
        String service_id=RegPrefManager.getInstance(PaymentCartActivity.this).getServiceId();
        String operatorName = RegPrefManager.getInstance(PaymentCartActivity.this).getElectricityBoardName();
        String operatorCode = RegPrefManager.getInstance(PaymentCartActivity.this).getElectricityBoardCode();
        String circle_Code = RegPrefManager.getInstance(PaymentCartActivity.this).getCircleCodeElectricity();

        @Override
        protected Void doInBackground(Void... params) {
            pDialog.show();
            ArrayList<NameValuePair> cred = new ArrayList<NameValuePair>();
            cred.add(new BasicNameValuePair("user_id",login_user));//user_email
            cred.add(new BasicNameValuePair("customer_id",ConsumerID ));
            cred.add(new BasicNameValuePair("operator_name", operatorName ));
            cred.add(new BasicNameValuePair("operatorCode", operatorCode ));
            cred.add(new BasicNameValuePair("amount",BillAmount ));
          /*  cred.add(new BasicNameValuePair("circle",circle_Code ));
            cred.add(new BasicNameValuePair("landline_ca_number",landline_ca_number ));
            cred.add(new BasicNameValuePair("other_values",other_values ));*/
            cred.add(new BasicNameValuePair("service_id",service_id ));
            cred.add(new BasicNameValuePair("offer_id",offerid ));
            Log.v("RES","Sending data" +login_user+ SelectStateName+ operatorName+operatorCode +circle+BillAmount
                    +landline_ca_number+other_values+service_id+offerid);


            String urlRouteList="https://genieservice.in/api/service/Crowdfinch_electricity";
            try {
                String route_response = CustomHttpClient.executeHttpPost(urlRouteList, cred);

                success = route_response;
                JSONObject jsonObject = new JSONObject(success);

                JSONObject jsonObject1=jsonObject.getJSONObject("data");
                status_response = jsonObject1.getString("status");
                err_msg = jsonObject1.getString("message");
                String number = jsonObject1.getString("number");
                Successid=jsonObject1.getString("transid");
                String opratorid = jsonObject1.getString("opratorid");

                RegPrefManager.getInstance(PaymentCartActivity.this).setSuccessID(Successid);
                RegPrefManager.getInstance(PaymentCartActivity.this).setDateAndTime(DateAndTime);


            } catch (Exception e)

            {
                Log.v("Connection error", e.toString());

            }return null;
        }
        protected void onPostExecute(Void result) {
            pDialog.dismiss();
            try {

               /* if (status==false&&data.equals("wallet balance low")){
                    Toast.makeText(PaymentCartActivity.this, data, Toast.LENGTH_SHORT).show();
                }
                else if (status==true&&data.equals("")){
                    Toast.makeText(PaymentCartActivity.this, "No data found", Toast.LENGTH_SHORT).show();
                }

                else*/ if (status==true && status_response.equals("success")) {
                    Toast.makeText(getApplicationContext(), status_response, Toast.LENGTH_LONG).show();
                    startActivity(new Intent(PaymentCartActivity.this,ThankuActivity.class));

                   // RegPrefManager.getInstance(PaymentCartActivity.this).setBackService("MobileRecharge");
                 //   startActivity(new Intent(PaymentCartActivity.this, ThankuActivity.class));

                    SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("AMOUNT", RechargeAmount);
                    editor.commit();
                    // Toast.makeText(MobileRecharge.this, recharge_amount, Toast.LENGTH_SHORT).show();
                    // startActivity(new Intent(MobileRecharge.this,PaymentActivity.class));finish();
                } else if (status_response.equals("fail")) {
                    Toast.makeText(getApplicationContext(), err_msg, Toast.LENGTH_LONG).show();
                    startActivity(new Intent(PaymentCartActivity.this, FailureActivity.class));
                } /*else if (status_response.contains("F")){
                    Toast.makeText(getApplicationContext(), "Payment Failed! Try again", Toast.LENGTH_LONG).show();
                   // RegPrefManager.getInstance(PaymentCartActivity.this).setBackService("MobileRecharge");
                    startActivity(new Intent(PaymentCartActivity.this, FailureActivity.class));
                    SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.remove("PHONE_NUMBER");
                    editor.clear();
                    editor.commit();
                }*/

            }catch (Exception e){
            Toast.makeText(getApplicationContext(), "Some error occuered or wallet balance is low", Toast.LENGTH_LONG).show();
            Toast.makeText(getApplicationContext(), "Please try again later", Toast.LENGTH_LONG).show();
        }
    }

        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(PaymentCartActivity.this);
            pDialog.setMessage("Loading In...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
    }


    private class AsynWaterBillSubmit extends AsyncTask<Void, Void, Void> {
        ProgressDialog pDialog;
        String success = null,data="",status="true";

        @Override
        protected Void doInBackground(Void... params) {
            pDialog.show();
            ArrayList<NameValuePair> cred = new ArrayList<NameValuePair>();
            cred.add(new BasicNameValuePair("user_id",login_user));//user_email
            cred.add(new BasicNameValuePair("customer_id",Boardname ));
            cred.add(new BasicNameValuePair("operator",operator ));
            cred.add(new BasicNameValuePair("circle",circle ));
            cred.add(new BasicNameValuePair("amount",BillAmount ));
            cred.add(new BasicNameValuePair("landline_ca_number",landline_ca_number ));
            cred.add(new BasicNameValuePair("other_values",other_values ));
            Log.v("RES","Sending data" +login_user+ Boardname+ operator +circle+BillAmount
                    +landline_ca_number+other_values);


            String urlRouteList="https://genieservice.in/api/service/electricity_insurance_gas_water";
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
            try {

                if (status.equals("true")) {
                    Toast.makeText(getApplicationContext(), "Bill Paid Successfully", Toast.LENGTH_LONG).show();

                    startActivity(new Intent(PaymentCartActivity.this, ThankuActivity.class));
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), data, Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), FailureActivity.class));
                }
            }catch (Exception e){
                Toast.makeText(getApplicationContext(), "Genie is away! Try after sometime", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(PaymentCartActivity.this);
            pDialog.setMessage("Loading In...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
    }


    private class AsynDTHpayment extends AsyncTask<Void, Void, Void> {
        ProgressDialog pDialog;
        String success = null,data="",status="true";
        String status_response;
        String err_msg;

        String dthOpName=RegPrefManager.getInstance(PaymentCartActivity.this).getDTHOperatorName();
        String service_id=RegPrefManager.getInstance(PaymentCartActivity.this).getServiceId();
        @Override
        protected Void doInBackground(Void... params) {
            pDialog.show();
            ArrayList<NameValuePair> cred = new ArrayList<NameValuePair>();
            cred.add(new BasicNameValuePair("user_id",login_user));//user_email
            cred.add(new BasicNameValuePair("operator_name",dthOpName ));
            cred.add(new BasicNameValuePair("operator",DTHoperatorCode ));
            cred.add(new BasicNameValuePair("circle",DTHcircleCode ));
            cred.add(new BasicNameValuePair("customer_id",DTHcustomerId ));
            cred.add(new BasicNameValuePair("amount",DTHbillAmount ));
            cred.add(new BasicNameValuePair("service_id",service_id ));
            Log.v("RES","Sending data " +login_user+dthOpName+ DTHoperatorCode+DTHcircleCode+ DTHcustomerId +DTHbillAmount+service_id);


            String urlRouteList="https://genieservice.in/api/service/mobile_dth_datacard_recharge1";
            try {
                String route_response = CustomHttpClient.executeHttpPost(urlRouteList, cred);

                success = route_response;
                JSONObject jsonObject = new JSONObject(success);

                // user_email=jsonObject.getString("user_email");
                    JSONObject jsonObject1=jsonObject.getJSONObject("data");
                    status_response = jsonObject1.getString("Status");
                    err_msg = jsonObject1.getString("ErrorMessage");
                    Successid=jsonObject1.getString("ApiTransID");
                    DateAndTime=jsonObject1.getString("TransactionDate");

                    RegPrefManager.getInstance(PaymentCartActivity.this).setSuccessID(Successid);
                    RegPrefManager.getInstance(PaymentCartActivity.this).setDateAndTime(DateAndTime);

              /*  String data=jsonObject.getString("Status");
                // Toast.makeText(Cart.this, sum_total, Toast.LENGTH_SHORT).show();
                JSONObject jsonObject1 = new JSONObject(data);
                status_response = jsonObject1.getString("Status");
                err_msg = jsonObject1.getString("ErrorMessage");*/

            } catch (Exception e)

            {
                Log.v("Connection error", e.toString());

            }return null;
        }
        protected void onPostExecute(Void result) {
            pDialog.dismiss();
            try {

                if (status_response.contains("S")) {
                    Toast.makeText(getApplicationContext(), "Recharge Successful", Toast.LENGTH_LONG).show();
                    RegPrefManager.getInstance(PaymentCartActivity.this).setSuccessID(Successid);
                    RegPrefManager.getInstance(PaymentCartActivity.this).setBackService("DTH");
                    startActivity(new Intent(getApplicationContext(), ThankuActivity.class));


               /* SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("AMOUNT", dth_recharge_amount = dth_amount.getText().toString());
                editor.commit();*/
                    // Toast.makeText(MobileRecharge.this, recharge_amount, Toast.LENGTH_SHORT).show();
                    // startActivity(new Intent(MobileRecharge.this,PaymentActivity.class));finish();
                } else if (status_response.contains("P")) {
                    Toast.makeText(getApplicationContext(), "Pending", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(PaymentCartActivity.this, PendingActivity.class));
                } else if (status_response.contains("F")) {
                    Toast.makeText(getApplicationContext(), "Failure", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(PaymentCartActivity.this, FailureActivity.class));
                } else {
                    Toast.makeText(getApplicationContext(), "Unsuccessful", Toast.LENGTH_LONG).show();
                    RegPrefManager.getInstance(PaymentCartActivity.this).setSuccessID(Successid);
                    RegPrefManager.getInstance(PaymentCartActivity.this).setBackService("DTH");
                    startActivity(new Intent(getApplicationContext(), FailureActivity.class));
                    SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.remove("OPERATOR_NAME");
                    editor.clear();
                    editor.commit();

                }
            }catch (Exception e){
                Toast.makeText(getApplicationContext(), "Some Error Occured! Try after sometime", Toast.LENGTH_LONG).show();
                startActivity(new Intent(PaymentCartActivity.this, FailureActivity.class));
            }
        }

        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(PaymentCartActivity.this);
            pDialog.setMessage("Loading In...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
    }

    private class AsyncInsurancePayment extends AsyncTask<Void, Void, Void> {
        ProgressDialog pDialog;
        String success = null,data="";
        boolean status;
        String status_response;
        String err_msg;
        String service_id=RegPrefManager.getInstance(PaymentCartActivity.this).getServiceId();
        String operatorName = RegPrefManager.getInstance(PaymentCartActivity.this).getInsurerName();
        String operatorCode = RegPrefManager.getInstance(PaymentCartActivity.this).getInsurerCode();
        String circle_Code = RegPrefManager.getInstance(PaymentCartActivity.this).getCircleCodeElectricity();

        @Override
        protected Void doInBackground(Void... params) {
            pDialog.show();
            ArrayList<NameValuePair> cred = new ArrayList<NameValuePair>();
            cred.add(new BasicNameValuePair("user_id",login_user));//user_email
            cred.add(new BasicNameValuePair("customer_id",accountNumber ));
            cred.add(new BasicNameValuePair("operator_name", operatorName ));
            cred.add(new BasicNameValuePair("operatorCode", operatorCode ));
            cred.add(new BasicNameValuePair("amount",InAmount ));
          /*  cred.add(new BasicNameValuePair("circle",circle_Code ));
            cred.add(new BasicNameValuePair("landline_ca_number",landline_ca_number ));
            cred.add(new BasicNameValuePair("other_values",other_values ));*/
            cred.add(new BasicNameValuePair("service_id",service_id ));
            cred.add(new BasicNameValuePair("offer_id",offerid ));
            Log.v("RES","Sending data" +login_user+ SelectStateName+ operatorName+operatorCode +circle+BillAmount
                    +landline_ca_number+other_values+service_id+offerid);


            String urlRouteList="https://genieservice.in/api/service/Crowdfinch_insurance";
            try {
                String route_response = CustomHttpClient.executeHttpPost(urlRouteList, cred);

                success = route_response;
                JSONObject jsonObject = new JSONObject(success);

                JSONObject jsonObject1=jsonObject.getJSONObject("data");
                status_response = jsonObject1.getString("status");
                err_msg = jsonObject1.getString("message");
                String number = jsonObject1.getString("number");
                Successid=jsonObject1.getString("transid");
                String opratorid = jsonObject1.getString("opratorid");

                RegPrefManager.getInstance(PaymentCartActivity.this).setSuccessID(Successid);
                RegPrefManager.getInstance(PaymentCartActivity.this).setDateAndTime(DateAndTime);


            } catch (Exception e)

            {
                Log.v("Connection error", e.toString());

            }return null;
        }
        protected void onPostExecute(Void result) {
            pDialog.dismiss();
            try {

               /* if (status==false&&data.equals("wallet balance low")){
                    Toast.makeText(PaymentCartActivity.this, data, Toast.LENGTH_SHORT).show();
                }
                else if (status==true&&data.equals("")){
                    Toast.makeText(PaymentCartActivity.this, "No data found", Toast.LENGTH_SHORT).show();
                }

                else*/ if (status==true && status_response.equals("success")) {
                    Toast.makeText(getApplicationContext(), err_msg, Toast.LENGTH_LONG).show();
                    // RegPrefManager.getInstance(PaymentCartActivity.this).setBackService("MobileRecharge");
                    startActivity(new Intent(PaymentCartActivity.this, ThankuActivity.class));

                    SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("AMOUNT", RechargeAmount);
                    editor.commit();
                    // Toast.makeText(MobileRecharge.this, recharge_amount, Toast.LENGTH_SHORT).show();
                    // startActivity(new Intent(MobileRecharge.this,PaymentActivity.class));finish();
                } else if (status_response.equals("fail")) {
                    Toast.makeText(getApplicationContext(), err_msg, Toast.LENGTH_LONG).show();
                    startActivity(new Intent(PaymentCartActivity.this, FailureActivity.class));
                } /*else if (status_response.contains("F")){
                    Toast.makeText(getApplicationContext(), "Payment Failed! Try again", Toast.LENGTH_LONG).show();
                   // RegPrefManager.getInstance(PaymentCartActivity.this).setBackService("MobileRecharge");
                    startActivity(new Intent(PaymentCartActivity.this, FailureActivity.class));
                    SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.remove("PHONE_NUMBER");
                    editor.clear();
                    editor.commit();
                }*/

            }catch (Exception e){
                Toast.makeText(getApplicationContext(), "Some error occuered or wallet balance is low", Toast.LENGTH_LONG).show();
                Toast.makeText(getApplicationContext(), "Please try again later", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(PaymentCartActivity.this);
            pDialog.setMessage("Loading In...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
    }

    private void getDTHRechargeResponse(){
        progressDialog.setMessage("Please wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();



        String dthOpName=RegPrefManager.getInstance(PaymentCartActivity.this).getDTHOperatorName();
     //   String service_id=RegPrefManager.getInstance(PaymentCartActivity.this).getServiceId();
        String dthServiceId = "14";

        Call<MobileRechargeResponse> call=apiService.postRecharge(login_user,DTHcustomerId,DTHoperatorCode,DTHoperatorName,DTHcircleCode,dthServiceId,DTHbillAmount);
        call.enqueue(new Callback<MobileRechargeResponse>() {
            @Override
            public void onResponse(Call<MobileRechargeResponse> call, Response<MobileRechargeResponse> response) {
                try{
                    progressDialog.dismiss();
                    boolean status =response.body().isStatus();

                    if (status==true){
                        err_msg = response.body().getData().getErrorMessage();
                        status_response = response.body().getData().getStatus();
                        String opRefNo = response.body().getData().getOperatorRef();
                        Successid = response.body().getData().getApiTransID();
                        DateAndTime = response.body().getData().getTransactionDate();

                        RegPrefManager.getInstance(PaymentCartActivity.this).setSuccessID(Successid);
                        RegPrefManager.getInstance(PaymentCartActivity.this).setDateAndTime(DateAndTime);

                        if (status_response.contains("S")) {
                            Toast.makeText(getApplicationContext(), "Recharge Successful", Toast.LENGTH_LONG).show();
                            RegPrefManager.getInstance(PaymentCartActivity.this).setBackService("MobileRecharge");
                            startActivity(new Intent(PaymentCartActivity.this, ThankuActivity.class));

                            SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                            SharedPreferences.Editor editor = pref.edit();
                            editor.putString("AMOUNT", RechargeAmount);
                            editor.commit();
                            // Toast.makeText(MobileRecharge.this, recharge_amount, Toast.LENGTH_SHORT).show();
                            // startActivity(new Intent(MobileRecharge.this,PaymentActivity.class));finish();
                        } else if (status_response.contains("P")) {
                            Toast.makeText(getApplicationContext(), "Pending", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(PaymentCartActivity.this, PendingActivity.class));
                        } else {
                            Toast.makeText(getApplicationContext(), "Recharge Failed! Try again", Toast.LENGTH_LONG).show();
                            RegPrefManager.getInstance(PaymentCartActivity.this).setBackService("MobileRecharge");
                            startActivity(new Intent(PaymentCartActivity.this, FailureActivity.class));
                            SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                            SharedPreferences.Editor editor = pref.edit();
                            editor.remove("PHONE_NUMBER");
                            editor.clear();
                            editor.commit();
                        }
                    }
                    else {
                        err_msg = response.body().getData().getErrorMessage();
                        Toast.makeText(PaymentCartActivity.this, err_msg, Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception ex){
                    ex.printStackTrace();
                    Toast.makeText(PaymentCartActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MobileRechargeResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),"Network Problem!!!", Toast.LENGTH_LONG).show();
            }
        });

    }


    private void volleyResponse() {

        final String value=RegPrefManager.getInstance(PaymentCartActivity.this).getMobileOperatorCode();
        final String opName =RegPrefManager.getInstance(PaymentCartActivity.this).getMobileOperatorName();
        final String cirle_code=RegPrefManager.getInstance(PaymentCartActivity.this).getMobileCircleCode();
        final String service_id=RegPrefManager.getInstance(PaymentCartActivity.this).getServiceId();

        String statusCf = null;
        final String responce1   = null;

        progressDialog.show();
        progressDialog.setMessage("Loading...");
        String tag_json_req = "user_login";
        StringRequest data = new StringRequest(com.android.volley.Request.Method.POST,
                "https://genieservice.in/api/service/mobile_dth_datacard_recharge1",//https://genieservice.in/api/recharge/mobile_recharge
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        try {
                            Log.d(" response is ", response);
                           // Toast.makeText(PaymentCartActivity.this, response, Toast.LENGTH_SHORT).show();

                            /*{
                                "status" = "true";
                                "userid" = "1";

                            }*/

                            JSONObject jsonObject = new JSONObject(response);

                            boolean status1 =jsonObject.getBoolean("status");
                            String data = jsonObject.getString("data");


                            JSONObject jsonObject1 = new JSONObject(data);
                           // String statusCf = jsonObject1.getString("status");

                           /* if (statusCf!=null){
                                String msg1 = jsonObject1.getString("message");
                                String amt1 = jsonObject1.getString("amount");
                                String num1 = jsonObject1.getString("number");
                                String trans1 = jsonObject1.getString("transid");
                                String opId1 = jsonObject1.getString("opratorid");
                                Toast.makeText(getApplicationContext(), "Recharge Successful", Toast.LENGTH_LONG).show();
                                RegPrefManager.getInstance(PaymentCartActivity.this).setBackService("MobileRecharge");
                                startActivity(new Intent(PaymentCartActivity.this, ThankuActivity.class));

                            }
*/

                            /*else if (responce1!=null){ */
                            String responce1 = jsonObject1.getString("Status");
                            String errorMessage1 = jsonObject1.getString("ErrorMessage");
                            String successId=jsonObject1.getString("ApiTransID");
                            RegPrefManager.getInstance(PaymentCartActivity.this).setSuccessID(successId);
                            String DtandTime=jsonObject1.getString("TransactionDate");
                            RegPrefManager.getInstance(PaymentCartActivity.this).setDateAndTime(DtandTime);



                                if (responce1.contains("S")) {
                                    Toast.makeText(getApplicationContext(), "Recharge Successful", Toast.LENGTH_LONG).show();
                                    RegPrefManager.getInstance(PaymentCartActivity.this).setBackService("MobileRecharge");
                                    startActivity(new Intent(PaymentCartActivity.this, ThankuActivity.class));

                                    SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = pref.edit();
                                    editor.putString("AMOUNT", RechargeAmount);
                                    editor.commit();
                                    // Toast.makeText(MobileRecharge.this, recharge_amount, Toast.LENGTH_SHORT).show();
                                    // startActivity(new Intent(MobileRecharge.this,PaymentActivity.class));finish();
                                } else if (responce1.contains("P")) {
                                    Toast.makeText(getApplicationContext(), "Pending", Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(PaymentCartActivity.this, PendingActivity.class));
                                } else {
                                    Toast.makeText(getApplicationContext(), "Recharge Failed! Try again", Toast.LENGTH_LONG).show();
                                    RegPrefManager.getInstance(PaymentCartActivity.this).setBackService("MobileRecharge");
                                    startActivity(new Intent(PaymentCartActivity.this, FailureActivity.class));
                                    SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = pref.edit();
                                    editor.remove("PHONE_NUMBER");
                                    editor.clear();
                                    editor.commit();
                                }
                            /*catch (Exception e){
                                Toast.makeText(getApplicationContext(), "Some Error Occured! Try after sometime", Toast.LENGTH_LONG).show();
                                Toast.makeText(PaymentCartActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
*/
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(PaymentCartActivity.this, "There may be some error or your wallet balance is low", Toast.LENGTH_SHORT).show();

                        }
                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.getMessage() == null) {
                    if (i < 3) {
                        Log.e("Retry due to error ", "for time : " + i);
                        i++;
                    } else  {
                        progressDialog.dismiss();
                        Toast.makeText(PaymentCartActivity.this, "Check your network connection.",
                                Toast.LENGTH_LONG).show();
                    }
                } else
                    Toast.makeText(PaymentCartActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {


            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //phone_no=8007972554&pass=123456
                Map<String, String> params = new HashMap<>();
                params.put("user_id",login_user);
                params.put("customer_id",PhoneNumber);
                params.put("operator",value);
                params.put("operator_name",opName);
                params.put("circle",cirle_code);
                params.put("amount",RechargeAmount);
                params.put("service_id",service_id);


                Log.d("params are :", "" + params);

                return params;
            }
        };
        data.setRetryPolicy(new
                DefaultRetryPolicy(30000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance().getRequestQueue().add(data).addMarker(tag_json_req);


    }


    private void volleyResponse1() {
        final String dthOpName=RegPrefManager.getInstance(PaymentCartActivity.this).getDTHOperatorName();
        final String service_id=RegPrefManager.getInstance(PaymentCartActivity.this).getServiceId();
        progressDialog.show();
        progressDialog.setMessage("Loading...");
        String tag_json_req = "user_login";
        StringRequest data = new StringRequest(com.android.volley.Request.Method.POST,
                "https://genieservice.in/api/service/mobile_dth_datacard_recharge1",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        try {
                            Log.d(" response is ", response);

                            /*{
                                "status" = "true";
                                "userid" = "1";

                            }*/


                            JSONObject jsonObject = new JSONObject(response);

                            boolean status2 =jsonObject.getBoolean("status");
                            String data = jsonObject.getString("data");


                            JSONObject jsonObject1 = new JSONObject(data);
                            String responce2 = jsonObject1.getString("Status");
                            String errorMessage2 = jsonObject1.getString("ErrorMessage");
                            String successId1=jsonObject1.getString("ApiTransID");
                            RegPrefManager.getInstance(PaymentCartActivity.this).setSuccessID(successId1);
                            String DtandTime1=jsonObject1.getString("TransactionDate");
                            RegPrefManager.getInstance(PaymentCartActivity.this).setDateAndTime(DtandTime1);



                            if (responce2.contains("S")) {
                                Toast.makeText(getApplicationContext(), "Recharge Successful", Toast.LENGTH_LONG).show();
                                RegPrefManager.getInstance(PaymentCartActivity.this).setBackService("MobileRecharge");
                                startActivity(new Intent(PaymentCartActivity.this, ThankuActivity.class));

                                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                                SharedPreferences.Editor editor = pref.edit();
                                editor.putString("AMOUNT", RechargeAmount);
                                editor.commit();
                                // Toast.makeText(MobileRecharge.this, recharge_amount, Toast.LENGTH_SHORT).show();
                                // startActivity(new Intent(MobileRecharge.this,PaymentActivity.class));finish();
                            } else if (responce2.contains("P")) {
                                Toast.makeText(getApplicationContext(), "Pending", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(PaymentCartActivity.this, PendingActivity.class));
                            } else {
                                Toast.makeText(getApplicationContext(), "Recharge Failed! Try again", Toast.LENGTH_LONG).show();
                                RegPrefManager.getInstance(PaymentCartActivity.this).setBackService("MobileRecharge");
                                startActivity(new Intent(PaymentCartActivity.this, FailureActivity.class));
                                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                                SharedPreferences.Editor editor = pref.edit();
                                editor.remove("PHONE_NUMBER");
                                editor.clear();
                                editor.commit();
                            }
                            /*catch (Exception e){
                                Toast.makeText(getApplicationContext(), "Some Error Occured! Try after sometime", Toast.LENGTH_LONG).show();
                                Toast.makeText(PaymentCartActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
*/
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(PaymentCartActivity.this, "There may be some error or your wallet balance is low", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.getMessage() == null) {
                    if (i < 3) {
                        Log.e("Retry due to error ", "for time : " + i);
                        i++;
                    } else  {
                        progressDialog.dismiss();
                        Toast.makeText(PaymentCartActivity.this, "Check your network connection.",
                                Toast.LENGTH_LONG).show();
                    }
                } else
                    Toast.makeText(PaymentCartActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {


            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //phone_no=8007972554&pass=123456
                Map<String, String> params = new HashMap<>();
                params.put("user_id",login_user);
                params.put("operator_name",dthOpName);
                params.put("operator",DTHoperatorCode);
                params.put("circle",DTHcircleCode);
                params.put("customer_id",DTHcustomerId);
                params.put("amount",DTHbillAmount);
                params.put("service_id",service_id);


                Log.d("params are :", "" + params);

                return params;
            }
        };
        data.setRetryPolicy(new
                DefaultRetryPolicy(30000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance().getRequestQueue().add(data).addMarker(tag_json_req);


    }


}
