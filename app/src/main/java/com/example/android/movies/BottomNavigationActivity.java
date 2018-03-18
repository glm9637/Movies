package com.example.android.movies;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.MenuItem;


/**
 * Created by glm9637 on 18.03.2018 12:21.
 */

public abstract class BottomNavigationActivity extends AppCompatActivity {
    private FragmentManager mFragmentManager;
    private SparseArray<Fragment> mFragments;

    interface NavigationItemSelected {
        void onNavigationItemSelected(MenuItem item);

        void onNavigationItemReSelected(MenuItem item);
    }

    protected NavigationItemSelected mNavigationItemSelectedListener;
    private BottomNavigationView mBottomNavigation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewID());
        mBottomNavigation = findViewById(R.id.bottom_navigation);
        mFragments = new SparseArray<>();
        setupBottomNavigation();
        setupFragments();
    }

    /**
     * sets the Listeners to the BottomNavigation and gets a reference to the SupportFragmentManager
     */
    private void setupBottomNavigation() {
        mBottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                pushFragment(mFragments.get(item.getItemId()));
                if (mNavigationItemSelectedListener != null) {
                    mNavigationItemSelectedListener.onNavigationItemSelected(item);
                }
                return true;
            }
        });

        mBottomNavigation.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                pushFragment(mFragments.get(item.getItemId()));
                if (mNavigationItemSelectedListener != null) {
                    mNavigationItemSelectedListener.onNavigationItemReSelected(item);
                }
            }
        });

        mFragmentManager = getSupportFragmentManager();
    }

    /**
     * abstract Method in which the child classes should load the Fragments and add it to the List
     */
    protected abstract void setupFragments();

    /**
     * abstract Method the get the id of the layout of the Activity
     */
    protected abstract int getContentViewID();

    /**
     * adds a Fragment to the Activity
     * @param fragmentID the ID of the Item inside the menu of the BottomNavigation
     * @param fragment the fragment
     */
    protected void addFragment(int fragmentID, Fragment fragment) {
        mFragments.append(fragmentID, fragment);
    }

    /**
     * Loads the Fragment referenced to the id
     * @param id the id of the menuItem which is referenced with the fragment
     */
    protected void setStartFragment(int id) {
        mBottomNavigation.setSelectedItemId(id);
    }


    /**
     * Displays a new Fragment
     * @param newFragment the Fragment to be displayed.
     */
    private void pushFragment(Fragment newFragment) {

        FragmentTransaction objFragmentTransaction = mFragmentManager.beginTransaction();
        objFragmentTransaction.replace(R.id.fragment_container, newFragment);
        objFragmentTransaction.commit();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("Fragment", mBottomNavigation.getSelectedItemId());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            int fragment = savedInstanceState.getInt("Fragment");
            mBottomNavigation.setSelectedItemId(fragment);
        }

    }

}
