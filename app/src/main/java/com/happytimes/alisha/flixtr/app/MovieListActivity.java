package com.happytimes.alisha.flixtr.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.happytimes.alisha.flixtr.R;
import com.happytimes.alisha.flixtr.helper.JacksonRequest;
import com.happytimes.alisha.flixtr.helper.VolleySingleton;
import com.happytimes.alisha.flixtr.model.Movie;
import com.happytimes.alisha.flixtr.model.MovieCollection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * An activity representing a list of Movies. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link MovieDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class MovieListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    public static boolean mTwoPane;
    RecyclerView recyclerView;
    MovieAdapter movieAdapter;
    List<Movie> moviesList = new ArrayList<>();

    private static final String TAG = MovieListActivity.class.getSimpleName();
    public static final String MOVIE_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=";
    public static final String API_KEY = "a07e22bc18f5cb106bfe4cc1f83ad8ed";
    //public static final String PAGE_NUMBER = "/1";

    public static final Map<String, Movie> MOVIE_MAP = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        //To reduce overdraw
        getWindow().setBackgroundDrawable(null);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        initializeRecyclerView();

        makeJSONRequest(MOVIE_URL + API_KEY);

        if (findViewById(R.id.movie_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }
    }

    private void initializeRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.movie_list);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        movieAdapter =  new MovieAdapter(this, moviesList);
        recyclerView.setAdapter(movieAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Movie movie = moviesList.get(position);
                Toast.makeText(getApplicationContext(), movie.getTitle() + " is selected!", Toast.LENGTH_SHORT).show();

                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putString(MovieDetailFragment.ARG_ITEM_ID, String.valueOf(movie.getId()));
                    MovieDetailFragment fragment = new MovieDetailFragment();
                    fragment.setArguments(arguments);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.movie_detail_container, fragment)
                            .commit();
                } else {
                    Context mContext = view.getContext();
                    Intent intent = new Intent(mContext, MovieDetailActivity.class);
                    intent.putExtra(MovieDetailFragment.ARG_ITEM_ID, String.valueOf(movie.getId()));
                    mContext.startActivity(intent);
                }


            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    private void makeJSONRequest(String url) {

        JacksonRequest<MovieCollection> jacksonRequest = new JacksonRequest<>
                (Request.Method.GET, url, null, MovieCollection.class, new Response.Listener<MovieCollection>() {
                    @Override
                    public void onResponse(MovieCollection response) {
                        Log.d(TAG+"Response", response.toString());
                        parseResponseDetails(response);
                    }

                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

        // Adding a request (in this example, called jacksonRequest) to the RequestQueue.
        VolleySingleton.getInstance(this).addToRequestQueue(jacksonRequest, TAG);
    }

    private void parseResponseDetails(MovieCollection response) {

        moviesList.addAll(response.getResults());
        displayDetails();
        addMoviesToMap();
    }

    private void addMoviesToMap() {
        for (Movie m : moviesList)
        MOVIE_MAP.put(String.valueOf(m.getId()), m);
    }

    private void displayDetails() {
        movieAdapter.notifyDataSetChanged();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuSortPopularity:
                Collections.sort(moviesList, MoviePopularityDescComparator);
                movieAdapter.notifyDataSetChanged();
                return true;
            case R.id.menuSortDateReleased:
                Collections.sort(moviesList, MovieDateReleasedComparator);
                movieAdapter.notifyDataSetChanged();
                return true;
            case R.id.menuSortTitle:
                Collections.sort(moviesList, MovieTitleComparator);
                movieAdapter.notifyDataSetChanged();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static Comparator<Movie> MoviePopularityDescComparator = new Comparator<Movie>() {

        public int compare(Movie m1, Movie m2) {
            return Double.compare(m2.getPopularity(), m1.getPopularity());
        }
    };

    public static Comparator<Movie> MovieTitleComparator = new Comparator<Movie>() {

        public int compare(Movie m1, Movie m2) {
            return m1.getTitle().compareToIgnoreCase(m2.getTitle());
        }
    };


    public static Comparator<Movie> MovieDateReleasedComparator = new Comparator<Movie>() {

        public int compare(Movie m1, Movie m2) {

            String date1 = m1.getReleaseDate();
            String date2 = m2.getReleaseDate();

            return date1.compareToIgnoreCase(date2);
        }
    };
}
