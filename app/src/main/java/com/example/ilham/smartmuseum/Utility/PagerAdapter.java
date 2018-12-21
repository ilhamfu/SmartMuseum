package com.example.ilham.smartmuseum.Utility;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


import com.example.ilham.smartmuseum.AboutFragment;



public class PagerAdapter extends FragmentPagerAdapter {

    private final int MAX_PAGE = 3;

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override

    public Fragment getItem(int i) {
        AboutFragment aboutFragment = new AboutFragment();
        aboutFragment.changeCurrentItem(i);
        return aboutFragment;
    }

    @Override
    public int getCount() {
        return MAX_PAGE;
    }
}
