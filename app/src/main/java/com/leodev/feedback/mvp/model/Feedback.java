package com.leodev.feedback.mvp.model;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.Map;

public class Feedback implements Parcelable{
    public static final String MAP_DATE = "Date";
    public static final String MAP_NAME = "Name";
    public static final String MAP_TEXT = "Text";

    private String Date;
    private String Name;
    private String Text;

    public Feedback(){}

    protected Feedback(Parcel in) {
        Date = in.readString();
        Name = in.readString();
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

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        this.Date = date;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
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
        dest.writeString(Date);
        dest.writeString(Name);
        dest.writeString(Text);
    }

    public Map<String, Object> getFeedMap(){
        HashMap<String, Object> map = new HashMap<>();
        map.put(MAP_DATE, getDate());
        map.put(MAP_NAME, getName());
        map.put(MAP_TEXT, getText());
        return map;
    }
}
