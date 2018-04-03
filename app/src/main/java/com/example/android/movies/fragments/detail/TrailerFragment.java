package com.example.android.movies.fragments.detail;

import android.content.Context;
import android.os.Bundle;
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
import com.example.android.movies.TrailerAdapter;
import com.example.android.movies.model.Trailer;
import com.example.android.movies.networking.loader.TrailerLoader;
import com.example.android.movies.utils.Constants;

import java.util.ArrayList;

/**
 * Erzeugt von M. Fengels am 03.04.2018.
 */
public class TrailerFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<Trailer>> {
	
	
	private RecyclerView mTrailerList;
	private TrailerAdapter mTrailerAdapter;
	private Context mContext;
	
	/**
	 * Inflates the Fragments rootView, create the Adapter for the Recyclerview, and starts a Loader, to load the cast.
	 * @return
	 */
	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_recycler, container, false);
		mContext = getContext();
		mTrailerList = rootView.findViewById(R.id.recycler_overview);
		LinearLayoutManager listManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
		mTrailerList.setLayoutManager(listManager);
		mTrailerAdapter = new TrailerAdapter(getContext());
		mTrailerList.setAdapter(mTrailerAdapter);
		
		if (getActivity() != null)
			getActivity().getSupportLoaderManager().initLoader(Constants.LOADER_ID_TRAILER, null, this).forceLoad();
		
		return rootView;
	}
	
	@NonNull
	@Override
	public Loader<ArrayList<Trailer>> onCreateLoader(int id, @Nullable Bundle args) {
		return new TrailerLoader(mContext, getArguments().getInt(Constants.MOVIE_ID));
	}
	
	@Override
	public void onLoadFinished(@NonNull Loader<ArrayList<Trailer>> loader, ArrayList<Trailer> data) {
		if (loader.getId() == Constants.LOADER_ID_TRAILER) {
			mTrailerAdapter.setData(data);
		}
		
	}
	
	@Override
	public void onLoaderReset(@NonNull Loader loader) {
		loader.reset();
	}
	
}
