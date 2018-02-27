package com.example.android.movies;

import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public class Overview extends AppCompatActivity {

	private FragmentManager mFragmentManager;
	private MoviePosterFragment mPopularMovies;
	private MoviePosterFragment mTopRatedMovies;
	private MoviePosterFragment mUpcomingMovies;
	private AppBarLayout mAppBarLayout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_overview);
		setupToolbar(R.string.app_name);
		setupFragments();
	}
	
	private void setupToolbar(int textID) {
		Toolbar toolbar = findViewById(R.id.toolbar);
		mAppBarLayout = findViewById(R.id.app_bar_layout);
		setSupportActionBar(toolbar);
		if(getSupportActionBar()!=null) {
			getSupportActionBar().setTitle(textID);
			getSupportActionBar().setDisplayShowTitleEnabled(false);
		}
	}
	
	private void setupFragments(){
		
		BottomNavigationView navigationView = findViewById(R.id.bottom_navigation);
		
		navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
			@Override
			public boolean onNavigationItemSelected(@NonNull MenuItem item) {
				switch (item.getItemId()){
					case R.id.action_top_rated:
						pushFragment(mTopRatedMovies);
						break;
					case R.id.action_popular:
						if(mPopularMovies == null){
							mPopularMovies = new MoviePosterFragment();
							mAppBarLayout.setExpanded(true,true);
						}
						pushFragment(mPopularMovies);
						break;
					case R.id.action_upcoming:
						if(mUpcomingMovies == null){
							mUpcomingMovies = new MoviePosterFragment();
							mAppBarLayout.setExpanded(true,true);
						}
						pushFragment(mUpcomingMovies);
						break;
				}
				return true;
			}
		});
		
		navigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
			@Override
			public void onNavigationItemReselected(@NonNull MenuItem item) {
				switch (item.getItemId()){
					case R.id.action_top_rated:
						mTopRatedMovies.ScrollToTop();
						break;
					case R.id.action_popular:
						mPopularMovies.ScrollToTop();
						break;
					case R.id.action_upcoming:
						mUpcomingMovies.ScrollToTop();
						break;
				}
				mAppBarLayout.setExpanded(true,true);
			}
		});
		
		mFragmentManager = getSupportFragmentManager();
		
		mTopRatedMovies = new MoviePosterFragment();
		
		FragmentTransaction objFragmentTransaction = mFragmentManager.beginTransaction();
		objFragmentTransaction.add(R.id.fragment_container, mTopRatedMovies);
		objFragmentTransaction.commit();
	}
	
	private void pushFragment(MoviePosterFragment newFragment) {
		
		FragmentTransaction objFragmentTransaction = mFragmentManager.beginTransaction();
		objFragmentTransaction.replace(R.id.fragment_container, newFragment);
		objFragmentTransaction.addToBackStack(null);
		objFragmentTransaction.commit();
		
	}
}
