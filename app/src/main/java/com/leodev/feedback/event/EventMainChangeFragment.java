package com.leodev.feedback.event;


import android.support.v4.app.Fragment;

public class EventMainChangeFragment {
    private Fragment mFragment;
    private boolean mBackStack;

    public EventMainChangeFragment(Fragment fragment, boolean toBackStack){
        mFragment = fragment;
        mBackStack = toBackStack;
    }

    public Fragment getFragment(){
        return mFragment;
    }

    public boolean isBackStack() {
        return mBackStack;
    }
}
