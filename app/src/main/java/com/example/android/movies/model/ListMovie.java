package com.example.android.movies.model;

import android.os.Parcel;
import android.os.Parcelable;

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
public class ListMovie implements Parcelable {

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
	
	protected ListMovie(Parcel in) {
		mPosterPath = in.readString();
		mIsAdult = in.readByte() != 0;
		mOverview = in.readString();
		if (in.readByte() == 0) {
			mId = null;
		} else {
			mId = in.readInt();
		}
		mOriginalLanguage = in.readString();
		mOriginalTitle = in.readString();
		mTitle = in.readString();
		mBackdropPath = in.readString();
		if (in.readByte() == 0) {
			mPopularity = null;
		} else {
			mPopularity = in.readDouble();
		}
		if (in.readByte() == 0) {
			mVoteCount = null;
		} else {
			mVoteCount = in.readInt();
		}
		mVideo = in.readByte() != 0;
		if (in.readByte() == 0) {
			mVoteAverage = null;
		} else {
			mVoteAverage = in.readDouble();
		}
		
		mReleaseDate =  new Date(in.readLong());
	}
	
	public static final Creator<ListMovie> CREATOR = new Creator<ListMovie>() {
		@Override
		public ListMovie createFromParcel(Parcel in) {
			return new ListMovie(in);
		}
		
		@Override
		public ListMovie[] newArray(int size) {
			return new ListMovie[size];
		}
	};
	
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
	
	@Override
	public int describeContents() {
		return 0;
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		
		dest.writeString(mPosterPath);
		dest.writeByte((byte) (mIsAdult ? 1 : 0));
		dest.writeString(mOverview);
		if (mId == null) {
			dest.writeByte((byte) 0);
		} else {
			dest.writeByte((byte) 1);
			dest.writeInt(mId);
		}
		dest.writeString(mOriginalLanguage);
		dest.writeString(mOriginalTitle);
		dest.writeString(mTitle);
		dest.writeString(mBackdropPath);
		if (mPopularity == null) {
			dest.writeByte((byte) 0);
		} else {
			dest.writeByte((byte) 1);
			dest.writeDouble(mPopularity);
		}
		if (mVoteCount == null) {
			dest.writeByte((byte) 0);
		} else {
			dest.writeByte((byte) 1);
			dest.writeInt(mVoteCount);
		}
		dest.writeByte((byte) (mVideo ? 1 : 0));
		if (mVoteAverage == null) {
			dest.writeByte((byte) 0);
		} else {
			dest.writeByte((byte) 1);
			dest.writeDouble(mVoteAverage);
		}
		
			dest.writeLong(mReleaseDate.getTime());
		
	}
	
	public String getLink() {
		return "https://www.themoviedb.org/movie/"+mId;
	}
}
