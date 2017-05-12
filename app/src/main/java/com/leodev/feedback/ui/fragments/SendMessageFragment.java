package com.leodev.feedback.ui.fragments;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.leodev.feedback.R;
import com.leodev.feedback.Utils;
import com.leodev.feedback.mvp.model.Feedback;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SendMessageFragment extends Fragment {
    private static final String ARGS_PAGE = "ARGS_PAGE";
    private int mSmileId;
    private OnBackPressedListener mCallback;
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

    public interface OnBackPressedListener{
        void onBackClick();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallback = (OnBackPressedListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    @OnClick({R.id.feed_send, R.id.feed_back_icon, R.id.feed_back_text})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.feed_send:
                String text = mMessage.getText().toString();
                if (TextUtils.isEmpty(text)){
                    text = Utils.getEmptyMessage(mSmileId);
                }
                Feedback feedback = new Feedback();
                feedback.setDate(new Date().getTime());
                feedback.setText(text);

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference reference = database.getReference(Utils.getFeedRoot(mSmileId));
                reference.push().updateChildren(feedback.getFeedMap()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getContext(), "Успішно", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case R.id.feed_back_icon:
                mCallback.onBackClick();
                break;
            case R.id.feed_back_text:
                mCallback.onBackClick();
                break;
        }
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

        return view;
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
