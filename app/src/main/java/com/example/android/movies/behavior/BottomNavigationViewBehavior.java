package com.example.android.movies.behavior;

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

    private int height;
	private boolean expanded = true;

    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, BottomNavigationView child, int layoutDirection) {
        height = child.getHeight();
        return super.onLayoutChild(parent, child, layoutDirection);
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout,
                                       @NonNull BottomNavigationView child, @NonNull
                                               View directTargetChild, @NonNull View target,
                                       int axes, int type)
    {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL;
    }

    @Override
    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull BottomNavigationView child,
                               @NonNull View target, int dxConsumed, int dyConsumed,
                               int dxUnconsumed, int dyUnconsumed,
                               @ViewCompat.NestedScrollType int type)
    {
        if (dyConsumed > 0) {
            slideDown(child);
        } else if (dyConsumed < 0) {
            slideUp(child);
        }
    }

    /**
     * Animates the sliding up of the BottomNavigationView
     * @param child the BottomNavigationView to slide up
     */
    private void slideUp(BottomNavigationView child) {
        child.clearAnimation();
        child.animate().translationY(0).setDuration(200);
        expanded = true;
    }

    /**
     * Animates the sliding down of the BottomNavigationView
     * @param child the BottomNavigationView to slide down
     */
    private void slideDown(BottomNavigationView child) {
        child.clearAnimation();
        child.animate().translationY(height).setDuration(200);
        expanded = false;
    }
    
    public boolean isExpanded(){
    	return expanded;
    }
}

