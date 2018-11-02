package com.example.user.genie;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.example.user.genie.Adapter.EventsAdapter;
import com.example.user.genie.Model.DTHOperatorsModel;

public class EventManagement extends AppCompatActivity implements ViewPagerEx.OnPageChangeListener,BaseSliderView.OnSliderClickListener {
    Toolbar toolbar;
    int[] image={ R.drawable.air_ticket_ad, R.drawable.ad_movie, R.drawable.ad_money_transfer, R.drawable.bday_ad};
    private SliderLayout mDemoSlider;
    EventsAdapter eventsAdapter;
    String[] bookingId={"1","2","3","4"};
    String[] details={"Date:10.4.2017", "Date:10.4.2017", "Date:10.4.2017", "Date:10.4.2017"};

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

        setImagePager();

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(EventManagement.this);
        events_recyclerview.setLayoutManager(mLayoutManager);
        eventsAdapter = new EventsAdapter(EventManagement.this, bookingId, details);
        events_recyclerview.setAdapter(eventsAdapter);

        events_recyclerview.addOnItemTouchListener(new RecyclerTouchListener(this, events_recyclerview, new RecyclerTouchListener.ClickListener() {
            @Override
            public boolean onClick(View view, int position) {
                Intent intent = new Intent(EventManagement.this,EventDetails.class);
                startActivity(intent);
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
}
