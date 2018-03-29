package com.example.android.movies;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.movies.fragments.detail.CastFragment;
import com.example.android.movies.fragments.detail.InfoFragment;
import com.example.android.movies.fragments.detail.MediaFragment;
import com.example.android.movies.model.ListMovie;
import com.example.android.movies.networking.NetworkHelper;
import com.example.android.movies.utils.Constants;
import com.squareup.picasso.Picasso;

public class DetailActivity extends BottomNavigationActivity
        implements AppBarLayout.OnOffsetChangedListener {

    private static final float PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR = 0.9f;
    private static final float PERCENTAGE_TO_HIDE_TITLE_DETAILS = 0.3f;
    private static final float PERCENTAGE_TO_HIDE_FAVORITE_BUTTON = 0.8f;
    private static final int ALPHA_ANIMATIONS_DURATION = 200;

    private boolean mIsTheTitleVisible = false;
    private boolean mIsTheTitleContainerVisible = true;
    private boolean mIsFavorizeButtonVisible = true;

    private LinearLayout mTitleContainer;
    private TextView mTitle;
    private FloatingActionButton mFavorize;
    private AppBarLayout mAppBarLayout;
    private Toolbar mToolbar;
    private ImageView mPosterImageView;
    private ImageView mBackDropImageView;
    private TextView mTitleTextExpanded;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindActivity();
        initUI();
        mAppBarLayout.addOnOffsetChangedListener(this);
        startAlphaAnimation(mTitle, 0, View.INVISIBLE);
    }

    @Override
    protected void setupFragments() {
        InfoFragment infoFragment = new InfoFragment();
        infoFragment.setArguments(getIntent().getBundleExtra(Constants.INTENT_BUNDLE));
        addFragment(R.id.action_description, infoFragment);

        CastFragment castFragment = new CastFragment();
        castFragment.setArguments(getIntent().getBundleExtra(Constants.INTENT_BUNDLE));
        addFragment(R.id.action_actors,castFragment);

        MediaFragment mediaFragment = new MediaFragment();
        mediaFragment.setArguments(getIntent().getBundleExtra(Constants.INTENT_BUNDLE));
        addFragment(R.id.action_media,mediaFragment);

        setStartFragment(R.id.action_description);
    }


    @Override
    protected int getContentViewID() {
        return R.layout.activity_detail;
    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
    	getMenuInflater().inflate(R.menu.menu_detail,menu);
		return true;
	}
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		
		if(!mIsFavorizeButtonVisible) {
			menu.add(0,0,0,"Favorize")
					.setIcon(R.drawable.ic_favorite_border_white_24dp)
					.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		}
		return super.onPrepareOptionsMenu(menu);
	}
	
	private void bindActivity() {
        mToolbar = findViewById(R.id.main_toolbar);
        mTitle = findViewById(R.id.main_textview_title);
        mTitleContainer = findViewById(R.id.main_linearlayout_title);
        mAppBarLayout = findViewById(R.id.main_appbar);
        mPosterImageView = findViewById(R.id.poster_view);
        mBackDropImageView = findViewById(R.id.backdrop_view);
        mTitleTextExpanded = findViewById(R.id.title_text_expanded);
        mFavorize = findViewById(R.id.btn_favorize);
    }

    /**
     * Handles the Alpha and Visibility changes when the offset changes
     */
    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int offset) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(offset) / (float) maxScroll;

        handleAlphaOnTitle(percentage);
        handleToolbarTitleVisibility(percentage);
        handleFavoriteFabVisibility(percentage);
    }

    /**
     * Hides the Title of the Toolbar when it is collapsed further than the value defined in PERCANTAGE_TO_SHOW_TITLE_AT_TOOLBAR
     * @param percentage the current percentage
     */
    private void handleToolbarTitleVisibility(float percentage) {
        if (percentage >= PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR) {
	
	        if (!mIsTheTitleVisible) {
		        startAlphaAnimation(mTitle, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
		        mIsTheTitleVisible = true;
	        }
	
        } else {
	
	        if (mIsTheTitleVisible) {
		        startAlphaAnimation(mTitle, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
		        mIsTheTitleVisible = false;
	        }
         
        }
    }

    /**
     * Hides the expanded Toolbar when it is collapsed further than the value defined in PERCENTAGE_TO_HIDE_TITLE_DETAILS
     * @param percentage the current percentage
     */
    private void handleAlphaOnTitle(float percentage) {
        if (percentage >= PERCENTAGE_TO_HIDE_TITLE_DETAILS) {
            if (mIsTheTitleContainerVisible) {
                startAlphaAnimation(mTitleContainer, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleContainerVisible = false;
            }

        } else {

            if (!mIsTheTitleContainerVisible) {
                startAlphaAnimation(mTitleContainer, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleContainerVisible = true;
            }
        }
    }
	
	/**
	 * Hides the FavorizeFab when the Toolbar is collapsed further than the value defined in PERCENTAGE_TO_HIDE_TITLE_DETAILS
	 * @param percentage the current percentage
	 */
	private void handleFavoriteFabVisibility(float percentage) {
		if (percentage >= PERCENTAGE_TO_HIDE_FAVORITE_BUTTON) {
			if (mIsFavorizeButtonVisible) {
				startAlphaAnimation(mFavorize, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
				mIsFavorizeButtonVisible = false;
				invalidateOptionsMenu();
			}
			
		} else {
			
			if (!mIsFavorizeButtonVisible) {
				startAlphaAnimation(mFavorize, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
				mIsFavorizeButtonVisible = true;
				invalidateOptionsMenu();
			}
		}
	}

    /**
     * fades in or out the given View with the given time.
     */
    public static void startAlphaAnimation(View v, long duration, int visibility) {
        AlphaAnimation alphaAnimation = (visibility == View.VISIBLE)
                ? new AlphaAnimation(0f, 1f)
                : new AlphaAnimation(1f, 0f);

        alphaAnimation.setDuration(duration);
        alphaAnimation.setFillAfter(true);
        v.startAnimation(alphaAnimation);
    }

    /**
     * Inits the UI with the value passed as Bundle to the Activity
     */
    private void initUI() {
        Bundle extras = getIntent().getBundleExtra(Constants.INTENT_BUNDLE);
	    ListMovie listMovie = extras.getParcelable(Constants.INTENT_BUNDLE_MOVIE);
	    if(listMovie!=null) {
		    mTitle.setText(listMovie.getTitle());
		    mTitleTextExpanded.setText(listMovie.getTitle());
		    Picasso.with(this).load(NetworkHelper.getImageUrl(listMovie.getPosterPath(), NetworkHelper.ImageSize.medium)).placeholder(R.drawable.progress_animation).into(mPosterImageView);
		    Picasso.with(this).load(NetworkHelper.getImageUrl(listMovie.getBackdropPath(), NetworkHelper.ImageSize.medium)).placeholder(R.drawable.progress_animation).into(mBackDropImageView);
	    }
	    setSupportActionBar(mToolbar);
	    getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

}
