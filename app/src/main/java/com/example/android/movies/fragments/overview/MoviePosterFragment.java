package com.example.android.movies.fragments.overview;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
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
import com.example.android.movies.model.ListMovie;
import com.example.android.movies.networking.loader.MovieListLoader;
import com.example.android.movies.utils.Constants;
import com.example.android.movies.utils.Utils;

import java.util.ArrayList;

/**
 * Erzeugt von M. Fengels am 26.02.2018.
 */

/**
 * Base Class for displaying a grid of Movies
 */
public abstract class MoviePosterFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<ListMovie>> {


    /**
     * interface for communication with the Activity, and displaying the first Movie on Top
     */
    public interface LoadTopImage {
        void onLoadTopImage(ListMovie topMovie, View.OnClickListener onMovieClicked);
    }

    private RecyclerView mPosterGrid;
    private MoviePosterAdapter mPosterAdapter;
    private Context mContext;
    private LoadTopImage mLoadTopImage;

    /**
     *
     * @return The Endpoint from which the loader should fetch its data
     */
    protected abstract String getUrlEndpoint();

    /**
     * @return the ID for the Loader which get's the data for the current Fragment
     */
    protected abstract int getLoaderID();

    /**
     * Adds a listener to load the first Image outside of the Recyclerview.
     * In this case at the Top of the Activity
     * @param loadTopImageListener the Listener to notify
     */
    public void setLoadTopImageListener(LoadTopImage loadTopImageListener) {
        mLoadTopImage = loadTopImageListener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recycler, container, false);
        mContext = getContext();
        mPosterGrid = rootView.findViewById(R.id.recycler_overview);
        GridLayoutManager gridManager = new GridLayoutManager(getContext(), 2);
        mPosterGrid.setLayoutManager(gridManager);
        mPosterAdapter = new MoviePosterAdapter(getContext(), new MoviePosterAdapter.MovieSelectedListener() {
            @Override
            public void onMovieSelected(ImageView posterView, Bundle info, Bundle detailInfo) {
                ActivityOptions options = null;
                Intent intent = new Intent(getContext(), DetailActivity.class);
                intent.putExtra(Constants.INTENT_BUNDLE,info);
                intent.putExtra(Constants.INTENT_BUNDLE_DETAIL,detailInfo);
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    options = ActivityOptions.makeSceneTransitionAnimation(getActivity(), posterView, getContext().getString(R.string.picture_transition_name));
                    getContext().startActivity(intent, null);
                }else {
                    getContext().startActivity(intent);
                }
            }
        });
        mPosterGrid.setAdapter(mPosterAdapter);

        if (getActivity() != null)
            getActivity().getSupportLoaderManager().initLoader(getLoaderID(), null, this).forceLoad();

        return rootView;
    }

    @NonNull
    @Override
    public Loader<ArrayList<ListMovie>> onCreateLoader(int id, @Nullable Bundle args) {

        return new MovieListLoader(mContext, getUrlEndpoint());


    }

    /**
     * Distibute the Data to the parent Activity, and the Recyclerview
     */
    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<ListMovie>> loader, final ArrayList<ListMovie> data) {
        if (mLoadTopImage != null) {
            mLoadTopImage.onLoadTopImage(data.get(0), new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ActivityOptions options = null;

                    Bundle itemInfo = new Bundle();
                    ListMovie currentSelection =data.get(0);
                    itemInfo.putInt(Constants.MOVIE_ID,currentSelection.getId());
                    itemInfo.putString(Constants.INTENT_BUNDLE_TITLE,currentSelection.getTitle());
                    itemInfo.putString(Constants.INTENT_BUNDLE_POSTER_PATH,currentSelection.getPosterPath());
                    itemInfo.putString(Constants.INTENT_BUNDLE_BACKDROP_PATH,currentSelection.getBackdropPath());
                    Bundle detailInfo = new Bundle();
                    detailInfo.putInt(Constants.MOVIE_ID,currentSelection.getId());
                    detailInfo.putString(Constants.INTENT_BUNDLE_OVERVIEW,currentSelection.getOverview());
                    detailInfo.putInt(Constants.INTENT_BUNDLE_VOTE_COUNT,currentSelection.getVoteCount());
                    detailInfo.putDouble(Constants.INTENT_BUNDLE_VOTE_AVERAGE,currentSelection.getVoteAverage());
                    detailInfo.putString(Constants.INTENT_BUNDLE_RELEASE_DATE, Utils.parseToString(currentSelection.getReleaseDate()));
                    Intent intent = new Intent(getContext(), DetailActivity.class);

                    intent.putExtra(Constants.INTENT_BUNDLE,itemInfo);
                    intent.putExtra(Constants.INTENT_BUNDLE_DETAIL,detailInfo);
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                        options = ActivityOptions.makeSceneTransitionAnimation(getActivity(), v, getContext().getString(R.string.picture_transition_name));
                        getContext().startActivity(intent, null);
                    }else {
                        getContext().startActivity(intent);
                    }
                }
            });
        }
        mPosterAdapter.setData(data);

    }

    @Override
    public void onLoaderReset(@NonNull Loader loader) {
        loader.reset();
    }

}
