package com.example.android.movies.networking.loader;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.android.movies.model.Movie;

import java.util.HashMap;

/**
 * Created by glm9637 on 15.03.2018 20:24.
 */

public class MovieLoader extends BaseNetworkLoader<Movie> {

    private String mServiceEndpoint ="/movie/";
    private Integer mMovieId;

    public MovieLoader(@NonNull Context context, @NonNull Integer movieId) {
        super(context);
        mMovieId = movieId;
    }

    /**
     * parses the JsonData to a Movie Object
     */
    @Override
    protected Movie parseData(String jsonResult) {
        Movie objMovie;
        objMovie = new Movie(jsonResult);
        return objMovie;
    }

    @Override
    protected String getServiceEndpoint() {
        return mServiceEndpoint+mMovieId;
    }

    @Override
    protected HashMap<String, String> getAdditionalQueryParameters() {
        return  new HashMap<>();
    }

}
