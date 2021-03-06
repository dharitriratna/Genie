package ratna.genie1.user.genie;

import android.Manifest;
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
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.androidnetworking.interfaces.UploadProgressListener;
import com.squareup.picasso.Picasso;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import ratna.genie1.user.genie.client.ApiClientGenie;
import ratna.genie1.user.genie.client.ApiInterface;
import ratna.genie1.user.genie.helper.RegPrefManager;

public class UpdateDemoFSE extends AppCompatActivity {
    Toolbar toolbar;
    TextView addImg, frontImg, backImg;
    ImageView candidate_photo, front_photo, back_photo, eye;
    private static final int PICK_PHOTO = 1958;
    private static final int PICK_PHOTOfront = 1958;
    private static final int PICK_PHOTOback = 1958;
    private final int requestCode = 20;
    private final int requestCodefront = 20;
    private final int requestCodeback = 20;
    private String imagefilePath = "";
    private String imagefilePathfront = "";
    private String imagefilePathback = "";
    private File file, file1, file2;
    private RadioGroup experienceRB, workcultureRB;
    String experience_rb;
    String workculture_rb;

    EditText phone_no;
    EditText candidatefsename;
    EditText email;
    EditText user_address;
    EditText user_landmark;
    EditText user_city;
    EditText user_pin;
    EditText user_state;
    TextView user_country;
    Spinner userAdproofpinner;
    ProgressDialog progressDialog;
    int i = 0;

    String userName, phoneNumber, EmailId, userAddress, userLane, userCity, userPin, userState, userCountry, UserFilePath, FrontFilePath, BackFilePath;
    String userAddressProof;
    Button btnSubmit;

    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    String login_user = "";
    FrameLayout frontframe, backframe, userframe;
    private static final int STORAGE_PERMISSION_CODE = 123;
    Uri imageUri, imageUri1, imageUri2, imageUri3;
    Boolean userImage, frontImage, backImage;
    String groupId;
    ApiInterface apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_fseprofile);
               /* OkHttpClient okHttpClient = new OkHttpClient() .newBuilder()
                .addNetworkInterceptor(new StethoInterceptor())
                .build();*/
        AndroidNetworking.initialize(getApplicationContext());
        apiService =
                ApiClientGenie.getClient().create(ApiInterface.class);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        progressDialog = new ProgressDialog(UpdateDemoFSE.this);

        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedpreferences.edit();
        login_user = sharedpreferences.getString("FLAG", "");
        editor.commit(); // commit changes

        Log.d("login_user", login_user);

        getProfileDetails();

        groupId = RegPrefManager.getInstance(UpdateDemoFSE.this).getUserGroup();

        experienceRB = findViewById(R.id.experienceRB);
        workcultureRB = findViewById(R.id.workcultureRB);
        phone_no = findViewById(R.id.phone_no);
        candidatefsename = findViewById(R.id.candidatefsename);
        email = findViewById(R.id.email);
        user_address = findViewById(R.id.user_address);
        user_landmark = findViewById(R.id.user_landmark);
        user_city = findViewById(R.id.user_city);
        user_pin = findViewById(R.id.user_pin);
        user_state = findViewById(R.id.user_state);
        user_country = findViewById(R.id.user_country);
        userAdproofpinner = findViewById(R.id.userAdproofpinner);
        btnSubmit = findViewById(R.id.btnSubmit);
        addImg = findViewById(R.id.addImg);
        frontImg = findViewById(R.id.frontImg);
        backImg = findViewById(R.id.backImg);
        front_photo = findViewById(R.id.front_photo);
        back_photo = findViewById(R.id.back_photo);
        candidate_photo = findViewById(R.id.candidate_photo);
        frontframe = findViewById(R.id.frontframe);
        backframe = findViewById(R.id.backframe);
        userframe = findViewById(R.id.userframe);

        userAdproofpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                userAddressProof = userAdproofpinner.getItemAtPosition(userAdproofpinner.getSelectedItemPosition()).toString();
                //  Toast.makeText(getApplicationContext(),"Add Images",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });

        experienceRB.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    experience_rb = rb.getText().toString();
                    Toast.makeText(UpdateDemoFSE.this, experience_rb, Toast.LENGTH_SHORT).show();
                }
            }
        });

        workcultureRB.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    workculture_rb = rb.getText().toString();
                    Toast.makeText(UpdateDemoFSE.this, workculture_rb, Toast.LENGTH_SHORT).show();
                }
            }
        });


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneNumber = phone_no.getText().toString().trim();
                userName = candidatefsename.getText().toString().trim();
                EmailId = email.getText().toString().trim();
                userAddress = user_address.getText().toString().trim();
                //  userLane=user_landmark.getText().toString().trim();
                userCity = user_city.getText().toString().trim();
                userPin = user_pin.getText().toString().trim();
                userState = user_state.getText().toString().trim();
                userCountry = user_country.getText().toString().trim();

              /*  if (userName.length() < 1) {
                    candidatefsename.setError("Please Enter Your Name");
                } else if (phoneNumber.length() < 1) {
                    phone_no.setError("Please Enter Your Phone No.");
                } else if (EmailId.length() < 1) {
                    email.setError("Please Enter Your Email");
                } else if (userAddress.length() < 1) {
                    user_address.setError("Please Enter Your Address");
                } else if (userCity.length() < 1) {
                    user_city.setError("Please Enter Your City");
                } else if (userPin.length() < 1) {
                    user_pin.setError("Please Enter Your Pin");
                } else if (userState.length() < 1) {
                    user_state.setError("Please Enter Your State");
                } else if (userCountry.length() < 1) {
                    user_country.setError("Please Enter Your Country");
                } else {*/
                    new AsyncUpdate().execute();
                   // SignUpWithData();

            }
        });

        userframe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(i, 1);*/
                userImage = true;
                frontImage = false;
                backImage = false;
                final CharSequence[] options_array = {"Camera", "Gallery"};

                AlertDialog.Builder builder = new AlertDialog.Builder(UpdateDemoFSE.this);
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

        frontframe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userImage = false;
                frontImage = true;
                backImage = false;
                final CharSequence[] options_array = {"Camera", "Gallery"};

                AlertDialog.Builder builder = new AlertDialog.Builder(UpdateDemoFSE.this);
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

        backframe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userImage = false;
                frontImage = false;
                backImage = true;
                final CharSequence[] options_array = {"Camera", "Gallery"};

                AlertDialog.Builder builder = new AlertDialog.Builder(UpdateDemoFSE.this);
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
                android.Manifest.permission.CAMERA,};
        if (!hasPermissions(getApplicationContext(), Permissions)) {
            ActivityCompat.requestPermissions(UpdateDemoFSE.this, Permissions, Permission_All);
        }
    }

    public boolean hasPermissions(Context context, String... permissions) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

   /* private String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(this, contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }
*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == PICK_PHOTO) {

            imageUri = data.getData();
            imagefilePath = getPath(imageUri);


            //set_image.setImageURI(imageUri);
            // String imageProfile=imageUri.toString();
            //  RegPrefManager.getInstance(UpdateProfile.this).setUpdateProfileImage(imageProfile);

            //  file=new File(imagefilePath);
            if (userImage == true) {
                imageUri1 = imageUri;
                UserFilePath = imagefilePath;
                candidate_photo.setImageURI(imageUri);
                addImg.setVisibility(View.GONE);

                file = new File(UserFilePath);
            } else if (frontImage == true) {
                imageUri2 = imageUri;
                FrontFilePath = imagefilePath;
                front_photo.setImageURI(imageUri);
                frontImg.setVisibility(View.GONE);

                file1 = new File(FrontFilePath);
            } else if (backImage == true) {
                imageUri3 = imageUri;
                BackFilePath = imagefilePath;
                back_photo.setImageURI(imageUri);
                backImg.setVisibility(View.GONE);

                file2 = new File(BackFilePath);
            }
            Log.d("imagefilePath", imagefilePath);

        } else if (this.requestCode == requestCode && resultCode == RESULT_OK) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            //set_image.setImageBitmap(bitmap);

            imageUri = getImageUri(this, bitmap);
            imagefilePath = getPath(imageUri);

            //String imageProfile=imageUri.toString();
            //RegPrefManager.getInstance(UpdateProfile.this).setUpdateProfileImage(imageProfile);

            Log.d("imagefilePath", imagefilePath);
            //  file=new File(imagefilePath);  // getting image captured filepath  < ----------------------------------------

            if (userImage == true) {
                UserFilePath = imagefilePath;
                candidate_photo.setImageURI(imageUri);
                addImg.setVisibility(View.GONE);

                file = new File(UserFilePath);
            } else if (frontImage == true) {
                FrontFilePath = imagefilePath;
                front_photo.setImageURI(imageUri);
                frontImg.setVisibility(View.GONE);


                file1 = new File(FrontFilePath);
            } else if (backImage == true) {
                BackFilePath = imagefilePath;
                back_photo.setImageURI(imageUri);
                backImg.setVisibility(View.GONE);


                file2 = new File(BackFilePath);
            }
        }
    }

    private String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};//,Video,Audio
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

    private void requestStoragePermission() {
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
                || (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)
                || (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                || (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED))
            return;

        if ((ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) ||
                (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) ||
                (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) ||
                (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION))) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, STORAGE_PERMISSION_CODE);
    }


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


    private void getProfileDetails() {
        progressDialog.setMessage("loading...");
        progressDialog.show();
        StringRequest stringRequest = new
                StringRequest(Request.Method.GET, "https://genieservice.in/api/user/getprofile?user_id=" + login_user,
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
                                String user_id = object.getString("id");
                                String name = object.getString("first_name");
                                String mobile = object.getString("phone");
                                String email_address = object.getString("email");
                                String frontphoto = object.getString("front_photo_address");
                                String backphoto = object.getString("back_photo_address");
                                String adProof = object.getString("address_proof");
                                String address = object.getString("line1");
                                String City = object.getString("city");
                                String Pin = object.getString("pin");
                                String State = object.getString("state");
                                String Country = object.getString("country");

                                candidatefsename.setText(name);
                                email.setText(email_address);
                                phone_no.setText(mobile);
                                //  userAdproofpinner.setText(adProof);
                                user_address.setText(address);
                                user_city.setText(City);
                                user_pin.setText(Pin);
                                user_state.setText(State);
                                user_country.setText(Country);


                                Picasso.with(getApplicationContext())
                                        .load(frontphoto)
                                        .into(front_photo);

                                file1 = new File(frontphoto);


                                Picasso.with(getApplicationContext())
                                        .load(backphoto)

                                        .into(back_photo);
                                file2 = new File(backphoto);


                              /*  full_name.setText(name);
                                phone_no.setText(mobile);
                                email_id.setText(email_address);*/

                              /*  RegPrefManager.getInstance(ViewFSEProfileActivity.this).setPhoneNo(mobile);
                                RegPrefManager.getInstance(ViewFSEProfileActivity.this).setUserName(name);
                                RegPrefManager.getInstance(ViewFSEProfileActivity.this).setUserEmail(email_address);*/


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
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.getMessage() == null) {
                    if (i < 3) {
                        Log.e("Retry due to error ", "for time : " + i);
                        i++;
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(UpdateDemoFSE.this, "Check your network connection.",
                                Toast.LENGTH_LONG).show();
                    }
                } else
                    Toast.makeText(UpdateDemoFSE.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(UpdateDemoFSE.this);
        requestQueue.add(stringRequest);
    }

    private void SignUpWithData() {


        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                . writeTimeout(120, TimeUnit.SECONDS)
                .build();
        // progressDialog.show();
        AndroidNetworking.upload("https://genieservice.in/api/user/updatefseProfile")
                .addMultipartFile("icon", file1)
                .addMultipartFile("icon1", file2)
                .addMultipartFile("icon2", file)
                .addMultipartParameter("first_name", userName)
                .addMultipartParameter("email", EmailId)
                .addMultipartParameter("phone", phoneNumber)
                .addMultipartParameter("user_id", login_user)
                .addMultipartParameter("sales_experience", experience_rb)
                .addMultipartParameter("job_type", workculture_rb)
                .addMultipartParameter("address_proof", userAddressProof)
                .addMultipartParameter("line1", userAddress)
                .addMultipartParameter("city", userCity)
                .addMultipartParameter("pin", userPin)
                .addMultipartParameter("state", userState)
                .addMultipartParameter("country", userCountry)
                .setTag("uploadTest")
                .setPriority(Priority.HIGH)
                .build()
                .getAsString(new StringRequestListener() {

                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(UpdateDemoFSE.this, response, Toast.LENGTH_SHORT).show();
                       /* JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response);
                            boolean status = Boolean.parseBoolean(jsonObject.getString("status"));
                            String message1 = jsonObject.getString("message");

                            if (status == true){
                                Toast.makeText(UpdateDemoFSE.this, "Submitted Successfully", Toast.LENGTH_SHORT).show();

                            }

                            else {
                                Toast.makeText(UpdateDemoFSE.this, message1, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


*/
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Toast.makeText(UpdateDemoFSE.this, "Network Error", Toast.LENGTH_SHORT).show();
                    }
                });

    }


    private class AsyncUpdate extends AsyncTask<Void, Void, Void> {
        ProgressDialog pDialog;
        String success = null,message="";
        boolean status=true;
        String user_groups;

        @Override
        protected Void doInBackground(Void... params) {
            pDialog.show();
            ArrayList<NameValuePair> cred = new ArrayList<NameValuePair>();
            cred.add(new BasicNameValuePair("icon",FrontFilePath));//user_email
            cred.add(new BasicNameValuePair("icon1",BackFilePath ));
            cred.add(new BasicNameValuePair("icon2",UserFilePath ));
            cred.add(new BasicNameValuePair("first_name",userName ));
            cred.add(new BasicNameValuePair("email",EmailId ));
            cred.add(new BasicNameValuePair("phone",phoneNumber ));
            cred.add(new BasicNameValuePair("user_id",login_user ));
            cred.add(new BasicNameValuePair("sales_experience",experience_rb ));
            cred.add(new BasicNameValuePair("job_type",workculture_rb ));
            cred.add(new BasicNameValuePair("address_proof",userAddressProof ));
            cred.add(new BasicNameValuePair("line1",userAddress ));
            cred.add(new BasicNameValuePair("city",userCity ));
            cred.add(new BasicNameValuePair("pin",userPin ));
            cred.add(new BasicNameValuePair("state",userState ));
            cred.add(new BasicNameValuePair("country",userCountry ));
         //   Log.v("RES","Sending data " +user_phone+ user_pwd  );

            String urlRouteList="https://genieservice.in/api/user/updatefseProfile";
            try {
                String route_response = CustomHttpClient.executeHttpPost(urlRouteList, cred);
                //  Toast.makeText(LogIn.this, route_response, Toast.LENGTH_SHORT).show();

                success = route_response;
                JSONObject jsonObject = new JSONObject(success);

                // user_email=jsonObject.getString("user_email");
                //    status=jsonObject.getString("status");
                status =jsonObject.getBoolean("status");
                message = jsonObject.getString("message");

                String data=jsonObject.getString("data");
                // Toast.makeText(Cart.this, sum_total, Toast.LENGTH_SHORT).show();

                JSONObject jsonObject1 = new JSONObject(data);

                //  Toast.makeText(LogIn.this, user_id, Toast.LENGTH_SHORT).show();

                String first_name=jsonObject1.getString("first_name");

                String phone = jsonObject1.getString("phone");
                String email = jsonObject1.getString("email");
                String username = jsonObject1.getString("username");

               /* if(jsonObject1.has("admin_status")) {
                    admin_status = jsonObject1.getString("admin_status");
                }*/

                //   RegPrefManager.getInstance(LogIn.this).setLoggedinUserId(user_id);
              /*  RegPrefManager.getInstance(LogIn.this).setUserGroup(user_groups);
                RegPrefManager.getInstance(LogIn.this).setLoggedInPhoneNo(user_phone);
                RegPrefManager.getInstance(LogIn.this).setUserName(user_name);
                RegPrefManager.getInstance(LogIn.this).setUserEmail(user_email);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("FLAG", user_id);
                Log.d("user_id", user_id);
                editor.commit();*/



            } catch (Exception e)

            {
                e.printStackTrace();
                //Log.v("Connection error", e.toString());
                //     Toast.makeText(getApplicationContext(), "Genie is away! Try after sometime", Toast.LENGTH_LONG).show();

            }return null;
        }


        protected void onPostExecute(Void result) {
            pDialog.dismiss();
            if(status==true)
            {
                Toast.makeText(getApplicationContext(),message, Toast.LENGTH_LONG).show();
                finish();
            }
            else{
                Toast.makeText(getApplicationContext(),message, Toast.LENGTH_LONG).show();
            }


        }

        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(UpdateDemoFSE.this);
            pDialog.setMessage("Loading In...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
    }

}



