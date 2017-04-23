package com.leodev.feedback.mvp.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface FeedbackView extends MvpView {
    @StateStrategyType(SkipStrategy.class)
    void showMessage(int arrIndexId);

    @StateStrategyType(SkipStrategy.class)
    void showDialog(int viewId);
}
