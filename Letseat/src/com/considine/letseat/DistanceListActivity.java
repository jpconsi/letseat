/**
 Copyright (C) 2013 John Considine.  All rights reserved.
 */

package com.considine.letseat;

import com.considine.util.Utils;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

/**
 * FragmentActivity to hold the main {@link DistanceListActivity}. On larger
 * screen devices which can fit two panes also load
 * {@link DistanceDetailFragment}.
 */
public class DistanceListActivity extends FragmentActivity {

    // Defines a tag for identifying log entries
    private static final String TAG = "DistanceListActivity";

    private DistanceListFragment mDistanceListFragment;

    private DistanceDetailFragment mDistanceDetailFragment;

    // If true, this is a larger screen device which fits two panes
    private boolean isTwoPaneLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (BuildConfig.DEBUG) {
            Utils.enableStrictMode();
        }
        super.onCreate(savedInstanceState);

        // Set main content view. On smaller screen devices this is a single
        // pane view with one
        // fragment. One larger screen devices this is a two pane view with two
        // fragments.
        setContentView(R.layout.distance_list_fragment);

        mDistanceDetailFragment = (DistanceDetailFragment)getSupportFragmentManager()
                .findFragmentById(R.id.distance_details_layout);
        // mDistanceDetailFragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction()
                .add(R.id.distance_details_layout, mDistanceDetailFragment).commit();

        // mDistanceListFragment =
        // (DistanceListFragment)getSupportFragmentManager().findFragmentById(
        // R.id.distance_list_fragment);

        // Check if two pane bool is set based on resource directories
        isTwoPaneLayout = getResources().getBoolean(R.bool.has_two_panes);

        if (isTwoPaneLayout) {
            // If two pane layout, locate the contact detail fragment
            mDistanceDetailFragment = (DistanceDetailFragment)getSupportFragmentManager()
                    .findFragmentById(R.id.distance_details_layout);
        }
    }
}
