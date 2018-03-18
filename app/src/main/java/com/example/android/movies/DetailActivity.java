package com.example.android.movies;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.movies.fragments.detail.CastFragment;
import com.example.android.movies.fragments.detail.InfoFragment;
import com.example.android.movies.fragments.detail.MediaFragment;
import com.example.android.movies.networking.NetworkHelper;
import com.example.android.movies.utils.Constants;
import com.squareup.picasso.Picasso;

public class DetailActivity extends BottomNavigationActivity
        implements AppBarLayout.OnOffsetChangedListener {

    private static final float PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR = 0.9f;
    private static final float PERCENTAGE_TO_HIDE_TITLE_DETAILS = 0.3f;
    private static final int ALPHA_ANIMATIONS_DURATION = 200;

    private boolean mIsTheTitleVisible = false;
    private boolean mIsTheTitleContainerVisible = true;

    private LinearLayout mTitleContainer;
    private TextView mTitle;
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
        mToolbar.inflateMenu(R.menu.menu_detail);
        startAlphaAnimation(mTitle, 0, View.INVISIBLE);
    }

    @Override
    protected void setupFragments() {
        InfoFragment infoFragment = new InfoFragment();
        infoFragment.setArguments(getIntent().getBundleExtra(Constants.INTENT_BUNDLE_DETAIL));
        addFragment(R.id.action_description, infoFragment);

        CastFragment castFragment = new CastFragment();
        castFragment.setArguments(getIntent().getBundleExtra(Constants.INTENT_BUNDLE_DETAIL));
        addFragment(R.id.action_actors,castFragment);

        MediaFragment mediaFragment = new MediaFragment();
        mediaFragment.setArguments(getIntent().getBundleExtra(Constants.INTENT_BUNDLE_DETAIL));
        addFragment(R.id.action_media,mediaFragment);

        setStartFragment(R.id.action_description);
    }


    @Override
    protected int getContentViewID() {
        return R.layout.activity_detail;
    }

    private void bindActivity() {
        mToolbar = findViewById(R.id.main_toolbar);
        mTitle = findViewById(R.id.main_textview_title);
        mTitleContainer = findViewById(R.id.main_linearlayout_title);
        mAppBarLayout = findViewById(R.id.main_appbar);
        mPosterImageView = findViewById(R.id.poster_view);
        mBackDropImageView = findViewById(R.id.backdrop_view);
        mTitleTextExpanded = findViewById(R.id.title_text_expanded);
    }

    /**
     * Handles the Alpha and Visibility changes when the offset changes
     * @param appBarLayout
     * @param offset
     */
    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int offset) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(offset) / (float) maxScroll;

        handleAlphaOnTitle(percentage);
        handleToolbarTitleVisibility(percentage);
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
        mTitle.setText(extras.getString(Constants.INTENT_BUNDLE_TITLE));
        mTitleTextExpanded.setText(extras.getString(Constants.INTENT_BUNDLE_TITLE));
        Picasso.with(this).load(NetworkHelper.getImageUrl(extras.getString(Constants.INTENT_BUNDLE_POSTER_PATH), NetworkHelper.ImageSize.medium)).placeholder(R.drawable.progress_animation).into(mPosterImageView);
        Picasso.with(this).load(NetworkHelper.getImageUrl(extras.getString(Constants.INTENT_BUNDLE_BACKDROP_PATH), NetworkHelper.ImageSize.medium)).placeholder(R.drawable.progress_animation).into(mBackDropImageView);
    }

}
