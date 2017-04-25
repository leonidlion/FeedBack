package com.leodev.feedback.ui.fragments;

import android.app.Dialog;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.leodev.feedback.R;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;


public class FeedbackDialog extends DialogFragment {
    private static final int AUTO_CLOSE_TIME = 10 * 1000;
    private AlertDialog mAlertDialog;
    private FeedbackDialogCallback mCallback;
    private View.OnClickListener mDismissListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mAlertDialog.dismiss();
        }
    };
    private int smileId;

    public static final String KEY_SMILE = "KEY_SMILE_ID";

    @BindView(R.id.et_dialog_text)
    EditText mText;
    @BindView(R.id.btn_dialog_send)
    Button mSend;
    @BindView(R.id.btn_dialog_cancel)
    Button mCancel;
    @BindView(R.id.iv_dialog_close)
    ImageView mCloseDialog;


    public interface FeedbackDialogCallback{
        void onSendClick(int idSmile, String text);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCallback = (FeedbackDialogCallback) getParentFragment();

        if (savedInstanceState != null){
            smileId = savedInstanceState.getInt(KEY_SMILE);
        }else {
            smileId = getArguments().getInt(KEY_SMILE);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_SMILE, smileId);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialod_feedback, null);
        ButterKnife.bind(this, view);

        mAlertDialog = new AlertDialog.Builder(getContext())
                .setView(view)
                .create();
        mAlertDialog.getWindow().setBackgroundDrawable( new ColorDrawable(android.graphics.Color.TRANSPARENT));

        mText.setHint(getResources().getStringArray(R.array.hint_text_arr)[smileId]);

        initAutoCloseTimer();
        initClickListeners();

        return mAlertDialog;
    }

    @Override
    public void onResume() {
        Window window = getDialog().getWindow();
        Point size = new Point();
        Display display = window.getWindowManager().getDefaultDisplay();
        display.getSize(size);
        window.setLayout((int) (size.x * 0.90), WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        super.onResume();
    }

    private void initClickListeners(){
        mSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(mText.getText().toString())){
                    mCallback.onSendClick(smileId, mText.getText().toString());
                }
                mAlertDialog.dismiss();
            }
        });
        mCancel.setOnClickListener(mDismissListener);
        mCloseDialog.setOnClickListener(mDismissListener);
    }

    private void initAutoCloseTimer(){
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (mAlertDialog.isShowing() &&
                        TextUtils.isEmpty(mText.getText().toString())){
                    mAlertDialog.dismiss();
                    timer.cancel();
                }
            }
        }, AUTO_CLOSE_TIME);
    }
}
