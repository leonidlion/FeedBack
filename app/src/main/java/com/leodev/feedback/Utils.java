package com.leodev.feedback;


import android.content.Context;
import android.net.ConnectivityManager;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Utils {
    public static final int ROOT_BAD = 0;
    public static final int ROOT_NEUTRAL = 1;
    public static final int ROOT_GOOD = 2;

    public static DatabaseReference getFeedbackReference(String child){
        return FirebaseDatabase.getInstance().getReference().child(child);
    }

    public static String getFeedRoot(int smileId) {
        switch (smileId){
            case ROOT_BAD:
                return "bad_feed";
            case ROOT_NEUTRAL:
                return "neutral_feed";
            case ROOT_GOOD:
                return "good_feed";
            default: return "";
        }
    }

    public static boolean isPlayServiceUpdated(Context context){
        GoogleApiAvailability api = GoogleApiAvailability.getInstance();
        int code = api.isGooglePlayServicesAvailable(context);
        if (code == ConnectionResult.SUCCESS) {
            return true;
        } else {
            new AlertDialog.Builder(context).setMessage(R.string.play_service_out_date).show();
        }
        return false;
    }

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm.getActiveNetworkInfo() == null) {
            Toast.makeText(context, R.string.err_internet_connection, Toast.LENGTH_LONG).show();
        }
        return cm.getActiveNetworkInfo() != null;
    }
}
