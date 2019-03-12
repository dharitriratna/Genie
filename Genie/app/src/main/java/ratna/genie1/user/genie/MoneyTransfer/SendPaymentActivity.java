package ratna.genie1.user.genie.MoneyTransfer;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import ratna.genie1.user.genie.Adapter.RemiterDetailsCustomAdapter;
import ratna.genie1.user.genie.FailureActivity;
import ratna.genie1.user.genie.MainActivity;
import ratna.genie1.user.genie.ObjectNew.FundTransferResponse;
import ratna.genie1.user.genie.ObjectNew.FundTransferStatusResponse;
import ratna.genie1.user.genie.ObjectNew.RemiterDetailsResponse;
import ratna.genie1.user.genie.PaymentCartActivity;
import ratna.genie1.user.genie.R;
import ratna.genie1.user.genie.RemiterDetailsActivity;
import ratna.genie1.user.genie.ThankuActivity;
import ratna.genie1.user.genie.client.ApiClientGenie;
import ratna.genie1.user.genie.client.ApiClientGenie1;
import ratna.genie1.user.genie.client.ApiInterface;
import ratna.genie1.user.genie.helper.RegPrefManager;

import java.util.HashMap;

import ratna.genie1.user.genie.client.ApiClientGenie1;
import ratna.genie1.user.genie.client.ApiInterface;
import ratna.genie1.user.genie.helper.RegPrefManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SendPaymentActivity extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemSelectedListener {
    private EditText amountEd;
    private Toolbar toolbar;
    private TextView banknameTv;
    private Button continueBtn;
    private String beneID,remiterphone;
    private AlertDialog.Builder alertDialog;
    ApiInterface apiService;
    ProgressDialog progressDialog;
    String[] bankArray = { "IMPS", "NEFT"};
    private  Spinner bankSpinner;
    private String spinnerSelect;
    String amountStr;
    String statussss;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    String login_user="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(ratna.genie1.user.genie.R.layout.activity_send_payment);
        apiService =
                ApiClientGenie1.getClient().create(ApiInterface.class);
        toolbar = findViewById(ratna.genie1.user.genie.R.id.toolbar);
        toolbar.setNavigationIcon(ratna.genie1.user.genie.R.drawable.ic_arrow_back_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //onBackPressed();
                startActivity(new Intent(SendPaymentActivity.this,MoneyTransferActivity.class));
                finish();
            }
        });
        progressDialog = new ProgressDialog(this);
        alertDialog = new AlertDialog.Builder(this);

        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

        SharedPreferences.Editor editor1 = sharedpreferences.edit();
        login_user=sharedpreferences.getString("FLAG", "");
        editor1.commit(); // commit changes


        Log.d("login_user", login_user);

        intialize();
    }
    private void intialize(){
        amountEd=findViewById(ratna.genie1.user.genie.R.id.amountEd);
        banknameTv=findViewById(ratna.genie1.user.genie.R.id.banknameTv);
        continueBtn=findViewById(ratna.genie1.user.genie.R.id.continueBtn);
        continueBtn.setOnClickListener(this);

      //  amountStr = amountEd.getText().toString().trim();

        bankSpinner = (Spinner) findViewById(ratna.genie1.user.genie.R.id.bankSpinner);
        bankSpinner.setOnItemSelectedListener(this);

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,bankArray);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        bankSpinner.setAdapter(aa);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case ratna.genie1.user.genie.R.id.continueBtn:
                if(validationNew()) {
                    if (isNetworkAvailable()) {
                        networkFundTransfer();
                    } else {
                        noNetwrokErrorMessage();
                    }
                }
                break;
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        HashMap<String, String> remitter = RegPrefManager.getInstance(SendPaymentActivity.this).getRemiterDetails();
        beneID=remitter.get("BeneID");
        String bankname=remitter.get("BankName");
      //  remiterphone=RegPrefManager.getInstance(SendPaymentActivity.this).getRemiterPhone();
        remiterphone = remitter.get("Mobile");

        banknameTv.setText(bankname);
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

    private void networkFundTransfer(){
        progressDialog.setMessage("Please wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        amountStr = amountEd.getText().toString().trim();

        Call<FundTransferResponse> call=apiService.postFundTransfer(login_user,remiterphone,beneID,
                amountStr,spinnerSelect);
        call.enqueue(new Callback<FundTransferResponse>() {
            @Override
            public void onResponse(Call<FundTransferResponse> call, Response<FundTransferResponse> response) {
                try{
                progressDialog.dismiss();
                boolean status = response.body().isStatus();
                String st = String.valueOf(status);

                if(status== true){

                    String statusRes=response.body().getData().getStatus();
                    String statusCode = response.body().getData().getStatuscode();
                    String agentId=response.body().getData().getData().getAgentid();
                    String oprID = response.body().getData().getData().getOpr_id();
                    String ipayId = response.body().getData().getData().getIpay_id();
                    String cyrusId = response.body().getData().getData().getCyrus_id();
                    String openBal = response.body().getData().getData().getOpening_bal();
                    String transAmt = response.body().getData().getData().getTrans_amt();
                    String locAmt = response.body().getData().getData().getLocked_amt();
                    String chrAmt = response.body().getData().getData().getCharged_amt();
                   // statussss = response.body().getStatus();
                    RegPrefManager.getInstance(SendPaymentActivity.this).setAgentId(agentId);
                    Toast.makeText(SendPaymentActivity.this, statusRes, Toast.LENGTH_SHORT).show();

                    checkStatus();
                }else {
                    String message = response.body().getData().getStatus();
                    Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
                }
            }catch (Exception e){
                    Toast.makeText(SendPaymentActivity.this, "Genie is away! Please try after some time", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FundTransferResponse> call, Throwable t) {
            progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),"Try again. After Some time! Amount should be more than 100rs",Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        spinnerSelect=bankArray[i];
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    spinnerSelect="IMPS";
    }


    private void checkStatus(){
    String agentId=RegPrefManager.getInstance(SendPaymentActivity.this).getAgentId();
        Call<FundTransferStatusResponse> call=apiService.postFundTransferStatus(login_user,agentId);
        call.enqueue(new Callback<FundTransferStatusResponse>() {
            @Override
            public void onResponse(Call<FundTransferStatusResponse> call, Response<FundTransferStatusResponse> response) {
                try {

                progressDialog.dismiss();
                boolean status = response.body().isStatus();
                String statusSt=response.body().getData().getStatuscode();
                if(status==true&&statusSt.equals("TXN")){
                    String st=response.body().getData().getStatus();
                    String agentId = response.body().getData().getData().getAgentid();
                    String dt = response.body().getData().getData().getRequest_date();
                    Toast.makeText(getApplicationContext(),st,Toast.LENGTH_LONG).show();
                    RegPrefManager.getInstance(SendPaymentActivity.this).setSuccessID(agentId);
                    RegPrefManager.getInstance(SendPaymentActivity.this).setDateAndTime(dt);
                    RegPrefManager.getInstance(SendPaymentActivity.this).setBackService("MONEYTRANSFER");
                    startActivity(new Intent(SendPaymentActivity.this, ThankuActivity.class));

                }
                else {
                    String st = response.body().getData().getStatus();
                    String agentId = response.body().getData().getData().getAgentid();
                    String dt = response.body().getData().getData().getRequest_date();
                    RegPrefManager.getInstance(SendPaymentActivity.this).setSuccessID(agentId);
                    RegPrefManager.getInstance(SendPaymentActivity.this).setDateAndTime(dt);
                    Toast.makeText(getApplicationContext(),st,Toast.LENGTH_LONG).show();
                    RegPrefManager.getInstance(SendPaymentActivity.this).setBackService("MONEYTRANSFER");
                    startActivity(new Intent(SendPaymentActivity.this, FailureActivity.class));
                }

            }catch (Exception e){
                e.printStackTrace();}
            }

            @Override
            public void onFailure(Call<FundTransferStatusResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),"Try again. After Some time",Toast.LENGTH_LONG).show();
            }
        });

    }

    private Boolean validationNew() {


        if (amountEd.getText().toString().trim().isEmpty()) {

            amountEd.setError("Please Enter Amount");

            return false;
        }

        return true;
    }




}
