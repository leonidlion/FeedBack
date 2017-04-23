package com.leodev.feedback.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
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
        Log.d(TAG, "newInstance: ");
        return new FeedbackFragment();
    }

    public FeedbackFragment(){}

    private static final String TAG = "FeedbackFragment";
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        View view = inflater.inflate(R.layout.fragment_smiles, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void showDialog(int smileId) {
        Log.d(TAG, "showDialog: ");
        Bundle args = new Bundle();
        args.putInt(FeedbackDialog.KEY_SMILE, smileId);
        DialogFragment dialog = new FeedbackDialog();
        dialog.setArguments(args);
        dialog.show(getChildFragmentManager(), null);
    }

    @Override
    public void onSendClick(final int idSmile, String name, String text) {
        Log.d(TAG, "onSendClick: ");
        mPresenter.sendDataToDatabase(idSmile, name, text);
    }

    @Override
    public void showMessage(int arrIndexId){
        Log.d(TAG, "showMessage: ");
        new AlertDialog.Builder(getContext())
                .setMessage(getResources().getStringArray(R.array.answer_arr)[arrIndexId])
                .show();
    }
}
