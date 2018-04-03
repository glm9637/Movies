package com.example.android.movies.networking.loader;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.android.movies.model.Review;
import com.example.android.movies.model.Trailer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Erzeugt von M. Fengels am 03.04.2018.
 */
public class ReviewLoader extends BaseNetworkLoader<ArrayList<Review>> {
	private String mServiceEndpoint;
	
	public ReviewLoader(@NonNull Context context, int movieID) {
		super(context);
		mServiceEndpoint = String.format("/movie/%s/reviews",movieID);
	}
	
	@Override
	protected ArrayList<Review> parseData(String jsonResult) {
		ArrayList<Review> resultData = new ArrayList<>();
		try {
			JSONObject result = new JSONObject(jsonResult);
			JSONArray jsonReviewArray = result.getJSONArray("results");
			for(int i=0;i<jsonReviewArray.length();i++){
				Review review = new Review(jsonReviewArray.getJSONObject(i));
				resultData.add(review);
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
