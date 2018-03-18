package com.example.android.movies.model;

import com.example.android.movies.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by glm9637 on 11.03.2018 14:15.
 */

/**
 * Model for saving a Movie, which gets returned by the api in a list
 */
public class ListMovie {

    private String mPosterPath;
    private boolean mIsAdult;
    private String mOverview;
    private Date mReleaseDate;
    private List<Integer> mGenreIds;
    private Integer mId;
    private String mOriginalLanguage;
    private String mOriginalTitle;
    private String mTitle;
    private String mBackdropPath;
    private Double mPopularity;
    private Integer mVoteCount;
    private boolean mVideo;
    private Double mVoteAverage;

    /**
     * create a new ListMovie Object from a jsonValue
     *
     * @param jsonMovie one JsonObject parsed from the returned Value for a cast query
     */
    public ListMovie(JSONObject jsonMovie) {
        try {
            mPosterPath = jsonMovie.getString("poster_path");
            mIsAdult = jsonMovie.getBoolean("adult");
            mOverview = jsonMovie.getString("overview");
            mReleaseDate = Utils.parseToDate(jsonMovie.getString("release_date"));

            JSONArray jsonGenres = jsonMovie.getJSONArray("genre_ids");
            mGenreIds = new ArrayList<>();
            for (int i = 0; i < jsonGenres.length(); i++) {
                mGenreIds.add(jsonGenres.getInt(i));
            }

            mId = jsonMovie.getInt("id");

            mOriginalLanguage = jsonMovie.getString("original_language");
            mOriginalTitle = jsonMovie.getString("original_title");
            mTitle = jsonMovie.getString("title");
            mBackdropPath = jsonMovie.getString("backdrop_path");
            mPopularity = jsonMovie.getDouble("popularity");
            mVoteCount = jsonMovie.getInt("vote_count");
            mVideo = jsonMovie.getBoolean("video");
            mVoteAverage = jsonMovie.getDouble("vote_average");


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public String getPosterPath() {
        return mPosterPath;
    }

    public boolean isAdult() {
        return mIsAdult;
    }

    public String getOverview() {
        return mOverview;
    }

    public Date getReleaseDate() {
        return mReleaseDate;
    }

    public List<Integer> getGenreIds() {
        return mGenreIds;
    }

    public Integer getId() {
        return mId;
    }

    public String getOriginalLanguage() {
        return mOriginalLanguage;
    }

    public String getOriginalTitle() {
        return mOriginalTitle;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getBackdropPath() {
        return mBackdropPath;
    }

    public Double getPopularity() {
        return mPopularity;
    }

    public Integer getVoteCount() {
        return mVoteCount;
    }

    public boolean isVideo() {
        return mVideo;
    }

    public Double getVoteAverage() {
        return mVoteAverage;
    }
}
