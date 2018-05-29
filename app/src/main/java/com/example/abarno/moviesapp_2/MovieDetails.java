package com.example.abarno.moviesapp_2;

import android.os.Parcel;
import android.os.Parcelable;

class MovieDetails implements Parcelable{

    private String movieOverView;
    private String movieTitle;
    private String moviePoster;
    private String releaseDate;
    private double voteAverage;
    private int movieId;

    public MovieDetails() {

    }

    public int getId() {
        return movieId;
    }

    public String getMovieOverView() {

        return movieOverView;
    }

    public String getMovieTitle() {

        return movieTitle;
    }

    public String getMoviePoster() {

        return moviePoster;
    }

    public String getReleaseDate() {

        return releaseDate;
    }

    public double getVoteAverage() {

        return voteAverage;
    }


    public static Creator<MovieDetails> getCREATOR() {

        return CREATOR;
    }


    public void setId(int movieId) {
        this.movieId = movieId;
    }

    public void setMovieOverView(String movieOverView) {

        this.movieOverView = movieOverView;
    }

    public void setMovieTitle(String movieTitle) {

        this.movieTitle = movieTitle;
    }

    public void setMoviePoster(String moviePoster) {

        this.moviePoster = moviePoster;
    }

    public void setReleaseDate(String releaseDate) {

        this.releaseDate = releaseDate;
    }

    public void setVoteAverage(double voteAverage) {

        this.voteAverage = voteAverage;
    }

    public MovieDetails(String movieOverView, String movieTitle, String moviePoster, String releaseDate, double voteAverage, int movieId) {
        this.movieOverView = movieOverView;
        this.movieTitle = movieTitle;
        this.moviePoster = moviePoster;
        this.releaseDate = releaseDate;
        this.voteAverage = voteAverage;
        this.movieId = movieId;
    }

    public MovieDetails(Parcel in) {
        movieOverView = in.readString();
        movieTitle = in.readString();
        moviePoster = in.readString();
        releaseDate = in.readString();
        voteAverage = in.readDouble();
        movieId = in.readInt();
    }

    public static final Creator<MovieDetails> CREATOR = new Creator<MovieDetails>() {
        @Override
        public MovieDetails createFromParcel(Parcel in) {

            return new MovieDetails(in);
        }

        @Override
        public MovieDetails[] newArray(int size) {

            return new MovieDetails[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(movieOverView);
        parcel.writeString(movieTitle);
        parcel.writeString(moviePoster);
        parcel.writeString(releaseDate);
        parcel.writeDouble(voteAverage);
        parcel.writeInt(movieId);

    }



}
