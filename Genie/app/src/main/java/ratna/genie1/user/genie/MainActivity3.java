package ratna.genie1.user.genie;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
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

import ratna.genie1.user.genie.Adapter.ServicesAdapter;
import ratna.genie1.user.genie.Fragments.FragmentMain;
import ratna.genie1.user.genie.Fragments.FragmentSendMoney;
import ratna.genie1.user.genie.Model.ServicesModel;
import ratna.genie1.user.genie.ObjectNew.ServiceImage;
import ratna.genie1.user.genie.Utils.GlobalClass;
import ratna.genie1.user.genie.client.ApiClientGenie;
import ratna.genie1.user.genie.client.ApiInterface;
import ratna.genie1.user.genie.helper.RegPrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ratna.genie1.user.genie.Utils.Count;
import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity3 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        ViewPagerEx.OnPageChangeListener,BaseSliderView.OnSliderClickListener {
    Toolbar toolbar;
    FragmentManager myFragmentManager;
    FragmentMain fragmentmain;
    FragmentSendMoney fragmentprofile;
    FrameLayout container;
    MenuItem menu_home;
    int[] image={ R.drawable.image_2, R.drawable.image_3, R.drawable.image_4, R.drawable.image_5, R.drawable.image_6, R.drawable.image_7, R.drawable.image_8};
    private SliderLayout mDemoSlider;
    ImageView start_nav;

    //  LinearLayout ac_mechanic, tv_mechanic, labour, driver, home_tutor;
    LinearLayout home_delivery, events, prepaid, electricity,train_booking,
            dth, broadband, landline, water,moviesLinear,flightLinear,linearBus,carLinear,bikeLinear,


    cabBookingLin,rentLin,birthday_planners, joblin,money_transfer,datacardLn, rawMeat, gasLayout, hotelLayout,insuranceLn;

    Button button1, button2, button3, button4, button5;

    TextView tagline_text,keyname,keyphone,keymail;
    ImageView account_wallet,imageHeader;

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
    ApiInterface apiService;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_layout3);
        apiService =
                ApiClientGenie.getClient().create(ApiInterface.class);
        //    profile = findViewById(R.id.profile);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);
        button5 = findViewById(R.id.button5);

        tagline_text = findViewById(R.id.tagline_text);
        account_wallet = findViewById(R.id.account_wallet);
        account_wallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),WalletActivity.class));
            }
        });

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
        linearBus=findViewById(R.id.linearBus);
        cabBookingLin=findViewById(R.id.cabBookingLin);
        rentLin=findViewById(R.id.rentLin);
        money_transfer=findViewById(R.id.money_transfer);
        datacardLn=findViewById(R.id.datacardLn);
        rawMeat = findViewById(R.id.raw_meat);
        gasLayout = findViewById(R.id.gasLayout);
        hotelLayout = findViewById(R.id.hotelLayout);
        bikeLinear=findViewById(R.id.bikeLinear);
        carLinear=findViewById(R.id.carLinear);

        birthday_planners=findViewById(R.id.birthday_planners);

        insuranceLn=findViewById(R.id.insuranceLn);

        joblin=findViewById(R.id.joblin);

        prepaid.setId(13);
        dth.setId(14);
        electricity.setId(15);
        cabBookingLin.setId(37);
        rentLin.setId(38);
        birthday_planners.setId(39);

        final ArrayList<String> al = new ArrayList<String>();
        al.add("13");
        al.add("14");
        al.add("15");
        al.add("37");
        al.add("38");
        al.add("39");
        al.add("MN");


        prepaid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //     RegPrefManager.getInstance(MainActivity2.this).setMobileCircle();
                RegPrefManager.getInstance(MainActivity3.this).setMobileOperator("","");
                RegPrefManager.getInstance(MainActivity3.this).setMobileCircle("","");
                RegPrefManager.getInstance(MainActivity3.this).setMobileRechargeAmount("");
                RegPrefManager.getInstance(MainActivity3.this).setPhoneNo("");
                RegPrefManager.getInstance(MainActivity3.this).setSuccessID("");
                Intent intent=(new Intent(MainActivity3.this,MobileRecharge.class));
                // intent.putExtra("MobileId",al.indexOf("13"));
                startActivity(intent);
            }
        });

        events.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegPrefManager.getInstance(MainActivity3.this).setSuccessID("");
                startActivity(new Intent(MainActivity3.this,EventManagement.class));
            }
        });

        electricity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegPrefManager.getInstance(MainActivity3.this).setSuccessID("");
                RegPrefManager.getInstance(MainActivity3.this).SetElectricityBoard("","");
                RegPrefManager.getInstance(MainActivity3.this).setElectricityOperator("","");
                Intent intent=(new Intent(MainActivity3.this,PayForElectricity.class));
                intent.putExtra("ElectricityId",al.indexOf("15"));
                startActivity(intent);
            }
        });

        train_booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegPrefManager.getInstance(MainActivity3.this).setSuccessID("");
                startActivity(new Intent(MainActivity3.this,BookTrain.class));
            }
        });
        home_delivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegPrefManager.getInstance(MainActivity3.this).setSuccessID("");
                startActivity(new Intent(MainActivity3.this, HomeDeliveryGrocery.class));
            }
        });

        broadband.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RegPrefManager.getInstance(MainActivity3.this).setSuccessID("");

                RegPrefManager.getInstance(MainActivity3.this).setLandlineOperator("","");
                RegPrefManager.getInstance(MainActivity3.this).setLandlineCircle("","");

                startActivity(new Intent(MainActivity3.this, LandLine.class));
            }
        });

        landline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RegPrefManager.getInstance(MainActivity3.this).setSuccessID("");
                RegPrefManager.getInstance(MainActivity3.this).setLandlineOperator("","");
                RegPrefManager.getInstance(MainActivity3.this).setLandlineCircle("","");
                startActivity(new Intent(MainActivity3.this, LandLine.class));
            }
        });
        water.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegPrefManager.getInstance(MainActivity3.this).setWaterBoard("","");
                RegPrefManager.getInstance(MainActivity3.this).setSuccessID("");
                startActivity(new Intent(MainActivity3.this, WaterBill.class));
            }
        });
        dth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegPrefManager.getInstance(MainActivity3.this).setDTHOperator("","");
                RegPrefManager.getInstance(MainActivity3.this).setMobileCircle("","");
                RegPrefManager.getInstance(MainActivity3.this).setMobileRechargeAmount("");
                RegPrefManager.getInstance(MainActivity3.this).setCustomerId("");
                RegPrefManager.getInstance(MainActivity3.this).setSuccessID("");
                Intent intent=(new Intent(MainActivity3.this,DTHRecharge.class));
                intent.putExtra("DTHId",al.indexOf("14"));
                startActivity(intent);
            }
        });
        moviesLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegPrefManager.getInstance(MainActivity3.this).setSuccessID("");
                startActivity(new Intent(MainActivity3.this,MovieActivity.class));
            }
        });
        flightLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegPrefManager.getInstance(MainActivity3.this).setSuccessID("");
                startActivity(new Intent(MainActivity3.this,FlightActivity.class));
            }
        });
        linearBus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegPrefManager.getInstance(MainActivity3.this).setSuccessID("");
                startActivity(new Intent(MainActivity3.this,BusBookingActivity.class));
            }
        });
        cabBookingLin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegPrefManager.getInstance(MainActivity3.this).setSuccessID("");
                RegPrefManager.getInstance(MainActivity3.this).setCabFromPlace("");
                startActivity(new Intent(MainActivity3.this,CabBookingActivity.class));
            }
        });
        rentLin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegPrefManager.getInstance(MainActivity3.this).setSuccessID("");
                startActivity(new Intent(MainActivity3.this,RentActivity.class));
            }
        });

        birthday_planners.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegPrefManager.getInstance(MainActivity3.this).setSuccessID("");
                startActivity(new Intent(MainActivity3.this,GiftsList.class));
            }
        });

        joblin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegPrefManager.getInstance(MainActivity3.this).setSuccessID("");
                startActivity(new Intent(MainActivity3.this,JobActivity.class));
            }
        });
        money_transfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id= RegPrefManager.getInstance(MainActivity3.this).getRemitterId();
                if(id!=null) {
                    startActivity(new Intent(MainActivity3.this, RemiterDetailsActivity.class));
                }else {
                    startActivity(new Intent(MainActivity3.this, RemiterRegistrationActivity.class));
                }

            }
        });
        datacardLn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegPrefManager.getInstance(MainActivity3.this).setDataCardNo("");
                RegPrefManager.getInstance(MainActivity3.this).setDataCardOperator("","");
                RegPrefManager.getInstance(MainActivity3.this).setDataCardCircle("","");

                startActivity(new Intent(MainActivity3.this,DataCardActivity.class));
            }
        });

        insuranceLn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegPrefManager.getInstance(MainActivity3.this).setSuccessID("");
                startActivity(new Intent(MainActivity3.this,InsuranceCrowdFinchActivity.class));
            }
        });


        rawMeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegPrefManager.getInstance(MainActivity3.this).setSuccessID("");
                startActivity(new Intent(MainActivity3.this,LoanPaymentActivity.class));
            }
        });

        gasLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegPrefManager.getInstance(MainActivity3.this).setGasBoard("","");
                RegPrefManager.getInstance(MainActivity3.this).setSuccessID("");
                startActivity(new Intent(MainActivity3.this,GasBillActivity.class));
            }
        });

        hotelLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegPrefManager.getInstance(MainActivity3.this).setSuccessID("");
                startActivity(new Intent(MainActivity3.this,HotelActivity.class));
            }
        });
        bikeLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegPrefManager.getInstance(MainActivity3.this).setSuccessID("");
                startActivity(new Intent(MainActivity3.this,ComingSoonActivity.class));

            }
        });
        carLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegPrefManager.getInstance(MainActivity3.this).setSuccessID("");
                startActivity(new Intent(MainActivity3.this,ComingSoonActivity.class));
            }
        });


        service_recyclerview = findViewById(R.id.service_recyclerview);

        GridLayoutManager manager = new GridLayoutManager(this, 1, GridLayoutManager.HORIZONTAL, false);
        service_recyclerview.setLayoutManager(manager);
        progressDialog =new ProgressDialog(this);

        servicesModels = new ArrayList<>();
        //getServices();

        getNetwork();


        setImagePager();
        service_recyclerview.addOnItemTouchListener(new RecyclerTouchListener(this, service_recyclerview, new RecyclerTouchListener.ClickListener() {
            @Override
            public boolean onClick(View view, int position) {
                ServicesModel list = servicesModels.get(position);
                String service_id = list.getService_id();
                String service_name = list.getService_name();
                String service_fee = list.getService_fee();
                String service_img = list.getService_img();


                Intent i = new Intent(MainActivity3.this, OrderGenerate.class);
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

        keyphone=(TextView) headerView.findViewById(R.id.keyphone);
        keyname=(TextView) headerView.findViewById(R.id.keyname);


        keyphone.setText(RegPrefManager.getInstance(this).getLoggedInPhoneNo());
        keyname.setText(RegPrefManager.getInstance(this).getUserName());

        imageHeader=(ImageView)headerView.findViewById(R.id.imageHeader);
        String image_value=RegPrefManager.getInstance(MainActivity3.this).getUpdateProfileImage();
        if(image_value!=null) {
            Uri image_uri = Uri.parse(image_value);
            imageHeader.setImageURI(image_uri);
        }

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
               // startActivity(new Intent(getApplicationContext(),AddMoneyActivity.class));
               /* AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getApplicationContext());
                alertDialogBuilder
                        .setMessage("You are not authorized user to use this service")
                        .setCancelable(false)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // if this button is clicked, close
                                // current activity
                              *//*  sharedpreferences = getSharedPreferences(mypreference,
                                        Context.MODE_PRIVATE);
                                Intent i = new Intent(LogIn.this, LogIn.class);
                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.remove("LOGGED_IN_AS"); // will delete key email
                                editor.commit(); // commit changes
                                startActivity(i);*//*

                            }
                        });
                      *//*  .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // if this button is clicked, just close
                                // the dialog box and do nothing
                                dialog.cancel();
                            }
                        });*//*

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();
                // show it
                alertDialog.show();*/
                Toast.makeText(MainActivity3.this, "You are not authorized user to use this service", Toast.LENGTH_SHORT).show();

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
                startActivity(new Intent(getApplicationContext(),OffersActivity.class));

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
            startActivity(new Intent(getApplicationContext(),RequestWalletActivity.class));

              /*  AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getApplicationContext());
                alertDialogBuilder
                        .setMessage("You are not authorized user to use this service")
                        .setCancelable(false)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // if this button is clicked, close
                                // current activity
                              *//*  sharedpreferences = getSharedPreferences(mypreference,
                                        Context.MODE_PRIVATE);
                                Intent i = new Intent(LogIn.this, LogIn.class);
                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.remove("LOGGED_IN_AS"); // will delete key email
                                editor.commit(); // commit changes
                                startActivity(i);*//*

                            }
                        });
                      *//*  .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // if this button is clicked, just close
                                // the dialog box and do nothing
                                dialog.cancel();
                            }
                        });*//*

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();
                // show it
                alertDialog.show();*/

              //  Toast.makeText(MainActivity3.this, "You are not authorized user to use this service", Toast.LENGTH_SHORT).show();

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
                sendIntent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=ratna.genie1.user.genie&hl=en");//share_cont + "\n" + "https://play.google.com/store/apps/details?id=com.nursevibe.vibe.nurse");
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
            TextSliderView textSliderView = new TextSliderView(MainActivity3.this);
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

        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Default);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(3000);
        mDemoSlider.addOnPageChangeListener(this);

    }
    private void getNetwork(){
        progressDialog.setMessage("Loading");
        progressDialog.show();
        Call<ServiceImage> call=apiService.getImageResponse();

        call.enqueue(new Callback<ServiceImage>() {
            @Override
            public void onResponse(Call<ServiceImage> call, retrofit2.Response<ServiceImage> response) {
                try{
                String service_id = "", service_name = "", service_fee = "", service_img="";
                progressDialog.dismiss();
                ArrayList<ServiceImage.Data> data=response.body().getData();
                if(data.size()>0){
                    for(int i=0;i<data.size();i++){
                        service_id=data.get(i).getId();
                        service_name=data.get(i).getName();
                        service_fee=data.get(i).getFee();
                        service_img=data.get(i).getIcon();


                        Log.d("image", service_img);

                        ServicesModel item = new ServicesModel(
                                service_id,service_name, service_fee,service_img);


                        servicesModels.add(item);
                    }
                }
                else {
                                /*Toast.makeText(getApplicationContext(), "No Data Found",
                                        Toast.LENGTH_LONG).show();*/
                    service_recyclerview.setVisibility(View.GONE);
                    // no_orders_text.setVisibility(View.VISIBLE);
                }
                servicesAdapter = new ServicesAdapter(servicesModels, getApplicationContext());
                service_recyclerview.setAdapter(servicesAdapter);
            }catch (IllegalArgumentException ex){
                ex.getMessage();
                }
            }

            @Override
            public void onFailure(Call<ServiceImage> call, Throwable t) {
                Log.d("Tag","Failure");
            }
        });
    }


    public void onResume(){
        super.onResume();
        getNetwork();
    }
    private void getServices() {
        progressDialog.setMessage("Loading");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                "https://genieservice.in/api/service/getservice",
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
                        Toast.makeText(MainActivity3.this, "Check your network connection.",
                                Toast.LENGTH_LONG).show();
                    }
                } else
                    Toast.makeText(MainActivity3.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity3.this);
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
            Count.setCounting(this, icon, "0");//a
        else
            Count.setCounting(this, icon, GlobalClass.somevalue);//a
        return true;
    }

    private void displaySelectedScreen(int itemId) {

        //creating fragment object
//creating fragment object
        Fragment fragment = null;

        //initializing the fragment object which is selected
        switch (itemId) {

            case R.id.my_order:
                setTitleColor(R.color.colorPrimaryDark);
                Intent order = new Intent(MainActivity3.this, MyOrders.class);
                startActivity(order);
                break;

            case R.id.profile:
                setTitleColor(R.color.colorPrimaryDark);
                Intent intent = new Intent(MainActivity3.this, ViewFSEProfileActivity.class);
                startActivity(intent);
                break;

            case R.id.changepassword:
                Intent changepassword = new Intent(MainActivity3.this, ChangePassword2.class);
                startActivity(changepassword);
                break;

         /*   case R.id.privacy:
                Intent privacy = new Intent(MainActivity3.this, PrivacyPolicy.class);
                startActivity(privacy);
                break;

            case R.id.refund:
                Intent refund = new Intent(MainActivity3.this, RefundPolicy.class);
                startActivity(refund);
                break;
*/
            case R.id.add_retailer:
                Intent addretailer = new Intent(MainActivity3.this,DemoRetailerSignUp.class);
                startActivity(addretailer);
                break;

            case R.id.retailer_list:
                Intent retailerlists = new Intent(MainActivity3.this,RetailersListActivity.class);
                startActivity(retailerlists);
                break;

            case R.id.dashboard:
                Intent dashboard = new Intent(MainActivity3.this, DashboardActivity2.class);
                startActivity(dashboard);
                break;

            case R.id.contest:
                Intent contest = new Intent(MainActivity3.this, ContestActivity.class);
                startActivity(contest);
                break;

            case R.id.help_support:
                Intent help_support = new Intent(MainActivity3.this, HelpAndSupportActivity.class);
                startActivity(help_support);
                break;

            case R.id.privacy:
                Intent privacy = new Intent(MainActivity3.this, PrivacyPolicy.class);
                startActivity(privacy);
                break;

            case R.id.refund:
                Intent refund = new Intent(MainActivity3.this, RefundPolicy.class);
                startActivity(refund);
                break;

            case R.id.logout:
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity3.this);
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
                                Intent i = new Intent(MainActivity3.this, LogIn.class);
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

/*
    public boolean onPrepareOptionsMenu(Menu menu){

        MenuItem fseList=menu.findItem(R.id.fse_list);
        MenuItem retailerList=menu.findItem(R.id.retailer_list);
        String user_groups = RegPrefManager.getInstance(MainActivity3.this).getUserGroup();
        Log.d("Group", user_groups);
        if (user_groups.equals("4")){
            fseList.setVisible(true);
            retailerList.setVisible(false);
        }else if (user_groups.equals("5")){
            fseList.setVisible(false);
            retailerList.setVisible(true);
        }else if (user_groups.equals("3")){
            fseList.setVisible(false);
            retailerList.setVisible(false);
        }

        return true;
    }
*/

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finishAffinity();
        this.finish();
    }
}
