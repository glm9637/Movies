package com.example.android.movies.database;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Erzeugt von M. Fengels am 06.04.2018.
 */
public class MovieContract {
	
	public static final String AUTHORITY = "com.exeamlpe.android.movies";
	public static final Uri BASE_CONTENT_URL = Uri.parse("content://"+AUTHORITY);
	
	public static final String PATH_MOVIE = "Movie";
	public static final String PATH_GENRE = "Genre";
	public static final String PATH_MOVIE_GENRE = "MovieGenre";
	
	public static final class MovieEntry implements BaseColumns {
		public static final Uri CONTENT_URI = BASE_CONTENT_URL.buildUpon().appendEncodedPath(PATH_MOVIE).build();
		public static final String TABLE_NAME = "Movie";
		
		public static final String COLUMN_NAME = "name";
		public static final String COLUMN_RATING = "rating";
		public static final String COLUMN_VOTE_COUNT = "votes";
		public static final String COLUMN_DESCRIPTION = "description";
		public static final String COLUMN_RUNTIME = "runtime";
		public static final String COLUMN_RELEASE = "releasedate";
		public static final String COLUMN_BUDGET = "budget";
		public static final String COLUMN_REVENUE = "revenue";
		public static final String COLUMN_POSTER = "poster";
		public static final String COLUMN_BACKDROP = "backdropth";
		
		public static final String INDEX_MOVIE_NAME = "IX_Movie_Name";
	}
	
	public static final class GenreEntry implements BaseColumns {
		public static final Uri CONTENT_URI = BASE_CONTENT_URL.buildUpon().appendEncodedPath(PATH_GENRE).build();
		public static final String TABLE_NAME = "Genre";
		
		public static final String COLUMN_NAME = "name";
	}
	
	public static final class MovieGenreEntry implements BaseColumns {
		public static final Uri CONTENT_URI = BASE_CONTENT_URL.buildUpon().appendEncodedPath(PATH_MOVIE_GENRE).build();
		public static final String TABLE_NAME = "MovieGenre";
		
		public static final String COLUMN_MOVIE_ID = "movieId";
		public static final String COLUMN_GENRE_ID = "genreId";
	}
	
}
