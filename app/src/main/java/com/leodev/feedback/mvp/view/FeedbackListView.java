package com.leodev.feedback.mvp.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.firebase.ui.database.FirebaseRecyclerAdapter;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface FeedbackListView extends MvpView {
    void showDialog();
    void hideDialog();
    void initRecycler(FirebaseRecyclerAdapter adapter);
    @StateStrategyType(SkipStrategy.class)
    void showDatePickerDialog();
    void setCountFeed(long childrenCount);
}
