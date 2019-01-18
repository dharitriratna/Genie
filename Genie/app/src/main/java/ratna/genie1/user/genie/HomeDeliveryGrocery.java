package ratna.genie1.user.genie;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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

import ratna.genie1.user.genie.Adapter.GoodHomeListAdapter;
import ratna.genie1.user.genie.Model.GoodHomeModel;
import ratna.genie1.user.genie.MoneyTransfer.MoneyTransferActivity;
import ratna.genie1.user.genie.ObjectNew.HomeGroceryResponse;
import ratna.genie1.user.genie.client.ApiClientGenie;
import ratna.genie1.user.genie.client.ApiClientGenie1;
import ratna.genie1.user.genie.client.ApiInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import ratna.genie1.user.genie.Model.GoodHomeModel;
import ratna.genie1.user.genie.client.ApiClientGenie1;
import ratna.genie1.user.genie.client.ApiInterface;
import ratna.genie1.user.genie.helper.RegPrefManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    private AlertDialog.Builder alertDialog;
    ApiInterface apiService;
    String groupId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_delivery_grocery);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        groupId = RegPrefManager.getInstance(HomeDeliveryGrocery.this).getUserGroup();
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
            }
        });
        apiService =
                ApiClientGenie1.getClient().create(ApiInterface.class);
        progressDialog = new ProgressDialog(this);
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
                innerobject.put("service_order_id", login_user);
                innerobject.put("service_id", getGoodHomeModelArrayList.get(i).getItem());
                innerobject.put("item_name", getGoodHomeModelArrayList.get(i).getItem());
                innerobject.put("qty", getGoodHomeModelArrayList.get(i).getQty());
                innerobject.put("unit", "KG");
                innerobject.put("price", "100");
                innerobject.put("total", "100");

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
        if (isNetworkAvailable()) {
            networkDelivery(); //register add beneficiary
        }
        else {
            noNetwrokErrorMessage();
        }
    }


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



    private void networkDelivery(){
        progressDialog.setMessage("Please wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        String value=objectDataMain.toString();

        Log.d("Tag", String.valueOf(value));

        Call<HomeGroceryResponse> call=apiService.postHomeGrocery(value);

        call.enqueue(new Callback<HomeGroceryResponse>() {
            @Override
            public void onResponse(Call<HomeGroceryResponse> call, Response<HomeGroceryResponse> response) {
                progressDialog.dismiss();

                boolean status=response.body().isStatus();
                Log.d("Tag","status");
                if (status==true){
                    String data=response.body().getData();
                    Toast.makeText(getApplicationContext(),data,Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<HomeGroceryResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_LONG).show();
            }
        });

    }
}
