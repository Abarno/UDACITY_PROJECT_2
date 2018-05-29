package com.example.abarno.moviesapp_2;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.VideoView;

import com.bumptech.glide.Glide;

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
import java.util.Objects;

import javax.net.ssl.HttpsURLConnection;

import static android.app.PendingIntent.getActivity;
import static android.widget.Toast.LENGTH_LONG;

public class MovieDetailsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private ImageView movieImage;
    private TextView movieTitle;
    private TextView movieVote;
    private TextView movieDate;
    private TextView movieOverview;
    private ArrayList<TrailerDetails> trailerList = new ArrayList<>();
    private ToggleButton button;
    private Context currentContext;
    private Context context;
    private GridView gridView;
    private ListView listView;
    private TrailerAdapter trailerAdapter;
    private ReviewAdapter reviewAdapter;
    private ArrayList<ReviewDetails> reviewDetailsArrayList = new ArrayList<>();
    private MovieDetails details;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        currentContext  = getApplicationContext();
        movieImage = findViewById(R.id.movie_image_details);
        movieTitle = findViewById(R.id.movie_title_details);
        movieVote = findViewById(R.id.movie_vote);
        movieDate = findViewById(R.id.movie_date);
        movieOverview = findViewById(R.id.movie_overview);
        button = findViewById(R.id.wishlist_button);
        listView = findViewById(R.id.review_view);
        gridView = findViewById(R.id.trailer_view);

        gridView.setOnItemClickListener(this);

        SharedPreferences sharedPrefs = getSharedPreferences("com.example.abarno.moviesapp_2", MODE_PRIVATE);
        button.setChecked(sharedPrefs.getBoolean("NameOfMovieToSave", true));


        details = (MovieDetails) Objects.requireNonNull(Objects.requireNonNull(getIntent().getExtras()).getParcelable("MOVIE_DETAILS"));

        if (details != null){
            Glide.with(this).load("https://image.tmdb.org/t/p/w500/"+details.getMoviePoster()).into(movieImage);
            movieTitle.setText("TITLE : "+details.getMovieTitle());
            movieVote.setText("RATING : "+Double.toString(details.getVoteAverage()));
            movieDate.setText("RELEASE : " + details.getReleaseDate());
            movieOverview.setText(details.getMovieOverView());

        }
        String videoURL = "https://api.themoviedb.org/3/movie/"+Integer.toString(details.getId())+"/videos?api_key=d9ac31413f412f924a99457ae2b87cf7&language=en-US";
        new FetchTrailers(currentContext).execute(videoURL);

        String reviewURL = "https://api.themoviedb.org/3/movie/"+Integer.toString(details.getId())+"/reviews?api_key=d9ac31413f412f924a99457ae2b87cf7&language=en-US";
        new FetchReviews(currentContext).execute(reviewURL);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        TrailerDetails trailerDetails;
        trailerDetails = trailerList.get(i);
        String key = trailerDetails.getMovieTrailerKey();
        Intent intent1 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v="+key));
        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent1);

    }

    public void favouriteList(View view) {

        if(button.isChecked()) {

            SharedPreferences.Editor editor = getSharedPreferences("com.example.abarno.moviesapp_2", MODE_PRIVATE).edit();
            editor.putBoolean("NameOfMovieToSave", true);
            editor.apply();
            view.setBackgroundResource(R.drawable.ic_favorite_black_24dp);

            String movieNameFav = details.getMovieTitle();
            String moviePosterPath = details.getMoviePoster();
            Double movieRatingFav = details.getVoteAverage();

            ContentValues contentValues = new ContentValues();

            contentValues.put(MovieContract.MovieList.MOVIE_NAME, movieNameFav);
            contentValues.put(MovieContract.MovieList.MOVIE_POSTER, moviePosterPath);
            contentValues.put(MovieContract.MovieList.MOVIE_RATING, movieRatingFav);

            Uri uri = getContentResolver().insert(MovieContract.MovieList.CONTENT_URI, contentValues);

            if(uri != null) {
                Toast.makeText(MovieDetailsActivity.this, uri.toString(), LENGTH_LONG).show();
            }
        }
        else {
            SharedPreferences.Editor editor = getSharedPreferences("com.example.abarno.moviesapp_2", MODE_PRIVATE).edit();
            editor.putBoolean("NameOfMovieToSave", false);
            editor.apply();
            view.setBackgroundResource(R.drawable.ic_favorite_border_black_24dp);
        }
    }

    private class FetchTrailers extends AsyncTask<String, Void, String> {
        public FetchTrailers(Context context) {
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

                for (int i = 0; i<jsonArray.length(); i++){
                    JSONObject object = jsonArray.getJSONObject(i);
                    TrailerDetails trailerDetails = new TrailerDetails();
                    trailerDetails.setMovieTrailerId(object.getString("name"));
                    trailerDetails.setMovieTrailerKey(object.getString("key"));
                    trailerList.add(trailerDetails);
                }

                trailerAdapter = new TrailerAdapter(MovieDetailsActivity.this,trailerList);
                gridView.setAdapter(trailerAdapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private class FetchReviews extends AsyncTask<String, Void, String> {
        public FetchReviews(Context context) {
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

                for (int i = 0; i<jsonArray.length(); i++){
                    JSONObject object = jsonArray.getJSONObject(i);
                    ReviewDetails reviewDetails = new ReviewDetails();
                    reviewDetails.setReviewAuthor(object.getString("author"));
                    reviewDetails.setReviewContent(object.getString("content"));
                    reviewDetailsArrayList.add(reviewDetails);
                }

                reviewAdapter = new ReviewAdapter(MovieDetailsActivity.this,reviewDetailsArrayList);
                listView.setAdapter(reviewAdapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
