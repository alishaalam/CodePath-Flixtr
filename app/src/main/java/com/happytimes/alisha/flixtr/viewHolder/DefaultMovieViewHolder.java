package com.happytimes.alisha.flixtr.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.happytimes.alisha.flixtr.R;

/**
 * Created by alishaalam on 7/19/16.
 */
public class DefaultMovieViewHolder extends RecyclerView.ViewHolder {


    public ImageView vPosterPath;
    public TextView vOverview;
    public TextView vTitle;
    public ImageView vBackdropPath;


    public DefaultMovieViewHolder(View itemView) {
        super(itemView);
        this.vPosterPath = (ImageView) itemView.findViewById(R.id.ivPosterPath);
        this.vOverview = (TextView) itemView.findViewById(R.id.tvOverview);
        this.vTitle = (TextView) itemView.findViewById(R.id.tvTitle);
        //this.vBackdropPath = (ImageView) itemView.findViewById(R.id.ivBackdropPath);
    }



}
