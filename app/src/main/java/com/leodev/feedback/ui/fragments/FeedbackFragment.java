package com.leodev.feedback.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.leodev.feedback.R;
import com.leodev.feedback.Utils;
import com.leodev.feedback.event.EventMainChangeFragment;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class FeedbackFragment extends Fragment {
    @BindView(R.id.ib_bad_smile)
    ImageView mBadSmile;
    @BindView(R.id.ib_neutral_smile)
    ImageView mNeutralSmile;
    @BindView(R.id.ib_good_smile)
    ImageView mGoodSmile;

    @OnClick({R.id.ib_bad_smile, R.id.ib_neutral_smile, R.id.ib_good_smile})
    public void onClick(View view) {
        int smileId;
        switch (view.getId()){
            case R.id.ib_bad_smile:
                smileId = Utils.ROOT_BAD;
                break;
            case R.id.ib_neutral_smile:
                smileId = Utils.ROOT_NEUTRAL;
                break;
            case R.id.ib_good_smile:
                smileId = Utils.ROOT_GOOD;
                break;
            default: smileId = Utils.ROOT_NEUTRAL;
        }
        EventBus.getDefault().post(new EventMainChangeFragment(SendMessageFragment.newInstance(smileId), true));
    }

    public static FeedbackFragment newInstance() {
        return new FeedbackFragment();
    }

    public FeedbackFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_smiles, container, false);
        ButterKnife.bind(this, view);
        initSmiles();
        return view;
    }

    private void initSmiles(){
        Glide.with(this)
                .load(R.drawable.angry).asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
               .into(mBadSmile);
        Glide.with(this)
                .load(R.drawable.sarcastic).asGif()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(mNeutralSmile);
        Glide.with(this)
                .load(R.drawable.clap).asGif()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(mGoodSmile);
    }
}