package com.leodev.feedback.ui.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
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
    @BindView(R.id.im_dialog_smile)
    ImageView mSmile;
    @BindView(R.id.et_dialog_name)
    EditText mName;
    @BindView(R.id.et_dialog_text)
    EditText mText;

    public interface FeedbackDialogCallback{
        void onSendClick(int idSmile, String name, String text);
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

    private static final String TAG = "FeedbackDialog";
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Log.d(TAG, "onCreateDialog: ");
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialod_feedback, null);
        ButterKnife.bind(this, view);

        TypedArray imgs = getResources().obtainTypedArray(R.array.smile_res);
        mSmile.setImageResource(imgs.getResourceId(smileId, -1));
        mText.setHint(getResources().getStringArray(R.array.hint_text_arr)[smileId]);

        final AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setView(view)
                .setTitle(getResources().getStringArray(R.array.smile_title)[smileId])
                .setPositiveButton(R.string.send, null)
                .create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface d) {
                Button doneBtn = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                doneBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (checkUserName() && checkText()){
                            mCallback.onSendClick(smileId, mName.getText().toString(),
                                    mText.getText().toString());
                            dialog.dismiss();
                        }
                    }
                });
            }
        });

        imgs.recycle();

        return dialog;
    }

    private boolean checkUserName(){
        if (TextUtils.isEmpty(mName.getText().toString())){
            mName.setError(getString(R.string.required));
            mName.requestFocus();
            return false;
        }
        return true;
    }

    private boolean checkText(){
        if (TextUtils.isEmpty(mText.getText().toString())){
            mText.setError(getString(R.string.required));
            mText.requestFocus();
            return false;
        }
        return true;
    }
}
