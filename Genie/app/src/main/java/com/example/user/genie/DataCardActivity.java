package com.example.user.genie;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.genie.ObjectNew.DataCardCircleResponse;
import com.example.user.genie.ObjectNew.DatacardRechargeResponse;
import com.example.user.genie.ObjectNew.getDataCardCircle;
import com.example.user.genie.client.ApiClientGenie;
import com.example.user.genie.client.ApiInterface;
import com.example.user.genie.helper.RegPrefManager;

import java.math.BigInteger;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
@SuppressWarnings("All")
public class DataCardActivity extends AppCompatActivity implements View.OnClickListener{
    private RadioButton prepaid,postpaid;
    private EditText contact_number,amountTv;
    private ImageView contact_list;
    private TextView operatorTv,circleTv;
    private Button btn_prepaid,btn_postpaid;
    private static final int PICK_CONTACT = 1995;
    Toolbar toolbar;
    private AlertDialog.Builder alertDialog;
    ApiInterface apiService;
    ProgressDialog progressDialog;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    String login_user="";
    private ArrayList<DataCardCircleResponse> dataCardArrayList;
    private String operatorcode="";
    private int  circlecode=0,customer_id,userid,amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_card);
        toolbar = findViewById(R.id.toolbar);
        apiService =
                ApiClientGenie.getClient().create(ApiInterface.class);
        progressDialog =new ProgressDialog(this);
        alertDialog=new AlertDialog.Builder(this);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DataCardActivity.this,MainActivity.class));
                finish();
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
        prepaid=findViewById(R.id.prepaid);
        postpaid=findViewById(R.id.postpaid);
        contact_list=findViewById(R.id.contact_list);
        contact_number=findViewById(R.id.contact_number);
        operatorTv=findViewById(R.id.operatorTv);
        circleTv=findViewById(R.id.circleTv);
        amountTv=findViewById(R.id.amountTv);
        btn_prepaid=findViewById(R.id.btn_prepaid);
        btn_postpaid=findViewById(R.id.btn_postpaid);

        btn_postpaid.setOnClickListener(this);
        btn_prepaid.setOnClickListener(this);
        contact_list.setOnClickListener(this);
        operatorTv.setOnClickListener(this);
        prepaid.setOnClickListener(this);
        postpaid.setOnClickListener(this);
        circleTv.setOnClickListener(this);

       /* if(prepaid.isChecked()){
            if (isNetworkAvailable()) {
                networkCircleService();
            } else {
                noNetwrokErrorMessage();
            }
        }*/
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_prepaid:
                if (validationNew()) {
                    if (isNetworkAvailable()) {
                        networkRecharge();
                    }
                    else {
                        noNetwrokErrorMessage();
                    }
                }
                break;
            case R.id.btn_postpaid:
                if (validationNew()) {
                    if (isNetworkAvailable()) {
                        networkRecharge();
                    }
                    else {
                        noNetwrokErrorMessage();
                    }
                }
                break;
            case R.id.contact_list:
                Intent intent= new Intent(Intent.ACTION_PICK,  ContactsContract.Contacts.CONTENT_URI);

                startActivityForResult(intent, PICK_CONTACT);
                break;
            case R.id.operatorTv:
                startActivity(new Intent(DataCardActivity.this,DataCardOperatorActivity.class));
                finish();
                break;
            case R.id.postpaid:
                circleTv.setVisibility(View.GONE);
                btn_prepaid.setVisibility(View.GONE);
                btn_postpaid.setVisibility(View.VISIBLE);
                circlecode=0;
                break;
            case R.id.prepaid:
                btn_prepaid.setVisibility(View.VISIBLE);
                btn_postpaid.setVisibility(View.GONE);
                circleTv.setVisibility(View.VISIBLE);

                    circlecode=Integer.parseInt(RegPrefManager.getInstance(this).gettDDataCardCircleCode());


                break;
            case R.id.circleTv:
                startActivity(new Intent(DataCardActivity.this,DataCardCircleActivity.class));
                finish();
                break;
        }
    }

    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        switch (reqCode) {
            case (PICK_CONTACT) :
                if (resultCode == Activity.RESULT_OK) {
                    Uri contactData = data.getData();
                    Cursor c = getContentResolver().query(contactData, null, null, null, null);
                    if (c.moveToFirst()) {
                        String contactId = c.getString(c.getColumnIndex(ContactsContract.Contacts._ID));
                        String hasNumber = c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                        String num = "";
                        if (Integer.valueOf(hasNumber) == 1) {
                            Cursor numbers =getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);
                            while (numbers.moveToNext()) {
                                num = numbers.getString(numbers.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                                //       Toast.makeText(MobileRecharge.this, "Number="+num, Toast.LENGTH_LONG).show();
                                contact_number.setText(num);
                            }
                        }
                    }
                    break;
                }
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        // put your code here...
        String operatorname= RegPrefManager.getInstance(this).getDataCardOperator();

        if(operatorname!=null){
            operatorTv.setText(operatorname);
        }

        String circlename=RegPrefManager.getInstance(this).getDDataCardCirclename();
        if(circlename!=null){
            circleTv.setText(circlename);
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
    private void networkRecharge(){
        progressDialog.setMessage("Please wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        String v=contact_number.getText().toString();
        Log.d("Tagvalue",v);
       String cus=contact_number.getText().toString();

            amount=Integer.parseInt(amountTv.getText().toString());
            if (prepaid.isChecked()) {
                circlecode = Integer.parseInt(RegPrefManager.getInstance(this).gettDDataCardCircleCode());
            }else {
                circlecode=0;
            }

        operatorcode=RegPrefManager.getInstance(this).getDataCardOperatorCode();
        Call<DatacardRechargeResponse> call=apiService.postDatacardRecharge(Integer.parseInt(login_user),cus,operatorcode,circlecode,amount);
        call.enqueue(new Callback<DatacardRechargeResponse>() {
            @Override
            public void onResponse(Call<DatacardRechargeResponse> call, Response<DatacardRechargeResponse> response) {
                progressDialog.dismiss();
                String id=response.body().getApiTransID();
                String status=response.body().getStatus();

                startActivity(new Intent(DataCardActivity.this,ThankYouActivity.class));
                finish();



            }

            @Override
            public void onFailure(Call<DatacardRechargeResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),"Failure",Toast.LENGTH_LONG).show();
            }
        });
    }

    private Boolean validationNew() {


        if (contact_number.getText().toString().trim().isEmpty()) {

            contact_number.setError("Please Enter Customer Id");

            return false;
        }


        if (operatorTv.getText().toString().trim().isEmpty()) {

            operatorTv.setError("Please Enter Operator name");

            return false;
        }

        if(circlecode!=0) {
            if (circleTv.getText().toString().trim().isEmpty()) {

                circleTv.setError("Please Enter Circle name");

                return false;
            }
        }
        if (amountTv.getText().toString().trim().isEmpty()) {

            amountTv.setError("Please Enter Amount");

            return false;
        }

        return true;
    }



}
