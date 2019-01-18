package ratna.genie1.user.genie;

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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import ratna.genie1.user.genie.MoneyTransfer.MoneyTransferActivity;
import ratna.genie1.user.genie.ObjectNew.LandlineResponse;
import ratna.genie1.user.genie.client.ApiClientGenie;
import ratna.genie1.user.genie.client.ApiInterface;
import ratna.genie1.user.genie.helper.RegPrefManager;

import ratna.genie1.user.genie.client.ApiClientGenie;
import ratna.genie1.user.genie.client.ApiInterface;
import ratna.genie1.user.genie.helper.RegPrefManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LandLine extends AppCompatActivity implements View.OnClickListener {
    Toolbar toolbar;
    Button btn_lb_proceed;
    EditText acc_user_name,amountEd,stdTv,phoneTv;
    TextView lb_operator_name,circleTv;
    String operator_name;
    private AlertDialog.Builder alertDialog;
    ApiInterface apiService;
    ProgressDialog progressDialog;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    String login_user="";
    private LinearLayout stdlinear;
    String std ="";
    private boolean flag;
    String groupId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_land_line);
        apiService =
                ApiClientGenie.getClient().create(ApiInterface.class);
        progressDialog =new ProgressDialog(this);
        alertDialog=new AlertDialog.Builder(this);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        groupId = RegPrefManager.getInstance(LandLine.this).getUserGroup();
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
        btn_lb_proceed = findViewById(R.id.btn_lb_proceed);
        acc_user_name = findViewById(R.id.acc_user_name);
        lb_operator_name = findViewById(R.id.lb_operator_name);
        stdTv=findViewById(R.id.stdTv);
        stdlinear=findViewById(R.id.stdlinear);
        phoneTv=findViewById(R.id.phoneTv);
        amountEd=findViewById(R.id.amountEd);
        circleTv=findViewById(R.id.circleTv);
        circleTv.setOnClickListener(this);
        lb_operator_name.setOnClickListener(this);
        btn_lb_proceed.setOnClickListener(this);
        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

        SharedPreferences.Editor editor1 = sharedpreferences.edit();
        login_user=sharedpreferences.getString("FLAG", "");
        editor1.commit(); // commit changes


        Log.d("login_user", login_user);

    }

    private Boolean validationNew() {


        if (lb_operator_name.getText().toString().trim().isEmpty()) {

            lb_operator_name.setError("Please Enter Operator Name");

            return false;
        }

        if (circleTv.getText().toString().trim().isEmpty()) {

            circleTv.setError("Please Enter Circle");

            return false;
        }


        if (acc_user_name.getText().toString().trim().isEmpty()) {

            acc_user_name.setError("Please Enter Customer ID");

            return false;
        }

        if (phoneTv.getText().toString().trim().isEmpty()) {

            phoneTv.setError("Please Enter Landline Number");

            return false;
        }
     /*   if(flag==true){

            if (stdTv.getText().toString().trim().isEmpty()) {

                stdTv.setError("Please Enter STD code");

                return false;
            }
        }*/


        if (amountEd.getText().toString().trim().isEmpty()) {

            amountEd.setError("Please Enter Amount");

            return false;
        }

        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.lb_operator_name:
                startActivity(new Intent(getApplicationContext(), LandlineOperator.class));
                finish();
                break;
            case R.id.circleTv:
                RegPrefManager.getInstance(this).setBack("Landline1");
                startActivity(new Intent(getApplicationContext(), DataCardCircleActivity.class));
                finish();
                break;
            case R.id.btn_lb_proceed:
                if(validationNew()) {
                RegPrefManager.getInstance(this).setLandlineBroadband(
                        phoneTv.getText().toString(),lb_operator_name.getText().toString(),
                        acc_user_name.getText().toString(),amountEd.getText().toString());
                    RegPrefManager.getInstance(this).setBackService("Landline");
                    RegPrefManager.getInstance(this).setServiceName("LandLine/BroadBand");
                startActivity(new Intent(LandLine.this,PaymentCartActivity.class));
                finish();
                }
                break;
        }

    }

    //flightPlaceCustomAdapter.setClickListener(this);
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

    @Override
    public void onResume(){
        super.onResume();
        // put your code here...
        String operatorname= RegPrefManager.getInstance(this).getLandlineOperatorname();

        if(operatorname!=null){
            lb_operator_name.setText(operatorname);
        }
        if(lb_operator_name.getText().toString().contains("BSNL")||lb_operator_name.getText().toString().contains("Airtel")){
            flag=true;
            stdlinear.setVisibility(View.GONE);
        }
        else {
            flag=false;
            stdlinear.setVisibility(View.GONE);
        }

        String circlename=RegPrefManager.getInstance(this).getLandlinecirclename();
        if(circlename!=null){
            circleTv.setText(circlename);
        }

    }

    private void networkCircleService(){
        progressDialog.setMessage("Please wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        String customer_id=phoneTv.getText().toString();
        String operator=lb_operator_name.getText().toString();
        String back= RegPrefManager.getInstance(this).getBack();
        String circle;
        if(back.equals("Landline1"))
        {
             circle=RegPrefManager.getInstance(this).getLandlinecirclecode();
        }else {
             circle=RegPrefManager.getInstance(this).gettDDataCardCircleCode();
        }
       // String circle=circleTv.getText().toString();
        String accountno=acc_user_name.getText().toString();
        String amount=amountEd.getText().toString();
        String std="00";

        Call<LandlineResponse> call=apiService.postLandlineRecharge(Integer.parseInt(login_user),
                customer_id,operator,Integer.parseInt(circle),Integer.parseInt(amount),accountno,std);

        call.enqueue(new Callback<LandlineResponse>() {
            @Override
            public void onResponse(Call<LandlineResponse> call, Response<LandlineResponse> response) {
                progressDialog.dismiss();
                boolean status=response.body().isStatus();
                Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call<LandlineResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),"Recharge Failed, Try again",Toast.LENGTH_LONG).show();
            }
        });

    }
}
