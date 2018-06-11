package com.example.abarno.moviesapp_2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private String mostpopular = "https://api.themoviedb.org/3/movie/popular?api_key=" + BuildConfig.API_KEY;
    private String toprated = "https://api.themoviedb.org/3/movie/top_rated?api_key=" + BuildConfig.API_KEY;
    private String upcoming = "https://api.themoviedb.org/3/movie/upcoming?api_key=" + BuildConfig.API_KEY;
    private TextView networkMessage;
    private Context currentContext;
    private GridView gridView;
    private int id;
    private MovieAdapter movieAdapter = new MovieAdapter();
    private ArrayList<MovieDetails> movieDetailsArrayLists = new ArrayList<>();
    private static final String LIST_STATE = "listState";
    private static final String TAG = MainActivity.class.getName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        networkMessage = findViewById(R.id.network_message);
        currentContext = getApplicationContext();

        gridView = findViewById(R.id.movie_view);
        gridView.setOnItemClickListener(this);

        if (connectionAvailability()) {
            if (savedInstanceState != null) {
                    movieDetailsArrayLists = savedInstanceState.getParcelableArrayList(LIST_STATE);
                    movieAdapter = new MovieAdapter(this, movieDetailsArrayLists);
                    gridView.setAdapter(movieAdapter);
                    Log.d(TAG, "***state restored***");
            } else {
                new FetchMovies(currentContext).execute(mostpopular);
            }

        } else {
            networkMessage.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(LIST_STATE, movieDetailsArrayLists);
        Log.d(TAG, "***save state***");
        super.onSaveInstanceState(outState);
    }



    @Override
    public void onBackPressed() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you want to exit app?");
        builder.setCancelable(true);
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public boolean connectionAvailability() {
        ConnectivityManager cm =
                (ConnectivityManager) currentContext.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.item_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        id = item.getItemId();

        if (id == R.id.mostpopular_button) {
            if (connectionAvailability()) {
                networkMessage.setVisibility(View.INVISIBLE);
                movieDetailsArrayLists.clear();
                new FetchMovies(currentContext).execute(mostpopular);
                movieAdapter.notifyDataSetChanged();
                return true;
            } else {
                networkMessage.setVisibility(View.VISIBLE);
            }
        } else if (id == R.id.highrated_button) {
            if (connectionAvailability()) {
                networkMessage.setVisibility(View.INVISIBLE);
                movieDetailsArrayLists.clear();
                new FetchMovies(currentContext).execute(toprated);
                movieAdapter.notifyDataSetChanged();
                return true;
            } else {
                networkMessage.setVisibility(View.VISIBLE);
            }
        }

        if (id == R.id.upcoming_button) {
            if (connectionAvailability()) {
                networkMessage.setVisibility(View.INVISIBLE);
                movieDetailsArrayLists.clear();
                new FetchMovies(currentContext).execute(upcoming);
                movieAdapter.notifyDataSetChanged();
                return true;
            } else {
                networkMessage.setVisibility(View.VISIBLE);
            }
        } else if (id == R.id.wishlist_button) {
            if (connectionAvailability()) {
                //networkMessage.setVisibility(View.INVISIBLE);
                Intent intent = new Intent(this, WishListActivity.class);
                startActivity(intent);
                return true;
            } else {
                //networkMessage.setVisibility(View.VISIBLE);
                Intent intent = new Intent(this, WishListActivity.class);
                startActivity(intent);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if (connectionAvailability()) {
            networkMessage.setVisibility(View.INVISIBLE);
            Intent intent = new Intent(this, MovieDetailsActivity.class);
            intent.putExtra("MOVIE_DETAILS", (Parcelable) adapterView.getItemAtPosition(i));
            startActivity(intent);
        } else {
            networkMessage.setVisibility(View.VISIBLE);
        }
    }

    private class FetchMovies extends AsyncTask<String, Void, String> {
        public FetchMovies(Context context) {
            currentContext = context;
        }

        @Override
        protected String doInBackground(String... params) {

            URL url = null;
            try {
                url = new URL(params[0]);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            try {
                HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();

                InputStream inputStream = urlConnection.getInputStream();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String response = bufferedReader.readLine();
                bufferedReader.close();

                return response;

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(s);

                JSONArray jsonArray = jsonObject.getJSONArray("results");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object = jsonArray.getJSONObject(i);
                    MovieDetails movieDetails = new MovieDetails();
                    movieDetails.setId(object.getInt("id"));
                    movieDetails.setMovieTitle(object.getString("original_title"));
                    movieDetails.setMovieOverView(object.getString("overview"));
                    movieDetails.setVoteAverage(object.getDouble("vote_average"));
                    movieDetails.setReleaseDate(object.getString("release_date"));
                    movieDetails.setMoviePoster(object.getString("poster_path"));
                    movieDetailsArrayLists.add(movieDetails);
                }

                movieAdapter = new MovieAdapter(MainActivity.this, movieDetailsArrayLists);
                gridView.setAdapter(movieAdapter);


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
