package ratna.genie1.user.genie;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class PaymentActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView month;
    String strDateFormat = "MM";
    SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
    Date date = new Date();
    String month_no;
    TextView paid_amount;
    String amount;
    RadioGroup radioGroup;
    RadioButton cod_method, debitcard_method, creditcard_method;
    Button proceed_payment_btn;

    private DatePickerDialog fromDatePickerDialog;
    Calendar mcurrenttime;
    private SimpleDateFormat dateFormatter;
    String Date_;
    LinearLayout payment_card_layout;

    String giftImage;
    String giftId;
    String toName;
    String senderName;
    String toNumber;
    String senderNo;
    String entry_date;
    String entry_time;
    String toAddress;
    String fromAddress;

    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    String login_user="";

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedpreferences.edit();
        login_user=sharedpreferences.getString("FLAG", "");
        editor.commit(); // commit changes


        paid_amount = findViewById(R.id.paid_amount);
        proceed_payment_btn = findViewById(R.id.proceed_payment_btn);
        radioGroup = findViewById(R.id.radioGroup);
        cod_method = findViewById(R.id.cod_method);
        debitcard_method = findViewById(R.id.debitcard_method);
        creditcard_method = findViewById(R.id.creditcard_method);
        payment_card_layout = findViewById(R.id.payment_card_layout);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        SharedPreferences.Editor editor1 = pref.edit();
        // commit changes
        String price=pref.getString("GIFT_PRICE", null);
        editor1.commit();

        paid_amount.setText(this.getResources().getString(R.string.rupee)+" "+price);

        cod_method.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proceed_payment_btn.setVisibility(View.VISIBLE);
                payment_card_layout.setVisibility(View.GONE);
            }
        });

        debitcard_method.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proceed_payment_btn.setVisibility(View.VISIBLE);
                payment_card_layout.setVisibility(View.VISIBLE);
            }
        });

        proceed_payment_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AsynGiftSubmit().execute();

            }
        });

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if(bundle != null) {

            giftImage = bundle.getString("Image");
            giftId = bundle.getString("giftID");
            toName = bundle.getString("ReceiverName");
            senderName = bundle.getString("Sender");
            toNumber = bundle.getString("ReceiverNumber");
            senderNo = bundle.getString("SenderNumber");
            entry_date = bundle.getString("EntryDate");
            entry_time = bundle.getString("EntryTime");
            toAddress = bundle.getString("ToAddress");
            fromAddress = bundle.getString("FromAddress");

            Log.d("url", senderNo);

        }

      //  month = findViewById(R.id.month);
/*
        month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strDateFormat = "MM";
                sdf = new SimpleDateFormat(strDateFormat);
              //  System.out.println("Current Month in MM format : " + sdf.format(date));
                month_no = ( sdf.format(date));
                Toast.makeText(PaymentActivity.this, month_no, Toast.LENGTH_SHORT).show();
            }
        });
*/
    }

    private class AsynGiftSubmit extends AsyncTask<Void, Void, Void> {
        ProgressDialog pDialog;
        String success = null,data="",status="true";

        @Override
        protected Void doInBackground(Void... params) {
            pDialog.show();
            ArrayList<NameValuePair> cred = new ArrayList<NameValuePair>();
            cred.add(new BasicNameValuePair("user_id",login_user));//user_email
            cred.add(new BasicNameValuePair("gift_id",giftId ));
            cred.add(new BasicNameValuePair("to_name",toName ));
            cred.add(new BasicNameValuePair("sender_name",senderName ));
            cred.add(new BasicNameValuePair("phone",toNumber ));
            cred.add(new BasicNameValuePair("sender_no",senderNo ));
            cred.add(new BasicNameValuePair("date",entry_date ));
            cred.add(new BasicNameValuePair("time",entry_time ));
            cred.add(new BasicNameValuePair("to_address",toAddress ));
            cred.add(new BasicNameValuePair("from_address",fromAddress ));
            Log.v("RES","Sending data" +login_user+ giftId+ toName+ senderName+ toNumber + senderNo +entry_date+entry_time
                    +toAddress+fromAddress);


            String urlRouteList = "https://genieservice.in/api/service/addgift";
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

            if(status.equals("true"))
            {
                Toast.makeText(getApplicationContext(),"Gift Ordered Successfully", Toast.LENGTH_LONG).show();
                startActivity(new Intent(PaymentActivity.this,ThankYouActivity.class));
                finish();

            }
            else{
                startActivity(new Intent(PaymentActivity.this,FailureActivity.class));
                Toast.makeText(getApplicationContext(),data, Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(PaymentActivity.this);
            pDialog.setMessage("Loading In...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
    }

}
