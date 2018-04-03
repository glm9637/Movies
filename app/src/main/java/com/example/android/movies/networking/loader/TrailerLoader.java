package com.example.android.movies.networking.loader;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.android.movies.model.Trailer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Erzeugt von M. Fengels am 03.04.2018.
 */
public class TrailerLoader extends BaseNetworkLoader<ArrayList<Trailer>> {
	private String mServiceEndpoint;
	
	public TrailerLoader(@NonNull Context context, int movieID) {
		super(context);
		mServiceEndpoint = String.format("/movie/%s/videos",movieID);
	}
	
	@Override
	protected ArrayList<Trailer> parseData(String jsonResult) {
		ArrayList<Trailer> resultData = new ArrayList<>();
		try {
			JSONObject result = new JSONObject(jsonResult);
			JSONArray jsonTrailerArray = result.getJSONArray("results");
			for(int i=0;i<jsonTrailerArray.length();i++){
				Trailer trailer = new Trailer(jsonTrailerArray.getJSONObject(i));
				if(trailer.getSite().toLowerCase().equals("youtube"))
					resultData.add(trailer);
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
		return null;
	}
}
