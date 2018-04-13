package com.example.android.movies.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.SparseArray;

import com.example.android.movies.database.MovieContract;
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
			for (int i = 0; i < jsonSpokenLanguages.length(); i++) {
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
	
	public Movie(ContentValues values) {
		mId = values.getAsInteger(MovieContract.MovieEntry._ID);
		mTitle = values.getAsString(MovieContract.MovieEntry.COLUMN_NAME);
		mVoteAverage = values.getAsDouble(MovieContract.MovieEntry.COLUMN_RATING);
		mVoteCount = values.getAsInteger(MovieContract.MovieEntry.COLUMN_VOTE_COUNT);
		mOverview = values.getAsString(MovieContract.MovieEntry.COLUMN_DESCRIPTION);
		mRuntime = values.getAsInteger(MovieContract.MovieEntry.COLUMN_RUNTIME);
		mReleaseDate = Utils.parseToDate(values.getAsString(MovieContract.MovieEntry.COLUMN_RELEASE));
		mBudget = values.getAsInteger(MovieContract.MovieEntry.COLUMN_BUDGET);
		mRevenue = values.getAsInteger(MovieContract.MovieEntry.COLUMN_REVENUE);
		mGenres = new SparseArray<>();
		try {
			JSONArray genres = new JSONArray(values.getAsString(MovieContract.GenreEntry.TABLE_NAME));
			for (int i = 0; i < genres.length(); i++) {
				int id = genres.getJSONObject(i).getInt(MovieContract.GenreEntry._ID);
				String name = genres.getJSONObject(i).getString(MovieContract.GenreEntry.COLUMN_NAME);
				mGenres.append(id, name);
			}
		} catch (JSONException ex) {
			ex.printStackTrace();
		}
	}
	
	public Movie(Cursor movieData, Cursor genreData) {
		movieData.moveToFirst();
		mId = movieData.getInt(movieData.getColumnIndex(MovieContract.MovieEntry._ID));
		mTitle = movieData.getString(movieData.getColumnIndex(MovieContract.MovieEntry.COLUMN_NAME));
		mVoteAverage = movieData.getDouble(movieData.getColumnIndex(MovieContract.MovieEntry.COLUMN_RATING));
		mVoteCount = movieData.getInt(movieData.getColumnIndex(MovieContract.MovieEntry.COLUMN_VOTE_COUNT));
		mOverview = movieData.getString(movieData.getColumnIndex(MovieContract.MovieEntry.COLUMN_DESCRIPTION));
		mRuntime = movieData.getInt(movieData.getColumnIndex(MovieContract.MovieEntry.COLUMN_RUNTIME));
		mReleaseDate = Utils.parseToDate(movieData.getString(movieData.getColumnIndex(MovieContract.MovieEntry.COLUMN_RELEASE)));
		mBudget = movieData.getInt(movieData.getColumnIndex(MovieContract.MovieEntry.COLUMN_BUDGET));
		mRevenue = movieData.getInt(movieData.getColumnIndex(MovieContract.MovieEntry.COLUMN_REVENUE));
		movieData.close();
		mGenres = new SparseArray<>();
		if (genreData.moveToFirst()) {
			do{
				int id = genreData.getInt(genreData.getColumnIndex(MovieContract.GenreEntry._ID));
				String name = genreData.getString(genreData.getColumnIndex(MovieContract.GenreEntry.COLUMN_NAME));
				mGenres.append(id, name);
			}while (genreData.moveToFirst());
		}
		genreData.close();
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
	
	public Integer getBudget() {
		return mBudget;
	}
	
	public SparseArray<String> getGenres() {
		return mGenres;
	}
	
	public String getHomepage() {
		return mHomepage;
	}
	
	public Integer getId() {
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
	
	public Integer getRevenue() {
		return mRevenue;
	}
	
	public Integer getRuntime() {
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
	
	private String getGenreListeAsJson() {
		if (mGenres == null) {
			return "";
		}
		JSONArray genreList = new JSONArray();
		for (int i = 0; i < mGenres.size(); i++) {
			JSONObject genre = new JSONObject();
			try {
				genre.put(MovieContract.GenreEntry._ID, mGenres.keyAt(i));
				genre.put(MovieContract.GenreEntry.COLUMN_NAME, mGenres.valueAt(i));
				genreList.put(genre);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return genreList.toString();
	}
	
	
	/**
	 * Creates the content value to save the Movie, but leaves out the path for the Backdrop and the Poster Images
	 *
	 * @return the filled ContentValues describing the Movie
	 */
	public ContentValues getContentValues() {
		ContentValues cv = new ContentValues();
		cv.put(MovieContract.GenreEntry.TABLE_NAME, getGenreListeAsJson());
		cv.put(MovieContract.MovieEntry._ID, mId);
		cv.put(MovieContract.MovieEntry.COLUMN_NAME, mTitle);
		cv.put(MovieContract.MovieEntry.COLUMN_RATING, mVoteAverage);
		cv.put(MovieContract.MovieEntry.COLUMN_VOTE_COUNT, mVoteCount);
		cv.put(MovieContract.MovieEntry.COLUMN_DESCRIPTION, mOverview);
		cv.put(MovieContract.MovieEntry.COLUMN_RUNTIME, mRuntime);
		cv.put(MovieContract.MovieEntry.COLUMN_RELEASE, Utils.parseToString(mReleaseDate));
		cv.put(MovieContract.MovieEntry.COLUMN_BUDGET, mBudget);
		cv.put(MovieContract.MovieEntry.COLUMN_REVENUE, mRevenue);
		return cv;
	}
}
