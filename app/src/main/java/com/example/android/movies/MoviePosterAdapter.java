package com.example.android.movies;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Erzeugt von M. Fengels am 26.02.2018.
 */

public class MoviePosterAdapter extends RecyclerView.Adapter<MoviePosterAdapter.MovieViewHolder> {
	
	private LayoutInflater mInflater;
	private MovieSelectedListener mMovieSelectedListener;
	
	
	public interface MovieSelectedListener {
		void onMovieSelected(ImageView posterView);
	}
	
	public MoviePosterAdapter(Context context, MovieSelectedListener movieSelectedListener){
		mInflater = 	LayoutInflater.from(context);
		mMovieSelectedListener = movieSelectedListener;
	}
	
	@Override
	public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		return new MovieViewHolder(mInflater.inflate(R.layout.overview_recycler_item,parent,false));
	}
	
	@Override
	public void onBindViewHolder(MovieViewHolder holder, int position) {
	
	}
	
	@Override
	public int getItemCount() {
		return 50;
	}
	
	class MovieViewHolder extends ViewHolder {
		
		ImageView mPosterView;
		
		MovieViewHolder(final View itemView) {
			super(itemView);
			mPosterView = itemView.findViewById(R.id.poster_view);
			itemView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					mMovieSelectedListener.onMovieSelected(mPosterView);
				}
			});
		}
	}
}
