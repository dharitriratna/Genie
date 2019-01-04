package ratna.genie1.user.genie;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import ratna.genie1.user.genie.Utils.Count;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class VerticalFragment extends AppCompatActivity implements VerticalRecyclerViewAdapter.MyClickListener {

    Toolbar toolbar;
    private RecyclerView mRecyclerView;
    private VerticalRecyclerViewAdapter mAdapter;
    private static String LOG_TAG = "VerticalFragment-RecyclerViewActivity";
    ImageView add_beat;
    Button btn_submit;
    TextView no_item;
    TextView add_img1;
    ImageView img1;
    File file;
    private static final int PICK_PHOTO = 1958;
    private final int requestCode = 20;
    ImageView imageView;
    TextView responseTextView ;
    private String imagefilePath="";
    Button select_btn;
    ImageView selcted_image;
    String items[]={""};
    String title, subTitle;
    int count;
    int id;

    EditText ed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vertical_fragment);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        no_item = findViewById(R.id.no_item);
        add_img1 = findViewById(R.id.add_img1);
        img1 = findViewById(R.id.img1);
        select_btn = findViewById(R.id.select_btn);
        selcted_image = findViewById(R.id.selcted_image);

        add_beat = findViewById(R.id.add_beat);
        btn_submit = findViewById(R.id.btn_submit);


        mRecyclerView = (RecyclerView) findViewById(R.id.vertical_fragment_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(VerticalFragment.this));
      /*  for (int i = 0; i < count; i++) {

            ed = new EditText(VerticalFragment.this);
            allEds.add(ed);
          //  ed.setBackgroundResource(R.color.blackOpacity);
            ed.setId(id);
            ed.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.FILL_PARENT,
                    RecyclerView.LayoutParams.WRAP_CONTENT));
            linear.addView(ed);
*/
        mAdapter = new VerticalRecyclerViewAdapter(getDataSet());
        mRecyclerView.setAdapter(mAdapter);
        RecyclerView.ItemDecoration itemDecoration =
                new DividerVerticalItemDecoration(VerticalFragment.this);
        mRecyclerView.addItemDecoration(itemDecoration);
        mAdapter.setOnItemClickListener(this);

        List<EditText>allEds = new ArrayList<EditText>();
        add_beat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  addItem();
                actionAdd();
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

        select_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final CharSequence[] options_array = {"Camera", "Gallery"};

                AlertDialog.Builder builder = new AlertDialog.Builder(VerticalFragment.this);
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

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new LongOperation().execute();
            }
        });
    }

    private class LongOperation extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("https://genieservice.in/api/service/allproduct");

            try {
                // Add your data
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("id", items.toString()));
                nameValuePairs.add(new BasicNameValuePair("stringdata", subTitle));
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                Log.d("LOG", items.toString());

                // Execute HTTP Post Request
                HttpResponse response = httpclient.execute(httppost);

            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
            } catch (IOException e) {
                // TODO Auto-generated catch block
            }
            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {
            // TextView txt = (TextView) findViewById(R.id.output);
            // txt.setText("Executed"); // txt.setText(result);
            // Toast.makeText(getApplicationContext(),
            // et.getText().toString(),Toast.LENGTH_SHORT).show();
            // might want to change "executed" for the returned string passed
            // into onPostExecute() but that is upto you
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_PHOTO) {

            Uri imageUri = data.getData();
            imagefilePath = getPath(imageUri);
            selcted_image.setImageURI(imageUri);
            add_img1.setVisibility(View.GONE);
            btn_submit.setVisibility(View.VISIBLE);
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
            selcted_image.setImageBitmap(bitmap);
            btn_submit.setVisibility(View.VISIBLE);
            add_img1.setVisibility(View.GONE);
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

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N && context!=null && permissions!=null){
            for(String permission: permissions){
                if(ActivityCompat.checkSelfPermission(context, permission)!= PackageManager.PERMISSION_GRANTED){
                    return  false;
                }
            }
        }
        return true;
    }

    private ArrayList<VerticalData> getDataSet() {
        ArrayList results = new ArrayList<>();
        for (int index = 0; index < 0; index++) {
            VerticalData obj = new VerticalData("Item Name" + index,
                    "Quantity" + index);
            results.add(index);
        }
        return results;
    }

    @Override
    public void onItemClick(final int position, View v) {
        new AlertDialog.Builder(VerticalFragment.this)
                .setTitle(getString(R.string.vertical_fragment_title_dialog_delete))
                .setMessage(getString(R.string.vertical_fragment_question_delete))
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        mAdapter.deleteItem(position);
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public void addItem() {
        new AlertDialog.Builder(VerticalFragment.this)
                .setTitle(getString(R.string.vertical_fragment_title_dialog_add))
                .setMessage(getString(R.string.vertical_fragment_question_add))
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        actionAdd();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public void actionAdd() {
        VerticalData object = new VerticalData("" ,
                "" );
        mAdapter.addItem(object, mAdapter.getItemCount());

        mRecyclerView.scrollToPosition(mAdapter.getItemCount() - 1);
        mRecyclerView.setVisibility(View.VISIBLE);
        no_item.setVisibility(View.GONE);
        btn_submit.setVisibility(View.VISIBLE);
    }
}
