package com.example.android.movies.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Erzeugt von M. Fengels am 03.04.2018.
 */

/**
 * Basic model for a trailer
 */
public class Trailer {
	private String mKey;
	private String mType;
	private String mName;
	private String mSite;
	
	public Trailer(JSONObject jsonTrailer){
		try {
			mKey = jsonTrailer.getString("key");
			mType = jsonTrailer.getString("type");
			mSite = jsonTrailer.getString("site");
			mName = jsonTrailer.getString("name");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}
	
	public String getSite() {
		return mSite;
	}
	
	public String getName() {
		return mName;
	}
	
	public String getType() {
		return mType;
	}
	
	public String getKey() {
		return mKey;
	}
}
