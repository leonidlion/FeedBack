package com.leodev.feedback.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.leodev.feedback.R;
import com.leodev.feedback.event.EventMainChangeFragment;
import com.leodev.feedback.ui.activity.AuthActivity;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChoicerFragment extends Fragment {
    private static final int AUTH_REQUEST = 1001;
    private static final int RESULT_OK = -1;

    @OnClick({R.id.btn_show_feed, R.id.btn_show_list})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_show_feed:
                EventBus.getDefault()
                        .post(new EventMainChangeFragment(FeedbackFragment.newInstance(), true));
                break;
            case R.id.btn_show_list:
                if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                    EventBus.getDefault()
                            .post(new EventMainChangeFragment(FeedbackTabFragment.newInstance(), true));
                }else {
                    startActivityForResult(new Intent(getContext(), AuthActivity.class), AUTH_REQUEST);
                }
               /* EventBus.getDefault()
                        .post(new EventMainChangeFragment(FeedbackTabFragment.newInstance(), true));*/
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if ((requestCode == AUTH_REQUEST) &&
            (resultCode == RESULT_OK)){
                EventBus.getDefault().post(new EventMainChangeFragment(FeedbackTabFragment.newInstance(), true));
        }
    }
}
