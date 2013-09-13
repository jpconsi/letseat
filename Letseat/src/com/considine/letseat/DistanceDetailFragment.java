/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.considine.letseat;

import com.considine.util.Utils;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.StructuredPostal;
import android.provider.ContactsContract.Contacts;
import android.provider.ContactsContract.Data;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * This fragment displays details of a specific contact from the contacts
 * provider. It shows the contact's display photo, name and all its mailing
 * addresses. You can also modify this fragment to show other information, such
 * as phone numbers, email addresses and so forth. This fragment appears
 * full-screen in an activity on devices with small screen sizes, and as part of
 * a two-pane layout on devices with larger screens, alongside the
 * {@link DistanceListFragment}. To create an instance of this fragment, use the
 * factory method {@link DistanceDetailFragment#newInstance(android.net.Uri)},
 * passing as an argument the contact Uri for the contact you want to display.
 */
public class DistanceDetailFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<Cursor> {

    // The geo Uri scheme prefix, used with Intent.ACTION_VIEW to form a
    // geographical address
    // intent that will trigger available apps to handle viewing a location
    // (such as Maps)
    private static final String GEO_URI_SCHEME_PREFIX = "geo:0,0?q=";

    /**
     * Factory method to generate a new instance of the fragment given a contact
     * Uri. A factory method is preferable to simply using the constructor as it
     * handles creating the bundle and setting the bundle as an argument.
     * 
     * @param contactUri The contact Uri to load
     * @return A new instance of {@link DistanceDetailFragment}
     */
    public static DistanceDetailFragment newInstance() {
        // Create new instance of this fragment
        final DistanceDetailFragment fragment = new DistanceDetailFragment();

        // Return fragment
        return fragment;
    }

    private Boolean mIsTwoPaneLayout;

    private TextView mDistanceName;

    private LinearLayout mDetailsLayout;

    private TextView mEmptyView;

    /**
     * Fragments require an empty constructor.
     */
    public DistanceDetailFragment() {
    }

    /**
     * When the Fragment is first created, this callback is invoked. It
     * initializes some key class fields.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Check if this fragment is part of a two pane set up or a single pane
        mIsTwoPaneLayout = getResources().getBoolean(R.bool.has_two_panes);

        // Let this fragment contribute menu items
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflates the main layout to be used by this fragment
        final View detailView = inflater.inflate(R.layout.distance_detail_fragment, container,
                false);

        // Gets handles to view objects in the layout

        mDetailsLayout = (LinearLayout)detailView.findViewById(R.id.distance_details_layout);
        mEmptyView = (TextView)detailView.findViewById(android.R.id.empty);

        if (mIsTwoPaneLayout) {
            // If this is a two pane view, the following code changes the
            // visibility of the contact
            // name in details. For a one-pane view, the contact name is
            // displayed as a title.
            mDistanceName = (TextView)detailView.findViewById(R.id.distance_name);
            mDistanceName.setVisibility(View.VISIBLE);
        }

        return detailView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // If not being created from a previous state
        /*
         * if (savedInstanceState == null) { // Sets the argument extra as the
         * currently displayed contact setContact(getArguments() != null ?
         * (Uri)getArguments() .getParcelable(EXTRA_CONTACT_URI) : null); } else
         * { // If being recreated from a saved state, sets the contact from the
         * // incoming // savedInstanceState Bundle
         * setContact((Uri)savedInstanceState.getParcelable(EXTRA_CONTACT_URI));
         * }
         */
    }

    /**
     * When the Fragment is being saved in order to change activity state, save
     * the currently-selected contact.
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        /*
         * // Saves the contact Uri outState.putParcelable(EXTRA_CONTACT_URI,
         * mContactUri);
         */
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /*
         * switch (item.getItemId()) { // When "edit" menu option selected case
         * R.id.menu_edit_distance: // Standard system edit contact intent
         * Intent intent = new Intent(Intent.ACTION_EDIT, mContactUri); //
         * Because of an issue in Android 4.0 (API level 14), clicking // Done
         * or Back in the // People app doesn't return the user to your app;
         * instead, it // displays the People // app's contact list. A
         * workaround, introduced in Android 4.0.3 // (API level 15) is // to
         * set a special flag in the extended data for the Intent you // send to
         * the People // app. The issue is does not appear in versions prior to
         * // Android 4.0. You can use // the flag with any version of the
         * People app; if the // workaround isn't needed, // the flag is
         * ignored. intent.putExtra("finishActivityOnSaveCompleted", true); //
         * Start the edit activity startActivity(intent); return true; }
         */
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        // Inflates the options menu for this fragment
        // inflater.inflate(R.menu.distance_detail_menu, menu);

        /*
         * // Gets a handle to the "find" menu item mEditContactMenuItem =
         * menu.findItem(R.id.menu_edit_); // If contactUri is null the edit
         * menu item should be hidden, otherwise // it is visible.
         * mEditContactMenuItem.setVisible(mContactUri != null);
         */
    }

    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        /*
         * switch (id) { // Two main queries to load the required information
         * case DistanceDetailQuery.QUERY_ID: // This query loads main contact
         * details, see // DistanceDetailQuery for more information. return new
         * CursorLoader(getActivity(), mContactUri,
         * DistanceDetailQuery.PROJECTION, null, null, null); case
         * DistanceAddressQuery.QUERY_ID: // This query loads contact address
         * details, see // DistanceAddressQuery for more information. final Uri
         * uri = Uri.withAppendedPath(mContactUri,
         * Contacts.Data.CONTENT_DIRECTORY); return new
         * CursorLoader(getActivity(), uri, DistanceAddressQuery.PROJECTION,
         * DistanceAddressQuery.SELECTION, null, null); }
         */
        return null;
    }

    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    public void onLoaderReset(Loader<Cursor> loader) {
        // Nothing to do here. The Cursor does not need to be released as it was
        // never directly
        // bound to anything (like an adapter).
    }

    /**
     * Constructs a geo scheme Uri from a postal address.
     * 
     * @param postalAddress A postal address.
     * @return the geo:// Uri for the postal address.
     */
    private Uri constructGeoUri(String postalAddress) {
        // Concatenates the geo:// prefix to the postal address. The postal
        // address must be
        // converted to Uri format and encoded for special characters.
        return Uri.parse(GEO_URI_SCHEME_PREFIX + Uri.encode(postalAddress));
    }

    /**
     * Fetches the width or height of the screen in pixels, whichever is larger.
     * This is used to set a maximum size limit on the contact photo that is
     * retrieved from the Contacts Provider. This limit prevents the app from
     * trying to decode and load an image that is much larger than the available
     * screen area.
     * 
     * @return The largest screen dimension in pixels.
     */
    private int getLargestScreenDimension() {
        // Gets a DisplayMetrics object, which is used to retrieve the display's
        // pixel height and
        // width
        final DisplayMetrics displayMetrics = new DisplayMetrics();

        // Retrieves a displayMetrics object for the device's default display
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        final int height = displayMetrics.heightPixels;
        final int width = displayMetrics.widthPixels;

        // Returns the larger of the two values
        return height > width ? height : width;
    }

    /**
     * This interface defines constants used by contact retrieval queries.
     */
    public interface DistanceDetailQuery {
        // A unique query ID to distinguish queries being run by the
        // LoaderManager.
        final static int QUERY_ID = 1;

        // The query projection (columns to fetch from the provider)
        @SuppressLint("InlinedApi")
        final static String[] PROJECTION = {
                Contacts._ID,
                Utils.hasHoneycomb() ? Contacts.DISPLAY_NAME_PRIMARY : Contacts.DISPLAY_NAME,
        };

        // The query column numbers which map to each value in the projection
        final static int ID = 0;

        final static int DISPLAY_NAME = 1;
    }

    /**
     * This interface defines constants used by address retrieval queries.
     */
    public interface DistanceAddressQuery {
        // A unique query ID to distinguish queries being run by the
        // LoaderManager.
        final static int QUERY_ID = 2;

        // The query projection (columns to fetch from the provider)
        final static String[] PROJECTION = {
                StructuredPostal._ID, StructuredPostal.FORMATTED_ADDRESS, StructuredPostal.TYPE,
                StructuredPostal.LABEL,
        };

        // The query selection criteria. In this case matching against the
        // StructuredPostal content mime type.
        final static String SELECTION = Data.MIMETYPE + "='" + StructuredPostal.CONTENT_ITEM_TYPE
                + "'";

        // The query column numbers which map to each value in the projection
        final static int ID = 0;

        final static int ADDRESS = 1;

        final static int TYPE = 2;

        final static int LABEL = 3;
    }
}
