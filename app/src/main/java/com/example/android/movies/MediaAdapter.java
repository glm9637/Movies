package com.example.android.movies;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.movies.model.ListMovie;
import com.example.android.movies.networking.NetworkHelper;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Erzeugt von M. Fengels am 26.02.2018.
 */

public class MediaAdapter extends RecyclerView.Adapter<MediaAdapter.MediaViewHolder> {

    private LayoutInflater mInflater;
    private ArrayList<String> mData;

    public MediaAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public MediaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View posterView = mInflater.inflate(R.layout.media_recycler_item, parent, false);
        return new MediaViewHolder(posterView);
    }

    @Override
    public void onBindViewHolder(MediaViewHolder holder, int position) {
        String currentItem = mData.get(position);
        Picasso.with(mInflater.getContext()).load(NetworkHelper.getImageUrl(currentItem, NetworkHelper.ImageSize.original)).placeholder(R.drawable.progress_animation).into(holder.mPosterView);

    }

    /**
     * refreshes the Data
     * @param newData the new Data to display
     */
    public void setData(ArrayList<String> newData) {
        mData = newData;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mData != null)
            return mData.size();
        return 0;
    }

    class MediaViewHolder extends ViewHolder {

        ImageView mPosterView;

        MediaViewHolder(final View itemView) {
            super(itemView);
            mPosterView = (ImageView) itemView;

        }
    }
}
