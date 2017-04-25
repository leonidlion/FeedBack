package com.leodev.feedback.ui.fragments;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.leodev.feedback.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class FeedbackDialog extends DialogFragment {
    private FeedbackDialogCallback mCallback;
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

        mText.setHint(getResources().getStringArray(R.array.hint_text_arr)[smileId]);

        final AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setView(view)
                .create();

        dialog.getWindow().setBackgroundDrawable( new ColorDrawable(android.graphics.Color.TRANSPARENT));

        initClickListeners(dialog);

        return dialog;
    }

    private void initClickListeners(final AlertDialog dialog){
        mSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(mText.getText().toString())){
                    mCallback.onSendClick(smileId, mText.getText().toString());
                }
                dialog.dismiss();
            }
        });
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        mCloseDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}
