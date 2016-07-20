package com.happytimes.alisha.flixtr.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.happytimes.alisha.flixtr.R;

/**
 * Created by alishaalam on 7/19/16.
 */
public class PopularMovieViewHolder extends RecyclerView.ViewHolder {


    public ImageView vBackdropPath;


    public PopularMovieViewHolder(View itemView) {
        super(itemView);
        this.vBackdropPath = (ImageView) itemView.findViewById(R.id.ivBackdropPath);
    }



}
