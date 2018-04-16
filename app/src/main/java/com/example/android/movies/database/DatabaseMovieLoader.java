package com.example.android.movies.database;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import com.example.android.movies.model.Movie;

/**
 * Erzeugt von M. Fengels am 13.04.2018.
 */
public class DatabaseMovieLoader extends AsyncTaskLoader<Movie> {
	private int mMovieID;
	
	
	public DatabaseMovieLoader(Context context,int movieID){
		super(context);
		mMovieID =movieID;
	}
	
	@Nullable
	@Override
	public Movie loadInBackground() {
		Cursor movieData = getContext().getContentResolver().query(ContentUris.withAppendedId(MovieContract.MovieEntry.CONTENT_URI,mMovieID),null,null,null,null);
		if(movieData.moveToFirst()) {
			Cursor genreData = getContext().getContentResolver().query(ContentUris.withAppendedId(MovieContract.MovieGenreEntry.CONTENT_URI, mMovieID), null, null, null, null);
			Movie movie =new Movie(movieData, genreData);
			movieData.close();
			genreData.close();
			return movie;
			
		}
		return null;
	}
}
