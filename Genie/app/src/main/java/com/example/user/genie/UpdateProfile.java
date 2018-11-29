package com.example.user.genie;

import android.app.Activity;
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
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.user.genie.helper.RegPrefManager;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;

public class UpdateProfile extends Activity {
    Toolbar toolbar;
    EditText full_name, email_id, phone_no, address, password;
    String fullName, email, mobile_no, userAddress, userPassword;
    Button btn_update;
    int i =0;
    ProgressDialog progressDialog;
    String login_user = "";
    String user_id;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    File file;
    private static final int PICK_PHOTO = 1958;
    private final int requestCode = 20;
  //  FrameLayout set_image;
    private String imagefilePath="";
    ImageView selected_image,set_image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        progressDialog = new ProgressDialog(this);
        full_name = findViewById(R.id.full_name);
        email_id = findViewById(R.id.email_id);
        phone_no = findViewById(R.id.phone_no);
      /*  address = findViewById(R.id.address);
        password = findViewById(R.id.password);*/
        btn_update = findViewById(R.id.btn_update);
        set_image = findViewById(R.id.set_image);
        //selected_image = findViewById(R.id.selected_image);
        TextView address = findViewById(R.id.address);
        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UpdateProfile.this,Adress.class));
            }
        });



        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fullName = full_name.getText().toString().trim();
                email = email_id.getText().toString().trim();
                mobile_no = phone_no.getText().toString().trim();
               /* userAddress = address.getText().toString().trim();
                userPassword = password.getText().toString().trim();*/

                if (fullName.length()<1){
                    full_name.setError("Enter Your Name");
                }
                else if (email.length()<1){
                    email_id.setError("Enter Your Email");
                }
                else if (mobile_no.length()<1){
                    phone_no.setError("Enter Your Phone Number");
                }
               /* else if (userAddress.length()<1){
                    address.setError("Enter Your Address");
                }
                else if (userPassword.length()<1){
                    password.setError("Enter Your Password");
                }*/
                else {
                   new AsynUpdateDetails().execute();
                   // Toast.makeText(UpdateProfile.this, "Profile Updated", Toast.LENGTH_SHORT).show();
                    //startActivity(new Intent(UpdateProfile.this,MainActivity.class));
                }
            }
        });


        set_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] options_array = {"Camera", "Gallery"};

                AlertDialog.Builder builder = new AlertDialog.Builder(UpdateProfile.this);
                builder.setTitle("Choose");
                builder.setItems(options_array, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (options_array[item].equals("Camera")) {
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(intent, requestCode);
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
        if(!hasPermissions(this, Permissions)){
            ActivityCompat.requestPermissions(this, Permissions, Permission_All);
        }

        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedpreferences.edit();
        login_user=sharedpreferences.getString("FLAG", "");
        editor.commit(); // commit changes

        Log.d("login_user", login_user);

        getProfile();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_PHOTO) {

            Uri imageUri = data.getData();
            imagefilePath = getPath(imageUri);

            set_image.setImageURI(imageUri);
          //  add_img1.setVisibility(View.GONE);
          //  btn_submit.setVisibility(View.VISIBLE);
            file=new File(imagefilePath);

          /*  SharedPreferences.Editor editor3 = sharedpreferences.edit();
            editor3.putString("IMAGE", imagefilePath );
            //   Log.d("user_id", user_id);
            editor3.commit();*/
            //getting image filepath
        }
        else if(this.requestCode == requestCode && resultCode == RESULT_OK)
        {
            Bitmap bitmap = (Bitmap)data.getExtras().get("data");
            set_image.setImageBitmap(bitmap);
          //  btn_submit.setVisibility(View.VISIBLE);
          //  add_img1.setVisibility(View.GONE);
            Uri cameraUri= getImageUri(this,bitmap);
            imagefilePath = getPath(cameraUri);
            Log.d("imagefilePath",imagefilePath);
            file=new File(imagefilePath);  // getting image captured filepath  < ----------------------------------------
        }
    }

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

    private void getProfile() {
        progressDialog.setMessage("loading...");
        progressDialog.show();
        StringRequest stringRequest=new
                StringRequest(Request.Method.GET, "http://demo.ratnatechnology.co.in/genie/index.php/api/user/getprofile?user_id="+login_user,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                     //   Toast.makeText(UpdateProfile.this, response, Toast.LENGTH_SHORT).show();
                        try {
                        // Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                            Log.d("onResponse:", response);
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray array = jsonObject.getJSONArray("data");

                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object = array.getJSONObject(i);
                                user_id=object.getString("id");
                                String name = object.getString("first_name");
                                String mobile=object.getString("phone");
                                String email_address=object.getString("email");

                              /*  String profile_image = object.getString("profile_image");

                                Picasso.with(getApplicationContext())
                                        .load(profile_image)
                                        .placeholder(R.drawable.round_background)   // optional
                                        .error(R.mipmap.ic_launcher)
                                        .into(imageView);*//*

*//*
                                Picasso.with(getApplicationContext())
                                        .load("http://demo.ratnatechnology.co.in/dogai/uploads/IMG_20180612_150603.jpg")
                                        .placeholder(R.drawable.round_background)   // optional
                                        .error(R.mipmap.ic_launcher)
                                        .into(view_img);
*/

                                full_name.setText(name);
                                phone_no.setText(mobile);
                                email_id.setText(email_address);

                                RegPrefManager.getInstance(UpdateProfile.this).setPhoneNo(mobile);
                                RegPrefManager.getInstance(UpdateProfile.this).setUserName(name);
                                RegPrefManager.getInstance(UpdateProfile.this).setUserEmail(email_address);



//                                userid.setText(user_id);

                               /* if (spin_gender.equals(gender)){
                                    spin_gender.getSelectedItem();
                                }
                                if (spin_country.equals(country)){
                                    spin_country.getSelectedItem();
                                }*/
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.getMessage() == null) {
                    if (i < 3) {
                        Log.e("Retry due to error ", "for time : " + i);
                        i++;
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(UpdateProfile.this, "Check your network connection.",
                                Toast.LENGTH_LONG).show();
                    }
                } else
                    Toast.makeText(UpdateProfile.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue requestQueue= Volley.newRequestQueue(UpdateProfile.this);
        requestQueue.add(stringRequest);
    }

    private class AsynUpdateDetails extends AsyncTask<Void, Void, Void> {
        ProgressDialog pDialog;
        String success = null;
        String status="true";
        String message;

        @Override
        protected Void doInBackground(Void... params) {
            ArrayList<NameValuePair> cred = new ArrayList<NameValuePair>();

            cred.add(new BasicNameValuePair("user_id",login_user));
            cred.add(new BasicNameValuePair("phone",mobile_no ));
            cred.add(new BasicNameValuePair("email",email ));
            cred.add(new BasicNameValuePair("first_name",fullName ));
           /* Log.v("RES","Sending data " +user_full_name+user_email_id+user_mobile_no+user_full_address+ user_pincode
                    +user_password );
*/
            String urlRouteList="http://demo.ratnatechnology.co.in/genie/index.php/api/user/updateprofile";
            try {
                String route_response = CustomHttpClient.executeHttpPost(urlRouteList, cred);
                Log.v(" ", "Response is " + route_response);
                success = route_response;
                JSONObject jsonObject = new JSONObject(success);
                 //status =jsonObject.getString("status");
                message = jsonObject.getString("message");

                String user_phone=jsonObject.getJSONObject("data").getString("phone");
                String user_name=jsonObject.getJSONObject("data").getString("first_name");
                String user_email=jsonObject.getJSONObject("data").getString("email");

                RegPrefManager.getInstance(UpdateProfile.this).setPhoneNo(user_phone);
                RegPrefManager.getInstance(UpdateProfile.this).setUserName(user_name);
                RegPrefManager.getInstance(UpdateProfile.this).setUserEmail(user_email);

            } catch (Exception e)

            {
                Log.v("ONMESSAGE", e.toString());
            }return null;
        }

        protected void onPostExecute(Void result) {
            pDialog.dismiss();

            // Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
            if (status.equals("true"))
            {
                Toast.makeText(UpdateProfile.this, "Profile Updated", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(UpdateProfile.this,MainActivity.class));
                finish();
            }
            else
            {
                Toast.makeText(UpdateProfile.this, "Validation Fails!Please give unique phone number or email id", Toast.LENGTH_SHORT).show();
//                Toast.makeText(Signup.this, "", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(UpdateProfile.this);
            pDialog.setMessage("Updating...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }
    }




}
