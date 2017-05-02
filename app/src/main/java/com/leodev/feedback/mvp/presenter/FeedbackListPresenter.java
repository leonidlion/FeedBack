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

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

@InjectViewState
public class FeedbackListPresenter extends MvpPresenter<FeedbackListView> {
    private static final int ALL_FEED       = 0;
    private static final int WEEK_FEED      = 1;
    private static final int MONTH_FEED     = 2;
    private static final int YEAR_FEED      = 3;
    private static final int CUSTOM_FEED    = 4;

    private Calendar mCalendar;
    private int mChildId;
    private int mSpinnerPosition;
    private long mTimeFrom, mTimeTo;

    public void initDataForHeader(int childId){
        initCalendar();
        getViewState().initSpinner(mSpinnerPosition);
        getViewState().showDialog();
        mChildId = childId;
    }

    public void onItemSelected(int position){
        mSpinnerPosition = position;
        if (position != CUSTOM_FEED){
            resetTimes();
        }
        switch (position){
            case ALL_FEED:
                showAllFeed();
                break;
            case WEEK_FEED:
                showWeekFeeds();
                break;
            case MONTH_FEED:
                showMonthFeeds();
                break;
            case YEAR_FEED:
                showYearFeeds();
                break;
            case CUSTOM_FEED:
                if (mTimeFrom != 0L && mTimeTo != 0L){
                    showFeedByDate(mTimeFrom, mTimeTo);
                }else {
                    getViewState().showDatePickerDialog();
                }
                break;
        }
    }

    public void showFeedByDate(long timeFrom, long timeTo){
        mTimeFrom = timeFrom;
        mTimeTo = timeTo;
        Query query = Utils.getChildByDateRange(
                mChildId,
                timeFrom
                ,timeTo);
        updateAdapter(query);
    }

    // ==== HELPERS =====
    private void initCalendar(){
        mCalendar = Calendar.getInstance(Locale.UK);
        mCalendar.set(Calendar.HOUR_OF_DAY, 0);
        mCalendar.clear(Calendar.MINUTE);
        mCalendar.clear(Calendar.SECOND);
        mCalendar.clear(Calendar.MILLISECOND);
    }

    private void showAllFeed(){
        Query query = Utils.getFeedbackReference(Utils.getFeedRoot(mChildId));
        updateAdapter(query);
    }

    private void showWeekFeeds(){
        mCalendar.set(Calendar.DAY_OF_WEEK, mCalendar.getFirstDayOfWeek());
        Query query = Utils.getChildByDateRange(
                mChildId,
                mCalendar.getTimeInMillis(),
                Calendar.getInstance(Locale.UK).getTimeInMillis());
        updateAdapter(query);
    }

    private void showMonthFeeds(){
        mCalendar.set(Calendar.DAY_OF_MONTH, 1);
        Calendar lastDayOfMonth = Calendar.getInstance();
        lastDayOfMonth.set(Calendar.DATE, lastDayOfMonth.getActualMaximum(Calendar.DATE));
        Query query = Utils.getChildByDateRange(
                mChildId,
                mCalendar.getTimeInMillis(),
                lastDayOfMonth.getTimeInMillis());
        updateAdapter(query);
    }

    private void showYearFeeds(){
        int currentYear = Calendar.getInstance(Locale.UK).get(Calendar.YEAR);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, currentYear);
        calendar.set(Calendar.DAY_OF_YEAR, 1);
        Date startYear = calendar.getTime();

        calendar.set(Calendar.MONTH, Calendar.DECEMBER);
        calendar.set(Calendar.DAY_OF_MONTH, 31);

        Date endYear = calendar.getTime();

        Query query = Utils.getChildByDateRange(
                mChildId,
                startYear.getTime(),
                endYear.getTime());
        updateAdapter(query);
    }

    private void updateAdapter(Query query){
        final FirebaseRecyclerAdapter<Feedback, FeedListHolder> adapter = new FirebaseRecyclerAdapter<Feedback, FeedListHolder>(
                Feedback.class, R.layout.feed_item, FeedListHolder.class, query) {
            @Override
            protected void populateViewHolder(FeedListHolder viewHolder, Feedback model, int position) {
                viewHolder.bind(model, position);
            }
        };
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                getViewState().setCountFeed(dataSnapshot.getChildrenCount());
                getViewState().hideDialog();
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        getViewState().initRecycler(adapter);
    }

    private void resetTimes(){
        mTimeFrom = 0L;
        mTimeTo = 0L;
    }
}
