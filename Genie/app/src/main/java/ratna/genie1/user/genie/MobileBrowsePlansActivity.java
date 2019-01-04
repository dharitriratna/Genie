package ratna.genie1.user.genie;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import ratna.genie1.user.genie.Adapter.BrowsePlansAdapter;

import ratna.genie1.user.genie.Fragments.BrowsePlanDetailsFragment;
import ratna.genie1.user.genie.Fragments.BrowsePlanDetailsFragment2g;
import ratna.genie1.user.genie.Fragments.BrowsePlanDetailsFragment3g;
import ratna.genie1.user.genie.Fragments.BrowsePlanDetailsFragment4g;
import ratna.genie1.user.genie.Fragments.BrowsePlanDetailsFragmentFt;
import ratna.genie1.user.genie.Fragments.BrowsePlanDetailsFragmentSpecial;
import ratna.genie1.user.genie.ObjectNew.planDescription;
import ratna.genie1.user.genie.client.ApiClientGenie1;
import ratna.genie1.user.genie.client.ApiInterface;
import ratna.genie1.user.genie.helper.RegPrefManager;

import java.util.ArrayList;
import java.util.List;

import ratna.genie1.user.genie.client.ApiClientGenie1;
import ratna.genie1.user.genie.client.ApiInterface;
import ratna.genie1.user.genie.helper.RegPrefManager;

public class MobileBrowsePlansActivity extends AppCompatActivity {
    Toolbar toolbar;
    int i=0;
    ApiInterface apiService;
    private AlertDialog.Builder alertDialog;
    ProgressDialog progressDialog;
    private BrowsePlansAdapter browsePlansAdapter;
    private ArrayList<planDescription> operatorList;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    String login_user="";
    String phoneNumber;
    String operatorName;
    String circleName;
    private TextView planlistTv;
    private String plans,planCircle;

    private TextView txtSeatSelected;
    private TabLayout htab_tabs;
    private ViewPager viewPager;


    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_plans);
        apiService =
                ApiClientGenie1.getClient().create(ApiInterface.class);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MobileBrowsePlansActivity.this,MobileRecharge.class));
                finish();
            }
        });
        planlistTv = findViewById(R.id.planlistTv);
        plans= RegPrefManager.getInstance(getApplicationContext()).getMobileOperatorName();
        planCircle= RegPrefManager.getInstance(getApplicationContext()).getMobileCircleName();
        planlistTv.setText(plans+" "+planCircle+" "+"plans");


        progressDialog = new ProgressDialog(this);

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
        tabOne.setText("Top");
        tabOne.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        htab_tabs.getTabAt(0).setCustomView(tabOne);

        TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tablayout, null);
        tabTwo.setText("Full Talktime");
        tabTwo.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        htab_tabs.getTabAt(1).setCustomView(tabTwo);

        TextView tabThree = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tablayout, null);
        tabThree.setText("Special");
        tabThree.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        htab_tabs.getTabAt(2).setCustomView(tabThree);

        TextView tabFour = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tablayout, null);
        tabFour.setText("2G");
        tabFour.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        htab_tabs.getTabAt(3).setCustomView(tabFour);

        TextView tabFive = (TextView)LayoutInflater.from(this).inflate(R.layout.custom_tablayout, null);
        tabFive.setText("3G");
        tabFive.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        htab_tabs.getTabAt(4).setCustomView(tabFive);

        TextView tabSix = (TextView)LayoutInflater.from(this).inflate(R.layout.custom_tablayout, null);
        tabSix.setText("4G");
        tabSix.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        htab_tabs.getTabAt(5).setCustomView(tabSix);

      /*  TextView tabSeven = (TextView)LayoutInflater.from(this).inflate(R.layout.custom_tablayout, null);
        tabSeven.setText("Validity");
        tabSeven.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        htab_tabs.getTabAt(6).setCustomView(tabSeven);

        TextView tabEight = (TextView)LayoutInflater.from(this).inflate(R.layout.custom_tablayout, null);
        tabEight.setText("Special");
        tabEight.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        htab_tabs.getTabAt(7).setCustomView(tabEight);*/

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new BrowsePlanDetailsFragment(), "Top");
        adapter.addFrag(new BrowsePlanDetailsFragmentFt(), "Full Talktime");
        adapter.addFrag(new BrowsePlanDetailsFragmentSpecial(), "Special");
        adapter.addFrag(new BrowsePlanDetailsFragment2g(), "2G");
        adapter.addFrag(new BrowsePlanDetailsFragment3g(), "3G");
        adapter.addFrag(new BrowsePlanDetailsFragment4g(), "4G");

      /*  adapter.addFrag(new BrowsePlanDetailsFragment(), "SEVEN");
        adapter.addFrag(new BrowsePlanDetailsFragment(), "EIGHT");*/
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
