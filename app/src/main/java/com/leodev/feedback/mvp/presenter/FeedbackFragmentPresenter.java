package com.leodev.feedback.mvp.presenter;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.leodev.feedback.R;
import com.leodev.feedback.Utils;
import com.leodev.feedback.mvp.model.Feedback;
import com.leodev.feedback.mvp.view.FeedbackView;

import java.util.Date;

@InjectViewState
public class FeedbackFragmentPresenter extends MvpPresenter<FeedbackView> {

    public void onClick(int viewId) {
        int smileId;
        switch (viewId){
            case R.id.ib_bad_smile:
                smileId = Utils.ROOT_BAD;
                break;
            case R.id.ib_neutral_smile:
                smileId = Utils.ROOT_NEUTRAL;
                break;
            case R.id.ib_good_smile:
                smileId = Utils.ROOT_GOOD;
                break;
            default: smileId = 0;
        }
        getViewState().showDialog(smileId);
    }

    public void sendDataToDatabase(final int idSmile, String text) {
        if (TextUtils.isEmpty(text)){
            text = Utils.getEmptyMessage(idSmile);
        }
        Feedback feedback = new Feedback();
        feedback.setDate(new Date().getTime());
        feedback.setText(text);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference(Utils.getFeedRoot(idSmile));
        reference.push().updateChildren(feedback.getFeedMap()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful() && task.isComplete()){
                    getViewState().showMessage(idSmile);
                }
            }
        });
    }
}
