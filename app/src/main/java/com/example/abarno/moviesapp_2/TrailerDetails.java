package com.example.abarno.moviesapp_2;

import android.os.Parcel;
import android.os.Parcelable;

public class TrailerDetails implements Parcelable {

    private String movieTrailerId;
    private String movieTrailerKey;

    public String getMovieTrailerId() {
        return movieTrailerId;
    }

    public String getMovieTrailerKey() {
        return movieTrailerKey;
    }

    public void setMovieTrailerId(String movieTrailerId) {
        this.movieTrailerId = movieTrailerId;
    }

    public void setMovieTrailerKey(String movieTrailerKey) {
        this.movieTrailerKey = movieTrailerKey;
    }

    public TrailerDetails() {

    }

    protected TrailerDetails(Parcel in) {
        movieTrailerId = in.readString();
        movieTrailerKey = in.readString();
    }

    public static final Creator<TrailerDetails> CREATOR = new Creator<TrailerDetails>() {
        @Override
        public TrailerDetails createFromParcel(Parcel in) {
            return new TrailerDetails(in);
        }

        @Override
        public TrailerDetails[] newArray(int size) {
            return new TrailerDetails[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(movieTrailerId);
        parcel.writeString(movieTrailerKey);
    }
}
