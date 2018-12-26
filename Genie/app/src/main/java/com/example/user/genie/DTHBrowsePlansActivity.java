package com.example.user.genie;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.user.genie.Fragments.BrowsePlanDetailsFragment;
import com.example.user.genie.Fragments.BrowsePlanDetailsFragment2g;
import com.example.user.genie.Fragments.BrowsePlanDetailsFragment3M;
import com.example.user.genie.Fragments.BrowsePlanDetailsFragment3g;
import com.example.user.genie.Fragments.BrowsePlanDetailsFragment4g;
import com.example.user.genie.Fragments.BrowsePlanDetailsFragment6M;
import com.example.user.genie.Fragments.BrowsePlanDetailsFragmentAP;
import com.example.user.genie.Fragments.BrowsePlanDetailsFragmentFt;
import com.example.user.genie.Fragments.BrowsePlanDetailsFragmentM;
import com.example.user.genie.Fragments.BrowsePlanDetailsFragmentSpecial;
import com.example.user.genie.helper.RegPrefManager;

import java.util.ArrayList;
import java.util.List;

public class DTHBrowsePlansActivity extends AppCompatActivity {
    private Toolbar toolbar;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    String login_user="";
    String phoneNumber;
    String operatorName;
    String circleName;
    private TextView planlistTv;
    private String plans;

    private TextView txtSeatSelected;
    private TabLayout htab_tabs;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dthbrowse_plans);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),DTHRecharge.class));
                finish();
            }
        });

        planlistTv = findViewById(R.id.planlistTv);
        plans= RegPrefManager.getInstance(getApplicationContext()).getDTHOperatorName();
        planlistTv.setText(plans);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        /*ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);*/
        setupViewPager(viewPager);

        htab_tabs = (TabLayout) findViewById(R.id.htab_tabs);
        htab_tabs.setupWithViewPager(viewPager);
        setupTabIcons();
     }


    @SuppressLint("NewApi")
    private void setupTabIcons() {

        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tablayout, null);
        tabOne.setText("Monthly Packs");
        tabOne.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        htab_tabs.getTabAt(0).setCustomView(tabOne);

        TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tablayout, null);
        tabTwo.setText("3 Month Packs");
        tabTwo.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        htab_tabs.getTabAt(1).setCustomView(tabTwo);

        TextView tabThree = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tablayout, null);
        tabThree.setText("6 Month Packs");
        tabThree.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        htab_tabs.getTabAt(2).setCustomView(tabThree);

        TextView tabFour = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tablayout, null);
        tabFour.setText("Annual Packs");
        tabFour.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        htab_tabs.getTabAt(3).setCustomView(tabFour);
     }

    private void setupViewPager(ViewPager viewPager) {
        DTHBrowsePlansActivity.ViewPagerAdapter adapter = new DTHBrowsePlansActivity.ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new BrowsePlanDetailsFragmentM(), "Monthly Packs");
        adapter.addFrag(new BrowsePlanDetailsFragment3M(), "3 Month Packs");
        adapter.addFrag(new BrowsePlanDetailsFragment6M(), "6 Month Packs");
        adapter.addFrag(new BrowsePlanDetailsFragmentAP(), "Annual Packs");

        viewPager.setAdapter(adapter);
     }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
