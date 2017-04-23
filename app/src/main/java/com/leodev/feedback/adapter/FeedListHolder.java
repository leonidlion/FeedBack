package com.leodev.feedback.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.leodev.feedback.R;
import com.leodev.feedback.mvp.model.Feedback;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FeedListHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_date)
        TextView mDate;
        @BindView(R.id.tv_name)
        TextView mName;
        @BindView(R.id.tv_text)
        TextView mText;

        public FeedListHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(Feedback feedback){
            mDate.setText(feedback.getDate());
            mName.setText(feedback.getName());
            mText.setText(feedback.getText());
        }
}
