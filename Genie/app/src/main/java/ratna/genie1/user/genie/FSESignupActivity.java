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
import android.hardware.camera2.params.StreamConfigurationMap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import ratna.genie1.user.genie.ObjectNew.FSESignupResponse;
import ratna.genie1.user.genie.ObjectNew.UpdateImageResponse;
import ratna.genie1.user.genie.client.ApiClientGenie;
import ratna.genie1.user.genie.client.ApiInterface;
import ratna.genie1.user.genie.helper.RegPrefManager;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import ratna.genie1.user.genie.ObjectNew.FSESignupResponse;
import ratna.genie1.user.genie.client.ApiClientGenie;
import ratna.genie1.user.genie.client.ApiInterface;
import ratna.genie1.user.genie.helper.RegPrefManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FSESignupActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView addImg,frontImg,backImg;
    ImageView candidate_photo,front_photo,back_photo,eye;
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
    private RadioGroup experienceRB,workcultureRB;
    String experience_rb;
    String workculture_rb;
    EditText password;
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

    String userName,phoneNumber,EmailId,Password,userAddress,userLane,userCity,userPin,userState,userCountry,UserFilePath,FrontFilePath,BackFilePath;
    String userAddressProof;
    Button btnSubmit;
    ApiInterface apiService;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    String login_user="";
    FrameLayout frontframe,backframe,userframe;
    private static final int STORAGE_PERMISSION_CODE = 123;
    Uri imageUri,imageUri1,imageUri2,imageUri3;
    Boolean userImage,frontImage,backImage;
    @SuppressLint({"WrongViewCast", "ClickableViewAccessibility"})
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fse_sign_up);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        requestStoragePermission();
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              onBackPressed();
            }
        });
        apiService =
                ApiClientGenie.getClient().create(ApiInterface.class);
        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedpreferences.edit();
        login_user=sharedpreferences.getString("FLAG", "");
        editor.commit(); // commit changes

        Log.d("login_user", login_user);


        experienceRB = findViewById(R.id.experienceRB);
        workcultureRB = findViewById(R.id.workcultureRB);
        eye = findViewById(R.id.eye);
        phone_no=findViewById(R.id.phone_no);
        password = findViewById(R.id.password);
        password.setVisibility(View.GONE);
        candidatefsename=findViewById(R.id.candidatefsename);
        email=findViewById(R.id.email);
        user_address=findViewById(R.id.user_address);
        user_landmark=findViewById(R.id.user_landmark);
        user_city=findViewById(R.id.user_city);
        user_pin=findViewById(R.id.user_pin);
        user_state=findViewById(R.id.user_state);
        user_country=findViewById(R.id.user_country);
        userAdproofpinner=findViewById(R.id.userAdproofpinner);
        btnSubmit=findViewById(R.id.btnSubmit);
        addImg = findViewById(R.id.addImg);
        frontImg=findViewById(R.id.frontImg);
        backImg=findViewById(R.id.backImg);
        front_photo=findViewById(R.id.front_photo);
        back_photo=findViewById(R.id.back_photo);
        candidate_photo= findViewById(R.id.candidate_photo);
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
                    experience_rb=rb.getText().toString();
                    Toast.makeText(FSESignupActivity.this, experience_rb, Toast.LENGTH_SHORT).show();
                }
            }
        });

        workcultureRB.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    workculture_rb=rb.getText().toString();
                    Toast.makeText(FSESignupActivity.this, workculture_rb, Toast.LENGTH_SHORT).show();
                }
            }
        });

      /*  addImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final CharSequence[] options_array = {"Camera", "Gallery"};

                AlertDialog.Builder builder = new AlertDialog.Builder(FSESignupActivity.this);
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
        });*/

         btnSubmit.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 phoneNumber=phone_no.getText().toString().trim();
                 userName=candidatefsename.getText().toString().trim();
                 EmailId=email.getText().toString().trim();
                 Password=password.getText().toString().trim();
                 userAddress=user_address.getText().toString().trim();
               //  userLane=user_landmark.getText().toString().trim();
                 userCity=user_city.getText().toString().trim();
                 userPin=user_pin.getText().toString().trim();
                 userState=user_state.getText().toString().trim();
                 userCountry=user_country.getText().toString().trim();

                  if (userName.length() < 1){
                     candidatefsename.setError("Please Enter Your Name");
                 }
                 else if (phoneNumber.length() < 1){
                     phone_no.setError("Please Enter Your Phone No.");
                 }
                 else if (EmailId.length() < 1){
                     email.setError("Please Enter Your Email");
                 }
               /*  else if (Password.length() < 1){
                     password.setError("Please Enter Your Password");
                 }*/
                 else if (front_photo.getDrawable() == null){
                      Toast.makeText(FSESignupActivity.this, "Please upload front photo", Toast.LENGTH_SHORT).show();
                 }
                 else if(back_photo.getDrawable() == null){
                      Toast.makeText(FSESignupActivity.this, "Please upload front photo", Toast.LENGTH_SHORT).show();
                 }
                 else if (candidate_photo.getDrawable() == null){
                      Toast.makeText(FSESignupActivity.this, "Please upload candidate photo", Toast.LENGTH_SHORT).show();
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
                  //   new AsyncRegister().execute();
                      getRegisterResponse();

                 }
             }
         });
       /* frontImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final CharSequence[] options_array = {"Camera", "Gallery"};

                AlertDialog.Builder builder = new AlertDialog.Builder(FSESignupActivity.this);
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
        });

        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final CharSequence[] options_array = {"Camera", "Gallery"};

                AlertDialog.Builder builder = new AlertDialog.Builder(FSESignupActivity.this);
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
               /* Intent i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(i, 1);*/
               userImage=true;
               frontImage=false;
               backImage=false;
                final CharSequence[] options_array = {"Camera", "Gallery"};

                AlertDialog.Builder builder = new AlertDialog.Builder(FSESignupActivity.this);
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
                userImage=false;
                frontImage=true;
                backImage=false;
                final CharSequence[] options_array = {"Camera", "Gallery"};

                AlertDialog.Builder builder = new AlertDialog.Builder(FSESignupActivity.this);
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
                userImage=false;
                frontImage=false;
                backImage=true;
                final CharSequence[] options_array = {"Camera", "Gallery"};

                AlertDialog.Builder builder = new AlertDialog.Builder(FSESignupActivity.this);
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
    }


    private String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(this, contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }
    private void  getRegisterResponse(){

        //creating a file
        File file1 = new File(getRealPathFromURI(imageUri1));
        File file2=new File(getRealPathFromURI(imageUri2));
        File file3=new File (getRealPathFromURI(imageUri3));

        //creating request body for file
        RequestBody mFile1 = RequestBody.create(MediaType.parse("image/jpeg"), file1);
        MultipartBody.Part fileToUpload1 = MultipartBody.Part.createFormData("icon", file1.getName(), mFile1);

        RequestBody mFile2 = RequestBody.create(MediaType.parse("image/jpeg"), file2);
        MultipartBody.Part fileToUpload2 = MultipartBody.Part.createFormData("icon1", file2.getName(), mFile2);

        RequestBody mFile3 = RequestBody.create(MediaType.parse("image/jpeg"), file3);
        MultipartBody.Part fileToUpload3 = MultipartBody.Part.createFormData("icon2", file3.getName(), mFile3);


        // RequestBody requestFile = RequestBody.create(MediaType.parse(getContentResolver().getType(selectedImage)), file);
        RequestBody firstnameBody = RequestBody.create(MediaType.parse("text/plain"), userName);
        //  RequestBody useridBody = RequestBody.create(MediaType.parse("text/plain"), EmailId);
        RequestBody emailBody = RequestBody.create(MediaType.parse("text/plain"), EmailId);
        RequestBody phoneNumberBody = RequestBody.create(MediaType.parse("text/plain"), phoneNumber);
        RequestBody distributoruseridBody = RequestBody.create(MediaType.parse("text/plain"), login_user);
        RequestBody saleexperienceBody = RequestBody.create(MediaType.parse("text/plain"), experience_rb);
        RequestBody jobtypeBody = RequestBody.create(MediaType.parse("text/plain"), workculture_rb);
        RequestBody addressproofBody = RequestBody.create(MediaType.parse("text/plain"), userAddressProof);
        RequestBody line1Body = RequestBody.create(MediaType.parse("text/plain"), userAddress);
        RequestBody cityBody = RequestBody.create(MediaType.parse("text/plain"), userCity);
        RequestBody pinBody = RequestBody.create(MediaType.parse("text/plain"), userPin);
        RequestBody stateBody = RequestBody.create(MediaType.parse("text/plain"), userState);
        RequestBody countryBody = RequestBody.create(MediaType.parse("text/plain"), userCountry);


        //   ProgressDialog pDialog;
        // pDialog.show();
        //creating a call and calling the upload image method
        Call<FSESignupResponse> call = apiService.postFSEResponse(fileToUpload1,fileToUpload2,fileToUpload3,
                firstnameBody,emailBody,phoneNumberBody,distributoruseridBody,saleexperienceBody,jobtypeBody,addressproofBody,
                line1Body,cityBody,pinBody,stateBody,countryBody);
       call.enqueue(new Callback<FSESignupResponse>() {
           @Override
           public void onResponse(Call<FSESignupResponse> call, Response<FSESignupResponse> response) {
               boolean status=response.body().isStatus();
               if(status==true){
                   String msg=response.body().getMessage();
                   Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();

                   int fse_user_id=response.body().getUser_id();

                   RegPrefManager.getInstance(FSESignupActivity.this).setFseUserId(String.valueOf(fse_user_id));
                 //  Toast.makeText(getApplicationContext(),"Registration Successful", Toast.LENGTH_LONG).show();
                   startActivity(new Intent(FSESignupActivity.this,FSERegisterPaymentActivity.class));
                   finish();

                 //  Toast.makeText(FSESignupActivity.this, fse_user_id, Toast.LENGTH_SHORT).show();

                  // String user_phone = response.body().getPhone();
                   //RegPrefManager.getInstance(FSESignupActivity.this).setPhoneNo(user_phone);

               }
               else {
                  // String msg=response.body().getMessage();
                   Toast.makeText(FSESignupActivity.this, "Registration Failed ! Try Again", Toast.LENGTH_SHORT).show();
               }
           }

           @Override
           public void onFailure(Call<FSESignupResponse> call, Throwable t) {
               Toast.makeText(FSESignupActivity.this, "Registration Failed ! Try Again", Toast.LENGTH_SHORT).show();
           }
       });
    }



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

            file=new File(imagefilePath);
            if(userImage==true){
                imageUri1=imageUri;
                UserFilePath=imagefilePath;
                candidate_photo.setImageURI(imageUri);
                addImg.setVisibility(View.GONE);
            }
            else if(frontImage==true){
                imageUri2=imageUri;
                FrontFilePath=imagefilePath;
                front_photo.setImageURI(imageUri);
                frontImg.setVisibility(View.GONE);
            }
            else if (backImage==true){
                imageUri3=imageUri;
                BackFilePath=imagefilePath;
                back_photo.setImageURI(imageUri);
                backImg.setVisibility(View.GONE);
            }
            Log.d("imagefilePath",imagefilePath);

        }
        else if(this.requestCode == requestCode && resultCode == RESULT_OK)
        {
            Bitmap bitmap = (Bitmap)data.getExtras().get("data");
            //set_image.setImageBitmap(bitmap);

            imageUri= getImageUri(this,bitmap);
            imagefilePath = getPath(imageUri);

            //String imageProfile=imageUri.toString();
            //RegPrefManager.getInstance(UpdateProfile.this).setUpdateProfileImage(imageProfile);

            Log.d("imagefilePath",imagefilePath);
            file=new File(imagefilePath);  // getting image captured filepath  < ----------------------------------------

            if(userImage==true){
                UserFilePath=imagefilePath;
                candidate_photo.setImageURI(imageUri);
                addImg.setVisibility(View.GONE);
            }
            else if(frontImage==true){
                FrontFilePath=imagefilePath;
                front_photo.setImageURI(imageUri);
                frontImg.setVisibility(View.GONE);
            }
            else if (backImage==true){
                BackFilePath=imagefilePath;
                back_photo.setImageURI(imageUri);
                backImg.setVisibility(View.GONE);
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
                    candidate_photo.setImageURI(imageUri);
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
                    candidate_photo.setImageBitmap(bitmap);
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

  /*  @Override
    protected void onActivityResult(int requestCodefront, int resultCode, Intent datafront) {
        super.onActivityResult(requestCodefront, resultCode, datafront);
        if (resultCode == RESULT_OK && requestCodefront == PICK_PHOTOfront) {

            Uri imageUri = datafront.getData();
            imagefilePathfront = getPath(imageUri);
            front_photo.setImageURI(imageUri);
            frontImg.setVisibility(View.GONE);
            //   btn_submit.setVisibility(View.VISIBLE);
            file1=new File(imagefilePathfront);

 *//* SharedPreferences.Editor editor3 = sharedpreferences.edit();
            editor3.putString("IMAGE", imagefilePath );
            //   Log.d("user_id", user_id);
            editor3.commit();*//*

            //getting image filepath
        }
        else if(this.requestCodefront == requestCodefront && resultCode == RESULT_OK)
        {
            Bitmap bitmap = (Bitmap)datafront.getExtras().get("data");
            front_photo.setImageBitmap(bitmap);
            //  btn_submit.setVisibility(View.VISIBLE);
            frontImg.setVisibility(View.GONE);
            Uri cameraUri= getImageUri(this,bitmap);
            imagefilePathfront = getPath(cameraUri);
            Log.d("imagefilePath",imagefilePathfront);
            file1=new File(imagefilePathfront);  // getting image captured filepath  < ----------------------------------------
        }
    }*/


  /*  @Override
    protected void onActivityResult(int requestCodeback, int resultCodeback, Intent databack) {
        super.onActivityResult(requestCodeback, resultCodeback, databack);
        if (resultCodeback == RESULT_OK && requestCodeback == PICK_PHOTOback) {

            Uri imageUri = databack.getData();
            imagefilePathback = getPath(imageUri);
            back_photo.setImageURI(imageUri);
            backImg.setVisibility(View.GONE);
            //   btn_submit.setVisibility(View.VISIBLE);
            file2=new File(imagefilePathback);

  SharedPreferences.Editor editor3 = sharedpreferences.edit();
            editor3.putString("IMAGE", imagefilePathback );
            //   Log.d("user_id", user_id);
            editor3.commit();

            //getting image filepath
        }
        else if(this.requestCodeback == requestCodeback && resultCodeback == RESULT_OK)
        {
            Bitmap bitmap = (Bitmap)databack.getExtras().get("data");
            back_photo.setImageBitmap(bitmap);
            //  btn_submit.setVisibility(View.VISIBLE);
            backImg.setVisibility(View.GONE);
            Uri cameraUri= getImageUri(this,bitmap);
            imagefilePathback = getPath(cameraUri);
            Log.d("imagefilePath",imagefilePathback);
            file2=new File(imagefilePathback);  // getting image captured filepath  < ----------------------------------------
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
    /*private class AsyncRegister extends AsyncTask<Void, Void, Void> {
        ProgressDialog pDialog;
        String success = null,message="",status="true";

        @Override
        protected Void doInBackground(Void... params) {
            pDialog.show();
            ArrayList<NameValuePair> cred = new ArrayList<NameValuePair>();
            cred.add(new BasicNameValuePair("first_name",userName));
            cred.add(new BasicNameValuePair("email",EmailId));//user_email
            cred.add(new BasicNameValuePair("phone",phoneNumber ));
        //    cred.add(new BasicNameValuePair("password",Password ));
            cred.add(new BasicNameValuePair("distributor_user_id",login_user ));
            cred.add(new BasicNameValuePair("sales_experience",experience_rb ));
            cred.add(new BasicNameValuePair("job_type",workculture_rb ));
            cred.add(new BasicNameValuePair("address_proof",userAddressProof ));
            cred.add(new BasicNameValuePair("icon",FrontFilePath ));
            cred.add(new BasicNameValuePair("icon1",BackFilePath ));
            cred.add(new BasicNameValuePair("icon2",UserFilePath ));
            cred.add(new BasicNameValuePair("line1",userAddress ));
            cred.add(new BasicNameValuePair("city",userCity ));
            cred.add(new BasicNameValuePair("pin",userPin ));
            cred.add(new BasicNameValuePair("state",userState ));
            cred.add(new BasicNameValuePair("country",userCountry ));
            Log.v("RES","Sending data " +userName+ EmailId+ phoneNumber +login_user+experience_rb+workculture_rb
                                 +userAddressProof+FrontFilePath+BackFilePath+UserFilePath+userAddress+userCity+userPin+userState+userCountry);

            String urlRouteList="https://genieservice.in/api/user/registerFse";
            try {
                String route_response = CustomHttpClient.executeHttpPost(urlRouteList, cred);

                success = route_response;
                JSONObject jsonObject = new JSONObject(success);

                // user_email=jsonObject.getString("user_email");
                status=jsonObject.getString("status");
                if(status.equals("false")) {
                    message = jsonObject.getString("errors");
                }
             *//*   String data=jsonObject.getString("data");
                // Toast.makeText(Cart.this, sum_total, Toast.LENGTH_SHORT).show();
                JSONObject jsonObject1 = new JSONObject(data);*//*
              String fse_user_id=jsonObject.getString("user_id");
              RegPrefManager.getInstance(FSESignupActivity.this).setFseUserId(fse_user_id);

                Toast.makeText(FSESignupActivity.this, fse_user_id, Toast.LENGTH_SHORT).show();
              *//*  SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("FLAG", user_id);
                Log.d("user_id", user_id);
                editor.commit();*//*
              String user_phone = jsonObject.getString("phone");
              RegPrefManager.getInstance(FSESignupActivity.this).setPhoneNo(user_phone);
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
                startActivity(new Intent(FSESignupActivity.this,FSERegisterPaymentActivity.class));
                finish();
            }
            else{
                Toast.makeText(getApplicationContext(),"Validation fails! phone number and email should be unique",
                        Toast.LENGTH_LONG).show();
                email.setError("Please enter a valid email");
                phone_no.setError("Please enter a valid no.");
            }
        }

        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(FSESignupActivity.this);
            pDialog.setMessage("Loading In...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
    }*/


    //Requesting permission
    private void requestStoragePermission() {
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
                || (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)
                ||(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                ||(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED))
            return;

        if ((ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) ||
                (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA))||
                (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION))||
                (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION))) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION}, STORAGE_PERMISSION_CODE);
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

}



/* switch(requestCode){
            case 1:
                if(resultCode == RESULT_OK){
                    Bundle extras = data.getExtras();
                    Bitmap bmp = (Bitmap) extras.get("data");
                    candidate_photo.setImageBitmap(bmp);
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
        }*/