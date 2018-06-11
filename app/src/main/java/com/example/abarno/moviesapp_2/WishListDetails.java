package com.example.abarno.moviesapp_2;

import android.os.Parcel;
import android.os.Parcelable;

public class WishListDetails implements Parcelable {


    private int favMovieId;
    private String favMovieName;
    private String favMoviePoster;
    private double favMovieRating;

    public void setFavMovieId(int favMovieId) {
        this.favMovieId = favMovieId;
    }

    public void setFavMovieName(String favMovieName) {
        this.favMovieName = favMovieName;
    }

    public void setFavMoviePoster(String favMoviePoster) {
        this.favMoviePoster = favMoviePoster;
    }

    public void setFavMovieRating(double favMovieRating) {
        this.favMovieRating = favMovieRating;
    }

    public int getFavMovieId() {

        return favMovieId;
    }

    public String getFavMovieName() {
        return favMovieName;
    }

    public String getFavMoviePoster() {
        return favMoviePoster;
    }

    public double getFavMovieRating() {
        return favMovieRating;
    }

    protected WishListDetails(Parcel in) {
        favMovieId = in.readInt();
        favMovieName = in.readString();
        favMoviePoster = in.readString();
        favMovieRating = in.readDouble();
    }

    public static final Creator<WishListDetails> CREATOR = new Creator<WishListDetails>() {
        @Override
        public WishListDetails createFromParcel(Parcel in) {
            return new WishListDetails(in);
        }

        @Override
        public WishListDetails[] newArray(int size) {
            return new WishListDetails[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(favMovieId);
        parcel.writeString(favMovieName);
        parcel.writeString(favMoviePoster);
        parcel.writeDouble(favMovieRating);
    }
}
