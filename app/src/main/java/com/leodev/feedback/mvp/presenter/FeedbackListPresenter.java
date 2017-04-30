package com.leodev.feedback.mvp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.leodev.feedback.R;
import com.leodev.feedback.Utils;
import com.leodev.feedback.adapter.FeedListHolder;
import com.leodev.feedback.mvp.model.Feedback;
import com.leodev.feedback.mvp.view.FeedbackListView;

import java.util.LinkedHashSet;
import java.util.Set;

@InjectViewState
public class FeedbackListPresenter extends MvpPresenter<FeedbackListView> {
    private int mChildId;
    private Query mQuery;
    private FirebaseRecyclerAdapter<Feedback, FeedListHolder> mAdapter;
    private ValueEventListener mListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            Set<String> dates = new LinkedHashSet<>();
            for (DataSnapshot x : dataSnapshot.getChildren()){
                dates.add(Utils.getDateFromLong(x.getValue(Feedback.class).getDate()));
            }
            getViewState().setHeaderData(dataSnapshot.getChildrenCount(), dates);
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    public void initDataForHeader(int childId){
        getViewState().showDialog();
        mChildId = childId;
        mQuery = Utils.getFeedbackReference(Utils.getFeedRoot(mChildId));
        mQuery.addValueEventListener(mListener);
    }

    public void showFeedByDate(String date){
        Query query = Utils.getChildByDate(mChildId, date);
        updateAdapter(query);
        getViewState().initRecycler(mAdapter);
    }

    public void showAllFeed(){
        Query query = Utils.getFeedbackReference(Utils.getFeedRoot(mChildId));
        updateAdapter(query);
    }

    private void updateAdapter(Query query){
        mAdapter = new FirebaseRecyclerAdapter<Feedback, FeedListHolder>(
                        Feedback.class, R.layout.feed_item, FeedListHolder.class, query) {
                    @Override
                    protected void populateViewHolder(FeedListHolder viewHolder, Feedback model, int position) {
                        viewHolder.bind(model, position);
                    }
                };
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                getViewState().hideDialog();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        getViewState().initRecycler(mAdapter);
    }

    public void unregisterDataListener(){
        mQuery.removeEventListener(mListener);
    }

}
