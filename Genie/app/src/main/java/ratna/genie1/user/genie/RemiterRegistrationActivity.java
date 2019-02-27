package ratna.genie1.user.genie;

import android.app.Dialog;
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
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import ratna.genie1.user.genie.Model.RemiterDetails;
import ratna.genie1.user.genie.MoneyTransfer.MoneyTransferActivity;
import ratna.genie1.user.genie.ObjectNew.RemiterRegisterResponse;
import ratna.genie1.user.genie.ObjectNew.RemitterValidateResponse;
import ratna.genie1.user.genie.client.ApiClientGenie;
import ratna.genie1.user.genie.client.ApiInterface;
import ratna.genie1.user.genie.helper.RegPrefManager;

import ratna.genie1.user.genie.client.ApiClientGenie;
import ratna.genie1.user.genie.client.ApiInterface;
import ratna.genie1.user.genie.helper.RegPrefManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RemiterRegistrationActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText phoneTv,nameTv,surnameTv,pincodeTv,otpTv;
    TextView resendTv;
    private Button registerBtn;
    Toolbar toolbar;
    private AlertDialog.Builder alertDialog;
    ApiInterface apiService;
    ProgressDialog progressDialog;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    String login_user="",namevalue, surname;
    String groupId;
    private boolean flag;
    Dialog dialog;
    String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remiter_registration);
        toolbar = findViewById(R.id.toolbar);
        apiService =
                ApiClientGenie.getClient().create(ApiInterface.class);
        progressDialog =new ProgressDialog(this);
        alertDialog=new AlertDialog.Builder(this);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        groupId = RegPrefManager.getInstance(RemiterRegistrationActivity.this).getUserGroup();
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

        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

        SharedPreferences.Editor editor1 = sharedpreferences.edit();
        login_user=sharedpreferences.getString("FLAG", "");
        editor1.commit(); // commit changes


        Log.d("login_user", login_user);
        intialize();
    }
    private void intialize(){
        registerBtn=findViewById(R.id.registerBtn);
        phoneTv=findViewById(R.id.phoneTv);
        nameTv=findViewById(R.id.nameTv);
        surnameTv=findViewById(R.id.surnameTv);
        pincodeTv=findViewById(R.id.pincodeTv);
        registerBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.registerBtn:
                if (validationNew()) {
                    if (isNetworkAvailable()) {
                         networkRegister();
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
    private Boolean validationNew() {

        if (phoneTv.getText().toString().trim().isEmpty()) {

            phoneTv.setError("Please Enter Phone Number");

            return false;
        }


        if (nameTv.getText().toString().trim().isEmpty()) {

            nameTv.setError("Please Enter Name");

            return false;
        }


        if (pincodeTv.getText().toString().trim().isEmpty()) {

            pincodeTv.setError("Please Enter Pincode");

            return false;
        }

        return true;
    }
    private void networkRegister(){
        progressDialog.setMessage("Please wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        phone= RegPrefManager.getInstance(this).getPhoneNo();
        namevalue=nameTv.getText().toString();
        surname=surnameTv.getText().toString();
        String pincode=pincodeTv.getText().toString();
        Call<RemiterRegisterResponse> call=apiService.postRemiterRegister(login_user,phone,namevalue,surname,pincode);
        call.enqueue(new Callback<RemiterRegisterResponse>() {
            @Override
            public void onResponse(Call<RemiterRegisterResponse> call, Response<RemiterRegisterResponse> response) {
                progressDialog.dismiss();
                boolean status=response.body().isStatus();
                int verified = response.body().getData().getData().getRemitter().getIs_verified();
                if(status==true && verified == 0){
                    String id=response.body().getData().getData().getRemitter().getId();
                    String statusResponse = response.body().getData().getStatus();
                    RegPrefManager.getInstance(RemiterRegistrationActivity.this).setRemitterId(id);
                    RegPrefManager.getInstance(RemiterRegistrationActivity.this).setRemiterName(namevalue);
                  //  startActivity(new Intent(RemiterRegistrationActivity.this, AddBeneficiaryActivity.class));
                    Toast.makeText(RemiterRegistrationActivity.this, statusResponse, Toast.LENGTH_SHORT).show();

                    DialogShow();
                 //   finish();
                }else if(status==true && verified ==1) {
                   // Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_LONG).show();
                    String id=response.body().getData().getData().getRemitter().getId();
                    RegPrefManager.getInstance(RemiterRegistrationActivity.this).setRemitterId(id);
                    startActivity(new Intent(RemiterRegistrationActivity.this, RemiterDetailsActivity.class));
                }
                else{
                    Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<RemiterRegisterResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        phoneTv.setText(RegPrefManager.getInstance(this).getPhoneNo());
    }


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

               // networkResend();
            }
        });

        dialog.show();
    }



    private void validateNetwork(){
        progressDialog.setMessage("Please wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        phone= RegPrefManager.getInstance(this).getPhoneNo();
        String remID= RegPrefManager.getInstance(this).getRemitterId();
        String otp=otpTv.getText().toString();
        surname=surnameTv.getText().toString();
     //   String pincode=pincodeTv.getText().toString();
        Call<RemitterValidateResponse> call=apiService.postRemitterValidate(login_user,phone,remID,otp);
        call.enqueue(new Callback<RemitterValidateResponse>() {
            @Override
            public void onResponse(Call<RemitterValidateResponse> call, Response<RemitterValidateResponse> response) {
                progressDialog.dismiss();
                boolean status=response.body().isStatus();
                int verified = response.body().getData().getData().getRemitter().getIs_verified();
                if(status==true && verified == 1){
                 //   String id=response.body().getData().getData().getRemitter().getId();
                 //   RegPrefManager.getInstance(RemiterRegistrationActivity.this).setRemitterId(id);
                 //   RegPrefManager.getInstance(RemiterRegistrationActivity.this).setRemiterName(namevalue);
                    String statusResponse = response.body().getData().getStatus();
                    Toast.makeText(RemiterRegistrationActivity.this, statusResponse, Toast.LENGTH_SHORT).show();
                      startActivity(new Intent(RemiterRegistrationActivity.this, AddBeneficiaryActivity.class));
                   // DialogShow();
                    finish();
                }else {
                    Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<RemitterValidateResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_LONG).show();
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
