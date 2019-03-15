package ratna.genie1.user.genie;

import android.app.DatePickerDialog;
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
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import ratna.genie1.user.genie.ObjectNew.RemitterValidateResponse;
import ratna.genie1.user.genie.ObjectNew.RequestWalletResponse;
import ratna.genie1.user.genie.Utils.VolleySingleton;
import ratna.genie1.user.genie.client.ApiClientGenie;
import ratna.genie1.user.genie.client.ApiInterface;
import ratna.genie1.user.genie.helper.RegPrefManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestWalletActivity extends AppCompatActivity {
    Toolbar toolbar;
    EditText amountTv;
    Button btnProceed;

    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    String login_user="";

    private AlertDialog.Builder alertDialog;
    ApiInterface apiService;
    String SendingAmount;
    String ReferralCode;
    Spinner paymentmethodspinner;
    String paymentMethod;
    EditText refCode;
    LinearLayout netBankingLayout,upiLayout,phonepeLayout,TvLayout;

    ProgressDialog progressDialog;
    private DatePickerDialog fromDatePickerDialog;
    Calendar mcurrenttime;
    private SimpleDateFormat dateFormatter;
    String Date_;
    TextView dept_date;
    TextView payment_method;
    int i = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_wallet);
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


        apiService =
                ApiClientGenie.getClient().create(ApiInterface.class);
        progressDialog = new ProgressDialog(this);
        amountTv = findViewById(R.id.amountTv);
        refCode = findViewById(R.id.refCode);
        btnProceed = findViewById(R.id.btnProceed);
        paymentmethodspinner= findViewById(R.id.paymentmethodspinner);
        netBankingLayout = findViewById(R.id.netBankingLayout);
        upiLayout = findViewById(R.id.upiLayout);
        phonepeLayout = findViewById(R.id.phonepeLayout);
        TvLayout = findViewById(R.id.TvLayout);
        dept_date = findViewById(R.id.dept_date);
        payment_method = findViewById(R.id.payment_method);

       /* paymentmethodspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                paymentMethod= paymentmethodspinner.getItemAtPosition(paymentmethodspinner.getSelectedItemPosition()).toString();
                Toast.makeText(getApplicationContext(),paymentMethod,Toast.LENGTH_LONG).show();
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
                fromDatePickerDialog = new DatePickerDialog(RequestWalletActivity.this, new DatePickerDialog.OnDateSetListener() {

                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, monthOfYear, dayOfMonth);

                        Date_ = dateFormatter.format(newDate.getTime());
                        dept_date.setText(Date_);
                        dept_date.setCursorVisible(false);

                    }},
                        mcurrenttime.get(Calendar.YEAR), mcurrenttime.get(Calendar.MONTH), mcurrenttime.get(Calendar.DAY_OF_MONTH));
                fromDatePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                fromDatePickerDialog.show();
            }
        });

        payment_method.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] options_array = {"IMPS", "BHIM UPI"};
                final String IMPSMethod = "IMPS";
                final String UPIMethod = "BHIM UPI";
            //    final String PhonePeMethod = "PHONE PE";

                AlertDialog.Builder builder = new AlertDialog.Builder(RequestWalletActivity.this);
                builder.setTitle("Payment Method");
                builder.setItems(options_array, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (options_array[item].equals("IMPS")) {
                            payment_method.setText(IMPSMethod);
                            paymentMethod = IMPSMethod;
                            netBankingLayout.setVisibility(View.VISIBLE);
                            upiLayout.setVisibility(View.GONE);
                            phonepeLayout.setVisibility(View.GONE);
                            TvLayout.setVisibility(View.GONE);

                        } else if (options_array[item].equals("BHIM UPI")) {
                            payment_method.setText(UPIMethod);
                            paymentMethod = UPIMethod;
                            upiLayout.setVisibility(View.VISIBLE);
                            netBankingLayout.setVisibility(View.GONE);
                            phonepeLayout.setVisibility(View.GONE);
                            TvLayout.setVisibility(View.VISIBLE);
                          //  dailog();
                        }
                       /* else if (options_array[item].equals("PHONE PE")){
                            payment_method.setText(PhonePeMethod);
                            paymentMethod = PhonePeMethod;
                            netBankingLayout.setVisibility(View.GONE);
                            upiLayout.setVisibility(View.GONE);
                            phonepeLayout.setVisibility(View.VISIBLE);
                            TvLayout.setVisibility(View.VISIBLE);
                        }*/
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
               /* else if (paymentMethod.length() < 1){
                    payment_method.setError("Please Set Payment Method");
                }*/
                else {
                    if (isNetworkAvailable()) {
                       // new Asynctask().execute();//register add beneficiary
                      //  volleyResponseAddMoney();
                        responseWalletRetrofit();
                    }
                    else {
                        noNetwrokErrorMessage();
                    }
                  //  new Asynctask().execute();
                }
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

    private class Asynctask extends AsyncTask<Void, Void, Void> {
        ProgressDialog pDialog;
        String success = null,message="",status;

        @Override
        protected Void doInBackground(Void... params) {
            pDialog.show();
            ArrayList<NameValuePair> cred = new ArrayList<NameValuePair>();
            cred.add(new BasicNameValuePair("user_id",login_user));
            cred.add(new BasicNameValuePair("amount",SendingAmount ));
            cred.add(new BasicNameValuePair("ref_no",ReferralCode ));
            cred.add(new BasicNameValuePair("date_of_deposit",Date_ ));
            cred.add(new BasicNameValuePair("payment_method",paymentMethod ));
            Log.v("RES","Sending data " +login_user +SendingAmount+ReferralCode+Date_+paymentMethod );

            String urlRouteList="https://genieservice.in/api/user/moneyTransferReq";
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
            try {

                if (status.equals("true")) {
                    Toast.makeText(getApplicationContext(), "Sucessfully requested", Toast.LENGTH_LONG).show();
                    //   startActivity(new Intent(getApplicationContext(),VerifyWalletOTPActivity.class));
                    finish();

                    //    startActivity(new Intent(AddMoneyActivity.this,OTPActivity.class));finish();
                } else {
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                }

            }catch (Exception e){
                Toast.makeText(getApplicationContext(), "Genie is away! Try after sometime", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(RequestWalletActivity.this);
            pDialog.setMessage("Loading In...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
      }




    private void volleyResponseAddMoney() {

        progressDialog.show();
        progressDialog.setMessage("Loading...");
        String tag_json_req = "user_login";
        StringRequest data = new StringRequest(com.android.volley.Request.Method.POST,
                "https://genieservice.in/api/user/moneyTransferReq",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        try {
                            Log.d(" response is ", response);
                            Toast.makeText(RequestWalletActivity.this, response, Toast.LENGTH_SHORT).show();

                            /*{
                                "status" = "true";
                                "userid" = "1";

                            }*/

                            JSONObject jsonObject = new JSONObject(response);

                            boolean status1 =jsonObject.getBoolean("status");
                            String message1 = jsonObject.getString("message");

                            if (status1==true){
                                Toast.makeText(getApplicationContext(), message1, Toast.LENGTH_LONG).show();
                                //   startActivity(new Intent(getApplicationContext(),VerifyWalletOTPActivity.class));
                                finish();

                            }
                            else {
                                Toast.makeText(getApplicationContext(), message1, Toast.LENGTH_LONG).show();
                            }




                            /*catch (Exception e){
                                Toast.makeText(getApplicationContext(), "Some Error Occured! Try after sometime", Toast.LENGTH_LONG).show();
                                Toast.makeText(PaymentCartActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
*/
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(RequestWalletActivity.this, "There may be some error or your wallet balance is low", Toast.LENGTH_SHORT).show();

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
                        Toast.makeText(RequestWalletActivity.this, "Check your network connection.",
                                Toast.LENGTH_LONG).show();
                    }
                } else
                    Toast.makeText(RequestWalletActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {


            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //phone_no=8007972554&pass=123456
                Map<String, String> params = new HashMap<>();
                params.put("user_id",login_user);
                params.put("amount",SendingAmount);
                params.put("ref_no",ReferralCode);
                params.put("date_of_deposit",Date_);
                params.put("payment_method",paymentMethod);



                Log.d("params are :", "" + params);

                return params;
            }
        };
        data.setRetryPolicy(new
                DefaultRetryPolicy(30000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance().getRequestQueue().add(data).addMarker(tag_json_req);

        }


    private void responseWalletRetrofit(){
        progressDialog.setMessage("Please wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        //   String pincode=pincodeTv.getText().toString();
        Call<RequestWalletResponse> call=apiService.postRequestWallet(login_user,SendingAmount,ReferralCode,Date_,paymentMethod);
        call.enqueue(new Callback<RequestWalletResponse>() {
            @Override
            public void onResponse(Call<RequestWalletResponse> call, Response<RequestWalletResponse> response) {
                try{
                    progressDialog.dismiss();
                    boolean status=response.body().isStatus();

                    if(status == true){

                        String message = response.body().getMessage();
                        Toast.makeText(RequestWalletActivity.this, message, Toast.LENGTH_SHORT).show();
                        // DialogShow();
                        finish();
                    }else {

                        String message1 = response.body().getMessage();
                        Toast.makeText(getApplicationContext(),message1,Toast.LENGTH_LONG).show();

                    }
                } catch (IllegalStateException ex){
                    ex.printStackTrace();
                } catch (Exception e){
                    e.printStackTrace();}
            }

            @Override
            public void onFailure(Call<RequestWalletResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),"Error!",Toast.LENGTH_LONG).show();
            }
        });
    }

}
