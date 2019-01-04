package ratna.genie1.user.genie;

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
import ratna.genie1.user.genie.Adapter.FSEListAdapter;
import ratna.genie1.user.genie.Adapter.RetailerListAdapter;
import ratna.genie1.user.genie.Model.FSEListModel;
import ratna.genie1.user.genie.Model.RetailerListModel;
import ratna.genie1.user.genie.Utils.GlobalClass;
import ratna.genie1.user.genie.helper.RegPrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ratna.genie1.user.genie.Model.RetailerListModel;

import static ratna.genie1.user.genie.Utils.Count.setCounting;


public class RetailersListActivity extends AppCompatActivity  {
    Toolbar toolbar;
    ImageView add_retailer;
    private RecyclerView retailerslistRecyclerView;
    private ProgressDialog progressDialog;
    private int i =0;
    private RetailerListAdapter retailerListAdapter;
    private List<RetailerListModel> retailerListModels;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    String login_user="";
    TextView no_orders_text;
    FragmentManager myFragmentManager;
    FrameLayout container;
    MenuItem menu_home;
    ImageView start_nav;
    TextView tagline_text,keyname,keyphone,keymail;
    ImageView account_wallet,imageHeader;
    EditText searchEd;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retailers_list);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
      /*  container = (FrameLayout) findViewById(R.id.maincontainer);
        start_nav=findViewById(R.id.start_nav);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view3);

        View header = navigationView.getHeaderView(0);
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout3);

        start_nav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(drawer.isDrawerOpen(GravityCompat.START))) {
                    drawer.openDrawer(GravityCompat.START);
                }
            }
        });

        navigationView.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) this);
        View headerView = navigationView.getHeaderView(0);
*/
        add_retailer = findViewById(R.id.add_retailer);
        add_retailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),RetailerSignupActivity.class));
            }
        });
        no_orders_text=findViewById(R.id.no_orders_text);


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


        retailerslistRecyclerView = findViewById(R.id.retailerslistRecyclerView);
        progressDialog = new ProgressDialog(this);

        GridLayoutManager manager = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);
        retailerslistRecyclerView.setLayoutManager(manager);

        retailerListModels = new ArrayList<>();
        getRetailerLists();

      /*  keyphone=(TextView) headerView.findViewById(R.id.keyphone);
        keyname=(TextView) headerView.findViewById(R.id.keyname);


        keyphone.setText(RegPrefManager.getInstance(this).getPhoneNo());
        keyname.setText(RegPrefManager.getInstance(this).getUserName());

        imageHeader=(ImageView)headerView.findViewById(R.id.imageHeader);
        String image_value=RegPrefManager.getInstance(RetailersListActivity.this).getUpdateProfileImage();
        if(image_value!=null) {
            Uri image_uri = Uri.parse(image_value);
            imageHeader.setImageURI(image_uri);
        }
*/


        retailerslistRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, retailerslistRecyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public boolean onClick(View view, int position) {
                RetailerListModel list = retailerListModels.get(position);
                String retailerId = list.getId();
                String retailerName = list.getFirst_name();
                String retailerEmail = list.getEmail();
                String retailerPhone = list.getPhone();
                String retailerAdProof = list.getAddress_proof();
                String retailerAddress = list.getLine1();

                Intent intent = new Intent(RetailersListActivity.this,ViewProfileDetailsActivity.class);
                intent.putExtra("FSE_ID",retailerId);
                intent.putExtra("FSE_NAME",retailerName);
                intent.putExtra("FSE_EMAIL",retailerEmail);
                intent.putExtra("FSE_PHONE",retailerPhone);
                intent.putExtra("FSE_ADPROOF",retailerAdProof);
                intent.putExtra("FSE_ADDRESS",retailerAddress);
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
        ArrayList<RetailerListModel> filterdNames = new ArrayList<>();

        for(int i=0;i<retailerListModels.size();i++) {
            //looping through existing elements
            /*for (String s : placeList) {
                //if the existing elements contains the search input
                if (s.toLowerCase().contains(text.toLowerCase())) {
                    //adding the element to filtered list
                    filterdNames.add(s);
                }
            }*/
            RetailerListModel circleModel=retailerListModels.get(i);
            if(circleModel.getFirst_name().toUpperCase().contains(text.toUpperCase())){
                filterdNames.add(circleModel);
            }
        }

        //calling a method of the adapter class and passing the filtered list
        retailerListAdapter.filterList(filterdNames);
    }

    /*  boolean doubleBackToExitPressedOnce = false;

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
                RegPrefManager.getInstance(RetailersListActivity.this).setBackService("");
                finish();
            }

        }, 2000);
    }
*/
    private void getRetailerLists() {
        progressDialog.setMessage("Loading");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                "https://genieservice.in/api/user/getListRetailer?user_id="+login_user,
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

                                        RetailerListModel item = new RetailerListModel(
                                                id,username, phone, email,first_name,address_proof,line1);


                                        //   Log.d("operator_name",event_name);
                                        retailerListModels.add(item);

                                    }
                                }
                                else {
                                    Toast.makeText(getApplicationContext(), "No Data Found",
                                            Toast.LENGTH_LONG).show();
                                    retailerslistRecyclerView.setVisibility(View.GONE);
                                    no_orders_text.setVisibility(View.VISIBLE);
                                }
                            }


                            retailerListAdapter = new RetailerListAdapter(retailerListModels, getApplicationContext());
                            retailerslistRecyclerView.setAdapter(retailerListAdapter);


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
                        Toast.makeText(RetailersListActivity.this, "Check your network connection.",
                                Toast.LENGTH_LONG).show();
                    }
                } else
                    Toast.makeText(RetailersListActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(RetailersListActivity.this);
        requestQueue.add(stringRequest);
    }

   /* @Override
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
                Intent intent = new Intent(RetailersListActivity.this, UpdateProfile.class);
                startActivity(intent);
                break;

            case R.id.privacy:
                Intent privacy = new Intent(RetailersListActivity.this, PrivacyPolicy.class);
                startActivity(privacy);
                break;

            case R.id.refund:
                Intent refund = new Intent(RetailersListActivity.this, RefundPolicy.class);
                startActivity(refund);
                break;

            case R.id.add_retailer:
                Intent addretailer = new Intent(RetailersListActivity.this,RetailerSignupActivity.class);
                startActivity(addretailer);
                break;

            case R.id.retailer_list:
                Intent retailerlists = new Intent(RetailersListActivity.this,RetailersListActivity.class);
                startActivity(retailerlists);
                break;

            case R.id.logout:
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(RetailersListActivity.this);
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
                                Intent i = new Intent(RetailersListActivity.this, LogIn.class);
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
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout3);
        drawer.closeDrawer(GravityCompat.START);
    }



    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }*/
}
