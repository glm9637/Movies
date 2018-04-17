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

/**
 * Displays a List of Reviews
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
	
	/**
	 * Binds data to a ViewHolder
	 * @param holder the Viewholder to display the data in
	 * @param position the Position of the Review inside the data List
	 */
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
	
	/**
	 * @return Returns the length of the data list, or 1 to display a empty Message
	 */
	@Override
	public int getItemCount() {
		if (mData != null)
			return Math.max(1,mData.size());
		return 1;
	}
	
	/**
	 * The ViewHolder to display a review
	 */
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
