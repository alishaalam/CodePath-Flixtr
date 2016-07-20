package com.happytimes.alisha.flixtr.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.happytimes.alisha.flixtr.R;
import com.happytimes.alisha.flixtr.helper.JacksonRequest;
import com.happytimes.alisha.flixtr.helper.VolleySingleton;
import com.happytimes.alisha.flixtr.model.Movie;
import com.happytimes.alisha.flixtr.model.Result;

import java.util.ArrayList;
import java.util.List;

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
    private boolean mTwoPane;
    RecyclerView recyclerView;
    MovieAdapter movieAdapter;
    List<Result> moviesList = new ArrayList<>();

    private static final String TAG = MovieListActivity.class.getSimpleName();
    public static final String MOVIE_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=";
    public static final String API_KEY = "a07e22bc18f5cb106bfe4cc1f83ad8ed";
    //public static final String PAGE_NUMBER = "/1";


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
    }

    private void makeJSONRequest(String url) {

        JacksonRequest<Movie> jacksonRequest = new JacksonRequest<>
                (Request.Method.GET, url, null, Movie.class, new Response.Listener<Movie>() {
                    @Override
                    public void onResponse(Movie response) {
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

    private void parseResponseDetails(Movie response) {

        moviesList.addAll(response.getResults());
        displayDetails();
    }

    private void displayDetails() {
        movieAdapter.notifyDataSetChanged();
    }




    /*private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(DummyContent.ITEMS));
    }*/

    /*public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final List<DummyContent.DummyItem> mValues;

        public SimpleItemRecyclerViewAdapter(List<DummyContent.DummyItem> items) {
            mValues = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.default_movie_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mItem = mValues.get(position);
            holder.mIdView.setText(mValues.get(position).id);
            holder.mContentView.setText(mValues.get(position).content);

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTwoPane) {
                        Bundle arguments = new Bundle();
                        arguments.putString(MovieDetailFragment.ARG_ITEM_ID, holder.mItem.id);
                        MovieDetailFragment fragment = new MovieDetailFragment();
                        fragment.setArguments(arguments);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.movie_detail_container, fragment)
                                .commit();
                    } else {
                        Context mContext = v.getContext();
                        Intent intent = new Intent(mContext, MovieDetailActivity.class);
                        intent.putExtra(MovieDetailFragment.ARG_ITEM_ID, holder.mItem.id);

                        mContext.startActivity(intent);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView mIdView;
            public final TextView mContentView;
            public DummyContent.DummyItem mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mIdView = (TextView) view.findViewById(R.id.id);
                mContentView = (TextView) view.findViewById(R.id.content);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mContentView.getText() + "'";
            }
        }
    }*/
}
