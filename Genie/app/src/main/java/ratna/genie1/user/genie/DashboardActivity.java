package ratna.genie1.user.genie;

import android.annotation.SuppressLint;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ratna.genie1.user.genie.Fragments.BrowsePlanDetailsFragment;
import ratna.genie1.user.genie.Fragments.BrowsePlanDetailsFragment2g;
import ratna.genie1.user.genie.Fragments.BrowsePlanDetailsFragment3g;
import ratna.genie1.user.genie.Fragments.BrowsePlanDetailsFragment4g;
import ratna.genie1.user.genie.Fragments.BrowsePlanDetailsFragmentFt;
import ratna.genie1.user.genie.Fragments.BrowsePlanDetailsFragmentSpecial;
import ratna.genie1.user.genie.Fragments.FSEDashboardFragment;
import ratna.genie1.user.genie.Fragments.RetailerDashboardFragment;

public class DashboardActivity extends AppCompatActivity {

    Toolbar toolbar;
    private TabLayout htab_tabs;
    private ViewPager viewPager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
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
        tabOne.setText("FSE Dashboard");
        tabOne.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        htab_tabs.getTabAt(0).setCustomView(tabOne);
       // htab_tabs.setTabGravity(TabLayout.GRAVITY_CENTER);

        TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tablayout, null);
        tabTwo.setText("Retailer Dashboard");
        tabTwo.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        htab_tabs.getTabAt(1).setCustomView(tabTwo);
       // htab_tabs.setTabGravity(TabLayout.GRAVITY_CENTER);

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFrag(new FSEDashboardFragment(), "FSE Dashboard");
        viewPagerAdapter.addFrag(new RetailerDashboardFragment(), "Retailer Dashboard");


      /*  adapter.addFrag(new BrowsePlanDetailsFragment(), "SEVEN");
        adapter.addFrag(new BrowsePlanDetailsFragment(), "EIGHT");*/
        viewPager.setAdapter(viewPagerAdapter);
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
