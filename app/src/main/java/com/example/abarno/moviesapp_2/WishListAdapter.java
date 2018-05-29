package com.example.abarno.moviesapp_2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class WishListAdapter extends CursorAdapter {

    private LayoutInflater inflater;

    public WishListAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {

        return inflater.inflate(R.layout.wishlist_item, viewGroup, false);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView favMovieName = view.findViewById(R.id.fav_movie_name);
        TextView favMovieRating = view.findViewById(R.id.fav_movie_rating);
        ImageView favMovieImage = view.findViewById(R.id.fav_movie_image);

        String movieTitleFetch = cursor.getString(cursor.getColumnIndex(MovieContract.MovieList.MOVIE_NAME));
        Double movieRatingFetch = cursor.getDouble(cursor.getColumnIndex(MovieContract.MovieList.MOVIE_RATING));
        String movieImageFetch = cursor.getString(cursor.getColumnIndex(MovieContract.MovieList.MOVIE_POSTER));

        favMovieName.setText(movieTitleFetch);
        favMovieRating.setText(Double.toString(movieRatingFetch));
        Glide.with(context).load(movieImageFetch).into(favMovieImage);
    }
}
