package com.example.user.genie;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.user.genie.LocationUtils.PermissionUtils;
import com.example.user.genie.ObjectNew.CabResponse;
import com.example.user.genie.ObjectNew.SellResponse;
import com.example.user.genie.client.ApiClientGenie1;
import com.example.user.genie.client.ApiInterface;
import com.example.user.genie.handler.AsyncTaskHandlerImage;
import com.example.user.genie.handler.AsyncTaskImageListener;
import com.example.user.genie.helper.Constants;
import com.example.user.genie.helper.RegPrefManager;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.File;
import java.util.ArrayList;


import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SellRentActivity extends AppCompatActivity implements View.OnClickListener, AsyncTaskImageListener, LocationListener {
    private Spinner spinner;
    String[] rent = {"Room Rents", "Office Rents", "Shop rent"};
    private Toolbar toolbar;
    private static final int STORAGE_PERMISSION_CODE = 123;
    private EditText priceEd, descripEd, addressEd;
    private Button uploadBtn, sellBtn;
    private ImageView image;
    private AlertDialog.Builder alertDialog;
    ApiInterface apiService;
    ProgressDialog progressDialog;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    String login_user = "", imagepath = "";
    Uri selectedImage;
    private final static int PLAY_SERVICES_REQUEST = 1000;
    private final static int REQUEST_CHECK_SETTINGS = 2000;

    private LocationManager mLastLocation;

    double longitude,latitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_rent);
        apiService =
                ApiClientGenie1.getClient().create(ApiInterface.class);
        requestStoragePermission();
        progressDialog = new ProgressDialog(this);
        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        login_user = sharedpreferences.getString("FLAG", "");
        editor.commit(); // commit changes
        alertDialog = new AlertDialog.Builder(this);

        Log.d("login_user", login_user);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SellRentActivity.this, RentActivity.class));
                finish();
            }
        });
        mLastLocation = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = mLastLocation.getLastKnownLocation(mLastLocation.NETWORK_PROVIDER);

        onLocationChanged(location);
        intialize();

    }

    private void intialize() {
        priceEd = findViewById(R.id.priceEd);
        descripEd = findViewById(R.id.descripEd);
        addressEd = findViewById(R.id.addressEd);
        uploadBtn = findViewById(R.id.uploadBtn);
        image = findViewById(R.id.image);
        spinner = findViewById(R.id.spinner);
        sellBtn = findViewById(R.id.sellBtn);
        uploadBtn.setOnClickListener(this);
        sellBtn.setOnClickListener(this);
        spinnerdata();

    }

    private void spinnerdata() {

//Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, rent);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spinner.setAdapter(aa);
    }


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


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sellBtn:
                if (validationNew()) {
                    if (isNetworkAvailable()) {
                        if(imagepath.isEmpty()){
                            Toast.makeText(getApplicationContext(),"Please Upload Image",Toast.LENGTH_SHORT).show();
                        }else {
                            networkService();
                        }
                        //   postimage();
                    } else {
                        noNetwrokErrorMessage();
                    }
                }
                break;
            case R.id.uploadBtn:
                GalleryImage();
                break;

        }
    }

    //select image from gallery
    private void GalleryImage() {
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            //the image URI
            selectedImage = data.getData();
            imagepath=getRealPathFromURI(selectedImage);
            image.setVisibility(View.VISIBLE);
            image.setImageURI(selectedImage);

        /*    Bitmap bitmap = null;
            try {
                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            image.setImageBitmap(bitmap);*/

            //calling the upload file method after choosing the file
            //   uploadFile(selectedImage, "My Image");
        }
    }

    /*private void uploadFile(Uri fileUri, String desc) {

        //creating a file
        File file = new File(getRealPathFromURI(fileUri));

        //creating request body for file
        RequestBody requestFile = RequestBody.create(MediaType.parse(getContentResolver().getType(fileUri)), file);
        RequestBody descBody = RequestBody.create(MediaType.parse("text/plain"), desc);







    }*/

    /*
    * This method is fetching the absolute path of the image file
    * if you want to upload other kind of files like .pdf, .docx
    * you need to make changes on this method only
    * Rest part will be the same
    * */
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

    private Boolean validationNew() {


        if (priceEd.getText().toString().trim().isEmpty()) {

            priceEd.setError("Please Enter Price");

            return false;
        }


        if (descripEd.getText().toString().trim().isEmpty()) {

            descripEd.setError("Please Enter Short Description");

            return false;
        }


        if (addressEd.getText().toString().trim().isEmpty()) {

            addressEd.setError("Please Enter Address");

            return false;
        }


        return true;
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void noNetwrokErrorMessage() {
        alertDialog.setTitle("Error!");
        alertDialog.setMessage("No internet connection. Please check your internet setting.");
        alertDialog.setCancelable(true);
        alertDialog.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alert = alertDialog.create();
        alert.show();

    }

    private void networkService() {
        String spinnerValue = spinner.getSelectedItem().toString();
        String price = priceEd.getText().toString();
        String desc = descripEd.getText().toString();
        String address = addressEd.getText().toString();
        //  String phone= RegPrefManager.getInstance(SellRentActivity.this).getPhoneNo();
        String phone = RegPrefManager.getInstance(SellRentActivity.this).getPhoneNo();
        String lat=String.valueOf(latitude);
        String lang=String.valueOf(longitude);


        //creating a file
        File file = new File(getRealPathFromURI(selectedImage));
        //creating request body for file
        RequestBody mFile = RequestBody.create(MediaType.parse("image/jpeg"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), mFile);

        // RequestBody requestFile = RequestBody.create(MediaType.parse(getContentResolver().getType(selectedImage)), file);
        RequestBody useridBody = RequestBody.create(MediaType.parse("text/plain"), login_user);
        RequestBody categoryBody = RequestBody.create(MediaType.parse("text/plain"), spinnerValue);
        RequestBody priceBody = RequestBody.create(MediaType.parse("text/plain"), price);
        RequestBody descBody = RequestBody.create(MediaType.parse("text/plain"), desc);
        RequestBody phoneBody = RequestBody.create(MediaType.parse("text/plain"), phone);
        RequestBody addressBody = RequestBody.create(MediaType.parse("text/plain"), address);
        RequestBody latitudeBody = RequestBody.create(MediaType.parse("text/plain"), lat);
        RequestBody logitudeBody = RequestBody.create(MediaType.parse("text/plain"), lang);

        progressDialog.setMessage("Loading");
        progressDialog.show();

        //creating a call and calling the upload image method
        Call<SellResponse> call = apiService.postSellResponse(fileToUpload, useridBody, categoryBody,
                priceBody, descBody, phoneBody, addressBody,latitudeBody,logitudeBody);
        call.enqueue(new Callback<SellResponse>() {
            @Override
            public void onResponse(Call<SellResponse> call, Response<SellResponse> response) {
                progressDialog.dismiss();
                String v = response.body().toString();
                //Log.d("Tag", response.body().toString());
                boolean status = response.body().isStatus();
                if(status==true){
                    String data=response.body().getData();
                    Toast.makeText(getApplicationContext(),data,Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SellRentActivity.this,RentActivity.class));
                    finish();
                }

            }

            @Override
            public void onFailure(Call<SellResponse> call, Throwable t) {
                progressDialog.dismiss();
                Log.d("Tag", "Failure");
            }
        });


    }

   /* private void networkServiceWithOutImage(){
        progressDialog.setMessage("Loading");
        progressDialog.show();
        String spinnerValue = spinner.getSelectedItem().toString();
        String price = priceEd.getText().toString();
        String desc = descripEd.getText().toString();
        String address = addressEd.getText().toString();
        //  String phone= RegPrefManager.getInstance(SellRentActivity.this).getPhoneNo();
        String phone = "";

        //creating a call and calling the upload image method
        Call<SellResponse> call = apiService.postSellOutImageResponse(imagepath, login_user, spinnerValue,
                price, desc, phone, address);
        call.enqueue(new Callback<SellResponse>() {
            @Override
            public void onResponse(Call<SellResponse> call, Response<SellResponse> response) {
                progressDialog.dismiss();
                String v = response.body().toString();
                //Log.d("Tag", response.body().toString());
                boolean status = response.body().isStatus();
                if(status==true){
                    String data=response.body().getData();
                    Toast.makeText(getApplicationContext(),data,Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SellRentActivity.this,MainActivity.class));
                }
            }

            @Override
            public void onFailure(Call<SellResponse> call, Throwable t) {
                Log.d("Tag", "Failure");
            }
        });

    }*/

    //another way to imahe upload
    private void postimage(){
        String spinnerValue = spinner.getSelectedItem().toString();
        String price = priceEd.getText().toString();
        String desc = descripEd.getText().toString();
        String address = addressEd.getText().toString();
        //  String phone= RegPrefManager.getInstance(SellRentActivity.this).getPhoneNo();
        String phone = "9738748330";
        //creating a file
        File file = new File(getRealPathFromURI(selectedImage));
        String imagepath=getRealPathFromURI(selectedImage);

        AsyncTaskHandlerImage asyncTaskHandler1 = new AsyncTaskHandlerImage(SellRentActivity.this, Constants.IMAGE_HOME_SCREEN ,
                login_user,spinnerValue,price,desc,phone,address,imagepath);
        asyncTaskHandler1.execute();
    }


    @Override
    public void onTaskCompleteImage(String result) {
        Log.d("Tag",result);

    }

    @Override
    public void onLocationChanged(Location location) {
         longitude=location.getLongitude();
         latitude=location.getLatitude();
        Log.d("Tag","lat:"+latitude+"lang:"+longitude);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
