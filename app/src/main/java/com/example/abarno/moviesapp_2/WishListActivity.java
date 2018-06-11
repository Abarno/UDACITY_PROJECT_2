package com.example.abarno.moviesapp_2;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.ToggleButton;

public class WishListActivity extends AppCompatActivity {

    private ListView listView;
    private WishListAdapter wishListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wish_list);
        listView = findViewById(R.id.movie_view_wishlist);

        wishListAdapter = new WishListAdapter(this, fetchWishList(),0);
        listView.setAdapter(wishListAdapter);
    }

    Cursor fetchWishList() {
        return getContentResolver().query(MovieContract.MovieList.CONTENT_URI,
                null,
                null,
                null,
                null);
    }
}
