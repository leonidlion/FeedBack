package com.leodev.feedback.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.leodev.feedback.R;
import com.leodev.feedback.Utils;
import com.leodev.feedback.mvp.model.Feedback;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FeedListHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_number)
        TextView mNumber;
        @BindView(R.id.tv_date)
        TextView mDate;
        @BindView(R.id.tv_time)
        TextView mTime;
        @BindView(R.id.tv_description)
        TextView mText;

        public FeedListHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(Feedback feedback, int position){
            mNumber.setText(String.valueOf(++position));
            mDate.setText(Utils.getDateFromLong(feedback.getDate()));
            mTime.setText(Utils.getTimeFromLong(feedback.getDate()));
            mText.setText(feedback.getText());
        }
}
