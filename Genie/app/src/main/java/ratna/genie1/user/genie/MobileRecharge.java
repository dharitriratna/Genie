package ratna.genie1.user.genie;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import ratna.genie1.user.genie.Adapter.BrowsePlansAdapter;
import ratna.genie1.user.genie.ObjectNew.BrowsePlansResponse;
import ratna.genie1.user.genie.ObjectNew.OperatorFinderResponse;
import ratna.genie1.user.genie.Utils.Count;
import ratna.genie1.user.genie.client.ApiInterface;
import ratna.genie1.user.genie.helper.RegPrefManager;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import ratna.genie1.user.genie.client.ApiInterface;
import ratna.genie1.user.genie.helper.RegPrefManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static ratna.genie1.user.genie.AddMoneyActivity.REQUEST_ID_MULTIPLE_PERMISSIONS;

public class MobileRecharge extends AppCompatActivity {
    Toolbar toolbar;
    ImageView contact_list;
    EditText contact_number, amount;
    TextView operator, circle;
    RadioGroup radioGroup;
    RadioButton prepaid, postpaid;
    Button btn_prepaid,btn_postpaid;
    LinearLayout browseplansLayout;
    ProgressDialog progressDialog;
    int i=0;
    private AlertDialog.Builder alertDialog;
    ApiInterface apiService;


    private static final int REQUEST_CODE = 1995;
    private static final int PICK_CONTACT = 1995;
    private static final int REQUEST_MULTIPLE_PERMISSIONS = 1985;

    private static final int STORAGE_PERMISSION_CODE = 123;

    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    String login_user="";

    String phone_number;
    String carrierName;
    String operator_circle_name;
    String operator_code;
    String circle_code;
    String recharge_amount;
    int selectedId = 1;
    String number;
    String check;
    String service_id;
    TextView serviceId;
    String groupId;
    boolean radioClickprepaid,radioClickpostpaid;
    public static final int REQUEST_READ_CONTACTS = 79;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_recharge);
      //  requestStoragePermission();
    //    AccessContact();
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        groupId = RegPrefManager.getInstance(MobileRecharge.this).getUserGroup();
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
            //   onBackPressed();
            }
        });

        alertDialog=new AlertDialog.Builder(this);
        radioGroup = findViewById(R.id.radioGroup);
        browseplansLayout = findViewById(R.id.browseplansLayout);
        serviceId=findViewById(R.id.serviceId);
        service_id = serviceId.getText().toString().trim();
        Log.d("tag", service_id);
        RegPrefManager.getInstance(MobileRecharge.this).setServiceId(service_id);


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
        progressDialog = new ProgressDialog(this);
        phone_number = contact_number.getText().toString().trim();

        phone_number = RegPrefManager.getInstance(getApplicationContext()).getPhoneNo();
    //    Toast.makeText(this, phone_number, Toast.LENGTH_SHORT).show();
        contact_number.setText(phone_number);


       /* SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        // commit changes
        String number=pref.getString("PHONE_NUMBER", null);
        editor.commit();
        Toast.makeText(this, number, Toast.LENGTH_SHORT).show();
*/

     /*   Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if(bundle != null) {

            service_id = bundle.getString("MobileId");

        }

        Toast.makeText(getApplicationContext(), service_id, Toast.LENGTH_SHORT).show();
*/

        carrierName = operator.getText().toString().trim();

        String value=RegPrefManager.getInstance(MobileRecharge.this).getRadioClick();
        if(value!=null) {
            if (value.equals("1")) {
                prepaid.setChecked(true);
                postpaid.setChecked(false);
            } else {
                postpaid.setChecked(true);
                prepaid.setChecked(false);
            }
        }

  //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> time duration
       /* if (isNetworkAvailable()) {
            networkCircleService();

        } else {
            noNetwrokErrorMessage();
        }*/

/*       */
  //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


/*
        contact_number.setCustomSelectionActionModeCallback(new ActionMode.Callback() {

            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            public void onDestroyActionMode(ActionMode mode) {
            }

            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                return false;
            }
        });
*/



        carrierName= RegPrefManager.getInstance(this).getMobileOperatorName();
        String carrierCode = RegPrefManager.getInstance(this).getMobileOperatorCode();
        operator.setText(carrierName);

        operator_circle_name= RegPrefManager.getInstance(this).getMobileCircleName();
        String circleCode = RegPrefManager.getInstance(this).getMobileCircleCode();
        circle.setText(operator_circle_name);

        recharge_amount= RegPrefManager.getInstance(this).getMobileRechargeAmount();
        amount.setText(recharge_amount);
        RegPrefManager.getInstance(MobileRecharge.this).setMobileRechargeAmount(recharge_amount);

     /*   phone_number = RegPrefManager.getInstance(MobileRecharge.this).getPhoneNo();
      //  contact_number.setText(phone_number);
        Log.d("tagphone", phone_number);*/

        /*Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if(bundle != null) {

            operator_circle_name = bundle.getString("CIRCLE_NAME");
            circle_code = bundle.getString("CIRCLE_CODE");
            //   Log.d("CIRCLE_NAME", operator_circle_name);
            carrierName = bundle.getString("OPERATOR_NAME");
            operator_code = bundle.getString("OPERATOR_CODE");
//            number = bundle.getString("NUMBER");
            operator.setText(carrierName);
            circle.setText(operator_circle_name);
            //    contact_number.setText(number);

        }*/

        operator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("PHONE_NUMBER", phone_number=contact_number.getText().toString());
                editor.commit();*/
                RegPrefManager.getInstance(MobileRecharge.this).setPhoneNo(phone_number = contact_number.getText().toString());
                startActivity(new Intent(MobileRecharge.this,MobileOperators.class));
                //  Toast.makeText(MobileRecharge.this, phone_number, Toast.LENGTH_SHORT).show();
                finish();
            }
        });


        circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegPrefManager.getInstance(MobileRecharge.this).setPhoneNo(phone_number = contact_number.getText().toString());
                startActivity(new Intent(MobileRecharge.this, MobileOperatorCircle.class));
                finish();
            }
        });

        contact_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent, PICK_CONTACT);

            }
        });


       prepaid.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
             //  radioClickprepaid=true;
             //  radioClickpostpaid=false;
               btn_prepaid.setVisibility(View.VISIBLE);
               btn_postpaid.setVisibility(View.GONE);
               RegPrefManager.getInstance(MobileRecharge.this).setRadioClick("1");
               SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
               SharedPreferences.Editor editor = sharedPreferences.edit();
               editor.putBoolean("Prepaid", prepaid.isChecked());
               editor.apply();
           }
       });
        postpaid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  radioClickpostpaid=true;
               // radioClickprepaid=false;
                RegPrefManager.getInstance(MobileRecharge.this).setRadioClick("2");
                btn_prepaid.setVisibility(View.GONE);
                btn_postpaid.setVisibility(View.VISIBLE);
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("Postpaid", postpaid.isChecked());
                editor.apply();
            }
        });

        loadRadioButtons();

      //  contact_number.setText(number);

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
               /* else if (phone_number.length()>1){
                    new AsyngetOperator().execute();
                }*/
                else if (carrierName.length()< 1){
                    operator.setError("Enter Your Operator");
                }
                else if (recharge_amount.length() < 1){
                    amount.setError("Enter Your Amount");
                }
                else {
                    RegPrefManager.getInstance(MobileRecharge.this).setBackService("MobileRecharge");
                    RegPrefManager.getInstance(MobileRecharge.this).setServiceName("MobileRecharge");
                    Intent intent = new Intent(MobileRecharge.this,PaymentCartActivity.class);
                    intent.putExtra("PhoneNumber",phone_number);
                    intent.putExtra("CarrierName", carrierName);
                    intent.putExtra("CarrierCode",operator_code);
                    intent.putExtra("CircleName", operator_circle_name);
                    intent.putExtra("CircleCode",circle_code);
                    intent.putExtra("RechargeAmount", recharge_amount);
                    startActivity(intent);
                }
            }
        });


        btn_postpaid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phone_number = contact_number.getText().toString();
                carrierName = operator.getText().toString();
                operator_circle_name = circle.getText().toString();
                recharge_amount = amount.getText().toString();

                if (phone_number.length() < 1){
                    contact_number.setError("Enter Your Mobile Number");
                }
               /* else if (phone_number.length()>1){
                    new AsyngetOperator().execute();
                }*/
                else if (carrierName.length()< 1){
                    operator.setError("Enter Your Operator");
                }
                else if (recharge_amount.length() < 1){
                    amount.setError("Enter Your Amount");
                }
                else {
                    RegPrefManager.getInstance(MobileRecharge.this).setBackService("MobileRecharge");
                    RegPrefManager.getInstance(MobileRecharge.this).setServiceName("MobileRecharge");
                    Intent intent = new Intent(MobileRecharge.this,PaymentCartActivity.class);
                    intent.putExtra("PhoneNumber",phone_number);
                    intent.putExtra("CarrierName", carrierName);
                    intent.putExtra("CarrierCode",operator_code);
                    intent.putExtra("CircleName", operator_circle_name);
                    intent.putExtra("CircleCode",circle_code);
                    intent.putExtra("RechargeAmount", recharge_amount);
                    startActivity(intent);
                  //  new AsynSignInDetails().execute();
                }
            }
        });

        browseplansLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phone_number = contact_number.getText().toString();
                carrierName = operator.getText().toString();
                operator_circle_name = circle.getText().toString();

                if (phone_number.length() < 1){
                    contact_number.setError("Enter Your Mobile Number");
                }
                else if (carrierName.length()< 1){
                    operator.setError("Enter Your Operator");
                }
                else if (operator_circle_name.length()< 1){
                    circle.setError("Enter Your Circle");
                }
                else {
                    Intent intent = new Intent(MobileRecharge.this,MobileBrowsePlansActivity.class);
                    RegPrefManager.getInstance(MobileRecharge.this).setPhoneNo(phone_number);
                    intent.putExtra("OperatorName",carrierName );
                    intent.putExtra("CircleName",operator_circle_name);
                    startActivity(intent);
                }
            }
        });

        contact_number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
              if (contact_number.equals("")){

              }
              else if (!contact_number.equals("")&& contact_number.length()>=10){
                  if (isNetworkAvailable()) {
                      new AsyngetOperator().execute();//register add beneficiary
                  }
                  else {
                      noNetwrokErrorMessage();
                  }

                //  new AsyngetOperator().execute();
              }

            }
        });


    /*    if (phone_number.equals(contact_number.getText().toString())){

            final long period = 1000;
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    new AsyngetOperator().execute();
                }
            }, 1000, period);
        }
        else {

        }*/



        /*if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_CONTACTS)
                == PackageManager.PERMISSION_GRANTED) {
            getContacts();
        } else {
            requestLocationPermission();
        }
*/

        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

        SharedPreferences.Editor editor1 = sharedpreferences.edit();
        login_user=sharedpreferences.getString("FLAG", "");
        editor1.commit(); // commit changes


        Log.d("login_user", login_user);


      /*  Intent intent1 = getIntent();
        Bundle bundle1 = intent1.getExtras();

        if (bundle1!= null){
            carrierName = bundle1.getString("OPERATOR_NAME");
            operator.setText(carrierName);
          //  Log.d("OPERATOR_NAME", carrierName);
        }*/

       /* int Permission_All = 1;

        String[] Permissions = {
//                android.Manifest.permission.ACCESS_COARSE_LOCATION,
//                android.Manifest.permission.ACCESS_FINE_LOCATION,
//                Manifest.permission.CALL_PHONE,
                Manifest.permission.INTERNET,
                Manifest.permission.READ_CONTACTS,};
        if(!hasPermissions(this, Permissions)){
            ActivityCompat.requestPermissions(this, Permissions, Permission_All);
        }*/
    }


    /*public static boolean hasPermissions(Context context, String... permissions){

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N_MR1 && context!=null && permissions!=null){
            for(String permission: permissions){
                if(ActivityCompat.checkSelfPermission(context, permission)!= PackageManager.PERMISSION_GRANTED){
                    return  false;
                }
            }
        }
        return true;
    }*/


/*
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
*/



  /*  @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_READ_CONTACTS: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    getContacts();

                } else {

                    // permission denied,Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

        }
    }*/






/*
    @Override
    public void onBackPressed() {
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
        }     //   Toast.makeText(this,"back key is pressed", Toast.LENGTH_SHORT).show();

    }
*/

    public void loadRadioButtons(){

        if (postpaid.isSelected()){
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            prepaid.setChecked(sharedPreferences.getBoolean("Prepaid", false));
            postpaid.setChecked(sharedPreferences.getBoolean("Postpaid", false));
        }

    }

/*
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
                                Toast.makeText(MobileRecharge.this, "Number="+num, Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                    break;
                }
        }
    }
*/








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

    /*@Override
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
*/



/*    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        switch (reqCode) {
            case (PICK_CONTACT) :
                if (resultCode == Activity.RESULT_OK) {
                    Uri contactData = data.getData();
                    Cursor c =  managedQuery(contactData, null, null, null, null);
                    startManagingCursor(c);
                    if (c.moveToFirst()) {
                        String name = c.getString(c.getColumnIndexOrThrow(Contacts.People.NAME));
                        String number = c.getString(c.getColumnIndexOrThrow(Contacts.People.NUMBER));
                        Toast.makeText(this,  name + " has number " + number, Toast.LENGTH_LONG).show();
                        contact_number.setText(number);

                    }
                }
                break;
        }

    }*/


    private class AsyngetOperator extends AsyncTask<Void, Void, Void> {
        ProgressDialog pDialog;
        String success = null,data="";
        String error;
        boolean status= true;
        String message;

        @Override
        protected Void doInBackground(Void... params) {
            pDialog.show();
            ArrayList<NameValuePair> cred = new ArrayList<NameValuePair>();
          //  cred.add(new BasicNameValuePair("user_id",login_user));//user_email
            cred.add(new BasicNameValuePair("phone",phone_number=contact_number.getText().toString()));

            Log.v("RES","Sending data " + phone_number);

            String urlRouteList="https://genieservice.in/api/service/operatorFinder";
            //getOperatorCircle
            try {
                String route_response = CustomHttpClient.executeHttpPost(urlRouteList, cred);

                success = route_response;
                JSONObject jsonObject = new JSONObject(success);

                // user_email=jsonObject.getString("user_email");
                status =jsonObject.getBoolean("status");

                String data=jsonObject.getString("data");
                // Toast.makeText(Cart.this, sum_total, Toast.LENGTH_SHORT).show();

                JSONObject jsonObject1 = new JSONObject(data);
                operator_code=jsonObject1.getString("OperatorCode");
                circle_code=jsonObject1.getString("CircleCode");

          //      RegPrefManager.getInstance(MobileRecharge.this).setMobileOperator(operator_code,listItem.getOperator_code());

                Log.d("code", operator_code);
                Log.d("code1", circle_code);
              /*  message = jsonObject1.getString("Message");
                error = jsonObject1.getString("Errorcode");
*/

             /*   operator.setText(operator_name);
                circle.setText(circle_name);
*/

            } catch (Exception e)

            {
                Log.v("Connection error", e.toString());

            }return null;
        }
        protected void onPostExecute(Void result) {
            pDialog.dismiss();
            try {

            if(status==true)
            {
              //  Toast.makeText(getApplicationContext(),operator_code, Toast.LENGTH_LONG).show();

             //   Toast.makeText(getApplicationContext(),circle_code,Toast.LENGTH_LONG).show();
               // operator.setText(operator_code);
                new AsynPostOperator().execute();

               /* SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("AMOUNT", recharge_amount = amount.getText().toString());
                editor.commit();
                Toast.makeText(MobileRecharge.this, recharge_amount, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MobileRecharge.this,PaymentActivity.class));finish();*/
            }
            else{
                Toast.makeText(getApplicationContext(),"Please Select Manually", Toast.LENGTH_LONG).show();
               /* SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.remove("PHONE_NUMBER");
                editor.commit();*/
            }
            }catch (Exception e){
                Toast.makeText(getApplicationContext(), "Genie is away! Try after sometime", Toast.LENGTH_LONG).show();
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

    private class AsynPostOperator extends AsyncTask<Void, Void, Void> {
        ProgressDialog pDialog;
        String success = null,data="",status="";
        String error;
        String message;
        String opCode = RegPrefManager.getInstance(getApplicationContext()).getMobileOperatorCode();
        String circleCode = RegPrefManager.getInstance(getApplicationContext()).getMobileCircleCode();

        @Override
        protected Void doInBackground(Void... params) {
            pDialog.show();
            ArrayList<NameValuePair> cred = new ArrayList<NameValuePair>();
            //  cred.add(new BasicNameValuePair("user_id",login_user));//user_email
            cred.add(new BasicNameValuePair("operator_code",operator_code));
            cred.add(new BasicNameValuePair("circle_code",circle_code));


            Log.v("RES","Sending data " + operator_code+circle_code);

            String urlRouteList="https://genieservice.in/api/service/getOperatorCircle";
            //getOperatorCircle
            try {
                String route_response = CustomHttpClient.executeHttpPost(urlRouteList, cred);

                success = route_response;
                JSONObject jsonObject = new JSONObject(success);

                // user_email=jsonObject.getString("user_email");
                status=jsonObject.getString("status");
                if(status.equals("false")) {
                    message = jsonObject.getString("Status");

                }else {
                    carrierName = jsonObject.getString("operator_name");
                    operator_circle_name = jsonObject.getString("circle_name");
                    operator.setText(carrierName);

                }


            } catch (Exception e)

            {
                Log.v("Connection error", e.toString());

            }return null;
        }
        protected void onPostExecute(Void result) {
            pDialog.dismiss();
            try {

                if (status.equals("true")) {
                    //  Toast.makeText(getApplicationContext(),carrierName, Toast.LENGTH_LONG).show();
                    circle.setText(operator_circle_name);

                    if(!operator_code.isEmpty()) {
                        RegPrefManager.getInstance(MobileRecharge.this).setMobileOperator(carrierName, operator_code);
                    }

                    if(!circle_code.isEmpty()){
                        RegPrefManager.getInstance(MobileRecharge.this).setMobileCircle(operator_circle_name,circle_code);
                    }
                    //    Toast.makeText(MobileRecharge.this, operator_circle_name, Toast.LENGTH_SHORT).show();

               /* SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("AMOUNT", recharge_amount = amount.getText().toString());
                editor.commit();
                Toast.makeText(MobileRecharge.this, recharge_amount, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MobileRecharge.this,PaymentActivity.class));finish();*/
                } else {
                    Toast.makeText(getApplicationContext(), "Please Select Manually", Toast.LENGTH_LONG).show();
               /* SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.remove("PHONE_NUMBER");
                editor.commit();*/
                }
            }catch (Exception e){
                Toast.makeText(getApplicationContext(), "Genie is away! Try after sometime", Toast.LENGTH_LONG).show();
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



    private void AccessContact()
    {
        List<String> permissionsNeeded = new ArrayList<String>();
        final List<String> permissionsList = new ArrayList<String>();
        if (!addPermission(permissionsList, Manifest.permission.READ_CONTACTS))
            permissionsNeeded.add("Read Contacts");
        if (!addPermission(permissionsList, Manifest.permission.WRITE_CONTACTS))
            permissionsNeeded.add("Write Contacts");
        if (permissionsList.size() > 0) {
            if (permissionsNeeded.size() > 0) {
                String message = "You need to grant access to " + permissionsNeeded.get(0);
                for (int i = 1; i < permissionsNeeded.size(); i++)
                    message = message + ", " + permissionsNeeded.get(i);
                    showMessageOKCancel(message,
                        new DialogInterface.OnClickListener() {
                            @SuppressLint("NewApi")
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                                        REQUEST_MULTIPLE_PERMISSIONS);
                            }
                        });
                return;
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                        REQUEST_MULTIPLE_PERMISSIONS);
            }
            return;
        }
    }



    @SuppressLint("NewApi")
    private boolean addPermission(List<String> permissionsList, String permission) {
        if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(permission);

            if (!shouldShowRequestPermissionRationale(permission))
                return false;
        }
        return true;
    }


    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(MobileRecharge.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }



    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        switch (reqCode) {
            case (PICK_CONTACT):
                if (resultCode == Activity.RESULT_OK) {
                    Uri contactData = data.getData();
                    Cursor c = managedQuery(contactData, null, null, null, null);
                    if (c.moveToFirst()) {
                        String id = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));
                        String hasPhone = c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                        try {
                            if (hasPhone.equalsIgnoreCase("1")) {
                                Cursor phones = getContentResolver().query(
                                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id,
                                        null, null);
                                phones.moveToFirst();
                                String cNumber = phones.getString(phones.getColumnIndex("data1"));
                                System.out.println("number is:" + cNumber);
                                Toast.makeText(this, cNumber, Toast.LENGTH_SHORT).show();
                                contact_number.setText("Phone Number is: "+cNumber);
                            }
                            String name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                           // txtname.setText("Name is: "+name);
                        }
                        catch (Exception ex)
                        {
                            ex.getMessage();
                        }
                    }
                }
                break;
        }
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





/*  private void requestStoragePermission() {
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED)
                || (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CONTACTS) == PackageManager.PERMISSION_GRANTED)
                || (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_NUMBERS) == PackageManager.PERMISSION_GRANTED))
            return;

        if ((ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_CONTACTS)) ||

                (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_CONTACTS)) ||
                (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_PHONE_NUMBERS)) ) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS, Manifest.permission.READ_PHONE_NUMBERS}, STORAGE_PERMISSION_CODE);
    }


    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == STORAGE_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
                Toast.makeText(this, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }
*/

/*>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/


/* public void readcontact(){
        try {
            Intent intent = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts/people"));
            startActivityForResult(intent, PICK_CONTACT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/




/*    public void getContacts() {

        String phoneNumber = null;
        String email = null;

        Uri CONTENT_URI = ContactsContract.Contacts.CONTENT_URI;
        String _ID = ContactsContract.Contacts._ID;
        String DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME;
        String HAS_PHONE_NUMBER = ContactsContract.Contacts.HAS_PHONE_NUMBER;

        Uri PhoneCONTENT_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String Phone_CONTACT_ID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID;
        String NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER;

        Uri EmailCONTENT_URI =  ContactsContract.CommonDataKinds.Email.CONTENT_URI;
        String EmailCONTACT_ID = ContactsContract.CommonDataKinds.Email.CONTACT_ID;
        String DATA = ContactsContract.CommonDataKinds.Email.DATA;

        StringBuffer output = new StringBuffer();

        ContentResolver contentResolver = getContentResolver();

        Cursor cursor = contentResolver.query(CONTENT_URI, null,null, null, null);

        // Loop for every contact in the phone
        if (cursor.getCount() > 0) {

            while (cursor.moveToNext()) {

                String contact_id = cursor.getString(cursor.getColumnIndex( _ID ));
                String name = cursor.getString(cursor.getColumnIndex( DISPLAY_NAME ));

                int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex( HAS_PHONE_NUMBER )));

                if (hasPhoneNumber > 0) {

                    output.append("\n First Name:" + name);

                    // Query and loop for every phone number of the contact

                    Cursor phoneCursor = contentResolver.query(PhoneCONTENT_URI, null, Phone_CONTACT_ID + " = ?", new String[] { contact_id }, null);

                    while (phoneCursor.moveToNext()) {
                        phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(NUMBER));
                        output.append("\n Phone number:" + phoneNumber);

                    }

                    phoneCursor.close();

                    // Query and loop for every email of the contact
                    Cursor emailCursor = contentResolver.query(EmailCONTENT_URI,    null, EmailCONTACT_ID+ " = ?", new String[] { contact_id }, null);

                    while (emailCursor.moveToNext()) {

                        email = emailCursor.getString(emailCursor.getColumnIndex(DATA));

                        output.append("\nEmail:" + email);

                    }

                    emailCursor.close();
                }

                output.append("\n");
            }

            contact_number.setText(output.toString());
        }
    }
*/