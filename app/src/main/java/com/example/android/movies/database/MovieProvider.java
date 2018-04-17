package com.example.android.movies.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.CancellationSignal;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Erzeugt von M. Fengels am 06.04.2018.
 */
public class MovieProvider extends ContentProvider {
	
	public static final int MOVIE = 100;
	public static final int MOVIE_WITH_ID = 101;
	public static final int MOVIE_GENRE = 200;
	public static final int GENRE = 300;
	public static final int GENRE_WITH_ID = 301;
	private static final UriMatcher sUriMatcher = buildUriMatcher();
	private MovieDbHelper mMovieDbHelper;
	
	private static UriMatcher buildUriMatcher() {
		UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI(MovieContract.AUTHORITY, MovieContract.PATH_MOVIE , MOVIE);
		uriMatcher.addURI(MovieContract.AUTHORITY, MovieContract.PATH_GENRE , GENRE);
		uriMatcher.addURI(MovieContract.AUTHORITY, MovieContract.PATH_MOVIE_GENRE +"/#", MOVIE_GENRE);
		uriMatcher.addURI(MovieContract.AUTHORITY, MovieContract.PATH_MOVIE + "/#", MOVIE_WITH_ID);
		uriMatcher.addURI(MovieContract.AUTHORITY, MovieContract.PATH_GENRE + "/#", GENRE_WITH_ID);
		
		return uriMatcher;
	}
	
	@Override
	public boolean onCreate() {
		mMovieDbHelper = new MovieDbHelper(getContext());
		return true;
	}
	
	@Nullable
	@Override
	public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
		return query(uri,projection,selection,selectionArgs,sortOrder,null);
	}
	
	@Nullable
	@Override
	public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder, @Nullable CancellationSignal cancellationSignal) {
		SQLiteDatabase db = mMovieDbHelper.getReadableDatabase();
		int match = sUriMatcher.match(uri);
		switch (match) {
			case MOVIE:
				return db.query(MovieContract.MovieEntry.TABLE_NAME,null,selection,selectionArgs,null,null,null);
			case MOVIE_WITH_ID:
				selection = MovieContract.MovieEntry._ID + "=?";
				selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
				return db.query(MovieContract.MovieEntry.TABLE_NAME, null, selection, selectionArgs, null, null, null);
			case MOVIE_GENRE:
				selection = MovieContract.MovieGenreEntry.COLUMN_MOVIE_ID + "=?";
				selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
				Cursor movieGenreCursor = db.query(MovieContract.MovieGenreEntry.TABLE_NAME, null, selection, selectionArgs, null, null, null);
				StringBuilder genreIDBuilder = new StringBuilder();
				genreIDBuilder.append(MovieContract.GenreEntry._ID).append(" in (");
				if (movieGenreCursor.moveToFirst()) {
					do {
						genreIDBuilder.append(movieGenreCursor.getInt(movieGenreCursor.getColumnIndex(MovieContract.MovieGenreEntry.COLUMN_GENRE_ID))).append(", ");
					} while (movieGenreCursor.moveToNext());
					movieGenreCursor.close();
					selection = genreIDBuilder.substring(0, genreIDBuilder.length() - 2) + ")";
					return db.query(MovieContract.GenreEntry.TABLE_NAME, null, selection, null, null, null, null);
				}
				return null;
			case GENRE_WITH_ID:
				selection = MovieContract.GenreEntry._ID + "=?";
				selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
				return db.query(MovieContract.GenreEntry.TABLE_NAME, null, selection, selectionArgs, null, null, null);
			default:
				throw new UnsupportedOperationException("Unknown Uri: " + uri);
		}
		
	}
	
	@Override
	public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
		return 0;
	}
	
	@Nullable
	@Override
	public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
		if(values==null){
			return null;
		}
		SQLiteDatabase db = mMovieDbHelper.getWritableDatabase();
		int match = sUriMatcher.match(uri);
		switch (match) {
			case MOVIE:
				String genres = values.getAsString(MovieContract.GenreEntry.TABLE_NAME);
				int movieID = values.getAsInteger(MovieContract.MovieEntry._ID);
				long insertedRows = 0;
				values.remove(MovieContract.GenreEntry.TABLE_NAME);
				db.insert(MovieContract.MovieEntry.TABLE_NAME,null,values);
				try {
					JSONArray genreArray = new JSONArray(genres);
					for(int i=0;i<genreArray.length();i++){
						JSONObject genre = genreArray.getJSONObject(i);
						ContentValues genreValues = new ContentValues();
						int genreID = genre.getInt(MovieContract.GenreEntry._ID);
						genreValues.put(MovieContract.GenreEntry._ID,genreID);
						genreValues.put(MovieContract.GenreEntry.COLUMN_NAME,genre.getString(MovieContract.GenreEntry.COLUMN_NAME));
						insert(MovieContract.GenreEntry.CONTENT_URI,genreValues);
						
						ContentValues movieGenreValues = new ContentValues();
						movieGenreValues.put(MovieContract.MovieGenreEntry.COLUMN_MOVIE_ID,movieID);
						movieGenreValues.put(MovieContract.MovieGenreEntry.COLUMN_GENRE_ID,genreID);
						try {
							
							insertedRows += db.insert(MovieContract.MovieGenreEntry.TABLE_NAME,null,movieGenreValues);
						}catch (Exception ex){
							ex.printStackTrace();
						}
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				return ContentUris.withAppendedId(uri,movieID);
			case GENRE:
				Uri result = ContentUris.withAppendedId(uri,values.getAsInteger(MovieContract.GenreEntry._ID));
				Cursor genreCursor = query(result,null,null,null,null);
				if(!genreCursor.moveToFirst()){
					db.insert(MovieContract.GenreEntry.TABLE_NAME,null,values);
				}
				genreCursor.close();
				return result;
			case MOVIE_GENRE:
				long id = db.insert(MovieContract.MovieGenreEntry.TABLE_NAME,null,values);
				return ContentUris.withAppendedId(uri,id);
			default:
				throw new UnsupportedOperationException("Unknown Uri: " + uri);
			
		}
	}
	
	@Override
	public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
		SQLiteDatabase db = mMovieDbHelper.getWritableDatabase();
		int match = sUriMatcher.match(uri);
		switch (match) {
			case MOVIE_WITH_ID:
				selection = MovieContract.MovieGenreEntry.COLUMN_MOVIE_ID + "=?";
				selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
				db.delete(MovieContract.MovieGenreEntry.TABLE_NAME, selection, selectionArgs);
				selection = MovieContract.MovieEntry._ID + "=?";
				return db.delete(MovieContract.MovieEntry.TABLE_NAME, selection, selectionArgs);
			default:
				throw new UnsupportedOperationException("Unknown Uri: " + uri);
		}
	}
	
	@Nullable
	@Override
	public String getType(@NonNull Uri uri) {
		return null;
	}
}
