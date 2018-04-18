package com.example.android.movies.fragments.detail;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.movies.R;
import com.example.android.movies.ReviewAdapter;
import com.example.android.movies.model.Review;
import com.example.android.movies.networking.loader.ReviewLoader;
import com.example.android.movies.utils.Constants;

import java.util.ArrayList;

/**
 * Erzeugt von M. Fengels am 03.04.2018.
 */
public class ReviewFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<Review>> {
	
	
	private RecyclerView mReviewList;
	private ReviewAdapter mReviewAdapter;
	private Context mContext;
	
	/**
	 * Inflates the Fragments rootView, create the Adapter for the Recyclerview, and starts a Loader, to load the cast.
	 */
	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_recycler, container, false);
		mContext = getContext();
		mReviewList = rootView.findViewById(R.id.recycler_overview);
		LinearLayoutManager listManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
		mReviewList.setLayoutManager(listManager);
		mReviewAdapter = new ReviewAdapter(getContext());
		mReviewList.setAdapter(mReviewAdapter);
		
		if (getActivity() != null)
			getActivity().getSupportLoaderManager().initLoader(Constants.LOADER_ID_REVIEW, null, this).forceLoad();
		
		return rootView;
	}
	
	@NonNull
	@Override
	public Loader<ArrayList<Review>> onCreateLoader(int id, @Nullable Bundle args) {
		return new ReviewLoader(mContext, getArguments().getInt(Constants.MOVIE_ID));
	}
	
	@Override
	public void onLoadFinished(@NonNull Loader<ArrayList<Review>> loader, ArrayList<Review> data) {
		if (loader.getId() == Constants.LOADER_ID_REVIEW) {
			mReviewAdapter.setData(data);
		}
		
	}
	
	@Override
	public void onLoaderReset(@NonNull Loader loader) {
		loader.reset();
	}
	
	@Override
	public void onSaveInstanceState(@NonNull Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putParcelable(Constants.SAVE_INSTANCE_RECYCLERVIEW,mReviewList.getLayoutManager().onSaveInstanceState());
	}
	
	@Override
	public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
		super.onViewStateRestored(savedInstanceState);
		
		if(savedInstanceState!=null){
			Parcelable savedRecyclerLayoutState = savedInstanceState.getParcelable(Constants.SAVE_INSTANCE_RECYCLERVIEW);
			mReviewList.getLayoutManager().onRestoreInstanceState(savedRecyclerLayoutState);
		}
	}
	
}
