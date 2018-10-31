package com.example.user.genie;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class MobileRecharge extends AppCompatActivity {
    Toolbar toolbar;
    ImageView contact_list;
    EditText contact_number, amount;
    TextView operator, circle;
    RadioGroup radioGroup;
    RadioButton prepaid, postpaid;
    Button btn_prepaid,btn_postpaid;

    private static final int REQUEST_CODE = 1995;

    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    String login_user="";

    String phone_number;
    String carrierName;
    String operator_circle_name;
    String recharge_amount;
    int selectedId = 1;
    String number;
    String check;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_recharge);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



        radioGroup = findViewById(R.id.radioGroup);

      //  int selectedId=radioGroup.getCheckedRadioButtonId();
        prepaid = findViewById(R.id.prepaid);
        prepaid.setSelected(true);
        postpaid = findViewById(R.id.postpaid);
        btn_prepaid = findViewById(R.id.btn_prepaid);
        btn_postpaid = findViewById(R.id.btn_postpaid);
        contact_list = findViewById(R.id.contact_list);
        contact_number = findViewById(R.id.contact_number);
        amount = findViewById(R.id.amount);
        operator = findViewById(R.id.operator);
        circle = findViewById(R.id.circle);
        phone_number = contact_number.getText().toString().trim();
        carrierName = operator.getText().toString().trim();

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        // commit changes
        String number=pref.getString("PHONE_NUMBER", null);
        editor.commit();
      //  Toast.makeText(this, number, Toast.LENGTH_SHORT).show();



        circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MobileRecharge.this, MobileOperatorCircle.class));
            }
        });

        contact_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent, REQUEST_CODE);

            }
        });

       prepaid.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               btn_prepaid.setVisibility(View.VISIBLE);
               btn_postpaid.setVisibility(View.GONE);
           }
       });
        postpaid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_prepaid.setVisibility(View.GONE);
                btn_postpaid.setVisibility(View.VISIBLE);
            }
        });

        contact_number.setText(number);

      //  if (contact_number.equals(contact_number.getText().toString()))

        /*contact_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
                startActivityForResult(intent, 1);
            }
        });
*/

        btn_prepaid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phone_number = contact_number.getText().toString();
                carrierName = operator.getText().toString();
                operator_circle_name = circle.getText().toString();
                recharge_amount = amount.getText().toString();

                if (phone_number.length() < 1){
                    contact_number.setError("Enter Your Mobile Number");
                }
                else if (carrierName.length()< 1){
                    operator.setError("Enter Your Operator");
                }
                else if (recharge_amount.length() < 1){
                    amount.setError("Enter Your Amount");
                }
                else {
                    new AsynSignInDetails().execute();
                }

            }
        });



        operator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("PHONE_NUMBER", phone_number = contact_number.getText().toString());
                editor.commit();
                startActivity(new Intent(MobileRecharge.this,MobileOperators.class));
              //  Toast.makeText(MobileRecharge.this, phone_number, Toast.LENGTH_SHORT).show();
                finish();
            }
        });


        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

        SharedPreferences.Editor editor1 = sharedpreferences.edit();
        login_user=sharedpreferences.getString("FLAG", "");
        editor1.commit(); // commit changes


        Log.d("login_user", login_user);





        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if(bundle != null) {

            operator_circle_name = bundle.getString("CIRCLE_NAME");
         //   Log.d("CIRCLE_NAME", operator_circle_name);
            carrierName = bundle.getString("OPERATOR_NAME");
//            number = bundle.getString("NUMBER");
            operator.setText(carrierName);
            circle.setText(operator_circle_name);
        //    contact_number.setText(number);

        }

      /*  Intent intent1 = getIntent();
        Bundle bundle1 = intent1.getExtras();

        if (bundle1!= null){
            carrierName = bundle1.getString("OPERATOR_NAME");
            operator.setText(carrierName);
          //  Log.d("OPERATOR_NAME", carrierName);
        }*/

        int Permission_All = 1;

        String[] Permissions = {
//                android.Manifest.permission.ACCESS_COARSE_LOCATION,
//                android.Manifest.permission.ACCESS_FINE_LOCATION,
//                Manifest.permission.CALL_PHONE,
                android.Manifest.permission.INTERNET,
                Manifest.permission.READ_CONTACTS,};
        if(!hasPermissions(this, Permissions)){
            ActivityCompat.requestPermissions(this, Permissions, Permission_All);
        }
    }


    public static boolean hasPermissions(Context context, String... permissions){

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N_MR1 && context!=null && permissions!=null){
            for(String permission: permissions){
                if(ActivityCompat.checkSelfPermission(context, permission)!= PackageManager.PERMISSION_GRANTED){
                    return  false;
                }
            }
        }
        return true;
    }
    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_CONTACT && resultCode == RESULT_OK) {
            Uri contactUri = data.getData();
            Cursor cursor = getContentResolver().query(contactUri, null, null, null, null);
            cursor.moveToFirst();
            int column = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            contact_number.setText(column);
         //  (new normalizePhoneNumberTask()).execute(cursor.getString(column));
            Log.d("phone_number", cursor.getString(column));
        }
    }*/


    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        switch (reqCode) {
            case (REQUEST_CODE):
                if (resultCode == Activity.RESULT_OK) {
                    Uri contactData = data.getData();
                    Cursor c = getContentResolver().query(contactData, null, null, null, null);
                    if (c.moveToFirst()) {
                        String contactId = c.getString(c.getColumnIndex(ContactsContract.Contacts._ID));
                        String hasNumber = c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                        String num = "";
                        if (Integer.valueOf(hasNumber) == 1) {
                            Cursor numbers = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);
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

    private class AsynSignInDetails extends AsyncTask<Void, Void, Void> {
        ProgressDialog pDialog;
        String success = null,data="",status="true";

        @Override
        protected Void doInBackground(Void... params) {
            pDialog.show();
            ArrayList<NameValuePair> cred = new ArrayList<NameValuePair>();
            cred.add(new BasicNameValuePair("user_id",login_user));//user_email
            cred.add(new BasicNameValuePair("phone",phone_number ));
            cred.add(new BasicNameValuePair("operator",carrierName ));
            cred.add(new BasicNameValuePair("circle",operator_circle_name ));
            cred.add(new BasicNameValuePair("amount",recharge_amount ));

            Log.v("RES","Sending data " +login_user+ phone_number+ carrierName +operator_circle_name+recharge_amount);

            String urlRouteList="http://demo.ratnatechnology.co.in/genie/api/service/mobile_recharge";
            try {
                String route_response = CustomHttpClient.executeHttpPost(urlRouteList, cred);

                success = route_response;
                JSONObject jsonObject = new JSONObject(success);

                // user_email=jsonObject.getString("user_email");
                status=jsonObject.getString("status");
                if(status.equals("false")) {
                    data = jsonObject.getString("ErrorMessage");

                }
                String data=jsonObject.getString("data");
                // Toast.makeText(Cart.this, sum_total, Toast.LENGTH_SHORT).show();
                JSONObject jsonObject1 = new JSONObject(data);

            } catch (Exception e)

            {
                Log.v("Connection error", e.toString());

            }return null;
        }
        protected void onPostExecute(Void result) {
            pDialog.dismiss();

            if(status.equals("SUCCESS"))
            {
                Toast.makeText(getApplicationContext(),"Recharge Successful", Toast.LENGTH_LONG).show();

                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("AMOUNT", recharge_amount = amount.getText().toString());
                editor.commit();
                Toast.makeText(MobileRecharge.this, recharge_amount, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MobileRecharge.this,PaymentActivity.class));finish();
            }
            else{
                Toast.makeText(getApplicationContext(),"Some Error Occured, Please try again later ", Toast.LENGTH_LONG).show();
                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.remove("PHONE_NUMBER");
                editor.commit();
            }
        }

        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(MobileRecharge.this);
            pDialog.setMessage("Loading In...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
    }


/*
   private class normalizePhoneNumberTask extends AsyncTask<String, Void, String> {

        //input your app key and secret from the Sinch dashboard
        private String appKey = "your_app_key";
        private String appSecret = "your_app_secret";

        //takes phone number string as an argument
        @Override
        protected String doInBackground(String... params) {

            String normalizedPhoneNumber = "";

            try {
                //get ready to make a get request to normalize the phone number
                HttpClient httpclient = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet("https://callingapi.sinch.com/v1/calling/query/number/" + params[0].replaceAll("\\s+",""));

                //sinch uses basic authentication
                String usernamePassword = "application:" + appKey + ":" + appSecret;
                String encoded = Base64.encodeToString(usernamePassword.getBytes(), Base64.NO_WRAP);
                httpGet.addHeader("Authorization", "Basic " + encoded);

                //handle the response
                HttpResponse response = httpclient.execute(httpGet);
                ResponseHandler<String> handler = new BasicResponseHandler();

                //parse JSON response from Sinch to get phone number
                normalizedPhoneNumber = parseJSONResponse(handler.handleResponse(response));
            } catch (ClientProtocolException e) {
                Log.d("ClientProtocolException", e.getMessage());
            } catch (IOException e) {
                Log.d("IOException", e.getMessage());
            }

            return normalizedPhoneNumber;
        }

        //once the asynctask is complete, display a toast message with the normalized phone number
        @Override
        protected void onPostExecute(String normalizedPhoneNumber) {
            //if you want to make a call with sinch, this is the place to do it!
            Toast.makeText(getApplicationContext(), normalizedPhoneNumber, Toast.LENGTH_LONG).show();
        }

        //the sinch api returns a json like {"number":{"restricted":false,"countryId":"US","numberType":"Mobile","normalizedNumber":"+16507141052"}}
        //this method will return a string of just the phone number, +16507141052
        private String parseJSONResponse(String jsonString) {

            String returnString = "";

            try {
                JSONObject jsonObject = new JSONObject(jsonString);
                returnString = jsonObject.getJSONObject("number").getString("normalizedNumber");
            } catch (JSONException e) {
                Log.d("JSONException", e.getMessage());
            }

            return returnString;
        }
    }
*/
}
