package com.leodev.feedback.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.leodev.feedback.ui.fragments.FeedListFragment;


public class FeedPagerAdapter extends FragmentStatePagerAdapter {
    private static final int TAB_COUNT = 3;
    private String[] mPageTitle;

    public FeedPagerAdapter(FragmentManager fm, String[] pageTitle) {
        super(fm);
        mPageTitle = pageTitle;
    }

    @Override
    public Fragment getItem(int position) {
        return FeedListFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return TAB_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mPageTitle[position];
    }
}
