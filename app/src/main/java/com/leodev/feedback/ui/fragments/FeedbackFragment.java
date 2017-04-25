package com.leodev.feedback.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.leodev.feedback.R;
import com.leodev.feedback.mvp.presenter.FeedbackFragmentPresenter;
import com.leodev.feedback.mvp.view.FeedbackView;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class FeedbackFragment extends MvpAppCompatFragment implements FeedbackView, FeedbackDialog.FeedbackDialogCallback {
    @InjectPresenter
    FeedbackFragmentPresenter mPresenter;

    @OnClick({R.id.ib_bad_smile, R.id.ib_neutral_smile, R.id.ib_good_smile})
    public void onClick(View view) {
        mPresenter.onClick(view.getId());
    }

    public static FeedbackFragment newInstance() {
        return new FeedbackFragment();
    }

    public FeedbackFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_smiles, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void showDialog(int smileId) {
        Bundle args = new Bundle();
        args.putInt(FeedbackDialog.KEY_SMILE, smileId);
        DialogFragment dialog = new FeedbackDialog();
        dialog.setArguments(args);
        dialog.show(getChildFragmentManager(), null);
    }

    @Override
    public void onSendClick(final int idSmile, String text) {
        mPresenter.sendDataToDatabase(idSmile, text);
    }

    @Override
    public void showMessage(int arrIndexId){
        new AlertDialog.Builder(getContext())
                .setMessage(getResources().getStringArray(R.array.answer_arr)[arrIndexId])
                .show();
    }
}
