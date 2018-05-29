package com.example.abarno.moviesapp_2;

import android.os.Parcel;
import android.os.Parcelable;

public class ReviewDetails implements Parcelable {

    private String reviewAuthor;
    private String reviewContent;

    public ReviewDetails() {

    }

    public void setReviewAuthor(String reviewAuthor) {
        this.reviewAuthor = reviewAuthor;
    }

    public void setReviewContent(String reviewContent) {
        this.reviewContent = reviewContent;
    }

    public String getReviewAuthor() {
        return reviewAuthor;
    }

    public String getReviewContent() {
        return reviewContent;
    }

    protected ReviewDetails(Parcel in) {
        reviewAuthor = in.readString();
        reviewContent = in.readString();
    }

    public static final Creator<ReviewDetails> CREATOR = new Creator<ReviewDetails>() {
        @Override
        public ReviewDetails createFromParcel(Parcel in) {
            return new ReviewDetails(in);
        }

        @Override
        public ReviewDetails[] newArray(int size) {
            return new ReviewDetails[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(reviewAuthor);
        parcel.writeString(reviewContent);
    }
}
