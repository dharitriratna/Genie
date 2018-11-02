package com.example.user.genie;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.user.genie.Adapter.ViewPagerAdapter;
import com.example.user.genie.Fragments.AboutEventsFragment;
import com.example.user.genie.Fragments.ShowTimeFragment;
import com.example.user.genie.Fragments.TrailerFragment;

public class EventDetails extends AppCompatActivity {

    private CollapsingToolbarLayout collapsing_toolbar;
    private Toolbar toolbar;
    AppBarLayout appBarLayout;
    private TabLayout events_tabs;
    private ViewPager viewPager;


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        appBarLayout = findViewById(R.id.appbar);
        collapsing_toolbar = findViewById(R.id.collapsing_toolbar);

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = true;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsing_toolbar.setTitle("Event Name");
                    collapsing_toolbar.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);
                    isShow = true;
                } else if (isShow) {
                    collapsing_toolbar.setTitle(" ");//carefull there should a space between double quote otherwise it wont work
                    isShow = false;
                }
            }
        });

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        setupViewPager(viewPager);

        events_tabs = findViewById(R.id.events_tabs);
        events_tabs.setupWithViewPager(viewPager);
        setupTabIcons();
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void setupTabIcons() {

        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tablayout, null);
        tabOne.setText("About");
        tabOne.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        // tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_tab_favourite, 0, 0);
        events_tabs.getTabAt(0).setCustomView(tabOne);

        TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tablayout, null);
        tabTwo.setText("Venue");
        tabTwo.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        // tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_tab_call, 0, 0);
        events_tabs.getTabAt(1).setCustomView(tabTwo);


    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new AboutEventsFragment(), "About");
        adapter.addFragment(new TrailerFragment(), "Trailers & More");

        viewPager.setAdapter(adapter);
    }
}
