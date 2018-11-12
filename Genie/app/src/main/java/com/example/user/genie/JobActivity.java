package com.example.user.genie;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
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
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.genie.ObjectNew.JobResponse;
import com.example.user.genie.ObjectNew.SellResponse;
import com.example.user.genie.client.ApiClientGenie1;
import com.example.user.genie.client.ApiInterface;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JobActivity extends AppCompatActivity implements View.OnClickListener{
    private Toolbar toolbar;
    private Spinner spinner,tittlespinner;
    private EditText nameTv,qualiTv;
    private EditText locationTv;
    private TextView pdfTv;
    private EditText numberTv,descriptionTv;
    private Button uploadBtn,searchBtn,postBtn;
    String[] exp = { "Total Experience","Less then 1 year", "1 year", "2 year","3 year","4 year","5 year"
            ,"6 year","7 year","8 year","9 year","10+ year"};
    String[] tittle={"Job Title(Current or Most Recent)"};

    //Pdf request code
    private int PICK_PDF_REQUEST = 1;

    //storage permission code
    private static final int STORAGE_PERMISSION_CODE = 123;


    //Uri to store the image uri
    private Uri filePath;
    ApiInterface apiService;
    ProgressDialog progressDialog;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    String login_user = "";
    private AlertDialog.Builder alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job);
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
                startActivity(new Intent(JobActivity.this,MainActivity.class));
                finish();
            }
        });
        intialize();
    }
    private void intialize(){
        spinner=findViewById(R.id.spinner);
        tittlespinner=findViewById(R.id.tittlespinner);
        nameTv=findViewById(R.id.nameTv);
        qualiTv=findViewById(R.id.qualiTv);
        numberTv=findViewById(R.id.numberTv);
        locationTv=findViewById(R.id.locationTv);
        pdfTv=findViewById(R.id.pdfTv);
       // tittleTv=findViewById(R.id.tittleTv);
        descriptionTv=findViewById(R.id.descriptionTv);
        uploadBtn=findViewById(R.id.uploadBtn);
        postBtn=findViewById(R.id.postBtn);
        searchBtn=findViewById(R.id.searchBtn);
        uploadBtn.setOnClickListener(this);
        postBtn.setOnClickListener(this);
        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,exp);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spinner.setAdapter(aa);

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aaa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,tittle);
        aaa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        tittlespinner.setAdapter(aaa);
    }

    @RequiresApi(api = Build.VERSION_CODES.N_MR1)
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.uploadBtn:
                chooseFiles();
                break;
            case R.id.postBtn:
                networkService();
                break;
        }
    }
    //Requesting permission
    private void requestStoragePermission() {
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
                || (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)
                ||(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED))
            return;

        if ((ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) ||
                (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA))
                || (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE))) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
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


    private void chooseFiles(){
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Pdf"), PICK_PDF_REQUEST);
    }
    //handling the image chooser activity result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_PDF_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            pdfTv.setVisibility(View.VISIBLE);
            filePath = data.getData();
           // String path = FileUtils.getPath(this, uri);
            pdfTv.setText(filePath.getPath());
            Log.d("Tag","fils");
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void networkService() {
        String name = nameTv.getText().toString();
        String number = numberTv.getText().toString();
        String highqual = qualiTv.getText().toString();
        String desc = descriptionTv.getText().toString();
        //  String phone= RegPrefManager.getInstance(SellRentActivity.this).getPhoneNo();


        //creating a file
        // String path = filePath
     /*   String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + filePath.getPath());
        myDir.mkdirs();
        String fname = "TestPdf-01A.pdf";

        File file = new File(myDir, fname);
        if (file.exists()) file.delete();*/
        //String fm=filePath.getPath();
        //Uri uu=Uri.parse(fm);

        // File file = new File(FilePath.getPath(this,filePath));
        File file = new File(getPath(this,filePath));

        //creating request body for file
        RequestBody mFile = RequestBody.create(MediaType.parse("application/pdf"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), mFile);

        // RequestBody requestFile = RequestBody.create(MediaType.parse(getContentResolver().getType(selectedImage)), file);
        RequestBody useridBody = RequestBody.create(MediaType.parse("text/plain"), login_user);
        RequestBody nameBody = RequestBody.create(MediaType.parse("text/plain"), name);
        RequestBody numberBody = RequestBody.create(MediaType.parse("text/plain"), number);
        RequestBody highqualBody = RequestBody.create(MediaType.parse("text/plain"), highqual);
        RequestBody descBody = RequestBody.create(MediaType.parse("text/plain"), desc);


        progressDialog.setMessage("Loading");
        progressDialog.show();


        //creating a call and calling the upload image method
        Call<JobResponse> call = apiService.uploadFile(fileToUpload, useridBody,nameBody, numberBody,
                highqualBody, descBody);
        call.enqueue(new Callback<JobResponse>() {
            @Override
            public void onResponse(Call<JobResponse> call, Response<JobResponse> response) {
                progressDialog.dismiss();
                //Response{protocol=http/1.1, code=400, message=Bad Request, url=http://demo.ratnatechnology.co.in/genie/api/service/job}
                String v = response.body().toString();
                //Log.d("Tag", response.body().toString());
                boolean status = response.body().isStatus();
                if(status==true){
                    String data=response.body().getData();
                    Toast.makeText(getApplicationContext(),data,Toast.LENGTH_LONG).show();
                    startActivity(new Intent(JobActivity.this,MainActivity.class));
                    finish();
                }
            }

            @Override
            public void onFailure(Call<JobResponse> call, Throwable t) {
                progressDialog.dismiss();
                Log.d("Tag", "Failure");
            }
        });


    }
    /**
     * Get a file path from a Uri. This will get the the path for Storage Access
     * Framework Documents, as well as the _data field for the MediaStore and
     * other file-based ContentProviders.
     *
     * @param context The context.
     * @param uri The Uri to query.
     * @author paulburke
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/all_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[] {
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context The context.
     * @param uri The Uri to query.
     * @param selection (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }


}
