package com.example.android.movies.networking.loader;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.example.android.movies.R;
import com.example.android.movies.networking.NetworkHelper;

import java.util.HashMap;

/**
 * Created by glm9637 on 12.03.2018 18:00.
 */

/**
 * Basic Loader to Fetch the Data from TheMovieDb
 * @param <T> The Type of the Data to fetch
 */
public abstract class BaseNetworkLoader<T> extends AsyncTaskLoader<T> {

    private T mData;
    private boolean hasResult = false;

    protected abstract T parseData(String jsonResult);

    protected abstract String getServiceEndpoint();

    protected abstract HashMap<String, String> getAdditionalQueryParameters();

    BaseNetworkLoader(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        if (takeContentChanged())
            forceLoad();
        else if (hasResult)
            deliverResult(mData);
    }

    /**
     * Loads the Data and calls the parseData Method, to parse the JsonValue to the desired Object
     * @return
     */
    @Nullable
    @Override
    protected T onLoadInBackground() {
        Log.w("Loader","onLoadInBackground");
        HashMap<String, String> queryParameters = getAdditionalQueryParameters();
        if(queryParameters==null){
            queryParameters=new HashMap<>();
        }
        queryParameters.put("api_key",getContext().getString(R.string.the_movie_db));
        String result = NetworkHelper.loadData(getServiceEndpoint(),getContext(),queryParameters);

        return parseData(result);
    }

    @Override
    public void deliverResult(final T data) {
        mData = data;
        hasResult = true;
        super.deliverResult(data);
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
        if (hasResult) {
            mData = null;
            hasResult = false;
        }
    }

    @Nullable
    @Override
    public T loadInBackground() {
        return null;
    }
}
