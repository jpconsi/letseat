/**
 Copyright (C) 2013 John Considine.  All rights reserved.
 */

package com.example.android.apis.app;

import com.considine.letseat.R;

import android.app.Activity;
import android.os.Bundle;

/**
 * FragmentActivity to hold the main {@link TitlesActivity}.
 */
public class TitlesActivity extends Activity {

    // Defines a tag for identifying log entries
    private static final String TAG = "TitlesActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_titles);
    }

    /*
     * @Override public void onItemSelected(String link) { DetailFragment
     * fragment = (DetailFragment)getFragmentManager().findFragmentById(
     * R.id.detailFragment); if (fragment != null && fragment.isInLayout()) {
     * fragment.setText(link); } }
     */

}
