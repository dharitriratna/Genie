package com.example.user.genie.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.user.genie.Fragments.BrowsePlanDetailsFragment;

public class CustomViewPagerAdapter extends FragmentPagerAdapter {

    private String title[] = {"Top"};

    public CustomViewPagerAdapter(FragmentManager manager) {
        super(manager);
    }

    @Override
    public Fragment getItem(int position) {
        return BrowsePlanDetailsFragment.getInstance(position);
    }

    @Override
    public int getCount() {
        return title.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }
}
