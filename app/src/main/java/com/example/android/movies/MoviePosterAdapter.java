package com.example.android.movies;

import android.content.Context;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.movies.model.ListMovie;
import com.example.android.movies.model.Movie;
import com.example.android.movies.networking.NetworkHelper;
import com.example.android.movies.utils.Constants;
import com.example.android.movies.utils.Utils;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

/**
 * Erzeugt von M. Fengels am 26.02.2018.
 */

public class MoviePosterAdapter extends RecyclerView.Adapter<MoviePosterAdapter.MovieViewHolder> {

    private LayoutInflater mInflater;
    private MovieSelectedListener mMovieSelectedListener;
    private ArrayList<ListMovie> mData;
	private ArrayList<ListMovie> mDataOffline;
    
    public interface MovieSelectedListener {
        void onMovieSelected(ImageView posterView, Bundle info);
    }

    public MoviePosterAdapter(Context context, MovieSelectedListener movieSelectedListener) {
        mInflater = LayoutInflater.from(context);
        mMovieSelectedListener = movieSelectedListener;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View posterView = mInflater.inflate(R.layout.overview_recycler_item, parent, false);
        View dialogView = mInflater.inflate(R.layout.movie_detail_short_dialog, parent, false);
        return new MovieViewHolder(posterView, dialogView);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
    	if(mData != null) {
		    ListMovie currentItem = mData.get(position + 1);
		    Picasso.with(mInflater.getContext()).load(NetworkHelper.getImageUrl(currentItem.getPosterPath(), NetworkHelper.ImageSize.small)).placeholder(R.drawable.progress_animation).into(holder.mPosterView);
		    holder.mTitleText.setText(currentItem.getTitle());
		    holder.mOverviewText.setText(currentItem.getOverview());
	    }else if(mDataOffline != null){
		    ListMovie currentItem = mDataOffline.get(position + 1);
		    Picasso.with(mInflater.getContext()).load(new File(currentItem.getPosterPath())).placeholder(R.drawable.progress_animation).into(holder.mPosterView);
		    holder.mTitleText.setText(currentItem.getTitle());
		    holder.mOverviewText.setText(currentItem.getOverview());
	    }
    }

    /**
     * refreshes the Data
     * @param newData the new Data to display
     */
    public void setData(ArrayList<ListMovie> newData) {
    	mDataOffline = null;
        mData = newData;
        notifyDataSetChanged();
    }
    
    public void setOfflineData(ArrayList<ListMovie> newData){
    	mData = null;
    	mDataOffline = newData;
    	notifyDataSetChanged();
    }

    /**
     * returns the size of the data -1 because the first entry is already displayed in the activity
     * @return
     */
    @Override
    public int getItemCount() {
        if (mData != null)
            return mData.size()-1;
        if(mDataOffline!=null){
        	return mDataOffline.size()-1;
        }
        return 0;
    }

    /**
     * Holds the Views for the Recyclerview and the Dialog for the long Press  Action
     */
    class MovieViewHolder extends ViewHolder {

        ImageView mPosterView;
        ImageView mDialogPoster;
        TextView mTitleText;
        TextView mOverviewText;
        Button mCloseDialogButton;
        AlertDialog mDialog;

        /**
         * Saves a reference to the View and initialises the onClick and onLongClickListener
         * @param itemView
         * @param mDialogView
         */
        MovieViewHolder(final View itemView, final View mDialogView) {
            super(itemView);
            mPosterView = itemView.findViewById(R.id.poster_view);
            mDialogPoster = mDialogView.findViewById(R.id.poster_view);
            mTitleText = mDialogView.findViewById(R.id.title_text);
            mOverviewText = mDialogView.findViewById(R.id.overview_text);
            mCloseDialogButton = mDialogView.findViewById(R.id.close_dialog_btn);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle itemInfo = new Bundle();
                    if(mData!=null) {
	                    itemInfo.putParcelable(Constants.INTENT_BUNDLE_MOVIE, mData.get(getAdapterPosition() + 1));
	                    itemInfo.putInt(Constants.MOVIE_ID, mData.get(getAdapterPosition() + 1).getId());
                    }else if(mDataOffline!=null){
	                    itemInfo.putParcelable(Constants.INTENT_BUNDLE_MOVIE, mDataOffline.get(getAdapterPosition() + 1));
	                    itemInfo.putInt(Constants.MOVIE_ID, mDataOffline.get(getAdapterPosition() + 1).getId());
                    }
                    mMovieSelectedListener.onMovieSelected(mPosterView,itemInfo);
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if(mDialog == null){
                        mDialog = new AlertDialog.Builder(mPosterView.getContext(), R.style.MovieOverviewDialog).create();
                        mDialog.setView(mDialogView);
                        mCloseDialogButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mDialog.dismiss();
                            }
                        });
                    }

                    mDialogPoster.setImageDrawable(mPosterView.getDrawable());
                    mDialog.show();
                    return true;
                }
            });
        }
    }
}
