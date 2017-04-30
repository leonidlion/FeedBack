package com.leodev.feedback.mvp.model;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.Map;

public class Feedback implements Parcelable{
    private static final String MAP_DATE = "Date";
    private static final String MAP_TEXT = "Text";

    private long Date;
    private String Text;

    public Feedback(){}

    protected Feedback(Parcel in) {
        Date = in.readLong();
        Text = in.readString();
    }

    public static final Creator<Feedback> CREATOR = new Creator<Feedback>() {
        @Override
        public Feedback createFromParcel(Parcel in) {
            return new Feedback(in);
        }

        @Override
        public Feedback[] newArray(int size) {
            return new Feedback[size];
        }
    };

    public long getDate() {
        return Date;
    }

    public void setDate(long date) {
        this.Date = date;
    }

    public String getText() {
        return Text;
    }

    public void setText(String text) {
        this.Text = text;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(Date);
        dest.writeString(Text);
    }

    public Map<String, Object> getFeedMap(){
        HashMap<String, Object> map = new HashMap<>();
        map.put(MAP_DATE, getDate());
        map.put(MAP_TEXT, getText());
        return map;
    }
}
