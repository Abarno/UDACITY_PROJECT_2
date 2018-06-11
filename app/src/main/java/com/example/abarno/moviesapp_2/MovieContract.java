package com.example.abarno.moviesapp_2;

import android.net.Uri;
import android.provider.BaseColumns;

public final class MovieContract {

    public static final String AUTHORITY = "com.example.abarno.moviesapp_2";

    static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public static final String PATH = "movies";

    public static final class MovieList implements BaseColumns{

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH).build();

        public static final String TABLE_NAME = "wishList";
        public static final String MOVIE_ID = "movieId";
        public static final String MOVIE_NAME = "movieName";
        public static final String MOVIE_POSTER = "moviePoster";
        public static final String MOVIE_RATING = "movieRating";
        public static final String MOVIE_WISHLIST = "wishListValue";

    }
}
