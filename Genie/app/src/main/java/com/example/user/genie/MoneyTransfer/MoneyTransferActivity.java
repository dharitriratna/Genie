package com.example.user.genie.MoneyTransfer;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.user.genie.Adapter.ViewPagerAdapter;
import com.example.user.genie.Fragments.IMPSFragment;
import com.example.user.genie.Fragments.ShowTimeFragment;
import com.example.user.genie.Fragments.TrailerFragment;
import com.example.user.genie.Fragments.UPIFragment;
import com.example.user.genie.Fragments.WalletFragment;
import com.example.user.genie.MainActivity;
import com.example.user.genie.R;

public class MoneyTransferActivity extends AppCompatActivity {
    Toolbar toolbar;
    private TabLayout htab_tabs;
    private ViewPager viewPager;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money_transfer);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //onBackPressed();
                startActivity(new Intent(MoneyTransferActivity.this,MainActivity.class));
                finish();
            }
        });
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        setupViewPager(viewPager);

        htab_tabs = (TabLayout) findViewById(R.id.htab_tabs);
        htab_tabs.setupWithViewPager(viewPager);
        setupTabIcons();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void setupTabIcons() {

        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tablayout, null);
        tabOne.setText("UPI");

        tabOne.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        // tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_tab_favourite, 0, 0);
        htab_tabs.getTabAt(1).setCustomView(tabOne);

        TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tablayout, null);
        tabTwo.setText("Wallet");

        tabTwo.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        // tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_tab_call, 0, 0);
        htab_tabs.getTabAt(2).setCustomView(tabTwo);

        TextView tabthree = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tablayout, null);
        tabthree.setText("BANK");

        tabthree.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        // tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_tab_call, 0, 0);
        htab_tabs.getTabAt(0).setCustomView(tabthree);


    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new IMPSFragment(), "BANK");
        adapter.addFragment(new UPIFragment(), "UPI");
        adapter.addFragment(new WalletFragment(), "Wallet");
        viewPager.setAdapter(adapter);
    }
}
