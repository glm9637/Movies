package com.example.android.movies;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.movies.model.Trailer;
import com.example.android.movies.networking.NetworkHelper;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Erzeugt von M. Fengels am 26.02.2018.
 */

/**
 * Displays a List of Trailers with their corresponding preview Image
 */
public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder> {

    private LayoutInflater mInflater;
    private ArrayList<Trailer> mData;
	private Context mContext;
    
    public TrailerAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        mContext = context;
    }

    @NonNull
    @Override
    public TrailerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View posterView = mInflater.inflate(R.layout.trailer_recycler_item, parent, false);
        return new TrailerViewHolder(posterView);
    }
	
	/**
	 * Binds the Trailer data to the Displayed View
	 * @param holder the ViewHolder to display the data in
	 * @param position the position of the Entry inside the data List
	 */
	@Override
    public void onBindViewHolder(@NonNull TrailerViewHolder holder, int position) {
        Trailer currentItem = mData.get(position);
        Picasso.with(mInflater.getContext()).load(NetworkHelper.getTrailerImageUrl(currentItem.getKey())).placeholder(R.drawable.progress_animation).into(holder.mPosterView);
        holder.mTitleView.setText(currentItem.getName());
    }

    /**
     * refreshes the Data
     * @param newData the new Data to display
     */
    public void setData(ArrayList<Trailer> newData) {
        mData = newData;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mData != null)
            return mData.size();
        return 0;
    }
	
	/**
	 * The ViewHolder for a single Trailer Entry
	 */
	class TrailerViewHolder extends ViewHolder {

        ImageView mPosterView;
        TextView mTitleView;
		
		/**
		 * Binds the Views to Variables and adds a onClickListener to the View
		 * @param itemView
		 */
		TrailerViewHolder(final View itemView) {
            super(itemView);
            mPosterView = itemView.findViewById(R.id.trailer_preview_image);
            mTitleView = itemView.findViewById(R.id.trailer_name);
			itemView.setOnClickListener(new View.OnClickListener() {
				/**
				 * Opens either the Youtube app, or the browser with the Trailer, if the app is not installed.
				 * @param v
				 */
				@Override
				public void onClick(View v) {
					Trailer trailer = mData.get(getAdapterPosition());
					Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + trailer.getKey()));
					try {
						mContext.startActivity(appIntent);
					} catch (ActivityNotFoundException ex) {
						Intent webIntent = new Intent(Intent.ACTION_VIEW,
								Uri.parse("http://www.youtube.com/watch?v=" + trailer.getKey()));
						mContext.startActivity(webIntent);
					}
				}
			});
        }
    }
}
