package com.example.android.movies.fragments.detail;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.movies.R;
import com.example.android.movies.Views.CircularRatingBar;
import com.example.android.movies.model.Movie;
import com.example.android.movies.networking.loader.MovieLoader;
import com.example.android.movies.utils.Constants;

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
    private int mMovieID;
    private Movie mData;
    private Context mContext;
    private LayoutInflater mInflater;

    /**
     * Inflates the Fragments rootView, and starts a Loader, to load detailed information.
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail_info, container, false);
        mContext = getContext();
        mOverview = rootView.findViewById(R.id.txt_overview);
        mRuntime = rootView.findViewById(R.id.txt_runtime);
        mReleaseDate = rootView.findViewById(R.id.txt_release);
        mGenreContainer = rootView.findViewById(R.id.container_genre);
        mBudget = rootView.findViewById(R.id.txt_budget);
        mRevenue = rootView.findViewById(R.id.txt_revenue);
        mRating = rootView.findViewById(R.id.user_rating_bar);
        mVoteCount = rootView.findViewById(R.id.user_rating_count_text);
        mInflater = inflater;
        initStartData();

        if (getActivity() != null)
            getActivity().getSupportLoaderManager().initLoader(Constants.LOADER_ID_MOVIE, null, this).forceLoad();

        return rootView;
    }

    /**
     * Initialises the Data, which gots parsed over from the Activity, before the Loader starts
     */
    private void initStartData() {
        if (getArguments() != null) {
            mMovieID = getArguments().getInt(Constants.MOVIE_ID);
            String overview = getArguments().getString(Constants.INTENT_BUNDLE_OVERVIEW, "");
            mOverview.setText(overview);
            int voteCount = getArguments().getInt(Constants.INTENT_BUNDLE_VOTE_COUNT, 0);
            mVoteCount.setText(String.format("%s %s", String.valueOf(voteCount),getString(R.string.votes)));
            Double voteAverage = getArguments().getDouble(Constants.INTENT_BUNDLE_VOTE_AVERAGE, 0f) * 10;
            mRating.setRatingPercent(voteAverage);
            String releaseDate = getArguments().getString(Constants.INTENT_BUNDLE_RELEASE_DATE, getString(R.string.unknown));
            mReleaseDate.setText(releaseDate);
        }
    }

    /**
     * Initialises the Data from the Loader, which was not set before
     */
    private void initData() {
        mRuntime.setText(String.format("%s%s %s%s", Math.floor(mData.getRuntime() / 60),getString(R.string.hour_short), mData.getRuntime() - Math.floor(mData.getRuntime() / 60) * 60,getString(R.string.minute_short)));
        mBudget.setText(String.format("$%s", mData.getBudget()));
        mRevenue.setText(String.format("$%s", mData.getRevenue()));
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
            initData();
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Movie> loader) {

    }
}
