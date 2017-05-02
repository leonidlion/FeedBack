package com.leodev.feedback.ui.fragments;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.leodev.feedback.R;
import com.leodev.feedback.Utils;
import com.leodev.feedback.mvp.presenter.FeedbackListPresenter;
import com.leodev.feedback.mvp.view.FeedbackListView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FeedListFragment extends MvpAppCompatFragment implements FeedbackListView {
    private static final String ARGS_PAGE_TITLE = "KEY_PAGE_TITLE";
    private ProgressDialog mProgressDialog;
    private DatePickerDialog mDatePickerDialog;
    private DatePickerDialog.OnDateSetListener mDateDialogListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            final long timeFrom = Utils.getUnixTime(year, month, dayOfMonth);
            getDateDialog(new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    long timeTo = Utils.getUnixTime(year, month, dayOfMonth);
                    mPresenter.showFeedByDate(timeFrom, timeTo);
                }
            }).show();
        }
    };
    private List<String> mSpinnerList = new ArrayList<>();
    private AdapterView.OnItemSelectedListener mSpinnerListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            mPresenter.onItemSelected(position);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    @InjectPresenter
    FeedbackListPresenter mPresenter;

    @BindView(R.id.tv_feed_count)
    TextView mCountText;
    @BindView(R.id.sp_feed_dates)
    Spinner mSpinner;
    @BindView(R.id.rv_list_feed)
    RecyclerView mRecyclerView;

    public static FeedListFragment newInstance(int page){
        Bundle args = new Bundle();
        args.putInt(ARGS_PAGE_TITLE, page);
        FeedListFragment fragment = new FeedListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feed_list_item, container, false);
        ButterKnife.bind(this, view);

        mPresenter.initDataForHeader(getArguments().getInt(ARGS_PAGE_TITLE));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mProgressDialog = new ProgressDialog(getContext());
        mProgressDialog.setMessage(getString(R.string.load_data));

        mDatePickerDialog = getDateDialog(mDateDialogListener);
        mDatePickerDialog.setCancelable(false);
    }

    @Override
    public void initSpinner(int position){
        mSpinnerList.add(getString(R.string.all_feed));
        mSpinnerList.add(getString(R.string.week_feed));
        mSpinnerList.add(getString(R.string.month_feed));
        mSpinnerList.add(getString(R.string.year_feed));
        mSpinnerList.add(getString(R.string.custom_feed));

        mSpinner.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, mSpinnerList));
        mSpinner.setSelection(position);
        mSpinner.setOnItemSelectedListener(mSpinnerListener);
    }

    @Override
    public void showDatePickerDialog() {
        if (mDatePickerDialog != null && !mDatePickerDialog.isShowing()){
            mDatePickerDialog.show();
        }
    }

    private DatePickerDialog getDateDialog(DatePickerDialog.OnDateSetListener listener){
        final Calendar currentCalendar = Calendar.getInstance(Locale.UK);
        DatePickerDialog dialog = new DatePickerDialog(getContext(),
                listener,
                currentCalendar.get(Calendar.YEAR),
                currentCalendar.get(Calendar.MONTH),
                currentCalendar.get(Calendar.DAY_OF_MONTH));
        dialog.setCancelable(false);
        dialog.setButton(DatePickerDialog.BUTTON_NEGATIVE, null, (DialogInterface.OnClickListener) null);
        return dialog;
    }

    @Override
    public void setCountFeed(long childrenCount) {
        mCountText.setText(getString(R.string.count_text, childrenCount));
    }

    @Override
    public void initRecycler(FirebaseRecyclerAdapter adapter) {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.swapAdapter(adapter, true);
    }

    @Override
    public void showDialog() {
        if (mProgressDialog != null && !mProgressDialog.isShowing()){
            mProgressDialog.show();
        }
    }

    @Override
    public void hideDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()){
            mProgressDialog.dismiss();
        }
    }
}
