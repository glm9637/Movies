package com.example.android.movies;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Erzeugt von M. Fengels am 26.02.2018.
 */

public class MoviePosterAdapter extends RecyclerView.Adapter<MoviePosterAdapter.MovieViewHolder> {
	
	LayoutInflater mInflater;
	
	public MoviePosterAdapter(Context context){
		mInflater = 	LayoutInflater.from(context);
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
		
		MovieViewHolder(final View itemView) {
			super(itemView);
			itemView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(itemView.getContext(),DetailActivity.class);
					itemView.getContext().startActivity(intent);
				}
			});
		}
	}
}
