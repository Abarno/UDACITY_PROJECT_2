package com.example.abarno.moviesapp_2;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
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

    public MovieAdapter() {

    }

    static class ViewHolder {
        TextView movieName;
        ImageView movieImage;
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
        ViewHolder holder = new ViewHolder();

        if (view == null){
            inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (inflater != null) {
                gridLayout = inflater.inflate(R.layout.movie_item, null);
            }
        }

        holder.movieName = gridLayout.findViewById(R.id.movie_name);
        holder.movieImage = gridLayout.findViewById(R.id.movie_image);

        holder.movieName.setText(details.getMovieTitle());
        Glide.with(context).load("https://image.tmdb.org/t/p/w500/"+details.getMoviePoster()).into(holder.movieImage);

        gridLayout.setTag(holder);

        return gridLayout;
    }
}
