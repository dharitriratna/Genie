package ratna.genie1.user.genie.MoneyTransfer;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import ratna.genie1.user.genie.Adapter.RemiterDetailsCustomAdapter;
import ratna.genie1.user.genie.MainActivity;
import ratna.genie1.user.genie.ObjectNew.FundTransferResponse;
import ratna.genie1.user.genie.ObjectNew.FundTransferStatusResponse;
import ratna.genie1.user.genie.ObjectNew.RemiterDetailsResponse;
import ratna.genie1.user.genie.R;
import ratna.genie1.user.genie.RemiterDetailsActivity;
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
        intialize();
    }
    private void intialize(){
        amountEd=findViewById(ratna.genie1.user.genie.R.id.amountEd);
        banknameTv=findViewById(ratna.genie1.user.genie.R.id.banknameTv);
        continueBtn=findViewById(ratna.genie1.user.genie.R.id.continueBtn);
        continueBtn.setOnClickListener(this);

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
        HashMap<String, String> remiter = RegPrefManager.getInstance(SendPaymentActivity.this).getRemiterDetails();
         beneID=remiter.get("BeneID");
        String bankname=remiter.get("BankName");
        remiterphone=RegPrefManager.getInstance(SendPaymentActivity.this).getRemiterPhone();

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

        Call<FundTransferResponse> call=apiService.postFundTransfer(remiterphone,beneID,
                amountEd.getText().toString(),spinnerSelect);
        call.enqueue(new Callback<FundTransferResponse>() {
            @Override
            public void onResponse(Call<FundTransferResponse> call, Response<FundTransferResponse> response) {
                progressDialog.dismiss();
                String status=response.body().getStatus();
                if(status.equals("SUCCESS")){
                    String agentId=response.body().getData().getCyrus_id();
                    RegPrefManager.getInstance(SendPaymentActivity.this).setAgentId(agentId);

                    checkStatus();
                }else {
                    Toast.makeText(getApplicationContext(),"Try again. After Some time",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<FundTransferResponse> call, Throwable t) {
            progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),"Try again. After Some time",Toast.LENGTH_LONG).show();
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
        Call<FundTransferStatusResponse> call=apiService.postFundTransferStatus(agentId);
        call.enqueue(new Callback<FundTransferStatusResponse>() {
            @Override
            public void onResponse(Call<FundTransferStatusResponse> call, Response<FundTransferStatusResponse> response) {
                progressDialog.dismiss();
                String status=response.body().getStatuscode();
                if(status.equals("TXN")){
                    String st=response.body().getStatus();
                    Toast.makeText(getApplicationContext(),st,Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(getApplicationContext(),"Try again. After Some time",Toast.LENGTH_LONG).show();
                }

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
