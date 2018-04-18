package com.example.android.movies.fragments.overview;

import android.app.ActivityOptions;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.movies.DetailActivity;
import com.example.android.movies.MoviePosterAdapter;
import com.example.android.movies.R;
import com.example.android.movies.database.MovieContract;
import com.example.android.movies.model.ListMovie;
import com.example.android.movies.utils.Constants;

import java.util.ArrayList;

/**
 * Erzeugt von M. Fengels am 16.04.2018.
 */
public class FavoriteFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
	
	private RecyclerView mPosterGrid;
	private MoviePosterAdapter mPosterAdapter;
	private FragmentActivity mContext;
	private MoviePosterFragment.LoadTopImage mLoadTopImage;
	private Parcelable mLayoutManagerState;
	private static Bundle mBundleRecyclerViewState;
	
	/**
	 * Adds a listener to load the first Image outside of the Recyclerview.
	 * In this case at the Top of the Activity
	 *
	 * @param loadTopImageListener the Listener to notify
	 */
	public void setLoadTopImageListener(MoviePosterFragment.LoadTopImage loadTopImageListener) {
		mLoadTopImage = loadTopImageListener;
	}
	
	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_recycler, container, false);
		mContext = getActivity();
		mPosterGrid = rootView.findViewById(R.id.recycler_overview);
		GridLayoutManager gridManager = new GridLayoutManager(getContext(), 2);
		mPosterGrid.setLayoutManager(gridManager);
		mPosterAdapter = new MoviePosterAdapter(getContext(), new MoviePosterAdapter.MovieSelectedListener() {
			@Override
			public void onMovieSelected(ImageView posterView, Bundle info) {
				Intent intent = new Intent(getContext(), DetailActivity.class);
				intent.putExtra(Constants.INTENT_BUNDLE, info);
				if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
					ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity(), posterView, getContext().getString(R.string.picture_transition_name));
					getContext().startActivity(intent, options.toBundle());
				} else {
					getContext().startActivity(intent);
				}
			}
		});
		mPosterGrid.setAdapter(mPosterAdapter);
		
		if (getActivity() != null)
			getActivity().getSupportLoaderManager().restartLoader(Constants.LOADER_ID_FAVORIT_LIST, null, this);
		
		return rootView;
	}
	
	@NonNull
	@Override
	public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
		
		
		return new CursorLoader(getContext(), MovieContract.MovieEntry.CONTENT_URI, null, null, null, null);
		
		
	}
	
	/**
	 * Distibute the Data to the parent Activity, and the Recyclerview
	 */
	@Override
	public void onLoadFinished(@NonNull Loader<Cursor> loader, final Cursor data) {
		if (data.isClosed()) {
			if (isAdded() && getActivity() != null)
				getActivity().getSupportLoaderManager().restartLoader(Constants.LOADER_ID_FAVORIT_LIST, null, this);
			return;
		}
		final ArrayList<ListMovie> movieData = new ArrayList<>();
		if (data.moveToFirst()) {
			do {
				movieData.add(new ListMovie(data));
			} while (data.moveToNext());
		}
		if (movieData.size() > 0) {
			
			mLoadTopImage.onLoadTopImage(movieData.get(0), new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Bundle itemInfo = new Bundle();
					itemInfo.putParcelable(Constants.INTENT_BUNDLE_MOVIE, movieData.get(0));
					itemInfo.putInt(Constants.MOVIE_ID, movieData.get(0).getId());
					Intent intent = new Intent(getContext(), DetailActivity.class);
					
					intent.putExtra(Constants.INTENT_BUNDLE, itemInfo);
					if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
						ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity(), v, getContext().getString(R.string.picture_transition_name));
						getContext().startActivity(intent, options.toBundle());
					} else {
						getContext().startActivity(intent);
					}
				}
			});
			
		} else {
			mLoadTopImage.onError(Constants.ERROR_NO_FAVORITES);
			
		}
		data.close();
		mPosterAdapter.setOfflineData(movieData);
		
		
	}
	
	@Override
	public void onLoaderReset(@NonNull Loader loader) {
		loader.reset();
	}
	
	@Override
	public void onPause() {
		super.onPause();
		mBundleRecyclerViewState = new Bundle();
		mLayoutManagerState = mPosterGrid.getLayoutManager().onSaveInstanceState();
		mBundleRecyclerViewState.putParcelable(Constants.SAVE_INSTANCE_RECYCLERVIEW, mLayoutManagerState);
		
	}
	
	
	@Override
	public void onResume() {
		super.onResume();
		if (mBundleRecyclerViewState != null) {
			Parcelable listState = mBundleRecyclerViewState.getParcelable(Constants.SAVE_INSTANCE_RECYCLERVIEW);
			mPosterGrid.getLayoutManager().onRestoreInstanceState(listState);
		}
	}
	
}
