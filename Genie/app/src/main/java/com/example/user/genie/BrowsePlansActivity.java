package com.example.user.genie;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.user.genie.Adapter.BrowsePlansAdapter;
import com.example.user.genie.Adapter.CustomViewPagerAdapter;
import com.example.user.genie.Adapter.RemiterDetailsCustomAdapter;
import com.example.user.genie.Adapter.ViewPagerAdapter;
import com.example.user.genie.Fragments.BrowsePlanDetailsFragment;
import com.example.user.genie.Fragments.BusSeatCumSleeperFragment;
import com.example.user.genie.Fragments.BusSeatFragment;
import com.example.user.genie.Fragments.BusSleeperFragment;
import com.example.user.genie.Fragments.FragmentMain;
import com.example.user.genie.Model.BeneficiaryDetailsResponse;
import com.example.user.genie.ObjectNew.BrowsePlansResponse;
import com.example.user.genie.ObjectNew.RemiterDetailsResponse;
import com.example.user.genie.ObjectNew.planDescription;
import com.example.user.genie.client.ApiClientGenie1;
import com.example.user.genie.client.ApiInterface;
import com.example.user.genie.helper.RegPrefManager;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BrowsePlansActivity extends AppCompatActivity {
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
                onBackPressed();
            }
        });
        progressDialog = new ProgressDialog(this);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        CustomViewPagerAdapter adapter = new CustomViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        htab_tabs = (TabLayout) findViewById(R.id.htab_tabs);
        htab_tabs.setupWithViewPager(viewPager);
      //  setupTabIcons();
    }

   /* @SuppressLint("NewApi")
    private void setupTabIcons() {

        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tablayout, null);
        tabOne.setText("Full Talktime");
        tabOne.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        htab_tabs.getTabAt(0).setCustomView(tabOne);

        TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tablayout, null);
        tabTwo.setText("Top");
        tabTwo.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        htab_tabs.getTabAt(1).setCustomView(tabTwo);

        TextView tabThree = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tablayout, null);
        tabThree.setText("2G");
        tabThree.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        htab_tabs.getTabAt(2).setCustomView(tabThree);

        TextView tabFour = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tablayout, null);
        tabFour.setText("3G");
        tabFour.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        htab_tabs.getTabAt(3).setCustomView(tabFour);

        TextView tabFive = (TextView)LayoutInflater.from(this).inflate(R.layout.custom_tablayout, null);
        tabFive.setText("4G");
        tabFive.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        htab_tabs.getTabAt(4).setCustomView(tabFive);

        TextView tabSix = (TextView)LayoutInflater.from(this).inflate(R.layout.custom_tablayout, null);
        tabSix.setText("Roaming");
        tabSix.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        htab_tabs.getTabAt(5).setCustomView(tabSix);

        TextView tabSeven = (TextView)LayoutInflater.from(this).inflate(R.layout.custom_tablayout, null);
        tabSeven.setText("Validity");
        tabSeven.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        htab_tabs.getTabAt(6).setCustomView(tabSeven);

        TextView tabEight = (TextView)LayoutInflater.from(this).inflate(R.layout.custom_tablayout, null);
        tabEight.setText("Special");
        tabEight.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        htab_tabs.getTabAt(7).setCustomView(tabEight);

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new BrowsePlanDetailsFragment(), "ONE");
        adapter.addFrag(new BrowsePlanDetailsFragment(), "TWO");
        adapter.addFrag(new BrowsePlanDetailsFragment(), "THREE");
        adapter.addFrag(new BrowsePlanDetailsFragment(), "FOUR");
        adapter.addFrag(new BrowsePlanDetailsFragment(), "FIVE");
        adapter.addFrag(new BrowsePlanDetailsFragment(), "SIX");
        adapter.addFrag(new BrowsePlanDetailsFragment(), "SEVEN");
        adapter.addFrag(new BrowsePlanDetailsFragment(), "EIGHT");
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
*/
}
