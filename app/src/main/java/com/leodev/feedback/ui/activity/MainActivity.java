package com.leodev.feedback.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.leodev.feedback.R;
import com.leodev.feedback.event.EventMainChangeFragment;
import com.leodev.feedback.ui.fragments.ChoicerFragment;
import com.leodev.feedback.ui.fragments.SendMessageFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;


public class MainActivity extends AppCompatActivity implements SendMessageFragment.OnBackPressedListener{

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setupFullScreen();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportFragmentManager().findFragmentById(R.id.fl_main_container) == null) {
            changeFragment(new EventMainChangeFragment(ChoicerFragment.newInstance(), false));
        }
    }

    private void setupFullScreen(){
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    protected void onStart(){
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this);
        }
    }

    @Subscribe
    public void changeFragment(EventMainChangeFragment changeFragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fl_main_container, changeFragment.getFragment());
        if(changeFragment.isBackStack()){
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count == 0){
            super.onBackPressed();
        } else {
            getSupportFragmentManager().popBackStack();
        }
    }

    @Override
    public void onBackClick() {
        getSupportFragmentManager().popBackStack();
    }
}
