package com.example.abarno.moviesapp_2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MovieAdapter extends BaseAdapter {

    private int i;
    private LayoutInflater inflater;
    private ArrayList<MovieDetails> movieDetailsArrayList;
    private Context context;
    private int resources;

    public MovieAdapter(Context context, ArrayList<MovieDetails> movieDetailsArrayList) {
        this.movieDetailsArrayList = movieDetailsArrayList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return movieDetailsArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return movieDetailsArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        View gridLayout = view;
        MovieDetails details = movieDetailsArrayList.get(position);

        if (view == null){
            inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (inflater != null) {
                gridLayout = inflater.inflate(R.layout.movie_item, null);
            }
        }

        TextView movieName = gridLayout.findViewById(R.id.movie_name);
        ImageView movieImage = gridLayout.findViewById(R.id.movie_image);
     //   TextView movieRate = gridLayout.findViewById(R.id.movie_date);

        movieName.setText(details.getMovieTitle());
     //   movieRate.setText(Double.toString(details.getVoteAverage()));

        Glide.with(context).load("https://image.tmdb.org/t/p/w500/"+details.getMoviePoster()).into(movieImage);

        return gridLayout;
    }
}
