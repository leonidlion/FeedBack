package com.leodev.feedback.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.leodev.feedback.R;
import com.leodev.feedback.Utils;
import com.leodev.feedback.event.EventMainChangeFragment;
import com.leodev.feedback.mvp.model.Feedback;
import com.leodev.feedback.ui.fragments.ChoicerFragment;
import com.leodev.feedback.ui.fragments.SendMessageFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Date;


public class MainActivity extends AppCompatActivity implements SendMessageFragment.ClickInteractions {

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
    public void onBackClick() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onSendClick(int root, String message) {
        Feedback feedback = new Feedback();
        feedback.setDate(new Date().getTime());
        feedback.setText(message);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference(Utils.getFeedRoot(root));
        reference.push().updateChildren(feedback.getFeedMap());
    }
}