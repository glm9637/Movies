package com.example.android.movies;

import android.app.Application;
import com.facebook.stetho.Stetho;

/**
 * Erzeugt von M. Fengels am 17.04.2018.
 */
public class MovieApplication extends Application {
	
	public void onCreate() {
		super.onCreate();
		Stetho.initializeWithDefaults(this);
	}
}
