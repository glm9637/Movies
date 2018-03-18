package com.example.android.movies.networking.loader;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.android.movies.model.ListMovie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by glm9637 on 12.03.2018 18:10.
 */

public class MovieListLoader extends BaseNetworkLoader<ArrayList<ListMovie>> {

    private int mPage = 1;
    private String mServiceEndpoint;

    public MovieListLoader(@NonNull Context context,@NonNull String endpoint) {
        super(context);
        mServiceEndpoint = endpoint;
    }

    public void setPage(int page){
        if(page != mPage){
            mPage = page;
            reset();
        }
    }

    /**
     * parses the JsonData to a arrayList of ListMovie Objects
     */
    @Override
    protected ArrayList<ListMovie> parseData(String jsonResult){
        ArrayList<ListMovie> resultData = new ArrayList<>();
        try {
            JSONObject result = new JSONObject(jsonResult);
            JSONArray jsonMovies = result.getJSONArray("results");
            for(int i=0;i<jsonMovies.length();i++){
                JSONObject jsonMovie = jsonMovies.getJSONObject(i);
                ListMovie currentMovie = new ListMovie(jsonMovie);
                resultData.add(currentMovie);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return resultData;
    }

    @Override
    protected String getServiceEndpoint() {
        return mServiceEndpoint;
    }

    @Override
    protected HashMap<String, String> getAdditionalQueryParameters() {
        HashMap<String, String> queryParameters = new HashMap<>();
        queryParameters.put("page", String.valueOf(mPage));
        return queryParameters;
    }
}
