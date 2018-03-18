package com.example.android.movies.model;

import android.util.SparseArray;

import com.example.android.movies.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by glm9637 on 11.03.2018 13:30.
 */

public class Movie {
    private boolean mIsAdult;
    private String mBackdropPath;
    private Object mBelongsToCollection;
    private Integer mBudget;
    private SparseArray<String> mGenres;
    private String mHomepage;
    private Integer mId;
    private String mImdbId;
    private String mOriginalLanguage;
    private String mOriginalTitle;
    private String mOverview;
    private Double mPopularity;
    private String mPosterPath;
    private SparseArray<String> mProductionCompanies;
    private SparseArray<String> mProductionCountries;
    private Date mReleaseDate;
    private Integer mRevenue;
    private Integer mRuntime;
    private Map<String, String> mSpokenLanguages;
    private String mStatus;
    private String mTagline;
    private String mTitle;
    private boolean mVideo;
    private Double mVoteAverage;
    private Integer mVoteCount;

    /**
     * create a new Movie Object from a jsonValue
     *
     * @param jsonValue the returned string from theMovieDb for a Movie
     */
    public Movie(String jsonValue) {
        try {
            JSONObject jsonMovie = new JSONObject(jsonValue);
            mIsAdult = jsonMovie.getBoolean("adult");
            mBackdropPath = jsonMovie.getString("backdrop_path");
            //mBelongsToCollection = jsonMovie.getJSONObject("belongs_to_collection");
            mBudget = jsonMovie.getInt("budget");

            JSONArray jsonGenres = jsonMovie.getJSONArray("genres");
            mGenres = new SparseArray<>();
            for (int i = 0; i < jsonGenres.length(); i++) {
                mGenres.append(jsonGenres.getJSONObject(i).getInt("id"),
                        jsonGenres.getJSONObject(i).getString("name"));
            }

            mHomepage = jsonMovie.getString("homepage");
            mId = jsonMovie.getInt("id");
            mImdbId = jsonMovie.getString("imdb_id");
            mOriginalLanguage = jsonMovie.getString("original_language");
            mOriginalTitle = jsonMovie.getString("original_title");
            mOverview = jsonMovie.getString("overview");
            mPopularity = jsonMovie.getDouble("popularity");
            mPosterPath = jsonMovie.getString("poster_path");

            JSONArray jsonProductionCompanies = jsonMovie.getJSONArray("genres");
            mProductionCompanies = new SparseArray<>();
            for (int i = 0; i < jsonGenres.length(); i++) {
                mProductionCompanies.append(jsonProductionCompanies.getJSONObject(i).getInt("id"),
                        jsonProductionCompanies.getJSONObject(i).getString("name"));
            }

            JSONArray jsonProductionCountries = jsonMovie.getJSONArray("genres");
            mProductionCountries = new SparseArray<>();
            for (int i = 0; i < jsonGenres.length(); i++) {
                mProductionCountries.append(jsonProductionCountries.getJSONObject(i).getInt("id"),
                        jsonProductionCountries.getJSONObject(i).getString("name"));
            }
            mReleaseDate = Utils.parseToDate(jsonMovie.getString("release_date"));

            mRevenue = jsonMovie.getInt("revenue");
            mRuntime = jsonMovie.getInt("runtime");

            JSONArray jsonSpokenLanguages = jsonMovie.getJSONArray("spoken_languages");
            mSpokenLanguages = new HashMap<>();
            for (int i = 0; i < jsonGenres.length(); i++) {
                mSpokenLanguages.put(jsonSpokenLanguages.getJSONObject(i).getString("iso_639_1"),
                        jsonSpokenLanguages.getJSONObject(i).getString("name"));
            }

            mStatus = jsonMovie.getString("status");
            mTagline = jsonMovie.getString("tagline");
            mTitle = jsonMovie.getString("title");
            mVideo = jsonMovie.getBoolean("video");
            mVoteAverage = jsonMovie.getDouble("vote_average");
            mVoteCount = jsonMovie.getInt("vote_count");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public boolean isAdult() {
        return mIsAdult;
    }

    public String getBackdropPath() {
        return mBackdropPath;
    }

    public Object getBelongsToCollection() {
        return mBelongsToCollection;
    }

    public int getBudget() {
        return mBudget;
    }

    public SparseArray<String> getGenres() {
        return mGenres;
    }

    public String getHomepage() {
        return mHomepage;
    }

    public int getId() {
        return mId;
    }

    public String getImdbId() {
        return mImdbId;
    }

    public String getOriginalLanguage() {
        return mOriginalLanguage;
    }

    public String getOriginalTitle() {
        return mOriginalTitle;
    }

    public String getOverview() {
        return mOverview;
    }

    public Double getPopularity() {
        return mPopularity;
    }

    public String getPosterPath() {
        return mPosterPath;
    }

    public SparseArray<String> getProductionCompanies() {
        return mProductionCompanies;
    }

    public SparseArray<String> getProductionCountries() {
        return mProductionCountries;
    }

    public Date getReleaseDate() {
        return mReleaseDate;
    }

    public int getRevenue() {
        return mRevenue;
    }

    public int getRuntime() {
        return mRuntime;
    }

    public Map<String, String> getSpokenLanguages() {
        return mSpokenLanguages;
    }

    public String getStatus() {
        return mStatus;
    }

    public String getTagline() {
        return mTagline;
    }

    public String getTitle() {
        return mTitle;
    }

    public boolean isVideo() {
        return mVideo;
    }

    public Double getVoteAverage() {
        return mVoteAverage;
    }

    public int getVoteCount() {
        return mVoteCount;
    }
}
