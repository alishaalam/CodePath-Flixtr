package com.happytimes.alisha.flixtr.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.happytimes.alisha.flixtr.R;

/**
 * Created by alishaalam on 7/19/16.
 */
public class PopularMovieViewHolder extends RecyclerView.ViewHolder {


    public ImageView vBackdropPath;
    public TextView vOverview;
    public TextView vTitle;


    public PopularMovieViewHolder(View itemView) {
        super(itemView);
        this.vBackdropPath = (ImageView) itemView.findViewById(R.id.ivBackdropPath);
        this.vOverview = (TextView) itemView.findViewById(R.id.tvOverview);
        this.vTitle = (TextView) itemView.findViewById(R.id.tvTitle);
    }



}
