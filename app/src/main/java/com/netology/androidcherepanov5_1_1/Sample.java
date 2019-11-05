package com.netology.androidcherepanov5_1_1;


import android.os.Parcel;
import android.os.Parcelable;

class Sample implements Parcelable {
    private String title;
    private String category;


    Sample(String title, String category) {
        this.title = title;
        this.category = category;

    }

    private Sample(Parcel in) {
        title = in.readString();
        category = in.readString();

    }

    public static final Creator<Sample> CREATOR = new Creator<Sample>() {
        @Override
        public Sample createFromParcel(Parcel in) {
            return new Sample(in);
        }

        @Override
        public Sample[] newArray(int size) {
            return new Sample[size];
        }
    };

    String getTitle() {
        return title;
    }

    String getCategory() {
        return category;
    }


    void setTitle(String title) {
        this.title = title;
    }

    void setCategory(String category) {
        this.category = category;
    }



    @Override
    public String toString() {
        return title + ';' + category + ';' + '\n';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(category);

    }
}
