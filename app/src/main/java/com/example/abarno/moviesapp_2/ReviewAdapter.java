package com.example.abarno.moviesapp_2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ReviewAdapter extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    ArrayList<ReviewDetails> reviewDetailsArrayList;

    public ReviewAdapter(Context context, ArrayList<ReviewDetails> reviewDetailsArrayList) {
        this.context = context;
        this.reviewDetailsArrayList = reviewDetailsArrayList;
    }

    @Override
    public int getCount() {
        return reviewDetailsArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return reviewDetailsArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View listLayout = view;
        ReviewDetails details = reviewDetailsArrayList.get(i);

        if (view == null){
            inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (inflater != null){
                listLayout = inflater.inflate(R.layout.review_item,null);
            }
        }

        TextView authorName = listLayout.findViewById(R.id.review_author);
        TextView content = listLayout.findViewById(R.id.review_content);

        authorName.setText(details.getReviewAuthor());
        content.setText(details.getReviewContent());

        return listLayout;
    }
}
