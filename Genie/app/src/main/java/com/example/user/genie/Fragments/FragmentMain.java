package com.example.user.genie.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.example.user.genie.Adapter.ServicesAdapter;
import com.example.user.genie.AllInsuranseActivity;
import com.example.user.genie.BookTrain;
import com.example.user.genie.BusBookingActivity;
import com.example.user.genie.CabBookingActivity;
import com.example.user.genie.ComingSoonActivity;
import com.example.user.genie.DTHRecharge;
import com.example.user.genie.DataCardActivity;
import com.example.user.genie.EventManagement;
import com.example.user.genie.FlightActivity;
import com.example.user.genie.GasBillActivity;
import com.example.user.genie.GiftsList;
import com.example.user.genie.HomeDeliveryGrocery;
import com.example.user.genie.HotelActivity;
import com.example.user.genie.JobActivity;
import com.example.user.genie.LandLine;
import com.example.user.genie.LoanPaymentActivity;
import com.example.user.genie.MainActivity2;
import com.example.user.genie.MobileRecharge;
import com.example.user.genie.Model.ServicesModel;
import com.example.user.genie.MovieActivity;
import com.example.user.genie.ObjectNew.ServiceImage;
import com.example.user.genie.OrderGenerate;
import com.example.user.genie.PayForElectricity;
import com.example.user.genie.R;
import com.example.user.genie.RecyclerTouchListener;
import com.example.user.genie.RemiterDetailsActivity;
import com.example.user.genie.RemiterRegistrationActivity;
import com.example.user.genie.RentActivity;
import com.example.user.genie.WaterBill;
import com.example.user.genie.client.ApiClientGenie;
import com.example.user.genie.client.ApiInterface;
import com.example.user.genie.helper.RegPrefManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;


public class FragmentMain extends Fragment implements ViewPagerEx.OnPageChangeListener,BaseSliderView.OnSliderClickListener {

    int[] image={ R.drawable.image_2, R.drawable.image_3, R.drawable.image_4, R.drawable.image_5, R.drawable.image_6, R.drawable.image_7, R.drawable.image_8};
    private SliderLayout mDemoSlider;

    LinearLayout home_delivery, events, prepaid, electricity,train_booking,
            dth, broadband, landline, water,moviesLinear,flightLinear,linearBus,carLinear,bikeLinear,
            cabBookingLin,rentLin,birthday_planners, joblin,money_transfer,datacardLn, rawMeat, gasLayout, hotelLayout,insuranceLn;

    RecyclerView service_recyclerview;
    ProgressDialog progressDialog;
    int i=0;
    private ServicesAdapter servicesAdapter;
    private List<ServicesModel> servicesModels;

    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    String login_user="";

    String acmech;
    ApiInterface apiService;

    public static FragmentMain newInstance() {

        return new FragmentMain();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_main, container, false);
        apiService =
                ApiClientGenie.getClient().create(ApiInterface.class);
        mDemoSlider = v.findViewById(R.id.slider);
        home_delivery = v.findViewById(R.id.home_delivery);
        events = v.findViewById(R.id.events);
        prepaid = v.findViewById(R.id.prepaid);
        dth = v.findViewById(R.id.dth);
        broadband = v.findViewById(R.id.broadband);
        landline = v.findViewById(R.id.landline);
        water = v.findViewById(R.id.water);
        electricity = v.findViewById(R.id.electricity);
        train_booking = v.findViewById(R.id.train_booking);
        moviesLinear=v.findViewById(R.id.moviesLinear);
        flightLinear=v.findViewById(R.id.flightLinear);
        linearBus=v.findViewById(R.id.linearBus);
        cabBookingLin=v.findViewById(R.id.cabBookingLin);
        rentLin=v.findViewById(R.id.rentLin);
        money_transfer=v.findViewById(R.id.money_transfer);
        datacardLn=v.findViewById(R.id.datacardLn);
        rawMeat = v.findViewById(R.id.raw_meat);
        gasLayout = v.findViewById(R.id.gasLayout);
        hotelLayout = v.findViewById(R.id.hotelLayout);
        bikeLinear=v.findViewById(R.id.bikeLinear);
        carLinear=v.findViewById(R.id.carLinear);

        birthday_planners=v.findViewById(R.id.birthday_planners);

        insuranceLn=v.findViewById(R.id.insuranceLn);

        joblin=v.findViewById(R.id.joblin);


        sharedpreferences = getActivity().getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedpreferences.edit();
        login_user=sharedpreferences.getString("FLAG", "");
        editor.commit(); // commit changes


        Log.d("login_user", login_user);

        prepaid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //     RegPrefManager.getInstance(MainActivity2.this).setMobileCircle();
                RegPrefManager.getInstance(getActivity()).setMobileOperator("","");
                RegPrefManager.getInstance(getActivity()).setMobileCircle("","");
                RegPrefManager.getInstance(getActivity()).setMobileRechargeAmount("");
                RegPrefManager.getInstance(getActivity()).setPhoneNo("");
                RegPrefManager.getInstance(getActivity()).setSuccessID("");
                Intent intent=(new Intent(getActivity(),MobileRecharge.class));
                // intent.putExtra("MobileId",al.indexOf("13"));
                startActivity(intent);
            }
        });

        events.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegPrefManager.getInstance(getActivity()).setSuccessID("");
                startActivity(new Intent(getActivity(),EventManagement.class));
            }
        });

        electricity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegPrefManager.getInstance(getActivity()).setSuccessID("");
                RegPrefManager.getInstance(getActivity()).SetElectricityBoard("","");
                RegPrefManager.getInstance(getActivity()).setElectricityOperator("","");
                Intent intent=(new Intent(getActivity(),PayForElectricity.class));
                startActivity(intent);
            }
        });

        train_booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegPrefManager.getInstance(getActivity()).setSuccessID("");
                startActivity(new Intent(getActivity(),BookTrain.class));
            }
        });
        home_delivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegPrefManager.getInstance(getActivity()).setSuccessID("");
                startActivity(new Intent(getActivity(), HomeDeliveryGrocery.class));
            }
        });

        broadband.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RegPrefManager.getInstance(getActivity()).setSuccessID("");

                RegPrefManager.getInstance(getActivity()).setLandlineOperator("","");
                RegPrefManager.getInstance(getActivity()).setLandlineCircle("","");

                startActivity(new Intent(getActivity(), LandLine.class));
            }
        });

        landline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RegPrefManager.getInstance(getActivity()).setSuccessID("");
                RegPrefManager.getInstance(getActivity()).setLandlineOperator("","");
                RegPrefManager.getInstance(getActivity()).setLandlineCircle("","");
                startActivity(new Intent(getActivity(), LandLine.class));
            }
        });
        water.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegPrefManager.getInstance(getActivity()).setWaterBoard("","");
                RegPrefManager.getInstance(getActivity()).setSuccessID("");
                startActivity(new Intent(getActivity(), WaterBill.class));
            }
        });
        dth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegPrefManager.getInstance(getActivity()).setDTHOperator("","");
                RegPrefManager.getInstance(getActivity()).setMobileCircle("","");
                RegPrefManager.getInstance(getActivity()).setMobileRechargeAmount("");
                RegPrefManager.getInstance(getActivity()).setCustomerId("");
                RegPrefManager.getInstance(getActivity()).setSuccessID("");
                Intent intent=(new Intent(getActivity(),DTHRecharge.class));
                startActivity(intent);
            }
        });
        moviesLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegPrefManager.getInstance(getActivity()).setSuccessID("");
                startActivity(new Intent(getActivity(),MovieActivity.class));
            }
        });
        flightLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegPrefManager.getInstance(getActivity()).setSuccessID("");
                startActivity(new Intent(getActivity(),FlightActivity.class));
            }
        });
        linearBus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegPrefManager.getInstance(getActivity()).setSuccessID("");
                startActivity(new Intent(getActivity(),BusBookingActivity.class));
            }
        });
        cabBookingLin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegPrefManager.getInstance(getActivity()).setSuccessID("");
                RegPrefManager.getInstance(getActivity()).setCabFromPlace("");
                startActivity(new Intent(getActivity(),CabBookingActivity.class));
            }
        });
        rentLin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegPrefManager.getInstance(getActivity()).setSuccessID("");
                startActivity(new Intent(getActivity(),RentActivity.class));
            }
        });

        birthday_planners.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegPrefManager.getInstance(getActivity()).setSuccessID("");
                startActivity(new Intent(getActivity(),GiftsList.class));
            }
        });

        joblin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegPrefManager.getInstance(getActivity()).setSuccessID("");
                startActivity(new Intent(getActivity(),JobActivity.class));
            }
        });
        money_transfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id= RegPrefManager.getInstance(getActivity()).getRemitterId();
                if(id!=null) {
                    startActivity(new Intent(getActivity(), RemiterDetailsActivity.class));
                }else {
                    startActivity(new Intent(getActivity(), RemiterRegistrationActivity.class));
                }

            }
        });
        datacardLn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegPrefManager.getInstance(getActivity()).setDataCardNo("");
                RegPrefManager.getInstance(getActivity()).setDataCardOperator("","");
                RegPrefManager.getInstance(getActivity()).setDataCardCircle("","");

                startActivity(new Intent(getActivity(),DataCardActivity.class));
            }
        });

        insuranceLn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegPrefManager.getInstance(getActivity()).setSuccessID("");
                startActivity(new Intent(getActivity(),AllInsuranseActivity.class));
            }
        });


        rawMeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegPrefManager.getInstance(getActivity()).setSuccessID("");
                startActivity(new Intent(getActivity(),LoanPaymentActivity.class));
            }
        });

        gasLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegPrefManager.getInstance(getActivity()).setGasBoard("","");
                RegPrefManager.getInstance(getActivity()).setSuccessID("");
                startActivity(new Intent(getActivity(),GasBillActivity.class));
            }
        });

        hotelLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegPrefManager.getInstance(getActivity()).setSuccessID("");
                startActivity(new Intent(getActivity(),HotelActivity.class));
            }
        });
        bikeLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegPrefManager.getInstance(getActivity()).setSuccessID("");
                startActivity(new Intent(getActivity(),ComingSoonActivity.class));

            }
        });
        carLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegPrefManager.getInstance(getActivity()).setSuccessID("");
                startActivity(new Intent(getActivity(),ComingSoonActivity.class));
            }
        });


        service_recyclerview = v.findViewById(R.id.service_recyclerview);

        GridLayoutManager manager = new GridLayoutManager(getActivity(), 1, GridLayoutManager.HORIZONTAL, false);
        service_recyclerview.setLayoutManager(manager);
        progressDialog =new ProgressDialog(getActivity());

        servicesModels = new ArrayList<>();

        service_recyclerview.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), service_recyclerview, new RecyclerTouchListener.ClickListener() {
            @Override
            public boolean onClick(View view, int position) {
                ServicesModel list = servicesModels.get(position);
                String service_id = list.getService_id();
                String service_name = list.getService_name();
                String service_fee = list.getService_fee();
                String service_img = list.getService_img();


                Intent i = new Intent(getActivity(), OrderGenerate.class);
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

        //getServices();

        getNetwork();

        setImagePager();

        return v;

    }


    private void setImagePager() {
        for(int i=0;i<image.length;i++){
            TextSliderView textSliderView = new TextSliderView(getActivity());
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
                servicesAdapter = new ServicesAdapter(servicesModels, getActivity());
                service_recyclerview.setAdapter(servicesAdapter);
            }

            @Override
            public void onFailure(Call<ServiceImage> call, Throwable t) {
                Log.d("Tag","Failure");
            }
        });
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
}
