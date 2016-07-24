package com.happytimes.alisha.flixtr.app;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.happytimes.alisha.flixtr.R;
import com.happytimes.alisha.flixtr.model.Movie;
import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * A fragment representing a single Movie detail screen.
 * This fragment is either contained in a {@link MovieListActivity}
 * in two-pane mode (on tablets) or a {@link MovieDetailActivity}
 * on handsets.
 */
public class MovieDetailFragment extends Fragment {


    private static final String TAG = MovieDetailFragment.class.getSimpleName();
    private static final Float MAX_VOTE_AVERAGE = 8.0f;
    private static final Float MAX_RATING = 5.0f;

    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private Movie mMovie;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MovieDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the content specified by the fragment arguments
            mMovie = MovieListActivity.MOVIE_MAP.get(getArguments().getString(ARG_ITEM_ID));

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {

                ImageView movie_poster = (ImageView) activity.findViewById(R.id.movie_detail_poster);
                Picasso.with(getActivity())
                        .load(mMovie.getPosterPath())
                        .fit()
                        .placeholder(R.drawable.ic_movie_placeholder)
                        .transform(new RoundedCornersTransformation(10, 10))
                        .into(movie_poster);


                ImageView movie_backdrop = (ImageView) activity.findViewById(R.id.movie_detail_backdrop);
                Picasso.with(getActivity())
                        .load(mMovie.getBackdropPath())
                        .fit()
                        .placeholder(R.drawable.ic_movie_placeholder)
                        .transform(new RoundedCornersTransformation(10, 10))
                        .into(movie_backdrop);

            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.movie_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (mMovie != null) {


            RatingBar rbProductRating = (RatingBar) rootView.findViewById(R.id.movie_review_rating);
            float rating = ((float) mMovie.getVoteAverage() * MAX_RATING) / MAX_VOTE_AVERAGE;
            rbProductRating.setRating(rating);

            String str_vote_count = "(" + mMovie.getVoteCount() + " votes)";
            ((TextView) rootView.findViewById(R.id.movie_vote_count)).setText(str_vote_count);
            ((TextView) rootView.findViewById(R.id.movie_title)).setText(mMovie.getTitle());
            ((TextView) rootView.findViewById(R.id.movie_overview)).setText(mMovie.getOverview());
        }
        return rootView;
    }
}
