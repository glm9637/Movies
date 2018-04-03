package com.example.android.movies;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.movies.model.Review;

import java.util.ArrayList;

/**
 * Erzeugt von M. Fengels am 03.04.2018.
 */
public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {
	
	private LayoutInflater mInflater;
	private ArrayList<Review> mData;
	
	public ReviewAdapter(Context context) {
		mInflater = LayoutInflater.from(context);
	}
	
	@NonNull
	@Override
	public ReviewAdapter.ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View reviewView = mInflater.inflate(R.layout.review_recycler_item, parent, false);
		return new ReviewViewHolder(reviewView);
	}
	
	@Override
	public void onBindViewHolder(@NonNull ReviewAdapter.ReviewViewHolder holder, int position) {
		if(mData==null || mData.size()==0) {
			holder.mAuthorText.setVisibility(View.GONE);
			holder.mReviewText.setText(R.string.txt_no_reviews);
		}else {
			Review currentItem = mData.get(position);
			holder.mAuthorText.setText(currentItem.getAuthor());
			holder.mReviewText.setText(currentItem.getReview());
		}
	}
	
	/**
	 * refreshes the Data
	 * @param newData the new Data to display
	 */
	public void setData(ArrayList<Review> newData) {
		mData = newData;
		notifyDataSetChanged();
	}
	
	@Override
	public int getItemCount() {
		if (mData != null)
			return Math.max(1,mData.size());
		return 1;
	}
	
	class ReviewViewHolder extends RecyclerView.ViewHolder {
		
		TextView mAuthorText;
		TextView mReviewText;
		
		ReviewViewHolder(final View itemView) {
			super(itemView);
			mAuthorText = itemView.findViewById(R.id.txt_author);
			mReviewText = itemView.findViewById(R.id.txt_review);
		}
	}
}
