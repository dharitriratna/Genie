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
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
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

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.androidnetworking.interfaces.UploadProgressListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import ratna.genie1.user.genie.ObjectNew.FSESignupResponse;
import ratna.genie1.user.genie.client.ApiClientGenie;
import ratna.genie1.user.genie.client.ApiInterface;
import ratna.genie1.user.genie.helper.RegPrefManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DemoSignUp extends AppCompatActivity {

    Toolbar toolbar;
    TextView addImg,frontImg,backImg;
    ImageView candidate_photo,front_photo,back_photo,eye;
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
    Button btnSubmit;
    String message = "";



    FrameLayout frontframe,backframe,userframe;

    String userAddressProof;

    String userName,phoneNumber,EmailId,Password,userAddress,userLane,userCity,userPin,userState,userCountry,UserFilePath,FrontFilePath,BackFilePath;
    Boolean userImage,frontImage,backImage;
    private static final int PICK_PHOTO = 1958;
    private static final int PICK_PHOTOfront = 1958;
    private static final int PICK_PHOTOback = 1958;
    private final int requestCode = 20;
    private static final int CAMERA_REQUEST = 1888;
    private final int requestCodefront = 20;
    private final int requestCodeback = 20;

    Uri imageUri,imageUri1,imageUri2,imageUri3;

    private String imagefilePath="";
    private String imagefilePathfront="";
    private String imagefilePathback="";

    private static final int STORAGE_PERMISSION_CODE = 123;

    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    String login_user="";
    ProgressDialog progressDialog;
    Bitmap bitmap;
    ApiInterface apiService;
    @SuppressLint("ClickableViewAccessibility")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_sign_up);
        requestStoragePermission();
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        progressDialog =new ProgressDialog(this);

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

    /*    OkHttpClient okHttpClient = new OkHttpClient() .newBuilder()
                .addNetworkInterceptor(new StethoInterceptor())
                .build();*/
      //  AndroidNetworking.initialize(getApplicationContext());
        apiService =
                ApiClientGenie.getClient().create(ApiInterface.class);

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
                    Toast.makeText(DemoSignUp.this, experience_rb, Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(DemoSignUp.this, workculture_rb, Toast.LENGTH_SHORT).show();
                }
            }
        });


        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedpreferences.edit();
        login_user=sharedpreferences.getString("FLAG", "");
        editor.commit(); // commit changes

        Log.d("login_user", login_user);



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
                    Toast.makeText(DemoSignUp.this, "Please upload front photo", Toast.LENGTH_SHORT).show();
                }
                else if(back_photo.getDrawable() == null){
                    Toast.makeText(DemoSignUp.this, "Please upload front photo", Toast.LENGTH_SHORT).show();
                }
                else if (candidate_photo.getDrawable() == null){
                    Toast.makeText(DemoSignUp.this, "Please upload candidate photo", Toast.LENGTH_SHORT).show();
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
                  getRegisterResponse();
                    //SignUpWithData();

                }
            }
        });


        userframe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(i, 1);*/
                userImage=true;
                frontImage=false;
                backImage=false;
                final CharSequence[] options_array = {"Camera", "Gallery"};

                AlertDialog.Builder builder = new AlertDialog.Builder(DemoSignUp.this);
                builder.setTitle("Choose");
                builder.setItems(options_array, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (options_array[item].equals("Camera")) {
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(intent, CAMERA_REQUEST);
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

                AlertDialog.Builder builder = new AlertDialog.Builder(DemoSignUp.this);
                builder.setTitle("Choose");
                builder.setItems(options_array, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (options_array[item].equals("Camera")) {
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(intent, CAMERA_REQUEST);
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

                AlertDialog.Builder builder = new AlertDialog.Builder(DemoSignUp.this);
                builder.setTitle("Choose");
                builder.setItems(options_array, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (options_array[item].equals("Camera")) {
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(intent, CAMERA_REQUEST);
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
            if(userImage==true){
                imageUri1=imageUri;
                UserFilePath=imagefilePath;
                candidate_photo.setImageURI(imageUri);
                addImg.setVisibility(View.GONE);

                file = new File(UserFilePath);
            }
            else if(frontImage==true){
                imageUri2=imageUri;
                FrontFilePath=imagefilePath;
                front_photo.setImageURI(imageUri);
                frontImg.setVisibility(View.GONE);

                file1 = new File(FrontFilePath);
            }
            else if (backImage==true){
                imageUri3=imageUri;
                BackFilePath=imagefilePath;
                back_photo.setImageURI(imageUri);
                backImg.setVisibility(View.GONE);

                file2 = new File(BackFilePath);
            }
            Log.d("imagefilePath",imagefilePath);

        }
        else if(requestCode == CAMERA_REQUEST && resultCode == RESULT_OK)
        {
             bitmap = (Bitmap)data.getExtras().get("data");
            //set_image.setImageBitmap(bitmap);

            imageUri= getImageUri(this,bitmap);
            imagefilePath = getPath(imageUri);

            //String imageProfile=imageUri.toString();
            //RegPrefManager.getInstance(UpdateProfile.this).setUpdateProfileImage(imageProfile);

            Log.d("Tag","imagefilePath==================> "+imagefilePath);
          //  file=new File(imagefilePath);  // getting image captured filepath  < ----------------------------------------
              //  if(userImage!=null){
                if (userImage == true) {
                    imageUri1=imageUri;
                    UserFilePath = imagefilePath;
                    candidate_photo.setImageBitmap(bitmap);
                    addImg.setVisibility(View.GONE);

                    file = new File(UserFilePath);
               // }
                }
              //  if(frontImage!=null){
               else if (frontImage == true) {
                    imageUri2=imageUri;
                    FrontFilePath = imagefilePath;
                    front_photo.setImageBitmap(bitmap);
                    frontImg.setVisibility(View.GONE);


                    file1 = new File(FrontFilePath);
             //   }
        }
             //   if(backImage!=null) {
                else     if (backImage == true) {
                    imageUri3=imageUri;
                        BackFilePath = imagefilePath;
                        back_photo.setImageBitmap(bitmap);
                        backImg.setVisibility(View.GONE);

                        file2 = new File(BackFilePath);
                 //   }
                }
            }


    }

    //Requesting permission
    private void requestStoragePermission() {
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
                || (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)
                || (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
                ||(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                ||(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED))
            return;

        if ((ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) ||
                (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA))||
                (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE))||
                (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION))||
                (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION))) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
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

   /* private void SignUpWithData() {


    //    progressDialog.show();
        AndroidNetworking.upload("https://genieservice.in/api/user/registerFse")
                .addMultipartFile("icon",file1)
                .addMultipartFile("icon1",file2)
                .addMultipartFile("icon2",file)
                .addMultipartParameter("first_name",userName)
                .addMultipartParameter("email",EmailId)
                .addMultipartParameter("phone",phoneNumber)
                .addMultipartParameter("distributor_user_id",login_user)
                .addMultipartParameter("sales_experience",experience_rb)
                .addMultipartParameter("job_type",workculture_rb)
                .addMultipartParameter("address_proof",userAddressProof)
                .addMultipartParameter("line1",userAddress)
                .addMultipartParameter("city",userCity)
                .addMultipartParameter("pin",userPin)
                .addMultipartParameter("state",userState)
                .addMultipartParameter("country",userCountry)
                .setTag("uploadTest")
                .setPriority(Priority.HIGH)
                .build()
                .setUploadProgressListener(new UploadProgressListener() {
                    @Override
                    public void onProgress(long bytesUploaded, long totalBytes) {
                        // do anything with progress
                    }
                })
                .getAsString(new StringRequestListener() {

                    @Override
                    public void onResponse(String response) {


                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String status = jsonObject.getString("status");

                            if(status.equals("false")) {
                                String message1 = jsonObject.getString("message");
                                Toast.makeText(DemoSignUp.this, message1, Toast.LENGTH_SHORT).show();
                            }
                            else{
                                progressDialog.dismiss();

                                Toast.makeText(DemoSignUp.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(DemoSignUp.this,FSERegisterPaymentActivity.class));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(DemoSignUp.this, "Registration Error", Toast.LENGTH_SHORT).show();
                    }
                });
    }*/
   private void  getRegisterResponse(){
      // progressDialog.show();

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

       progressDialog.setMessage("Please wait...");
       progressDialog.setCanceledOnTouchOutside(false);
       progressDialog.show();
       Call<FSESignupResponse> call = apiService.postFSEResponse(fileToUpload1,fileToUpload2,fileToUpload3,
               firstnameBody,emailBody,phoneNumberBody,distributoruseridBody,saleexperienceBody,jobtypeBody,addressproofBody,
               line1Body,cityBody,pinBody,stateBody,countryBody);
       call.enqueue(new Callback<FSESignupResponse>() {
           @Override
           public void onResponse(Call<FSESignupResponse> call, Response<FSESignupResponse> response) {
               progressDialog.dismiss();
               boolean status=response.body().isStatus();
               if(status==true){
                   Toast.makeText(DemoSignUp.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                   int fseretaileruserId = response.body().getUser_id();
                   RegPrefManager.getInstance(DemoSignUp.this).setRetailerUserId(String.valueOf(fseretaileruserId));
                   startActivity(new Intent(DemoSignUp.this,FSERegisterPaymentActivity.class));
                   finish();

                   //  Toast.makeText(FSESignupActivity.this, fse_user_id, Toast.LENGTH_SHORT).show();

                   // String user_phone = response.body().getPhone();
                   //RegPrefManager.getInstance(FSESignupActivity.this).setPhoneNo(user_phone);

               }
               else {
                   String message1 = response.body().getMessage();
                   Toast.makeText(DemoSignUp.this, message1, Toast.LENGTH_SHORT).show();

                   // String msg=response.body().getMessage();
           //        Toast.makeText(FSESignupActivity.this, "Registration Failed ! Try Again", Toast.LENGTH_SHORT).show();
               }
           }

           @Override
           public void onFailure(Call<FSESignupResponse> call, Throwable t) {
               progressDialog.dismiss();
               Toast.makeText(DemoSignUp.this, "Registration Failed ! Try Again", Toast.LENGTH_SHORT).show();
           }
       });
   }


}

