package com.example.android.movies;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Erzeugt von M. Fengels am 26.02.2018.
 */

public class MoviePosterFragment extends Fragment {
	
	RecyclerView mPosterGrid;
	
	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_overview, container, false);
		
		mPosterGrid = rootView.findViewById(R.id.recycler_overview);
		GridLayoutManager gridManager = new GridLayoutManager(getContext(),2);
		mPosterGrid.setLayoutManager(gridManager);
		MoviePosterAdapter adapter = new MoviePosterAdapter(getContext(), new MoviePosterAdapter.MovieSelectedListener() {
			@Override
			public void onMovieSelected(ImageView posterView) {
				ActivityOptions options = null;
				if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
					options = ActivityOptions.makeSceneTransitionAnimation(getActivity(), posterView, getContext().getString(R.string.picture_transition_name));
					Intent intent = new Intent(getContext(),DetailActivity.class);
					getContext().startActivity(intent, options.toBundle());
				}
			}
		});
		mPosterGrid.setAdapter(adapter);
		
		return rootView;
	}
	
	public void ScrollToTop(){
		mPosterGrid.smoothScrollToPosition(0);
	}
}
