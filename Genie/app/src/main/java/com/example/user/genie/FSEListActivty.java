package com.example.user.genie;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
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
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.example.user.genie.Adapter.FSEListAdapter;
import com.example.user.genie.Adapter.GiftsAdapter;
import com.example.user.genie.Model.ElectricityBoardModel;
import com.example.user.genie.Model.FSEListModel;
import com.example.user.genie.Model.GiftsModel;
import com.example.user.genie.Utils.GlobalClass;
import com.example.user.genie.helper.RegPrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.user.genie.Utils.Count.setCounting;

public class FSEListActivty extends AppCompatActivity {
    Toolbar toolbar;
    ImageView add_fse;
    RecyclerView fselistRecyclerview;
    private ProgressDialog progressDialog;
    FragmentManager myFragmentManager;
    private int i =0;
    private FSEListAdapter fseListAdapter;
    private List<FSEListModel> fseListModels;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    String login_user="";
    FrameLayout container;
    MenuItem menu_home;
    ImageView start_nav;
    TextView tagline_text,keyname,keyphone,keymail;
    ImageView account_wallet,imageHeader;
    EditText searchEd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fselist_activty);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        add_fse = findViewById(R.id.add_fse);
        add_fse.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {

           final CharSequence[] options_array = {"FSE", "Retailer"};

           AlertDialog.Builder builder = new AlertDialog.Builder(FSEListActivty.this);
           builder.setTitle("Add");
           builder.setItems(options_array, new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialog, int item) {
                   if (options_array[item].equals("FSE")) {
                       startActivity(new Intent(FSEListActivty.this, FSESignupActivity.class));
                   } else if (options_array[item].equals("Retailer")) {
                       startActivity(new Intent(FSEListActivty.this,RetailerSignupActivity.class));
                   }
               }
           });
           builder.show();
       }
    });

        searchEd = findViewById(R.id.searchEd);
        searchEd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());
            }
        });

        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedpreferences.edit();
        login_user=sharedpreferences.getString("FLAG", "");
        editor.commit(); // commit c

        fselistRecyclerview=findViewById(R.id.fselistRecyclerview);
        progressDialog = new ProgressDialog(this);

        GridLayoutManager manager = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);
        fselistRecyclerview.setLayoutManager(manager);

        fseListModels = new ArrayList<>();
        getFseLists();

      /*  keyphone=(TextView) headerView.findViewById(R.id.keyphone);
        keyname=(TextView) headerView.findViewById(R.id.keyname);


        keyphone.setText(RegPrefManager.getInstance(this).getPhoneNo());
        keyname.setText(RegPrefManager.getInstance(this).getUserName());


        imageHeader=(ImageView)headerView.findViewById(R.id.imageHeader);
        String image_value=RegPrefManager.getInstance(FSEListActivty.this).getUpdateProfileImage();
        if(image_value!=null) {
            Uri image_uri = Uri.parse(image_value);
            imageHeader.setImageURI(image_uri);
        }
*/

        fselistRecyclerview.addOnItemTouchListener(new RecyclerTouchListener(this, fselistRecyclerview, new RecyclerTouchListener.ClickListener() {
            @Override
            public boolean onClick(View view, int position) {
                FSEListModel list = fseListModels.get(position);
                String fselistId = list.getId();
                String fseName = list.getFirst_name();
                String fsePhone = list.getPhone();
                String fseEmail = list.getEmail();
                String fseAdProof = list.getAddress_proof();
                String fseAddress = list.getLine1();
                Intent intent = new Intent(FSEListActivty.this,ViewProfileDetailsActivity.class);
                intent.putExtra("FSE_ID",fselistId);
                intent.putExtra("FSE_NAME",fseName);
                intent.putExtra("FSE_PHONE",fsePhone);
                intent.putExtra("FSE_EMAIL",fseEmail);
                intent.putExtra("FSE_ADPROOF",fseAdProof);
                intent.putExtra("FSE_ADDRESS",fseAddress);
                startActivity(intent);
                // finish();

                return true;
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

    }

    private void filter(String text) {
        //new array list that will hold the filtered data
        ArrayList<FSEListModel> filterdNames = new ArrayList<>();

        for(int i=0;i<fseListModels.size();i++) {
            //looping through existing elements
            /*for (String s : placeList) {
                //if the existing elements contains the search input
                if (s.toLowerCase().contains(text.toLowerCase())) {
                    //adding the element to filtered list
                    filterdNames.add(s);
                }
            }*/
            FSEListModel circleModel=fseListModels.get(i);
            if(circleModel.getFirst_name().toUpperCase().contains(text.toUpperCase())){
                filterdNames.add(circleModel);
            }
        }

        //calling a method of the adapter class and passing the filtered list
        fseListAdapter.filterList(filterdNames);
    }


   /* boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {

        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Press BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
                RegPrefManager.getInstance(FSEListActivty.this).setBackService("");
                finish();
            }

        }, 2000);
    }*/

    private void getFseLists() {
        progressDialog.setMessage("Loading");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                "https://genieservice.in/api/user/getListFse?user_id="+login_user,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        progressDialog.dismiss();
                        try {
                            Log.d("onResponse:", s);
                            JSONObject jsonObject = new JSONObject(s);
                            JSONObject object=null;
                            String message="";

                            if (jsonObject!=null){
                                JSONArray array = jsonObject.getJSONArray("data");
                                if (array.length()>0) {

                                    for (int i = 0; i < array.length(); i++) {
                                        String id = "", username = "", phone="", email = "", first_name="",address_proof="",line1="";
                                        JSONObject o = array.getJSONObject(i);

                                        id = o.getString("id");
                                        username = o.getString("username");

                                        phone = o.getString("phone");
                                        email = o.getString("email");
                                        first_name = o.getString("first_name");
                                        address_proof = o.getString("address_proof");
                                        line1 = o.getString("line1");

                                        FSEListModel item = new FSEListModel(
                                                id,username, phone, email,first_name,address_proof,line1);


                                        //   Log.d("operator_name",event_name);
                                        fseListModels.add(item);

                                    }
                                }
                                else {
                                    Toast.makeText(getApplicationContext(), "No Data Found",
                                            Toast.LENGTH_LONG).show();
                                    fselistRecyclerview.setVisibility(View.GONE);
                                    // no_orders_text.setVisibility(View.VISIBLE);
                                }
                            }

                            fseListAdapter = new FSEListAdapter(fseListModels, getApplicationContext());
                            fselistRecyclerview.setAdapter(fseListAdapter);


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
                        Toast.makeText(FSEListActivty.this, "Check your network connection.",
                                Toast.LENGTH_LONG).show();
                    }
                } else
                    Toast.makeText(FSEListActivty.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(FSEListActivty.this);
        requestQueue.add(stringRequest);
    }

  /*  @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        item.setCheckable(true);
        displaySelectedScreen(item.getItemId());
        //make this method blank
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_menu, menu);
        menu_home = menu.findItem(R.id.menu1A);
        LayerDrawable icon = (LayerDrawable) menu_home
                .getIcon();
        if(GlobalClass.somevalue.equals(""))
            setCounting(this, icon, "0");//a
        else
            setCounting(this, icon, GlobalClass.somevalue);//a
        return true;
    }


    private void displaySelectedScreen(int itemId) {

        //creating fragment object
//creating fragment object
        Fragment fragment = null;

        //initializing the fragment object which is selected
        switch (itemId) {


            case R.id.profile:
                setTitleColor(R.color.colorPrimaryDark);
                Intent intent = new Intent(FSEListActivty.this, UpdateProfile.class);
                startActivity(intent);
                break;

            case R.id.privacy:
                Intent privacy = new Intent(FSEListActivty.this, PrivacyPolicy.class);
                startActivity(privacy);
                break;

            case R.id.refund:
                Intent refund = new Intent(FSEListActivty.this, RefundPolicy.class);
                startActivity(refund);
                break;


            case R.id.logout:

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(FSEListActivty.this);
                alertDialogBuilder.setTitle("Log Out");

                alertDialogBuilder
                        .setMessage("Click yes to exit!")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // if this button is clicked, close
                                // current activity
                                sharedpreferences = getSharedPreferences(mypreference,
                                        Context.MODE_PRIVATE);
                                Intent i = new Intent(FSEListActivty.this, LogIn.class);
                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.remove("LOGGED_IN_AS"); // will delete key email
                                editor.commit(); // commit changes
                                startActivity(i);
                                finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // if this button is clicked, just close
                                // the dialog box and do nothing
                                dialog.cancel();
                            }
                        });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();
                // show it
                alertDialog.show();
        }


        if (fragment != null) {
            FragmentTransaction fragmentTransaction = myFragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.maincontainer, fragment);
            fragmentTransaction.commit();

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout2);
        drawer.closeDrawer(GravityCompat.START);
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }*/
}
