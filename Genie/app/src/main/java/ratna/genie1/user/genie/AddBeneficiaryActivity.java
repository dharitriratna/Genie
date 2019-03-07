package ratna.genie1.user.genie;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import ratna.genie1.user.genie.MoneyTransfer.MoneyTransferActivity;
import ratna.genie1.user.genie.ObjectNew.BeneficiaryRegisterResponse;
import ratna.genie1.user.genie.ObjectNew.BeneficiaryValidateResponse;
import ratna.genie1.user.genie.ObjectNew.ResendOTPResponse;
import ratna.genie1.user.genie.client.ApiClientGenie;
import ratna.genie1.user.genie.client.ApiInterface;
import ratna.genie1.user.genie.helper.RegPrefManager;

import java.util.ArrayList;
import java.util.List;

import ratna.genie1.user.genie.client.ApiClientGenie;
import ratna.genie1.user.genie.client.ApiInterface;
import ratna.genie1.user.genie.helper.RegPrefManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddBeneficiaryActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText nameTv,phoneTv,ifscTv,accontTv,otpTv;
    private TextView resendTv;

    private Button registerBtn;
    Toolbar toolbar;
    private AlertDialog.Builder alertDialog;
    ApiInterface apiService;
    ProgressDialog progressDialog;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    String  otp_name;
    private boolean flag;
    Dialog dialog;
    String groupId;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    String login_user="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_beneficiary);
     //   checkAndRequestPermissions();
        toolbar = findViewById(R.id.toolbar);
        apiService =
                ApiClientGenie.getClient().create(ApiInterface.class);
        progressDialog =new ProgressDialog(this);
        alertDialog=new AlertDialog.Builder(this);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        groupId = RegPrefManager.getInstance(AddBeneficiaryActivity.this).getUserGroup();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // startActivity(new Intent(AddBeneficiaryActivity.this,RemiterDetailsActivity.class));
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
                finish();
            }
        });


        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

        SharedPreferences.Editor editor1 = sharedpreferences.edit();
        login_user=sharedpreferences.getString("FLAG", "");
        editor1.commit(); // commit changes


        Log.d("login_user", login_user);


        registerBtn=findViewById(R.id.registerBtn);
        nameTv=findViewById(R.id.nameTv);
        phoneTv=findViewById(R.id.phoneTv);
        ifscTv=findViewById(R.id.ifscTv);
        accontTv=findViewById(R.id.accontTv);
        registerBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.registerBtn:
                if (validationNew()) {
                    if (isNetworkAvailable()) {
                        networkRegister(); //register add beneficiary
                    }
                    else {
                        noNetwrokErrorMessage();
                    }
                }
                break;
        }
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
    private void networkRegister(){
        progressDialog.setMessage("Please wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        String remittid= RegPrefManager.getInstance(this).getRemitterId();


        Call<BeneficiaryRegisterResponse> call=apiService.postBeneficiaryRegister(login_user,remittid,nameTv.getText().toString(),
                phoneTv.getText().toString(),ifscTv.getText().toString(),accontTv.getText().toString());
        call.enqueue(new Callback<BeneficiaryRegisterResponse>() {
            @Override
            public void onResponse(Call<BeneficiaryRegisterResponse> call, Response<BeneficiaryRegisterResponse> response) {
                try{
                progressDialog.dismiss();
                boolean status=response.body().isStatus();
                int verified = response.body().getData().getData().getBeneficiary().getVerified();
                if(status==true && verified==0){
                    String remitterid=response.body().getData().getData().getRemitter().getId();
                    String beneficiaryid=response.body().getData().getData().getBeneficiary().getId();
                    RegPrefManager.getInstance(AddBeneficiaryActivity.this).setRemitterId(remitterid);
                    RegPrefManager.getInstance(AddBeneficiaryActivity.this).setBeneficaryId(beneficiaryid);
                  //  startActivity(new Intent(AddBeneficiaryActivity.this,RemiterDetailsActivity.class));
                    DialogShow(); // show dialog
                }
                else if (status==true && verified==1){
                    String id=response.body().getData().getData().getBeneficiary().getId();
                    RegPrefManager.getInstance(AddBeneficiaryActivity.this).setBeneficaryId(id);
                    startActivity(new Intent(AddBeneficiaryActivity.this, RemiterDetailsActivity.class));
                }
                else {
                    String status_message = response.body().getData().getStatus();
                    Toast.makeText(getApplicationContext(),status_message,Toast.LENGTH_LONG).show();
                }

            }catch (Exception e){
                e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<BeneficiaryRegisterResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),"Sever is slow.Please Try Again bit later..",Toast.LENGTH_LONG).show();
            }
        });

    }
    private Boolean validationNew() {


        if (phoneTv.getText().toString().trim().isEmpty()) {

            phoneTv.setError("Please Enter Phone Number");

            return false;
        }


        if (nameTv.getText().toString().trim().isEmpty()) {

            nameTv.setError("Please Enter Name");

            return false;
        }


        if (ifscTv.getText().toString().trim().isEmpty()) {

            ifscTv.setError("Please IFSC code");

            return false;
        }

        if (accontTv.getText().toString().trim().isEmpty()) {

            accontTv.setError("Please Account Number ");

            return false;
        }


        return true;
    }

    /*private  boolean checkAndRequestPermissions() {
        int permissionSendMessage = ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS);
        int receiveSMS = ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS);
        int readSMS = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (receiveSMS != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.RECEIVE_MMS);
        }
        if (readSMS != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_SMS);
        }
        if (permissionSendMessage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.SEND_SMS);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this,
                    listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),
                    REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }*/

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase("otp")) {
                final String message = intent.getStringExtra("message");
                otp_name=message;
              //  otp_name=otp_name.replaceAll("[^0-6]","");
              //  otpTv.setText(otp_name);
                Log.d("TagOTP",otp_name);
            }
        }
    };


    private void DialogShow(){
        flag=true;
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.otpverifylayout);
        dialog.setTitle("Title...");

        // set the custom dialog components - text, image and button
         otpTv = (EditText) dialog.findViewById(R.id.otpTv);
        resendTv=(TextView)dialog.findViewById(R.id.resendTv);

        Button continueBtn=(Button)dialog.findViewById(R.id.continueBtn);
        // if button is clicked, close the custom dialog
        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                validateNetwork(); // validate beneficiary
               /*startActivity(new Intent(AddBeneficiaryActivity.this,RemiterDetailsActivity.class));
               finish();*/
            }
        });

        resendTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

                networkResend();
            }
        });

        dialog.show();
    }

    @Override
    public void onResume() {
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter("otp"));
        super.onResume();
        //String ph=RegPrefManager.getInstance(this).getPhoneNo();
        //phoneTv.setText(ph);
       // nameTv.setText(RegPrefManager.getInstance(this).getRemiterName());
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }
    private void networkResend(){
        String remittid= RegPrefManager.getInstance(this).getRemitterId();
        String benificiaryid=RegPrefManager.getInstance(this).getBeneficaryId();
        if(benificiaryid!=null){
            Call<ResendOTPResponse> call=apiService.postResendOTP(login_user,remittid,benificiaryid);
            call.enqueue(new Callback<ResendOTPResponse>() {
                @Override
                public void onResponse(Call<ResendOTPResponse> call, Response<ResendOTPResponse> response) {

                DialogShow();
                }

                @Override
                public void onFailure(Call<ResendOTPResponse> call, Throwable t) {
                    Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_LONG).show();
                }
            });
        }
        else {
            Toast.makeText(getApplicationContext(),"Please Check Network Condition",Toast.LENGTH_LONG).show();
        }

    }
    private void validateNetwork(){
        progressDialog.setMessage("Please wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        final String remittid= RegPrefManager.getInstance(this).getRemitterId();
        String benificiaryid=RegPrefManager.getInstance(this).getBeneficaryId();
        Call<BeneficiaryValidateResponse> call=apiService.postBeneficiaryValidate(login_user,remittid,benificiaryid,otp_name);

        call.enqueue(new Callback<BeneficiaryValidateResponse>() {
            @Override
            public void onResponse(Call<BeneficiaryValidateResponse> call, Response<BeneficiaryValidateResponse> response) {
                try{
                progressDialog.dismiss();
                boolean status=response.body().isStatus();
                if (status==true){
                    String statuss=response.body().getData().getStatus();
                    Toast.makeText(getApplicationContext(),statuss,Toast.LENGTH_LONG).show();
                    startActivity(new Intent(AddBeneficiaryActivity.this,RemiterDetailsActivity.class));
                    finish();
                }else {
                    Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_LONG).show();
                }

            }catch (Exception e){
                e.printStackTrace();}
            }

            @Override
            public void onFailure(Call<BeneficiaryValidateResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_LONG).show();
            }
        });

    }

}
