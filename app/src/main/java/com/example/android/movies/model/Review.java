package com.example.android.movies.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Erzeugt von M. Fengels am 03.04.2018.
 */
public class Review {
	
	private String mAuthor;
	private String mReview;
	
	public Review(JSONObject jsonReview){
		try {
			mAuthor = jsonReview.getString("author");
			mReview = jsonReview.getString("content");
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	
	public String getAuthor() {
		return mAuthor;
	}
	
	public String getReview() {
		return mReview;
	}
}
