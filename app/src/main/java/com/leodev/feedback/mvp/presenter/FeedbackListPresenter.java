package com.leodev.feedback.mvp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.Query;
import com.leodev.feedback.R;
import com.leodev.feedback.Utils;
import com.leodev.feedback.adapter.FeedListHolder;
import com.leodev.feedback.mvp.model.Feedback;
import com.leodev.feedback.mvp.view.FeedbackListView;

@InjectViewState
public class FeedbackListPresenter extends MvpPresenter<FeedbackListView> {

    public void loadData(int page) {
        getViewState().showDialog();

        Query query = Utils.getFeedbackReference(Utils.getFeedRoot(page));
        FirebaseRecyclerAdapter<Feedback, FeedListHolder> adapter =
                new FirebaseRecyclerAdapter<Feedback, FeedListHolder>(
                        Feedback.class, R.layout.feed_item, FeedListHolder.class, query) {
            @Override
            protected void populateViewHolder(FeedListHolder viewHolder, Feedback model, int position) {
                viewHolder.bind(model);
                getViewState().hideDialog();
            }
        };

        getViewState().initRecycler(adapter);
    }

}
