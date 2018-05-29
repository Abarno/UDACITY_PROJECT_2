package com.example.abarno.moviesapp_2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class TrailerAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private ArrayList<TrailerDetails> trailerAdapterArrayList;
    private Context context;

    public TrailerAdapter(Context context,ArrayList<TrailerDetails> trailerAdapterArrayList) {
        this.trailerAdapterArrayList = trailerAdapterArrayList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return trailerAdapterArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return trailerAdapterArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View gridLayout = view;
        TrailerDetails details = trailerAdapterArrayList.get(i);

        if (view == null){
            inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (inflater != null) {
                gridLayout = inflater.inflate(R.layout.trailer_item, null);
            }
        }

        TextView trailerName = gridLayout.findViewById(R.id.trailer_name);

        trailerName.setText(details.getMovieTrailerId());

        return gridLayout;
    }
}
