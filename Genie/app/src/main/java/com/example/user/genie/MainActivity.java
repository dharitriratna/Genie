package com.example.user.genie;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.example.user.genie.Adapter.ServicesAdapter;
import com.example.user.genie.Fragments.FragmentMain;
import com.example.user.genie.Fragments.FragmentProfile;
import com.example.user.genie.Model.CardModel;
import com.example.user.genie.Model.ServicesModel;
import com.example.user.genie.Utils.GlobalClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.user.genie.LogIn.mypreference;
import static com.example.user.genie.Utils.Count.setCounting;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        ViewPagerEx.OnPageChangeListener,BaseSliderView.OnSliderClickListener {
    Toolbar toolbar;
    FragmentManager myFragmentManager;
    FragmentMain fragmentmain;
    FragmentProfile fragmentprofile;
    FrameLayout container;
    MenuItem menu_home;
    int[] image={ R.drawable.air_ticket_ad, R.drawable.ad_movie, R.drawable.ad_money_transfer, R.drawable.bday_ad};
    private SliderLayout mDemoSlider;
    ImageView start_nav;

  //  LinearLayout ac_mechanic, tv_mechanic, labour, driver, home_tutor;
    LinearLayout home_delivery, events, prepaid, electricity,train_booking,
          dth, broadband, landline, water,moviesLinear,flightLinear;
    Button button1, button2, button3, button4, button5;

    TextView tagline_text;

    RecyclerView service_recyclerview;
    ProgressDialog progressDialog;
    int i=0;
    private ServicesAdapter servicesAdapter;
    private List<ServicesModel> servicesModels;


    final static String TAG_1 = "FRAGMENT_1";
    final static String TAG_2 = "FRAGMENT_2";
    final static String TAG_3 = "FRAGMENT_3";
    final static String TAG_4 = "FRAGMENT_4";
    final static String TAG_5 = "FRAGMENT_5";
    final static String KEY_MSG_1 = "FRAGMENT1_MSG";
    final static String KEY_MSG_2 = "FRAGMENT2_MSG";
    final static String KEY_MSG_3 = "FRAGMENT3_MSG";
    final static String KEY_MSG_4 = "FRAGMENT4_MSG";
    final static String KEY_MSG_5 = "FRAGMENT5_MSG";

    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    String login_user="";

    String acmech;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_layout);
    //    profile = findViewById(R.id.profile);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);
        button5 = findViewById(R.id.button5);

        tagline_text = findViewById(R.id.tagline_text);

        String fontPath = "fonts/Raleway-Light.ttf";
        String fontPath2 = "fonts/Raleway-Thin.ttf";
        String fontPath3 = "fonts/Raleway_SemiBold.ttf";

        Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);
        Typeface tf2 = Typeface.createFromAsset(getAssets(), fontPath2);
        Typeface tf3 = Typeface.createFromAsset(getAssets(), fontPath3);

        tagline_text.setTypeface(tf3);

        start_nav = findViewById(R.id.start_nav);
        mDemoSlider = findViewById(R.id.slider);
        home_delivery = findViewById(R.id.home_delivery);
        events = findViewById(R.id.events);
        prepaid = findViewById(R.id.prepaid);
        dth = findViewById(R.id.dth);
        broadband = findViewById(R.id.broadband);
        landline = findViewById(R.id.landline);
        water = findViewById(R.id.water);
        electricity = findViewById(R.id.electricity);
        train_booking = findViewById(R.id.train_booking);
        moviesLinear=findViewById(R.id.moviesLinear);
        flightLinear=findViewById(R.id.flightLinear);

        prepaid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,MobileRecharge.class));
            }
        });

        events.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,EventManagement.class));
            }
        });

        electricity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,PayForElectricity.class));
            }
        });

        train_booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,BookTrain.class));
            }
        });
        home_delivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, VerticalFragment.class));
            }
        });

        broadband.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LandLine.class));
            }
        });

        landline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LandLine.class));
            }
        });
        water.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, WaterBill.class));
            }
        });
        dth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, DTHRecharge.class));
            }
        });
        moviesLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,MovieActivity.class));
            }
        });
        flightLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,FlightActivity.class));
            }
        });
        service_recyclerview = findViewById(R.id.service_recyclerview);

        GridLayoutManager manager = new GridLayoutManager(this, 1, GridLayoutManager.HORIZONTAL, false);
        service_recyclerview.setLayoutManager(manager);
        progressDialog =new ProgressDialog(this);

        servicesModels = new ArrayList<>();
        getServices();


        setImagePager();
        service_recyclerview.addOnItemTouchListener(new RecyclerTouchListener(this, service_recyclerview, new RecyclerTouchListener.ClickListener() {
            @Override
            public boolean onClick(View view, int position) {
                ServicesModel list = servicesModels.get(position);
                String service_id = list.getService_id();
                String service_name = list.getService_name();
                String service_fee = list.getService_fee();
                String service_img = list.getService_img();


                Intent i = new Intent(MainActivity.this, OrderGenerate.class);
                i.putExtra("SERVICE_ID", service_id);
                i.putExtra("SERVICE_NAME", service_name);
                i.putExtra("SERVICE_FEES", service_fee);
                i.putExtra("SERVICE_IMAGE", service_img);
                startActivity(i);

                return true;
            }

            @Override
            public void onLongClick(View view, int position) {
                // Toast.makeText(Samagri.this, "Long Press", Toast.LENGTH_SHORT).show();
            }
        }));


/*
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),UpdateProfile.class));
            }
        });
*/

        sharedpreferences = getSharedPreferences(mypreference, MODE_PRIVATE);

        Toolbar toolbar = findViewById(R.id.toolbar);
     //   toolbar.setNavigationIcon(R.drawable.ic_clear_all);


        container = (FrameLayout) findViewById(R.id.maincontainer);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        View header = navigationView.getHeaderView(0);
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        start_nav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(drawer.isDrawerOpen(GravityCompat.START))) {
                    drawer.openDrawer(GravityCompat.START);
                }
            }
        });

        navigationView.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) this);

        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedpreferences.edit();
        login_user=sharedpreferences.getString("FLAG", "");
        editor.commit(); // commit changes


        Log.d("login_user", login_user);

        button1.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

        button1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                    button1.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    button2.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                    button3.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                    button4.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                    button5.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
            }
        });


        button2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                    button2.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    button1.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                    button3.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                    button4.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                    button5.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                button3.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                button1.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                button2.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                button4.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                button5.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                button4.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                button1.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                button2.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                button3.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                button5.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
            }
        });

        button5.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "App is not available in playstore yet");//share_cont + "\n" + "https://play.google.com/store/apps/details?id=com.nursevibe.vibe.nurse");
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent,"share via:"));
              //
                //  startActivity(sendIntent);
                button5.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                button1.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                button2.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                button4.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                button3.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
            }
        });
    }


    private void setImagePager() {
        for(int i=0;i<image.length;i++){
            TextSliderView textSliderView = new TextSliderView(MainActivity.this);
            // initialize a SliderLayout
            textSliderView.image(image[i])
                    .description("Genie")
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);
//.description("Tourism")
            //add your extra information
            //textSliderView.bundle(new Bundle());
            //textSliderView.getBundle().putString("extra","Tourism");

            mDemoSlider.addSlider(textSliderView);
        }

        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(3000);
        mDemoSlider.addOnPageChangeListener(this);

    }
    private void getServices() {
        progressDialog.setMessage("Loading");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                "http://demo.ratnatechnology.co.in/genie/index.php/api/service/getservice",
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
                                        String service_id = "", service_name = "", service_fee = "", service_img="";
                                        JSONObject o = array.getJSONObject(i);
                                        service_id = o.getString("id");
                                        service_name = o.getString("name");
                                        service_fee = o.getString("fee");
                                        service_img = o.getString("icon");

                                        Log.d("image", service_img);

                                        ServicesModel item = new ServicesModel(
                                                service_id,service_name, service_fee,service_img);


                                        servicesModels.add(item);

                                    /*String strCurrentDate = order_date;
                                    SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy hh:mm:ss Z");
                                    Date newDate = format.parse(strCurrentDate);

                                    format = new SimpleDateFormat("MMM dd,yyyy hh:mm a");
                                    String date = format.format(newDate);*/
                                    }
                                }
                                else {
                                /*Toast.makeText(getApplicationContext(), "No Data Found",
                                        Toast.LENGTH_LONG).show();*/
                                    service_recyclerview.setVisibility(View.GONE);
                                    // no_orders_text.setVisibility(View.VISIBLE);
                                }
                            }

                            servicesAdapter = new ServicesAdapter(servicesModels, getApplicationContext());
                            service_recyclerview.setAdapter(servicesAdapter);


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
                        Toast.makeText(MainActivity.this, "Check your network connection.",
                                Toast.LENGTH_LONG).show();
                    }
                } else
                    Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(stringRequest);
    }



    @Override
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
           /* case R.id.about:
                startActivity(new Intent(MainActivity.this, About.class));
                break;

            case R.id.contact:
                startActivity(new Intent(MainActivity.this, Contacts.class));
                break;
            *//*case R.id.nav_menu3:
                break;*//*

            case R.id.subscription:
                startActivity(new Intent(MainActivity.this,Subscription_status.class));
                break;

            case R.id.wishlist:
                startActivity(new Intent(MainActivity.this,Wishlist.class));
                break;

            case R.id.mycart:
                startActivity(new Intent(MainActivity.this, Cart.class));
                break;

            case R.id.order:
                startActivity(new Intent(MainActivity.this, Order.class));
                break;

            case R.id.share_app:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=com.bloomandwild&hl=en");//share_cont + "\n" + "https://play.google.com/store/apps/details?id=com.nursevibe.vibe.nurse");
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
                break;
*/
            case R.id.my_order:
                Intent intent_ordr = new Intent(MainActivity.this,MyOrders.class);
                startActivity(intent_ordr);
                break;

            case R.id.profile:
                setTitleColor(R.color.colorPrimaryDark);
                Intent intent = new Intent(MainActivity.this, UpdateProfile.class);
                startActivity(intent);
                break;

            case R.id.privacy:
                Intent privacy = new Intent(MainActivity.this, PrivacyPolicy.class);
                startActivity(privacy);
                break;

            case R.id.refund:
                Intent refund = new Intent(MainActivity.this, RefundPolicy.class);
                startActivity(refund);
                break;

            case R.id.logout:

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
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
                                Intent i = new Intent(MainActivity.this, LogIn.class);
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
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
