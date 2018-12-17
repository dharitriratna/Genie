package com.example.user.genie;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.genie.helper.RegPrefManager;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;

public class RetailerSignupActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView addImg,frontImg,backImg;
    ImageView shop_photo,front_photo,back_photo,eye;
    private static final int PICK_PHOTO = 1958;
    private static final int PICK_PHOTOfront = 1958;
    private static final int PICK_PHOTOback = 1958;
    private final int requestCode = 20;
    private final int requestCodefront = 20;
    private final int requestCodeback = 20;
    private String imagefilePath="";
    private String imagefilePathfront="";
    private String imagefilePathback="";
    private File file,file1,file2;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    String login_user="";
    EditText password;
    EditText ownername;
    EditText phone_no;
    EditText emailId;
    EditText businessname;
    EditText retailsubtype;
    EditText panid;
    EditText user_address;
    EditText user_city;
    EditText user_pin;
    EditText user_state;
    TextView user_country;
    Spinner userAdproofpinner;

    String ownerName,phoneNumber,EmailId,Password,userAddress,userLane,userCity,userPin,userState,userCountry;
    String retailerBusinessName,retailerSubType;
    String userAddressProof;
    Button btnSubmit;
    FrameLayout frontframe,backframe,userframe;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retailer_sign_up);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RetailerSignupActivity.this,RetailersListActivity.class));
                finish();
            }
        });
        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedpreferences.edit();
        login_user=sharedpreferences.getString("FLAG", "");
        editor.commit(); // commit changes

        Log.d("login_user", login_user);
        ownername=findViewById(R.id.ownername);
        phone_no=findViewById(R.id.phone_no);
        password = findViewById(R.id.password);
        emailId=findViewById(R.id.emailId);
        user_address=findViewById(R.id.user_address);
        user_city=findViewById(R.id.user_city);
        user_pin=findViewById(R.id.user_pin);
        user_state=findViewById(R.id.user_state);
        user_country=findViewById(R.id.user_country);
        businessname=findViewById(R.id.businessname);
        retailsubtype=findViewById(R.id.retailsubtype);
        btnSubmit=findViewById(R.id.btnSubmit);
        eye=findViewById(R.id.eye);
        userAdproofpinner=findViewById(R.id.userAdproofpinner);
        addImg = findViewById(R.id.addImg);
        frontImg=findViewById(R.id.frontImg);
        backImg=findViewById(R.id.backImg);
        front_photo=findViewById(R.id.front_photo);
        back_photo=findViewById(R.id.back_photo);
        shop_photo = findViewById(R.id.candidate_photo);
        frontframe=findViewById(R.id.frontframe);
        backframe=findViewById(R.id.backframe);
        userframe=findViewById(R.id.userframe);


        eye.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {

                switch ( event.getAction() ) {

                    case MotionEvent.ACTION_UP:
                        password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        break;

                    case MotionEvent.ACTION_DOWN:
                        password.setInputType(InputType.TYPE_CLASS_TEXT);
                        break;
                }
                return true;
            }
        });

        userAdproofpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                userAddressProof= userAdproofpinner.getItemAtPosition(userAdproofpinner.getSelectedItemPosition()).toString();
                 Toast.makeText(getApplicationContext(),"Add Images Below",Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });



      /*  addImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final CharSequence[] options_array = {"Camera", "Gallery"};

                AlertDialog.Builder builder = new AlertDialog.Builder(RetailerSignupActivity.this);
                builder.setTitle("Choose");
                builder.setItems(options_array, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (options_array[item].equals("Camera")) {
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(intent, 0);
                        } else if (options_array[item].equals("Gallery")) {
                            Intent intent = new Intent(Intent.ACTION_PICK,
                                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(intent, PICK_PHOTO);
                        }
                    }
                });
                builder.show();
            }
        });
*/
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ownerName = ownername.getText().toString().trim();
                phoneNumber=phone_no.getText().toString().trim();
                EmailId=emailId.getText().toString().trim();
                Password=password.getText().toString().trim();
                retailerBusinessName=businessname.getText().toString().trim();
                retailerSubType=retailsubtype.getText().toString().trim();
                userAddress=user_address.getText().toString().trim();
                userCity=user_city.getText().toString().trim();
                userPin=user_pin.getText().toString().trim();
                userState=user_state.getText().toString().trim();
                userCountry=user_country.getText().toString().trim();

                if (ownerName.length() < 1){
                    ownername.setError("Please Enter Owner Name");
                }
                else if (phoneNumber.length() < 1){
                    phone_no.setError("Please Enter Phone no");
                }
                else if (EmailId.length() < 1){
                    emailId.setError("Please Enter Email");
                }
                else if (Password.length() < 1){
                    password.setError("Please Enter Password");
                }
                else if (retailerBusinessName.length() < 1){
                    businessname.setError("Please Enter Business Name");
                }
                else if (retailerSubType.length() < 1){
                    retailsubtype.setError("Please Enter Retail Subtype");
                }
                else if (userAddress.length() < 1){
                    user_address.setError("Please Enter Your Address");
                }
                else if (userCity.length() < 1){
                    user_city.setError("Please Enter Your City");
                }
                else if (userPin.length() < 1){
                    user_pin.setError("Please Enter Your Pin");
                }
                else if (userState.length() < 1){
                    user_state.setError("Please Enter Your State");
                }
                else if (userCountry.length() < 1){
                    user_country.setError("Please Enter Your Country");
                }
                else {
                    new AsyncRegister().execute();
                }
            }
        });

      /*  frontImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final CharSequence[] options_array = {"Camera", "Gallery"};

                AlertDialog.Builder builder = new AlertDialog.Builder(RetailerSignupActivity.this);
                builder.setTitle("Choose");
                builder.setItems(options_array, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (options_array[item].equals("Camera")) {
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(intent, 1);
                        } else if (options_array[item].equals("Gallery")) {
                            Intent intent = new Intent(Intent.ACTION_PICK,
                                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(intent, PICK_PHOTOfront);
                        }
                    }
                });
                builder.show();
            }
        });*/
/*

        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final CharSequence[] options_array = {"Camera", "Gallery"};

                AlertDialog.Builder builder = new AlertDialog.Builder(RetailerSignupActivity.this);
                builder.setTitle("Choose");
                builder.setItems(options_array, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (options_array[item].equals("Camera")) {
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(intent, 2);
                        } else if (options_array[item].equals("Gallery")) {
                            Intent intent = new Intent(Intent.ACTION_PICK,
                                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(intent, PICK_PHOTOback);
                        }
                    }
                });
                builder.show();
            }
        });
*/
          userframe.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  Intent i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                  startActivityForResult(i, 1);
              }
          });

          frontframe.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  Intent i1 = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                  startActivityForResult(i1, 2);
              }
          });

          backframe.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  Intent i2 = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                  startActivityForResult(i2,3);
              }
          });


        int Permission_All = 1;

        String[] Permissions = {
//                android.Manifest.permission.ACCESS_COARSE_LOCATION,
//                android.Manifest.permission.ACCESS_FINE_LOCATION,
//                Manifest.permission.CALL_PHONE,
                android.Manifest.permission.INTERNET,
                android.Manifest.permission.ACCESS_WIFI_STATE,
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.CAMERA, };
        if(!hasPermissions(getApplicationContext(), Permissions)){
            ActivityCompat.requestPermissions(RetailerSignupActivity.this, Permissions, Permission_All);
        }
    }


    public boolean hasPermissions(Context context, String... permissions){

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N && context!=null && permissions!=null){
            for(String permission: permissions){
                if(ActivityCompat.checkSelfPermission(context, permission)!= PackageManager.PERMISSION_GRANTED){
                    return  false;
                }
            }
        }
        return true;
    }

 /*   public void onClick(View v) { // TODO Auto-generated method stub

        switch (v.getId()) {

            case R.id.addImg:
                Intent i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(i, 1);

                break;
            case R.id.frontImg:
                Intent i1 = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(i1, 2);
        }
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){
            case 1:
                if(resultCode == RESULT_OK){
                    Bundle extras = data.getExtras();
                    Bitmap bmp = (Bitmap) extras.get("data");
                    shop_photo.setImageBitmap(bmp);
                    addImg.setVisibility(View.GONE);
                    imagefilePath=String.valueOf(getImageUri(this,bmp));
                }
                break;
            case 2:
                if(resultCode == RESULT_OK) {
                    Bundle extras = data.getExtras();
                    Bitmap bmp1 = (Bitmap) extras.get("data");
                    front_photo.setImageBitmap(bmp1);
                    frontImg.setVisibility(View.GONE);
                    imagefilePathfront=String.valueOf(getImageUri(this,bmp1));
                    Log.d("image", imagefilePathfront);
                }
                break;
            case 3:
                if (resultCode ==RESULT_OK){
                    Bundle extras = data.getExtras();
                    Bitmap bmp2 = (Bitmap)extras.get("data");
                    back_photo.setImageBitmap(bmp2);
                    backImg.setVisibility(View.GONE);
                    imagefilePathback= String.valueOf(getImageUri(this,bmp2));
                    Log.d("backimage", imagefilePathback);
                }
        }
    }

/*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 0:
                super.onActivityResult(requestCode, resultCode, data);
                if (resultCode == RESULT_OK && requestCode == PICK_PHOTO) {

                    Uri imageUri = data.getData();
                    imagefilePath = getPath(imageUri);
                    shop_photo.setImageURI(imageUri);
                    addImg.setVisibility(View.GONE);
                    //  btn_submit.setVisibility(View.VISIBLE);
                    file=new File(imagefilePath);

          */
/*  SharedPreferences.Editor editor3 = sharedpreferences.edit();
            editor3.putString("IMAGE", imagefilePath );
            //   Log.d("user_id", user_id);
            editor3.commit();*//*

                    //getting image filepath
                }
                else if(this.requestCode == requestCode && resultCode == RESULT_OK)
                {
                    Bitmap bitmap = (Bitmap)data.getExtras().get("data");
                    shop_photo.setImageBitmap(bitmap);
                    // btn_submit.setVisibility(View.VISIBLE);
                    addImg.setVisibility(View.GONE);
                    Uri cameraUri= getImageUri(getApplicationContext(),bitmap);
                    imagefilePath = getPath(cameraUri);
                    Log.d("imagefilePath",imagefilePath);
                    file=new File(imagefilePath);  // getting image captured filepath  < ----------------------------------------
                }
                break;

            case 1:
                super.onActivityResult(requestCodefront, resultCode, data);
                if (resultCode == RESULT_OK && requestCodefront == PICK_PHOTOfront) {

                    Uri imageUri = data.getData();
                    imagefilePathfront = getPath(imageUri);
                    front_photo.setImageURI(imageUri);
                    frontImg.setVisibility(View.GONE);
                    //   btn_submit.setVisibility(View.VISIBLE);
                    file1=new File(imagefilePathfront);


                    //getting image filepath
                }
                else if(this.requestCodefront == requestCodefront && resultCode == RESULT_OK)
                {
                    Bitmap bitmap = (Bitmap)data.getExtras().get("data");
                    front_photo.setImageBitmap(bitmap);
                    //  btn_submit.setVisibility(View.VISIBLE);
                    frontImg.setVisibility(View.GONE);
                    Uri cameraUri= getImageUri(this,bitmap);
                    imagefilePathfront = getPath(cameraUri);
                    Log.d("imagefilePath",imagefilePathfront);
                    file1=new File(imagefilePathfront);  // getting image captured filepath  < ----------------------------------------
                }
                break;

            case 3:

                super.onActivityResult(requestCodeback, resultCode, data);
                if (resultCode == RESULT_OK && requestCodeback == PICK_PHOTOback) {

                    Uri imageUri = data.getData();
                    imagefilePathback = getPath(imageUri);
                    back_photo.setImageURI(imageUri);
                    backImg.setVisibility(View.GONE);
                    //   btn_submit.setVisibility(View.VISIBLE);
                    file2=new File(imagefilePathback);


                    //getting image filepath
                }
                else if(this.requestCodeback == requestCodeback && resultCode == RESULT_OK)
                {
                    Bitmap bitmap = (Bitmap)data.getExtras().get("data");
                    back_photo.setImageBitmap(bitmap);
                    //  btn_submit.setVisibility(View.VISIBLE);
                    backImg.setVisibility(View.GONE);
                    Uri cameraUri= getImageUri(this,bitmap);
                    imagefilePathback = getPath(cameraUri);
                    Log.d("imagefilePath",imagefilePathback);
                    file2=new File(imagefilePathback);  // getting image captured filepath  < ----------------------------------------
                }
                break;
        }
    }
*/

    private String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };//,Video,Audio
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);//Video
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    @SuppressLint("StaticFieldLeak")
    private class AsyncRegister extends AsyncTask<Void, Void, Void> {
        ProgressDialog pDialog;
        String success = null,message="",status="true";

        @Override
        protected Void doInBackground(Void... params) {
            pDialog.show();
            ArrayList<NameValuePair> cred = new ArrayList<NameValuePair>();
            cred.add(new BasicNameValuePair("first_name",ownerName));
            cred.add(new BasicNameValuePair("email",EmailId));//user_email
            cred.add(new BasicNameValuePair("phone",phoneNumber ));
            cred.add(new BasicNameValuePair("password",Password ));
            cred.add(new BasicNameValuePair("fse_user_id",login_user ));
            cred.add(new BasicNameValuePair("business_name",retailerBusinessName ));
            cred.add(new BasicNameValuePair("retail_sub_type",retailerSubType ));
            cred.add(new BasicNameValuePair("icon",imagefilePathfront ));
            cred.add(new BasicNameValuePair("icon1",imagefilePathback ));
            cred.add(new BasicNameValuePair("icon2",imagefilePath ));
            cred.add(new BasicNameValuePair("address_proof",userAddressProof ));
            cred.add(new BasicNameValuePair("line1",userAddress ));
            cred.add(new BasicNameValuePair("city",userCity ));
            cred.add(new BasicNameValuePair("pin",userPin ));
            cred.add(new BasicNameValuePair("state",userState ));
            cred.add(new BasicNameValuePair("country",userCountry ));
            Log.v("RES","Sending data " +ownerName+ EmailId+ phoneNumber +Password+login_user+retailerBusinessName+retailerSubType
                    +imagefilePathfront+imagefilePathback+imagefilePath+userAddressProof+userAddress+userCity+userPin+userState+userCountry);

            String urlRouteList="http://demo.ratnatechnology.co.in/genie/api/user/registerRetailer";
            try {
                String route_response = CustomHttpClient.executeHttpPost(urlRouteList, cred);

                success = route_response;
                JSONObject jsonObject = new JSONObject(success);

                // user_email=jsonObject.getString("user_email");
                status=jsonObject.getString("status");
                if(status.equals("false")) {
                    message = jsonObject.getString("errors");
                }
             /*   String data=jsonObject.getString("data");
                // Toast.makeText(Cart.this, sum_total, Toast.LENGTH_SHORT).show();
                JSONObject jsonObject1 = new JSONObject(data);*/
                String user_id=jsonObject.getString("user_id");
                Toast.makeText(RetailerSignupActivity.this, user_id, Toast.LENGTH_SHORT).show();
              /*  SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("FLAG", user_id);
                Log.d("user_id", user_id);
                editor.commit();*/
                String user_phone = jsonObject.getString("phone");
                RegPrefManager.getInstance(RetailerSignupActivity.this).setPhoneNo(user_phone);
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
                Toast.makeText(getApplicationContext(),"Registration Successful", Toast.LENGTH_LONG).show();
                startActivity(new Intent(RetailerSignupActivity.this,RetailersListActivity.class));finish();
            }
            else{
                Toast.makeText(getApplicationContext(),"Validation fails! phone number and email should be unique",
                        Toast.LENGTH_LONG).show();
                emailId.setError("Please enter a valid email");
                phone_no.setError("Please enter a valid no.");
            }
        }

        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(RetailerSignupActivity.this);
            pDialog.setMessage("Loading In...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
    }


}
