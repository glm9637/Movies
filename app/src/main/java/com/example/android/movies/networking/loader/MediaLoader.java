package com.example.android.movies.networking.loader;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.android.movies.model.Cast;
import com.example.android.movies.model.ListMovie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by glm9637 on 12.03.2018 18:10.
 */

public class MediaLoader extends BaseNetworkLoader<ArrayList<String>> {

    private String mServiceEndpoint;

    public MediaLoader(@NonNull Context context, int movieID) {
        super(context);
        mServiceEndpoint = String.format("/movie/%s/images",movieID);
    }

    /**
     * parses the JsonData to a arrayList of Strings, which represents the image
     */
    @Override
    protected ArrayList<String> parseData(String jsonResult){
        ArrayList<String> resultData = new ArrayList<>();
        try {
            JSONObject result = new JSONObject(jsonResult);
            JSONArray jsonBackdrops = result.getJSONArray("backdrops");
            for(int i=0;i<jsonBackdrops.length();i++){
                JSONObject jsonImage = jsonBackdrops.getJSONObject(i);
                resultData.add(jsonImage.getString("file_path"));
            }
            JSONArray jsonPoster = result.getJSONArray("posters");
            for(int i=0;i<jsonPoster.length();i++){
                JSONObject jsonImage = jsonPoster.getJSONObject(i);
                resultData.add(jsonImage.getString("file_path"));
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
        return new HashMap<>();
    }
}
