package com.happytimes.alisha.flixtr.app;

import android.content.Context;
import android.content.res.Configuration;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.toolbox.ImageLoader;
import com.happytimes.alisha.flixtr.R;
import com.happytimes.alisha.flixtr.helper.VolleySingleton;
import com.happytimes.alisha.flixtr.model.Result;
import com.happytimes.alisha.flixtr.viewHolder.DefaultMovieViewHolder;
import com.happytimes.alisha.flixtr.viewHolder.PopularMovieViewHolder;
import com.happytimes.alisha.flixtr.viewHolder.ProgressViewHolder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alishaalam on 7/19/16.
 */
public class MovieAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = MovieAdapter.class.getSimpleName();
    Context mContext;
    private List<Result> mMoviesList = new ArrayList<>();

    ImageLoader mImageLoader;

    //Constants to help with ViewType
    //Represents end of the list
    public static final int VIEW_PROG = 0;
    //Represent object types in the list
    public static final int DEFAULT_MOVIE = 2, POPULAR_MOVIE = 1;


    public MovieAdapter(Context context, List<Result> movies) {
        this.mContext = context;
        this.mMoviesList = movies;
    }


    @Override
    public int getItemCount() {
        return mMoviesList.size();
    }

    /**
     * Returns the view type of the item at position for view recycling
     **/
    @Override
    public int getItemViewType(int position) {
        if (mMoviesList.get(position) == null) {
            return VIEW_PROG;
        } else {
            if (mMoviesList.get(position).getVoteAverage() > 5.0) {
                return POPULAR_MOVIE;
            } else {
                return DEFAULT_MOVIE;
            }
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        mContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);
        switch (viewType) {
            case VIEW_PROG:
                View v0 = inflater.inflate(R.layout.progressbar_item, parent, false);
                viewHolder = new ProgressViewHolder(v0);
                break;
            case POPULAR_MOVIE:
                View v2 = inflater.inflate(R.layout.popular_movie_list_content, parent, false);
                viewHolder = new PopularMovieViewHolder(v2);
                break;
            default:
                View v1 = inflater.inflate(R.layout.default_movie_list_content, parent, false);
                viewHolder = new DefaultMovieViewHolder(v1);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        switch (viewHolder.getItemViewType()) {
            case VIEW_PROG:
                ((ProgressViewHolder) viewHolder).progressBar.setIndeterminate(true);
                break;
            case POPULAR_MOVIE:
                PopularMovieViewHolder popularMovieViewHolder = (PopularMovieViewHolder) viewHolder;
                configurePopularMovieViewHolder(popularMovieViewHolder, position);
                break;
            case DEFAULT_MOVIE:
                DefaultMovieViewHolder defaultMovieViewHolder = (DefaultMovieViewHolder) viewHolder;
                configureDefaultMovieViewHolder(defaultMovieViewHolder, position);
                break;
        }
    }


    private void configurePopularMovieViewHolder(PopularMovieViewHolder popularMovieViewHolder, int position) {
        final Result result = mMoviesList.get(position);
        if (result != null) {
            if (mImageLoader == null)
                mImageLoader = VolleySingleton.getInstance(mContext.getApplicationContext()).getImageLoader();


            //Always show backdrop image for popular movies, irrespective of device orientation
            Picasso.with(mContext)
                    .load(result.getBackdropPath())
                    .placeholder(R.drawable.ic_movie_placeholder)
                    .into(popularMovieViewHolder.vBackdropPath);

            int orientation = mContext.getResources().getConfiguration().orientation;

            //Show movie title and overview only if the device is in landscape mode
            if (orientation == Configuration.ORIENTATION_LANDSCAPE) {

                popularMovieViewHolder.vTitle.setText(result.getTitle());
                popularMovieViewHolder.vOverview.setText(result.getOverview());
            }
        }
    }

    private void configureDefaultMovieViewHolder(DefaultMovieViewHolder defaultMovieViewHolder, int position) {

        final Result result = mMoviesList.get(position);
        if (result != null) {
            if (mImageLoader == null)
                mImageLoader = VolleySingleton.getInstance(mContext.getApplicationContext()).getImageLoader();


            defaultMovieViewHolder.vTitle.setText(result.getTitle());
            defaultMovieViewHolder.vOverview.setText(result.getOverview());


            int orientation = mContext.getResources().getConfiguration().orientation;
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                Picasso.with(mContext)
                        .load(result.getPosterPath())
                        .fit().centerInside()
                        .placeholder(R.drawable.ic_movie_placeholder)
                        .into(defaultMovieViewHolder.vPosterPath);

            }else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {

                Picasso.with(mContext)
                        .load(result.getBackdropPath())
                        .placeholder(R.drawable.ic_movie_placeholder)
                        .into(defaultMovieViewHolder.vBackdropPath);
            }
        }
    }


}
