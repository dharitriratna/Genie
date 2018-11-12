package com.example.user.genie;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
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
import com.example.user.genie.Adapter.DTHOperatorAdapter;
import com.example.user.genie.Adapter.EventsAdapter;
import com.example.user.genie.Model.DTHOperatorsModel;
import com.example.user.genie.Model.EventsModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class EventManagement extends AppCompatActivity implements ViewPagerEx.OnPageChangeListener,BaseSliderView.OnSliderClickListener {
    Toolbar toolbar;
    int[] image={ R.drawable.air_ticket_ad, R.drawable.ad_movie, R.drawable.ad_money_transfer, R.drawable.bday_ad};
    private SliderLayout mDemoSlider;
    ProgressDialog progressDialog;
    int i=0;
    private EventsAdapter eventsAdapter;
    private List<EventsModel> eventsModels;
    RecyclerView events_recyclerview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_management);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mDemoSlider = findViewById(R.id.slider);
        events_recyclerview = findViewById(R.id.events_recyclerview);
        progressDialog = new ProgressDialog(this);

        GridLayoutManager manager = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);
        events_recyclerview.setLayoutManager(manager);

        eventsModels = new ArrayList<>();
        getEvents();

        setImagePager();


        events_recyclerview.addOnItemTouchListener(new RecyclerTouchListener(this, events_recyclerview, new RecyclerTouchListener.ClickListener() {
            @Override
            public boolean onClick(View view, int position) {
                EventsModel list = eventsModels.get(position);
                String eventId = list.getEvent_id();
                String eventName = list.getEvent_name();
                String eventPrice = list.getEvent_price();
                String eventImage = list.getEvent_image();

                Intent intent = new Intent(EventManagement.this,EventDetails.class);
                intent.putExtra("EVENT_ID",eventId);
                intent.putExtra("EVENT_NAME",eventName);
                intent.putExtra("EVENT_PRICE",eventPrice);
                intent.putExtra("EVENT_IMAGE",eventImage);
                startActivity(intent);
                finish();

                return true;
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }


    private void setImagePager() {
        for(int i=0;i<image.length;i++){
            TextSliderView textSliderView = new TextSliderView(EventManagement.this);
            // initialize a SliderLayout
            textSliderView.image(image[i])
                    .description("Events")
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


    private void getEvents() {
        progressDialog.setMessage("Loading");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                "http://demo.ratnatechnology.co.in/genie/api/service/getallevent",
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
                                        String event_id = "", event_name = "", event_price="", event_image = "";
                                        JSONObject o = array.getJSONObject(i);

                                        event_id = o.getString("id");
                                        event_name = o.getString("event_name");

                                        event_price = o.getString("price");
                                        event_image = o.getString("image");

                                        EventsModel item = new EventsModel(
                                                event_id,event_name, event_price, event_image);


                                        Log.d("operator_name",event_name);
                                        eventsModels.add(item);

                                    }
                                }
                                else {
                                /*Toast.makeText(getApplicationContext(), "No Data Found",
                                        Toast.LENGTH_LONG).show();*/
                                    events_recyclerview.setVisibility(View.GONE);
                                    // no_orders_text.setVisibility(View.VISIBLE);
                                }
                            }


                            eventsAdapter = new EventsAdapter(eventsModels, getApplicationContext());
                            events_recyclerview.setAdapter(eventsAdapter);


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
                        Toast.makeText(EventManagement.this, "Check your network connection.",
                                Toast.LENGTH_LONG).show();
                    }
                } else
                    Toast.makeText(EventManagement.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(EventManagement.this);
        requestQueue.add(stringRequest);
    }

}
