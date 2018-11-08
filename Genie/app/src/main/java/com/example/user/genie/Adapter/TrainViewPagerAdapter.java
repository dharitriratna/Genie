package com.example.user.genie.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.user.genie.Fragments.YourFragment;

public class TrainViewPagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    Fragment fragment = null;

    public TrainViewPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        for (int i = 0; i < mNumOfTabs ; i++) {
            if (i == position) {
                fragment = YourFragment.newInstance();
                break;
            }
        }
        return fragment;

    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return super.getPageTitle(position);
    }
}
