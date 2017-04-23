package com.leodev.feedback.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leodev.feedback.R;
import com.leodev.feedback.event.EventMainChangeFragment;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChoicerFragment extends Fragment {
    private static final String TAG = "ChoicerFragment";

    @OnClick({R.id.btn_show_feed, R.id.btn_show_list})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_show_feed:
                Log.d(TAG, "onClick: ");
                EventBus.getDefault().post(new EventMainChangeFragment(FeedbackFragment.newInstance(), true));
                break;
            case R.id.btn_show_list:
                EventBus.getDefault().post(new EventMainChangeFragment(FeedbackTabFragment.newInstance(), true));
                break;
        }
    }

    public static ChoicerFragment newInstance() {
        return new ChoicerFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choicer, container, false);
        ButterKnife.bind(this, view);
        return view;
    }
}
