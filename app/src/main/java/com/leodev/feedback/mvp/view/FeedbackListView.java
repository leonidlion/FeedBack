package com.leodev.feedback.mvp.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.firebase.ui.database.FirebaseRecyclerAdapter;

import java.util.Set;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface FeedbackListView extends MvpView {
    void showDialog();
    void hideDialog();
    void initRecycler(FirebaseRecyclerAdapter adapter);
    void setHeaderData(long count, Set<String> arrDates);
}
