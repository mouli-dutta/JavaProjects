package com.myjavaprojects.utility;

import android.os.Parcel;
import android.os.Parcelable;

public class Child implements Parcelable {
    private final String title;
    private final String task;
    private final String code;
    private final String output;

    public Child(String title, String task, String code, String output) {
        this.title = title;
        this.task = task;
        this.code = code;
        this.output = output;
    }

    public String getTitle() {
        return title;
    }

    public String getTask() {
        return task;
    }

    public String getCode() {
        return code;
    }

    public String getOutput() {
        return output;
    }

    protected Child(Parcel in) {
        title = in.readString();
        task = in.readString();
        code = in.readString();
        output = in.readString();
    }

    public static final Creator<Child> CREATOR = new Creator<Child>() {
        @Override
        public Child createFromParcel(Parcel in) {
            return new Child(in);
        }

        @Override
        public Child[] newArray(int size) {
            return new Child[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(task);
        dest.writeString(code);
        dest.writeString(output);
    }
}
