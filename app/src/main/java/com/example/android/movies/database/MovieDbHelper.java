package com.example.android.movies.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.android.movies.database.MovieContract.MovieEntry;
import com.example.android.movies.database.MovieContract.GenreEntry;
import com.example.android.movies.database.MovieContract.MovieGenreEntry;


/**
 * Erzeugt von M. Fengels am 06.04.2018.
 */
public class MovieDbHelper extends SQLiteOpenHelper {
	
	private static final String DATABASE_NAME = "Movie.db";
	
	private static final int DATABASE_VERSION = 1;
	
	public MovieDbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		String createTable;
		
		createTable = "CREATE TABLE " + GenreEntry.TABLE_NAME + " (" +
				GenreEntry._ID + " INTEGER PRIMARY KEY, " +
				GenreEntry.COLUMN_NAME + " TEXT NOT NULL)";
		
		db.execSQL(createTable);
		
		createTable = "CREATE TABLE " + MovieEntry.TABLE_NAME + " (" +
				MovieEntry._ID + " INTEGER PRIMARY KEY, " +
				MovieEntry.COLUMN_NAME + " TEXT, " +
				MovieEntry.COLUMN_RATING + " REAL, " +
				MovieEntry.COLUMN_VOTE_COUNT + " INTEGER , " +
				MovieEntry.COLUMN_DESCRIPTION + " TEXT, " +
				MovieEntry.COLUMN_RUNTIME + " INTEGER , " +
				MovieEntry.COLUMN_RELEASE + " TEXT, " +
				MovieEntry.COLUMN_BUDGET + " INTEGER , " +
				MovieEntry.COLUMN_REVENUE + " INTEGER , " +
				MovieEntry.COLUMN_POSTER + " TEXT, " +
				MovieEntry.COLUMN_BACKDROP + " TEXT)";
		
		db.execSQL(createTable);
		
		createTable = "CREATE TABLE " + MovieGenreEntry.TABLE_NAME + " (" +
				MovieGenreEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
				MovieGenreEntry.COLUMN_GENRE_ID + " INTEGER NOT NULL, " +
				MovieGenreEntry.COLUMN_MOVIE_ID + " INTEGER NOT NULL, " +
				"FOREIGN KEY ("+MovieGenreEntry.COLUMN_MOVIE_ID+") REFERENCES "+ MovieEntry.TABLE_NAME+"("+MovieEntry._ID+"), " +
				"FOREIGN KEY ("+MovieGenreEntry.COLUMN_GENRE_ID+") REFERENCES "+ GenreEntry.TABLE_NAME+"("+GenreEntry._ID+"))";
		
		db.execSQL(createTable);
		
		String createIndex = "CREATE INDEX %s ON %s(%s)";
		db.execSQL(String.format(createIndex, MovieEntry.INDEX_MOVIE_NAME, MovieEntry.TABLE_NAME,MovieEntry.COLUMN_NAME));
		
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + MovieGenreEntry.TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + MovieEntry.TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + GenreEntry.TABLE_NAME);
		onCreate(db);
	}
}
