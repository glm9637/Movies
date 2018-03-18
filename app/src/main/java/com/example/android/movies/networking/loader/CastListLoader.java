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
 * Created by glm9637 on 18.03.2018 17:20.
 */

public class CastListLoader extends BaseNetworkLoader<ArrayList<Cast>> {

    private String mServiceEndpoint;

    public CastListLoader(@NonNull Context context, int movieID) {
        super(context);
        mServiceEndpoint = String.format("/movie/%s/credits",movieID);
    }


    /**
     * parses the JsonData to a arrayList of Cast Objects
     */
    @Override
    protected ArrayList<Cast> parseData(String jsonResult){
        ArrayList<Cast> resultData = new ArrayList<>();
        try {
            JSONObject result = new JSONObject(jsonResult);
            JSONArray jsonCast = result.getJSONArray("cast");
            for(int i=0;i<jsonCast.length();i++){
                JSONObject jsonCastItem = jsonCast.getJSONObject(i);
                Cast currentCast = new Cast(jsonCastItem);
                resultData.add(currentCast);
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
