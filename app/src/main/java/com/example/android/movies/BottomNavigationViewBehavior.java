package com.example.android.movies;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Erzeugt von M. Fengels am 26.02.2018.
 */

public class BottomNavigationViewBehavior extends CoordinatorLayout.Behavior<BottomNavigationView> {
	
	public BottomNavigationViewBehavior() {
		super();
	}
	
	public BottomNavigationViewBehavior(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	@Override
	public boolean layoutDependsOn(CoordinatorLayout parent, BottomNavigationView child, View dependency) {
		return dependency instanceof FrameLayout;
	}
	
	@Override
	public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull BottomNavigationView child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
		return axes == ViewCompat.SCROLL_AXIS_VERTICAL;
	}
	
	@Override
	public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull BottomNavigationView child, @NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
		if(dy < 0) {
			showBottomNavigationView(child);
		}
		else if(dy > 0) {
			hideBottomNavigationView(child);
		}
	}
	
	
	private void hideBottomNavigationView(BottomNavigationView view) {
		view.animate().translationY(view.getHeight());
	}
	
	private void showBottomNavigationView(BottomNavigationView view) {
		view.animate().translationY(0);
	}
}

