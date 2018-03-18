package com.example.android.movies;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.example.android.movies.fragments.overview.MoviePosterFragment;
import com.example.android.movies.fragments.overview.PopularMoviesFragment;
import com.example.android.movies.fragments.overview.TopRatedFragment;
import com.example.android.movies.fragments.overview.UpcomingMoviesFragment;
import com.example.android.movies.model.ListMovie;
import com.example.android.movies.networking.NetworkHelper;
import com.squareup.picasso.Picasso;

public class OverviewActivity extends BottomNavigationActivity implements MoviePosterFragment.LoadTopImage {

    private AppBarLayout mAppBarLayout;
    private ImageView mTopImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTopImageView = findViewById(R.id.iv_top);
        setupToolbar(R.string.app_name);
    }

    /**
     * Sets up the Toolbartitle
     * @param textID
     */
    private void setupToolbar(int textID) {
        Toolbar toolbar = findViewById(R.id.toolbar);
        mAppBarLayout = findViewById(R.id.app_bar_layout);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(textID);
        }
    }

    /**
     * adds the Fragment to the BottomNavigation and the onNavigationItemSelectedListener, which expands the appBar
     */
    protected void setupFragments() {
        mNavigationItemSelectedListener = new NavigationItemSelected() {
            @Override
            public void onNavigationItemSelected(MenuItem item) {
                    mAppBarLayout.setExpanded(true, true);
            }

            @Override
            public void onNavigationItemReSelected(MenuItem item) {

            }
        };

        MoviePosterFragment topRatedMovies = new TopRatedFragment();
        topRatedMovies.setLoadTopImageListener(OverviewActivity.this);
        addFragment(R.id.action_top_rated,topRatedMovies);
        MoviePosterFragment popularMovies = new PopularMoviesFragment();
        popularMovies.setLoadTopImageListener(OverviewActivity.this);
        addFragment(R.id.action_popular,popularMovies);
        MoviePosterFragment upcomingMovies = new UpcomingMoviesFragment();
        upcomingMovies.setLoadTopImageListener(OverviewActivity.this);
        addFragment(R.id.action_upcoming,upcomingMovies);
        setStartFragment(R.id.action_top_rated);

    }

    @Override
    protected int getContentViewID() {
        return R.layout.activity_overview;
    }

    /**
     * handles the Loading of the Top image from the Fragments
     * @param topMovie the Movie to be displayed
     * @param onPosterClick the OnClick Action for the ImageView
     */
    @Override
    public void onLoadTopImage(ListMovie topMovie, View.OnClickListener onPosterClick) {
        mTopImageView.setOnClickListener(onPosterClick);
        Picasso.with(this).load(NetworkHelper.getImageUrl(topMovie.getPosterPath(), NetworkHelper.ImageSize.medium)).into(mTopImageView);
    }


}
