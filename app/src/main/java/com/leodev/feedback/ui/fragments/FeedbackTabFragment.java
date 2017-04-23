package com.leodev.feedback.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leodev.feedback.R;
import com.leodev.feedback.adapter.FeedPagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FeedbackTabFragment extends Fragment {
    @BindView(R.id.vp_feed_list)
    ViewPager mViewPager;
    @BindView(R.id.toolbar_actionbar)
    Toolbar mToolbar;
    @BindView(R.id.tl_feed_tab)
    TabLayout mTabLayout;

    public static FeedbackTabFragment newInstance() {
         return new FeedbackTabFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feed_tab, container, false);
        ButterKnife.bind(this, view);
        initAdapter();
        return view;
    }

    private void initAdapter(){
        FeedPagerAdapter adapter = new FeedPagerAdapter(getChildFragmentManager(),
                getResources().getStringArray(R.array.smile_title));
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(3);
        mTabLayout.setupWithViewPager(mViewPager);
        mToolbar.setTitle(R.string.show_list_fragment);
    }
}
