package com.example.android.movies.fragments.detail;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.movies.BottomNavigationActivity;
import com.example.android.movies.R;
import com.example.android.movies.Views.CircularRatingBar;
import com.example.android.movies.model.ListMovie;
import com.example.android.movies.model.Movie;
import com.example.android.movies.networking.loader.MovieLoader;
import com.example.android.movies.utils.Constants;
import com.example.android.movies.utils.Utils;

/**
 * Created by glm9637 on 17.03.2018 13:04.
 */

public class InfoFragment extends Fragment implements LoaderManager.LoaderCallbacks<Movie> {
	
	private TextView mOverview;
	private TextView mRuntime;
	private TextView mReleaseDate;
	private LinearLayout mGenreContainer;
	private TextView mBudget;
	private TextView mRevenue;
	private CircularRatingBar mRating;
	private TextView mVoteCount;
	private View mRootView;
	private int mMovieID;
	private Movie mData;
	private Context mContext;
	private LayoutInflater mInflater;
	
	/**
	 * Inflates the Fragments rootView, and starts a Loader, to load detailed information.
	 *
	 * @return the inflated rootView
	 */
	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		mRootView = inflater.inflate(R.layout.fragment_detail_info, container, false);
		mContext = getContext();
		mOverview = mRootView.findViewById(R.id.txt_overview);
		mRuntime = mRootView.findViewById(R.id.txt_runtime);
		mReleaseDate = mRootView.findViewById(R.id.txt_release);
		mGenreContainer = mRootView.findViewById(R.id.container_genre);
		mBudget = mRootView.findViewById(R.id.txt_budget);
		mRevenue = mRootView.findViewById(R.id.txt_revenue);
		mRating = mRootView.findViewById(R.id.user_rating_bar);
		mVoteCount = mRootView.findViewById(R.id.user_rating_count_text);
		mInflater = inflater;
		initStartData();
		
		if (getActivity() != null)
			getActivity().getSupportLoaderManager().initLoader(Constants.LOADER_ID_MOVIE, null, this).forceLoad();
		
		return mRootView;
	}
	
	/**
	 * Initialises the Data, which got parsed over from the Activity, before the Loader starts
	 */
	private void initStartData() {
		if (getArguments() != null) {
			ListMovie listMovie = getArguments().getParcelable(Constants.INTENT_BUNDLE_MOVIE);
			if (listMovie != null) {
				mMovieID = listMovie.getId();
				String overview = listMovie.getOverview();
				mOverview.setText(overview);
				int voteCount = listMovie.getVoteCount();
				mVoteCount.setText(String.format("%s %s", String.valueOf(voteCount), getString(R.string.votes)));
				Double voteAverage = listMovie.getVoteAverage()*10;
				mRating.setRatingPercent(voteAverage);
				String releaseDate = Utils.parseToString(listMovie.getReleaseDate());
				mReleaseDate.setText(releaseDate);
			}
		}
	}
	
	/**
	 * Initialises the Data from the Loader, which was not set before
	 */
	private void initData() {
		if (mData.getRuntime() == null) {
			mRuntime.setText(getText(R.string.unknown));
		} else {
			mRuntime.setText(String.format("%s%s %s%s", (int) Math.floor(mData.getRuntime() / 60), getString(R.string.hour_short), (int) (mData.getRuntime() - Math.floor(mData.getRuntime() / 60) * 60), getString(R.string.minute_short)));
		}
		
		if (mData.getBudget() == null) {
			mBudget.setText(getText(R.string.unknown));
		} else {
			mBudget.setText(String.format("$%s", mData.getBudget()));
		}
		if (mData.getRevenue() == null) {
			mRevenue.setText(getText(R.string.unknown));
		} else {
			mRevenue.setText(String.format("$%s", mData.getRevenue()));
		}
		mGenreContainer.removeAllViews();
		for (int i = 0; i < mData.getGenres().size(); i++) {
			TextView genreText = (TextView) mInflater.inflate(R.layout.chip_textview, mGenreContainer, false);
			genreText.setText(mData.getGenres().get(mData.getGenres().keyAt(i)));
			mGenreContainer.addView(genreText);
		}
	}
	
	
	@NonNull
	@Override
	public Loader<Movie> onCreateLoader(int id, @Nullable Bundle args) {
		return new MovieLoader(mContext, mMovieID);
	}
	
	@Override
	public void onLoadFinished(@NonNull Loader<Movie> loader, Movie data) {
		if (loader.getId() == Constants.LOADER_ID_MOVIE) {
			mData = data;
			if (mData == null) {
				((BottomNavigationActivity) getActivity()).hideBottomBar();
				Snackbar.make(mRootView, R.string.error_loading_movie, Snackbar.LENGTH_INDEFINITE).show();
			} else {
				initData();
			}
		}
	}
	
	@Override
	public void onLoaderReset(@NonNull Loader<Movie> loader) {
	
	}
}
