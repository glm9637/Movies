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

import com.example.android.movies.MediaAdapter;
import com.example.android.movies.R;
import com.example.android.movies.networking.loader.MediaLoader;
import com.example.android.movies.utils.Constants;

import java.util.ArrayList;

/**
 * Created by glm9637 on 18.03.2018 17:58.
 */

public class MediaFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<String>> {


    private RecyclerView mImageList;
    private MediaAdapter mMediaAdapter;
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
        mImageList = rootView.findViewById(R.id.recycler_overview);
        LinearLayoutManager listManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        mImageList.setLayoutManager(listManager);
        mMediaAdapter = new MediaAdapter(getContext());
        mImageList.setAdapter(mMediaAdapter);

        if (getActivity() != null)
            getActivity().getSupportLoaderManager().initLoader(Constants.LOADER_ID_MEDIA, null, this).forceLoad();

        return rootView;
    }

    @NonNull
    @Override
    public Loader<ArrayList<String>> onCreateLoader(int id, @Nullable Bundle args) {
        return new MediaLoader(mContext, getArguments().getInt(Constants.MOVIE_ID));
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<String>> loader, ArrayList<String> data) {
        if (loader.getId() == Constants.LOADER_ID_MEDIA) {
            mMediaAdapter.setData(data);
        }

    }

    @Override
    public void onLoaderReset(@NonNull Loader loader) {
        loader.reset();
    }

}
