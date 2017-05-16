package com.leodev.feedback.ui.fragments;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.leodev.feedback.R;
import com.leodev.feedback.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SendMessageFragment extends Fragment {
    private static final String ARGS_PAGE = "ARGS_PAGE";
    private int mSmileId;
    private ClickInteractions mCallback;
    @BindView(R.id.feed_header)
    TextView mTextHeader;
    @BindView(R.id.feed_message)
    EditText mMessage;
    @BindView(R.id.feed_boss_image)
    ImageView mBossImage;
    @BindView(R.id.feed_text_hint)
    TextView mTextHint;
    @BindView(R.id.feed_send)
    Button mSend;
    @BindView(R.id.feed_smile_caption)
    TextView mSmileCaption;
    @BindView(R.id.feed_smile)
    ImageView mSmile;

    public interface ClickInteractions {
        void onBackClick();
        void onSendClick(int root, String message);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallback = (ClickInteractions) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    @OnClick({R.id.feed_send, R.id.feed_back_icon, R.id.feed_back_text})
    public void onClick(View view){
        hideKeyboard();
        if (view.getId() == R.id.feed_send){
            String text = mMessage.getText().toString();
            if (TextUtils.isEmpty(text)){
                text = Utils.getEmptyMessage(mSmileId);
            }
            mCallback.onSendClick(mSmileId, text);
        }
        mCallback.onBackClick();
    }

    public static SendMessageFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARGS_PAGE, page);
        SendMessageFragment fragment = new SendMessageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public SendMessageFragment(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feedback, container, false);
        ButterKnife.bind(this, view);

        mSmileId = getArguments().getInt(ARGS_PAGE);
        if (savedInstanceState != null){
            mSmileId = savedInstanceState.getInt(ARGS_PAGE);
        }
        initText();

        showKeyboard();

        return view;
    }

    private void showKeyboard() {
        mMessage.requestFocus();
//        InputMethodManager imm = (InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    private void hideKeyboard(){
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mMessage.getWindowToken(), 0);
    }

    private void initText(){
        if (mSmileId == Utils.ROOT_BAD){
            mBossImage.setVisibility(View.VISIBLE);
        }
        TypedArray imageArray = getResources().obtainTypedArray(R.array.smile_image_arr);
        mTextHeader.setText(getResources().getStringArray(R.array.hint_text_arr)[mSmileId]);
        mTextHint.setText(getResources().getStringArray(R.array.answer_arr)[mSmileId]);
        mSend.setText(getResources().getStringArray(R.array.feed_btn_text)[mSmileId]);
        mSmileCaption.setText(getResources().getStringArray(R.array.smile_description_arr)[mSmileId]);
        mSmile.setImageDrawable(imageArray.getDrawable(mSmileId));
        imageArray.recycle();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(ARGS_PAGE, mSmileId);
    }
}
