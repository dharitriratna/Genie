package ratna.genie1.user.genie;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import ratna.genie1.user.genie.helper.RegPrefManager;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import ratna.genie1.user.genie.helper.RegPrefManager;

public class FSERegisterPaymentActivity extends AppCompatActivity {
    Toolbar toolbar;
    EditText amountTv;
    Button btnProceed;

    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    String login_user="";

    String SendingAmount;
    String ReferralCode;
    Spinner paymentmethodspinner;
    String paymentMethod;
    EditText refCode;
    LinearLayout netBankingLayout,upiLayout;

    private AlertDialog.Builder alertDialog;
    private DatePickerDialog fromDatePickerDialog;
    Calendar mcurrenttime;
    private SimpleDateFormat dateFormatter;
    String Date_;
    TextView dept_date;
    EditText payment_method;
    String fseuserID;
    String fseretaileruserID;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_payment);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mcurrenttime = Calendar.getInstance();
        dateFormatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);

       // fseuserID = RegPrefManager.getInstance(getApplicationContext()).getFseUserId();
        fseretaileruserID = RegPrefManager.getInstance(getApplicationContext()).getRetailerUserId();


        amountTv = findViewById(R.id.amountTv);
        refCode = findViewById(R.id.refCode);
        btnProceed = findViewById(R.id.btnProceed);
        paymentmethodspinner= findViewById(R.id.paymentmethodspinner);
        netBankingLayout = findViewById(R.id.netBankingLayout);
        upiLayout = findViewById(R.id.upiLayout);
        dept_date = findViewById(R.id.dept_date);
        payment_method = findViewById(R.id.payment_method);

       /* paymentmethodspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                paymentMethod= paymentmethodspinner.getItemAtPosition(paymentmethodspinner.getSelectedItemPosition()).toString();
                // Toast.makeText(getApplicationContext(),paymentMethod,Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });*/

        dept_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                fromDatePickerDialog = new DatePickerDialog(FSERegisterPaymentActivity.this, new DatePickerDialog.OnDateSetListener() {

                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, monthOfYear, dayOfMonth);

                        Date_ = dateFormatter.format(newDate.getTime());
                        dept_date.setText(Date_);
                        dept_date.setCursorVisible(false);

                    }},
                        mcurrenttime.get(Calendar.YEAR), mcurrenttime.get(Calendar.MONTH), mcurrenttime.get(Calendar.DAY_OF_MONTH));
                fromDatePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                fromDatePickerDialog.show();
            }
        });

        payment_method.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] options_array = {"IMPS", "BHIM UPI"};
                final String IMPSMethod = "IMPS";
                final String UPIMethod = "UPI";

                AlertDialog.Builder builder = new AlertDialog.Builder(FSERegisterPaymentActivity.this);
                builder.setTitle("Payment Method");
                builder.setItems(options_array, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (options_array[item].equals("IMPS")) {
                            payment_method.setText(IMPSMethod);
                            netBankingLayout.setVisibility(View.VISIBLE);
                            upiLayout.setVisibility(View.GONE);
                            payment_method.setText(IMPSMethod);

                        } else if (options_array[item].equals("BHIM UPI")) {
                            payment_method.setText(UPIMethod);
                            upiLayout.setVisibility(View.VISIBLE);
                            netBankingLayout.setVisibility(View.GONE);

                            //  dailog();
                        }
                    }
                });
                builder.show();

            }
        });



        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedpreferences.edit();
        login_user=sharedpreferences.getString("FLAG", "");
        editor.commit(); //

        btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendingAmount = amountTv.getText().toString().trim();
                ReferralCode = refCode.getText().toString().trim();

                if (SendingAmount.length() < 1){
                    amountTv.setText("Please Enter Amount");
                }
               /* else if (ReferralCode.length() < 1){
                    refCode.setText("Please Enter Code");
                }*/

                else if (Date_.length() < 1){
                    dept_date.setError("Please Enter Date");
                }
             /*   else if (paymentMethod.length() < 1){
                    payment_method.setError("Please Set Payment Method");
                }*/
                else {
                    new Asynctask().execute();
                }
            }
        });
    }

    private class Asynctask extends AsyncTask<Void, Void, Void> {
        ProgressDialog pDialog;
        String success = null,message="",status="true";

        @Override
        protected Void doInBackground(Void... params) {
            pDialog.show();
            ArrayList<NameValuePair> cred = new ArrayList<NameValuePair>();
            cred.add(new BasicNameValuePair("user_id",fseretaileruserID));
            cred.add(new BasicNameValuePair("amount",SendingAmount ));
            cred.add(new BasicNameValuePair("ref_no",ReferralCode ));
            cred.add(new BasicNameValuePair("date_of_deposit",Date_ ));
            cred.add(new BasicNameValuePair("payment_method",paymentMethod ));
            Log.v("RES","Sending data " +login_user +SendingAmount+ReferralCode+Date_+paymentMethod );

            String urlRouteList="https://genieservice.in/api/user/registerPaymentReq";
            try {
                String route_response = CustomHttpClient.executeHttpPost(urlRouteList, cred);

                success = route_response;
                JSONObject jsonObject = new JSONObject(success);

                // user_email=jsonObject.getString("user_email");
                status=jsonObject.getString("status");
                if(status.equals("false")) {
                    message = jsonObject.getString("message");
                }
                String data=jsonObject.getString("message");
                // Toast.makeText(Cart.this, sum_total, Toast.LENGTH_SHORT).show();
                JSONObject jsonObject1 = new JSONObject(data);
               /* String user_id=jsonObject1.getString("user_id");
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("FLAG", user_id);
                Log.d("user_id", user_id);
                editor.commit();
                String user_phone = jsonObject1.getString("phone");
                RegPrefManager.getInstance(AddMoneyActivity.this).setPhoneNo(user_phone);*/
                //   String user_email=jsonObject1.getString("user_email");

            } catch (Exception e)

            {
                Log.v("Connection error", e.toString());

            }return null;
        }
        protected void onPostExecute(Void result) {
            pDialog.dismiss();

            if(status.equals("true"))
            {
                Toast.makeText(getApplicationContext(),"Sucessfully requested", Toast.LENGTH_LONG).show();
                //   startActivity(new Intent(getApplicationContext(),VerifyWalletOTPActivity.class));
                finish();

                //    startActivity(new Intent(AddMoneyActivity.this,OTPActivity.class));finish();
            }
            else{
                Toast.makeText(getApplicationContext(),message, Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(FSERegisterPaymentActivity.this);
            pDialog.setMessage("Loading In...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
    }

}
