package com.example.user.genie;

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
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.genie.Adapter.GoodHomeListAdapter;
import com.example.user.genie.Model.GoodHomeModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;
@SuppressWarnings("ALL")
public class HomeDeliveryGrocery extends AppCompatActivity  implements View.OnClickListener{
    Toolbar toolbar;

    private RecyclerView vertical_fragment_recycler_view;
    private TextView no_item;
    private Button select_btn,btn_submit;
    private ImageView selcted_image,add_beat;
    private GoodHomeListAdapter goodHomeListAdapter;
    private ArrayList<GoodHomeModel> goodHomeModelArrayList,getGoodHomeModelArrayList,arrayList;
    private Random mRandom = new Random();
    private String imagefilePath="",login_user;
    private LinearLayout nextLn;
    File file;
    private static final int PICK_PHOTO = 1958;
    private final int requestCode = 20;
    private JSONObject objectDataMain;
    ProgressDialog progressDialog;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_delivery_grocery);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeDeliveryGrocery.this,MainActivity.class));
                finish();
            }
        });
        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        login_user=sharedpreferences.getString("FLAG", "");
        editor.commit(); // commit changes
        intialize();


    }
    private void intialize(){
        nextLn=findViewById(R.id.nextLn);


        goodHomeModelArrayList=new ArrayList<>();
        getGoodHomeModelArrayList=new ArrayList<>();
        arrayList=new ArrayList<>();
        GoodHomeModel goodHomeModel=new GoodHomeModel();
        goodHomeModel.setItem("");
        goodHomeModel.setQty("");
        goodHomeModelArrayList.add(goodHomeModel);

        no_item=findViewById(R.id.no_item);
        vertical_fragment_recycler_view=findViewById(R.id.vertical_fragment_recycler_view);
        select_btn=findViewById(R.id.select_btn);
        selcted_image=findViewById(R.id.selcted_image);
        btn_submit=findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(this);
        add_beat=findViewById(R.id.add_beat);

        add_beat.setOnClickListener(this);

        if(goodHomeModelArrayList.size()>0) {
            no_item.setVisibility(View.GONE);
            vertical_fragment_recycler_view.setVisibility(View.VISIBLE);

            vertical_fragment_recycler_view.setHasFixedSize(true);
            vertical_fragment_recycler_view.setLayoutManager(new LinearLayoutManager(this));

            goodHomeListAdapter = new GoodHomeListAdapter(HomeDeliveryGrocery.this, goodHomeModelArrayList);

            vertical_fragment_recycler_view.setAdapter(goodHomeListAdapter);
        }
        else {
            no_item.setVisibility(View.VISIBLE);
            vertical_fragment_recycler_view.setVisibility(View.GONE);
        }

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

        select_btn.setOnClickListener(this);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_PHOTO) {

            Uri imageUri = data.getData();
            imagefilePath = getPath(imageUri);
            selcted_image.setImageURI(imageUri);
            btn_submit.setVisibility(View.VISIBLE);
            selcted_image.setVisibility(View.VISIBLE);
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
            selcted_image.setVisibility(View.VISIBLE);
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

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.add_beat:
                // Specify the position
                int position = 0;
              //  String itemLabel = "" + mRandom.nextInt(100);
                GoodHomeModel goodHomeModel=new GoodHomeModel();
                goodHomeModel.setItem("");
                goodHomeModel.setQty("");
                // Add an item to animals list
                goodHomeModelArrayList.add(position,goodHomeModel);


                /*
                    public final void notifyItemInserted (int position)
                        Notify any registered observers that the item reflected at position has been
                        newly inserted. The item previously at position is now at position position + 1.

                        This is a structural change event. Representations of other existing items
                        in the data set are still considered up to date and will not be rebound,
                        though their positions may be altered.

                    Parameters
                    position : Position of the newly inserted item in the data set

                */

                // Notify the adapter that an item inserted
                goodHomeListAdapter.notifyItemInserted(position);

                /*
                    public void scrollToPosition (int position)
                        Convenience method to scroll to a certain position. RecyclerView does not
                        implement scrolling logic, rather forwards the call to scrollToPosition(int)

                    Parameters
                    position : Scroll to this adapter position

                */
                // Scroll to newly added item position
                vertical_fragment_recycler_view.scrollToPosition(position);
                vertical_fragment_recycler_view.setVisibility(View.VISIBLE);
                no_item.setVisibility(View.GONE);

                // Show the added item label
              //  Toast.makeText(HomeDeliveryGrocery.this,"Added : " + "item",Toast.LENGTH_SHORT).show();
                break;
            case R.id.select_btn:
                final CharSequence[] options_array = {"Camera", "Gallery"};

                AlertDialog.Builder builder = new AlertDialog.Builder(HomeDeliveryGrocery.this);
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
                break;
            case R.id.btn_submit:
                getGoodHomeModelArrayList=goodHomeListAdapter.getGoodsArray();
                for(int i=0;i<getGoodHomeModelArrayList.size();i++){
                    GoodHomeModel goodHomeModel1=getGoodHomeModelArrayList.get(i);
                    String checkitem=goodHomeModel1.getItem();
                    String checkqty=goodHomeModel1.getQty();
                    if(checkitem.isEmpty()||checkqty.isEmpty()){
                        Toast.makeText(this, "Please Enter All Item Details(name,Quantity)", Toast.LENGTH_LONG).show();
                    }else {
                        getArray();
                    }


                }
                break;
        }
    }
    private void getArray(){
        objectDataMain=new JSONObject();
        JSONObject objectMain=new JSONObject();
        JSONArray array=new JSONArray();
        for(int i=0;i<getGoodHomeModelArrayList.size();i++) {
            JSONObject innerobject = new JSONObject();
            try {
                innerobject.put("user_id", login_user);
                innerobject.put("item_name", getGoodHomeModelArrayList.get(i).getItem());
                innerobject.put("quantity", getGoodHomeModelArrayList.get(i).getQty());
                innerobject.put("image", imagefilePath);

                array.put(innerobject);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        try {
            objectMain.put("order", array);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        try{
            objectDataMain.put("data",objectMain);

        }catch (JSONException e){
            e.printStackTrace();
        }

        Log.d("TAG","JSON_OBJECT---->"+objectDataMain);

    }
}
