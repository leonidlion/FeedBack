package com.leodev.feedback.ui.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.leodev.feedback.R;
import com.leodev.feedback.mvp.presenter.FeedbackListPresenter;
import com.leodev.feedback.mvp.view.FeedbackListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FeedListFragment extends MvpAppCompatFragment implements FeedbackListView {
    private static final String ARGS_PAGE_TITLE = "KEY_PAGE_TITLE";
    private ProgressDialog mProgressDialog;

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
        return view;
    }

    @Override
    public void setHeaderData(long count, Set<String> arrDates){
        List<String> list = new ArrayList<>();
        list.addAll(arrDates);
        list.add(0, getString(R.string.all_feed));
        mCountText.setText(getString(R.string.count_text, count));
        mSpinner.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, list));
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0){
                    mPresenter.showAllFeed();
                }else {
                    mPresenter.showFeedByDate(parent.getItemAtPosition(position).toString());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.unregisterDataListener();
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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mProgressDialog = new ProgressDialog(getContext());
        mProgressDialog.setMessage(getString(R.string.load_data));
    }
}
