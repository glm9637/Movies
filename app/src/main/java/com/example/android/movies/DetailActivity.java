package com.example.android.movies;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.movies.database.MovieContract;
import com.example.android.movies.fragments.detail.CastFragment;
import com.example.android.movies.fragments.detail.InfoFragment;
import com.example.android.movies.fragments.detail.MediaFragment;
import com.example.android.movies.fragments.detail.ReviewFragment;
import com.example.android.movies.fragments.detail.TrailerFragment;
import com.example.android.movies.model.ListMovie;
import com.example.android.movies.networking.NetworkHelper;
import com.example.android.movies.utils.Constants;
import com.example.android.movies.utils.Utils;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;

public class DetailActivity extends BottomNavigationActivity
        implements AppBarLayout.OnOffsetChangedListener, LoaderManager.LoaderCallbacks<Cursor> {

    private static final float PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR = 0.9f;
    private static final float PERCENTAGE_TO_HIDE_TITLE_DETAILS = 0.3f;
    private static final float PERCENTAGE_TO_HIDE_FAVORITE_BUTTON = 0.8f;
    private static final int ALPHA_ANIMATIONS_DURATION = 200;

    private boolean mIsTheTitleVisible = false;
    private boolean mIsTheTitleContainerVisible = true;
    private boolean mIsFavorizeButtonVisible = true;
	private boolean mIsFavoritMovie = false;
    
    private LinearLayout mTitleContainer;
    private TextView mTitle;
    private FloatingActionButton mFavorize;
    private AppBarLayout mAppBarLayout;
    private Toolbar mToolbar;
    private ImageView mPosterImageView;
    private ImageView mBackDropImageView;
    private TextView mTitleTextExpanded;
	private ListMovie mMovie;
	private InfoFragment mInfoFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindActivity();
        initUI();
        mAppBarLayout.addOnOffsetChangedListener(this);
        startAlphaAnimation(mTitle, 0, View.INVISIBLE);
	    getSupportLoaderManager().initLoader(Constants.LOADER_ID_MOVIE_INTERNAL, null, this).forceLoad();
    }

    @Override
    protected void setupFragments() {
    	mMovie = getIntent().getBundleExtra(Constants.INTENT_BUNDLE).getParcelable(Constants.INTENT_BUNDLE_MOVIE);
        mInfoFragment = new InfoFragment();
        mInfoFragment.setArguments(getIntent().getBundleExtra(Constants.INTENT_BUNDLE));
        addFragment(R.id.action_description, mInfoFragment);

        CastFragment castFragment = new CastFragment();
        castFragment.setArguments(getIntent().getBundleExtra(Constants.INTENT_BUNDLE));
        addFragment(R.id.action_actors,castFragment);

        MediaFragment mediaFragment = new MediaFragment();
        mediaFragment.setArguments(getIntent().getBundleExtra(Constants.INTENT_BUNDLE));
        addFragment(R.id.action_media,mediaFragment);
	
	    TrailerFragment trailerFragment = new TrailerFragment();
	    trailerFragment.setArguments(getIntent().getBundleExtra(Constants.INTENT_BUNDLE));
		addFragment(R.id.action_trailer,trailerFragment);
	
	    ReviewFragment reviewFragment = new ReviewFragment();
	    reviewFragment.setArguments(getIntent().getBundleExtra(Constants.INTENT_BUNDLE));
	    addFragment(R.id.action_review,reviewFragment);
	    
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
			menu.add(0,R.id.menu_item_favorize,0,"Favorize")
					.setIcon(mIsFavoritMovie?R.drawable.ic_favorite_red_24dp: R.drawable.ic_favorite_border_white_24dp)
					.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		}
		return super.onPrepareOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()){
			case R.id.menu_item_share:
				Intent shareIntent = new Intent(Intent.ACTION_SEND);
				shareIntent.setType("text/plain");
				shareIntent.putExtra(Intent.EXTRA_SUBJECT,mMovie.getTitle());
				shareIntent.putExtra(Intent.EXTRA_TEXT,mMovie.getLink());
				startActivity(Intent.createChooser(shareIntent,"Share this movie"));
				break;
			case R.id.menu_item_favorize:
				toggleFavorit();
		}
		
		return true;
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
				startAlphaAnimation(mFavorize, ALPHA_ANIMATIONS_DURATION, View.GONE);
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
    public static void startAlphaAnimation(final View v,final long duration, final int visibility) {
        AlphaAnimation alphaAnimation = (visibility == View.VISIBLE)
                ? new AlphaAnimation(0f, 1f)
                : new AlphaAnimation(1f, 0f);

        alphaAnimation.setDuration(duration);
        alphaAnimation.setFillAfter(true);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
	        @Override
	        public void onAnimationStart(Animation animation) {
		
	        }
	
	        @Override
	        public void onAnimationEnd(Animation animation) {
				v.setVisibility(visibility);
	        }
	
	        @Override
	        public void onAnimationRepeat(Animation animation) {
		
	        }
        });
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
	    mFavorize.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {
				toggleFavorit();
		    }
	    });
	    
    }
    
    private void toggleFavorit(){
    	mIsFavoritMovie = !mIsFavoritMovie;
    	setFavoritUI();
    	Runnable r = new Runnable() {
		    @Override
		    public void run() {
			    if(mIsFavoritMovie){
				    ContentValues data = mInfoFragment.getMovieData();
				
				    String posterPath = saveToInternalStorage(Utils.drawableToBitmap(mPosterImageView.getDrawable()),"poster");
				    String backdropPath = saveToInternalStorage(Utils.drawableToBitmap(mBackDropImageView.getDrawable()),"backdrop");
				    data.put(MovieContract.MovieEntry.COLUMN_POSTER,posterPath);
				    data.put(MovieContract.MovieEntry.COLUMN_BACKDROP, backdropPath);
				    getContentResolver().insert(MovieContract.MovieEntry.CONTENT_URI,data);
			    }else {
				    deleteFromInternalStorage();
				    getContentResolver().delete(ContentUris.withAppendedId(MovieContract.MovieEntry.CONTENT_URI,mMovie.getId()),null,null);
			    }
		    }
	    };
    	Thread t = new Thread(r);
    	t.start();
    }
    
    private void setFavoritUI(){
    	mFavorize.setImageResource(mIsFavoritMovie?R.drawable.ic_favorite_red_24dp: R.drawable.ic_favorite_border_white_24dp);
    	invalidateOptionsMenu();
    }
    
    private void deleteFromInternalStorage(){
	    ContextWrapper cw = new ContextWrapper(getApplicationContext());
	    File directory = cw.getDir(mMovie.getId().toString(), Context.MODE_PRIVATE);
	    directory.delete();
	    
    }
	
	private String saveToInternalStorage(Bitmap bitmapImage, String name){
		ContextWrapper cw = new ContextWrapper(getApplicationContext());
		File directory = cw.getDir(mMovie.getId().toString(), Context.MODE_PRIVATE);
		File mypath=new File(directory,name+".jpg");
		
		FileOutputStream fos = null;
		try {
			// fos = openFileOutput(filename, Context.MODE_PRIVATE);
			
			fos = new FileOutputStream(mypath);
			
			// Use the compress method on the BitMap object to write image to the OutputStream
			bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return directory.getAbsolutePath();
	}
	
	@NonNull
	@Override
	public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
    	switch (id){
		    default:
			    Uri movieUri = ContentUris.withAppendedId(MovieContract.MovieEntry.CONTENT_URI,mMovie.getId());
			    return new android.support.v4.content.CursorLoader(this, movieUri ,null,null,null,null);
	    }
		
	}
	
	@Override
	public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        if(data == null) {
	        mIsFavoritMovie =false;
        }else {
	        mIsFavoritMovie = data.moveToFirst();
	        data.close();
        }
		
		setFavoritUI();
	}
	
	@Override
	public void onLoaderReset(@NonNull Loader<Cursor> loader) {
	
	}
}
