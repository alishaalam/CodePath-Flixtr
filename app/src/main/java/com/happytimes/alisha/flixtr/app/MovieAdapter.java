package com.happytimes.alisha.flixtr.app;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.toolbox.ImageLoader;
import com.happytimes.alisha.flixtr.R;
import com.happytimes.alisha.flixtr.helper.VolleySingleton;
import com.happytimes.alisha.flixtr.model.Result;
import com.happytimes.alisha.flixtr.viewHolder.MovieViewHolder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alishaalam on 7/19/16.
 */
public class MovieAdapter extends RecyclerView.Adapter<MovieViewHolder> {

    private static final String TAG = MovieAdapter.class.getSimpleName();
    Context mContext;
    private List<Result> mMoviesList = new ArrayList<>();

    ImageLoader mImageLoader;



    public MovieAdapter(Context context, List<Result> movies) {
        this.mContext = context;
        this.mMoviesList = movies;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.movie_list_content, parent, false);
        return new MovieViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder movieViewHolder, int position) {
        final Result result = mMoviesList.get(position);
        if (result != null) {
            if (mImageLoader == null)
                mImageLoader = VolleySingleton.getInstance(mContext.getApplicationContext()).getImageLoader();


            movieViewHolder.vTitle.setText(result.getTitle());
            movieViewHolder.vOverview.setText(result.getOverview());
            Picasso.with(mContext)
                    .load(result.getPosterPath())
                    .into(movieViewHolder.vPosterPath);
            /*.fit().centerCrop()
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)*/
        }
    }

    @Override
    public int getItemCount() {
        return mMoviesList.size();
    }
}
